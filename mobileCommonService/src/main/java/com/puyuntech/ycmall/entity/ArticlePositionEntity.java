package com.puyuntech.ycmall.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * Entity - 文章位. 
 * Created on 2015-8-26 下午4:39:20 
 * @author Liaozhen
 */
@Indexed
@Entity
@Table(name = "t_article_position")
public class ArticlePositionEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = -8074127358653854855L;
	
	/** 文章位名称 */
	private String name;
	
	/** 宽度 */
	private Integer width;
	
	/** 高度 */
	private Integer height;
	
	/** 描述 */
	private String description;
	
	/** 模板 */
	private String template;
	
	/** 文章  **/
	private Set<ArticleEntity> articles = new HashSet<>();

	@NotEmpty
	@Length(max = 255)
	@Column(name = "f_name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "f_width", nullable = false)
	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Column(name = "f_height", nullable = false)
	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Column(name = "f_description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Lob
	@Column(name = "f_template", nullable = false)
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	@ManyToMany(targetEntity=ArticleEntity.class , mappedBy = "positions", fetch = FetchType.LAZY)
	public Set<ArticleEntity> getArticles() {
		return articles;
	}

	public void setArticles(Set<ArticleEntity> articles) {
		this.articles = articles;
	}
	
	
	

}