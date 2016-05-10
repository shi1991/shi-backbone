package com.puyuntech.ycmall.vo;

import java.math.BigDecimal;

/**
 * 绑定促销的 VO 对象
 * <p/>
 * Created by 施长成 on 2015/10/9 0009.
 */
public class PromotionBindVO {


    
    /**
     * 绑定促销商品Id
     */
    private Long productId;
    
    /**
     * 绑定促销价格
     */
    private BigDecimal price;
    
    /**
     * 商品促销商品 图片
     */
    private String image;
    
    /**
     * 绑定促销title
     */
    private String title;


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
    
}
