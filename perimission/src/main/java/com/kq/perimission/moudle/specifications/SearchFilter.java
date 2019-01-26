package com.kq.perimission.moudle.specifications;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Map.Entry;

public class SearchFilter {
	/**
	 * GTEDT 大于等于 DT 是针对时间格式 YYYY-MM-DD HH24:MM:MI的格式
	 */
	public enum Operator {
		EQ, LIKE, GT, LT, GE, LE,NEQ,GTEDT,LTEDT,FULLUSER,FULLROLE
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}


/**
	 * searchParams中key的格式为OPERATOR_FIELDNAME
	 */

	public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if (StringUtils.isBlank((String) value)) {
				continue;
			}

			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "-");
			if (names.length != 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			String filedName = names[1];
			Operator operator = Operator.valueOf(names[0]);
			/*增加了针对日期处理的方法*/
			try {
				if( value.toString().matches("((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))") ){

					SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					if( operator.equals(Operator.GE))
						value = formater.parse(value.toString()+" 00:00:00");
					if( operator.equals(Operator.LE))
						value = formater.parse(value.toString()+" 23:59:59");
					if( operator.equals(Operator.GT))
						value = formater.parse(value.toString()+" 00:00:00");
					if( operator.equals(Operator.LT))
						value = formater.parse(value.toString()+" 23:59:59");
					if(operator.equals(Operator.GTEDT))
						value = formater.parse(value.toString());
					if(operator.equals(Operator.LTEDT))
						value = formater.parse(value.toString());
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// 创建searchFilter
			SearchFilter filter = new SearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}
}

