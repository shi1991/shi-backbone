package com.puyuntech.ycmall.service.impl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.puyuntech.ycmall.Filter;
import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.Setting;
import com.puyuntech.ycmall.dao.AppIndexDao;
import com.puyuntech.ycmall.dao.OrganizationDao;
import com.puyuntech.ycmall.dao.SnDao;
import com.puyuntech.ycmall.entity.AdEntity;
import com.puyuntech.ycmall.entity.Admin;
import com.puyuntech.ycmall.entity.BonusEntity;
import com.puyuntech.ycmall.entity.BonusLog;
import com.puyuntech.ycmall.entity.Cart;
import com.puyuntech.ycmall.entity.CartItem;
import com.puyuntech.ycmall.entity.Comment;
import com.puyuntech.ycmall.entity.ContractPhoneNumberUserInfo;
import com.puyuntech.ycmall.entity.Coupon;
import com.puyuntech.ycmall.entity.CouponCode;
import com.puyuntech.ycmall.entity.DeliveryCorp;
import com.puyuntech.ycmall.entity.GodMoneyLog;
import com.puyuntech.ycmall.entity.GrabSeckill;
import com.puyuntech.ycmall.entity.GrabSeckillLog;
import com.puyuntech.ycmall.entity.KeyWords;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.OrderItem;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.Payment;
import com.puyuntech.ycmall.entity.PaymentMethod;
import com.puyuntech.ycmall.entity.PhoneNumber;
import com.puyuntech.ycmall.entity.PointLog;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.Receiver;
import com.puyuntech.ycmall.entity.Refunds;
import com.puyuntech.ycmall.entity.ReturnOrder;
import com.puyuntech.ycmall.entity.ReturnOrder.Status;
import com.puyuntech.ycmall.entity.ReturnOrderItem;
import com.puyuntech.ycmall.entity.ReturnOrderLog;
import com.puyuntech.ycmall.entity.ReturnOrderLog.Type;
import com.puyuntech.ycmall.entity.Shipping;
import com.puyuntech.ycmall.entity.ShippingMethod;
import com.puyuntech.ycmall.entity.StockLog;
import com.puyuntech.ycmall.entity.TrackingLog;
import com.puyuntech.ycmall.entity.value.CartItemBindProductValue;
import com.puyuntech.ycmall.entity.value.Invoice;
import com.puyuntech.ycmall.service.AdminService;
import com.puyuntech.ycmall.service.AppIndexService;
import com.puyuntech.ycmall.service.AreaService;
import com.puyuntech.ycmall.service.BonusLogService;
import com.puyuntech.ycmall.service.BonusService;
import com.puyuntech.ycmall.service.CartItemService;
import com.puyuntech.ycmall.service.CartService;
import com.puyuntech.ycmall.service.CommentService;
import com.puyuntech.ycmall.service.ContractPhoneNumberUserInfoService;
import com.puyuntech.ycmall.service.CouponCodeService;
import com.puyuntech.ycmall.service.CouponService;
import com.puyuntech.ycmall.service.DeliveryCorpService;
import com.puyuntech.ycmall.service.GodMoneyLogService;
import com.puyuntech.ycmall.service.GrabSeckillLogService;
import com.puyuntech.ycmall.service.GrabSeckillService;
import com.puyuntech.ycmall.service.KeyWordsService;
import com.puyuntech.ycmall.service.MemberService;
import com.puyuntech.ycmall.service.OrderItemService;
import com.puyuntech.ycmall.service.OrderService;
import com.puyuntech.ycmall.service.OrganizationService;
import com.puyuntech.ycmall.service.PaymentMethodService;
import com.puyuntech.ycmall.service.PhoneNumberService;
import com.puyuntech.ycmall.service.PointLogService;
import com.puyuntech.ycmall.service.ProductService;
import com.puyuntech.ycmall.service.PromotionService;
import com.puyuntech.ycmall.service.ReceiverService;
import com.puyuntech.ycmall.service.RefundsService;
import com.puyuntech.ycmall.service.ReturnOrderItemService;
import com.puyuntech.ycmall.service.ReturnOrderLogService;
import com.puyuntech.ycmall.service.ReturnOrderService;
import com.puyuntech.ycmall.service.ShippingMethodService;
import com.puyuntech.ycmall.service.ShippingService;
import com.puyuntech.ycmall.service.StockLogService;
import com.puyuntech.ycmall.service.TrackingLogService;
import com.puyuntech.ycmall.util.CommonParameter;
import com.puyuntech.ycmall.util.RandomValueUtils;
import com.puyuntech.ycmall.util.SystemUtils;
import com.puyuntech.ycmall.util.UnivParameter;
import com.puyuntech.ycmall.util.WebUtils;
import com.puyuntech.ycmall.util.kuaidi.ResultItem;
import com.puyuntech.ycmall.vo.Result;

/**
 * 
 * ServiceImpl - APP首页相关信息. 
 * Created on 2015-8-25 下午3:49:21 
 * @author yanzhisen
 */
@Service("appIndexServiceImpl")
public class AppIndexServiceImpl extends BaseServiceImpl<AdEntity, Long> implements AppIndexService {
	
	/** 模糊查询最小相似度 **/
	private static final float FUZZY_QUERY_MINIMUM_SIMILARITY  = 0.8F;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * 手机号 Service
	 */
	@Resource(name="phoneNumberServiceImpl")
	private PhoneNumberService phoneNumerService;
	
	/**
	 * 合约套餐及用户信息
	 */
	@Resource(name = "contractPhoneNumberUserInfoServiceImpl")
	private ContractPhoneNumberUserInfoService contractPhoneNumUserInfoService;
	
	@Resource(name="organizationDaoImpl")
	private OrganizationDao organizationDao;
	
	/**
     * 退款单
     */
    @Resource(name="refundsServiceImpl")
	private RefundsService refundsService;
	
	@Resource(name="returnOrderLogServiceImpl")
    private ReturnOrderLogService returnOrderLogService;
	@Resource(name = "trackingLogServiceImpl")
	private TrackingLogService trackingLogService;
	/**
	 * 商品Service
	 */
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	
	@Resource(name ="areaServiceImpl")
	private AreaService areaService;
	
	@Resource(name ="commentServiceImpl")
	private CommentService commentService;
	
	@Resource(name="contractPhoneNumberUserInfoServiceImpl")
	private ContractPhoneNumberUserInfoService contractPhoneNumberUserInfoService;
	
	/**
	 * 订单项 Service
	 */
	@Resource(name = "orderItemServiceImpl")
	private OrderItemService orderItemService;
	
	@Resource(name="receiverServiceImpl")
	private ReceiverService receiverService;
	
	@Resource(name = "appIndexDaoImpl")
	private AppIndexDao appIndexDao;
	
	@Resource(name = "stockLogServiceImpl")
	private StockLogService stockLogService;
	
	/**
	 * 猜你喜欢Service
	 */
	@Resource(name = "keyWordsServiceImpl")
	private KeyWordsService keyWordsService;
	
	
	/**
	 * 红包 Service 
	 */
	@Resource(name = "bonusServiceImpl")
	private BonusService bonusService;
	/**
	 * 红包记录 Service 
	 */
	@Resource(name = "bonusLogServiceImpl")
	private BonusLogService bonusLogService;
	
	@Resource(name="shippingServiceImpl")
	private ShippingService shippingService;
	/**
	 * 优惠码.  Service 
	 */
	@Resource(name = "couponCodeServiceImpl")
	private CouponCodeService couponCodeService;
	/**
	 * 优惠卷.  Service 
	 */
	@Resource(name = "couponServiceImpl")
	private CouponService couponService;
	
	/**
	 * 购物车Service
	 */
	@Resource(name = "cartServiceImpl")
	private CartService cartService;
	
	/**
	 * 会员Service  
	 */
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	/**
	 * 会员Service  
	 */
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	
	/**
	 * 秒杀 Service  
	 */
	@Resource(name = "grabSeckillServiceImpl")
	private GrabSeckillService grabSeckillService;
	/**
	 * 秒杀记录 Service  
	 */
	@Resource(name = "grabSeckillLogServiceImpl")
	private GrabSeckillLogService grabSeckillLogService;
	
	/**
	 * 神币Service
	 */
	@Resource(name = "godMoneyLogServiceImpl")
	private GodMoneyLogService godMoneyLogService;
	/**
	 * 积分Service
	 */
	@Resource(name = "pointLogServiceImpl")
	private PointLogService pointLogService;
	/**
	 * 门店Service
	 */
	@Resource(name = "organizationServiceImpl")
	private OrganizationService organizationService;
	
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	
	@Resource(name = "cartItemServiceImpl")
	private CartItemService cartItemService;
	
