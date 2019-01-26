package com.kq.perimission.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.kq.common.DTO.ObjectRestResponse;
import com.kq.perimission.moudle.specifications.DynamicSpecifications;
import com.kq.perimission.moudle.specifications.SearchFilter;
import com.kq.perimission.util.Sort;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.named.NamedContextFactory;


import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BaseController<T,S extends IService<T>> {

    protected Class<T> entityClass;

    @Autowired(required=true)
    protected S defaultDAO;

    @Autowired
    public HttpServletRequest request;

    public Page<T> queryContion(Map<String,Object> searchParams, Sort sort) throws Exception {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        int pageNumber=0;
        int pageSize=10;
        if(request.getParameter("pageNumber")==null || "".equals(request.getParameter("pageNumber"))){
            pageNumber=1;
        }else{
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"))>1?
                    Integer.parseInt(request.getParameter("pageNumber")):1;

        }
        if(request.getParameter("pageSize")==null || "".equals(request.getParameter("pageSize"))){
            pageSize =10;
        }else{
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }

        EntityWrapper<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), entityClass);

        if(StringUtils.isNotBlank(sort.getDirection())){
            if(sort.getDirection().equals("ASC")){
                spec.orderAsc(Arrays.asList(sort.getColName()));

            } else if (sort.getDirection().equals("DESC")) {
                spec.orderDesc(Arrays.asList(sort.getColName()));
            }else {
                throw new Exception("sort parame is error");
            }
        }
        Page<T> pagelist = null;
        pagelist = defaultDAO.selectPage(new Page<T>(pageNumber,pageSize),spec);
        return pagelist;
    }

    public Page<T> queryContion(Map<String,Object> searchParams){
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        int pageNumber=0;
        int pageSize=10;
        if(request.getParameter("pageNumber")==null || "".equals(request.getParameter("pageNumber"))){
            pageNumber=0;
        }else{
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"))>1?
                    Integer.parseInt(request.getParameter("pageNumber")):1;

        }
        if(request.getParameter("pageSize")==null || "".equals(request.getParameter("pageSize"))){
            pageSize =10;
        }else{
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        }

        EntityWrapper<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), entityClass);

        Page<T> pagelist = null;
        pagelist = defaultDAO.selectPage(new Page<T>(pageNumber,pageSize),spec);
        return pagelist;
    }


    /**
     * 没有分页的List多条件查询
     * @param searchParams
     * @return
     */
    public List<T> queryContionNoPage(Map<String,Object> searchParams){
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        EntityWrapper<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), entityClass);
        List<T> resultList = this.defaultDAO.selectList(spec);
        return resultList;
    }


    /**
     * 判断一个实体是否存在
     * @param colName 字段名称
     * @param colValue 字段的值
     * @return  false 表示数据不存在   true 表示存在
     */
    public boolean exists(String colName,String colValue){
        boolean resulut = false;
        EntityWrapper<T> spec = new EntityWrapper<T>();
        spec.and(""+colName+"={0}",colValue);
         int count = this.defaultDAO.selectCount(spec);
        if(count <0){
            resulut = false;
        }else  if(count > 0) {
            resulut = true;

        }
        return resulut;
    }


    /**
     * 统一的向前端突出一个包装体，方便前端调用
     * @param o
     * @param message
     * @return
     */
    public Object jsonObjectResult(Object o,String message){
        if(o==null){
            return JSON.toJSON(new ObjectRestResponse().data("").message("未查询到数据！"));
        }else{
            return JSON.toJSON(new ObjectRestResponse().data(o).message(message).rel(true));
        }

    }






}
