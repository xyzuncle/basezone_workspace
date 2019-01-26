package com.kq.perimission.moudle.specifications;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;

import com.kq.perimission.util.Collections3;
import org.apache.commons.lang3.StringUtils;


import java.util.Collection;
import java.util.List;

/**
 * 动态的获取查询字段，并封装成bean
 */
public class DynamicSpecifications {
    public static <T> EntityWrapper<T> bySearchFilter(final Collection<SearchFilter> filters, final Class<T> entityClazz) {

        EntityWrapper<T> ew = new EntityWrapper<T>();

        if (Collections3.isNotEmpty(filters)) {

            for (SearchFilter filter : filters) {
                // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
                String[] names = StringUtils.split(filter.fieldName, ".");

                // logic operator
                switch (filter.operator) {
                    case EQ:
                        ew.andNew().eq(names[0], filter.value);
                        break;
                    case LIKE:
                        ew.andNew().like(names[0],  filter.value+"");
                        break;
                    case GT:
                        //  predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
                        ew.gt(names[0], filter.value);
                        break;
                    case LT:
                        // predicates.add(builder.lessThan(expression, (Comparable) filter.value));
                        ew.lt(names[0], filter.value);
                        break;
                    case GE:
                        //   predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                        ew.ge(names[0], filter.value);
                        break;
                    case LE:
                        // predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                        ew.le(names[0], filter.value);
                        break;
                    case NEQ:
                        // predicates.add(builder.notEqual(expression, (Comparable) filter.value));
                        ew.ne(names[0], filter.value);
                        break;
                    case FULLUSER:
                      /*  ew.where(true,"MATCH(mobile_phone,username,idepart_name,name) AGAINST({0} IN BOOLEAN MODE)",filter.value+"*");*/
                        ew.andNew().like
                                ("mobile_phone",filter.value+"")
                                .or()
                                .like("username", filter.value+"")
                                .or()
                                .like("idepart_name", filter.value+"")
                                .or()
                                .like("name", filter.value+"");
                        break;
                    case FULLROLE:
                        ew.andNew().
                                like("name", filter.value + "")
                                .or()
                                .like("attr1", filter.value + "");
                        break;

                }
            }
        }
        return ew;
    }

}

