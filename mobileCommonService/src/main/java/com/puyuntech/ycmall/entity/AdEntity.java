package com.puyuntech.ycmall.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * AdEntity - 广告实体. 
 * Created on 2015-8-24 下午5:05:30 
 * @author Liaozhen
 */
@Entity
@Table(name = "t_ad")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_ad")
public class AdEntity extends OrderEntity<Long>{

	private static final long serialVersionUID = 524864822499088441L;

	/** 广告类型 */
	public enum Type {

		/** 文本 */
		text,

		/** 图片 */
		image
	}

	/** 标题 */
	private String title;

	/** 类型 */
	private AdEntity.Type type;

	/** 内容 */
	private String content;

	/** 路径 */
	private String path;

	/** 起始日期 */
	private Date beginDate;

	/** 结束日期 */
	private Date endDate;

	/** 链接地址 */
	private String url;

	/** 广告位 */
	private AdPositionEntity adPosition;
	
	/** 是否在移动端显示 **/
	private String showMobile;
	
	
	/**
	 * 获取是否在移动端显示.
	 * 该字段不确定是否使用
	 * author: Liaozhen
	 *   date: 2015-8-24 下午5:07:42
	 * @return String PC端0；移动端1；POS端2
	 */
	@Length(max = 200)
	@Column(name = "f_position_type")
	public String getPositionType() {
		return showMobile;
	}

	/**
	 * 设置是否在移动端显示.
	 * 该字段不确定是否使用
	 * author: Liaozhen
	 *   date: 2015-8-24 下午5:10:04
	 * @param String PC端0；移动端1；POS端2
	 */
	public void setPositionType(String showMobile) {
		this.showMobile = showMobile;
	}
	
	
	/**
	 * 获取标题.
	 * author: Liaozhen
	 *   date: 2015-8-24 下午5:11:10
	 * @return String 标题
	 */
	@NotEmpty
	@Length(max = 200)
	@Column(name = "f_title", nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题.
	 * author: Liaozhen
	 *   date: 2015-8-24 下午5:11:44
	 * @param String 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取类型.
	 * author: Liaozhen
	 *   date: 2015-8-24 下午5:12:08
	 * @return enum.type 文字/图片
	 */
	@NotNull
	@Column(name = "f_type", nullable = false)
	public AdEntity.Type getType() {
		return type;
	}

	/**
	 * 设置类型.
	 * author: Liaozhen
	 *   date: 2015-8-24 下午5:13:11
	 * @param AdEntity.type 文字/图片
	 */
	public void setType(AdEntity.Type type) {
		this.type = type;
	}

	/**
	 * 获取内容.
	 * author: Liaozhen
	 *   date: 2015-8-24 下午5:13:54
	 * @return String 内容
	 */
	@Lob
	@Column(name = "f_content")
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容.
	 * author: Liaozhen
	 *   date: 2015-8-24 下午5:14:17
	 * @param String 内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取图片路径.
	 * 正则验证路径是否正确
	 * author: Liaozhen
	 *   date: 2015-8-24 下午5:14:41
	 * @return String 图片路径
	 */
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	@Column(name = "f_path")
	public String getPath() {
		return path;
	}

	/**
	 * 设置 图片路径.
	 * author: Liaozhen
	 *   date: 2015-8-24 下午5:15:33
	 * @param String 路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 获取开始时间.
	 * author: Liaozhen
	 *   date: 2015-8-24 下午5:17:06
	 * @return Date 开始时间
	 */
	@Column(name = "f_begin_date")
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * 设置开始时间.
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	
	@Column(name = "f_end_date")
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置结束日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取链接地址
	 * 
	 * @return 链接地址
	 */
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|ftp:\\/\\/|mailto:|\\/|#).*$")
	@Column(name = "f_url")
	public String getUrl() {
		return url;
	}

	/**
	 * 设置链接地址
	 * 
	 * @param url
	 *            链接地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取广告位
	 * 
	 * @return 广告位
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "f_ad_position", nullable = false)
	public AdPositionEntity getAdPosition() {
		return adPosition;
	}

	/**
	 * 设置广告位
	 * 
	 * @param adPosition
	 *            广告位
	 */
	public void setAdPosition(AdPositionEntity adPosition) {
		this.adPosition = adPosition;
	}

	/**
	 * 判断是否已开始
	 * 
	 * @return 是否已开始
	 */
	@Transient
	public boolean hasBegun() {
		return getBeginDate() == null || !getBeginDate().after(new Date());
	}

	/**
	 * 判断是否已结束
	 * 
	 * @return 是否已结束
	 */
	@Transient
	public boolean hasEnded() {
		return getEndDate() != null && !getEndDate().after(new Date());
	}

}
