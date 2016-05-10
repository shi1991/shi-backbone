package com.puyuntech.ycmall.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.springframework.util.CollectionUtils;

import com.puyuntech.ycmall.Setting;
import com.puyuntech.ycmall.util.SystemUtils;

/**
 * 
 * Entity - 文章. 
 * Created on 2015-8-17 上午11:25:49 
 * @author Liaozhen
 */
@Indexed
@Entity
@Table(name = "t_article")
public class ArticleEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = -6451390787219930495L;

	/** 点击数缓存名称 */
	public static final String HITS_CACHE_NAME = "articleHits";

	/** 内容分页长度 */
	private static final int PAGE_CONTENT_LENGTH = 2000;

	/** 内容分页标签 */
	private static final String PAGE_BREAK_TAG = "puyun_page_break_tag";

	/** 段落配比 */
	private static final Pattern PARAGRAPH_PATTERN = Pattern.compile("[^,;\\.!?，；。！？]*([,;\\.!?，；。！？]+|$)");

	/**
	 * 静态生成方式
	 */
	public enum GenerateMethod {

		/** 无 */
		none,

		/** 即时 */
		eager,

		/** 延时 */
		lazy
	}

	/** 标题 */
	private String title;

	/** 作者 */
	private String author;

	/** 内容 */
	private String content;

	/** 页面标题 */
	private String seoTitle;

	/** 页面关键词 */
	private String seoKeywords;

	/** 页面描述 */
	private String seoDescription;

	/** 是否发布 */
	private Boolean isPublication;

	/** 是否置顶 */
	private Boolean isTop;

	/** 点击数 */
	private Long hits;

	/** 静态生成方式 */
	private ArticleEntity.GenerateMethod generateMethod;

	/** 标签 */
	private Set<Tag> tags = new HashSet<Tag>();

	/** 文章宣传图片 */
	private String image;
	
	/** 文章位置 **/
	private Set<ArticlePositionEntity> positions = new HashSet<>();
	
	/**
	 * 获取宣传图片
	 * 
	 * @return 图片路径
	 */
	@Length(max = 255)
	@Column(name = "f_image")
	public String getImage() {
		return image;
	}

	/**
	 * 设置宣传图片
	 * 
	 * @param image
	 *            图片路径
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 获取标题
	 * 
	 * @return 标题
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@NotEmpty
	@Length(max = 200)
	@Column(name = "f_title", nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取作者
	 * 
	 * @return 作者
	 */
	@Field(store = Store.YES, index = Index.NO, analyze = Analyze.NO)
	@Length(max = 200)
	@Column(name = "f_author")
	public String getAuthor() {
		return author;
	}

	/**
	 * 设置作者
	 * 
	 * @param author
	 *            作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.YES)
	@Lob
	@Column(name = "f_content")
	public String getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content
	 *            内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取页面标题
	 * 
	 * @return 页面标题
	 */
	@Column(name = "f_seo_title")
	@Length(max = 200)
	public String getSeoTitle() {
		return seoTitle;
	}

	/**
	 * 设置页面标题
	 * 
	 * @param seoTitle
	 *            页面标题
	 */
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	/**
	 * 获取页面关键词
	 * 
	 * @return 页面关键词
	 */
	@Column(name = "f_seo_keywords")
	@Length(max = 200)
	public String getSeoKeywords() {
		return seoKeywords;
	}

	/**
	 * 设置页面关键词
	 * 
	 * @param seoKeywords
	 *            页面关键词
	 */
	public void setSeoKeywords(String seoKeywords) {
		if (seoKeywords != null) {
			seoKeywords = seoKeywords.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
		}
		this.seoKeywords = seoKeywords;
	}

	/**
	 * 获取页面描述
	 * 
	 * @return 页面描述
	 */
	@Column(name = "f_seo_description")
	@Length(max = 200)
	public String getSeoDescription() {
		return seoDescription;
	}

	/**
	 * 设置页面描述
	 * 
	 * @param seoDescription
	 *            页面描述
	 */
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	/**
	 * 获取是否发布
	 * 
	 * @return 是否发布
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NotNull
	@Column(name = "f_is_publication", nullable = false)
	public Boolean getIsPublication() {
		return isPublication;
	}

	/**
	 * 设置是否发布
	 * 
	 * @param isPublication
	 *            是否发布
	 */
	public void setIsPublication(Boolean isPublication) {
		this.isPublication = isPublication;
	}

	/**
	 * 获取是否置顶
	 * 
	 * @return 是否置顶
	 */
	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NotNull
	@Column(name = "f_is_top", nullable = false)
	public Boolean getIsTop() {
		return isTop;
	}

	/**
	 * 设置是否置顶
	 * 
	 * @param isTop
	 *            是否置顶
	 */
	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	/**
	 * 获取点击数
	 * 
	 * @return 点击数
	 */
	@Column(name = "f_hits", nullable = false)
	public Long getHits() {
		return hits;
	}

	/**
	 * 设置点击数
	 * 
	 * @param hits
	 *            点击数
	 */
	public void setHits(Long hits) {
		this.hits = hits;
	}

	/**
	 * 获取静态生成方式
	 * 
	 * @return 静态生成方式
	 */
	@Column(name = "f_generate_method", nullable = false)
	public ArticleEntity.GenerateMethod getGenerateMethod() {
		return generateMethod;
	}

	/**
	 * 设置静态生成方式
	 * 
	 * @param generateMethod
	 *            静态生成方式
	 */
	public void setGenerateMethod(ArticleEntity.GenerateMethod generateMethod) {
		this.generateMethod = generateMethod;
	}

	/**
	 * 获取标签
	 * 
	 * @return 标签
	 *  ManyToMany
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_article_tag", joinColumns = { @JoinColumn(name = "f_articles", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_tags", referencedColumnName = "f_id") })
	@OrderBy("order asc")
	public Set<Tag> getTags() {
		return tags;
	}

	/**
	 * 设置标签
	 * 
	 * @param tags
	 *            标签
	 */
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_article_article_position", joinColumns = { @JoinColumn(name = "f_article", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_position", referencedColumnName = "f_id") })
	@OrderBy("order asc")
	public Set<ArticlePositionEntity> getPositions() {
		return positions;
	}

	public void setPositions(Set<ArticlePositionEntity> positions) {
		this.positions = positions;
	}

	/**
	 * 获取路径
	 * 
	 * @return 路径
	 */
	@Transient
	public String getPath() {
		return getPath(1);
	}

	/**
	 * 获取路径
	 * 
	 * @param pageNumber
	 *            页码
	 * @return 路径
	 */
	@Transient
	public String getPath(Integer pageNumber) {
		return null;
	}

	/**
	 * 获取URL
	 * 
	 * @return URL
	 */
	@Transient
	public String getUrl() {
		return getUrl(1);
	}

	/**
	 * 获取URL
	 * 
	 * @param pageNumber
	 *            页码
	 * @return URL
	 */
	@Transient
	public String getUrl(Integer pageNumber) {
		if (pageNumber == null || pageNumber < 1) {
			return null;
		}
		Setting setting = SystemUtils.getSetting();
		return setting.getSiteUrl() + getPath(pageNumber);
	}

	/**
	 * 获取文本内容
	 * 
	 * @return 文本内容
	 */
	@Transient
	public String getText() {
		if (StringUtils.isEmpty(getContent())) {
			return StringUtils.EMPTY;
		}
		return StringUtils.remove(Jsoup.parse(getContent()).text(), PAGE_BREAK_TAG);
	}

	/**
	 * 获取分页内容
	 * 
	 * @return 分页内容
	 */
	@Transient
	public String[] getPageContents() {
		if (StringUtils.isEmpty(getContent())) {
			return new String[] { StringUtils.EMPTY };
		}
		if (StringUtils.contains(getContent(), PAGE_BREAK_TAG)) {
			return StringUtils.splitByWholeSeparator(getContent(), PAGE_BREAK_TAG);
		}
		List<Node> childNodes = Jsoup.parse(getContent()).body().childNodes();
		if (CollectionUtils.isEmpty(childNodes)) {
			return new String[] { getContent() };
		}
		List<String> pageContents = new ArrayList<String>();
		int textLength = 0;
		StringBuilder paragraph = new StringBuilder();
		for (Node node : childNodes) {
			if (node instanceof Element) {
				Element element = (Element) node;
				paragraph.append(element.outerHtml());
				textLength += element.text().length();
				if (textLength >= PAGE_CONTENT_LENGTH) {
					pageContents.add(paragraph.toString());
					textLength = 0;
					paragraph.setLength(0);
				}
			} else if (node instanceof TextNode) {
				TextNode textNode = (TextNode) node;
				Matcher matcher = PARAGRAPH_PATTERN.matcher(textNode.text());
				while (matcher.find()) {
					String content = matcher.group();
					paragraph.append(content);
					textLength += content.length();
					if (textLength >= PAGE_CONTENT_LENGTH) {
						pageContents.add(paragraph.toString());
						textLength = 0;
						paragraph.setLength(0);
					}
				}
			}
		}
		String pageContent = paragraph.toString();
		if (StringUtils.isNotEmpty(pageContent)) {
			pageContents.add(pageContent);
		}
		return pageContents.toArray(new String[pageContents.size()]);
	}

	/**
	 * 获取分页内容
	 * 
	 * @param pageNumber
	 *            页码
	 * @return 分页内容
	 */
	@Transient
	public String getPageContent(Integer pageNumber) {
		if (pageNumber == null || pageNumber < 1) {
			return null;
		}
		String[] pageContents = getPageContents();
		if (pageContents.length < pageNumber) {
			return null;
		}
		return pageContents[pageNumber - 1];
	}

	/**
	 * 获取总页数
	 * 
	 * @return 总页数
	 */
	@Transient
	public int getTotalPages() {
		return getPageContents().length;
	}

}
