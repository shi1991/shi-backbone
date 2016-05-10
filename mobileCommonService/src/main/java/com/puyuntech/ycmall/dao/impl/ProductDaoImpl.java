package com.puyuntech.ycmall.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.Setting;
import com.puyuntech.ycmall.dao.ProductDao;
import com.puyuntech.ycmall.entity.Attribute;
import com.puyuntech.ycmall.entity.Brand;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.ProductCategory;
import com.puyuntech.ycmall.entity.ProductModel;
import com.puyuntech.ycmall.entity.Promotion;
import com.puyuntech.ycmall.entity.Tag;
import com.puyuntech.ycmall.util.SystemUtils;

/**
 * 
 * 商品 Dao. Created on 2015-9-6 上午10:40:22
 * 
 * @author 施长成
 */
@Repository("productDaoImpl")
public class ProductDaoImpl extends BaseDaoImpl<Product, Long> implements
		ProductDao {

	/**
	 * 该商品编号 是否已经存在
	 * <p>
	 * Title: snExists.
	 * </P>
	 * <p>
	 * Description:
	 * </P>
	 * 
	 * @param sn
	 *            商品编号
	 * @return
	 * @see com.puyuntech.ycmall.dao.ProductDao#snExists(java.lang.String)
	 */
	public boolean snExists(String sn) {
		/**
		 * 判断序号是否为空
		 */
		if (StringUtils.isEmpty(sn)) {
			return false;
		}
		/**
		 * 定义jpql语句并返回查询结果
		 */
		String jpql = "select count(*) from Product product where lower(product.sn) = lower(:sn)";
		Long count = entityManager.createQuery(jpql, Long.class)
				.setParameter("sn", sn).getSingleResult();
		return count > 0;
	}

	@Override
	public List<Product> listProductByTagType(Tag tag, Boolean isMarketable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);

		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(root.join("tags"), tag));

		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}

		criteriaQuery.where(restrictions);
		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		List<Product> list = query.getResultList();

		return list;
	}

	@Override
	public Product findBySn(String sn) {
		if (StringUtils.isEmpty(sn)) {
			logger.warn("ProductDaoImpl findBySn param sn is null");
		}

		String jpql = "select goods from Product goods where lower(goods.sn) = lower(:sn)";

		try {
			return entityManager.createQuery(jpql, Product.class)
					.setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			logger.error("GoodsDaoImpl findBySn param sn is :" + sn
					+ " error is :" + e);
			return null;
		}
	}

	@Override
	public List<Product> listGoodsByTagType(Tag tag, Boolean isMarketable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(root.join("tags"), tag));

		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}

		criteriaQuery.where(restrictions);
		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		List<Product> list = query.getResultList();
		return list;
	}

	@Override
	public void clearAttributeValue(Attribute attribute) {
		if (attribute == null || attribute.getPropertyIndex() == null
				|| attribute.getProductCategory() == null) {
			return;
		}

		String jpql = "update Product product set product."
				+ Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
				+ attribute.getPropertyIndex()
				+ " = null where product.productCategory = :productCategory";
		entityManager
				.createQuery(jpql)
				.setParameter("productCategory", attribute.getProductCategory())
				.executeUpdate();
	}

	@Override
	public List<Product> listProductsByModel(ProductModel productModel) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);

		Predicate restrictions = criteriaBuilder.conjunction();

		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(root.get("isMarketable"), true));

		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(root.get("productModel"), productModel));

		criteriaQuery.where(restrictions);

		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);

		List<Product> list = query.getResultList();

		return list;
	}

	public Page<Product> findPage(Product.Type type,
			ProductCategory productCategory, Brand brand, Promotion promotion,
			Tag tag, ProductModel productModel, Map<Attribute, String> attributeValueMap,
			BigDecimal startPrice, BigDecimal endPrice, Boolean isMarketable,
			Boolean isList, Boolean isTop, Boolean isOutOfStock,
			Boolean isStockAlert, Boolean hasPromotion,
			Product.OrderType orderType, Pageable pageable) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("type"), type));
		}
		if (productCategory != null) {
			Subquery<ProductCategory> subquery = criteriaQuery
					.subquery(ProductCategory.class);
			Root<ProductCategory> subqueryRoot = subquery
					.from(ProductCategory.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.or(criteriaBuilder.equal(
					subqueryRoot, productCategory), criteriaBuilder.like(
					subqueryRoot.<String> get("treePath"), "%"
							+ ProductCategory.TREE_PATH_SEPARATOR
							+ productCategory.getId()
							+ ProductCategory.TREE_PATH_SEPARATOR + "%")));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.in(root.get("productCategory")).value(subquery));
		}
		if (brand != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("brand"), brand));
		}
		if (productModel != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("productModel"), productModel));
		}
		if (promotion != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.join("promotions"), promotion));
		}
		if (tag != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.join("tags"), tag));
		}
		if (attributeValueMap != null) {
			for (Map.Entry<Attribute, String> entry : attributeValueMap
					.entrySet()) {
				String propertyName = Product.ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX
						+ entry.getKey().getPropertyIndex();
				restrictions = criteriaBuilder.and(
						restrictions,
						criteriaBuilder.equal(root.get(propertyName),
								entry.getValue()));
			}
		}
		if (startPrice != null && endPrice != null
				&& startPrice.compareTo(endPrice) > 0) {
			BigDecimal temp = startPrice;
			startPrice = endPrice;
			endPrice = temp;
		}
		if (startPrice != null && startPrice.compareTo(BigDecimal.ZERO) >= 0) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.ge(root.<Number> get("price"), startPrice));
		}
		if (endPrice != null && endPrice.compareTo(BigDecimal.ZERO) >= 0) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.le(root.<Number> get("price"), endPrice));
		}
		if (isMarketable != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("isMarketable"), isMarketable));
		}
		if (isList != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("isList"), isList));
		}
		if (isTop != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.equal(root.get("isTop"), isTop));
		}
		if (isOutOfStock != null) {
			Path<Integer> stock = root.get("stock");
			Path<Integer> allocatedStock = root.get("allocatedStock");
			if (isOutOfStock) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder
								.lessThanOrEqualTo(stock, allocatedStock));
			} else {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.greaterThan(stock, allocatedStock));
			}
		}
		if (isStockAlert != null) {
			Path<Integer> stock = root.get("stock");
			Path<Integer> allocatedStock = root.get("allocatedStock");
			Setting setting = SystemUtils.getSetting();
			if (isStockAlert) {
				restrictions = criteriaBuilder.and(restrictions,
						criteriaBuilder.lessThanOrEqualTo(
								stock,
								criteriaBuilder.sum(allocatedStock,
										setting.getStockAlertCount())));
			} else {
				restrictions = criteriaBuilder.and(
						restrictions,
						criteriaBuilder.greaterThan(
								stock,
								criteriaBuilder.sum(allocatedStock,
										setting.getStockAlertCount())));
			}
		}
		if (hasPromotion != null) {
			restrictions = criteriaBuilder.and(restrictions,
					criteriaBuilder.isNotNull(root.join("promotions")));
		}
		criteriaQuery.where(restrictions);
		if (orderType != null) {
			switch (orderType) {
			case topDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")),
						criteriaBuilder.desc(root.get("createDate")));
				break;
			case priceAsc:
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")),
						criteriaBuilder.desc(root.get("createDate")));
				break;
			case priceDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")),
						criteriaBuilder.desc(root.get("createDate")));
				break;
			case salesDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("sales")),
						criteriaBuilder.desc(root.get("createDate")));
				break;
			case scoreDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("score")),
						criteriaBuilder.desc(root.get("createDate")));
				break;
			case dateDesc:
				criteriaQuery.orderBy(criteriaBuilder.desc(root
						.get("createDate")));
				break;
			}
		} else if (pageable == null
				|| ((StringUtils.isEmpty(pageable.getOrderProperty()) || pageable
						.getOrderDirection() == null) && (CollectionUtils
						.isEmpty(pageable.getOrders())))) {
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("isTop")),
					criteriaBuilder.desc(root.get("createDate")));
		}
		return super.findPage(criteriaQuery, pageable);

	}

	public boolean specificationValueExists(Product product) {
		/**
		 * 判断商品是否为空
		 */
		if (null == product) {
			return false;
		}
		/**
		 * 定义jpql语句并返回查询结果product.specificationValues = :specificationValues and
		 * product.operator = :operator
		 */
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (product.getOperator() != null) {
			restrictions = criteriaBuilder.and(
					restrictions,
					criteriaBuilder.equal(root.get("operator"),
							product.getOperator()));
		}
		if (product.getProductModel() != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder
					.equal(root.get("productModel"), product.getProductModel()));
		}
		criteriaQuery.where(restrictions);
		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);
		List<Product> list = query.getResultList();
		if (list.size() > 0) {
			for (Product productResult : list) {
				if (productResult.getSpecificationValues().equals(
						product.getSpecificationValues())) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}
	
	public List<Product> scoreProducts(Integer min,Integer max){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder
				.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);

		Predicate restrictions = criteriaBuilder.conjunction();

		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(root.get("isMarketable"), true));
		/** TODO 未测试 **/
		if( min != null  ){
			restrictions =  criteriaBuilder.and(restrictions ,  criteriaBuilder.ge(root.<Number>get("exchangePoint"), min));
		}
		if( max != null ){
			restrictions = criteriaBuilder.and( restrictions ,  criteriaBuilder.le(root.<Number>get("exchangePoint"), max ));
		}
		
		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(root.get("type"), Product.Type.exchange));

		criteriaQuery.where(restrictions);

		TypedQuery<Product> query = entityManager.createQuery(criteriaQuery);

		List<Product> list = query.getResultList();

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String selectGoodsStock(String goodsId, String shopId) {
		List<Object> list=new ArrayList<Object>();
		String sql = null;
		// 商品名称检索
		sql= "SELECT count(a.f_product_sn) shopGodsNum FROM t_stock_log a LEFT JOIN t_organization b ON a.f_organization = b.f_id LEFT JOIN t_product c ON c.f_id = " +
				"a.f_product WHERE c.f_id = :goodsId  AND b.f_id = :shopId  AND   a.f_state=1    ";
		// 执行sql语句
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter("shopId", shopId);
		query.setParameter("goodsId", goodsId);
		list = query.getResultList();
		return list.get(0).toString();
	}

    @Override
    public Product getProductByProductInfo(String productId, String productSn) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder
                .createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.select(root);

        Predicate restrictions = criteriaBuilder.conjunction();

        restrictions = criteriaBuilder.and(restrictions,
                criteriaBuilder.equal(root.get("isMarketable"), true));

        if( null != productId ){
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.equal(root.get("id"), productId));
        }

        if( null != productSn ){
            restrictions = criteriaBuilder.and(restrictions,
                    criteriaBuilder.equal(root.get("sn"), productSn));
        }
        criteriaQuery.where(restrictions);
      
        Product product = entityManager.createQuery(criteriaQuery).getSingleResult();
		
        return product;
    }
}
