
package com.puyuntech.ycmall.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.puyuntech.ycmall.BaseAttributeConverter;

/**
 * 
 * Entity - 插件配置. 
 * Created on 2015-10-14 下午2:41:57 
 * @author 王凯斌
 */
@Entity
@Table(name = "t_plugin_config")
public class PluginConfig extends OrderEntity<Long> {

	private static final long serialVersionUID = -9032604480440886099L;

	/** 插件ID */
	private String pluginId;

	/** 是否启用 */
	private Boolean isEnabled;

	/** 属性 */
	private Map<String, String> attributes = new HashMap<String, String>();

	/**
	 * 获取插件ID
	 * 
	 * @return 插件ID
	 */
	@Column(name="f_plugin_id",nullable = false, updatable = false, unique = true)
	public String getPluginId() {
		return pluginId;
	}

	/**
	 * 设置插件ID
	 * 
	 * @param pluginId
	 *            插件ID
	 */
	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	/**
	 * 获取是否启用
	 * 
	 * @return 是否启用
	 */
	@Column(name="is_enabled",nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	/**
	 * 设置是否启用
	 * 
	 * @param isEnabled
	 *            是否启用
	 */
	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	/**
	 * 获取属性
	 * 
	 * @return 属性
	 */
	@Column(name="f_attributes",length = 4000)
	@Convert(converter = MapConverter.class)
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * 设置属性
	 * 
	 * @param attributes
	 *            属性
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	/**
	 * 获取属性值
	 * 
	 * @param name
	 *            属性名称
	 * @return 属性值
	 */
	@Transient
	public String getAttribute(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		return getAttributes() != null ? getAttributes().get(name) : null;
	}

	/**
	 * 类型转换 - Map
	 * 
	 */
	@Converter
	public static class MapConverter extends BaseAttributeConverter<Map<String, String>> implements AttributeConverter<Object, String> {
	}

}
