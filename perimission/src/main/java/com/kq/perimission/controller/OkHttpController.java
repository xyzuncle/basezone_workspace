package com.kq.perimission.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kq.common.DTO.ObjectRestResponse;
import com.kq.perimission.domain.BaseElement;
import com.kq.perimission.dto.SwaggerDTO;
import com.kq.perimission.service.impl.BaseElementServiceImpl;
import io.swagger.models.Swagger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.codehaus.jackson.map.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequestMapping("/okhttp")
@Controller
public class OkHttpController {

    @Value("${swagger.rootpath}")
    private String hostUrl;

    @Autowired
    OkHttpClient okHttpClient;

    @Autowired
    BaseElementServiceImpl baseElementService;

    @RequestMapping(value = "/importApi",method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse parseSwaggerDocs() throws Exception {
        String token = null;
        try {
            String url = hostUrl + "/pmsn/v2/api-docs";
            Request request = new Request.Builder().url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            /*SwaggerDTO swaggerDTO = (SwaggerDTO) JSON.parse(result);*/
            JSONObject jsonObject = JSON.parseObject(result);
            String baseUrl = jsonObject.get("basePath").toString();
            JSONObject pathObject = jsonObject.getJSONObject("paths");
            List<BaseElement> baseElements = new ArrayList<BaseElement>();
              pathObject.keySet().stream().forEach((k) ->{
                  JSONObject resourceObj = null;
                  BaseElement baseElement = new BaseElement();
                  String method = null;
                  if(pathObject.getJSONObject(k).containsKey("get")==true){
                        resourceObj = pathObject.getJSONObject(k).getJSONObject("get");
                      method = "get";
                   }else if(pathObject.getJSONObject(k).containsKey("delete")==true){
                        resourceObj = pathObject.getJSONObject(k).getJSONObject("delete");
                      method = "delete";
                   }else if(pathObject.getJSONObject(k).containsKey("post")==true){
                        resourceObj = pathObject.getJSONObject(k).getJSONObject("post");
                      method = "post";
                   }
                  baseElement.setCode(baseUrl);
                  baseElement.setType(resourceObj.get("tags").toString());
                  baseElement.setName(resourceObj.get("summary").toString());
                  baseElement.setUri(k);
                  baseElement.setMethod(method);
                  baseElement.setDescription(resourceObj.get("description")==null?"":resourceObj.get("description").toString());
                  //默认可用
                  baseElement.setStatus("1");
                  baseElements.add(baseElement);

              });
            baseElementService.insertBatch(baseElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ObjectRestResponse().data(token);
    }

}
