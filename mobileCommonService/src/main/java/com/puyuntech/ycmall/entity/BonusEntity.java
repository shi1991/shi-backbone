package com.puyuntech.ycmall.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

/**
 * 
 * Entity - 红包. 
 * Created on 2015-8-26 下午5:45:54 
 * @author Liaozhen
 */
@Indexed
@Entity
@Table(name = "t_bonus")
public class BonusEntity extends OrderEntity<Long> {
	private static final long serialVersionUID = 3969567765294078774L;

	/**TODO  这个有问题 这儿的Type 是从0开始，而红包中讲的是从1 开始 类型 */
	public enum Type {
		/** 其他 */
		other,
		
		/** 神币  1*/
		godMoney,

		/** 积分 2 */
		point,
		
		/** 实物 3 */
		entity
	}
	
	/** 状态 */
    public enum State{
    	
    	/** 审核中 0 */
    	checking,
    	
    	/** 审核不通过 1 */
    	disallow,
    	
    	/** 等待发布  2*/
    	waiting,
    	
    	/** 抢购中 3*/
    	robing,
    	
    	/** 结束 4*/
    	ending
    }
    /**拆分类型*/
    public enum BonusKind{
    	/** 等份 实物类为等份  0*/
    	Equal,
    	
    	/** 随机  1*/
    	Random
    }
    
    /**拆分类型*/
    private  BonusEntity.BonusKind bonusKind;
	
    /** 红包记录*/
	private Set<BonusLog> bonusLog= new HashSet<BonusLog>();

	/** 申请人 */
   private Member member;
	
	/** 类型 */
	private BonusEntity.Type type;
	
	/** 申请时间 */
    private Date applyTime;
    
    /** 审核人 */
    private Admin checkUser;
    
    /** 组织机构 */
    private Organization org;
    
    /** 审核时间 */
    private Date checkTime;
    
      /** 备注 */
    private String memo;
    
    /** 状态 */
	private BonusEntity.State state;
	
	/** 内容介绍 */
	private String content;
	
	/** 标题 */
	private String title;
	
	/** 总数 */
	private Integer gross;
	
	/** 剩余 */
	private Integer residue;
	
	/** 红包总额度 */
	private BigDecimal credit;
	
	/** 图片 */
	private String image;
	
	 /** 发布时间 */
    private Date releaseTime;
    
    /**结束时间*/
    private Date endTime;
    
    /**物品名称*/
    private String packetGoods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_member")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	@Column(name = "f_credit", nullable = true,precision = 27, scale = 12)
	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit  = credit;
	}
	
	@Column(name = "f_bonus_type", nullable = false)
	public BonusEntity.Type getType() {
		return type;
	}

	public void setType(BonusEntity.Type type) {
		this.type = type;
	}

	@Column(name = "f_apply_time")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "f_check_user", referencedColumnName ="f_id")
	public Admin getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(Admin checkUser) {
		this.checkUser = checkUser;
	}
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "f_organization", referencedColumnName ="f_id")
	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	@Column(name = "f_check_time")
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	@Column(name = "f_memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "f_state", nullable = false)
	public BonusEntity.State getState() {
		return state;
	}

	public void setState(BonusEntity.State state) {
		this.state = state;
	}

	@Column(name = "f_content", nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "f_title", nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "f_gross", nullable = false)
	public Integer getGross() {
		return gross;
	}

	public void setGross(Integer gross) {
		this.gross = gross;
	}

	@Column(name = "f_residue", nullable = false)
	public Integer getResidue() {
		return residue;
	}

	public void setResidue(Integer residue) {
		this.residue = residue;
	}

	@Column(name = "f_image")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Column(name = "f_Bonus_kind", nullable = false)
	public BonusEntity.BonusKind getBonusKind() {
		return bonusKind;
	}

	public void setBonusKind(BonusEntity.BonusKind bonusKind) {
		this.bonusKind = bonusKind;
	}

	
	@Column(name = "f_release_time")
	public Date getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}

	
	@Column(name = "f_end_time")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	

	@Column(name = "f_packet_goods")
	public String getPacketGoods() {
		return packetGoods;
	}

	public void setPacketGoods(String packetGoods) {
		this.packetGoods = packetGoods;
	}
	/**
	 * 获取红包记录
	 *
	 * @return    红包记录
	 */
	@OneToMany(mappedBy = "bonus", fetch = FetchType.LAZY)
	public Set<BonusLog> getBonusLog() {
		return bonusLog;
	}

	/**
	 * 设置红包记录
	 * 
	 * @param bonusLog
	 *            红包记录
	 */
	public void setBonusLog(Set<BonusLog> bonusLog) {
		this.bonusLog = bonusLog;
	}


	
}
