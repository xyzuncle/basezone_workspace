package com.kq.perimission.service;

import com.kq.perimission.domain.BaseElement;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yerui
 * @since 2018-03-23
 */
public interface IBaseElementService extends IService<BaseElement> {

    public List<BaseElement> getAllElement();

    public List<BaseElement> getElementByUser(String userId);

    public boolean getAcess(HttpServletRequest request, Authentication authentication);

    public List<BaseElement> getElementByUserId(@Param("userId") String userId);


}
