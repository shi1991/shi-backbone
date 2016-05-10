package com.puyuntech.ycmall.entity.value;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 参数值 Entity . 
 * Created on 2015-8-27 下午12:05:55 
 * @author 施长成
 */
public class ParameterValue implements Serializable {

	private static final long serialVersionUID = 1162528455861645377L;

	/** 参数组 */
	private String group;

	/** 条目 */
	private List<ParameterValue.Entry> entries = new ArrayList<ParameterValue.Entry>();

	/**
	 * 获取参数组
	 * 
	 * @return 参数组
	 */
	@NotEmpty
	@Length(max = 200)
	public String getGroup() {
		return group;
	}

	/**
	 * 设置参数组
	 * 
	 * @param group
	 *            参数组
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * 获取条目
	 * 
	 * @return 条目
	 */
	@Valid
	@NotEmpty
	public List<ParameterValue.Entry> getEntries() {
		return entries;
	}

	/**
	 * 设置条目
	 * 
	 * @param entries
	 *            条目
	 */
	public void setEntries(List<ParameterValue.Entry> entries) {
		this.entries = entries;
	}

	/**
	 * Entity - 条目
	 * 
	 */
	public static class Entry implements Serializable {

		private static final long serialVersionUID = 2665540272509005738L;

		/** 名称 */
		private String name;

		/** 值 */
		private String value;

		/**
		 * 获取名称
		 * 
		 * @return 名称
		 */
		@NotEmpty
		@Length(max = 200)
		public String getName() {
			return name;
		}

		/**
		 * 设置名称
		 * 
		 * @param name
		 *            名称
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 获取值
		 * 
		 * @return 值
		 */
		@NotEmpty
		@Length(max = 200)
		public String getValue() {
			return value;
		}

		/**
		 * 设置值
		 * 
		 * @param value
		 *            值
		 */
		public void setValue(String value) {
			this.value = value;
		}

	}

}
