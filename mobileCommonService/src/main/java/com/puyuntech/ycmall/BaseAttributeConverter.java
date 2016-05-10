package com.puyuntech.ycmall;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.AttributeConverter;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.ResolvableType;

import com.fasterxml.jackson.databind.JavaType;
import com.puyuntech.ycmall.util.JsonUtils;

/**
 * 
 * 类型转换的基类  . 
 * Created on 2015-8-14 下午4:46:37 
 * @author 施长成
 * @param <T>
 */
public abstract class BaseAttributeConverter<T> implements AttributeConverter<Object, String> {

	/** 类型 */
	private JavaType javaType;

	/**
	 * 构造方法
	 */
	public BaseAttributeConverter() {
		ResolvableType resolvableType = ResolvableType.forClass(getClass());
		Type type = resolvableType.getSuperType().getGeneric().getType();
		javaType = JsonUtils.constructType(type);
	}

	/**
	 * 转换属性为数据库值
	 * 
	 * @param attribute
	 *            属性
	 * @return 数据库值
	 */
	public String convertToDatabaseColumn(Object attribute) {
		if (attribute == null) {
			return null;
		}

		return JsonUtils.toJson(attribute);
	}

	/**
	 * 转换数据库值为属性
	 * 
	 * @param dbData
	 *            数据库值
	 * @return 属性
	 */
	public Object convertToEntityAttribute(String dbData) {
		if (StringUtils.isEmpty(dbData)) {
			if (List.class.isAssignableFrom(javaType.getRawClass())) {
				return Collections.EMPTY_LIST;
			} else if (Set.class.isAssignableFrom(javaType.getRawClass())) {
				return Collections.EMPTY_SET;
			} else if (Map.class.isAssignableFrom(javaType.getRawClass())) {
				return Collections.EMPTY_MAP;
			} else {
				return null;
			}
		}

		return JsonUtils.toObject(dbData, javaType);
	}

}