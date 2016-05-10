package com.puyuntech.ycmall.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeConverter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.puyuntech.ycmall.BaseAttributeConverter;

/**
 *
 * 组织机构信息 实体 . 
 * Created on 2015-8-25 下午5:46:18 
 * @author 施长成
 */
@Entity
@Table(name="t_organization")
public class Organization extends OrderEntity<Long> {

    private static final long serialVersionUID = -5956709918329061215L;
    /**
     *
     * 组织类型.
     * Created on 2015-8-25 下午5:51:50
     * @author 施长成
     */
    public enum Type{
    	//维修点包含:
    	//自提点包含:
    	//门店包含    :
    	
        //体验门店
        store,

        //维修网点
        repairShop,

        //仓库
        warehouse,

        //总部
        headquarters,

    }

    private String name;

    private String longitude;

    private String latitude;

    private String address;

    /** 简介*/
    private String intro;

    private String tel;

    private String opening;

    private String way;

    private String image;

    private Area area;

    /** 组织类型(多对多) **/
    private List<Organization.Type> types;

    /** 营业时间（周一到周日） **/
    private List<String> openingHours;

    /** 活动创建人 **/
    private Admin admin;

    /** 库存记录 */
    private Set<StockLog> stockLogs = new HashSet<StockLog>();

    /** 预采购 **/
    private Set<PurchaseRequisition> purchaseRequisitions = new HashSet<PurchaseRequisition>();

    /** 采购单 **/
    private Set<Purchase> purchases = new HashSet<Purchase>();

    /** 积分赠送-门店**/
    private Set<PointReward> pointReward = new HashSet<PointReward>();

    /** 红包*/
    private Set<BonusEntity> bonusEntity= new HashSet<BonusEntity>();

    /** 订单 **/
    private Set<Order> orders = new HashSet<Order>();

    /** 活动 */
    private Set<Activity> activities = new HashSet<Activity>();

    /** 退订单 **/
    private Set<ReturnOrder> returnOrders;

    /** 公告 **/
    private Set<Inform> informs;

    /** 点赞数量 **/
    private Integer praiseCount;

    @Column(name="f_intro" , nullable=true)
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Column(name="f_name" , nullable=false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="f_longitude" , nullable=false)
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Column(name="f_latitude",nullable=false)
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Column(name="f_address" , nullable=false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name="f_tel" , nullable=false)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    @Column(name = "f_opening")
    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    @Column(name="f_way")
    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    @Column(name="f_image")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="f_area" )
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    /**
     * 获取库存记录
     *
     * @return 库存记录
     */
    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<StockLog> getStockLogs() {
        return stockLogs;
    }

    /**
     * 设置库存记录
     *
     * @param stockLogs
     *            库存记录
     */
    public void setStockLogs(Set<StockLog> stockLogs) {
        this.stockLogs = stockLogs;
    }


    @OneToMany(mappedBy = "organization" ,fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public Set<PurchaseRequisition> getPurchaseRequisitions() {
        return purchaseRequisitions;
    }

    public void setPurchaseRequisitions(
            Set<PurchaseRequisition> purchaseRequisitions) {
        this.purchaseRequisitions = purchaseRequisitions;
    }

    /**
     * 采购单
     * author: 施长成
     *   date: 2015-8-28 上午11:42:57
     * @return
     */
    @OneToMany(mappedBy="organization",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    @OneToMany(mappedBy="organization",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }



    @OneToMany(mappedBy="org",fetch = FetchType.LAZY)
    public Set<BonusEntity> getBonusEntity() {
        return bonusEntity;
    }

    public void setBonusEntity(Set<BonusEntity> bonusEntity) {
        this.bonusEntity = bonusEntity;
    }

    /**
     * 获取活动
     *
     * @return 活动
     */
    @OneToMany(fetch = FetchType.LAZY , mappedBy="organization")
    @OrderBy("createDate desc")
    public Set<Activity> getActivities() {
        return activities;
    }

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("createDate asc")
    public Set<ReturnOrder> getReturnOrders() {
        return returnOrders;
    }

    public void setReturnOrders(Set<ReturnOrder> returnOrders) {
        this.returnOrders = returnOrders;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }



    /**
     * 获取管理员
     *
     * @return 活动
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_admin")
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    /**
     * 获取积分赠送
     *
     * @return    积分赠送
     */
    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
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

    @ManyToMany(mappedBy = "organizations", fetch = FetchType.LAZY )
    @OrderBy("order asc")
    public Set<Inform> getInforms() {
        return informs;
    }

    public void setInforms(Set<Inform> informs) {
        this.informs = informs;
    }

    @Column(name="f_types", length = 4000)
    @Convert(converter = TypesConverter.class)
    public List<Organization.Type> getTypes() {
        return types;
    }

    public void setTypes(List<Organization.Type> types) {
        this.types = types;
    }

    @Column(name="f_openingHours", length = 4000)
    @Convert(converter = OpeningHoursConverter.class)
    public List<String> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<String> openingHours) {
        this.openingHours = openingHours;
    }

    /**
     * 类型转换 - 营业时间
     *
     */
    @Converter
    public static class OpeningHoursConverter extends BaseAttributeConverter<List<String>> implements AttributeConverter<Object, String> {
    }

    /**
     * 类型转换 - 门店类型
     *
     */
    @Converter
    public static class TypesConverter extends BaseAttributeConverter<List<Organization.Type>> implements AttributeConverter<Object, String> {
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
