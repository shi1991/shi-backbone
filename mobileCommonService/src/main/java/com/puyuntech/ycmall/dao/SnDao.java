package com.puyuntech.ycmall.dao;

import com.puyuntech.ycmall.entity.Sn;

/**
 * 
 * Dao 生成序列号 . 
 * Created on 2015-9-13 下午2:43:26 
 * @author 施长成
 */
public interface SnDao {

	/**
	 * 
	 * 生成序列号 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-9-13 下午2:44:01
	 * @param type 类型 
	 * @return 序列号
	 */
	String generate(Sn.Type type);
	
}