	@Resource(name = "shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	
	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	
	@Resource(name = "paymentMethodServiceImpl")
	private PaymentMethodService paymentMethodService;
	
	@Resource(name = "promotionServiceImpl")
	private PromotionService promotionService;
	
	/**
	 * 
	 */
	@Resource(name="returnOrderServiceImpl")
	private ReturnOrderService returnOrderService;
	
	@Resource(name="returnOrderItemServiceImpl")
	private ReturnOrderItemService returnOrderItemService;
	
	@Resource(name="deliveryCorpServiceImpl")
	private DeliveryCorpService deliveryCorpService;
	
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public List<AdEntity> findAdvertisement() {
		
		return appIndexDao.findAdvertisement();
	}

	@Override
	public List<AdEntity> findAdByPosition(Integer positionId) {

		return appIndexDao.findAdByPosition(positionId);
	}

	@Override
	public Map<String,Object> getUserInfo(String userId) {
		return appIndexDao.getUserInfo(userId);
	}

	@Override
	public Map<String, Object> getParameterText(String goodsId) {
		return appIndexDao.getParameterText(goodsId);
	}

	@Override
	public Map<String, Object> getSpecificationText(String goodsId) {
		return appIndexDao.getSpecificationText(goodsId);
	}

	@Override
	public Map<String, Object> getGoodsImageText(String goodsId) {
		return appIndexDao.getGoodsImageText(goodsId);
	}

	@Override
	public Map<String, Object> getUserDetail(String userId) {
		return appIndexDao.getUserDetail(userId);
	}

	@Override
	public Map<String, Object> getCurrency(String userId) {
		return appIndexDao.getCurrency(userId);
	}

	@Override
	public Map<String, Object> getHistory(String userId,String getInfoType) {
		return appIndexDao.getHistory(userId,getInfoType);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGoodsList(String selectVaule,
			int pageSize , int count ,Product.OrderType orderType,BigDecimal startPrice, BigDecimal endPrice) {
		Map<String, Object> mapResult=new HashMap<String, Object>();
		Page<Product> page =new Page<Product>();
		try {
			String text = QueryParser.escape(selectVaule);
			TermQuery snQuery = new TermQuery(new Term("sn", text));
			Query keywordQuery = new QueryParser(Version.LUCENE_36, "keyword", new IKAnalyzer()).parse(text);
			QueryParser nameParser = new QueryParser(Version.LUCENE_36, "name", new IKAnalyzer());
			
			nameParser.setDefaultOperator(QueryParser.AND_OPERATOR);
			Query nameQuery = nameParser.parse(text);
			FuzzyQuery nameFuzzyQuery = new FuzzyQuery(new Term("name", text), FUZZY_QUERY_MINIMUM_SIMILARITY);
			
			TermQuery introductionQuery = new TermQuery(new Term("introduction", text));
			TermQuery isMarketableQuery = new TermQuery(new Term("isMarketable", "true"));
			TermQuery isListQuery = new TermQuery(new Term("isList", "true"));
			
			BooleanQuery textQuery = new BooleanQuery();
			BooleanQuery query = new BooleanQuery();
			
			textQuery.add(snQuery, BooleanClause.Occur.SHOULD);
			textQuery.add(keywordQuery, BooleanClause.Occur.SHOULD);
			textQuery.add(nameQuery, BooleanClause.Occur.SHOULD);
			textQuery.add(nameFuzzyQuery, BooleanClause.Occur.SHOULD);
			
			textQuery.add(introductionQuery, BooleanClause.Occur.SHOULD);
			query.add(isMarketableQuery, BooleanClause.Occur.MUST);
			query.add(isListQuery, BooleanClause.Occur.MUST);
			query.add(textQuery, BooleanClause.Occur.MUST);
			
			if (startPrice != null && endPrice != null && startPrice.compareTo(endPrice) > 0) {
				BigDecimal temp = startPrice;
				startPrice = endPrice;
				endPrice = temp;
			}
			if (startPrice != null && startPrice.compareTo(BigDecimal.ZERO) >= 0 && endPrice != null && endPrice.compareTo(BigDecimal.ZERO) >= 0) {
				NumericRangeQuery<Double> numericRangeQuery = NumericRangeQuery.newDoubleRange("price", startPrice.doubleValue(), endPrice.doubleValue(), true, true);
				query.add(numericRangeQuery, BooleanClause.Occur.MUST);
			} else if (startPrice != null && startPrice.compareTo(BigDecimal.ZERO) >= 0) {
				NumericRangeQuery<Double> numericRangeQuery = NumericRangeQuery.newDoubleRange("price", startPrice.doubleValue(), null, true, false);
				query.add(numericRangeQuery, BooleanClause.Occur.MUST);
			} else if (endPrice != null && endPrice.compareTo(BigDecimal.ZERO) >= 0) {
				NumericRangeQuery<Double> numericRangeQuery = NumericRangeQuery.newDoubleRange("price", null, endPrice.doubleValue(), false, true);
				query.add(numericRangeQuery, BooleanClause.Occur.MUST);
			}
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, Product.class);
//			SortField[] sortFields = null;
//			if (orderType != null) {
//				switch (orderType) {
//				case topDesc:
//					sortFields = new SortField[] { new SortField("isTop", SortField.STRING, true), new SortField(null, SortField.SCORE), new SortField("createDate", SortField.LONG, true) };
//					break;
//				case priceAsc:
//					sortFields = new SortField[] { new SortField("price", SortField.DOUBLE, false), new SortField("createDate", SortField.LONG, true) };
//					break;
//				case priceDesc:
//					sortFields = new SortField[] { new SortField("price", SortField.DOUBLE, true), new SortField("createDate", SortField.LONG, true) };
//					break;
//				case salesDesc:
//					sortFields = new SortField[] { new SortField("sales", SortField.LONG, true), new SortField("createDate", SortField.LONG, true) };
//					break;
//				case scoreDesc:
//					sortFields = new SortField[] { new SortField("score", SortField.FLOAT, true), new SortField("createDate", SortField.LONG, true) };
//					break;
//				case dateDesc:
//					sortFields = new SortField[] { new SortField("createDate", SortField.LONG, true) };
//					break;
//				}
//			} else {
//				sortFields = new SortField[] { new SortField("isTop", SortField.STRING, true), new SortField(null, SortField.SCORE), new SortField("createDate", SortField.LONG, true) };
//			}
			//两数相除，如果有余数则取整加1
			int pageNumber = count%pageSize ==0?count/pageSize :count/pageSize +1;
			Pageable pageable=new Pageable(pageNumber,pageSize );
			
//			fullTextQuery.setSort(new Sort(sortFields));
			fullTextQuery.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
			fullTextQuery.setMaxResults(pageable.getPageSize());
			page=new Page<Product>(fullTextQuery.getResultList(), fullTextQuery.getResultSize(), pageable);
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (ParseException e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		mapResult.put(UnivParameter.DATA, page);
		return mapResult;
	}
	@Override
	public Map<String, Object> getRankingList( int luckyNum) {
		return appIndexDao.getRankingList(luckyNum);
	}

	@Override
	public Map<String, Object> listGoodsByTagType(int goodsType) {
		return appIndexDao.listGoodsByTagType(goodsType);
	}

	@Override
	public Map<String, Object> addAddress(Receiver receiver,Long areaId,Long userId) {
		return appIndexDao.addAddress(receiver,areaId,userId);
	}
	@Override
	public Map<String, Object> updateAddress(Receiver receiver,Long areaId,Long userId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		
		try {
			//修改数据
			Receiver receiver2=receiverService.find(receiver.getId());
			receiver2.setMember(memberService.find(userId));
			receiver2.setArea(areaService.find(areaId));
			receiver2.setAreaName(areaService.find(areaId).getFullName());
			receiver2.setConsignee(receiver.getConsignee());
			receiver2.setIsDefault(receiver.getIsDefault());
			receiver2.setAddress(receiver.getAddress());
			receiver2.setPhone(receiver.getPhone());
			receiver2.setZipCode(receiver.getZipCode());
			receiverService.update(receiver2);
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
			return mapResult;
	}

	@Override
	public Map<String, Object> deleteAddress(Receiver receiver) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		try {
			//删除数据
			receiverService.delete(receiver.getId());
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
			return mapResult;
	}

	@Override
	public Map<String, Object> getAddress(String userId) {
		return appIndexDao.getAddress(userId);
	}

	@Override
	public Map<String, Object> getCoupon(String userId, String couponType,
			String type, String couponId, int pageLoadType,
			int pageRowsCount) {
		return appIndexDao.getCoupon(userId,couponType,type,couponId,pageLoadType,pageRowsCount);
	}

	@Override
	public Map<String, Object> useCoupon(String userId, String couponId) {
		return appIndexDao.useCoupon(userId,couponId);
	}

	@Override
	public Map<String, Object> getCart(String userId) {
		return appIndexDao.getCart(userId);
	}

	@Override
	public Map<String, Object> getFavorite(String userId, int pageLoadType,
			int pageRowsCount, String goodsId) {
		
		return  appIndexDao.getFavorite(userId,pageLoadType,pageRowsCount,goodsId);
	}



	@Override
	public Map<String, Object> getBonusHistroy(String userId, int pageLoadType,
			int pageRowsCount, String packetId) {
		return  appIndexDao.getBonusHistroy(userId,pageLoadType,pageRowsCount,packetId);
	}

	@Override
	public Map<String, Object> getGoodsAfterSale(String goodsId) {
		return  appIndexDao.getGoodsAfterSale(goodsId);
	}

	@Override
	public Map<String, Object> getShop(int pageLoadType,int pageRowsCount,
			String shopId,Float userXCoordinate,Float userYCoordinate) {
		return  appIndexDao.getShop(pageLoadType,pageRowsCount,shopId,userXCoordinate,userYCoordinate);
	}

	@Override
	public Map<String, Object> getOrders(String userId, int ordersType,
			int pageLoadType, int pageRowsCount, String createDate) {
		return  appIndexDao.getOrders(userId,ordersType, pageLoadType,pageRowsCount,createDate);
	}

	@Override
	public Map<String, Object> getShopDetailAndActivity(int shopInfoType,
			String shopId){
		return  appIndexDao.getShopDetailAndActivity(shopInfoType,shopId);
	}

	@Override
	public Map<String, Object> getShopId(Float userXCoordinate,
			Float userYCoordinate) {
		return  appIndexDao.getShopId(userXCoordinate,userYCoordinate);
	}
	
	@Override
	public Map<String, Object> applyBonus(String userId, int packetType,
			BigDecimal packetCredit, int pcaketNumber, String splitType,
			String packetTitle, String packetInfo, String packetGoods,String packetCheckTime,String packetOrganization) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		if(packetCredit==null||packetTitle==null){
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "额度或红包名称为空");
			return mapResult;
		}
		Member member =memberService.find(Long.parseLong(userId));
		if(member==null){
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "会员不存在");
			return mapResult;
		}
		if(packetType==1&&member.getGodMoney().compareTo(packetCredit)<0){
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "会员神币不足");
			return mapResult;
		}
		if(packetType==2&&new BigDecimal(member.getPoint()).compareTo(packetCredit)<0){
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "会员积分不足");
			return mapResult;
		}
		try {
			if(packetType==3){
				Organization organization	=	organizationService.find( Long.parseLong(packetOrganization));
				if(organization==null){
					//存放错误的返回参数CODE--0
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, "门店不存在");
					return mapResult;
				}
				//定义时间
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Date date=sdf.parse(packetCheckTime);
				//红包类
				BonusEntity bonus =new BonusEntity();
				bonus.setMember(member);
				bonus.setCredit(packetCredit);
				bonus.setResidue(pcaketNumber);
				bonus.setState(BonusEntity.State.checking);
				bonus.setTitle(packetTitle);
				bonus.setPacketGoods(packetGoods);
				bonus.setGross(pcaketNumber);
				bonus.setContent(packetInfo);
				bonus.setOrg(organization);
				bonus.setCheckTime(date);
				bonus.setApplyTime(new Date());
				bonus.setType(BonusEntity.Type.entity);
				bonus.setBonusKind(BonusEntity.BonusKind.Equal);
				bonusService.save(bonus);
			}else{
				//红包类
				BonusEntity bonus =new BonusEntity();
				bonus.setMember(member);
				
				bonus.setResidue(pcaketNumber);
				bonus.setState(BonusEntity.State.checking);
				bonus.setTitle(packetTitle);
				bonus.setGross(pcaketNumber);
				bonus.setContent(packetInfo);
				bonus.setApplyTime(new Date());
				if(packetType==1){
					bonus.setType(BonusEntity.Type.godMoney);
					bonus.setPacketGoods("神币");
					bonus.setCredit(packetCredit);
				}else if(packetType==2){
					bonus.setType(BonusEntity.Type.point);
					bonus.setPacketGoods("积分");
					bonus.setCredit(packetCredit);
				}
				if(splitType.equals("0")){
					bonus.setBonusKind(BonusEntity.BonusKind.Equal);
				}else{
					bonus.setBonusKind(BonusEntity.BonusKind.Random);
				}
				bonusService.save(bonus);
				if(packetType==1){
					//会员减去神币
					member.setGodMoney(member.getGodMoney().subtract(packetCredit));
					memberService.update(member);
					//创建一条神币扣除记录
					GodMoneyLog godMoneyLog = new GodMoneyLog();
					godMoneyLog.setBalance(member.getGodMoney());
					godMoneyLog.setDebit(packetCredit);
					godMoneyLog.setCredit(BigDecimal.ZERO);
					godMoneyLog.setType(GodMoneyLog.Type.bounsApply);
					godMoneyLog.setMember(member);
					godMoneyLogService.save(godMoneyLog);
					}else if(packetType==2){
					//会员减去积分
					member.setPoint(member.getPoint()-packetCredit.longValue());
					memberService.update(member);
					//创建一条积分扣除记录
					//创建一条积分记录
					PointLog pointLog = new PointLog();
					pointLog.setType(PointLog.Type.bounsApply);
					pointLog.setCredit(0l);
					pointLog.setDebit(packetCredit.longValue());
					pointLog.setBalance(member.getPoint());
					pointLog.setMember(member);
					pointLogService.save(pointLog);
				}			
			}
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.ERRORMESSAGE, "发布成功");
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		return mapResult;
	}

	@Override
	public Map<String, Object> getTodayRobList() {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> result = new ArrayList<Object>();
		String sql = null;
		String sql1 = "SELECT a.f_id grabSeckillId,date_format(a.f_start_time,'%Y-%m-%d %T') startTime,a.f_left_img leftImg,a.f_right_img rightImg,"
				+ "b.f_id robGoodsId, a.f_title robGoodsName, a.f_price robPrice, a.f_goods_residue stockGross, a.f_state state," +
				" a.f_content robGoodsInfo, a.f_image robGoodsImagePath, a.f_goods_type robGoodsType, a.f_position robGoodsPosition FROM" +
				" t_grab_seckill a LEFT JOIN t_product b ON a.f_goods = b.f_id WHERE a.f_goods_type = 1 AND a.f_type = 1 AND a.f_state = 1 and a.f_id = :goodsId" ;
		String sql2 = "SELECT a.f_id grabSeckillId,date_format(a.f_start_time,'%Y-%m-%d %T') startTime,a.f_left_img leftImg,a.f_right_img rightImg,"
				+ "b.f_id robGoodsId, b.f_title robGoodsName, a.f_price robPrice, a.f_goods_residue stockGross, a.f_state state, " +
				"CASE WHEN b.f_bonus_type = 3 THEN b.f_packet_goods ELSE b.f_content END robGoodsInfo, a.f_image robGoodsImagePath, "
				+ "b.f_bonus_type+1 robGoodsType,a.f_position robGoodsPosition FROM " +
				"t_grab_seckill a LEFT JOIN t_bonus b ON a.f_goods = b.f_id WHERE a.f_goods_type = 2 AND a.f_type = 1 AND a.f_state = 1 and a.f_id = :goodsId";
		String sql3 = "SELECT a.f_id grabSeckillId,date_format(a.f_start_time,'%Y-%m-%d %T') startTime,a.f_left_img leftImg,a.f_right_img rightImg,"
				+ "b.f_id robGoodsId, a.f_title robGoodsName, a.f_price robPrice, a.f_goods_residue stockGross, a.f_state state, a.f_content robGoodsInfo," +
				" a.f_image robGoodsImagePath, a.f_goods_type + 2 robGoodsType, b.f_count_price robCouponPrice, a.f_position robGoodsPosition FROM t_grab_seckill a" +
				" LEFT JOIN t_coupon b ON a.f_goods = b.f_id WHERE a.f_goods_type = 3 AND a.f_type = 1 AND a.f_state = 1 and a.f_id = :goodsId";
		try {
			for (int j = 1; j < 4; j++) {
				GrabSeckill grabSeckill = grabSeckillService.findByPosition(j);
				if( grabSeckill != null){
					switch (grabSeckill.getGoodsTypes().ordinal()) {
					case 1:
						sql = sql1;
						break;
					case 2:
						sql = sql2;
						break;
					case 3:
						sql = sql3;
						break;
					default:
						break;
					}
					
					switch (j) {
					case 1:
							List<Object> listGoods= appIndexDao.sql(grabSeckill.getId(),sql);
							if(listGoods.isEmpty()){
								Map<String,Object> resultGoods = new HashMap<String, Object>();
								resultGoods.put("robGoodsPosition",grabSeckill.getPosition());
								result.add(resultGoods);
							}else{
								result.add(listGoods.get(0));	
							}
						break;
					case 2:
							List<Object> listBonus= appIndexDao.sql(grabSeckill.getId(),sql);
							if(listBonus.isEmpty()){
								Map<String,Object> resultBonus =  new HashMap<String, Object>();
								resultBonus.put("robGoodsPosition",grabSeckill.getPosition());
								result.add(resultBonus);
							}else{
								result.add(listBonus.get(0));	
							}
						break;
					case 3:
							List<Object> list= appIndexDao.sql(grabSeckill.getId(),sql);
							if(list.isEmpty()){
								Map<String,Object> resultGoods = new HashMap<String, Object>();
								resultGoods.put("robGoodsPosition",grabSeckill.getPosition());
								result.add(resultGoods);
							}else{
								result.add(list.get(0));
							}
						break;
					default:
						break;
					}
				}else{
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("robGoodsPosition",j);
					result.add(map);
				}
				
			}
			
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			e.printStackTrace();
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
			//存放最终的执行结果
			mapResult.put(UnivParameter.DATA, result);
			return mapResult;
	}

	@Override
	public Map<String, Object> snapJudgment(Long grabSeckillId,
			int robGoodsType,Long userId,String startTime) {
		return appIndexDao.snapJudgment(grabSeckillId,robGoodsType,userId,startTime);
	}
	
	@Override
	public Map<String, Object> getRushGoodsRecommend(String grabSeckillId,String robGoodsId,
			int robGoodsType,String userId) {
		return appIndexDao.getRushGoodsRecommend(grabSeckillId,robGoodsId,robGoodsType,userId);
	}
	
	
	@Override
	public Map<String, Object> getShoppingRush(String userId) {
		return appIndexDao.getShoppingRush(userId);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> shoppingRush(Long grabSeckillId,Long robGoodsId,
			int robGoodsType, Long userId) {
			//初始化map集合
			Map<String,Object> mapResult = new HashMap<String,Object>();
			Map<String, Object> map=new HashMap<String, Object>();
			Map<String, Object> map2=new HashMap<String, Object>();
			Map<String, Object> map3=new HashMap<String, Object>();
			List<Object> list2=new ArrayList<Object>();
			try {
					Member member = memberService.find(userId);
					//根据抢购商品的ID从实体类中获取抢购商品
					GrabSeckill grabSeckill = grabSeckillService.find(grabSeckillId);
					int residue =grabSeckill.getGoodsResidue();
					if(residue>=1){
						//秒杀物品中的剩余数量减去一	
						grabSeckill.setGoodsResidue(residue-1);
						grabSeckillService.update(grabSeckill);
						//秒杀记录中添加一条
						GrabSeckillLog grabSeckillLog =new GrabSeckillLog();
						grabSeckillLog.setVersion(0L);
						grabSeckillLog.setGrabSeckills(grabSeckill);
						grabSeckillLog.setGoods(grabSeckill.getGoods());
						grabSeckillLog.setType(GrabSeckillLog.GrabSecKillTypeEnmu.GRAB);
						grabSeckillLog.setMember(member);
						grabSeckillLog.setDatetime(new Date());
						grabSeckillLog.setPrice(grabSeckill.getPrice() == null ? BigDecimal.ZERO : grabSeckill.getPrice());
						//robGoodsType 商品类型商品类型【1：商品，2：神币类红包 3：积分类红包，4：实物类红包  5：优惠券】
						if(robGoodsType==1){
							//秒杀记录中添加一条
							grabSeckillLog.setGoodsTypes(GrabSeckillLog.GoodsTypeEnmu.GOODS);
							Long grabSeckillLogId = grabSeckillLogService.save(grabSeckillLog).getId();
							
							Receiver defaultReceiver = receiverService.findDefault(member);
							Product product=productService.find(robGoodsId);
							if(!StringUtils.isEmpty(defaultReceiver)){
								map2.put("consignee", defaultReceiver.getConsignee());
								map2.put("id", defaultReceiver.getId());
								map2.put("xiangxidizhi", defaultReceiver.getAreaName() + defaultReceiver.getAddress());
								map2.put("phone", defaultReceiver.getPhone());
								map.put("receiver", map2);
							}else{
								map.put("receiver", null);
							}
							
							map3.put("grabSeckillLogId", grabSeckillLogId);
							map3.put("goodsId", product.getId());
							map3.put("goodsImagePath",product.getThumbnail());
							map3.put("goodsName",product.getName());
							map3.put("goodsSubhead",product.getCaption());
							map3.put("goodsPrice",product.getPrice());
							list2.add(map3);
							map.put("proList", list2);
							
							//TODO
							BigDecimal freight = BigDecimal.ZERO;
							map.put("orderamount", grabSeckill.getPrice().add(freight).doubleValue());
							map.put("freight", freight);
							map.put("orderPrice", grabSeckill.getPrice());
							
							
							
							//存放正确的返回参数CODE--1
							mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
							mapResult.put(UnivParameter.DATA,map);
						}else if(robGoodsType==2||robGoodsType==3||robGoodsType==4){
							
							//秒杀记录中添加一条
							grabSeckillLog.setGoodsTypes(GrabSeckillLog.GoodsTypeEnmu.BONUS);
							grabSeckillLogService.save(grabSeckillLog);
							//修改红包中的剩余数量
							BonusEntity bonus = bonusService.find(robGoodsId);
							bonus.setResidue(residue-1);
							bonusService.update(bonus);
							//根据红包ID取红包记录
							List<Object> list=bonusLogService.find(robGoodsId.toString());
							
							
							if(list.size()==0){
								//存放错误的返回参数CODE--0
								mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
								mapResult.put(UnivParameter.ERRORMESSAGE, "就差一点点,请您再接再厉!惊喜还在后面!");
							}else{
								String name = bonus.getMember().getName();
								Map mm = (Map) list.get(0);
								Long  bonusLogId = Long.parseLong(mm.get("bonusLogId").toString());
								//获取红包记录中的额度
								BonusLog bonusLog = bonusLogService.find(bonusLogId);
								BigDecimal credits=bonusLog.getCredits();
								//修改红包记录里的member
								bonusLog.setAcquireTime(new Date());
								bonusLog.setMember(member);
								//神币类红包
								if(robGoodsType==2){
									//修改红包记录中的兑换状态
									bonusLog.setExchangeState('1');
									bonusLogService.update(bonusLog);
									//会员表中增加神币
									BigDecimal godMoney =member.getGodMoney();
									member.setGodMoney(godMoney.add(credits));
									memberService.update(member);
									// 创建一条神币增加记录
									GodMoneyLog godMoneyLog = new GodMoneyLog();
									godMoneyLog.setVersion(0L);
									BigDecimal godMoneyBalance = godMoney.add(credits);
									godMoneyLog.setBalance(godMoneyBalance);
									godMoneyLog.setCredit(credits);
									godMoneyLog.setDebit(BigDecimal.ZERO );
									godMoneyLog.setType(GodMoneyLog.Type.packetAcquisition);
									godMoneyLog.setMember(member);
									godMoneyLogService.save(godMoneyLog);
									
									
									map.put("robPacketCredits", credits);
									map.put("robGoodInfo", bonus.getContent());
									map.put("robPacketMemberName", name);
									//存放正确的返回参数CODE--1
									mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);	
									mapResult.put(UnivParameter.DATA,map);
									//积分类红包
								}else	if(robGoodsType==3){
									//修改红包记录中的兑换状态
									bonusLog.setExchangeState('1');
									bonusLogService.update(bonusLog);
									Long pointHas = credits.longValue(); 
									//会员表中增加积分
									Long point =member.getPoint();
									member.setPoint(pointHas+point);
									memberService.update(member);
									// 创建一条积分添加记录
									PointLog pointLog = new PointLog();
									pointLog.setBalance(point + pointHas);
									pointLog.setCredit(pointHas);
									pointLog.setDebit(0l);
									pointLog.setType(PointLog.Type.packetAcquisition);
									pointLog.setMember(member);
									pointLogService.save(pointLog);
									map.put("robGoodInfo", bonus.getContent());
									map.put("robPacketCredits", pointHas);
									map.put("robPacketMemberName", name);
									//存放正确的返回参数CODE--1
									mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);	
									mapResult.put(UnivParameter.DATA,map);
									//实物类红包
								}else if(robGoodsType==4){
									bonusLogService.update(bonusLog);
									map.put("robGoodTitle", bonus.getTitle());
									map.put("robGoodInfo", bonus.getPacketGoods());
									map.put("robCouponTime", format2.format(grabSeckillLog.getCreateDate()));
									map.put("robCouponOrg", bonus.getOrg().getName());
									map.put("robPacketMemberName", name);
//									map.put("orgName", bonus.getOrg().getName());
									//存放正确的返回参数CODE--1
									mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);	
									mapResult.put(UnivParameter.DATA,map);
								}
							}
							//优惠券
						}else if(robGoodsType==5){
							//秒杀记录中添加一条
							grabSeckillLog.setGoodsTypes(GrabSeckillLog.GoodsTypeEnmu.COUPONS);
							grabSeckillLogService.save(grabSeckillLog);
							//查找优惠卷并修改其中剩余数量
							Coupon coupon = couponService.find(robGoodsId);
							int couponResidue=coupon.getResidue();
							coupon.setResidue(couponResidue-1);
							couponService.update(coupon);
							//优惠码中修改其中记录
							List<Object>  couponCodeList = couponCodeService.find(robGoodsId.toString());
							if(couponCodeList.size()==0){
								//存放错误的返回参数CODE--0
								mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
								mapResult.put(UnivParameter.ERRORMESSAGE, "就差一点点,请您再接再厉!惊喜还在后面!");
							}else{
								Map mm = (Map) couponCodeList.get(0);
								Long  couponCodeId = Long.parseLong(mm.get("couponCodeId").toString());
								CouponCode couponCode = couponCodeService.find(couponCodeId);
								couponCode.setMember(member);
								couponCode.setIsUsed(false);
								couponCodeService.update(couponCode);
								
								map.put("introduction", coupon.getIntroduction());
								map.put("beginDate", coupon.getBeginDate());
								map.put("endDate", coupon.getEndDate());
								map.put("countPrice", coupon.getCountPrice());
								map.put("minimumPrice", coupon.getMinimumPrice());
								map.put("couponType", coupon.getCouponType());
								//存放正确的返回参数CODE--1
								mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);	
								mapResult.put(UnivParameter.DATA,map);
							}
						}
				}else{
					//存放错误的返回参数CODE--0
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, "就差一点点,请您再接再厉!惊喜还在后面!");
					return mapResult;
				}
			}catch (Exception e) {
				//存放错误的返回参数CODE--500
				mapResult.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
				return mapResult;
			}
		//存放最终的执行结果
		return mapResult;
	}
	
	@Override
	public Map<String, Object> getBonusDetail(String packetId) {
		return appIndexDao.getBonusDetail(packetId);
	}

	@Override
	public Map<String, Object> getGoodsDetail(String goodsId,String productSn) {
		return appIndexDao.getGoodsDetail(goodsId,productSn);
	}

	@Override
	public Map<String, Object> getGoodsPromotionDetail(String goodsId,
			int promotionType) {
		return appIndexDao.getGoodsPromotionDetail(goodsId,promotionType);
	}

	@Override

	public Map<String, Object> idgroup(Long id, String specification,int operator) {
		return appIndexDao.idgroup(id,specification,operator);
	}

	@Override
	@Transactional
	public Map<String, Object> setDefaultAddress(Long userId, Long addressId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Member member=memberService.find(userId);
			if( null == member){
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.DATA,"用户不可为空");
				return result;
			}
			Receiver receiver=receiverService.findDefault(member);
			if( null != receiver){
				receiver.setIsDefault(false);
				receiverService.update(receiver);
			}
			Receiver receiver2=receiverService.find(addressId);
			if( null == receiver2){
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.DATA,"地址不存在");
				return result;
			}
			receiver2.setIsDefault(true);
			receiverService.update(receiver2);
			// 存放正确的返回参数CODE--1
			result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			result.put(UnivParameter.DATA, "修改成功");
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			result.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return result;
		}
		return result;
	}

	@Override
	public Map<String,Object> getBind(Long productId) {
		return appIndexDao.getBind(productId);
	}

	@Override
	public List<CartItem> getCartItem(Cart cart,Product product) {
		return appIndexDao.getCartItem(cart,product);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getCartList(Long userId) {
		Cart cart=cartService.getCurrent(memberService.find(userId).getUsername());

		List< Object > list=new ArrayList< Object >();
		List<Object> list2 = new ArrayList<Object>();
		List<Object> listmanzeng=new ArrayList<Object>();
		List<Object> listmaizeng=new ArrayList<Object>();
		List<Object> listmaijian=new ArrayList<Object>();
		List<Object> listdanpin=new ArrayList<Object>();
		Map<String,Object> result2= new HashMap<String, Object>();
		Map<String,Object> result= new HashMap<String, Object>();
		Map<String,Object> result3= new HashMap<String, Object>();
		BigDecimal decimal2 = null;
		Map<String, Object> mapResult = new HashMap<String, Object>();
		
		List<CartItem> cartItems=new ArrayList<CartItem>(cart.getCartItems());
		try {
			if( cart != null  &&  cartItems.size() > 0 ){
				for( CartItem cartItem :  cartItems ){
					decimal2 = new BigDecimal(0);
					Map<String, Object> mapPro=new HashMap<String, Object>();
					mapPro.put("cartItemId", cartItem.getId());
					
					
					//捆绑
					if(CartItem.type.mainproduct == cartItem.getParentId() ){
						List<CartItemBindProductValue> bindProducts = cartItem.getBindProductIds() ;
						List<Object> bindlist = new ArrayList<Object>();
						for( CartItemBindProductValue bindProduct : bindProducts){
							result2=new HashMap<String, Object>();
							Product product = productService.find( bindProduct.getId() );
							result2.put("goodsId", bindProduct.getId());
							result2.put("goodsPrice", bindProduct.getPrice());
							result2.put("goodsName", product.getName());
							result2.put("img", product.getImage());
							result2.put("count", cartItem.getQuantity() );
							result2.put("zongjia", bindProduct.getPrice().multiply( new BigDecimal( cartItem.getQuantity() ) ));
							bindlist.add(result2);
							decimal2=decimal2.add(bindProduct.getPrice());
						}
						mapPro.put("bind", bindlist);

					}
					
					//TODO get方法有问题，不执行查询
					Product product2 = cartItem.getProduct();
					Long[] l = new Long[1];
					l[0] = cartItem.getId();
					Cart cart2 = cartService.getPrice(userId,l);
		            BigDecimal effectivePrice = cartService.getEffectivePrice(cart2);
					
					result = new HashMap<String, Object>();
					result.put("goodsId", product2.getId());
					result.put("goodsName", product2.getName());
					result.put("goodsPrice", product2.getPrice());
					result.put("taocanPrice", product2.getPrice().add(decimal2));
					result.put("goodsMarketPrice", product2.getMarketPrice());
					result.put("effectivePrice", effectivePrice);
					result.put("goodsImagePath", product2.getThumbnail());
					result.put("goodsImage", product2.getProductImages());
					result.put("goodsStock", product2.getAvailableStock());
					int productQuantity = 0;
					if (cartItem.getQuantity() != null) {
						productQuantity += cartItem.getQuantity();
							if( cartItem.parentId == CartItem.type.mainproduct ){
								//若商品存在绑定促销，计算数量时，需要将绑定销售的数量合并
								productQuantity  +=  cartItem.getQuantity() * cartItem.getBindProductIds().size();
							}
					}
					result.put("count", cartItem.getQuantity());
					result.put("totalcount", productQuantity);
					
					mapPro.put("pro", result);
					
					
					//满赠
					String sqlmanzeng="SELECT b.f_id id,b.f_name goodsName,b.f_image image,c.f_minimum_price minPrice,c.f_title title,c.f_name titleName FROM t_promotion_gift a LEFT JOIN t_promotion c ON a.f_promotions=c.f_id  LEFT JOIN t_product b ON a.f_gifts=b.f_id WHERE a.f_promotions = ( " +
							"SELECT a.f_id promotionId FROM t_promotion a LEFT JOIN t_promotion_bind c ON a.f_promotion_type = c.f_promotions WHERE a.f_id IN ( " +
							"SELECT b.f_promotions FROM t_product a LEFT JOIN t_product_promotion b ON a.f_id = b.f_product WHERE a.f_id = :goodsId ) AND a.f_promotion_type = 3 AND a.f_end_date > NOW()  and a.f_begin_date < NOW() );";
					
					listmanzeng = appIndexDao.sql(product2.getId(),sqlmanzeng);
					
					if(listmanzeng.size()>0){
						mapPro.put("manzeng",listmanzeng.get(0));
					}
					
					
					//单品促销
					String sqldanpin="SELECT a.f_id promotionId,a.f_name titleName, a.f_title title FROM t_promotion a LEFT JOIN t_promotion_bind c ON a.f_promotion_type = c.f_promotions WHERE a.f_id IN (" +
							"SELECT b.f_promotions FROM t_product a LEFT JOIN t_product_promotion b ON a.f_id = b.f_product WHERE a.f_id = :goodsId AND a.f_promotion_type = 1 AND a.f_end_date > NOW()  and a.f_begin_date < NOW())";
					
					listdanpin= appIndexDao.sql(product2.getId(),sqldanpin);
					
					if(listdanpin.size()>0){
						mapPro.put("danpin",listdanpin.get(0));
					}
					
					//买赠
					String sqlmaizeng="SELECT b.f_id id,b.f_name goodsName, b.f_product_images img,c.f_title title,c.f_name titleName FROM t_promotion_gift a LEFT JOIN t_promotion c ON a.f_promotions=c.f_id LEFT JOIN t_product b ON a.f_gifts=b.f_id WHERE a.f_promotions = ( " +
							"SELECT a.f_id promotionId FROM t_promotion a LEFT JOIN t_promotion_bind c ON a.f_promotion_type = c.f_promotions WHERE a.f_id IN ( " +
							"SELECT b.f_promotions FROM t_product a LEFT JOIN t_product_promotion b ON a.f_id = b.f_product WHERE a.f_id = :goodsId ) AND a.f_promotion_type = 4 AND a.f_end_date > NOW()  and a.f_begin_date < NOW());";
					listmaizeng= appIndexDao.sql(product2.getId(),sqlmaizeng);
					
					if(listmaizeng.size()>0){
						for (Object  object : listmaizeng) {
							result3= new HashMap<String, Object>();
							Map<String, Object> m=(Map<String, Object>)object;
							result3.put("id", String.valueOf(m.get("id")));
							result3.put("goodsName", String.valueOf(m.get("goodsName")));
							result3.put("img", String.valueOf(m.get("img")));
							result3.put("title", String.valueOf(m.get("title")));
							result3.put("titleName", String.valueOf(m.get("titleName")));
							result3.put("zengcount", cartItem.getQuantity());
							list2.add(result3);
						}
						mapPro.put("maizeng",list2);
					}
					
					//买减
 					String sqlmaijian="SELECT a.f_title title,a.f_price_expression expression,a.f_name titleName FROM t_promotion a WHERE a.f_id = ( SELECT a.f_id promotionId FROM t_promotion a WHERE a.f_id IN" +
 							" ( SELECT b.f_promotions FROM t_product a LEFT JOIN t_product_promotion b ON a.f_id = b.f_product WHERE a.f_id = :goodsId ) AND a.f_promotion_type = 2 " +
 							"AND a.f_end_date > NOW()  and a.f_begin_date < NOW());";
					listmaijian= appIndexDao.sql(product2.getId(),sqlmaijian);
					if(listmaijian.size()>0){
						result3= new HashMap<String, Object>();
						Map<String,Object> m = (Map<String, Object>) listmaijian.get(0);
						//计算优惠后的价格
						//TODO 计算有问题，出现小数
						BigDecimal decimal = BigDecimal.ZERO;
						String expression=String.valueOf(m.get("expression"));
						String title=String.valueOf(m.get("title"));
						String titleName=String.valueOf(m.get("titleName"));
						Binding binding = new Binding();
						binding.setVariable("quantity", cartItem.getQuantity());
						binding.setVariable("price", product2.getPrice());
						GroovyShell groovyShell = new GroovyShell(binding);
						decimal = new BigDecimal(groovyShell.evaluate(String.valueOf(expression)).toString());
						result3.put("youhuijia", decimal);
						result3.put("title", title);
						result3.put("titleName", titleName);
						mapPro.put("manjian",result3);
					}
					
					list.add(mapPro);
				}
			}
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		}
		
		mapResult.put(UnivParameter.DATA, list);
		
		return mapResult;
	
	}

	@Override
	public Map<String, Object> getOrdersDetail(String orderSn) {
		return appIndexDao.getOrdersDetail(orderSn);
	}

	@Override
	public int findFavorite(Long userId,Long productId) {
		return appIndexDao.findFavorite(userId,productId);
	}

	@Override
	public Map<String, Object> addFavorite(Long userId, Long productId) {
		return appIndexDao.addFavorite(userId,productId);
	}

	@Override
	public Map<String, Object> dellFavorite(Long userId, Long[] productIds) {
		return appIndexDao.dellFavorite(userId,productIds);
	}
	
	@SuppressWarnings("unused")
	public Map<String, Object> getReserve(Long userId) {

		Map<String, Object> resultData = new HashMap<String, Object>();

		List<Map<String, Object>> orderdatas = new ArrayList<>();
		int d = 0;
		try {
			List<Filter> filters = new ArrayList<Filter>();
			filters.add(Filter.eq("type", Order.Type.reserve));
			filters.add(Filter.eq("member", memberService.find(userId)));
			List<Order> orders = orderService.findList(null, filters, null);
			for (Order order : orders) {
				if(order.getOrderItems().size()>0){
					Product product=order.getOrderItems().get(0).getProduct();
					Map<String, Object> orderdata = new HashMap<String, Object>();
					
					if(product.getPreOrderTime() == null){
						d = 0;
					}else{
						long preDate =format.parse( format.format(product.getPreOrderTime())).getTime();
						long date=format.parse(format.format(new Date())).getTime();
						
						d = (int) (Math.abs(preDate-date) / (1000 * 60 * 60 * 24));
					}
					
					orderdata.put("ordersId", order.getId().toString());
					orderdata.put("goodsId", product.getId().toString());
					orderdata.put("goodsName", product.getName());
					orderdata.put("goodsImagePath", product.getImage());
					if(order.getAmountPaid().subtract(BigDecimal.ZERO) == BigDecimal.ZERO){
						orderdata.put("expirce", format.format(order.getExpire()));
					}
					orderdata.put("goodsPrice", product.getPrice());
					orderdata.put("buyDate", format.format(product.getPreOrderTime()));
					orderdata.put("deposit", order.getAmountPaid());
					orderdata.put("shijideposit", order.getOrderItems().get(0).getProduct().getPreOrderPrice());
//					orderdata.put("reservationType", order.getStatus().toString());
					orderdata.put("changeDate", format.format(order.getModifyDate()));
					orderdata.put("orderDate", format.format(order.getCreateDate()));
					orderdata.put("orderSn", order.getSn());
					orderdatas.add(orderdata);
				}
			}
			// 存放正确的返回参数CODE--1
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return resultData;
		}
		// 存放最终的执行结果
		resultData.put(UnivParameter.DATA, orderdatas);
		return resultData;
	}

	public Map<String, Object> cartCount(Long userId) {

		Map<String, Object> resultData = new HashMap<String, Object>();

		Map<String, Object> datas = new HashMap<String, Object>();

		try {
			Member member = memberService.find(userId);
			Cart cart = member.getCart();
			Integer productQuantity = 0;
			if ( null !=cart && cart.getCartItems() != null) {
				for (CartItem cartItem : cart.getCartItems()) {
					if (cartItem.getQuantity() != null) {
						productQuantity += cartItem.getQuantity();
						if( cartItem.parentId == CartItem.type.mainproduct ){
							//若商品存在绑定促销，计算数量时，需要将绑定销售的数量合并
							productQuantity  +=  cartItem.getQuantity() * cartItem.getBindProductIds().size();
						}
					}
				}
			}
			datas.put("quantity", productQuantity);
			// 存放正确的返回参数CODE--1
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return resultData;
		}
		// 存放最终的执行结果
		resultData.put(UnivParameter.DATA, datas);
		return resultData;
	}
public Map<String, Object> preOrderCheck(Long productId,Long userId){

		Map<String, Object> resultData = new HashMap<String, Object>();

		Map<String, Object> datas = new HashMap<String, Object>();
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		List<Object> list=new ArrayList<Object>();
		//默认收货人信息
		Member member=memberService.find(userId);
		Receiver defaultReceiver = receiverService.findDefault(member);
		try {
			Product product = productService.find(productId);			
			if(product==null){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "商品不存在");
				return resultData;
			}
			
			if(null == member){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "会员不存在");
				return resultData;
			}
			
		/*	if(product.getIsOutOfStock()){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "该商品缺货中");
				return resultData;
			}*/

			if(!product.getIsMarketable()||!product.getIsList()){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "该商品状态异常");
				return resultData;
			}
			
			if(!product.getIsPreOrder()){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "商品非预定商品");
				return resultData;
			}
			if(defaultReceiver != null ){
				map2.put("consignee", defaultReceiver.getConsignee());
				map2.put("id", defaultReceiver.getId());
				map2.put("xiangxidizhi", defaultReceiver.getAreaName() + defaultReceiver.getAddress());
				map2.put("phone", defaultReceiver.getPhone());
				datas.put("receiver", map2);
			}
			
			map3.put("goodsName", product.getName());
			map3.put("goodsImagePath", product.getThumbnail());
			map3.put("earnestMoney", product.getPreOrderPrice());
			map3.put("goodsPrice", product.getPrice());
			map3.put("goodsSubhead", product.getCaption());
			map3.put("goodsId", product.getId());
			list.add(map3);
			
			//TODO
			BigDecimal freight = BigDecimal.ZERO;
			datas.put("orderamount", product.getPreOrderPrice());
			datas.put("freight", freight);
			datas.put("orderPrice", product.getPrice());
			datas.put("preOrderPrice", product.getPreOrderPrice());
			
			datas.put("proList", list);
			// 存放正确的返回参数CODE--1
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return resultData;
		}
		// 存放最终的执行结果
		resultData.put(UnivParameter.DATA, datas);
		return resultData;
	}
	
	@Transactional
	public Map<String, Object> preOrderCreate(Long productId,Long userId,Long paymentMethodId, Long shippingMethodId,Long receiverId,String invTitle,String recommended){

		Map<String, Object> resultData = new HashMap<String, Object>();

		Map<String, Object> datas = new HashMap<String, Object>();

		try {
			Member member = memberService.find(userId);
			Product product = productService.find(productId);			
			if(product==null){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "商品不存在");
				return resultData;
			}
			
			if( member.getPhone() == recommended ){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "推荐人不可为本人");
				return resultData;
			}
			
			if(!product.getIsMarketable()||!product.getIsList()){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "该商品状态异常");
				return resultData;
			}
			
			if(!product.getIsPreOrder()){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "商品非预定商品");
				return resultData;
			}
			
			if(!product.hasPrepreOrderIng()){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "商品已发售");
				return resultData;
			}
			PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
			if( null == paymentMethod){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "请选择支付方式");
				return resultData;
			}

			// 发票 抬头
			Invoice invoice = StringUtils.isEmpty(invTitle) ? null :new Invoice(
					invTitle, null);

			Order order = orderService.createOrder(Order.Type.reserve, product, memberService.find(userId),1, receiverService.find(receiverId), paymentMethod, shippingMethodService.find(shippingMethodId), null, invoice, null, null,recommended);
			datas.put("orderSn", order.getSn());
			datas.put("amount", order.getAmount());
			datas.put("istoPay", true);
			
			// 存放正确的返回参数CODE--1
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return resultData;
		}
		// 存放最终的执行结果
		resultData.put(UnivParameter.DATA, datas);
		return resultData;
	}

	
	public Map<String, Object> organizationList(List<Organization> list) {
		return appIndexDao.organizationList(list);
	}

	@Transactional
	public Map<String, Object> createGrabOrder(Long productId,Long userId,Long grabId,Long paymentMethodId,Long shippingMethodId,Long receiverId,
			Long[] bindProductIds,Long bindId,String invTitle,String invCont,String memo,String recommended,Long organizationId,String date,Long grabSeckillLogId){

		Map<String, Object> resultData = new HashMap<String, Object>();

		Map<String, Object> datas = new HashMap<String, Object>();

		try {
			Member member = memberService.find(userId);
			Product product = productService.find(productId);			
			if(product==null){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "商品不存在");
				return resultData;
			}
			
			if( member.getPhone() == recommended ){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "推荐人不可为本人");
				return resultData;
			}
			
			if(product.getIsOutOfStock()){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "该商品缺货中");
				return resultData;
			}

			if(!product.getIsMarketable()||!product.getIsList()){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "该商品状态异常");
				return resultData;
			}
			
			// 收货地址信息
			Receiver receiver = null;
			// 物流配送方式
			ShippingMethod shippingMethod = null;
			
			// 自提门店
			Organization organization = null;
			shippingMethod = shippingMethodService.find(shippingMethodId);
			if (shippingMethodId == 1) {
				receiver = receiverService.find(receiverId);
				if (null == receiver) {
					// 收货地址不存在
					resultData.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
					resultData.put(UnivParameter.ERRORMESSAGE,"收货地址不存在");
					return resultData;
				}
				
				if (null == shippingMethod) {
					resultData.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
					resultData.put(UnivParameter.ERRORMESSAGE,"支付方式不存在");
					return resultData;
				}
			}
			organization = organizationService.find( organizationId );
	        if( shippingMethodId==2 ){
	        	if ( null == date ) {
            		resultData.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
            		resultData.put(UnivParameter.ERRORMESSAGE,"请选择自提时间");
					return resultData;
				}
            	if( null == organization ){
            		resultData.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
            		resultData.put(UnivParameter.ERRORMESSAGE,"请选择自提门店");
					return resultData;
            	}
            	
            }	
			
			
			if(shippingMethodId == 2 && null == organizationId ){
				resultData.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE,"请选择自提门店");
				return resultData;
			}
			
			PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
			if( null == paymentMethod){
				resultData.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE,"请选择支付方式");
				return resultData;
			}
			
			Order order =orderService.createGrabOrder(product, member,
					grabSeckillService.find(grabId), receiver, 
					paymentMethod, shippingMethodService.find(shippingMethodId), 
					invTitle, memo, bindProductIds, promotionService.find(bindId),recommended,organization,date,grabSeckillLogId);

			if( order.getAmountPayable().compareTo( BigDecimal.ZERO ) > 0 ){
				datas.put("istoPay" , true );
	        }else{
	        	datas.put("istoPay" , false );
	        }
			if( paymentMethodId == 1 ){
				datas.put("istoPays" , true );
			}else{
				datas.put("istoPays" , false );
			}
			datas.put("orderSn", order.getSn());
			datas.put("amount", order.getAmount());
			
			// 存放正确的返回参数CODE--1
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return resultData;
		}
		// 存放最终的执行结果
		resultData.put(UnivParameter.DATA, datas);
		return resultData;
	}

	public Map<String, Object> preOrderDetail(String orderSn){

		Map<String, Object> resultData = new HashMap<String, Object>();
		List<Object> list=new ArrayList<Object>();
		Map<String, Object> datas = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			Order order = orderService.findBySn(orderSn);			
			if(order==null){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "订单不存在");
				return resultData;
			}
			
			if(!order.getType().equals(Order.Type.reserve)){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "该订单非预定单");
				return resultData;
			}

			datas.put("orderSn", order.getSn());
			datas.put("orderDate", format.format(order.getCreateDate()));
			
			long preDate =format.parse( format.format(order.getOrderItems().get(0).getProduct().getPreOrderTime())).getTime();
			long date=format.parse(format.format(new Date())).getTime();
			
			int d = (int) (Math.abs(preDate-date) / (1000 * 60 * 60 * 24));
			Product product=order.getOrderItems().get(0).getProduct();
			datas.put("preDate", d);
			datas.put("consignee", order.getConsignee());
			datas.put("phone", order.getPhone());
			datas.put("areaName", order.getArea().getName());
			datas.put("address", order.getAddress());
			datas.put("price", order.getAmount());
			if(order.getInvoice()!=null){
				datas.put("invoiceTitle", order.getInvoice().getTitle());
				datas.put("invCont", order.getInvoice().getTitle());
			}else{
				datas.put("invoiceTitle", "");
			}
			datas.put("shijideposit", order.getOrderItems().get(0).getProduct().getPreOrderPrice());
			datas.put("prePrice", order.getAmountPaid());
			list.add(datas);
			
			map.put("pro", list);
			
			datas = new HashMap<String, Object>();
			list = new ArrayList<Object>();
			datas.put("id", product.getId());
			datas.put("caption", product.getCaption());
			datas.put("quantity", order.getQuantity());
			datas.put("name", product.getName());
			datas.put("price", product.getPrice());
			datas.put("thumbnail", product.getThumbnail());
			
			list.add(datas);
			map.put("proItem", list);
			
			datas = new HashMap<String, Object>();
			list = new ArrayList<Object>();
			
			if(order.getShippingMethod().getId() == 2){
				Organization organization = order.getOrganization();
				datas.put( "organization", organization.getName() );
				datas.put( "organizationAddress", organization.getArea().getName()+organization.getAddress() );
				datas.put( "organizationTel", organization.getTel() );
				datas.put( "collectTime", order.getCollectTime() );
				list.add(datas);
				map.put("ziti", list);
			}else{
				map.put("ziti", new ArrayList<Result>());
			}
			
			
			// 存放正确的返回参数CODE--1
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return resultData;
		}
		// 存放最终的执行结果
		resultData.put(UnivParameter.DATA, map);
		return resultData;
	}
	
	public Map<String, Object> godMoneyAvail(Long userId){

		Map<String, Object> resultData = new HashMap<String, Object>();

		Map<String, Object> datas = new HashMap<String, Object>();

		try {
			Member member = memberService.find(userId);
			if(member==null){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "会员不存在");
				return resultData;
			}
			
			datas.put("godMoney", member.getGodMoney());
			
			// 存放正确的返回参数CODE--1
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return resultData;
		}
		// 存放最终的执行结果
		resultData.put(UnivParameter.DATA, datas);
		return resultData;
	}
	
	/**
	 * 
	 * 神币记录.
	 * author: 王凯斌
	 *   date: 2015-12-10 下午5:38:21
	 * @param userId 用户id
	 * @return
	 */
	public Map<String, Object> godMoneyLog(Long userId){

		Map<String, Object> resultData = new HashMap<String, Object>();

		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

		try {
			Member member = memberService.find(userId);
			if(member==null){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "会员不存在");
				return resultData;
			}
			List<Filter> filters = new ArrayList<Filter>();
			filters.add(Filter.eq("member", member));
			for(GodMoneyLog godMoneyLog:godMoneyLogService.findList(null, filters, null)){
				
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("type", godMoneyLog.getType().toString());
				item.put("credit", godMoneyLog.getCredit());
				item.put("debit", godMoneyLog.getDebit());
				item.put("date", godMoneyLog.getCreateDate());
				datas.add(item);
			}
			
			
			
			// 存放正确的返回参数CODE--1
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return resultData;
		}
		// 存放最终的执行结果
		resultData.put(UnivParameter.DATA, datas);
		return resultData;
	}
	
	/**
	 * 
	 * 积分记录.
	 * author: 王凯斌
	 *   date: 2015-12-10 下午5:38:21
	 * @param userId 用户id
	 * @return
	 */
	public Map<String, Object> pointLog(Long userId){

		Map<String, Object> resultData = new HashMap<String, Object>();

		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

		try {
			Member member = memberService.find(userId);
			if(member==null){
				resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				resultData.put(UnivParameter.ERRORMESSAGE, "会员不存在");
				return resultData;
			}
			List<Filter> filters = new ArrayList<Filter>();
			filters.add(Filter.eq("member", member));
			for(PointLog pointLog:pointLogService.findList(null, filters, null)){
				
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("type", pointLog.getType().toString());
				item.put("credit", pointLog.getCredit());
				item.put("debit", pointLog.getDebit());
				item.put("date", pointLog.getCreateDate());
				datas.add(item);
			}
			// 存放正确的返回参数CODE--1
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return resultData;
		}
		// 存放最终的执行结果
		resultData.put(UnivParameter.DATA, datas);
		return resultData;
	}

	@Override
	public Map<String, Object> sendPhoneCode(String phoneNumber) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> codeMap= new HashMap<String,Object>();
		 //创建随机验证码
	   	String code = RandomValueUtils.randomNumberValue(4);
	   	Setting setting =SystemUtils.getSetting();
	   	//获取短信服务器IP
	   	String smsHost = setting.getSmsHost();
	   	//获取短信服务器端口
	   	String smsPort = setting.getSmsPort();
	   	//获取短信主账号的ID 
	   	String smsSn = setting.getSmsSn();
	   	//获取短信主账号的token
	   	String smsKey= setting.getSmsKey();	
	   	//获取短信初始化应用ID
	   	String smsApplication =setting.getSmsApplication();
	  /* 	//获取短信发送模版
	   	String smsTemplate	=setting.getSmsTemplate();*/
	   	
	   	String statusCode = setting.getStatusCode();
	   	HashMap<String, Object> result = null;
	   	CCPRestSDK restAPI= new CCPRestSDK();
			restAPI.init(smsHost,smsPort);// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
			restAPI.setAccount(smsSn, smsKey);// 初始化主帐号和主帐号TOKEN
			restAPI.setAppId(smsApplication);// 初始化应用ID
			/*result = restAPI.sendTemplateSMS(phoneNumber,"1" ,new String[]{smsTemplate+code,"5"});*/

            result = restAPI.sendTemplateSMS(phoneNumber, CommonParameter.SMSTEMPLATE_VERI_CODE ,new String[]{code,"5"});
			if(statusCode.equals(result.get("statusCode"))){
				codeMap.put("code", code);
				//存放正确的返回参数CODE--1
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
				mapResult.put(UnivParameter.DATA,codeMap);
			}else{
				//存放错误的返回参数CODE--0
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
//				mapResult.put(UnivParameter.ERRORMESSAGE, "对不起，发送失败 ！错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
				mapResult.put(UnivParameter.ERRORMESSAGE, "一天的验证码上限已经达到");
				return mapResult;
			}
		return mapResult;
	}
	
	@Override
	public Map<String, Object> getKeyWordsList() {
		Map<String, Object> resultData = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();;
		try {
			Page<KeyWords>	KeyWords=keyWordsService.findPage();
			if(KeyWords.getContent()!=null){
				for(KeyWords 	k	:	KeyWords.getContent()){
					list.add(k.getName());
				}
			}else{
				list.clear();
			}
			resultData.put(UnivParameter.DATA, list);
			// 存放正确的返回参数CODE--1
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			resultData.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			resultData.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return resultData;
		}
	return resultData;
	}

	@Override
	public Map<String, Object> grabSkillList(Long userId,int pageLoadType, int pageRowsCount, String createDate) {
		return appIndexDao.grabSkillList(userId,pageLoadType,pageRowsCount,createDate);
	}

	
	
	@Transactional
	@Override
	public Map<String, Object> favorite(Long userId, Long[] productIds, int type) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if(type == 1){
				//新增
				for (Long productId : productIds) {
					if (this.findFavorite(userId,productId)>0) {
						result.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
					}else{
						Product product = productService.find( productId );
						if (product == null) {
							// 崩溃性逻辑处理错误CODE-0
							result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
							// 加载逻辑处理层崩溃性MESSAGE
							result.put(UnivParameter.ERRORMESSAGE, "该商品不存在");
							return result;
						}
						
						if (Member.MAX_FAVORITE_COUNT != null
								&& this.findFavorite(userId,null) >= Member.MAX_FAVORITE_COUNT) {
							// 崩溃性逻辑处理错误CODE-0
							result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
							// 加载逻辑处理层崩溃性MESSAGE
							result.put(UnivParameter.ERRORMESSAGE, "超过收藏数量");
							return result;
						}
						Map<String, Object> map=appIndexDao.addFavorite(userId,productId);
						if (UnivParameter.DATA_ERRORCODE.equals(map.get(
								UnivParameter.CODE).toString())) {
							// 崩溃性数据处理错误CODE-1
							result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
							// 加载数据处理层崩溃性MESSAGE
							result.put(UnivParameter.REASON,
							map.get(UnivParameter.ERRORMESSAGE).toString());
						} else {
							result.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
							// 数据处理层正确性结果判断--加载正确的数据结果集
							result.put(UnivParameter.RESULT, "成功收藏");
						}
					}
				}
				
			}else{
				Map<String, Object> map=appIndexDao.dellFavorite(userId,productIds);
				if (UnivParameter.DATA_ERRORCODE.equals(map.get(
						UnivParameter.CODE).toString())) {
					// 崩溃性数据处理错误CODE-1
					result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
					// 加载数据处理层崩溃性MESSAGE
					result.put(UnivParameter.ERRORMESSAGE,
					"删除失败");
				} else {
					result.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
					// 数据处理层正确性结果判断--加载正确的数据结果集
					result.put(UnivParameter.RESULT, "删除成功");
				}
			}
		} catch (Exception e) {
			// 清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();

			// 崩溃性逻辑处理错误CODE-0
			result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);

			// 加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return result;
		}
		return result;
	}
	
	@Transactional
	@Override
	public Map<String, Object> createCartOrder(Long userId, Long receiverId,String cartToken,
			Long paymentMethodId, Long shippingMethodId, Long[] couponCodes,
			String invoiceTitle, BigDecimal godMoneyNum, String recommended, Long organizationId,String date) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		try {
			Member member=memberService.find(userId);
			Cart cart=cartService.getCurrent(member.getUsername());
			if (!cart.getToken().equals(cartToken)) {
				result.put(UnivParameter.CODE,UnivParameter.CART_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"购物车发生变动");
				return result;
	        }
			if (null == cart ||  cart.isEmpty()) {
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"购物车不存在");
				return result;
			}
			if(member.getPhone() == recommended){
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"推荐人不可为本人");
				return result;
			}
			
			Set<CartItem> cartItems=cart.getCartItems();
			Map<Long, Integer> map = new HashMap<Long, Integer>();
			Product product = null;
			List<CartItemBindProductValue> cartItemBindProductValues = null;
			/**
			 * 循环统计 每种商品的 所需要的库存
			 */
			for (CartItem cartItem : cartItems) {

				if (null != cartItem && cartItem.getIsSelect()) {
					product = cartItem.getProduct();

					if (map.containsKey(product.getId())) {
						map.put(product.getId(),
								cartItem.getQuantity() + map.get(product.getId()));
					} else {
						map.put(product.getId(), cartItem.getQuantity());
					}
					/** 存在绑定商品，也需要统计绑定的商品 **/
					if (cartItem.getParentId().equals(CartItem.type.mainproduct)) {
						cartItemBindProductValues = cartItem.getBindProductIds();
						for (CartItemBindProductValue bindProduct : cartItemBindProductValues) {
							if (map.containsKey(bindProduct.getId())) {
								map.put(bindProduct.getId(), cartItem.getQuantity()
										+ map.get(bindProduct.getId()));
							} else {
								map.put(bindProduct.getId(), cartItem.getQuantity());
							}
						}
					}
				}
			}
			Long[] productIds = map.keySet().toArray(new Long[0]);

			List<Product> products = productService.findList(productIds);
			/**
			 * 循环判断库存是否充足
			 */
			List<String> productNames = new ArrayList<String>();
			for (Product product1 : products) {
				if (map.get(product1.getId()) > product1.getAvailableStock()) {
					productNames.add(product1.getName());
				}
			}

			if (productNames.size() > 0) {
				// 存在商品库存不足
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"商品不存不足");
				return result;
			}
			// end 库存是否充足
			
			// 收货地址信息
			Receiver receiver = null;
			// 物流配送方式
			ShippingMethod shippingMethod = null;
			
			// 自提门店
			Organization organization = null;
			PaymentMethod paymentMethod = paymentMethodService
					.find(paymentMethodId);
			shippingMethod = shippingMethodService.find(shippingMethodId);
			if (shippingMethodId == 1) {
				receiver = receiverService.find(receiverId);
				if (null == receiver) {
					// 收货地址不存在
					result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
					result.put(UnivParameter.ERRORMESSAGE,"收货地址不存在");
					return result;
				}
				
				if (null == shippingMethod) {
					result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
					result.put(UnivParameter.ERRORMESSAGE,"支付方式不存在");
					return result;
				}
			}
				organization = organizationService.find( organizationId );
	            if( shippingMethodId==2 ){
	            	if ( null == date ) {
						result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
						result.put(UnivParameter.ERRORMESSAGE,"请选择自提时间");
						return result;
					}
	            	if( null == organization ){
						result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
						result.put(UnivParameter.ERRORMESSAGE,"请选择自提门店");
						return result;
	            	}
	            	
	            }
			
			// 校验使用的神币
			if (godMoneyNum != null && godMoneyNum.compareTo(BigDecimal.ZERO) < 0) {
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"神币不能为0");
				return result;
			}
			if (godMoneyNum != null
					&& godMoneyNum.compareTo(member.getGodMoney()) > 0) {
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"神币不足");
				return result;
			}
			
			// 发票 抬头
			Invoice invoice = StringUtils.isEmpty(invoiceTitle) ? null :new Invoice(
					invoiceTitle, null);

			// 保存订单信息
			Map<String, Object> mapResult = orderService.createCartOrder(Order.Type.general, cart,
					receiver, paymentMethod, shippingMethod, couponCodes, invoice,organization,date,
					godMoneyNum, null , recommended);
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				result.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.ERRORMESSAGE,
						mapResult.get(UnivParameter.REASON).toString());
			} else {
				// 崩溃性数据处理错误CODE-1
				result.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
				// 数据处理层正确性结果判断--加载正确的数据结果集
				Order order = (Order) mapResult.get(UnivParameter.DATA);
				if( order.getAmountPayable().compareTo( BigDecimal.ZERO ) > 0 ){
					map2.put("istoPay" , true );
		        }else{
		        	map2.put("istoPay" , false );
		        }
				if( paymentMethod.getId() == 1 ){
					map2.put("istoPays" , true );
				}else{
				    map2.put("istoPays" , false );
				}
//				if( order.getAmountPayable().compareTo( BigDecimal.ZERO ) > 0 && paymentMethod.getId() == 2  ){
//					map2.put("istoPays" , true );
//		        }else{
//		        	map2.put("istoPays" , false );
//		        }
				map2.put("orderSn" , order.getSn() );
				map2.put("amount", order.getAmountPayable());
				result.put(UnivParameter.DATA,map2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();

			// 崩溃性逻辑处理错误CODE-0
			result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);

			// 加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> praise(int type, Long id) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		int praise;
		try {
			switch (type) {
			case 1:
				Organization organization=organizationService.find(id);
				if(null == organization){
					mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE,"组织不存在");
					return mapResult;
				}
				praise=organization.getPraiseCount();
				organization.setPraiseCount(praise+1);
				organizationService.update(organization);
				break;
			case 2:
				Admin admin=adminService.find(id);
				if(admin == null){
					mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE,"店员不存在");
					return mapResult;
				}
				praise=admin.getPraiseCount();
				admin.setPraiseCount(praise+1);
				adminService.update(admin);
				break;
			default:
				break;
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
			return mapResult;
	}
	
	@Override
	public Map<String, Object> comment(HttpServletRequest request, int type,
			Long id, Long userId, String content) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		try {
            String ip = WebUtils.getIpAddr(request);
			Member member = memberService.find(userId);
			if(null == member){
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,"会员不存在");
				return mapResult;
			}
			Comment comment = new Comment();
			comment.setContent(content);
			comment.setIp(ip);
			comment.setIsShow(true);
			comment.setScore(1);
			comment.setMember(member);
			switch (type) {
			case 1:
				Organization organization=organizationService.find(id);
				if(null == organization){
					mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE,"组织不存在");
					return mapResult;
				}
				comment.setType(Comment.Type.organization);
				comment.setOrganization(organization);
				break;
			case 2:
				Admin admin=adminService.find(id);
				if(admin == null){
					mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE,"店员不存在");
					return mapResult;
				}
				comment.setType(Comment.Type.admin);
				comment.setAdmin(admin);
				break;
			default:
				break;
			}
			commentService.save(comment);
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
					
		} catch (Exception e) {
			e.printStackTrace();
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
			//存放最终的执行结果
			return mapResult;
	}

	@Override
	public Map<String, Object> getGoodsSaleSupport(String goodsId) {
		return appIndexDao.getGoodsSaleSupport(goodsId);
	}

	@Override
	public Map<String, Object> fengshenbang(int type) {
		return appIndexDao.fengshenbang(type);
	}

    @Override
    public Map<String, Object> reviewList(int type, int pageLoadType,int pageRowsCount, String id,String time) {
        return appIndexDao.reviewList(type, pageLoadType, pageRowsCount, id,time);
    }
    
    @Transactional
	@Override
	public Map<String, Object> setCurrency(String userId,
			BigDecimal godCurrencyOut, Long integralIn) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		try {
			Member member = memberService.find( Long.parseLong(userId));
			// 将兑换的积分String型转为Long型
			long pointHas = Long.valueOf(integralIn);
			// 获取现有积分
			long pointLast = member.getPoint();
			// 修改数据库中member表中神币和积分记录
			member.setPoint(pointLast + pointHas);
			member.setGodMoney(member.getGodMoney()
					.subtract(godCurrencyOut));
			memberService.update(member);
			// 创建一条积分添加记录
			PointLog pointLog = new PointLog();
			pointLog.setBalance(pointLast + pointHas);
			pointLog.setCredit(pointHas);
			pointLog.setDebit(0l);
			pointLog.setType(PointLog.Type.exchange);
			pointLog.setMember(member);
			pointLogService.save(pointLog);
			// 创建一条神币扣除记录
			GodMoneyLog godMoneyLog = new GodMoneyLog();
			BigDecimal godMoneyBalance = member.getGodMoney().subtract(
					godCurrencyOut);
			godMoneyLog.setBalance(godMoneyBalance);
			BigDecimal godMoneyDebit = godCurrencyOut;
			godMoneyLog.setDebit(godMoneyDebit);
			godMoneyLog.setCredit(BigDecimal.ZERO);
			godMoneyLog.setType(GodMoneyLog.Type.paymentPoint);
			godMoneyLog.setMember(member);
			godMoneyLogService.save(godMoneyLog);
			// 修改成功的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,
					UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.DATA,
					"积分兑换成功");
					
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
			//存放最终的执行结果
			return mapResult;
	}

	@Override
	public Map<String, Object> getShopComment(int commentType,
			int pageLoadType, int pageRowsCount, String shopId, String commentId) {
		return appIndexDao.getShopComment(commentType,pageLoadType,pageRowsCount,shopId,commentId);
	}

	@Override
	public Map<String, Object> packageOrder(String userId, Long id,
			Long productId) {
		Map<String , Object> data = new HashMap<String , Object>();
		
		Member member = memberService.find( Long.parseLong(userId) );
		if( null == member ){
			data.put("success", false);
			return data;
		}
		
		Product product = productService.find(productId);
		
		/**
		 *  判断库存是否充足
		 */
		
		if(product.getAvailableStock() < 0){
			//存在商品库存不足
			data.put("success", false);
			data.put("message" , "库存不足" );
			return data;
		}else{
			data.put("success", true);
			//库存充足 ， 返回 订单需要的数据
			Map<String , Object> receiver = null;
			
			//默认收货人信息
			Receiver defaultReceiver = receiverService.findDefault(member);
			
			//创建一个订单
			Order order = orderService.generate(Long.valueOf(userId),Order.Type.general,product,1,defaultReceiver, null, null, null, null, null, null);
	
			if( null == defaultReceiver ){
				data.put("receiver", null);
			}else{
				receiver = new HashMap<String , Object>();
				receiver.put("consignee", defaultReceiver.getConsignee());
				receiver.put("id", defaultReceiver.getId());
				receiver.put("xiangxidizhi", defaultReceiver.getAreaName() + defaultReceiver.getAddress());
				receiver.put("phone", defaultReceiver.getPhone());
				data.put("receiver", receiver);
			}
			
			//优惠劵信息
			Set<CouponCode> couponCodes = couponService.getAvailableCoupons(order, member);
			List<Map<String , Object>> couponCodesDatas = new ArrayList<Map<String , Object>>();
			for(CouponCode couponCode:couponCodes){
				Map<String , Object> couponCodesItem = new HashMap<String , Object>();
				couponCodesItem.put("couponName", couponCode.getCoupon().getName());
				couponCodesItem.put("couponCountPrice", couponCode.getCoupon().getCountPrice());
				couponCodesItem.put("couponCode", couponCode.getCode());
				couponCodesItem.put("couponStartDate", format.format(couponCode.getCoupon().getBeginDate()));
				couponCodesItem.put("couponEndDate", format.format(couponCode.getCoupon().getEndDate()));				
				couponCodesDatas.add(couponCodesItem);
			}
			data.put("couponCodes", couponCodesDatas);
			//神币
			data.put("godMoney", member.getGodMoney());
			//人民币对神币的比例
			Setting setting = SystemUtils.getSetting();
			String godRate = setting.getMoneyRechargeGodMoney();
			data.put("goodRate",  godRate );
			
			//商品金额
			data.put("orderPrice",  product.getPrice().add(contractPhoneNumberUserInfoService.find(id).getPhoneNumber().getPrice()));
			//应付金额
			data.put("orderamount",  product.getPrice().add(contractPhoneNumberUserInfoService.find(id).getPhoneNumber().getPrice()) );
			//运费
			data.put( "freight", order.getFreight());
			data.put( "orderSn", order.getSn());
		}
		
		return data;
	}
	
	@Transactional
	@Override
	public Map<String, Object> packageOrderCheck(Long userId,Long productId, Long receiverId,
			Long paymentMethodId, Long shippingMethodId, Long[] couponCodes,
			String invoiceTitle, BigDecimal godMoneyNum, Long contractInfoId,
			String memo, String recommended, Long organizationId, String collectTime) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> mapResult2= new HashMap<String,Object>();
		try {
			Product product = productService.find(productId);
			if (product == null) {
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,"商品不存在");
				return mapResult;
			}
			ContractPhoneNumberUserInfo contractPhoneInfo = contractPhoneNumberUserInfoService
					.find(contractInfoId);
			if (contractInfoId == null) {
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,"套餐不存在");
				return mapResult;
			}
			if (contractPhoneInfo.getPhoneNumber().getExpire()
					.before(new Date())) {
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,"您的选的手机号已经超过锁定时间，订单失效");
				return mapResult;
			}
			Member member = memberService.find(userId);
			
			if(member.getPhone() == recommended){
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,"推荐人不可为本人");
				return mapResult;
			}
			
			PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
			if (paymentMethod == null) {
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,"支付方式错误");
				return mapResult;
			}
			ShippingMethod shippingMethod = shippingMethodService.find(shippingMethodId);
			if (shippingMethod == null) {
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,"配送方式错误");
				return mapResult;
			}
			Receiver receiver = receiverService.find(receiverId);
			
			Organization organization = organizationService.find( organizationId );
			if( shippingMethodId==2 &&  (null == organization || null==collectTime)){
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,"缺少自提信息");
				return mapResult;
            }
			if( shippingMethodId == 1){
				if (receiver == null) {
    				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
    				mapResult.put(UnivParameter.ERRORMESSAGE,"用户地址错误");
    				return mapResult;
    			}
				
			}
			// 生成普通订单
			Set<CartItem> cartItems = new HashSet<CartItem>();
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setQuantity(1);
			cartItems.add(cartItem);
			cartItem.setIsSelect(true);

			Cart cart = new Cart();
			cart.setMember(member);
			cart.setCartItems(cartItems);

            // 发票 抬头
            Invoice invoice = StringUtils.isEmpty(invoiceTitle) ? null :new Invoice(invoiceTitle, null);
                    
			Order order = orderService.createOrder(userId,Order.Type.general, cart, product,
					1, receiver, paymentMethod, shippingMethod,
					couponCodes, invoice , godMoneyNum, memo, contractPhoneInfo , organization , collectTime);
			mapResult.put("amount", order.getAmountPayable());
            //在线支付 并且 需要支付的金额大于0，才需要调转到支付页面
            if( order.getAmountPayable().compareTo( BigDecimal.ZERO ) > 0){
            	mapResult.put("istoPay" , true );
            }else{
            	mapResult.put("istoPay" , false );
            }
            if(paymentMethod.getId() == 1  ){
            	mapResult.put("istoPays" , true );
            }else{
            	mapResult.put("istoPays" , false );
            }
            mapResult.put("orderSn", order.getSn());
            mapResult2.put(UnivParameter.DATA, mapResult);
            mapResult2.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
		} catch (Exception e) {
			e.printStackTrace();
			//存放错误的返回参数CODE--0
			mapResult2.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult2.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult2;
		}
			//存放最终的执行结果
			return mapResult2;
	}

	@Override
	public Map<String, Object> selectedPhone(Long phoneId, Long userId) {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//参数是否存在
	    	if(null == phoneId ){
	    		mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,"请选择手机号");
				return mapResult;
	    	}
			
			PhoneNumber phoneNumber = phoneNumerService.find(phoneId);
	    	//手机号是否存在 ，以及是否售出
	    	if(null == phoneNumber || phoneNumber.getIsSold().equals(PhoneNumber.PHONESTATE.sold)  ){
	    		mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,"手机号已售后，请重新选择号码");
				return mapResult;
	    	}
	    	
	    	map = phoneNumerService.selectedPhone(phoneNumber, userId);
	    	// 数据处理层崩溃性结果判断--1
			if (UnivParameter.DATA_ERRORCODE.equals(map.get(
					UnivParameter.CODE).toString())) {
				// 崩溃性数据处理错误CODE-1
				mapResult.put(UnivParameter.CODE,UnivParameter.LOGIC_COLLAPSECODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,mapResult.get(UnivParameter.ERRORMESSAGE).toString());
			} else {
				//存放正确的返回参数CODE--1
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
			}
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;
	}

	@Override
	public Map<String, Object> aftersales(Long memberId, int pageLoadType, int pageRowsCount, String createDate) {
		
		return appIndexDao.aftersales(memberId,pageLoadType,pageRowsCount,createDate);
	}

	@Override
	public Map<String, Object> returnOrderDetail(Long returnId) {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		List<Object> list2 = new ArrayList<Object>();
		List<Object> list4 = new ArrayList<Object>();
		Map<String, Object> mapreturnProduct = new HashMap<String, Object>();
		try {
			
			ReturnOrder returnOrder = returnOrderService.find(returnId);
			
			//参数是否存在
	    	if(null == returnOrder ){
	    		mapResult.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,"退单不能为空");
				return mapResult;
	    	}
	    	
	    	mapreturnProduct.put("describe", returnOrder.getDescribe());
	    	map.put("describe", mapreturnProduct);
	    	map.put("returnOrderType", returnOrder.getType().ordinal());
	    	list.add(map);
	    	
	    	map = new HashMap<String, Object>();
	    	mapreturnProduct = new HashMap<String, Object>();
	    	mapreturnProduct.put("shouhuoName", returnOrder.getContacter());
	    	mapreturnProduct.put("shouhuoAdress", returnOrder.getAddress());
	    	mapreturnProduct.put("shouhuoTell", returnOrder.getPhone());
	    	map.put("shouhuoren", mapreturnProduct);
	    	
	    	
	    	if(returnOrder.getMemberShippingMethod() != null){
	    		if(returnOrder.getMemberShippingMethod().equals("2")){
			    	mapreturnProduct = new HashMap<String, Object>();
			    	mapreturnProduct.put("orgName", returnOrder.getOrganization().getName());
			    	mapreturnProduct.put("orgAdress", returnOrder.getOrganization().getArea().getName()+returnOrder.getOrganization().getAddress());
			    	mapreturnProduct.put("orgTell", returnOrder.getOrganization().getTel());
			    	map.put("org", mapreturnProduct);
	    		}else{
		    		map.put("org", null);
		    	}
	    	}else{
	    		map.put("org", null);
	    	}
	    	list.add(map);
	    	//退货
	    	if(returnOrder.getType() == ReturnOrder.Type.returnProduct){
	    		map = new HashMap<String, Object>();
	    		mapreturnProduct = new HashMap<String, Object>();
//	    		mapreturnProduct.put("returnPriceActual", returnOrder.getReturnPriceActual());
//	    		mapreturnProduct.put("returnGodMoney", returnOrder.getReturnGodMoney());
//	    		mapreturnProduct.put("returnPoint", returnOrder.getReturnPoint());
//	    		mapreturnProduct.put("returnCouponCode", returnOrder.getReturnCouponCode());
//	    		mapreturnProduct.put("returnResult", returnOrder.getResult());
	    		mapreturnProduct.put("returnMemo", returnOrder.getMemo());
	    		map.put("chulijieguo", mapreturnProduct);
	    		list.add(map);
	    		
	    	//维修
	    	}else if(returnOrder.getType() == ReturnOrder.Type.fixProduct){
	    		map = new HashMap<String, Object>();
	    		mapreturnProduct = new HashMap<String, Object>();
//	    		mapreturnProduct.put("repairMemo", returnOrder.getRepairMemo());
//	    		mapreturnProduct.put("repairPrice", returnOrder.getRepairPrice());
	    		mapreturnProduct.put("returnResult", returnOrder.getResult());
	    		mapreturnProduct.put("returnMemo", returnOrder.getMemo());
	    		map.put("chulijieguo", mapreturnProduct);
	    		list.add(map);
	    	}else{
	    		map = new HashMap<String, Object>();
	    		mapreturnProduct = new HashMap<String, Object>();
	    		mapreturnProduct.put("returnMemo", returnOrder.getMemo());
	    		mapreturnProduct.put("returnResult", returnOrder.getResult());
	    		map.put("chulijieguo", mapreturnProduct);
	    		list.add(map);
	    	}
	    	
	    	
	    	
	    	//除退货外有物流信息
	    	if(true){
	    		map = new HashMap<String, Object>();
		    	if(returnOrder.getMemberTrackingNo() != null){
		    		mapreturnProduct = new HashMap<String, Object>();
		    		
		    		TrackingLog trackingLog=trackingLogService.findByTrackingId(returnOrder.getMemberTrackingNo());
		    		if(trackingLog != null){
		    			List<ResultItem> logistics=trackingLog.getTrackingInfo();
						for (ResultItem resultItem : logistics) {
							mapreturnProduct = new HashMap<String, Object>();
							mapreturnProduct.put("time", resultItem.getFtime());
							mapreturnProduct.put("context", resultItem.getContext());
							list2.add(mapreturnProduct);
						}
						map.put("memberwuliu", list2);
		    		}else{
			    		map.put("memberwuliu", null);
			    	}
		    		
		    	}else{
		    		map.put("memberwuliu", null);
		    	}
		    	
		    	if(returnOrder.getStoreTrackingNo() != null){
		    		mapreturnProduct = new HashMap<String, Object>();
		    		TrackingLog trackingLog=trackingLogService.findByTrackingId(returnOrder.getStoreTrackingNo());
		    		if(trackingLog != null){
		    			List<ResultItem> logistics=trackingLog.getTrackingInfo();
						for (ResultItem resultItem : logistics) {
							mapreturnProduct = new HashMap<String, Object>();
							mapreturnProduct.put("time", resultItem.getFtime());
							mapreturnProduct.put("context", resultItem.getContext());
							list2.add(mapreturnProduct);
						}
						map.put("orgwuliu", list2);
		    		}else{
			    		map.put("orgwuliu", null);
			    	}
		    	}else{
		    		map.put("orgwuliu", null);
		    	}
		    	
		    	List<ReturnOrderLog> list3 = new ArrayList<ReturnOrderLog>(returnOrder.getReturnOrderLogs());
		    	if(list3.size() > 0){
		    		for (ReturnOrderLog resultItem : list3) {
						mapreturnProduct = new HashMap<String, Object>();
						mapreturnProduct.put("content", resultItem.getContent());
						if(resultItem.getOperator() != null){
							mapreturnProduct.put("name", resultItem.getOperator().getName());
						}else{
							mapreturnProduct.put("name", null);
						}
						
						mapreturnProduct.put("time", format.format(resultItem.getCreateDate()));
						list4.add(mapreturnProduct);
					}
					map.put("chulijindu", list4);
		    	}
		    	list.add(map);
	    	}
			// 存放正确的返回参数CODE--1
	    	mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
	    	mapResult.put(UnivParameter.ERRORMESSAGE, UnivParameter.NULL);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		mapResult.put(UnivParameter.DATA, list);
		return mapResult;
	}
	
	@SuppressWarnings("unused")
	@Transactional
	@Override
	public Map<String, Object> returnOrder(Long[] itemIds, String typenum, Order order1, Long organizationId, String time, String tel, 
			String shouhuoren, String address,String[] imgs,String memo,int shippingType,String describe) {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		try {
			ReturnOrder returnOrder=new ReturnOrder();
			Order order = orderService.find(order1.getId());
	        StringBuffer content = new StringBuffer();
	        BigDecimal bigDecimal = BigDecimal.ZERO;

			int countLogs = 0;
	        for(Shipping shipping:order.getShippings()){
	        	countLogs+=shipping.getStockLogs().size();
	        }
	        
	        //若订单状态为未完成，则改成已经完成状态
	        if( order.getStatus() != Order.Status.completed ){
	            order.setStatus( Order.Status.completed );
	            order.setCompleteDate( new Date() );
	            orderService.update( order );
	        }
	        orderService.update( order );
	        //需要退货的商品种类和数量
	        Map<Product, Integer > stockMap = new HashMap<Product,Integer >();
	        //需要退货的订单项和数量
	        Map<OrderItem, Integer > orderItemMap = new HashMap<OrderItem,Integer >();

	        for(Long stockLogId:itemIds){
	            StockLog stockLog = stockLogService.find(stockLogId);
	            if(!stockMap.containsKey(stockLog.getProduct())){
	                stockMap.put(stockLog.getProduct(), 1);
	            }else{
	                stockMap.put(stockLog.getProduct(), stockMap.get(stockLog.getProduct())+1);
	            }
	        }

	        List<OrderItem> orderItems = order.getOrderItems();

			returnOrder.setOrder(order);

	        Integer returnCount = 0;
			switch(typenum){
				case "1":
	                content.append( "申请退货。" );
					returnOrder.setType(ReturnOrder.Type.returnProduct);
					//所有发货项总数 不等于 勾选退货项总数 ， 表示只退部分商品
					
					//需要退回该用户的所有金额
                    BigDecimal returnPrice = BigDecimal.ZERO;
                    //需要退回给用户的所有积分
                    Long returnPoint = 0l;
                    
                    if( order.getType() == Order.Type.exchange ){
                        //积分订单 只退积分
                        for( OrderItem orderItem : orderItems ){
                            returnCount = stockMap.get( orderItem.getProduct() );
                            //map中不存在该商品就退出
                            if( null == returnCount ){
                                continue;
                            }
                            returnPoint = returnPoint + orderItem.getProduct().getExchangePoint() * returnCount;
                            orderItemMap.put( orderItem , returnCount );
                            
                            returnOrder.setReturnPrice( BigDecimal.ZERO );
                            returnOrder.setReturnPriceActual( BigDecimal.ZERO );
                            returnOrder.setReturnGodMoney( BigDecimal.ZERO );
                            returnOrder.setReturnPoint( returnPoint );
                        }
                    }else{
                    	for( OrderItem orderItem : orderItems ){
                            returnCount = stockMap.get( orderItem.getProduct() );
                            //map中不存在该商品就退出
                            if( null == returnCount ){
                                continue;
                            }
                            
                            if( orderItem.getReturnPrice() != null){
                                bigDecimal = orderItem.getReturnPrice();
                            }
                            //计算需要退回的金额
                            returnPrice = returnPrice.add( bigDecimal.multiply( new BigDecimal(returnCount) ) );
                            orderItemMap.put( orderItem , returnCount );
                        }
                        returnOrder.setReturnPrice(returnPrice);
                    }
                    
					for(Long stockLogId:itemIds){
						StockLog stockLog = stockLogService.find(stockLogId);
						stockLog.setState("7");
						stockLogService.update(stockLog);
					}
					break;
				case "2":
	                content.append( "申请换货。" );
					returnOrder.setType(ReturnOrder.Type.changeProduct);
					for(Long stockLogId:itemIds){
						StockLog stockLog = stockLogService.find(stockLogId);
						stockLog.setState("7");
						stockLogService.update(stockLog);
					}

	                for( OrderItem orderItem : orderItems ){
	                    returnCount = stockMap.get( orderItem.getProduct() );
	                    //map中不存在该商品就退出
	                    if( null == returnCount ){
	                        continue;
	                    }
	                    orderItemMap.put( orderItem , returnCount );
	                }
					break;	
				case "3":
	                content.append( "申请维修。" );
					returnOrder.setType(ReturnOrder.Type.fixProduct);
					for(Long stockLogId:itemIds){
						StockLog stockLog = stockLogService.find(stockLogId);
						stockLog.setState("7");
						stockLogService.update(stockLog);
					}
					for( OrderItem orderItem : orderItems ){
	                    returnCount = stockMap.get( orderItem.getProduct() );
	                    //map中不存在该商品就退出
	                    if( null == returnCount ){
	                        continue;
	                    }
	                    orderItemMap.put( orderItem , returnCount );
	                }
			}
			
			switch (shippingType) {
			case 1:
				returnOrder.setStoreShippingMethod("线上物流");
				break;
			case 2:
				returnOrder.setStoreShippingMethod("线下门店");
				break;
			default:
				break;
			}
			
			returnOrder.setOrganization(organizationService.find(organizationId));
			returnOrder.setDescribe(describe);
			returnOrder.setOrderDate(format2.parse(time));
			returnOrder.setStatus(ReturnOrder.Status.pendingReturn);
			returnOrder.setMember(order.getMember());
			returnOrder.setPhone(tel);
			returnOrder.setContacter(shouhuoren);
			returnOrder.setAddress(address);
			List<String> imgList = Arrays.asList( imgs );
			returnOrder.setImage(imgList);
			
            List<String> list = new ArrayList<String>();
            list.add( memo );
			returnOrder.setMemo(  list );
			returnOrder.setStockLogs(new HashSet<StockLog>(stockLogService.findList(itemIds)));

			returnOrderService.save(returnOrder);
			
			Iterator<Entry<OrderItem, Integer >> iterator = orderItemMap.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<OrderItem, Integer> pair = iterator.next();
	            OrderItem productOrderItem = pair.getKey();
				ReturnOrderItem returnOrderItem = new ReturnOrderItem();
				returnOrderItem.setName(productOrderItem.getName());
				returnOrderItem.setSn(productOrderItem.getSn());
				returnOrderItem.setType(productOrderItem.getType());
				returnOrderItem.setWeight(productOrderItem.getWeight());
				returnOrderItem.setQuantity(pair.getValue());
				returnOrderItem.setProduct(productOrderItem.getProduct());
				returnOrderItem.setSpecificationValues(productOrderItem.getSpecifications());
	            returnOrderItem.setThumbnail( productOrderItem.getThumbnail() );
				returnOrderItem.setReturnOrder(returnOrder);
	            returnOrderItem.setPrice( productOrderItem.getPrice() );
				returnOrderItemService.save(returnOrderItem);
			}

	        ReturnOrderLog returnOrderLog = new ReturnOrderLog();
	        returnOrderLog.setOperator(null);
	        returnOrderLog.setReturnOrder(returnOrder);
	        returnOrderLog.setType(ReturnOrderLog.Type.complete);
	        returnOrderLog.setContent( content.toString() );
	        returnOrderLogService.save(returnOrderLog);
	        
	        // 存放正确的返回参数CODE--1
	    	mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
    	mapResult.put(UnivParameter.DATA, new HashMap<String,Object>());
		
		return mapResult;
	}
	@Transactional
	@Override
	public Map<String, Object> returnOrders(Long orderId) {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		try {
			Order order = orderService.find(orderId);
            Setting setting = SystemUtils.getSetting();
			if( order == null){
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, "订单不存在");
				return mapResult;
			}
			
			for (Shipping shipping : order.getShippings()) {
				shippingService.update(shipping);
			}
			//订单状态变成已经退货
			order.setStatus(Order.Status.returned);
            order.setReturnedQuantity( order.getQuantity() );
            //退款金额为实际付款金额 减去 预定金
            if( null == order.getDeposit()){
                order.setRefundAmount( order.getAmount()  );
            }else{
                order.setRefundAmount( order.getAmount().subtract( order.getDeposit()) );
            }

            order.setCompleteDate( new Date() );

            //售后单
            ReturnOrder returnOrder = new ReturnOrder();
            returnOrder.setType( ReturnOrder.Type.returnProduct );
            returnOrder.setOrder( order );
            returnOrder.setOrganization( order.getOrganization() );
            returnOrder.setMember( order.getMember() );
            returnOrder.setAddress( order.getAddress() );
            returnOrder.setPhone( order.getPhone() );
            returnOrder.setContacter( order.getConsignee() );
            returnOrder.setStoreShippingMethod( order.getShippingMethodName() );
            
            returnOrder.setReturnPrice( order.getAmountPaid() );
            returnOrder.setReturnPriceActual( order.getAmountPaid() );
            returnOrder.setReturnPoint( order.getExchangePoint() );
            returnOrder.setReturnGodMoney( order.getGodMoneyCount() );
            List<String> couponCodes = new ArrayList<String>();
            List<CouponCode> coupons = order.getCouponCode();
            for( CouponCode coupon : coupons ){
                couponCodes.add( coupon.getCode() );
            }
            returnOrder.setReturnCouponCode( couponCodes );
            try{
                returnOrder.setStatus( ReturnOrder.Status.complete );
                returnOrderService.save( returnOrder );
            }catch (Exception e){
                logger.warn( "生成售后单失败 ：" + e.getMessage() );
                e.printStackTrace();
                //存放错误的返回参数CODE--0
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
				return mapResult;
            }

            //积分订单  积分立即到账
            if( Order.Type.exchange == order.getType() ){
                //积分订单
                order.setReturnExchagePoint( order.getExchangePoint()  );
                returnOrder.setStatus( ReturnOrder.Status.complete );
                orderService.undoExchangePoint( order );
            }else {
                //非积分订单
                Set<Payment> payments = order.getPayments();
                order.setReturnGodMoenyAmount( order.getGodMoneyPaid() );
                try{
                    Refunds refunds = null;
                    for(Payment payment:payments){
                        refunds = new Refunds();
                        /*TODO 定金不退*/
                        if(payment.getMethod().equals(Payment.Method.online) && payment.getType() == Payment.Type.payment){
                            refunds.setPaymentMethod(order.getPaymentMethodName());
                            refunds.setMethod(Refunds.Method.online);
                            refunds.setAmount(payment.getAmount());
                            refunds.setOperator(adminService.find(1L));
                            refunds.setOrder(order);
                            refunds.setAccount(payment.getAccount());
                            refunds.setBank(payment.getBank());
                            refunds.setPayee(payment.getPayer());
                            refunds.setStatus( Refunds.Status.wait );
                            refunds.setReturnOrder( returnOrder );
                            refundsService.save(refunds);
                            //若存在退还现金的情况 则修改为待退款状态
                            if( ReturnOrder.Status.pendingRefund != returnOrder.getStatus() ){
                                returnOrder.setStatus( ReturnOrder.Status.pendingRefund );
                                returnOrderService.update( returnOrder );
                            }
                        }
                    }
                    String godRate = setting.getMoneyRechargeGodMoney();
                    //退回神币
                    memberService.addBalance(order.getMember(), order.getGodMoneyPaid().multiply( new BigDecimal(godRate) ) ,GodMoneyLog.Type.refunds ,null , null);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.warn( "生成退款单，和售后单失败 ：" + e.getMessage() );
                    //存放错误的返回参数CODE--0
    				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
    				mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
    				return mapResult;
                }

                //释放优惠劵
                orderService.undoUseCouponCode( order );
            }

            orderService.releaseAllocatedStock(order);

            orderService.update(order);

            //更新订单项状态
            List<OrderItem> orderItems = order.getOrderItems();

            ReturnOrderItem returnOrderItem = null;
            for( OrderItem orderItem : orderItems  ){
                orderItem.setReturnedQuantity( orderItem.getQuantity() );
                returnOrderItem = new ReturnOrderItem();
                returnOrderItem.setReturnOrder( returnOrder );
                returnOrderItem.setSn( orderItem.getSn() );
                returnOrderItem.setName( orderItem.getName() );
                returnOrderItem.setType( orderItem.getType() );
                returnOrderItem.setPrice( orderItem.getReturnPrice() );
                returnOrderItem.setWeight( orderItem.getWeight() );
                returnOrderItem.setPoint( 0L );
                returnOrderItem.setThumbnail( orderItem.getThumbnail() );
                returnOrderItem.setQuantity( orderItem.getQuantity() );
                returnOrderItem.setProduct( orderItem.getProduct() );
                returnOrderItem.setSpecificationValues(orderItem.getSpecifications());
                returnOrderItemService.save( returnOrderItem );
                orderItemService.update( orderItem );
            }

            ReturnOrderLog returnOrderLog = new ReturnOrderLog();
            returnOrderLog.setOperator(null);
            returnOrderLog.setReturnOrder(returnOrder);
            returnOrderLog.setType(ReturnOrderLog.Type.deal);
            returnOrderLog.setContent("用户未确认收货，执行退货申请");
            returnOrderLogService.save(returnOrderLog);

	        // 存放正确的返回参数CODE--1
	    	mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
    	mapResult.put(UnivParameter.DATA, new HashMap<String,Object>());
		
		return mapResult;
	}

	@Override
	public Map<String, Object> getDeliveryCorpList() {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> list = new ArrayList<Object>();
		try {
			List<DeliveryCorp> deliveryCorp = deliveryCorpService.findByName();
			if( deliveryCorp.size() > 0){
				for (DeliveryCorp deliveryCorp2 : deliveryCorp) {
					map = new HashMap<String, Object>();
					map.put("name", deliveryCorp2.getName());
					list.add(map);
				}
				// 存放正确的返回参数CODE--1
				result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
				result.put(UnivParameter.DATA, list);
			}else{
				// 存放正确的返回参数CODE--1
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.DATA, "物流公司暂未添加");
			}
			
			
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			result.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return result;
		}
		return result;
	}
	@Transactional
	@Override
	public Map<String, Object> preToOrder(Long preOrderId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Order order = orderService.find(preOrderId);
			BigDecimal shoujia=orderService.findFristOrderItem( preOrderId );
			order.setExpire(DateUtils.addMinutes(new Date(), order.getPaymentMethod().getTimeout() ));
			order.setAmount(shoujia);
			order.setStatus(Order.Status.pendingPayment);
			order.setType(Order.Type.general);
			orderService.update(order);
			
			OrderItem item = order.getOrderItems().get(0);
			item.setReturnPrice(shoujia.subtract(order.getDeposit()));
			orderItemService.update(item);
			// 存放正确的返回参数CODE--1
			result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			result.put(UnivParameter.DATA, "成功");
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			result.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return result;
		}
		return result;
		
	}
	@Transactional
	@Override
	public Map<String, Object> receiverReturnOrder(ReturnOrder returnOrder1) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ReturnOrder returnOrder = returnOrderService.find(returnOrder1.getId());
			returnOrder.setStatus(Status.complete);
			returnOrderService.update(returnOrder);
			
			ReturnOrderLog returnOrderLog = new ReturnOrderLog();
			returnOrderLog.setContent("用户已确认收货，本次售后完成");
			returnOrderLog.setType(Type.complete);
			returnOrderLog.setReturnOrder(returnOrder);
			returnOrderLogService.save(returnOrderLog);
			
			List<StockLog> list = new ArrayList<StockLog>(returnOrder.getStockLogs());
			for (StockLog stockLog : list) {
				stockLog.setState("10");
				stockLogService.update(stockLog);
			}
			
			// 存放正确的返回参数CODE--1
			result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
			result.put(UnivParameter.DATA, new HashMap<>());
			
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			result.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> validationStock(String orderId) {
		// 定义结果数据结构
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Order order = orderService.findBySn(orderId);
			
			List<OrderItem> orderItems=order.getOrderItems();
			Map<Long, Integer> map = new HashMap<Long, Integer>();
			Product product = null;
			/**
			 * 循环统计 每种商品的 所需要的库存
			 */
			for (OrderItem orderItem : orderItems) {

				if (null != orderItem) {
					product = orderItem.getProduct();

					if (map.containsKey(product.getId())) {
						map.put(product.getId(),
								orderItem.getQuantity() + map.get(product.getId()));
					} else {
						map.put(product.getId(), orderItem.getQuantity());
					}
				}
			}
			Long[] productIds = map.keySet().toArray(new Long[0]);

			List<Product> products = productService.findList(productIds);
			/**
			 * 循环判断库存是否充足
			 */
			List<String> productNames = new ArrayList<String>();
			for (Product product1 : products) {
				if (map.get(product1.getId()) > product1.getAvailableStock()) {
					productNames.add(product1.getName());
				}
			}

			if (productNames.size() > 0) {
				// 存在商品库存不足
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"商品库存不足");
				return result;
			}else{
				// 存放正确的返回参数CODE--1
				result.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
				result.put(UnivParameter.DATA, new HashMap<>());
			}
		} catch (Exception e) {
			// 存放错误的返回参数CODE--0
			e.printStackTrace();
			result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			result.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> findPromotionByProduct(int i,Long id) {
		return appIndexDao.findPromotionByProduct(i,id);
	}

	@Override
	public Map<String, Object> createOrder(Long userId, Long productId,
			Integer quantity, Long receiverId, Long paymentMethodId,
			Long shippingMethodId, Long[] couponCodes, String invoiceTitle,
			BigDecimal godMoneyNum, Long contractInfoId, String memo,
			String recommended, Long organizationId, String collectTime) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		try {
			Member member=memberService.find(userId);
			Product product = productService.find(productId);
			Receiver receiver = receiverService.find(receiverId);
	        ShippingMethod shippingMethod = null;
	        Organization organization = organizationService.find(organizationId);
	        PaymentMethod paymentMethod = paymentMethodService.find(paymentMethodId);
			if(member.getPhone() == recommended){
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"推荐人不可为本人");
				return result;
			}
			if (quantity == null || quantity < 1) {
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"商品数量错误");
				return result;
	        }
			
			if (!product.getIsMarketable() ) {
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"商品尚未上架");
				return result;
	        }
			
			if ( !product.hasPrepreOrderIng() && quantity > product.getAvailableStock()) {
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"商品库存不足");
				return result;
	        }
			
			if (receiver == null || !member.equals(receiver.getMember())) {
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"收货地址不正确");
				return result;
	        }

	        shippingMethod = shippingMethodService.find(shippingMethodId);
	        if (shippingMethod == null) {
	        	result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"配送方式不正确");
				return result;
	        }
	        
	        if( shippingMethodId==2 &&  (null == organization || null==collectTime)){
	        	result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"请选择自提门店");
				return result;
	        }
			
	        if (godMoneyNum != null
	                && godMoneyNum.compareTo(member.getGodMoney()) > 0) {
	        	result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"神币不足");
				return result;
	        }
	        
	        // 发票 抬头
	        Invoice invoice = StringUtils.isEmpty(invoiceTitle) ? null  : new Invoice(
	                invoiceTitle, null);
	        
	        Set<CartItem> cartItems = new HashSet<CartItem>();
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setIsSelect(true);
            
            cartItems.add(cartItem);

            Cart cart = new Cart();
            cart.setMember(member);
            cart.setCartItems(cartItems);

            ContractPhoneNumberUserInfo contractPhoneInfo = null;
            
            if (null != contractInfoId) {
                contractPhoneInfo = contractPhoneNumUserInfoService
                        .find(contractInfoId);
                if (contractPhoneInfo.getPhoneNumber().getExpire()
                        .before(new Date())) {
                	result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
    				result.put(UnivParameter.ERRORMESSAGE,"您的选的手机号已经超过锁定时间，订单失效");
    				return result;
                }
            }
            
            Map<String, Object> mapResult = orderService.create( Order.Type.general , cart, product,
                    quantity, receiver, paymentMethod, shippingMethod,
                    couponCodes, invoice , godMoneyNum, memo, contractPhoneInfo , organization , collectTime , recommended);
	        
			
			if (UnivParameter.DATA_ERRORCODE.equals(mapResult.get(
					UnivParameter.CODE).toString())) {

				// 崩溃性数据处理错误CODE-1
				result.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);

				// 加载数据处理层崩溃性MESSAGE
				result.put(UnivParameter.ERRORMESSAGE,
						mapResult.get(UnivParameter.REASON).toString());
			} else {
				// 崩溃性数据处理错误CODE-1
				result.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
				// 数据处理层正确性结果判断--加载正确的数据结果集
				Order order = (Order) mapResult.get(UnivParameter.DATA);
				if( order.getAmountPayable().compareTo( BigDecimal.ZERO ) > 0 ){
					map2.put("istoPay" , true );
		        }else{
		        	map2.put("istoPay" , false );
		        }
				if( paymentMethod.getId() == 1 ){
					map2.put("istoPays" , true );
				}else{
				    map2.put("istoPays" , false );
				}
//				if( order.getAmountPayable().compareTo( BigDecimal.ZERO ) > 0 && paymentMethod.getId() == 2  ){
//					map2.put("istoPays" , true );
//		        }else{
//		        	map2.put("istoPays" , false );
//		        }
				map2.put("orderSn" , order.getSn() );
				map2.put("amount", order.getAmountPayable());
				result.put(UnivParameter.DATA,map2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();

			// 崩溃性逻辑处理错误CODE-0
			result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);

			// 加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> purchaseAtOnce(String userId, Long id,int quantity,Long[] productBinds) {
		Map<String , Object> data = new HashMap<String , Object>();
		
		Member member = memberService.find( Long.parseLong(userId) );
		if( null == member ){
			data.put("success", false);
			return data;
		}
		
		Product product = productService.find(id) ;
		/**
		 * 校验绑定商品 库存是否
		 */
		List<Product> bindProducts = productService.findList(productBinds);
		for (Product bindProduct : bindProducts) {
			if (quantity > bindProduct.getAvailableStock()) {
				//存在商品库存不足
				data.put("success", false);
				data.put("message" , bindProduct.getName()+"绑定商品库存不足" );
				return data;
			}
		}
		
		/**
		 *  判断库存是否充足
		 */
		
		
		if(quantity > product.getAvailableStock()){
			//存在商品库存不足
			data.put("success", false);
			data.put("message" , "商品数量不足" );
			return data;
		}else{
			data.put("success", true);
			//库存充足 ， 返回 订单需要的数据
			Map<String , Object> receiver = null;
			
			//默认收货人信息
			Receiver defaultReceiver = receiverService.findDefault(member);
			
			
			// 生成普通订单

            Set<CartItem> cartItems = new HashSet<CartItem>();

            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setIsSelect(true);
            cartItem.setParentId(CartItem.type.mainproduct);
            cartItems.add(cartItem);
            
            Cart cart = new Cart();
            cart.setMember(member);
            cart.setCartItems(cartItems);
			
			
			
            Order order = orderService.generate(Order.Type.general, cart,
                    defaultReceiver, null, null, null, null, null, null);
	
			if( null == defaultReceiver ){
				data.put("receiver", null);
			}else{
				receiver = new HashMap<String , Object>();
				receiver.put("consignee", defaultReceiver.getConsignee());
				receiver.put("id", defaultReceiver.getId());
				receiver.put("xiangxidizhi", defaultReceiver.getAreaName() + defaultReceiver.getAddress());
				receiver.put("phone", defaultReceiver.getPhone());
				data.put("receiver", receiver);
			}
			
			//优惠劵信息
			Set<CouponCode> couponCodes = couponService.getAvailableCoupons(order, member);
			List<Map<String , Object>> couponCodesDatas = new ArrayList<Map<String , Object>>();
			for(CouponCode couponCode:couponCodes){
				Map<String , Object> couponCodesItem = new HashMap<String , Object>();
				couponCodesItem.put("couponId", couponCode.getId());
				couponCodesItem.put("couponName", couponCode.getCoupon().getName());
				couponCodesItem.put("couponInfo", couponCode.getCoupon().getIntroduction());
				couponCodesItem.put("couponCountPrice", couponCode.getCoupon().getCountPrice());
				couponCodesItem.put("couponCode", couponCode.getCode());
				couponCodesItem.put("couponStartDate", format.format(couponCode.getCoupon().getBeginDate()));
				couponCodesItem.put("couponEndDate", format.format(couponCode.getCoupon().getEndDate()));				
				couponCodesDatas.add(couponCodesItem);
			}
			data.put("couponCodes", couponCodesDatas);
			//神币
			data.put("godMoney", member.getGodMoney());
			//人民币对神币的比例
			Setting setting = SystemUtils.getSetting();
			String godRate = setting.getMoneyRechargeGodMoney();
			data.put("goodRate",  godRate );
			//商品金额
			data.put("orderPrice",  order.getPrice() );
			//应付金额
			data.put("orderamount",  order.getAmount() );
			//运费
			data.put( "freight", order.getFreight());
		}
		
		return data;
	}
	
	@Transactional
	@Override
	public Map<String, Object> godMoney(Long userId, BigDecimal godMoney,
			String orderSn) {
		Map<String, Object> result=new HashMap<String, Object>();
		try {
			Setting setting = SystemUtils.getSetting();
			Member member = memberService.find(userId);

			Order order = orderService.findBySn(orderSn);
			
			// 神币使用前 检查 神币使用数量 ， 用户神币数量，神币使用数量和订单金额
			if (godMoney != null
					&& ( godMoney.compareTo(BigDecimal.ZERO) < 0 || godMoney.compareTo(member.getGodMoney()) > 0 || godMoney.compareTo(order.getAmount()) > 0)) {
				logger.error("service createOrder  godmoney use is error");
				result.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE, "神币不足");
				return result;
			}

			// 将神币根据转换比例转换成人民币
			BigDecimal amountPayable = godMoney != null ? order.getAmount()
					.subtract(
							godMoney.multiply(new BigDecimal(setting.getMoneyRechargeGodMoney())) 
							) : order.getAmount();
			
			//使用神币数量		
			if( godMoney != null  ){
				order.setGodMoneyCount( godMoney  );
				order.setGodMoneyPaid( godMoney.multiply(new BigDecimal(setting.getMoneyRechargeGodMoney()))  );
			}else{
				order.setGodMoneyCount( BigDecimal.ZERO  );
				order.setGodMoneyPaid( BigDecimal.ZERO );
			}
			order.setAmountPaid(godMoney);
			ShippingMethod shippingMethod  = order.getShippingMethod();
			PaymentMethod paymentMethod = order.getPaymentMethod();
			if (amountPayable.compareTo(BigDecimal.ZERO) > 0) {

	            // 订单状态 默认为 等待付款
	            order.setStatus(Order.Status.pendingPayment);

	            //在线支付 才存在订单有效时间
	            if (shippingMethod.getId() == 1l && paymentMethod.getTimeout() != null
	                    && Order.Status.pendingPayment.equals(order.getStatus())) {
	                order.setExpire(DateUtils.addMinutes(new Date(),
	                        paymentMethod.getTimeout()));
	            }

	            if (PaymentMethod.Method.online.equals(paymentMethod.getMethod())) {
	            	orderService.lock(order, member);
	            }
	        } else {
	            // 神币 大于 需要支付的商品价值
	            if( order.getType() == Order.Type.reserve ){
	                order.setStatus(Order.Status.pendingPayment);
	            }else{
	                order.setStatus(Order.Status.pendingShipment);

	                if( !(shippingMethod.getId() == 1l) ){
	                    //门店自提，改为待自提状态
	                    order.setStatus(Order.Status.daiziti);
	                }
	            }
	        }
			orderService.update(order);
			// 使用神币支付 生成一条支付记录
//			if (godMoney != null && godMoney.compareTo(BigDecimal.ZERO) > 0) {
//				Payment payment = new Payment();
//				payment.setType(Payment.Type.payment);
//				payment.setMethod(Payment.Method.deposit);
//				payment.setFee(BigDecimal.ZERO);
//				payment.setAmount(godMoney);
//				payment.setOrder(order);
//				orderService.payment(order, payment, null);
//			}
			result.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			result.put(UnivParameter.DATA, new HashMap<String, Object>());
		} catch (Exception e) {
			// 清空结果数据结构体-准备装载逻辑层错误信息
			result.clear();

			// 崩溃性逻辑处理错误CODE-0
			result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);

			// 加载逻辑处理层崩溃性MESSAGE
			result.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return result;
		}
		return result;
	}

	@Override
	public Map<String, Object> payment(Long userId, BigDecimal godMoney,
			String orderSn) {
		// TODO Auto-generated method stub
		return null;
	}
}
