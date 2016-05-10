package com.puyuntech.ycmall.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.dao.ContractDao;
import com.puyuntech.ycmall.dao.ProductDao;
import com.puyuntech.ycmall.dao.SnDao;
import com.puyuntech.ycmall.entity.Attribute;
import com.puyuntech.ycmall.entity.Brand;
import com.puyuntech.ycmall.entity.Contract;
import com.puyuntech.ycmall.entity.ContractItem;
import com.puyuntech.ycmall.entity.GrabSeckill;
import com.puyuntech.ycmall.entity.Operator;
import com.puyuntech.ycmall.entity.OrderItem;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.ProductCategory;
import com.puyuntech.ycmall.entity.ProductModel;
import com.puyuntech.ycmall.entity.Promotion;
import com.puyuntech.ycmall.entity.PromotionBind;
import com.puyuntech.ycmall.entity.Sn;
import com.puyuntech.ycmall.entity.Tag;
import com.puyuntech.ycmall.entity.value.ProductImage;
import com.puyuntech.ycmall.exception.ContractException;
import com.puyuntech.ycmall.service.GrabSeckillService;
import com.puyuntech.ycmall.service.OrderItemService;
import com.puyuntech.ycmall.service.ProductService;
import com.puyuntech.ycmall.util.UnivParameter;
import com.puyuntech.ycmall.vo.ProductSpecificationVO;
import com.puyuntech.ycmall.vo.PromotionBindVO;

/**
 * 
 * Service 商品. Created on 2015-9-6 上午10:00:34
 * 
 * @author 施长成
 */

