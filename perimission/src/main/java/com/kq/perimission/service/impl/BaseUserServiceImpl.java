package com.kq.perimission.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.base.Strings;
import com.kq.common.exception.BaseException;
import com.kq.common.exception.UserException.UserInvaildException;
import com.kq.perimission.controller.rpc.service.PerimissionService;
import com.kq.perimission.domain.BaseGroup;
import com.kq.perimission.domain.BaseGroupMember;
import com.kq.perimission.domain.BaseUser;
import com.kq.perimission.domain.OauthClientDetails;
import com.kq.perimission.mail.EmailService;
import com.kq.perimission.mapper.BaseUserMapper;
import com.kq.perimission.redis.RedisService;
import com.kq.perimission.service.IBaseUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kq.perimission.util.MD5;
import com.kq.perimission.util.Tools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.ServletWebRequest;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yerui
 * @since 2018-03-21
 */
@Service
@Slf4j
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements IBaseUserService {

    @Value("${spring.outside.default.roleId}")
    private String outSideDefaultRoleId;

    @Value("${spring.outside.default.orgId}")
    private String outSideDefaultOrgId;

    @Autowired
    private BaseGroupMemberServiceImpl baseGroupMemberService;

    @Autowired
    private PerimissionService perimissionService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private OauthClientDetailsServiceImpl oauthClientDetailsService;

  /*  @Autowired
    ProviderSignInUtils providerSignInUtils;*/


    @Override
    public BaseUser getUserByUserName(String userName) {
        return this.baseMapper.getUserInfoByUserName(userName);
    }

    @Override
    public boolean getExistUser(@RequestParam String mobileNum) {
        boolean result = false;
        int count = this.baseMapper.getExistUserInfo(mobileNum);
        if (count > 0) {
            result = true;
        } else if (count <=0) {
            result = false;
        }
        return result;
    }

    @Override
    @Transactional
    public boolean customSaveUser(BaseUser user,HttpServletRequest request) {
        //如果前端注册不分配角色，那么就是外部个人默认角色
        if(Strings.isNullOrEmpty(user.getGroupId())){
            String outSideDefaultRoleName = "外部个人默认角色";
            user.setGroupName(outSideDefaultRoleName);
            user.setGroupId(outSideDefaultRoleId);

        }

        String tempGroupId = user.getGroupId();
        String tempUserId = user.getId();

        //如果前端注册的是机构，是通过的情况,需要变更角色
        if(user.getVerifyStatus()!=null && user.getVerifyStatus().equals("1")){
            user.setGroupId(outSideDefaultOrgId);
            user.setGroupName("外部注册机构账户角色");
        }
        //反查groupId的作用是为了比较组是否有变化，
        BaseUser baseUser = this.selectById(tempUserId);
        String oldGroupId = "";
        if(baseUser!=null){
             oldGroupId = baseUser.getGroupId();
        }else{
            //证明是新增的用户
        }

        //移动上段代码逻辑，是以为我需要反查用户的组ID,然后在保存用户
        boolean result = this.insertOrUpdate(user);

      /*  //增加了社交用户的信息与业务用户的关系绑定.很重要
        providerSignInUtils.doPostSignUp(user.getId(),new ServletWebRequest(request));*/

        //如果tempUserId是空，证明是新增，同时更新中间表关系
        if(StringUtils.isBlank(tempUserId)){
            this.perimissionService.updateNumByUserGroupID(user.getGroupId(),user.getId(),oldGroupId);
        }
        //如果userID 和 gourpID 都存在的情况下，证明是修改，包含组ID变化的逻辑判断
        else if(StringUtils.isNotBlank(user.getGroupId())&& StringUtils.isNotEmpty(user.getId())){

            this.perimissionService.updateNumByUserGroupID(user.getGroupId(),user.getId(),oldGroupId);
        }

        BaseGroupMember baseGroupMember = new BaseGroupMember();
        //所属组ID
        baseGroupMember.setGroupId(user.getGroupId());
        //所属用户ID
        baseGroupMember.setUserId(user.getId());
        this.baseGroupMemberService.insertOrUpdate(baseGroupMember);



        //维护用户与客户端之间的关系
        if(StringUtils.isNotBlank(user.getClientId())){
            OauthClientDetails existObj =  oauthClientDetailsService.selectById(user.getClientId());
            if(Objects.isNull(existObj)){
                OauthClientDetails details = new OauthClientDetails();
                details.setClientId(user.getClientId());
                oauthClientDetailsService.insert(details);
            }else{
                /*log.debug("用户已经存在客户端的关系！");*/
            }

        }

        return result;

    }

    @Override
    public boolean compliePass(String userId, String pass) {
        BaseUser baseUser= this.baseMapper.selectById(userId);
        String oldPass = baseUser.getPassword();
        //考虑到资源卫星老用户的修改密码情况，这里需要做特别处理
        boolean result;
        if(StringUtils.isNotBlank(baseUser.getSatelliteType())){
            if(baseUser.getSatelliteType().equals("1")){
                ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
                result = shaPasswordEncoder.isPasswordValid(oldPass, pass, null);
            }else{
                result = MD5.compilePass(pass, oldPass);
            }
        }else{
             result = MD5.compilePass(pass, oldPass);
        }



        return result;
    }



    public List<BaseUser> getFullTextUser(String searchkey){
        EntityWrapper<BaseUser> ew = new EntityWrapper<BaseUser>();
        ew.where(true,"MATCH(mobile_phone,username,idepart_name,name) AGAINST({0} IN BOOLEAN MODE)",searchkey);
        List<BaseUser> result =   this.baseMapper.getFullTextUser(ew);
        return result;
    }


    /**
     * 忘记密码，重置密码
     *  code 邮箱返回的验证码
     * @return
     */
    @Override
    public String sendMail(String sentToEmail){
        //生成6位的随机数
        String tempCode = Tools.getRandomNum()+"";
        String code = redisService.getStrTemplate().opsForValue().get(sentToEmail);

       // String code = null;

        //如果从redis拿到的为空
        if (Strings.isNullOrEmpty(code)) {
            //十分钟内失效
          /*  redisService.setStr(sentToEmail, tempCode+"", 600);*/
            redisService.getStrTemplate().opsForValue().set(sentToEmail, tempCode+"", 600);
            //通过邮件发送
            try {
                emailService.sendForgetpw(tempCode,sentToEmail);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            tempCode = code;
        }



        return tempCode;
    }

    /**
     * 根据邮箱重置password
     * @param newPassword
     * @param email
     */
    @Override
    public void resetPwd(String newPassword,String email) {
        String sha1pwd = MD5.encryptPassword(newPassword);
        this.baseMapper.resetPwdByEmail(email, sha1pwd);
    }

    @Override
    public boolean getExistByType(String somename, String type) {
        boolean result =false;
        if(StringUtils.isNotBlank(type) && StringUtils.isNotBlank(somename)){
            EntityWrapper ew = new EntityWrapper();

            if(type.equalsIgnoreCase("email")){
                ew.eq("email", somename);
                result =  getExistResult(ew);
            }else if(type.equalsIgnoreCase("username")){
                ew.eq("username", somename);
                result =  getExistResult(ew);
            }else if(type.equalsIgnoreCase("mobile")){
                ew.eq("mobile", somename);
                result =  getExistResult(ew);
            }

        }else{
            throw new BaseException("参数和类型不能为空");
        }
        return result;
    }

    private boolean getExistResult(EntityWrapper ew) {
        int result = this.selectCount(ew);
        if(result>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断邮箱是否唯一
     * @param email
     * @return
     */
    @Override
    public boolean getExistEmail(String email){
        boolean result = false;
        int count = this.baseMapper.getExistEmail(email);
        if (count > 0) {
            result = true;
        } else if (count <=0) {
            result = false;
        }
        return result;
    }






}
