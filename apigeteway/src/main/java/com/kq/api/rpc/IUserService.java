package com.kq.api.rpc;



import com.kq.api.config.FeignConfig;
import com.kq.common.DTO.PerimissionInfo;
import com.kq.common.domain.BaseUser;
import com.kq.common.domain.RemainingNumber;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@FeignClient(value = "perimission",configuration = FeignConfig.class)
public interface IUserService {

    @RequestMapping(value="/api/permissions",method = RequestMethod.GET)
    List<PerimissionInfo> getAllPerimissionInfo();
    //根据用户获取该用户下的权限
    @RequestMapping(value="/api/user/un/{username}/permissions",method = RequestMethod.GET)
    public List<PerimissionInfo> getPermissionByUsername(@PathVariable("username") String username);

    @RequestMapping(value = "/api/user/us/{username}/permissions",method = RequestMethod.GET)
    public List<PerimissionInfo> getServicePermissionByUserName(@PathVariable("username") String username);

    @RequestMapping(value = "/api/userapi/{username}",method = RequestMethod.GET)
    public List<PerimissionInfo> getUserApiByUserId(@PathVariable("username") String username);

    @RequestMapping(value = "/satellitePerimission/satelitePression.do",method = RequestMethod.GET)
    public Boolean getSatellitePerimission(@RequestParam("satelliteType") String satelliteType,
                                          @RequestParam("satelliteId") String satelliteId,
                                          @RequestParam("sensorId") String sensorId);

    @RequestMapping(value = "/baseUser/getUserInfo",method = RequestMethod.GET)
    public BaseUser getUserByInfo(@RequestParam("username") String username);

    @RequestMapping(value = "/api/user/getuser/{username}", method = RequestMethod.GET)
    public BaseUser getUserByName(@PathVariable("username") String username);


    @RequestMapping(value = "/remainingNumber/queryRemaingInfo.do",method = RequestMethod.GET)
    public RemainingNumber getFontPermission(@RequestParam("userId") String userId,
                                                      @RequestParam("satelliteType") String satelliteType,
                                                      @RequestParam("satelliteId") String satelliteId);

    @RequestMapping(value = "/remainingNumber/getRedisMap.do", method = RequestMethod.GET)
    public void getRidesMapByUserId(@RequestParam("type") String type,
                                    @RequestParam("userId") String userId,
                                    @RequestParam("level") String level);

    @RequestMapping(value = "/token/genJwtToken", method = RequestMethod.GET)
    public void genJwtToken(@RequestParam("userName") String userName);



}