@Service("productServiceImpl")
public class ProductServiceImpl extends BaseServiceImpl<Product, Long>
		implements ProductService {
	/**
	 * 商品dao
	 */
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	/**
	 * 商品序号 Dao
	 */
	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	
	/**
	 * 订单项 Service
	 */
	@Resource(name = "orderItemServiceImpl")
	private OrderItemService orderItemService;
	
	/**
	 * 合约套餐 Dao
	 */
	@Resource(name = "contractDaoImpl")
	private ContractDao contractDao;
	/**
	 * 抢购 Service
	 */
	@Resource(name = "grabSeckillServiceImpl")
	private GrabSeckillService grabSeckillService;
	

	@Override
	public void addAllocatedStock(Product product, int amount) {
		if (amount == 0) {
			return;
		}

		if (!LockModeType.PESSIMISTIC_WRITE.equals(productDao
				.getLockMode(product))) {
			productDao.refresh(product, LockModeType.PESSIMISTIC_WRITE);
		}

		boolean previousOutOfStock = product.getIsOutOfStock();

		product.setAllocatedStock(product.getAllocatedStock() + amount);
		productDao.flush();

		if (product.getIsOutOfStock() != previousOutOfStock) {
			product.setGenerateMethod(Product.GenerateMethod.eager);
		} else {
			product.setGenerateMethod(Product.GenerateMethod.lazy);
		}
	}

	@Override
	public List<Product> listProductByTagType(Tag tag, boolean isMarketable) {
		return productDao.listProductByTagType(tag, isMarketable);
	}

	@Override
	public Product findBySn(String sn) {
		return productDao.findBySn(sn);
	}

	@Override
	public List<Product> listGoodsByTagType(Tag tag, boolean isMarketable) {
		return productDao.listGoodsByTagType(tag, isMarketable);
	}

	/**
	 * 
	 * 生成图片和编号. author: 王凯斌 date: 2015-10-12 下午5:27:33
	 * 
	 * @param product
	 */
	public void setValue(Product product) {

		/**
		 * 判断商品是否为空
		 */
		if (product == null) {
			return;
		}

		/**
		 * 生产图片路径
		 */
		if (StringUtils.isEmpty(product.getImage())
				&& StringUtils.isNotEmpty(product.getThumbnail())) {
			product.setImage(product.getThumbnail());
		}

		/**
		 * 生成序号
		 */
		if (product.isNew()) {
			if (StringUtils.isEmpty(product.getSn())) {
				String sn;
				do {
					sn = snDao.generate(Sn.Type.goods);
				} while (snExists(sn));
				product.setSn(sn);
			}
		}
	}

	@Transactional(readOnly = true)
	public boolean snExists(String sn) {
		return productDao.snExists(sn);
	}

	@Override
	public List<Product> listProductsByModel(ProductModel productModel) {
		return productDao.listProductsByModel(productModel);
	}

	@Transactional(readOnly = true)
	public Page<Product> findPage(Product.Type type,
			ProductCategory productCategory, Brand brand,
			ProductModel productModel, Promotion promotion, Tag tag,
			Map<Attribute, String> attributeValueMap, BigDecimal startPrice,
			BigDecimal endPrice, Boolean isMarketable, Boolean isList,
			Boolean isTop, Boolean isOutOfStock, Boolean isStockAlert,
			Boolean hasPromotion, Product.OrderType orderType, Pageable pageable) {
		return productDao.findPage(type, productCategory, brand, promotion,
				tag, productModel, attributeValueMap, startPrice, endPrice,
				isMarketable, isList, isTop, isOutOfStock, isStockAlert,
				hasPromotion, orderType, pageable);

	}

	public boolean specificationValueExists(Product product) {
		if (productDao.specificationValueExists(product)
				&& !snExists(product.getSn())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void querySelectContractInfo(ModelMap model, Long productId)
			throws Exception {
		Product product = productDao.find(productId);
		// 查找不到商品 商品的运营商ID是1时，返回错误页面
		if (null == product || 1 == product.getOperator().getId()) {
			logger.error("the product isnot exist or the product donot has constact");
			throw new ContractException();
		}
		// 运营商
		Operator operator = product.getOperator();
		// 查询该运营商下面有哪些内容 ， 以及每个合约和都存在哪些合约期
		List<Contract> contracts = contractDao.findByOperator(operator);
		if (null == contracts || contracts.size() < 1) {
			logger.error("the product isnot exist or the product donot has constact");
			throw new ContractException();
		}

	

		TreeSet<Integer> lastTimes = new TreeSet<Integer>();
		List<ContractItem> contractItems = new ArrayList<ContractItem>();
		;
		Set<ContractItem> items = null;
		Integer lastTime = null;

		for (Contract contract : contracts) {
			items = contract.getContractItems();
			for (ContractItem contractItem : items) {
				lastTime = contractItem.getLasttime();
				lastTimes.add(lastTime);
			}
			contractItems.addAll(items);
		}

		Integer[] lastTimesOrder = lastTimes.toArray(new Integer[0]);
		Arrays.sort(lastTimesOrder);

		ContractItem[] contractItemOrder = contractItems
				.toArray(new ContractItem[0]);
		Arrays.sort(contractItemOrder, new Comparator<ContractItem>() {
			@Override
			public int compare(ContractItem o1, ContractItem o2) {
				return o1.getMonthCost().compareTo(o2.getMonthCost());
			}
		});

		model.addAttribute("defaultLastTime", lastTimesOrder[0]);
		model.addAttribute("lastTimes", lastTimesOrder);
		model.addAttribute("contractItems", contractItemOrder);
		model.addAttribute("contract", contracts.get(0));
		model.addAttribute("product", product);

	}

	public List<Product> scoreProducts(Integer min, Integer max) {
		return productDao.scoreProducts(min, max);
	}

	@Override
	public Map<String, Object> getProductInfo(String goodsId) {
		//初始化map集合
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> mapResult = new HashMap<String,Object>();
		try {
			Product product = this.find(Long.parseLong(goodsId));
			
			//商品不存在返回失败 
			if( null == product  ){
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				return result;
			}
			
			//添加 商品信息 start 
			result.put("goodsId", product.getId());
			result.put("goodsSn", product.getSn());
			result.put("goodsName", product.getName());
			result.put("goodsOperator", product.getOperator().getName());
			result.put("goodsSubhead", product.getCaption() );
			result.put("goodsPrice", product.getPrice());
			result.put("goodsMarketPrice", product.getMarketPrice() );
			result.put("specificationItems" ,product.getSpecificationValues() );
			List<String> imgs = new ArrayList<String>(); 
			for(ProductImage proImg :  product.getProductImages()){
				imgs.add( proImg.getLarge() );
			}
			result.put("images", imgs);
			//添加 商品信息 end

		// 促销-绑定销售商品
		PromotionBind bind = null;
		Set<Promotion> tempPromotions = product.getValidPromotions();
		
		List<Map<String, Object>> promotions = new ArrayList<Map<String , Object>>();
		Map<String , Object> tempPromotion = null;
		
		for (Promotion p : tempPromotions) {
			if (p.getPromotionType().equals(Promotion.Type.bindPromotion)) {
				bind = p.getPromotionBind();
			} else {
				tempPromotion = new HashMap<String , Object>();
				tempPromotion.put("promotionType", p.getPromotionType().ordinal() );
				tempPromotion.put("promotionTitle", p.getTitle() );
				Set<Product> gifts = p.getGifts();
			
					if( null != gifts && gifts.size() > 0 ){
						List< Map<String, Object> >promotionGifts = new ArrayList< Map<String, Object> >();
						
						Map<String, Object> tempGift = null;
						for( Product gift : gifts ){
							tempGift =  new HashMap<String, Object>();
							tempGift.put("promotionGiftId", gift.getId() );
							tempGift.put("promotionGiftName", gift.getName() );
							tempGift.put("promotionGiftImagePath", gift.getImage() );
							promotionGifts.add(tempGift);
						}
						
						tempPromotion.put("promotionGift", promotionGifts );
					}
					
					promotions.add( tempPromotion );
				}
			}
			
			result.put("promotions", promotions);

			// 存在绑定促销的商品 将商品分成最多五个商品 返回给 ftl
			if (null != bind) {
				List<PromotionBindVO> bindVos = new ArrayList<PromotionBindVO>();
				PromotionBindVO tempVO = null;
				// 绑定促销商品1
				if (bind.getProduct1() != null) {
					tempVO = new PromotionBindVO();
					tempVO.setProductId( bind.getProduct1().getId() );
					tempVO.setImage( bind.getProduct1().getImage() );
					tempVO.setPrice(bind.getPrice1());
					tempVO.setTitle(bind.getTitle1());
					bindVos.add(tempVO);
				}
				// 绑定促销商品2
				if (bind.getProduct2() != null) {
					tempVO = new PromotionBindVO();
					tempVO.setProductId( bind.getProduct2().getId() );
					tempVO.setImage( bind.getProduct2().getImage() );
					tempVO.setPrice(bind.getPrice2());
					tempVO.setTitle(bind.getTitle2());
					bindVos.add(tempVO);
				}
				// 绑定促销商品3
				if (bind.getProduct3() != null) {
					tempVO = new PromotionBindVO();
					tempVO.setProductId( bind.getProduct3().getId() );
					tempVO.setImage( bind.getProduct3().getImage() );
					tempVO.setPrice(bind.getPrice3());
					tempVO.setTitle(bind.getTitle3());
					bindVos.add(tempVO);
				}
				// 绑定促销商品4
				if (bind.getProduct4() != null) {
					tempVO = new PromotionBindVO();
					tempVO.setProductId( bind.getProduct4().getId() );
					tempVO.setImage( bind.getProduct4().getImage() );
					tempVO.setPrice(bind.getPrice4());
					tempVO.setTitle(bind.getTitle4());
					bindVos.add(tempVO);
				}
				// 绑定促销商品5
				if (bind.getProduct5() != null) {
					tempVO = new PromotionBindVO();
					tempVO.setProductId( bind.getProduct5().getId() );
					tempVO.setImage( bind.getProduct5().getImage() );
					tempVO.setPrice(bind.getPrice5());
					tempVO.setTitle(bind.getTitle5());
					bindVos.add(tempVO);
				}
				//将绑定促销的商品的信息返回给前端
				result.put("promotionBinds", bindVos);
			}

			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, result);
			return mapResult;
		
	}

	@Override
	public Map<String, Object> getNewGoods(String goodsId,
			String shopId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			Product product = this.find( Long.parseLong(goodsId) );
			//商品信息 start
			result.put( "goodsId", product.getId() );
			result.put( "goodsPrice", product.getPrice() );
			result.put( "goodsImagePath", product.getImage() );
			result.put("defaultSpecificationItems" ,product.getSpecificationValues() );
			int stock =Integer.parseInt(productDao.selectGoodsStock(goodsId,shopId));
			result.put("goodsStock", stock);
			//商品信息 end
			//根据 Model 查找到 和该商品匹配的商品
			ProductModel productModel = product.getProductModel();
			List<Product> products = this.listProductsByModel(productModel);
			
			//从entity中抽出相关VO数据
			List<ProductSpecificationVO> productSpecificationVOs = new ArrayList<ProductSpecificationVO>();
			ProductSpecificationVO tempProductSpecificationVO = null;
			
			//循环生成 VO 数据
			for(Product tempproduct : products ){
				tempProductSpecificationVO = new ProductSpecificationVO();
				tempProductSpecificationVO.setId(tempproduct.getId());
				tempProductSpecificationVO.setSn(tempproduct.getSn());
				tempProductSpecificationVO.setOperatorId( tempproduct.getOperator().getId() );
				tempProductSpecificationVO.setOperatorName( tempproduct.getOperator().getName() );
				tempProductSpecificationVO.setSpecifications(tempproduct.getSpecifications());
				tempProductSpecificationVO.setSpecificationValueIds(tempproduct.getSpecificationValueIds());
				tempProductSpecificationVO.setOutOfStock(tempproduct.getIsOutOfStock());
				productSpecificationVOs.add(tempProductSpecificationVO);
			}
			result.put("productOperator", product.getOperator().getName());
			result.put("specificationItems", product.getSpecificationItems());
			result.put("productSpecification" ,  productSpecificationVOs);
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		mapResult.put(UnivParameter.DATA, result);
		return mapResult;
	}

	@Override
	public Map<String, Object> getNewGoodsTop(String goodsId, String shopId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		try{
			Product product = this.find( Long.parseLong(goodsId) );
			//商品信息 start
			result.put( "goodsId", product.getId() );
			result.put( "goodsPrice", product.getPrice() );
			result.put( "goodsImagePath", product.getImage());
			int stock =Integer.parseInt(productDao.selectGoodsStock(goodsId,shopId));
			result.put("goodsStock", stock);
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		mapResult.put(UnivParameter.DATA, result);
		return mapResult;
	}

	@Override
	public Map<String, Object> getRushGoodsRecommend(String grabSeckillId,
			String robGoodsId) {
		//初始化map集合
				Map<String,Object> result = new HashMap<String,Object>();
				Map<String,Object> mapResult = new HashMap<String,Object>();
				try {
					Product product = this.find(Long.parseLong(robGoodsId));
					GrabSeckill  GrabSeckill =grabSeckillService.find(Long.parseLong(grabSeckillId));
					//商品不存在返回失败 
					if( null == product  ||GrabSeckill==null){
						result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
						result.put(UnivParameter.ERRORMESSAGE, "没有该抢购商品");
						return result;
					}
					//添加 商品信息 start 
					result.put("goodsId", product.getId());
					result.put("goodsSn", product.getSn());
					result.put("goodsName", product.getName());
					result.put("goodsSubhead", product.getCaption() );
					result.put("goodsPrice", product.getPrice());
					result.put("goodsMarketPrice", product.getMarketPrice() );
					result.put("goodsPrice", product.getPrice() );
					result.put("goodsColor" ,product.getSpecificationValues() );
					result.put("goodsSeckillPrice",GrabSeckill.getPrice());
					List<String> imgs = new ArrayList<String>(); 
					for(ProductImage proImg :  product.getProductImages()){
						imgs.add( proImg.getLarge() );
					}
					result.put("images", imgs);
					//添加 商品信息 end

					List<Object> promotionBind=new ArrayList<Object>();
					PromotionBind bind = null;
					Set<Promotion> tempPromotions = product.getValidPromotions();
					for (Promotion p : tempPromotions) {
						if (p.getPromotionType().equals(Promotion.Type.bindPromotion)) {
							bind = p.getPromotionBind();
						}
					}
					// 存在绑定促销的商品 将商品分成最多五个商品 返回给 ftl
					if (null != bind) {
						List<PromotionBindVO> bindVos = new ArrayList<PromotionBindVO>();
						PromotionBindVO tempVO = null;
						// 绑定促销商品1
						if (bind.getProduct1() != null) {
							tempVO = new PromotionBindVO();
							tempVO.setProductId( bind.getProduct1().getId() );
							tempVO.setImage( bind.getProduct1().getImage() );
							tempVO.setPrice(bind.getPrice1());
							tempVO.setTitle(bind.getTitle1());
							bindVos.add(tempVO);
						}
						// 绑定促销商品2
						if (bind.getProduct2() != null) {
							tempVO = new PromotionBindVO();
							tempVO.setProductId( bind.getProduct2().getId() );
							tempVO.setImage( bind.getProduct2().getImage() );
							tempVO.setPrice(bind.getPrice2());
							tempVO.setTitle(bind.getTitle2());
							bindVos.add(tempVO);
						}
						// 绑定促销商品3
						if (bind.getProduct3() != null) {
							tempVO = new PromotionBindVO();
							tempVO.setProductId( bind.getProduct3().getId() );
							tempVO.setImage( bind.getProduct3().getImage() );
							tempVO.setPrice(bind.getPrice3());
							tempVO.setTitle(bind.getTitle3());
							bindVos.add(tempVO);
						}
						// 绑定促销商品4
						if (bind.getProduct4() != null) {
							tempVO = new PromotionBindVO();
							tempVO.setProductId( bind.getProduct4().getId() );
							tempVO.setImage( bind.getProduct4().getImage() );
							tempVO.setPrice(bind.getPrice4());
							tempVO.setTitle(bind.getTitle4());
							bindVos.add(tempVO);
						}
						// 绑定促销商品5
						if (bind.getProduct5() != null) {
							tempVO = new PromotionBindVO();
							tempVO.setProductId( bind.getProduct5().getId() );
							tempVO.setImage( bind.getProduct5().getImage() );
							tempVO.setPrice(bind.getPrice5());
							tempVO.setTitle(bind.getTitle5());
							bindVos.add(tempVO);
						}
						//将绑定促销的商品的信息返回给前端
						result.put("promotionBinds", bindVos);
					}else{
						result.put("promotionBinds",promotionBind );
					}

					//存放正确的返回参数CODE--1
					mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
				} catch (Exception e) {
					//存放错误的返回参数CODE--0
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
					return mapResult;
				}
				
					//存放最终的执行结果
					mapResult.put(UnivParameter.DATA, result);
					return mapResult;
	}

	@Override
	public Product findByOrderitem(OrderItem orderitem) {
		OrderItem orderItem2=orderItemService.find(orderitem.getId());
		return productDao.find(orderItem2.getProduct().getId());
	}
}
