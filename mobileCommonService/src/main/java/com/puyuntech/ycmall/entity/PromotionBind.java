package com.puyuntech.ycmall.entity;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * Created by 施长成 on 2015/9/26 0026.
 * 促销和绑定指定商品销售
 */
@Entity
@Table(name = "t_promotion_bind")
public class PromotionBind extends BaseEntity<Long> {

	private static final long serialVersionUID = 4733324825619091076L;

	//促销
    private Promotion promotion ;

    private Product product1;
    private BigDecimal price1;
    private String title1;

    private Product product2;
    private BigDecimal price2;
    private String title2;

    private Product product3;
    private BigDecimal price3;
    private String title3;

    private Product product4;
    private BigDecimal price4;
    private String title4;

    private Product product5;
    private BigDecimal price5;
    private String title5;

    @OneToOne(fetch=FetchType.LAZY , targetEntity = Promotion.class,cascade=CascadeType.ALL )
    @JoinColumn(name = "f_promotions" , updatable = false)
    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_product_1")
    public Product getProduct1() {
        return product1;
    }

    public void setProduct1(Product product1) {
        this.product1 = product1;
    }

    @Column(name="f_price_1")
    public BigDecimal getPrice1() {
        return price1;
    }

    public void setPrice1(BigDecimal price1) {
        this.price1 = price1;
    }

    @Column(name="f_title_1")
    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_product_2" )
    public Product getProduct2() {
        return product2;
    }

    public void setProduct2(Product product2) {
        this.product2 = product2;
    }

    @Column(name="f_price_2")
    public BigDecimal getPrice2() {
        return price2;
    }

    public void setPrice2(BigDecimal price2) {
        this.price2 = price2;
    }

    @Column(name="f_title_2")
    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_product_3" )
    public Product getProduct3() {
        return product3;
    }

    public void setProduct3(Product product3) {
        this.product3 = product3;
    }

    @Column(name="f_price_3")
    public BigDecimal getPrice3() {
        return price3;
    }

    public void setPrice3(BigDecimal price3) {
        this.price3 = price3;
    }

    @Column(name="f_title_3")
    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_product_4" )
    public Product getProduct4() {
        return product4;
    }

    public void setProduct4(Product product4) {
        this.product4 = product4;
    }

    @Column(name="f_price_4")
    public BigDecimal getPrice4() {
        return price4;
    }

    public void setPrice4(BigDecimal price4) {
        this.price4 = price4;
    }

    @Column(name="f_titke_4")
    public String getTitle4() {
        return title4;
    }

    public void setTitle4(String title4) {
        this.title4 = title4;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_product_5" )
    public Product getProduct5() {
        return product5;
    }

    public void setProduct5(Product product5) {
        this.product5 = product5;
    }

    @Column(name="f_price_5")
    public BigDecimal getPrice5() {
        return price5;
    }

    public void setPrice5(BigDecimal price5) {
        this.price5 = price5;
    }

    @Column(name="f_title_5")
    public String getTitle5() {
        return title5;
    }

    public void setTitle5(String title5) {
        this.title5 = title5;
    }

    public PromotionBind() {
    }
}
