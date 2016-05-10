package com.puyuntech.ycmall;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 系统设置. 
 * Created on 2015-8-17 上午11:11:11 
 * @author 施长成
 */
@ScriptAssert(lang = "javascript", script = "_this.usernameMaxLength >= _this.usernameMinLength && _this.passwordMaxLength >= _this.passwordMinLength")
public class Setting implements Serializable {

	public static void main(String[] args) {
		System.out.println(WatermarkPosition.no.ordinal());

		System.out.println(WatermarkPosition.topLeft.ordinal());

		System.out.println(WatermarkPosition.topRight.ordinal());


	}

	private static final long serialVersionUID = -1478999889661796840L;

	/** 缓存名称 */
	public static final String CACHE_NAME = "setting";

	/** 分隔符 */
	private static final String SEPARATOR = ",";

	/**
	 * 水印位置
	 */
	public enum WatermarkPosition {

		/** 无 */
		no,

		/** 左上 */
		topLeft,

		/** 右上 */
		topRight,

		/** 居中 */
		center,

		/** 左下 */
		bottomLeft,

		/** 右下 */
		bottomRight
	}

	/**
	 * 小数位精确方式
	 */
	public enum RoundType {

		/** 四舍五入 */
		roundHalfUp,

		/** 向上取整 */
		roundUp,

		/** 向下取整 */
		roundDown
	}

	/**
	 * 验证码类型
	 */
	public enum CaptchaType {

		/** 会员登录 */
		memberLogin,

		/** 会员注册 */
		memberRegister,

		/** 后台登录 */
		adminLogin,

		/** 商品评论 */
		review,

		/** 商品咨询 */
		consultation,

		/** 找回密码 */
		findPassword,

		/** 重置密码 */
		resetPassword,

		/** 其它 */
		other
	}

	/**
	 * 账号锁定类型
	 */
	public enum AccountLockType {

		/** 会员 */
		member,

		/** 管理员 */
		admin
	}

	/**
	 * 库存分配时间点
	 */
	public enum StockAllocationTime {

		/** 下订单 */
		order,

		/** 订单支付 */
		payment,

		/** 订单发货 */
		ship
	}

	/**
	 * 评论权限
	 */
	public enum ReviewAuthority {

		/** 任何访问者 */
		anyone,

		/** 注册会员 */
		member,

		/** 已购买会员 */
		purchased
	}

	/**
	 * 咨询权限
	 */
	public enum ConsultationAuthority {

		/** 任何访问者 */
		anyone,

		/** 注册会员 */
		member
	}

	/**
	 * 区域设置
	 */
	public enum Locale {

		/** 中文(简体, 中国) */
		zh_CN,

		/** 中文(繁体, 台湾) */
		zh_TW,

		/** 英语(美国) */
		en_US
	}

	/** 网站名称 */
	private String siteName;

	/** 网站网址 */
	private String siteUrl;

	/** logo */
	private String logo;

	/** 热门搜索 */
	private String hotSearch;

	/** 联系地址 */
	private String address;

	/** 联系电话 */
	private String phone;

	/** 邮政编码 */
	private String zipCode;

	/** E-mail */
	private String email;

	/** 备案编号 */
	private String certtext;

	/** 是否网站开启 */
	private Boolean isSiteEnabled;

	/** 网站关闭消息 */
	private String siteCloseMessage;

	/** 商品图片(大)宽度 */
	private Integer largeProductImageWidth;

	/** 商品图片(大)高度 */
	private Integer largeProductImageHeight;

	/** 商品图片(中)宽度 */
	private Integer mediumProductImageWidth;

	/** 商品图片(中)高度 */
	private Integer mediumProductImageHeight;

	/** 商品缩略图宽度 */
	private Integer thumbnailProductImageWidth;

	/** 商品缩略图高度 */
	private Integer thumbnailProductImageHeight;

	/** 默认商品图片(大) */
	private String defaultLargeProductImage;

	/** 默认商品图片(小) */
	private String defaultMediumProductImage;

	/** 默认缩略图 */
	private String defaultThumbnailProductImage;

	/** 水印透明度 */
	private Integer watermarkAlpha;

	/** 水印图片 */
	private String watermarkImage;

	/** 水印位置 */
	private WatermarkPosition watermarkPosition;

	/** 价格精确位数 */
	private Integer priceScale;

	/** 价格精确方式 */
	private RoundType priceRoundType;

	/** 是否前台显示市场价 */
	private Boolean isShowMarketPrice;

	/** 默认市场价换算比例 */
	private Double defaultMarketPriceScale;

	/** 是否开放注册 */
	private Boolean isRegisterEnabled;

	/** 是否允许E-mail重复注册 */
	private Boolean isDuplicateEmail;

	/** 禁用用户名 */
	private String disabledUsername;

	/** 用户名最小长度 */
	private Integer usernameMinLength;

	/** 用户名最大长度 */
	private Integer usernameMaxLength;

	/** 密码最小长度 */
	private Integer passwordMinLength;

	/** 密码最大长度 */
	private Integer passwordMaxLength;

	/** 注册初始积分 */
	private Long registerPoint;

	/** 注册协议 */
	private String registerAgreement;

	/** 是否允许E-mail登录 */
	private Boolean isEmailLogin;

	/** 验证码类型 */
	private CaptchaType[] captchaTypes;

	/** 账号锁定类型 */
	private AccountLockType[] accountLockTypes;

	/** 连续登录失败最大次数 */
	private Integer accountLockCount;

	/** 自动解锁时间 */
	private Integer accountLockTime;

	/** 安全密匙有效时间 */
	private Integer safeKeyExpiryTime;

	/** 上传文件最大限制 */
	private Integer uploadMaxSize;

	/** 允许上传图片扩展名 */
	private String uploadImageExtension;

	/** 允许上传媒体扩展名 */
	private String uploadMediaExtension;

	/** 允许上传文件扩展名 */
	private String uploadFileExtension;

	/** 二维码图片保存路径 **/
	private String qrEcodeimageUploadPath;

	/** 二维码图片保存路径 **/
	private String barCodeimageUploadPath;

	/** 图片上传路径 */
	private String imageUploadPath;

	/** 媒体上传路径 */
	private String mediaUploadPath;

	/** 文件上传路径 */
	private String fileUploadPath;

	/** SMTP服务器地址 */
	private String smtpHost;

	/** SMTP服务器端口 */
	private Integer smtpPort;

	/** SMTP用户名 */
	private String smtpUsername;

	/** SMTP密码 */
	private String smtpPassword;

	/** SMTP是否启用SSL */
	private Boolean smtpSSLEnabled;

	/** 发件人邮箱 */
	private String smtpFromMail;

	/** 货币符号 */
	private String currencySign;

	/** 货币单位 */
	private String currencyUnit;

	/** 库存警告数 */
	private Integer stockAlertCount;

	/** 库存分配时间点 */
	private StockAllocationTime stockAllocationTime;

	/** 默认积分换算比例 */
	private Double defaultPointScale;

	/** 是否开启开发模式 */
	private Boolean isDevelopmentEnabled;

	/** 是否开启评论 */
	private Boolean isReviewEnabled;

