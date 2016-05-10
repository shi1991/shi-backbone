package com.puyuntech.ycmall.entity;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import javax.persistence.*;
import java.util.Set;

/**
 * 
 *  资讯. 
 * Created on 2015-12-11 下午1:17:37 
 * @author 王凯斌
 */
@Entity
@Table(name="t_information")
public class Information extends BaseEntity<Long> {

	private static final long serialVersionUID = -4609532834993651364L;

	/**咨询标题 */
	private String title;
	
	/**咨询作者 */
	private String author;
	
	/**图片 */
	private String image;
	
	/**咨询内容 */
	private String content;
	
	/**是否发布 */
	private Boolean isDisplay;
	
	/**创建人 */
	private Admin creater;
	
	/**关联组织 */
	private Set<Organization> organizations;

	@Column(name="f_title",nullable=false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="f_author")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name="f_image")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Lob
	@Column(name="f_content",nullable=false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="f_is_display",nullable=false)
	public Boolean getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="f_creater")
	public Admin getCreater() {
		return creater;
	}

	public void setCreater(Admin creater) {
		this.creater = creater;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_organization_information", joinColumns = { @JoinColumn(name = "f_information", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_organization", referencedColumnName = "f_id") })
	@OrderBy("order asc")
	public Set<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(Set<Organization> organizations) {
		this.organizations = organizations;
	}
	
}
