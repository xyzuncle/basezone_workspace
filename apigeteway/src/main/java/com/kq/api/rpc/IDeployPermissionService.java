package com.kq.api.rpc;

import com.kq.api.config.FeignConfig;
import com.kq.common.DTO.PerimissionInfo;
import com.kq.common.domain.ParamsModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @Description: 此处是要调用彭兴的内外网判断的接口
 * @Author: yerui
 * @CreateDate : 2019/1/10 16:52
 * @Version: 1.0
 *
 */
@FeignClient(value = "metadataservice",configuration = FeignConfig.class)
public interface IDeployPermissionService {
    //通过前端的语音或者是面板返回的请求串，来判断内外网结果
    @RequestMapping(value="/api/metadatas/satelliteInfo/auth",method = RequestMethod.POST)
    String getDeployPermissionStatus(@RequestBody Object params);
}