	/** 是否审核评论 */
	private Boolean isReviewCheck;

	/** 评论权限 */
	private ReviewAuthority reviewAuthority;

	/** 是否开启咨询 */
	private Boolean isConsultationEnabled;

	/** 是否审核咨询 */
	private Boolean isConsultationCheck;

	/** 咨询权限 */
	private ConsultationAuthority consultationAuthority;

	/** 是否开启发票功能 */
	private Boolean isInvoiceEnabled;

	/** 是否开启含税价 */
	private Boolean isTaxPriceEnabled;

	/** 税率 */
	private Double taxRate;

	/** Cookie路径 */
	private String cookiePath;

	/** Cookie作用域 */
	private String cookieDomain;

	/** 快递100授权KEY */
	private String kuaidi100Key;

	/** 快递100推送路径 */
	private String kuaidi;

	/** 是否开启CNZZ统计 */
	private Boolean isCnzzEnabled;

	/** CNZZ统计站点ID */
	private String cnzzSiteId;

	/** CNZZ统计密码 */
	private String cnzzPassword;

	/** 短信服务器IP */
	private String smsHost;

	/** 短信服务器端口 */
	private String smsPort;

	/** 短信主账号ID */
	private String smsSn;

	/** 短信主账号的token*/
	private String smsKey;


	private String statusCode;

	/** 短信初始化应用ID */
	private String smsApplication;

	/** 区域设置 */
	private Locale locale;

	/** 主题 */
	private String theme;

	/** 炫酷神器默认图片 **/
	private String coolGoodsDefaultImg;

	/**神币转人民币的汇率 **/
	private String moneyRechargeGodMoney;

	/**人民币转神币的汇率 **/
	private String godMoneyRechargeMoney;

	/**神币转积分的汇率**/
	private String godMoneyExchangePoint;

	/** 用户推荐获利比率  **/
	private String recommendationRate;

	/** 炫酷神器默认提示信息 **/
	private String coolGoodsDefaultName;

	/** 线下体验信息  **/
	private String lineExperience;

	/** 短信模板 */
	private String smsTemplate;

	/** 邮件模板 */
	private String mailTemplate;

	/** 是否开启短信服务 */
	private Boolean smsIsEnable;

	/** 是否开启邮件服务 */
	private Boolean mailIsEnable;

	/** 项目部署路径 */
	private String projectPath;

	/** 红包拒绝原因一 */
	private String packetRefuseSeasonOne;

	/** 红包拒绝原因二 */
	private String packetRefuseSeasonTwo;

	/** 红包拒绝原因三 */
	private String packetRefuseSeasonThree;

	/** 红包拒绝原因四 */
	private String packetRefuseSeasonFour;

    /** 关于我们 **/
    private String footerAbout;

    /** 联系我们 **/
    private String footerContact;

    /** 法律声明 **/
    private String footerLawInfo;

    /** ios版本 **/
    private String iOSVersion;
    
    /** POS版本 **/
    private String posVersion;
    
    /** 安卓版本 **/
    private String andriodVersion;
    
	public String getiOSVersion() {
		return iOSVersion;
	}

	public void setiOSVersion(String iOSVersion) {
		this.iOSVersion = iOSVersion;
	}

	public String getPosVersion() {
		return posVersion;
	}

	public void setPosVersion(String posVersion) {
		this.posVersion = posVersion;
	}

	public String getAndriodVersion() {
		return andriodVersion;
	}

	public void setAndriodVersion(String andriodVersion) {
		this.andriodVersion = andriodVersion;
	}

	/**
	 * 获取网站名称
	 *
	 * @return 网站名称
	 */
	@NotEmpty
	@Length(max = 200)
	public String getSiteName() {
		return siteName;
	}

