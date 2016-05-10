package com.puyuntech.ycmall.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * 管理员 实体类. 
 * Created on 2015-8-24 下午6:06:26 
 * @author 施长成
 */
@Entity
@Table(name = "t_admin")
public class Admin extends OrderEntity<Long>{

	private static final long serialVersionUID = -6003470702148125699L;

	/** "登录令牌"Cookie名称 */
	public static final String LOGIN_TOKEN_COOKIE_NAME = "adminLoginToken";
	
	/** 红包*/
	private Set<BonusEntity> bonusEntity= new HashSet<BonusEntity>();

	/** Web端用户名 */
	private String webUsername;

	/** Web端密码 */
	private String webPassword;
	
	/** POS帐号 用户名 **/
	private String posUsername;
	
	/** POS 密码 **/
	private String posPassword;
	
	/** 店员介绍 **/
	private String description;
	
	/** 工号 唯一 **/
	private String jobNumber;
	
	/** 手机号 **/
	private String phone;

	/** E-mail */
	private String email;

	/** 姓名 */
	private String name;
	
	/** 组织 */
	private String organization;

	/** 部门 */
	private String department;
	
	/** 照片 **/
	private String image;
	
	/** 职务 */
	public enum office{
		/** 店长 **/
		manager,
		/** 店员 **/
		clerk
		
	}
	
	/** 身份证 */
	private String  cardCode;
	
	/** 职务 */
	private Admin.office office;
	
	/** 是否启用 */
	private Boolean isEnabled;

	/** 是否锁定 */
	private Boolean isLocked;

	/** 连续登录失败次数 */
	private Integer loginFailureCount;

	/** 锁定日期 */
	private Date lockedDate;

	/** 最后登录日期 */
	private Date loginDate;

	/** 最后登录IP */
	private String loginIp;

	/** 锁定KEY */
	private String lockKey;

	/** 角色 */
	private Set<Role> roles = new HashSet<Role>();
	
	/** 反馈 */
	private Set<Feedback> feedbacks = new HashSet<Feedback>();
	
	/** 积分赠送-审核人 **/
	private Set<PointReward> pointReward = new HashSet<PointReward>();
	
	/** 采购单-采购人 **/
	private Set<Purchase> purchases = new HashSet<Purchase>();
	
	/** 采购单-审批人 **/
	private Set<Purchase> approvals = new HashSet<Purchase>();
	
	/** 采购验收 **/
	private Set<PurchaseCheck> purchaseChecks = new HashSet<PurchaseCheck>();
	
	/** 订单处理人 **/
	private Set<Order> orders = new HashSet<Order>();
	
	/** 订单处理人 **/
	private Set<ReturnOrderLog> returnOrderLog = new HashSet<ReturnOrderLog>();
	
	/** 点赞数量 **/
	private Integer praiseCount;
	
	/**
	 * 
	 * 设置用户名.
	 * author: 施长成
	 *   date: 2015-8-26 上午10:07:34
	 * @return
	 */
	@Pattern(regexp = "^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$")
	@Length(min = 2, max = 20)
	@Column(name="f_web_username",unique = true)
	public String getWebUsername() {
		return webUsername;
	}

	public void setWebUsername(String webUsername) {
		this.webUsername = webUsername;
	}
	
	@Column(name="f_image",nullable=true)
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * 获取职务
	 * 
	 * @return 职务
	 */
	@Column(name="f_office",nullable = false)
	public Admin.office getOffice() {
		return office;
	}

	public void setOffice(Admin.office office) {
		this.office = office;
	}

	/**
	 * 
	 * 设置密码.
	 * author: 施长成
	 *   date: 2015-8-26 上午10:10:34
	 * @return
	 */
	@Length(min = 4, max = 20)
	@Column(name = "f_web_password")
	public String getWebPassword() {
		return webPassword;
	}

	public void setWebPassword(String webPassword) {
		this.webPassword = webPassword;
	}

	@Pattern(regexp = "^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$")
	@Length(min = 2, max = 20)
	@Column(name="f_pos_username",unique = true)
	public String getPosUsername() {
		return posUsername;
	}

	public void setPosUsername(String posUsername) {
		this.posUsername = posUsername;
	}

	@Length(min = 4, max = 20)
	@Column(name = "f_pos_password")
	public String getPosPassword() {
		return posPassword;
	}

