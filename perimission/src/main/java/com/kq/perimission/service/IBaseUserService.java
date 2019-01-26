package com.kq.perimission.service;

import com.kq.perimission.domain.BaseUser;
import com.baomidou.mybatisplus.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-03-21
 */
public interface IBaseUserService extends IService<BaseUser> {

    /**
     * 根据用户名获取用户信息
     * @param userName
     * @return
     */
    public BaseUser getUserByUserName(String userName);


    /**
     * 根据手机号判断用户是否存在
     * @param mobileNum
     * @return
     */
    public boolean getExistUser(String mobileNum);

    /**
     * 保存用户的同时需要保存组 和 用户之间的关联关系
     * @param user
     */
    public boolean customSaveUser(BaseUser user, HttpServletRequest request);


    /**
     * 根据用户ID和新密码，来比较密码是否正确
     * @param
     *        userId
     * @param
     *        pass
     * @return
     */
    public boolean compliePass(String userId,String pass);


    public List<BaseUser> getFullTextUser(String searchkey);

    /**
     * 根据邮箱判断是否唯一
     * @param email
     * @return
     */
    public boolean getExistEmail(String email);

    /**
     * 发送邮箱,并返回随机的验证码
     */
    public String sendMail(String sentToEmail);


    /**
     * 重置密码
     * @param newPassword
     */
    public void resetPwd(String newPassword,String email);

    /**
     * 根据不同的类型效验是否唯一
     * @param somename
     * @param type
     * @return
     */
    public boolean getExistByType(String somename,String type);

}