	/**
	 * 设置网站名称
	 *
	 * @param siteName
	 *            网站名称
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	/**
	 * 获取网站网址
	 *
	 * @return 网站网址
	 */
	@NotEmpty
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/).*$")
	public String getSiteUrl() {
		return siteUrl;
	}

	/**
	 * 设置网站网址
	 *
	 * @param siteUrl
	 *            网站网址
	 */
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = StringUtils.removeEnd(siteUrl, "/");
	}

	/**
	 * 获取logo
	 *
	 * @return logo
	 */
	@NotEmpty
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	public String getLogo() {
		return logo;
	}

	/**
	 * 设置logo
	 *
	 * @param logo
	 *            logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * 获取热门搜索
	 *
	 * @return 热门搜索
	 */
	@Length(max = 200)
	public String getHotSearch() {
		return hotSearch;
	}

	/**
	 * 设置热门搜索
	 *
	 * @param hotSearch
	 *            热门搜索
	 */
	public void setHotSearch(String hotSearch) {
		if (hotSearch != null) {
			hotSearch = hotSearch.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
		}
		this.hotSearch = hotSearch;
	}

	/**
	 * 获取联系地址
	 *
	 * @return 联系地址
	 */
	@Length(max = 200)
	public String getAddress() {
		return address;
	}

	/**
	 * 设置联系地址
	 *
	 * @param address
	 *            联系地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取联系电话
	 *
	 * @return 联系电话
	 */
	@Length(max = 200)
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置联系电话
	 *
	 * @param phone
	 *            联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取邮政编码
	 *
	 * @return 邮政编码
	 */
	@Length(max = 200)
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * 设置邮政编码
	 *
	 * @param zipCode
	 *            邮政编码
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * 获取E-mail
	 *
	 * @return E-mail
	 */
	@Email
	@Length(max = 200)
	public String getEmail() {
		return email;
	}

	/**
	 * 设置E-mail
	 *
	 * @param email
	 *            E-mail
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取备案编号
	 *
	 * @return 备案编号
	 */
	@Length(max = 200)
	public String getCerttext() {
		return certtext;
	}

	/**
	 * 设置备案编号
	 *
	 * @param certtext
	 *            备案编号
	 */
	public void setCerttext(String certtext) {
		this.certtext = certtext;
	}

	/**
	 * 获取是否网站开启
	 *
	 * @return 是否网站开启
	 */
	@NotNull
	public Boolean getIsSiteEnabled() {
		return isSiteEnabled;
	}

	/**
	 * 设置是否网站开启
	 *
	 * @param isSiteEnabled
	 *            是否网站开启
	 */
	public void setIsSiteEnabled(Boolean isSiteEnabled) {
		this.isSiteEnabled = isSiteEnabled;
	}

	/**
	 * 获取网站关闭消息
	 *
	 * @return 网站关闭消息
	 */
	@NotEmpty
	public String getSiteCloseMessage() {
		return siteCloseMessage;
	}

	/**
	 * 设置网站关闭消息
	 *
	 * @param siteCloseMessage
	 *            网站关闭消息
	 */
	public void setSiteCloseMessage(String siteCloseMessage) {
		this.siteCloseMessage = siteCloseMessage;
	}

	/**
	 * 获取商品图片(大)宽度
	 *
	 * @return 商品图片(大)宽度
	 */
	@NotNull
	@Min(1)
	public Integer getLargeProductImageWidth() {
		return largeProductImageWidth;
	}

	/**
	 * 设置商品图片(大)宽度
	 *
	 * @param largeProductImageWidth
	 *            商品图片(大)宽度
	 */
	public void setLargeProductImageWidth(Integer largeProductImageWidth) {
		this.largeProductImageWidth = largeProductImageWidth;
	}

	/**
	 * 获取商品图片(大)高度
	 *
	 * @return 商品图片(大)高度
	 */
	@NotNull
	@Min(1)
	public Integer getLargeProductImageHeight() {
		return largeProductImageHeight;
	}

	/**
	 * 设置商品图片(大)高度
	 *
	 * @param largeProductImageHeight
	 *            商品图片(大)高度
	 */
	public void setLargeProductImageHeight(Integer largeProductImageHeight) {
		this.largeProductImageHeight = largeProductImageHeight;
	}

	/**
	 * 获取商品图片(中)宽度
	 *
	 * @return 商品图片(中)宽度
	 */
	@NotNull
	@Min(1)
	public Integer getMediumProductImageWidth() {
		return mediumProductImageWidth;
	}

	/**
	 * 设置商品图片(中)宽度
	 *
	 * @param mediumProductImageWidth
	 *            商品图片(中)宽度
	 */
	public void setMediumProductImageWidth(Integer mediumProductImageWidth) {
		this.mediumProductImageWidth = mediumProductImageWidth;
	}

	/**
	 * 获取商品图片(中)高度
	 *
	 * @return 商品图片(中)高度
	 */
	@NotNull
	@Min(1)
	public Integer getMediumProductImageHeight() {
		return mediumProductImageHeight;
	}

	/**
	 * 设置商品图片(中)高度
	 *
	 * @param mediumProductImageHeight
	 *            商品图片(中)高度
	 */
	public void setMediumProductImageHeight(Integer mediumProductImageHeight) {
		this.mediumProductImageHeight = mediumProductImageHeight;
	}

	/**
	 * 获取商品缩略图宽度
	 *
	 * @return 商品缩略图宽度
	 */
	@NotNull
	@Min(1)
	public Integer getThumbnailProductImageWidth() {
		return thumbnailProductImageWidth;
	}

	/**
	 * 设置商品缩略图宽度
	 *
	 * @param thumbnailProductImageWidth
	 *            商品缩略图宽度
	 */
	public void setThumbnailProductImageWidth(Integer thumbnailProductImageWidth) {
		this.thumbnailProductImageWidth = thumbnailProductImageWidth;
	}

	/**
	 * 获取商品缩略图高度
	 *
	 * @return 商品缩略图高度
	 */
	@NotNull
	@Min(1)
	public Integer getThumbnailProductImageHeight() {
		return thumbnailProductImageHeight;
	}

	/**
	 * 设置商品缩略图高度
	 *
	 * @param thumbnailProductImageHeight
	 *            商品缩略图高度
	 */
	public void setThumbnailProductImageHeight(Integer thumbnailProductImageHeight) {
		this.thumbnailProductImageHeight = thumbnailProductImageHeight;
	}

	/**
	 * 获取默认商品图片(大)
	 *
	 * @return 默认商品图片(大)
	 */
	@NotEmpty
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	public String getDefaultLargeProductImage() {
		return defaultLargeProductImage;
	}

	/**
	 * 设置默认商品图片(大)
	 *
	 * @param defaultLargeProductImage
	 *            默认商品图片(大)
	 */
	public void setDefaultLargeProductImage(String defaultLargeProductImage) {
		this.defaultLargeProductImage = defaultLargeProductImage;
	}

	/**
	 * 获取默认商品图片(小)
	 *
	 * @return 默认商品图片(小)
	 */
	@NotEmpty
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	public String getDefaultMediumProductImage() {
		return defaultMediumProductImage;
	}

	/**
	 * 设置默认商品图片(小)
	 *
	 * @param defaultMediumProductImage
	 *            默认商品图片(小)
	 */
	public void setDefaultMediumProductImage(String defaultMediumProductImage) {
		this.defaultMediumProductImage = defaultMediumProductImage;
	}

	/**
	 * 获取默认缩略图
	 *
	 * @return 默认缩略图
	 */
	@NotEmpty
	@Length(max = 200)
	@Pattern(regexp = "^(?i)(http:\\/\\/|https:\\/\\/|\\/).*$")
	public String getDefaultThumbnailProductImage() {
		return defaultThumbnailProductImage;
	}

	/**
	 * 设置默认缩略图
	 *
	 * @param defaultThumbnailProductImage
	 *            默认缩略图
	 */
	public void setDefaultThumbnailProductImage(String defaultThumbnailProductImage) {
		this.defaultThumbnailProductImage = defaultThumbnailProductImage;
	}

	/**
	 * 获取水印透明度
	 *
	 * @return 水印透明度
	 */
	@NotNull
	@Min(0)
	@Max(100)
	public Integer getWatermarkAlpha() {
		return watermarkAlpha;
	}

	/**
	 * 设置水印透明度
	 *
	 * @param watermarkAlpha
	 *            水印透明度
	 */
	public void setWatermarkAlpha(Integer watermarkAlpha) {
		this.watermarkAlpha = watermarkAlpha;
	}

	/**
	 * 获取水印图片
	 *
	 * @return 水印图片
	 */
	public String getWatermarkImage() {
		return watermarkImage;
	}

	/**
	 * 设置水印图片
	 *
	 * @param watermarkImage
	 *            水印图片
	 */
	public void setWatermarkImage(String watermarkImage) {
		this.watermarkImage = watermarkImage;
	}

	/**
	 * 获取水印位置
	 *
	 * @return 水印位置
	 */
	@NotNull
	public WatermarkPosition getWatermarkPosition() {
		return watermarkPosition;
	}

	/**
	 * 设置水印位置
	 *
	 * @param watermarkPosition
	 *            水印位置
	 */
	public void setWatermarkPosition(WatermarkPosition watermarkPosition) {
		this.watermarkPosition = watermarkPosition;
	}

	/**
	 * 获取价格精确位数
	 *
	 * @return 价格精确位数
	 */
	@NotNull
	@Min(0)
	@Max(3)
	public Integer getPriceScale() {
		return priceScale;
	}

	/**
	 * 设置价格精确位数
	 *
	 * @param priceScale
	 *            价格精确位数
	 */
	public void setPriceScale(Integer priceScale) {
		this.priceScale = priceScale;
	}

	/**
	 * 获取价格精确方式
	 *
	 * @return 价格精确方式
	 */
	@NotNull
	public RoundType getPriceRoundType() {
		return priceRoundType;
	}

	/**
	 * 设置价格精确方式
	 *
	 * @param priceRoundType
	 *            价格精确方式
	 */
	public void setPriceRoundType(RoundType priceRoundType) {
		this.priceRoundType = priceRoundType;
	}

	/**
	 * 获取是否前台显示市场价
	 *
	 * @return 是否前台显示市场价
	 */
	@NotNull
	public Boolean getIsShowMarketPrice() {
		return isShowMarketPrice;
	}

	/**
	 * 设置是否前台显示市场价
	 *
	 * @param isShowMarketPrice
	 *            是否前台显示市场价
	 */
	public void setIsShowMarketPrice(Boolean isShowMarketPrice) {
		this.isShowMarketPrice = isShowMarketPrice;
	}

	/**
	 * 获取默认市场价换算比例
	 *
	 * @return 默认市场价换算比例
	 */
	@NotNull
	@Min(0)
	@Digits(integer = 3, fraction = 3)
	public Double getDefaultMarketPriceScale() {
		return defaultMarketPriceScale;
	}

	/**
	 * 设置默认市场价换算比例
	 *
	 * @param defaultMarketPriceScale
	 *            默认市场价换算比例
	 */
	public void setDefaultMarketPriceScale(Double defaultMarketPriceScale) {
		this.defaultMarketPriceScale = defaultMarketPriceScale;
	}

	/**
	 * 获取是否开放注册
	 *
	 * @return 是否开放注册
	 */
	@NotNull
	public Boolean getIsRegisterEnabled() {
		return isRegisterEnabled;
	}

	/**
	 * 设置是否开放注册
	 *
	 * @param isRegisterEnabled
	 *            是否开放注册
	 */
	public void setIsRegisterEnabled(Boolean isRegisterEnabled) {
		this.isRegisterEnabled = isRegisterEnabled;
	}

	/**
	 * 获取是否允许E-mail重复注册
	 *
	 * @return 是否允许E-mail重复注册
	 */
	@NotNull
	public Boolean getIsDuplicateEmail() {
		return isDuplicateEmail;
	}

	/**
	 * 设置是否允许E-mail重复注册
	 *
	 * @param isDuplicateEmail
	 *            是否允许E-mail重复注册
	 */
	public void setIsDuplicateEmail(Boolean isDuplicateEmail) {
		this.isDuplicateEmail = isDuplicateEmail;
	}

	/**
	 * 获取禁用用户名
	 *
	 * @return 禁用用户名
	 */
	@Length(max = 200)
	public String getDisabledUsername() {
		return disabledUsername;
	}

	/**
	 * 设置禁用用户名
	 *
	 * @param disabledUsername
	 *            禁用用户名
	 */
	public void setDisabledUsername(String disabledUsername) {
		if (disabledUsername != null) {
			disabledUsername = disabledUsername.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "");
		}
		this.disabledUsername = disabledUsername;
	}

	/**
	 * 获取用户名最小长度
	 *
	 * @return 用户名最小长度
	 */
	@NotNull
	@Min(1)
	@Max(117)
	public Integer getUsernameMinLength() {
		return usernameMinLength;
	}

	/**
	 * 设置用户名最小长度
	 *
	 * @param usernameMinLength
	 *            用户名最小长度
	 */
	public void setUsernameMinLength(Integer usernameMinLength) {
		this.usernameMinLength = usernameMinLength;
	}

	/**
	 * 获取用户名最大长度
	 *
	 * @return 用户名最大长度
	 */
	@NotNull
	@Min(1)
	@Max(117)
	public Integer getUsernameMaxLength() {
		return usernameMaxLength;
	}

	/**
	 * 设置用户名最大长度
	 *
	 * @param usernameMaxLength
	 *            用户名最大长度
	 */
	public void setUsernameMaxLength(Integer usernameMaxLength) {
		this.usernameMaxLength = usernameMaxLength;
	}

	/**
	 * 获取密码最小长度
	 *
	 * @return 密码最小长度
	 */
	@NotNull
	@Min(1)
	@Max(117)
	public Integer getPasswordMinLength() {
		return passwordMinLength;
	}

	/**
	 * 设置密码最小长度
	 *
	 * @param passwordMinLength
	 *            密码最小长度
	 */
	public void setPasswordMinLength(Integer passwordMinLength) {
		this.passwordMinLength = passwordMinLength;
	}

	/**
	 * 获取密码最大长度
	 *
	 * @return 密码最大长度
	 */
	@NotNull
	@Min(1)
	@Max(117)
	public Integer getPasswordMaxLength() {
		return passwordMaxLength;
	}

	/**
	 * 设置密码最大长度
	 *
	 * @param passwordMaxLength
	 *            密码最大长度
	 */
	public void setPasswordMaxLength(Integer passwordMaxLength) {
		this.passwordMaxLength = passwordMaxLength;
	}

	/**
	 * 获取注册初始积分
	 *
	 * @return 注册初始积分
	 */
	@NotNull
	@Min(0)
	public Long getRegisterPoint() {
		return registerPoint;
	}

	/**
	 * 设置注册初始积分
	 *
	 * @param registerPoint
	 *            注册初始积分
	 */
	public void setRegisterPoint(Long registerPoint) {
		this.registerPoint = registerPoint;
	}

	/**
	 * 获取注册协议
	 *
	 * @return 注册协议
	 */
	@NotEmpty
	public String getRegisterAgreement() {
		return registerAgreement;
	}

	/**
	 * 设置注册协议
	 *
	 * @param registerAgreement
	 *            注册协议
	 */
	public void setRegisterAgreement(String registerAgreement) {
		this.registerAgreement = registerAgreement;
	}

	/**
	 * 获取是否允许E-mail登录
	 *
	 * @return 是否允许E-mail登录
	 */
	@NotNull
	public Boolean getIsEmailLogin() {
		return isEmailLogin;
	}

	/**
	 * 设置是否允许E-mail登录
	 *
	 * @param isEmailLogin
	 *            是否允许E-mail登录
	 */
	public void setIsEmailLogin(Boolean isEmailLogin) {
		this.isEmailLogin = isEmailLogin;
	}

	/**
	 * 获取验证码类型
	 *
	 * @return 验证码类型
	 */
	public CaptchaType[] getCaptchaTypes() {
		return captchaTypes;
	}

	/**
	 * 设置验证码类型
	 *
	 * @param captchaTypes
	 *            验证码类型
	 */
	public void setCaptchaTypes(CaptchaType[] captchaTypes) {
		this.captchaTypes = captchaTypes;
	}

	/**
	 * 获取账号锁定类型
	 *
	 * @return 账号锁定类型
	 */
	public AccountLockType[] getAccountLockTypes() {
		return accountLockTypes;
	}

	/**
	 * 设置账号锁定类型
	 *
	 * @param accountLockTypes
	 *            账号锁定类型
	 */
	public void setAccountLockTypes(AccountLockType[] accountLockTypes) {
		this.accountLockTypes = accountLockTypes;
	}

	/**
	 * 获取连续登录失败最大次数
	 *
	 * @return 连续登录失败最大次数
	 */
	@NotNull
	@Min(1)
	public Integer getAccountLockCount() {
		return accountLockCount;
	}

	/**
	 * 设置连续登录失败最大次数
	 *
	 * @param accountLockCount
	 *            连续登录失败最大次数
	 */
	public void setAccountLockCount(Integer accountLockCount) {
		this.accountLockCount = accountLockCount;
	}

	/**
	 * 获取自动解锁时间
	 *
	 * @return 自动解锁时间
	 */
	@NotNull
	@Min(0)
	public Integer getAccountLockTime() {
		return accountLockTime;
	}

	/**
	 * 设置自动解锁时间
	 *
	 * @param accountLockTime
	 *            自动解锁时间
	 */
	public void setAccountLockTime(Integer accountLockTime) {
		this.accountLockTime = accountLockTime;
	}

	/**
	 * 获取安全密匙有效时间
	 *
	 * @return 安全密匙有效时间
	 */
	@NotNull
	@Min(0)
	public Integer getSafeKeyExpiryTime() {
		return safeKeyExpiryTime;
	}

	/**
	 * 设置安全密匙有效时间
	 *
	 * @param safeKeyExpiryTime
	 *            安全密匙有效时间
	 */
	public void setSafeKeyExpiryTime(Integer safeKeyExpiryTime) {
		this.safeKeyExpiryTime = safeKeyExpiryTime;
	}

	/**
	 * 获取上传文件最大限制
	 *
	 * @return 上传文件最大限制
	 */
	@NotNull
	@Min(0)
	public Integer getUploadMaxSize() {
		return uploadMaxSize;
	}

	/**
	 * 设置上传文件最大限制
	 *
	 * @param uploadMaxSize
	 *            上传文件最大限制
	 */
	public void setUploadMaxSize(Integer uploadMaxSize) {
		this.uploadMaxSize = uploadMaxSize;
	}

	/**
	 * 获取允许上传图片扩展名
	 *
	 * @return 允许上传图片扩展名
	 */
	@Length(max = 200)
	public String getUploadImageExtension() {
		return uploadImageExtension;
	}

	/**
	 * 设置允许上传图片扩展名
	 *
	 * @param uploadImageExtension
	 *            允许上传图片扩展名
	 */
	public void setUploadImageExtension(String uploadImageExtension) {
		if (uploadImageExtension != null) {
			uploadImageExtension = uploadImageExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
		}
		this.uploadImageExtension = uploadImageExtension;
	}

	/**
	 * 获取允许上传媒体扩展名
	 *
	 * @return 允许上传媒体扩展名
	 */
	@Length(max = 200)
	public String getUploadMediaExtension() {
		return uploadMediaExtension;
	}

	/**
	 * 设置允许上传媒体扩展名
	 *
	 * @param uploadMediaExtension
	 *            允许上传媒体扩展名
	 */
	public void setUploadMediaExtension(String uploadMediaExtension) {
		if (uploadMediaExtension != null) {
			uploadMediaExtension = uploadMediaExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
		}
		this.uploadMediaExtension = uploadMediaExtension;
	}

	/**
	 * 获取允许上传文件扩展名
	 *
	 * @return 允许上传文件扩展名
	 */
	@Length(max = 200)
	public String getUploadFileExtension() {
		return uploadFileExtension;
	}

	/**
	 * 设置允许上传文件扩展名
	 *
	 * @param uploadFileExtension
	 *            允许上传文件扩展名
	 */
	public void setUploadFileExtension(String uploadFileExtension) {
		if (uploadFileExtension != null) {
			uploadFileExtension = uploadFileExtension.replaceAll("[,\\s]*,[,\\s]*", ",").replaceAll("^,|,$", "").toLowerCase();
		}
		this.uploadFileExtension = uploadFileExtension;
	}

	/**
	 * 获取图片上传路径
	 *
	 * @return 图片上传路径
	 */
	@NotEmpty
	@Length(max = 200)
	public String getImageUploadPath() {
		return imageUploadPath;
	}

	/**
	 * 设置图片上传路径
	 *
	 * @param imageUploadPath
	 *            图片上传路径
	 */
	public void setImageUploadPath(String imageUploadPath) {
		if (imageUploadPath != null) {
			if (!imageUploadPath.startsWith("/")) {
				imageUploadPath = "/" + imageUploadPath;
			}
			if (!imageUploadPath.endsWith("/")) {
				imageUploadPath += "/";
			}
		}
		this.imageUploadPath = imageUploadPath;
	}

	/**
	 * 获取生成商品二维码的图片路径  .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-11-23 下午2:21:23
	 * @return
	 */
	@NotEmpty
	@Length(max = 200)
	public String getQrEcodeimageUploadPath() {
		return qrEcodeimageUploadPath;
	}

	/**
	 *
	 * 设置生成商品二维码的图片路径.
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-11-23 下午2:21:51
	 * @param qrEcodeimageUploadPath
	 */
	public void setQrEcodeimageUploadPath(String qrEcodeimageUploadPath) {
		if (qrEcodeimageUploadPath != null) {
			if (!qrEcodeimageUploadPath.startsWith("/")) {
				qrEcodeimageUploadPath = "/" + qrEcodeimageUploadPath;
			}
			if (!qrEcodeimageUploadPath.endsWith("/")) {
				qrEcodeimageUploadPath += "/";
			}
		}

		this.qrEcodeimageUploadPath = qrEcodeimageUploadPath;
	}

	/**
	 *
	 * 条形码路径.
	 * author: 王凯斌
	 *   date: 2015-12-18 上午11:01:12
	 * @return
	 */
	@Length(max = 200)
	public String getBarCodeimageUploadPath() {
		return barCodeimageUploadPath;
	}

	public void setBarCodeimageUploadPath(String barCodeimageUploadPath) {

		if (barCodeimageUploadPath != null) {
			if (!barCodeimageUploadPath.startsWith("/")) {
				barCodeimageUploadPath = "/" + barCodeimageUploadPath;
			}
			if (!barCodeimageUploadPath.endsWith("/")) {
				barCodeimageUploadPath += "/";
			}
		}
		this.barCodeimageUploadPath = barCodeimageUploadPath;
	}

	/**
	 * 获取媒体上传路径
	 *
	 * @return 媒体上传路径
	 */
	@NotEmpty
	@Length(max = 200)
	public String getMediaUploadPath() {
		return mediaUploadPath;
	}

	/**
	 * 设置媒体上传路径
	 *
	 * @param mediaUploadPath
	 *            媒体上传路径
	 */
	public void setMediaUploadPath(String mediaUploadPath) {
		if (mediaUploadPath != null) {
			if (!mediaUploadPath.startsWith("/")) {
				mediaUploadPath = "/" + mediaUploadPath;
			}
			if (!mediaUploadPath.endsWith("/")) {
				mediaUploadPath += "/";
			}
		}
		this.mediaUploadPath = mediaUploadPath;
	}

	/**
	 * 获取文件上传路径
	 *
	 * @return 文件上传路径
	 */
	@NotEmpty
	@Length(max = 200)
	public String getFileUploadPath() {
		return fileUploadPath;
	}

	/**
	 * 设置文件上传路径
	 *
	 * @param fileUploadPath
	 *            文件上传路径
	 */
	public void setFileUploadPath(String fileUploadPath) {
		if (fileUploadPath != null) {
			if (!fileUploadPath.startsWith("/")) {
				fileUploadPath = "/" + fileUploadPath;
			}
			if (!fileUploadPath.endsWith("/")) {
				fileUploadPath += "/";
			}
		}
		this.fileUploadPath = fileUploadPath;
	}

	/**
	 * 获取SMTP服务器地址
	 *
	 * @return SMTP服务器地址
	 */
	@NotEmpty
	@Length(max = 200)
	public String getSmtpHost() {
		return smtpHost;
	}

	/**
	 * 设置SMTP服务器地址
	 *
	 * @param smtpHost
	 *            SMTP服务器地址
	 */
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	/**
	 * 获取SMTP服务器端口
	 *
	 * @return SMTP服务器端口
	 */
	@NotNull
	@Min(0)
	public Integer getSmtpPort() {
		return smtpPort;
	}

	/**
	 * 设置SMTP服务器端口
	 *
	 * @param smtpPort
	 *            SMTP服务器端口
	 */
	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	/**
	 * 获取SMTP用户名
	 *
	 * @return SMTP用户名
	 */
	@NotEmpty
	@Length(max = 200)
	public String getSmtpUsername() {
		return smtpUsername;
	}

	/**
	 * 设置SMTP用户名
	 *
	 * @param smtpUsername
	 *            SMTP用户名
	 */
	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	/**
	 * 获取SMTP密码
	 *
	 * @return SMTP密码
	 */
	@Length(max = 200)
	public String getSmtpPassword() {
		return smtpPassword;
	}

	/**
	 * 设置SMTP密码
	 *
	 * @param smtpPassword
	 *            SMTP密码
	 */
	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	/**
	 * 获取SMTP是否启用SSL
	 *
	 * @return SMTP是否启用SSL
	 */
	@NotNull
	public Boolean getSmtpSSLEnabled() {
		return smtpSSLEnabled;
	}

	/**
	 * 设置SMTP是否启用SSL
	 *
	 * @param smtpSSLEnabled
	 *            SMTP是否启用SSL
	 */
	public void setSmtpSSLEnabled(Boolean smtpSSLEnabled) {
		this.smtpSSLEnabled = smtpSSLEnabled;
	}

	/**
	 * 获取发件人邮箱
	 *
	 * @return 发件人邮箱
	 */
	@NotEmpty
	@Email
	@Length(max = 200)
	public String getSmtpFromMail() {
		return smtpFromMail;
	}

	/**
	 * 设置发件人邮箱
	 *
	 * @param smtpFromMail
	 *            发件人邮箱
	 */
	public void setSmtpFromMail(String smtpFromMail) {
		this.smtpFromMail = smtpFromMail;
	}

	/**
	 * 获取货币符号
	 *
	 * @return 货币符号
	 */
	@NotEmpty
	@Length(max = 200)
	public String getCurrencySign() {
		return currencySign;
	}

	/**
	 * 设置货币符号
	 *
	 * @param currencySign
	 *            货币符号
	 */
	public void setCurrencySign(String currencySign) {
		this.currencySign = currencySign;
	}

	/**
	 * 获取货币单位
	 *
	 * @return 货币单位
	 */
	@NotEmpty
	@Length(max = 200)
	public String getCurrencyUnit() {
		return currencyUnit;
	}

	/**
	 * 设置货币单位
	 *
	 * @param currencyUnit
	 *            货币单位
	 */
	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	/**
	 * 获取库存警告数
	 *
	 * @return 库存警告数
	 */
	@NotNull
	@Min(0)
	public Integer getStockAlertCount() {
		return stockAlertCount;
	}

	/**
	 * 设置库存警告数
	 *
	 * @param stockAlertCount
	 *            库存警告数
	 */
	public void setStockAlertCount(Integer stockAlertCount) {
		this.stockAlertCount = stockAlertCount;
	}

	/**
	 * 获取库存分配时间点
	 *
	 * @return 库存分配时间点
	 */
	@NotNull
	public StockAllocationTime getStockAllocationTime() {
		return stockAllocationTime;
	}

	/**
	 * 设置库存分配时间点
	 *
	 * @param stockAllocationTime
	 *            库存分配时间点
	 */
	public void setStockAllocationTime(StockAllocationTime stockAllocationTime) {
		this.stockAllocationTime = stockAllocationTime;
	}

	/**
	 * 获取默认积分换算比例
	 *
	 * @return 默认积分换算比例
	 */
	@NotNull
	@Min(0)
	@Digits(integer = 3, fraction = 3)
	public Double getDefaultPointScale() {
		return defaultPointScale;
	}

	/**
	 * 设置默认积分换算比例
	 *
	 * @param defaultPointScale
	 *            默认积分换算比例
	 */
	public void setDefaultPointScale(Double defaultPointScale) {
		this.defaultPointScale = defaultPointScale;
	}

	/**
	 * 获取是否开启开发模式
	 *
	 * @return 是否开启开发模式
	 */
	@NotNull
	public Boolean getIsDevelopmentEnabled() {
		return isDevelopmentEnabled;
	}

	/**
	 * 设置是否开启开发模式
	 *
	 * @param isDevelopmentEnabled
	 *            是否开启开发模式
	 */
	public void setIsDevelopmentEnabled(Boolean isDevelopmentEnabled) {
		this.isDevelopmentEnabled = isDevelopmentEnabled;
	}

	/**
	 * 获取是否开启评论
	 *
	 * @return 是否开启评论
	 */
	@NotNull
	public Boolean getIsReviewEnabled() {
		return isReviewEnabled;
	}

	/**
	 * 设置是否开启评论
	 *
	 * @param isReviewEnabled
	 *            是否开启评论
	 */
	public void setIsReviewEnabled(Boolean isReviewEnabled) {
		this.isReviewEnabled = isReviewEnabled;
	}

	/**
	 * 获取是否审核评论
	 *
	 * @return 是否审核评论
	 */
	@NotNull
	public Boolean getIsReviewCheck() {
		return isReviewCheck;
	}

	/**
	 * 设置是否审核评论
	 *
	 * @param isReviewCheck
	 *            是否审核评论
	 */
	public void setIsReviewCheck(Boolean isReviewCheck) {
		this.isReviewCheck = isReviewCheck;
	}

	/**
	 * 获取评论权限
	 *
	 * @return 评论权限
	 */
	@NotNull
	public ReviewAuthority getReviewAuthority() {
		return reviewAuthority;
	}

	/**
	 * 设置评论权限
	 *
	 * @param reviewAuthority
	 *            评论权限
	 */
	public void setReviewAuthority(ReviewAuthority reviewAuthority) {
		this.reviewAuthority = reviewAuthority;
	}

	/**
	 * 获取是否开启咨询
	 *
	 * @return 是否开启咨询
	 */
	@NotNull
	public Boolean getIsConsultationEnabled() {
		return isConsultationEnabled;
	}

	/**
	 * 设置是否开启咨询
	 *
	 * @param isConsultationEnabled
	 *            是否开启咨询
	 */
	public void setIsConsultationEnabled(Boolean isConsultationEnabled) {
		this.isConsultationEnabled = isConsultationEnabled;
	}

	/**
	 * 获取是否审核咨询
	 *
	 * @return 是否审核咨询
	 */
	@NotNull
	public Boolean getIsConsultationCheck() {
		return isConsultationCheck;
	}

	/**
	 * 设置是否审核咨询
	 *
	 * @param isConsultationCheck
	 *            是否审核咨询
	 */
	public void setIsConsultationCheck(Boolean isConsultationCheck) {
		this.isConsultationCheck = isConsultationCheck;
	}

	/**
	 * 获取咨询权限
	 *
	 * @return 咨询权限
	 */
	@NotNull
	public ConsultationAuthority getConsultationAuthority() {
		return consultationAuthority;
	}

	/**
	 * 设置咨询权限
	 *
	 * @param consultationAuthority
	 *            咨询权限
	 */
	public void setConsultationAuthority(ConsultationAuthority consultationAuthority) {
		this.consultationAuthority = consultationAuthority;
	}

	/**
	 * 获取是否开启发票功能
	 *
	 * @return 是否开启发票功能
	 */
	@NotNull
	public Boolean getIsInvoiceEnabled() {
		return isInvoiceEnabled;
	}

	/**
	 * 设置是否开启发票功能
	 *
	 * @param isInvoiceEnabled
	 *            是否开启发票功能
	 */
	public void setIsInvoiceEnabled(Boolean isInvoiceEnabled) {
		this.isInvoiceEnabled = isInvoiceEnabled;
	}

	/**
	 * 获取是否开启含税价
	 *
	 * @return 是否开启含税价
	 */
	@NotNull
	public Boolean getIsTaxPriceEnabled() {
		return isTaxPriceEnabled;
	}

	/**
	 * 设置是否开启含税价
	 *
	 * @param isTaxPriceEnabled
	 *            是否开启含税价
	 */
	public void setIsTaxPriceEnabled(Boolean isTaxPriceEnabled) {
		this.isTaxPriceEnabled = isTaxPriceEnabled;
	}

	/**
	 * 获取税率
	 *
	 * @return 税率
	 */
	@NotNull
	@Min(0)
	@Digits(integer = 3, fraction = 3)
	public Double getTaxRate() {
		return taxRate;
	}

	/**
	 * 设置税率
	 *
	 * @param taxRate
	 *            税率
	 */
	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	/**
	 * 获取Cookie路径
	 *
	 * @return Cookie路径
	 */
	@NotEmpty
	@Length(max = 200)
	public String getCookiePath() {
		return cookiePath;
	}

	/**
	 * 设置Cookie路径
	 *
	 * @param cookiePath
	 *            Cookie路径
	 */
	public void setCookiePath(String cookiePath) {
		if (cookiePath != null && !cookiePath.endsWith("/")) {
			cookiePath += "/";
		}
		this.cookiePath = cookiePath;
	}

	/**
	 * 获取Cookie作用域
	 *
	 * @return Cookie作用域
	 */
	@Length(max = 200)
	public String getCookieDomain() {
		return cookieDomain;
	}

	/**
	 * 设置Cookie作用域
	 *
	 * @param cookieDomain
	 *            Cookie作用域
	 */
	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	/**
	 * 获取快递100授权KEY
	 *
	 * @return 快递100授权KEY
	 */
	@Length(max = 200)
	public String getKuaidi100Key() {
		return kuaidi100Key;
	}

	/**
	 * 设置快递100授权KEY
	 *
	 * @param kuaidi100Key
	 *            快递100授权KEY
	 */
	public void setKuaidi100Key(String kuaidi100Key) {
		this.kuaidi100Key = kuaidi100Key;
	}

	/**
	 * 获取是否开启CNZZ统计
	 *
	 * @return 是否开启CNZZ统计
	 */
	@Null
	public Boolean getIsCnzzEnabled() {
		return isCnzzEnabled;
	}

	/**
	 * 设置是否开启CNZZ统计
	 *
	 * @param isCnzzEnabled
	 *            是否开启CNZZ统计
	 */
	public void setIsCnzzEnabled(Boolean isCnzzEnabled) {
		this.isCnzzEnabled = isCnzzEnabled;
	}

	/**
	 * 获取CNZZ统计站点ID
	 *
	 * @return CNZZ统计站点ID
	 */
	@Null
	public String getCnzzSiteId() {
		return cnzzSiteId;
	}

	/**
	 * 设置CNZZ统计站点ID
	 *
	 * @param cnzzSiteId
	 *            CNZZ统计站点ID
	 */
	public void setCnzzSiteId(String cnzzSiteId) {
		this.cnzzSiteId = cnzzSiteId;
	}

	/**
	 * 获取CNZZ统计密码
	 *
	 * @return CNZZ统计密码
	 */
	@Null
	public String getCnzzPassword() {
		return cnzzPassword;
	}

	/**
	 * 设置CNZZ统计密码
	 *
	 * @param cnzzPassword
	 *            CNZZ统计密码
	 */
	public void setCnzzPassword(String cnzzPassword) {
		this.cnzzPassword = cnzzPassword;
	}



	/**
	 * 获取短信主账号的ID
	 *
	 * @return 短信主账号的ID
	 */
	@Length(max = 200)
	public String getSmsSn() {
		return smsSn;
	}

	/**
	 * 设置短信主账号的ID
	 *
	 * @param smsSn
	 *            短信主账号的ID
	 */
	public void setSmsSn(String smsSn) {
		this.smsSn = smsSn;
	}

	/**
	 * 获取短信主账号的token
	 *
	 * @return 短信主账号的token
	 */
	@Length(max = 200)
	public String getSmsKey() {
		return smsKey;
	}

	/**
	 * 设置短信主账号的token
	 *
	 * @param smsKey
	 *            短信主账号的token
	 */
	public void setSmsKey(String smsKey) {
		this.smsKey = smsKey;
	}


	/**
	 * 获取区域设置
	 *
	 * @return 区域设置
	 */
	@NotNull
	public Locale getLocale() {
		return locale;
	}

	/**
	 * 设置区域设置
	 *
	 * @param locale
	 *            区域设置
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * 获取主题
	 * 
	 * @return 主题
	 */
	@Null
	public String getTheme() {
		return theme;
	}

	/**
	 * 设置主题
	 * 
	 * @param theme
	 *            主题
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * 获取热门搜索关键词
	 * 
	 * @return 热门搜索关键词
	 */
	public String[] getHotSearches() {
		return StringUtils.split(hotSearch, SEPARATOR);
	}

	/**
	 * 获取禁用用户名
	 * 
	 * @return 禁用用户名
	 */
	public String[] getDisabledUsernames() {
		return StringUtils.split(disabledUsername, SEPARATOR);
	}

	/**
	 * 获取允许上传图片扩展名
	 * 
	 * @return 允许上传图片扩展名
	 */
	public String[] getUploadImageExtensions() {
		return StringUtils.split(uploadImageExtension, SEPARATOR);
	}

	/**
	 * 获取允许上传媒体扩展名
	 * 
	 * @return 允许上传媒体扩展名
	 */
	public String[] getUploadMediaExtensions() {
		return StringUtils.split(uploadMediaExtension, SEPARATOR);
	}

	/**
	 * 获取允许上传文件扩展名
	 * 
	 * @return 允许上传文件扩展名
	 */
	public String[] getUploadFileExtensions() {
		return StringUtils.split(uploadFileExtension, SEPARATOR);
	}

	/**
	 * 设置精度
	 * 
	 * @param amount
	 *            数值
	 * @return 数值
	 */
	public BigDecimal setScale(BigDecimal amount) {
		if (amount != null && getPriceScale() != null && getPriceRoundType() != null) {
			switch (getPriceRoundType()) {
			case roundUp:
				return amount.setScale(getPriceScale(), BigDecimal.ROUND_UP);
			case roundDown:
				return amount.setScale(getPriceScale(), BigDecimal.ROUND_DOWN);
			case roundHalfUp:
				return amount.setScale(getPriceScale(), BigDecimal.ROUND_HALF_UP);
			}
		}
		return amount;
	}

	public String getCoolGoodsDefaultImg() {
		return coolGoodsDefaultImg;
	}

	public void setCoolGoodsDefaultImg(String coolGoodsDefaultImg) {
		this.coolGoodsDefaultImg = coolGoodsDefaultImg;
	}

	
	public String getMoneyRechargeGodMoney() {
		return moneyRechargeGodMoney;
	}

	public void setMoneyRechargeGodMoney(String moneyRechargeGodMoney) {
		this.moneyRechargeGodMoney = moneyRechargeGodMoney;
	}

	
	
	public String getGodMoneyRechargeMoney() {
		return godMoneyRechargeMoney;
	}

	public void setGodMoneyRechargeMoney(String godMoneyRechargeMoney) {
		this.godMoneyRechargeMoney = godMoneyRechargeMoney;
	}

	public String getCoolGoodsDefaultName() {
		return coolGoodsDefaultName;
	}

	public void setCoolGoodsDefaultName(String coolGoodsDefaultName) {
		this.coolGoodsDefaultName = coolGoodsDefaultName;
	}
	public String getGodMoneyExchangePoint() {
		return godMoneyExchangePoint;
	}

	public void setGodMoneyExchangePoint(String godMoneyExchangePoint) {
		this.godMoneyExchangePoint = godMoneyExchangePoint;
	}

	/**
	 * 获取短信服务器IP
	 * 
	 * @return 短信服务器IP
	 */
	@Length(max = 200)
	public String getSmsHost() {
		return smsHost;
	}
	/**
	 * 设置短信服务器IP
	 * 
	 * @param smsHost
	 *            短信服务器IP
	 */
	public void setSmsHost(String smsHost) {
		this.smsHost = smsHost;
	}
	/**
	 * 获取短信服务器端口
	 * 
	 * @return 短信服务器端口
	 */
	@Length(max = 200)
	public String getSmsPort() {
		return smsPort;
	}
	/**
	 * 设置短信服务器端口
	 * 
	 * @param smsPort
	 *            短信服务器端口
	 */
	public void setSmsPort(String smsPort) {
		this.smsPort = smsPort;
	}
	/**
	 * 获取短信初始化应用ID
	 * 
	 * @return 短信初始化应用ID
	 */
	@Length(max = 200)
	public String getSmsApplication() {
		return smsApplication;
	}
	/**
	 * 设置短信初始化应用ID
	 * 
	 * @param smsApplication
	 *            短信初始化应用ID
	 */
	public void setSmsApplication(String smsApplication) {
		this.smsApplication = smsApplication;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * 
	 * 获取线下体验 描述信息 html .
	 * author: 施长成
	 *   date: 2015-11-30 上午11:26:53
	 * @return
	 */
	@Length(max = 200)
	public String getLineExperience() {
		return lineExperience;
	}

	/**
	 * 
	 * 设置 线下体验 描述信息 html  .
	 * author: 施长成
	 *   date: 2015-11-30 上午11:27:21
	 * @param lineExperience
	 */
	public void setLineExperience(String lineExperience) {
		this.lineExperience = lineExperience;
	}

	/***
	 * 获取 推荐获利 比率 .
	 * <br>
	 * author: 施长成
	 *   date: 2015-12-1 下午6:26:05
	 * @return
	 */
	public String getRecommendationRate() {
		return recommendationRate;
	}

	/**
	 * 
	 * 设置 推荐获利 比率 .
	 * <p>方法详细说明,如果要换行请使用<br>标签</p>
	 * <br>
	 * author: 施长成
	 *   date: 2015-12-1 下午6:26:38
	 * @param recommendationRate
	 */
	public void setRecommendationRate(String recommendationRate) {
		this.recommendationRate = recommendationRate;
	}

	/**
	 * 
	 * 获得短信模板.
	 * author: 王凯斌
	 *   date: 2015-12-2 下午1:49:50
	 * @return
	 */
	public String getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	/**
	 * 
	 * 获得邮件模板.
	 * author: 王凯斌
	 *   date: 2015-12-2 下午1:50:09
	 * @return
	 */
	public String getMailTemplate() {
		return mailTemplate;
	}

	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
	}
	
	public Boolean getSmsIsEnable() {
		return smsIsEnable;
	}

	public void setSmsIsEnable(Boolean smsIsEnable) {
		this.smsIsEnable = smsIsEnable;
	}

	public Boolean getMailIsEnable() {
		return mailIsEnable;
	}

	public void setMailIsEnable(Boolean mailIsEnable) {
		this.mailIsEnable = mailIsEnable;
	}

	/**
	 * 
	 * 获得项目路径.
	 * author: 王凯斌
	 *   date: 2015-12-4 上午9:52:48
	 * @return
	 */
    @NotEmpty
    public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
	
	/**
	 * 
	 * 获取快递100推送路径.
	 * author: 严志森
	 *   date: 2016年1月7日 下午2:19:25
	 * @return 快递100推送路径
	 */
    @NotEmpty
	public String getKuaidi() {
		return kuaidi;
	}
	
	public void setKuaidi(String kuaidi) {
		this.kuaidi = kuaidi;
	}

	/**
	 * 红包拒绝原因
	 * @return
	 */
    @NotEmpty
	public String getPacketRefuseSeasonOne() {
		return packetRefuseSeasonOne;
	}

	public void setPacketRefuseSeasonOne(String packetRefuseSeasonOne) {
		this.packetRefuseSeasonOne = packetRefuseSeasonOne;
	}

    @NotEmpty
	public String getPacketRefuseSeasonTwo() {
		return packetRefuseSeasonTwo;
	}

	public void setPacketRefuseSeasonTwo(String packetRefuseSeasonTwo) {
		this.packetRefuseSeasonTwo = packetRefuseSeasonTwo;
	}

    @NotEmpty
	public String getPacketRefuseSeasonThree() {
		return packetRefuseSeasonThree;
	}

	public void setPacketRefuseSeasonThree(String packetRefuseSeasonThree) {
		this.packetRefuseSeasonThree = packetRefuseSeasonThree;
	}

    @NotEmpty
	public String getPacketRefuseSeasonFour() {
		return packetRefuseSeasonFour;
	}

	public void setPacketRefuseSeasonFour(String packetRefuseSeasonFour) {
		this.packetRefuseSeasonFour = packetRefuseSeasonFour;
	}

    @NotEmpty
    public String getFooterAbout() {
        return footerAbout;
    }

    public void setFooterAbout(String footerAbout) {
        this.footerAbout = footerAbout;
    }

    @NotEmpty
    public String getFooterContact() {
        return footerContact;
    }

    public void setFooterContact(String footerContact) {
        this.footerContact = footerContact;
    }

    @NotEmpty
    public String getFooterLawInfo() {
        return footerLawInfo;
    }

    public void setFooterLawInfo(String footerLawInfo) {
        this.footerLawInfo = footerLawInfo;
    }
}