	public void setPosPassword(String posPassword) {
		this.posPassword = posPassword;
	}

	/**
	 * 
	 * 店员信息介绍.
	 * author: 施长成
	 *   date: 2015-8-26 上午10:11:43
	 * @return
	 */
	@Column(name="f_description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * 员工工号.
	 * author: 施长成
	 *   date: 2015-8-26 上午10:12:02
	 * @return
	 */
	@Column(name="f_jobnumber",nullable=false,unique=true)
	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	@Column(name="f_phone",nullable=false,unique=true)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Email
	@Length(max = 200)
	@Column(name="f_email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="f_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="f_organization")
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	@Column(name="f_department")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@NotNull
	@Column(name="f_is_enabled",nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	@NotNull
	@Column(name="f_is_locked")
	public Boolean getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	/**
	 * 获取积分赠送
	 * 
	 * @return    积分赠送
	 */
	@OneToMany(mappedBy = "operator", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<PointReward> getPointReward() {
		return pointReward;
	}

	/**
	 * 设置积分赠送
	 * 
	 * @param pointReward
	 *            积分赠送
	 */
	public void setPointReward(Set<PointReward> pointReward) {
		this.pointReward = pointReward;
	}

	/**
	 * 获取红包
	 * 
	 * @return    红包
	 */
	@OneToMany(mappedBy = "checkUser", fetch = FetchType.LAZY)
	public Set<BonusEntity> getBonusEntity() {
		return bonusEntity;
	}

	/**
	 * 设置红包
	 * 
	 * @param bonusEntity
	 *            红包
	 */
	public void setBonusEntity(Set<BonusEntity> bonusEntity) {
		this.bonusEntity = bonusEntity;
	}

	@NotNull
	@Column(name="f_login_failure_count")
	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	@Column(name="f_locked_date")
	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	@Column(name="f_login_date")
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	@Column(name="f_login_ip")
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Column(name="f_lock_key")
	public String getLockKey() {
		return lockKey;
	}

	public void setLockKey(String lockKey) {
		this.lockKey = lockKey;
	}

	/**
	 * 获取角色
	 * 
	 * @return 角色
	 */
	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_admin_role", joinColumns = { @JoinColumn(name = "t_admin", referencedColumnName = "f_id") }, inverseJoinColumns = { @JoinColumn(name = "f_role", referencedColumnName = "f_id") })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	/**
	 * 获取反馈
	 * 
	 * @return 反馈
	 */
	@OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<Feedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(Set<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}
	
	
	@OneToMany(mappedBy = "purchaser", fetch = FetchType.LAZY)
	public Set<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(Set<Purchase> purchases) {
		this.purchases = purchases;
	}
	
	@OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
	public Set<Purchase> getApprovals() {
		return approvals;
	}
	public void setApprovals(Set<Purchase> approvals) {
		this.approvals = approvals;
	}
	
	/**
	 * 获取入库验收
	 * 
	 * @return 入库验收
	 */
	@OneToMany(mappedBy = "operator", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public Set<PurchaseCheck> getPurchaseChecks() {
		return purchaseChecks;
	}

	public void setPurchaseChecks(Set<PurchaseCheck> purchaseChecks) {
		this.purchaseChecks = purchaseChecks;
	}
	
	@OneToMany(fetch = FetchType.LAZY , mappedBy="admin")
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	@OneToMany(fetch = FetchType.LAZY , mappedBy="operator")
	public Set<ReturnOrderLog> getReturnOrderLog() {
		return returnOrderLog;
	}

	public void setReturnOrderLog(Set<ReturnOrderLog> returnOrderLog) {
		this.returnOrderLog = returnOrderLog;
	}

	/**
	 * 持久化前处理
	 */
	@PrePersist
	public void prePersist() {
		setWebUsername(StringUtils.lowerCase(getWebUsername()));
		setPosUsername(StringUtils.lowerCase(getPosUsername()));
		setEmail(StringUtils.lowerCase(getEmail()));
//		setLockKey(DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30)));
	}

	/**
	 * 更新前处理
	 */
	@PreUpdate
	public void preUpdate() {
		setEmail(StringUtils.lowerCase(getEmail()));
	}

	
	@Column(name="f_card_code",nullable=true)
	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	
	/**
	 * 获取点赞数量
	 * 
	 * @return 层级
	 */
	@Column(name="f_praise_count",nullable = false)
	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	
	
}
