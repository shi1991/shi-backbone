package com.puyuntech.ycmall.service.impl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.model.Charge;
import com.puyuntech.ycmall.Filter;
import com.puyuntech.ycmall.Order.Direction;
import com.puyuntech.ycmall.Page;
import com.puyuntech.ycmall.Pageable;
import com.puyuntech.ycmall.Setting;
import com.puyuntech.ycmall.dao.OrganizationDao;
import com.puyuntech.ycmall.dao.PosIndexDao;
import com.puyuntech.ycmall.dao.ProductDao;
import com.puyuntech.ycmall.dao.SnDao;
import com.puyuntech.ycmall.entity.AdEntity;
import com.puyuntech.ycmall.entity.Admin;
import com.puyuntech.ycmall.entity.BonusEntity;
import com.puyuntech.ycmall.entity.BonusLog;
import com.puyuntech.ycmall.entity.ContractItem;
import com.puyuntech.ycmall.entity.ContractPhoneNumberUserInfo;
import com.puyuntech.ycmall.entity.DeliveryCorp;
import com.puyuntech.ycmall.entity.GrabSeckill;
import com.puyuntech.ycmall.entity.Member;
import com.puyuntech.ycmall.entity.Order;
import com.puyuntech.ycmall.entity.OrderItem;
import com.puyuntech.ycmall.entity.OrderLog;
import com.puyuntech.ycmall.entity.Organization;
import com.puyuntech.ycmall.entity.PaymentLog;
import com.puyuntech.ycmall.entity.PaymentMethod;
import com.puyuntech.ycmall.entity.PhoneNumber;
import com.puyuntech.ycmall.entity.PointBehavior;
import com.puyuntech.ycmall.entity.PointLog;
import com.puyuntech.ycmall.entity.PointReward;
import com.puyuntech.ycmall.entity.Product;
import com.puyuntech.ycmall.entity.Promotion;
import com.puyuntech.ycmall.entity.PromotionBind;
import com.puyuntech.ycmall.entity.Shipping;
import com.puyuntech.ycmall.entity.ShippingItem;
import com.puyuntech.ycmall.entity.ShippingMethod;
import com.puyuntech.ycmall.entity.Sn;
import com.puyuntech.ycmall.entity.StockLog;
import com.puyuntech.ycmall.entity.StockTransItem;
import com.puyuntech.ycmall.entity.StockTransLog;
import com.puyuntech.ycmall.service.AdminService;
import com.puyuntech.ycmall.service.BonusLogService;
import com.puyuntech.ycmall.service.BonusService;
import com.puyuntech.ycmall.service.CacheService;
import com.puyuntech.ycmall.service.ContractItemService;
import com.puyuntech.ycmall.service.ContractPhoneNumberUserInfoService;
import com.puyuntech.ycmall.service.DeliveryCorpService;
import com.puyuntech.ycmall.service.GrabSeckillService;
import com.puyuntech.ycmall.service.MemberRankService;
import com.puyuntech.ycmall.service.MemberService;
import com.puyuntech.ycmall.service.OrderItemService;
import com.puyuntech.ycmall.service.OrderLogService;
import com.puyuntech.ycmall.service.OrderService;
import com.puyuntech.ycmall.service.OrganizationService;
import com.puyuntech.ycmall.service.PaymentLogService;
import com.puyuntech.ycmall.service.PaymentMethodService;
import com.puyuntech.ycmall.service.PhoneNumberService;
import com.puyuntech.ycmall.service.PointBehaviorService;
import com.puyuntech.ycmall.service.PointLogService;
import com.puyuntech.ycmall.service.PointRewardService;
import com.puyuntech.ycmall.service.PosIndexService;
import com.puyuntech.ycmall.service.ProductService;
import com.puyuntech.ycmall.service.ShippingItemService;
import com.puyuntech.ycmall.service.ShippingMethodService;
import com.puyuntech.ycmall.service.ShippingService;
import com.puyuntech.ycmall.service.StockLogService;
import com.puyuntech.ycmall.service.StockTransCheckService;
import com.puyuntech.ycmall.service.StockTransItemService;
import com.puyuntech.ycmall.service.StockTransLogService;
import com.puyuntech.ycmall.util.CommonParameter;
import com.puyuntech.ycmall.util.DateUtil;
import com.puyuntech.ycmall.util.KuaidiUtils;
import com.puyuntech.ycmall.util.RandomValueUtils;
import com.puyuntech.ycmall.util.SystemUtils;
import com.puyuntech.ycmall.util.UnivParameter;
import com.puyuntech.ycmall.vo.PromotionBindVO;

/**
 * 
 *  ServiceImpl - POS首页相关信息. 
 * Created on 2015-11-3 下午7:26:53 
 * @author 南金豆
 */
@Service("posIndexServiceImpl")
public class PosIndexServiceImpl extends BaseServiceImpl<AdEntity, Long>  implements PosIndexService {
	/**
	 * 订单 Service 
	 */
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	
	/**
	 * 手机号 Service
	 */
	@Resource(name="phoneNumberServiceImpl")
	private PhoneNumberService phoneNumerService;
	
	/**
	 * 订单项 Service 
	 */
	@Resource(name = "orderItemServiceImpl")
	private OrderItemService orderItemService;
	
	/**
	 * 订单项 Service 
	 */
	@Resource(name = "stockTransItemServiceImpl")
	private StockTransItemService stockTransItemService;
	/**
	 * 订单记录 Service 
	 */
	@Resource(name = "orderLogServiceImpl")
	private OrderLogService orderLogService;
	/**
	 * 红包 Service 
	 */
	@Resource(name = "bonusServiceImpl")
	private BonusService bonusService;
	/**
	 * 管理员Service  
	 */
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;

	/**
	 * 缓存Service对象
	 */
	@Resource(name = "cacheServiceImpl")
    private CacheService cacheService;
	/**
	 * 会员Service对象
	 */
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	/**
	 * 会员等级 Service
	 */
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	/**
	 * 商品 Service
	 */
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	/**
	 *库存记录 Service
	 */
	@Resource(name = "stockLogServiceImpl")
	private StockLogService stockLogService;
	/**
	 * 发货单 Service
	 */
	@Resource(name = "shippingServiceImpl")
	private ShippingService shippingService;
	/**
	 * 发货项 Service
	 */
	@Resource(name = "shippingItemServiceImpl")
	private ShippingItemService shippingItemService;
	/**
	 * 积分 Service
	 */
	@Resource(name = "pointLogServiceImpl")
	private PointLogService pointLogService;
	/**
	 * 积分通道 Service
	 */
	@Resource(name = "pointRewardServiceImpl")
	private PointRewardService pointRewardService;
	
	@Resource(name = "organizationDaoImpl")
	private OrganizationDao organizationDao;
	
	/**
	 * 门店 Service
	 */
	@Resource(name = "organizationServiceImpl")
	private OrganizationService organizationService;
	/**
	 * 手机号 Service
	 */
	@Resource(name="phoneNumberServiceImpl")
	private PhoneNumberService phoneNumberService;
	/**
	 * 套餐内容项 Service
	 */
	@Resource(name="contractItemServiceImpl")
	private ContractItemService contractItemService;
	/**
	 * 合约套餐以及用户信息 Service
	 */
	@Resource(name="contractPhoneNumberUserInfoServiceImpl")
	private ContractPhoneNumberUserInfoService contractPhoneNumberUserInfoService;
	/**
	 * 配送方式 Service
	 */
	@Resource(name="shippingMethodServiceImpl")
	private ShippingMethodService shippingMethodService;
	/**
	 *支付方式 Service
	 */
	@Resource(name="paymentMethodServiceImpl")
	private PaymentMethodService paymentMethodService;
	/**
	 *支付记录 Service
	 */
	@Resource(name="paymentLogServiceImpl")
	private PaymentLogService paymentLogService;
	/**
	 *物流公司 Service
	 */
	@Resource(name="deliveryCorpServiceImpl")
	private DeliveryCorpService deliveryCorpService;
	/**
	 * 积分获得行为 Service
	 */
	@Resource(name = "pointBehaviorServiceImpl")
	private PointBehaviorService pointBehaviorService;
	
	@Resource(name="bonusLogServiceImpl")
	private BonusLogService bonusLogService;
	
	@Resource(name="grabSeckillServiceImpl")
	private GrabSeckillService secKillService;
	/**
	 * 商品dao
	 */
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	
	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	
	
	@Resource(name = "posIndexDaoImpl")
	private PosIndexDao posIndexDao;
	
	@Resource(name = "stockTransLogServiceImpl")
    private StockTransLogService stockTransLogService;
    
    @Resource(name = "stockTransCheckServiceImpl")
    private StockTransCheckService stockTransCheckService;
	

	public Map<String, Object> goodsSearch(String selectVaule, int selectType,
			int pageLoadType, int pageRowsCount, String goodsId,String shopId) {
		return  posIndexDao.goodsSearch(selectVaule,selectType,pageLoadType,pageRowsCount,goodsId,shopId);
	}

	public Map<String, Object> InventorySearch(String selectVaule,
			int pageLoadType, int pageRowsCount, String shopId,String content,int pageType) {
		return  posIndexDao.InventorySearch(selectVaule,pageLoadType,pageRowsCount,shopId,content,pageType);
	}

	
	public Map<String, Object> deleteOrders(String ordersId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		if(ordersId==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "数据不可为空");
			return mapResult;
		}
		try{	
			//定义要删除的orderId
			String orderId[]=new String[]{};
			orderId=ordersId.split(",");
			for(int i=0;i<orderId.length;i++){
				Order order = orderService.find(Long.parseLong(orderId[i]));
				if(order==null||(order.getStatus()!=Order.Status.pendingPayment&&order.getIsOnline()==false)){
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, "订单无法删除");
					return mapResult;
				}else{
					order.setIsDelete(true);
					orderService.update(order);
				}
			}
			result.put("reason", "删除成功");
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.DATA,result);
		} catch (Exception e) {
			//存放错误的返回参数CODE--500
			mapResult.put(UnivParameter.LOGIC_COLLAPSECODE, UnivParameter.LOGIC_COLLAPSECODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		//存放最终的执行结果
		return mapResult;
	}
	

	@Override
	public Map<String, Object> getAdminInfo(String adminId) {
		return  posIndexDao.getAdminInfo(adminId);
	}

	@Override
	public Map<String, Object> getBonusAccept(int pageLoadType,
			int pageRowsCount, String packetId,String shopId) {
		return  posIndexDao.getBonusAccept(pageLoadType,pageRowsCount,packetId,shopId);
	}

	@Override
	public Map<String, Object> getBonusDetails(String packetId) {
		return  posIndexDao.getBonusDetails(packetId);
	}

	@Override
	public Map<String, Object> acceptBonus(String packetId, Long checkUser,
			 int checkType,String refuseSeasonId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			if(packetId==null||checkUser==null){
				//存放错误的返回参数CODE--0
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, "缺少审核人或者红包编号");
				return mapResult;
			}
			if(checkType<1||checkType>2){
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, "审核方式错误");
				return mapResult;
			}
			if(refuseSeasonId==null&&checkType==1){
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, "红包拒绝受理时缺少拒绝理由");
				return mapResult;
			}
			Admin admin =adminService.find(checkUser);
			if(admin==null){
				//存放错误的返回参数CODE--0
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, "店员不存在");
				return mapResult;
			}
			BonusEntity bonus =bonusService.find( Long.parseLong(packetId));
			if(bonus==null||!bonus.getState().equals(BonusEntity.State.checking)){
				//存放错误的返回参数CODE--0
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, "红包不存在或已经被审核过");
				return mapResult;
			}
			Setting setting =SystemUtils.getSetting();
		   	//获取红包拒绝原因一
		   	String packetRefuseSeasonOne = setting.getPacketRefuseSeasonOne();
		   	//获取红包拒绝原因二
		   	String packetRefuseSeasonTwo = setting.getPacketRefuseSeasonTwo();
		   	//获取红包拒绝原因三
		   	String packetRefuseSeasonThree = setting.getPacketRefuseSeasonThree();
		   	//获取红包拒绝原因四
		   	String packetRefuseSeasonFour= setting.getPacketRefuseSeasonFour();
			if(checkType==1){
				int refuseSeason=Integer.parseInt(refuseSeasonId);
				bonus.setState(BonusEntity.State.disallow);
				if(refuseSeason==1){
					bonus.setMemo(packetRefuseSeasonOne);
				}else if(refuseSeason==2){
					bonus.setMemo(packetRefuseSeasonTwo);
				}else if(refuseSeason==3){
					bonus.setMemo(packetRefuseSeasonThree);
				}else{
					bonus.setMemo(packetRefuseSeasonFour);
				}
			}else{
				bonus.setState(BonusEntity.State.waiting);

				GrabSeckill grabSeckill = new GrabSeckill();
				grabSeckill.setType(GrabSeckill.GrabSecKillTypeEnmu.GRAB);
				grabSeckill.setState(GrabSeckill.StateEnmu.DONE);
				grabSeckill.setGoodsTypes(GrabSeckill.GoodsTypeEnmu.BONUS);
				grabSeckill.setPrice(BigDecimal.ZERO);
				grabSeckill.setGoodsGross(bonus.getGross());
				grabSeckill.setGoodsResidue(bonus.getGross());
				if(bonus.getType().equals(BonusEntity.Type.entity)){
					grabSeckill.setContent(bonus.getContent());
					grabSeckill.setGoods(bonus.getPacketGoods());
					for(int i=0;i<bonus.getGross();i++){
						BonusLog bonusLog =new BonusLog();
						bonusLog.setBonus(bonus);
						bonusLog.setExchangeState('0');
						bonusLogService.save(bonusLog);
					}
				}else if(bonus.getBonusKind().equals(BonusEntity.BonusKind.Equal)){
					for(int i=0;i<bonus.getGross();i++){
						BonusLog bonusLog =new BonusLog();
						bonusLog.setBonus(bonus);
						bonusLog.setExchangeState('0');
						bonusLog.setCredits(bonus.getCredit().divide(new BigDecimal(bonus.getGross())));
						bonusLogService.save(bonusLog);
					}
				}else if(bonus.getBonusKind().equals(BonusEntity.BonusKind.Random)){
					
					BigDecimal[] credits = new BigDecimal[bonus.getGross()];
					for(int k=0;k<credits.length;k++){
						credits[k] = BigDecimal.ONE;
					}
					for(int j=0;j<(bonus.getCredit().intValue()-bonus.getGross());j++){
						Integer subscript = (int) (Math.random()*bonus.getGross());
						credits[subscript]= credits[subscript].add(BigDecimal.ONE);
					}
					for(int i=0;i<bonus.getGross();i++){
						BonusLog bonusLog =new BonusLog();
						bonusLog.setBonus(bonus);
						bonusLog.setExchangeState('0');
						bonusLog.setCredits(credits[i]);
						bonusLogService.save(bonusLog);
					}
				}
				if(bonus.getType().equals(BonusEntity.Type.godMoney)){
					grabSeckill.setContent("神币");
				}
				if(bonus.getType().equals(BonusEntity.Type.point)){
					grabSeckill.setContent("积分");
				}
				grabSeckill.setGoods(bonus.getId().toString());
				grabSeckill.setBonus(bonus);
				secKillService.save(grabSeckill);
				bonus.setState(BonusEntity.State.waiting);
				bonusService.update(bonus);
			
			}
			bonus.setCheckUser(admin);
			bonus.setCheckTime(new Date());
			bonusService.update(bonus);
			result.put("reason", "审核结束");
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.DATA,result);
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
	public Map<String, Object> getGoodsParameter(String goodsId) {
		return  posIndexDao.getGoodsParameter(goodsId);
	}

	@Override
	public Map<String, Object> getGoodsInfo(String goodsId) {
		return  posIndexDao.getGoodsInfo(goodsId);
	}

	
	@Override
	public Map<String, Object> getOperaInfo(String goodsId) {
		return  posIndexDao.getOperaInfo(goodsId);
	}

	@Override
	public Map<String, Object> changePhoneNumber(String phoneNumId,String selectValue, String goodsId) {
		return  posIndexDao.changePhoneNumber(phoneNumId,selectValue,goodsId);
	}

	@Override
	public Map<String, Object> changeContractPackage(String goodsId,String contractTime) {
		return  posIndexDao.changeContractPackage(goodsId,contractTime);
	}

	@Override
	public Map<String, Object> getShopAdmin(String shopId,String  selectValue) {
		return  posIndexDao.getShopAdmin(shopId,selectValue);
	}

	@Override
	public Map<String, Object> getAdminSelling(String adminId,
			String beginTime, String endtime) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		List<Object> list=new ArrayList<Object>();
		if(beginTime.compareTo(endtime)>0){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "开始时间不能大于结束时间");
			return mapResult;
		}
		if(adminId==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "没有选择店员");
			return mapResult;
		}
		//定义要查询的adminsId
		String adminsId[]=new String[]{};
		adminsId=adminId.split(",");
		try{	
			for(int i=0;i<adminsId.length;i++){
				Admin admin = adminService.find(Long.parseLong(adminsId[i]));
				if(admin==null){
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, "没有Id为"+adminsId[i]+"的员工");
					return mapResult;
				}else{
					list.add(posIndexDao.getShopAdmin(adminsId[i],beginTime,endtime));
				}
			}
			mapResult.put(UnivParameter.DATA,list);
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
		} catch (Exception e) {
			//存放错误的返回参数CODE--500
			mapResult.put(UnivParameter.LOGIC_COLLAPSECODE, UnivParameter.LOGIC_COLLAPSECODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;
	}

	@Override
	public Map<String, Object> shopRank(String beginTime, String endtime) {
		return  posIndexDao.shopRank(beginTime,endtime);
	}

	@Override
	public Map<String, Object> sendPhoneCode(String phoneNumber, String adminId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> resultReson = new HashMap<String,Object>();
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

	   	//获取短信发送模版	   	
	   	String statusCode = setting.getStatusCode();

	   	HashMap<String, Object> result = null;
	   	CCPRestSDK restAPI= new CCPRestSDK();
			restAPI.init(smsHost,smsPort);// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
			restAPI.setAccount(smsSn, smsKey);// 初始化主帐号和主帐号TOKEN
			restAPI.setAppId(smsApplication);// 初始化应用ID

//			result = restAPI.sendTemplateSMS(phoneNumber,"1" ,new String[]{smsTemplate+code,"5"});

            result = restAPI.sendTemplateSMS(phoneNumber, CommonParameter.SMSTEMPLATE_VERI_CODE ,new String[]{code,"5"});

			if(statusCode.equals(result.get("statusCode"))){
				resultReson.put("reason", "验证码已经发送");
				//存放正确的返回参数CODE--1
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
				mapResult.put(UnivParameter.DATA,resultReson);
					//将ID和验证码放到Cache中
			   	cacheService.setCache( CommonParameter.CACHE_NAME_VCODE ,phoneNumber +adminId, code);
			}else{
				//存放错误的返回参数CODE--0
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, "对不起，发送失败 ！错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
				return mapResult;
			}
		return mapResult;
	}

	

	

	@Override
	public Map<String, Object> myShopRank(String shopId, String beginTime,
			String endtime) {
		return  posIndexDao.myShopRank(shopId,beginTime,endtime);
	}

	@Override
	public Map<String, Object> getNewGoods(String goodsId, String goodsColor,
			String goodsMemory) {
			return  posIndexDao.getNewGoods(goodsId,goodsColor,goodsMemory);
	}

	@Override
	public Map<String, Object> getShopOrders(String shopId, int ordersType,
			int pageLoadType, int pageRowsCount, String ordersId) {
		return  posIndexDao.getShopOrders(shopId,ordersType,pageLoadType,pageRowsCount,ordersId);
	}

	@Override
	public Map<String, Object> getTakeOrders(String shopId, int ordersType,
			int pageLoadType, int pageRowsCount, String ordersId) {
		return  posIndexDao.getTakeOrders(shopId,ordersType,pageLoadType,pageRowsCount,ordersId);
	}

	@Override
	public Map<String, Object> getDistributeOrders(String shopId,
			int ordersType, int pageLoadType, int pageRowsCount, String ordersId) {
		return  posIndexDao.getDistributeOrders(shopId,ordersType,pageLoadType,pageRowsCount,ordersId);
	}

	@Override
	public Map<String, Object> getOrdersDetail(String ordersId) {
		return  posIndexDao.getOrdersDetail(ordersId);
	}

	@Override
	public Map<String, Object> getInventoryChange(String shopId,Pageable pageable, int type) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			List<Object> dataList=new ArrayList<Object>();
			List<Object> dataList2=new ArrayList<Object>();
			List<Filter> filters = new ArrayList<Filter>();
			Organization organization= organizationService.find(Long.valueOf(shopId));
			Map<String,Object> data= new HashMap<String, Object>();
			Map<String,Object> data2= new HashMap<String, Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
			switch (type) {
			case 1:
				filters.add(Filter.eq("fromOrganization", organization));
				filters.add(Filter.eq("type", StockTransLog.Type.Return));
				pageable.setFilters(filters);
				pageable.setOrderDirection(Direction.desc);
				pageable.setOrderProperty("createDate");
				Page<StockTransLog> stockFromLogs = stockTransLogService.findPage(pageable);
				for(StockTransLog stockTransLog:stockFromLogs.getContent()){
					if(stockTransLog.getType().equals(StockTransLog.Type.Delivery)){
						continue;
					}
					for(StockTransItem stockTransItem:stockTransLog.getStockTransItems()){
						data= new HashMap<String, Object>();
						data.put("listId",stockTransItem.getId());
						data.put("createTime",sdf.format(stockTransLog.getCreateDate()));
						data.put("adminName",stockTransLog.getOperator().getName());
						data.put("goodsId",stockTransItem.getProduct().getId());
						data.put("goodsName",stockTransItem.getProduct().getName());
						data.put("goodsNum",stockTransItem.getStockLogs().size());
						data.put("goodsSn",stockTransItem.getProduct().getSn());
						data.put("listType","1");
						if(stockTransLog.getStatus().equals(StockTransLog.Status.await)){
							data.put("listState","3");
						}else{
							data.put("listState","4");
						}
						dataList.add(data);
					}
				}
				break;
				
			case 2:
				filters.add(Filter.eq("fromOrganization", organization));
				filters.add(Filter.eq("type", StockTransLog.Type.Allocation));
				pageable.setFilters(filters);
				pageable.setOrderDirection(Direction.desc);
				pageable.setOrderProperty("createDate");
				Page<StockTransLog> stockFromLogs2 = stockTransLogService.findPage(pageable);
				for(StockTransLog stockTransLog:stockFromLogs2.getContent()){
					if(stockTransLog.getType().equals(StockTransLog.Type.Delivery)){
						continue;
					}
					for(StockTransItem stockTransItem:stockTransLog.getStockTransItems()){
						data= new HashMap<String, Object>();
						data.put("listId",stockTransItem.getId());
						data.put("createTime",sdf.format(stockTransLog.getCreateDate()));
						data.put("adminName",stockTransLog.getOperator().getName());
						data.put("goodsId",stockTransItem.getProduct().getId());
						data.put("goodsName",stockTransItem.getProduct().getName());
						data.put("goodsNum",stockTransItem.getStockLogs().size());
						data.put("goodsSn",stockTransItem.getProduct().getSn());
						data.put("listType","2");
						if(stockTransLog.getStatus().equals(StockTransLog.Status.await)){
							data.put("listState","3");
						}else{
							data.put("listState","4");
						}
						dataList.add(data);
					}
				}
				break;
			case 3:
				filters.add(Filter.eq("toOrganization", organization));
				filters.add(Filter.eq("type", StockTransLog.Type.Allocation));
				pageable.setFilters(filters);
				pageable.setOrderDirection(Direction.desc);
				pageable.setOrderProperty("createDate");
				Page<StockTransLog> stockToLogs = stockTransLogService.findPage(pageable);
				for(StockTransLog stockTransLog:stockToLogs.getContent()){
					if(stockTransLog.getType().equals(StockTransLog.Type.Return)){
						continue;
					}
					for(StockTransItem stockTransItem:stockTransLog.getStockTransItems()){
						data= new HashMap<String, Object>();
						data.put("listId",stockTransItem.getId());
						data.put("createTime",sdf.format(stockTransLog.getCreateDate()));
						data.put("adminName",stockTransLog.getOperator().getName());
						data.put("goodsId",stockTransItem.getProduct().getId());
						data.put("goodsName",stockTransItem.getProduct().getName());
						data.put("goodsNum",stockTransItem.getStockLogs().size());
						data.put("goodsSn",stockTransItem.getProduct().getSn());
						data.put("listType","3");
						if(stockTransLog.getStatus().equals(StockTransLog.Status.checked)){
							data.put("listState","2");
						}else{
							data.put("listState","1");
						}
						dataList.add(data);
					}
				}
				break;
			case 4:
				filters.add(Filter.eq("toOrganization", organization));
				filters.add(Filter.eq("type", StockTransLog.Type.Delivery));
				pageable.setFilters(filters);
				pageable.setOrderDirection(Direction.desc);
				pageable.setOrderProperty("createDate");
				Page<StockTransLog> stockToLogs2 = stockTransLogService.findPage(pageable);
				for(StockTransLog stockTransLog:stockToLogs2.getContent()){
					if(stockTransLog.getType().equals(StockTransLog.Type.Return)){
						continue;
					}
					for(StockTransItem stockTransItem:stockTransLog.getStockTransItems()){
						data= new HashMap<String, Object>();
						data.put("listId",stockTransItem.getId());
						data.put("createTime",sdf.format(stockTransLog.getCreateDate()));
						data.put("adminName",stockTransLog.getOperator().getName());
						data.put("goodsId",stockTransItem.getProduct().getId());
						data.put("goodsName",stockTransItem.getProduct().getName());
						data.put("goodsNum",stockTransItem.getStockLogs().size());
						data.put("goodsSn",stockTransItem.getProduct().getSn());
						data.put("listType","4");
						if(stockTransLog.getStatus().equals(StockTransLog.Status.checked)){
							data.put("listState","2");
						}else{
							data.put("listState","1");
						}
						dataList.add(data);
					}
				}
				break;
			case 5:
				filters.add(Filter.eq("organization", organization));
				pageable.setFilters(filters);
				pageable.setOrderDirection(Direction.desc);
				pageable.setOrderProperty("createDate");
				Page<Order> orders = orderService.findPage(pageable);
				for(Order order:orders.getContent()){
					data = new HashMap<String, Object>();
					for(Shipping shipping:order.getShippings()){
						dataList2 = new ArrayList<Object>();
						
						for(ShippingItem item:shipping.getShippingItems()){
							data.put("listId",shipping.getId());
							data.put("adminName",shipping.getOperator());
							data.put("listType","5");
							data.put("listState","4");
							data.put("createTime",sdf.format(shipping.getCreateDate()));
							data2 = new HashMap<String, Object>();
							data2.put("goodsId",item.getProduct().getId());
							data2.put("goodsName",item.getProduct().getName());
							data2.put("goodsNum",item.getQuantity());
							data2.put("goodsSn",item.getProduct().getSn());
							dataList2.add(data2);
							data.put("goods", dataList2);
							
						}
						if( data.size() >0 ){
							dataList.add(data);
						}
					}
				}
				break;
			default:
				break;
			}
			result.put(UnivParameter.DATA, dataList);
			//存放正确的返回参数CODE--1
			result.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			result.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			result.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
			result.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return result;
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getInventoryChangeDetail(String listId,
			int listType) {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> data = new HashMap<String,Object>();
		List<Object> dataList = new ArrayList<Object>();
		try {
			if(StringUtils.isEmpty(listId)){
				result.put(UnivParameter.CODE,UnivParameter.DATA_ERRORCODE);
				result.put(UnivParameter.ERRORMESSAGE,"订单号不存在");
				return result;
			}
			Long l = Long.parseLong(listId);
			switch (listType) {
			case 5:
				Shipping shipping = shippingService.find(l);
				for(StockLog stockLog:shipping.getStockLogs()){
					data= new HashMap<String, Object>();
					data.put("sn",stockLog.getProductSn());
					dataList.add(data);
				}
//				dataresult.put("sn", shippingItem.getSn());
				break;

			default:
				StockTransItem stockTransItem = stockTransItemService.find(l);
				for(StockLog stockLog:stockTransItem.getStockLogs()){
						data= new HashMap<String, Object>();
						data.put("sn",stockLog.getProductSn());
						dataList.add(data);
				}
				break;
			}
			result.put(UnivParameter.DATA, dataList);
			//存放正确的返回参数CODE--1
			result.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			result.put(UnivParameter.ERRORMESSAGE,UnivParameter.NULL);
		} catch (Exception e) {
			//存放错误的返回参数CODE--0
			result.put(UnivParameter.CODE, UnivParameter.LOGIC_COLLAPSECODE);
			result.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return result;
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getAfterSale(String shopId , Pageable pageable) {
		return  posIndexDao.getAfterSale(shopId,pageable);
	}

	@Override
	public Map<String, Object> getAdminList(String shopId) {
		return  posIndexDao.getAdminList(shopId);
	}

	@Override
	public Map<String, Object> deleteAdmin(String adminId) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		if(adminId==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "请选择人员");
			return mapResult;
		}
		//定义要删除的adminsId
		String adminsId[]=new String[]{};
		adminsId=adminId.split(",");
		
		try{	
			for(int i=0;i<adminsId.length;i++){
				Admin admin = adminService.find(Long.parseLong(adminsId[i]));
				if(admin==null){
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, "部分员工不存在");
					return mapResult;
				}else if(admin.getOffice().equals(Admin.office.manager)){
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, "无权限删除店长");
					return mapResult;
				}else{
					admin.setIsEnabled(false);
					adminService.update(admin);
				}
			}
			result.put("reason", "删除成功");
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,result);
		} catch (Exception e) {
			//存放错误的返回参数CODE--500
			mapResult.put(UnivParameter.LOGIC_COLLAPSECODE, UnivParameter.LOGIC_COLLAPSECODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;
	}

	@Override
	public Map<String, Object> adminRegist(String shopId,String adminName,
			String adminPassWord, String adminCardCode, String adminPhone,
			String adminImage, String adminJobNumber) {
		//初始化map集合
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		if(shopId==null||adminName==null||adminPassWord==null||adminPhone==null||adminJobNumber==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "缺少参数");
			return mapResult;
		}
		Admin adm=adminService.findByPosUsername(adminName);
		if(adm!=null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "用户名已经存在");
			return mapResult;
		}
		if(adminService.phoneeExists(adminPhone)){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "手机号已经存在");
			return mapResult;
		}
		Admin ad =adminService.findByJobNumber(adminJobNumber);
		if(ad!=null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "工号已经存在");
			return mapResult;
		}
		try{	
			Admin admin =new Admin();
			admin.setOrganization(shopId);
			admin.setPhone(adminPhone);
			admin.setName(adminName);
			admin.setCardCode(adminCardCode);
			admin.setImage(adminImage);
			admin.setPosPassword(DigestUtils.md5Hex(adminPassWord));
			admin.setPosUsername(adminName);
			admin.setWebPassword(DigestUtils.md5Hex(adminPassWord));
			admin.setWebUsername(adminName);
			admin.setOffice(Admin.office.clerk);
			admin.setIsLocked(false);
			admin.setLoginFailureCount(0);
			admin.setVersion(0L);
			admin.setIsEnabled(true);
			admin.setJobNumber(adminJobNumber);
			admin.setPraiseCount(0);
			adminService.save(admin);
			result.put("reason", "生成成功");
			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,result);
		} catch (Exception e) {
			//存放错误的返回参数CODE--500
			mapResult.put(UnivParameter.LOGIC_COLLAPSECODE, UnivParameter.LOGIC_COLLAPSECODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;
	}

	@Override
	public Map<String, Object> dataManagement(String adminId) {
		return  posIndexDao.dataManagement(adminId);
	}

	@Override
	public Map<String, Object> advisoryCenterList(String shopId,int type) {
		return  posIndexDao.advisoryCenterList(shopId,type);
	}

	@Override
	public Map<String, Object> getAdvisoryDetail(String shopActivityId) {
		return  posIndexDao.getAdvisoryDetail(shopActivityId);
	}

	@Override
	public Map<String, Object> pointReward(String applerPhone,
			String applerName, String shopId, String invoiceImage) {
			//初始化map集合
			Map<String,Object> mapResult = new HashMap<String,Object>();
			Map<String,Object> result = new HashMap<String,Object>();
			if(applerPhone==null||applerName==null||shopId==null||invoiceImage==null){
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, "请填写完整数据");
				return mapResult;
			}
			Organization organization=organizationService.find(Long.parseLong(shopId));
			if(organization==null){
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, "门店不存在");
				return mapResult;
			}
			
			
			try{	
				 PointReward pointReward =new PointReward();
				 pointReward.setInvoiceImage(invoiceImage);
				 pointReward.setName(applerName);
				 pointReward.setOrganization(organization);
				 pointReward.setPhone(applerPhone);
				 pointReward.setState('0');
				 pointRewardService.save(pointReward);
				//存放正确的返回参数CODE--1
				 result.put("reason", "生成成功");
				mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE,result);
			} catch (Exception e) {
				//存放错误的返回参数CODE--500
				e.printStackTrace();
				mapResult.put(UnivParameter.LOGIC_COLLAPSECODE, UnivParameter.LOGIC_COLLAPSECODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
				return mapResult;
			}
			return mapResult;
	}

    @org.springframework.transaction.annotation.Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public Map<String, Object> confirmfProductSn(String adminId,List<Map<String,Object>>productSnList,Long orderId) {

        Map<String, Object> mapResult = new HashMap<String, Object>();
        Map<String, Object> result = new HashMap<String, Object>();
        if (adminId == null || productSnList == null) {
            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, "店员编号和订单信息不可为空");
            return mapResult;
        }
        Admin admin = adminService.find(Long.parseLong(adminId));
        if (admin == null) {
            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, "该店员不存在");
            return mapResult;
        }

        Organization organization = organizationDao.find(Long.parseLong(admin.getOrganization()));
        Order order = orderService.find(orderId);
        Member member = order.getMember();
        ShippingMethod shippingMethod = order.getShippingMethod();
        Set<StockLog> stockLogs = new HashSet<StockLog>();
        StockLog stockLog = null;
        try {
            //productSn商品串号，ordersId订单号
            for (int i = 0; i < productSnList.size(); i++) {
                Map<String, Object> m = (Map<String, Object>) productSnList.get(i);
                Long productId = Long.valueOf(m.get("productId").toString());
                String productSn = m.get("productSn").toString();

                if (productSnList.size() != order.getQuantity()) {
                    mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
                    mapResult.put(UnivParameter.ERRORMESSAGE, "缺少商品序列号，请填写完整");
                    return mapResult;
                }
                if (!order.getStatus().equals(Order.Status.daiziti)) {
                    mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
                    mapResult.put(UnivParameter.ERRORMESSAGE, "订单不为待自提状态，商品不能发货");
                    return mapResult;
                }

                    /*循环校验串号是否正确*/
                stockLog = stockLogService.findBySnAndProId(productDao.find(productId), productSn, organization);
                if (stockLog != null && stockLog.getState().equals("1")) {
                    stockLogs.add(stockLog);
                } else {
                    //存放错误的返回参数CODE--0
                    mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
                    mapResult.put(UnivParameter.ERRORMESSAGE, "串号为" + productSn + "的商品不存在，请重新输入");
                    return mapResult;
                }
            }

            //生成发货单
            Shipping shipping = new Shipping();
            shipping.setAddress(order.getAddress());
            shipping.setArea(order.getArea());
            shipping.setSn(snDao.generate(Sn.Type.shipping));
            shipping.setConsignee(order.getConsignee());
            shipping.setOrder(order);
            shipping.setOperator(admin.getName());
            shipping.setPhone(order.getPhone());
            shipping.setZipCode(order.getZipCode());
            shipping.setShippingMethod(shippingMethod.getName());

            shipping.setStockLogs(stockLogs);

            shippingService.save(shipping);

            for (StockLog stockLog1 : stockLogs) {
                //修改库存的商品属性
                stockLog1.setType(StockLog.Type.stockOut);
                stockLog1.setState("10");
                stockLogService.update(stockLog1);
                //获得商品
                Product product = stockLog1.getProduct();
                //将每个商品序列号生成发货项
                ShippingItem shippingItem = new ShippingItem();
                shippingItem.setIsDelivery(false);
                shippingItem.setName(product.getName());
                shippingItem.setProduct(product);
                shippingItem.setSn(product.getSn());
                shippingItem.setQuantity(1);
                shippingItem.setShipping(shipping);
                shippingItem.setSpecifications(product.getSpecifications());
                shippingItemService.save(shippingItem);
            }

            //为客户赠送积分
            Long pointHas = order.getRewardPoint();
            if (pointHas > 0) {
                Long point = member.getPoint();
                member.setPoint(pointHas + point);
                memberService.update(member);
                // 创建一条积分添加记录
                PointLog pointLog = new PointLog();
                pointLog.setBalance(point + pointHas);
                pointLog.setCredit(pointHas);
                pointLog.setDebit(0l);
                pointLog.setType(PointLog.Type.reward);
                pointLog.setMember(member);
                pointLogService.save(pointLog);
            }

            //修改订单数据
            Date nowDate = new Date();
            order.setStatus(Order.Status.completed);
            order.setCompleteDate(nowDate);
            order.setShippedQuantity(order.getQuantity());
            order.setAdmin(admin);
            String weeb = DateUtil.getWeekOfDate(nowDate);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
            StringBuffer buffer = new StringBuffer(weeb);
            buffer.append("[").append(simpleDateFormat.format(nowDate));
            order.setIsExchangePoint(true);
            order.setCollectTime(buffer.toString());
            orderService.update(order);
            //创建订单记录接口
            OrderLog orderLog = new OrderLog();
            orderLog.setModifyDate(new Date());
            orderLog.setOperator(admin);
            orderLog.setType(OrderLog.Type.receive);
            orderLog.setOrder(order);
            orderLog.setVersion(0L);
            orderLogService.save(orderLog);

            //存放正确的返回参数CODE--1
            result.put("reason", "录入成功，订单已完结");
            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, result);
        } catch (Exception e) {
            e.printStackTrace();
            //存放错误的返回参数CODE--500
            mapResult.put(UnivParameter.LOGIC_COLLAPSECODE, UnivParameter.LOGIC_COLLAPSECODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
            return mapResult;
        }
        return mapResult;
    }

    @org.springframework.transaction.annotation.Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
    @Override
	public Map<String, Object> sendGoods(String adminId, List<Map<String, Object>> productSnList, String trackingSn, String deliveryCorpId, BigDecimal freight, String orderId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		String resultReson ="";
		if(productSnList==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "商品序列号不可为空");
			return mapResult;
		}
		if(trackingSn==null||freight==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "物流公司运费或运单号不可为空");
			return mapResult;
		}
		Admin admin = adminService.find( Long.parseLong(adminId));
		if(admin==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "员工不存在");
			return mapResult;
		}
		//获得物流公司		
		DeliveryCorp delivery = deliveryCorpService.find(Long.parseLong(deliveryCorpId));
		if(delivery==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "物流公司不存在");
			return mapResult;
		}

        Organization organization = organizationDao.find(Long.parseLong(admin.getOrganization()));
        Order order =orderService.find( Long.parseLong(orderId));
        Set<StockLog> stockLogs = new HashSet<StockLog>();

        if (productSnList.size() != order.getQuantity()) {
            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, "缺少商品序列号，请填写完整");
            return mapResult;
        }

        if (order.getStatus().equals(Order.Status.daiziti) || order.getStatus().equals(Order.Status.pendingShipment) ) {

        }else{
            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, "订单不为待自提或待发货状态，商品不能发货");
            return mapResult;
        }

		try {
			Map<Product,List<StockLog>> stockMap = new HashMap<Product,List<StockLog>>();
            //productSn商品串号，ordersId订单号
            for (int i = 0; i < productSnList.size(); i++) {
                Map<String, Object> m = (Map<String, Object>) productSnList.get(i);
                String productId = m.get("productId").toString();
                String productSn = m.get("productSn").toString();

                /*循环校验串号是否正确*/
                StockLog stockLog = stockLogService.findBySnAndProId(productDao.find(Long.parseLong(productId)), productSn, organization);

                if (stockLog != null && stockLog.getState().equals("1")) {
                    stockLogs.add(stockLog);
                    if(!stockMap.containsKey(stockLog.getProduct())){
    					stockMap.put(stockLog.getProduct(), new ArrayList<StockLog>());
    				}
                    stockMap.get(stockLog.getProduct()).add(stockLog);
                    
                    stockLog.setState("5");
    				stockLogService.update(stockLog);
                } else {
                    //存放错误的返回参数CODE--0
                    mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
                    mapResult.put(UnivParameter.ERRORMESSAGE, "串号为" + productSn + "的商品不存在，请重新输入");
                    return mapResult;
                }
            }

            //向快递100发送订阅请求
            Boolean resp = KuaidiUtils.Express(delivery.getCode(), "江苏盐城", order.getArea() + order.getAddress(), trackingSn );
            if (!resp) {
                //存放错误的返回参数CODE--0
                mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
                mapResult.put(UnivParameter.ERRORMESSAGE, "物流单号输入错误，请重新输入");
                return mapResult;
            }

            //生成发货单
            Shipping shipping = new Shipping();
            shipping.setAddress(order.getAddress());
            shipping.setArea(order.getArea());
            shipping.setConsignee(order.getConsignee());
            shipping.setOrder(order);
            shipping.setOperator(admin.getName());
            shipping.setSn(snDao.generate(Sn.Type.shipping));
            shipping.setTrackingNo(trackingSn);
            shipping.setDeliveryCorp(delivery.getName());
            shipping.setFreight(freight);
            shipping.setDeliveryCorpCode(delivery.getCode());
            shipping.setDeliveryCorpUrl(delivery.getUrl());
            shipping.setPhone(order.getPhone());
            shipping.setZipCode(order.getZipCode());
            shipping.setShippingMethod(order.getShippingMethod());
            shipping.setStockLogs(stockLogs);

            shippingService.save(shipping);
            
            Iterator<Entry<Product, List<StockLog>>> it = stockMap.entrySet()
					.iterator();
			while (it.hasNext()) {
				ShippingItem shippingItem = new ShippingItem();
				Map.Entry<Product, List<StockLog>> pair = (Map.Entry<Product, List<StockLog>>) it
						.next();
				shippingItem.setIsDelivery(true);
				shippingItem.setName(pair.getKey().getName());
				shippingItem.setProduct(pair.getKey());
				shippingItem.setSn(pair.getKey().getSn());
				shippingItem.setQuantity(pair.getValue().size());
				shippingItem.setShipping(shipping);
				shippingItem.setSpecifications(pair.getKey().getSpecifications());
				shippingItemService.save(shippingItem);
				it.remove();
			}

            //修改订单数据
            order.setCompleteDate(new Date());
            order.setFreight(freight);
            order.setStatus(Order.Status.pendingReceive);
            order.setAdmin(admin);
            
            order.setShippedQuantity(order.getQuantity());
            orderService.update(order);
            //创建订单记录接口
            OrderLog orderLog = new OrderLog();
            orderLog.setModifyDate(new Date());
            orderLog.setOperator(admin);
            orderLog.setType(OrderLog.Type.shipping);
            orderLog.setOrder(order);
            orderLog.setVersion(0L);
            orderLogService.save(orderLog);

            result.put("reason", resultReson);
            //存放正确的返回参数CODE--1
            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, result);
        } catch (Exception e) {
			//存放错误的返回参数CODE--500
			mapResult.put(UnivParameter.LOGIC_COLLAPSECODE, UnivParameter.LOGIC_COLLAPSECODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;
	}

    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public Map<String, Object> orderPayment(String orderId, String adminId, BigDecimal payMoney, String channel, String ip) {

        Charge charge = null;
        String payUrl = null;
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object>  result = new HashMap<String,Object>();
		String paymentName=" ";
		String paymentId="";

		Admin admin = adminService.find( Long.parseLong(adminId));

		if(admin==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "员工数据错误");
			return mapResult;
		}

		//计算订单应付的总金额
		double  orderAmount =0.00;

        Order order = orderService.find(Long.parseLong(orderId));
        if (order == null) {
            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, "组合商品错误");
            return mapResult;
        }
        List<String> productIds = new ArrayList<String>();
        List<OrderItem> orderItemList = order.getOrderItems();

        for(OrderItem orderItem : orderItemList){
            productIds.add( orderItem.getSn() );
        }

        
        if (order.getStatus() != Order.Status.pendingPayment) {
            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, "订单错误");
            return mapResult;
        }
        orderAmount = order.getAmount().subtract(order.getAmountPaid()).doubleValue() + orderAmount;

        //校验订单总价和传入价格是否一致
		if(payMoney.doubleValue()!=orderAmount){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "商品价格不正确");
			return mapResult;
		}

        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", payMoney.multiply(new BigDecimal(100)).intValue());//单位为分
        Map<String, String> app = new HashMap<String, String>();
        app.put("id", CommonParameter.PINGXX_APPID);
        chargeMap.put("app", app);
        chargeMap.put("currency", "cny");
        String description = "订单支付";
        chargeMap.put("subject", org.apache.commons.lang.StringUtils.abbreviate(description.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5 ]", ""), 60));
        chargeMap.put("body", org.apache.commons.lang.StringUtils.abbreviate(description.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5 ]", ""), 600));

        chargeMap.put("channel", channel);

        /*支付记录号*/
        PaymentLog paymentLog = new PaymentLog();
        paymentLog.setType(PaymentLog.Type.payment);
        paymentLog.setAmount(order.getAmount().subtract(order.getAmountPaid()));
        paymentLog.setFee(BigDecimal.ZERO);//TODO 手续费默认为空
        paymentLog.setMember(order.getMember());
        paymentLog.setOrder(order);
        paymentLog.setSn(snDao.generate(Sn.Type.paymentLog));
        paymentLog.setStatus(PaymentLog.Status.wait );
        paymentLogService.save(paymentLog);

        PaymentMethod paymentMethod = null ;
		try{
            switch (channel) {
                case "alipay_qr":{
                    //支付宝扫码支付
                    paymentId = "alipayQrPaymentPlugin";
                    paymentName = "支付宝扫码支付";
                    break;
                }
                case "wx_pub_qr":{
                    //微信扫码支付
                    String productId = "";
                    Map<String , Object> aluMap = new HashMap<String , Object>();
                    if( null != productIds && productIds.size() > 0 ){
                        productId = StringUtils.join(productIds, "_").replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5 ]", "");
                    }else{
                        productId = (""+new Date().getTime()).replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5 ]", "");
                    }

                    if( productId.length() > 30 ){
                        productId = productId.substring(0 ,30);
                    }

                    aluMap.put("product_id" , productId );
                    chargeMap.put("extra",aluMap);
                    paymentId = "wxPubQrPaymentPlugin";
                    paymentName = "微信扫码支付";
                    break;
                }
                
                case "cash_line":{
                    //现金支付
                    paymentId = "cashline";
                    paymentName = "现金支付";
                    break;
                }
                case "upacp_line":{
                    //现金支付
                    paymentId = "upacpline";
                    paymentName = "银联支付";
                    break;
                }
            }

            chargeMap.put("order_no", paymentLog.getSn());
            /* Charge 支付 */
            chargeMap.put("client_ip", ip );

            //微信 或者 支付才生成返回值
            if( channel.equals( "alipay_qr" ) || channel.equals( "wx_pub_qr" ) ){
                result.put("reason", "支付二维码生成成功");
                paymentMethod = paymentMethodService.findByName("在线支付");
                charge = Charge.create(chargeMap);

                //发起交易请求
                if (channel.equals("alipay_qr")) {
                    Map<String , Object> credential = charge.getCredential();
                    payUrl = credential.get("alipay_qr").toString();
                } else if (channel.equals("wx_pub_qr")) {
                    Map<String , Object> credential = charge.getCredential();
                    payUrl = credential.get("wx_pub_qr").toString();
                }
                order.setStatus(Order.Status.pendingPayment);

                //更新
                paymentLog.setPingXXSN(charge.getId());
                order.setIsAllocatedStock(false);
                paymentLog.setStatus(PaymentLog.Status.wait);

            }else{
                result.put("reason", "支付成功");
            	paymentLog.setStatus(PaymentLog.Status.success);
                paymentMethod = paymentMethodService.findByName("线下支付");
                // 银联 ， 现金 直接保存订单
                order.setAmountPaid(order.getAmount());
                order.setIsAllocatedStock(true);
                order.setStatus(Order.Status.daiziti);
                PointBehavior pointBehavior = pointBehaviorService.findByName("订单支付");
                if (pointBehavior != null) {
                    Long point = pointBehaviorService.addPoint(order.getMember(), pointBehavior, PointLog.Type.payment);
                    if (point != 0) {
                        Member member = order.getMember();
                        member.setPoint(member.getPoint() + point);
                        memberService.update(member);
                        //创建一条积分记录
                        PointLog pointLog = new PointLog();
                        pointLog.setType(PointLog.Type.payment);
                        pointLog.setCredit(point);
                        pointLog.setDebit(0L);
                        pointLog.setBalance(member.getPoint());
                        pointLog.setMember(member);
                        pointLogService.save(pointLog);
                    }
                }
                //商品已分配库存数量	增加
                List<OrderItem> orderItems = order.getOrderItems();
                for (OrderItem orderItem : orderItems) {
                    Product product = orderItem.getProduct();
                    product.setAllocatedStock(product.getAllocatedStock() + orderItem.getQuantity());
                    productService.update(product);
                }
            }
            
            paymentLog.setPaymentPluginName(paymentName);
            paymentLog.setPaymentPluginId(paymentId);
            paymentLogService.update( paymentLog );

            //修改订单数据
            order.setAdmin(admin);

            order.setPaymentMethod(paymentMethod);
            order.setPaymentMethodName(paymentMethod.getName());
            order.setPaymentMethodType(paymentMethod.getType());
            orderService.update(order);

            //创建订单记录接口
            OrderLog orderLog = new OrderLog();
            orderLog.setModifyDate(new Date());
            orderLog.setOperator(admin);
            orderLog.setType(OrderLog.Type.payment);
            orderLog.setOrder(order);
            orderLog.setVersion(0L);
            orderLogService.save(orderLog);

			//存放正确的返回参数CODE--1
			mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE,result);

		} catch (Exception e) {
			//存放错误的返回参数CODE--500
            e.printStackTrace();
			mapResult.put(UnivParameter.LOGIC_COLLAPSECODE, UnivParameter.LOGIC_COLLAPSECODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}

        mapResult.put("payUrl" , payUrl);
        mapResult.put("paymentLogSn" , paymentLog.getSn() );
		return mapResult;
	}

	/**
	 *  订单确认下单
	 * author: 南金豆
	 *   date: 2015-12-7 下午5:13:26
	 * @param promotion 把组合商品的Id逗号区分形成String
	 * @param promotionPrice 组合商品促销总价（将选择的组合商品的促销价格加在一起）
	 * @param goodsType 商品类型【1：合约机  2：非合约机】
	 * @param phoneNumber 客户手机号
	 * @param adminId 员工编号
	 * @param goodsId 商品编号
	 * @param goodsMarketPrice 商品价格
	 * @param goodsNumber 商品数量
	 * @param phoneCode 短信验证码
	 * @param contractTelfare 套餐预存费用（合约机）
	 * @param memberCardCode 客户身份证
	 * @param contractId	套餐编号
	 * @param memberName 客户姓名
	 * @param phoneNumId 手机号编号（选择的手机号）
	 * @param cardFrontImge 证件照正面
	 * @param cardBackImge 证件照反面
	 * @return
	 */
	@Override
	public Map<String, Object> orderConfirmation(String phoneNumber,String adminId, String goodsId, BigDecimal goodsMarketPrice,int goodsNumber, 
			String phoneCode, String registerIp,String promotion, BigDecimal promotionPrice, int goodsType,BigDecimal contractTelfare, String memberCardCode, String
			contractId,String memberName, String phoneNumId, String cardFrontImge,String cardBackImge) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		//检验数据是否为空		
		if(goodsType<1||goodsType>2||adminId==null||goodsMarketPrice==null||goodsNumber==0||phoneCode==null||phoneNumber==null||goodsId==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "参数错误");
			return mapResult;
		}
		//检验非合约机是否有不属于它的信息		
		if(goodsType==2&&(contractId!=null||phoneNumId!=null||memberName!=null||memberCardCode!=null||cardFrontImge!=null||cardBackImge!=null)){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "非合约无法选择套餐，手机号");
			return mapResult;
		}
		//检验操作人,商品是否存在
		Admin admin= adminService.find(Long.parseLong(adminId));
		Product  product = productService.find(Long.parseLong(goodsId));
		if(product==null&&admin==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "商品或员工不正确");
			return mapResult;
		}
		String shopId=admin.getOrganization();
		if(Integer.parseInt(productDao.selectGoodsStock(goodsId,shopId))<goodsNumber){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "该门店库存不足");
			return mapResult;
		}
		//门店信息
		Organization organization=organizationService.find(Long.parseLong(admin.getOrganization()));
		//获得缓存Id
//		String  codeId = phoneNumber +adminId;
		//检验短信验证码 默认1234不验证
//		if(!phoneCode.equals(String.valueOf(cacheService.getCache(CommonParameter.CACHE_NAME_VCODE ,codeId)))){
//			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
//			mapResult.put(UnivParameter.ERRORMESSAGE, "验证码错误");
//			return mapResult;
//		}
		cacheService.removeCache(CommonParameter.CACHE_NAME_VCODE ,phoneNumber +adminId);
		//根据手机号获取会员信息				
		Member member = memberService.findByPhone(phoneNumber);
		try{
			//订单价格
			BigDecimal ordersPrice=BigDecimal.ZERO;
			BigDecimal goodsNum=new BigDecimal(goodsNumber);
			//商品促销方式
			Set<Promotion> tempPromotions = product.getValidPromotions();
			//定义计算公式
			String priceExpression =null;
			//定义赠品的Id		
			List<String> promotionBind =new ArrayList<String>();
			//定义选择组合的商品
			String productId[]=new String[]{};
			//定义商品价格			
			BigDecimal goodsPrice =goodsMarketPrice;
			//定义组合商品价格			
			BigDecimal goodsBindPrice =BigDecimal.ZERO;
			//计算赠送积分
			Long rewardPoint =  product.getRewardPoint() * goodsNumber;
			//定义组合存在		
			PromotionBind bind = null;
			List<PromotionBindVO> bindVos=null;
			String imple ="1";
			//定义商品的数量
			int	ordersQuantity=0;
			//计算订单价格
			if(promotionPrice!=null){
				ordersPrice = goodsMarketPrice.multiply(goodsNum).add(promotionPrice);
			}else{
				ordersPrice = goodsMarketPrice.multiply(goodsNum);
			}
			for (Promotion p : tempPromotions) {
				//单品促销的时候进行计算				
				if (p.getPromotionType().equals(Promotion.Type.productPromotion)&&(p.getMinimumPrice()==null||goodsPrice.compareTo(p.getMinimumPrice())>=1)&&p.getPriceExpression()!=null) {
					priceExpression = p.getPriceExpression();
					Binding binding = new Binding();
					binding.setVariable("price", goodsMarketPrice);
					GroovyShell gs = new GroovyShell(binding);
					Object value = gs.evaluate(priceExpression);//执行groovyshell脚本
					goodsPrice=  (BigDecimal)value;
					if(promotionPrice!=null){
						ordersPrice = goodsPrice.multiply(goodsNum).subtract(promotionPrice);
					}else{
						ordersPrice = goodsPrice.multiply(goodsNum);
					}
				} 
				//计算满减
				if(p.getPromotionType().equals(Promotion.Type.fullCutPromotion)&&(p.getMinimumPrice()==null||goodsPrice.compareTo(p.getMinimumPrice())>=1)&& p.getPriceExpression()!=null){
					priceExpression = p.getPriceExpression();
					Binding binding = new Binding();
					binding.setVariable("price", ordersPrice);
					GroovyShell gs = new GroovyShell(binding);
					Object value = gs.evaluate(priceExpression);//执行groovyshell脚本
					ordersPrice =  (BigDecimal)value;
				}
				//计算满赠的商品，统计赠品的Id
				if(p.getPromotionType().equals(Promotion.Type.fullGiftsPromotion)&&(p.getMinimumPrice()==null||goodsPrice.compareTo(p.getMinimumPrice())>=1)){
					Set<Product> gifts = p.getGifts();
					for( Product gift : gifts ){
						promotionBind.add(gift.getId().toString());
					}
				}	
				//计算买增的商品，统计赠品的Id
				if(p.getPromotionType().equals(Promotion.Type.buyGiftsPromotion)){
					Set<Product> gifts = p.getGifts();
					for( Product gift : gifts ){
						promotionBind.add(gift.getId().toString());
					}
				}
				//获取组合					
				if (p.getPromotionType().equals(Promotion.Type.bindPromotion)) {
					bind = p.getPromotionBind();
				}
			}
			// 存在绑定促销的商品 将商品分成最多五个商品 返回给 ftl
			if (null != bind) {
				 bindVos = new ArrayList<PromotionBindVO>();
				PromotionBindVO tempVO = null;
				// 绑定促销商品1
				if (bind.getProduct1() != null) {
					tempVO = new PromotionBindVO();
					tempVO.setProductId( bind.getProduct1().getId() );
					tempVO.setPrice(bind.getPrice1());
					bindVos.add(tempVO);
				}
				// 绑定促销商品2
				if (bind.getProduct2() != null) {
					tempVO = new PromotionBindVO();
					tempVO.setProductId( bind.getProduct2().getId() );
					tempVO.setPrice(bind.getPrice2());
					bindVos.add(tempVO);
				}
				// 绑定促销商品3
				if (bind.getProduct3() != null) {
					tempVO = new PromotionBindVO();
					tempVO.setProductId( bind.getProduct3().getId() );
					tempVO.setPrice(bind.getPrice3());
					bindVos.add(tempVO);
				}
				// 绑定促销商品4
				if (bind.getProduct4() != null) {
					tempVO = new PromotionBindVO();
					tempVO.setProductId( bind.getProduct4().getId() );
					tempVO.setPrice(bind.getPrice4());
					bindVos.add(tempVO);
				}
				// 绑定促销商品5
				if (bind.getProduct5() != null) {
					tempVO = new PromotionBindVO();
					tempVO.setProductId( bind.getProduct5().getId() );
					tempVO.setPrice(bind.getPrice5());
					bindVos.add(tempVO);
				}
			}
			//计算数目			
			if(promotion!=null){
				productId=promotion.split(",");
				ordersQuantity=productId.length	+ promotionBind.size()+goodsNumber;
			}else{
				ordersQuantity= promotionBind.size()+goodsNumber;
			}
			//获取配送方式
			ShippingMethod  shippingMethod = shippingMethodService.findByName("上门自提");
			//无合约机信息			
			if(contractId==null&&phoneNumId==null&&memberName==null&&memberCardCode==null&&cardFrontImge==null&&cardBackImge==null){
				if(member==null){
					//创建新的member
					Member one= new Member();
					 //创建随机用户名
				   	String username = RandomValueUtils.randomStringValue(4) + phoneNumber;
					 //创建随机密码	
				   	String password = RandomValueUtils.randomStringValue(6) ;
					one.setUsername(username);
					one.setPassword(DigestUtils.md5Hex(password));
					one.setPhone(phoneNumber);
					one.setNickname(null);
					one.setPoint(0L);
					one.setAmount(BigDecimal.ZERO);
					one.setIsEnabled(true);
					one.setIsLocked(false);
					one.setLoginFailureCount(0);
					one.setRegisterIp(registerIp);
					one.setSafeKey(null);
					one.setExperience(0L);
					one.setGodMoney(BigDecimal.ZERO);
					one.setSimple(imple);
					one.setMemberRank(memberRankService.findDefault());
					memberService.save(one);
					//新建order表
					Order order = new Order();
					order.setAdmin(admin);
					order.setType(Order.Type.general);
					order.setMember(one);
					order.setStatus(Order.Status.pendingPayment);
					order.setAmount(ordersPrice);
					order.setOrganization(organization);
					order.setShippingMethod(shippingMethod);
					order.setShippedQuantity(0);
					order.setIsDelete(false);
					order.setDeposit(BigDecimal.ZERO);
					order.setSn(snDao.generate(Sn.Type.order));
					order.setFee(BigDecimal.ZERO);
					order.setFreight(BigDecimal.ZERO);
					order.setShippingMethodName(shippingMethod.getName());
					order.setPromotionDiscount(BigDecimal.ZERO);
					order.setOffsetAmount(BigDecimal.ZERO);
					order.setAmountPaid(BigDecimal.ZERO);
					order.setRefundAmount(BigDecimal.ZERO);
					order.setGodMoneyCount(BigDecimal.ZERO);
					order.setGodMoneyPaid(BigDecimal.ZERO);
					order.setIsAllocatedStock(false);
					order.setIsUseCouponCode(false);
					order.setPhone(phoneNumber);
					order.setTax(BigDecimal.ZERO);
					order.setCouponCode(null);
					order.setOffsetAmount(BigDecimal.ZERO);
					order.setRewardPoint(rewardPoint);
					order.setExchangePoint(0L);
					order.setWeight(0);
					order.setIsOnline(false);
					order.setCouponDiscount(BigDecimal.ZERO);
					order.setExchangePoint(0L);
					order.setQuantity(ordersQuantity);
					order.setShippedQuantity(0);
					order.setReturnedQuantity(0);
					order.setIsExchangePoint(false);
					order.setVersion(0L);
					order.setPrice(goodsMarketPrice);
					orderService.save(order);
					//组合商品和组合商品的价格不为空							
					if(promotion!=null&&promotionPrice!=null){
						//根据组合商品的Id填到订单项中	
						productId=promotion.split(",");
						for(int i=0;i<productId.length;i++){
							Product  goods= productService.find(Long.parseLong(productId[i]));
							if(goods==null){
								orderService.delete(order);
								memberService.delete(one);
								mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
								mapResult.put(UnivParameter.ERRORMESSAGE, "组合商品错误");
								return mapResult;
							}else{
								for(int y=0;y<bindVos.size();y++){
									if(bindVos.get(y).getProductId()==Long.parseLong(productId[i])){
										goodsBindPrice=bindVos.get(y).getPrice();
									}
								}
								OrderItem orderItems = new OrderItem();
								orderItems.setIsDelivery(false);
								orderItems.setName(goods.getName());
								orderItems.setIsReview(false);
								orderItems.setOrder(order);
								orderItems.setPrice(goodsBindPrice);
								orderItems.setWeight(goods.getWeight());
                                orderItems.setVersion(0L);
								orderItems.setProduct(goods);
								orderItems.setSn(goods.getSn());
								orderItems.setQuantity(1);
								orderItems.setShippedQuantity(0);
								orderItems.setType(goods.getType());
                                orderItems.setThumbnail(goods.getThumbnail());
								orderItems.setReturnedQuantity(0);
                                orderItems.setReturnPrice( goodsBindPrice );
								orderItemService.save(orderItems);
							}
						}
					}
					//将商品的赠品添加到订单项中										
					if(promotionBind.size()>0){					
							for(int i=0;i<promotionBind.size();i++){
								Product  goods= productService.find(Long.parseLong(promotionBind.get(i)));
								OrderItem orderItems = new OrderItem();
								orderItems.setIsDelivery(false);
								orderItems.setName(goods.getName());
								orderItems.setIsReview(false);
								orderItems.setOrder(order);
								orderItems.setPrice(BigDecimal.ZERO);
								orderItems.setWeight(goods.getWeight());
								orderItems.setProduct(goods);
								orderItems.setSn(goods.getSn());
								orderItems.setQuantity(1);
								orderItems.setShippedQuantity(0);
								orderItems.setType(goods.getType());
								orderItems.setReturnedQuantity(0);
                                orderItems.setThumbnail(goods.getThumbnail());
                                orderItems.setReturnPrice( BigDecimal.ZERO );
								orderItemService.save(orderItems);
						}
					}
					//将商品添加到订单项中	
					OrderItem orderItems = new OrderItem();
					orderItems.setIsDelivery(false);
					orderItems.setName(product.getName());
					orderItems.setIsReview(false);
					orderItems.setOrder(order);
					orderItems.setPrice(goodsPrice);
					orderItems.setWeight(product.getWeight());
					orderItems.setVersion(0L);
					orderItems.setProduct(product);
					orderItems.setSn(product.getSn());
					orderItems.setQuantity(goodsNumber);

                    orderItems.setThumbnail(product.getThumbnail());
                    orderItems.setReturnPrice( goodsPrice.divide(new BigDecimal( goodsNumber ) , 2, BigDecimal.ROUND_DOWN) );

					orderItems.setShippedQuantity(0);
					orderItems.setType(product.getType());
					orderItems.setReturnedQuantity(0);
					orderItemService.save(orderItems);
					//建一条订单记录
					OrderLog  orderLog = new OrderLog();
					orderLog.setModifyDate(new Date());
					orderLog.setOperator(admin);
					orderLog.setType(OrderLog.Type.create);
					orderLog.setOrder(order);
					orderLog.setVersion(0L);
					orderLogService.save(orderLog);
					result.put("reason", "下单成功，欢迎你下次再来。");
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, result);
					//有会员					
				}else{
					//新建order表
					Order order = new Order();
					order.setAdmin(admin);
					order.setType(Order.Type.general);
					order.setMember(member);
					order.setStatus(Order.Status.pendingPayment);
					order.setAmount(ordersPrice);
					order.setOrganization(organization);
					order.setShippingMethod(shippingMethod);
					order.setShippedQuantity(0);
					order.setIsDelete(false);
					order.setDeposit(BigDecimal.ZERO);
					order.setSn(snDao.generate(Sn.Type.order));
					order.setFee(BigDecimal.ZERO);
					order.setFreight(BigDecimal.ZERO);
					order.setShippingMethodName(shippingMethod.getName());
					order.setPromotionDiscount(BigDecimal.ZERO);
					order.setOffsetAmount(BigDecimal.ZERO);
					order.setAmountPaid(BigDecimal.ZERO);
					order.setRefundAmount(BigDecimal.ZERO);
					order.setGodMoneyCount(BigDecimal.ZERO);
					order.setGodMoneyPaid(BigDecimal.ZERO);
					order.setIsAllocatedStock(false);
					order.setIsUseCouponCode(false);
					order.setPhone(phoneNumber);
					order.setTax(BigDecimal.ZERO);
					order.setCouponCode(null);
					order.setIsOnline(false);
					order.setOffsetAmount(BigDecimal.ZERO);
					order.setRewardPoint(rewardPoint);
					order.setExchangePoint(0L);
					order.setWeight(0);
					order.setCouponDiscount(BigDecimal.ZERO);
					order.setExchangePoint(0L);
					order.setQuantity(ordersQuantity);
					order.setShippedQuantity(0);
					order.setReturnedQuantity(0);
					order.setIsExchangePoint(false);
					order.setVersion(0L);
					order.setPrice(goodsMarketPrice);
					orderService.save(order);
					//组合商品和组合商品的价格不为空							
					if(promotion!=null&&promotionPrice!=null){
						//根据组合商品的Id填到订单项中	
						productId=promotion.split(",");
						for(int i=0;i<productId.length;i++){
							Product  goods= productService.find(Long.parseLong(productId[i]));
							if(goods==null){
								orderService.delete(order);
								mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
								mapResult.put(UnivParameter.ERRORMESSAGE, "组合商品错误");
								return mapResult;
							}else{
								for(int y=0;y<bindVos.size();y++){
									if(bindVos.get(y).getProductId()==Long.parseLong(productId[i])){
										goodsBindPrice=bindVos.get(y).getPrice();
									}
								}
								OrderItem orderItems = new OrderItem();
								orderItems.setIsDelivery(false);
								orderItems.setName(goods.getName());
								orderItems.setIsReview(false);
								orderItems.setOrder(order);
								orderItems.setPrice(goodsBindPrice);
								orderItems.setWeight(goods.getWeight());
								orderItems.setThumbnail(goods.getThumbnail());
								orderItems.setVersion(0L);
								orderItems.setProduct(goods);
								orderItems.setSn(goods.getSn());
								orderItems.setQuantity(1);
								orderItems.setShippedQuantity(0);
								orderItems.setType(goods.getType());
								orderItems.setReturnedQuantity(0);
								orderItemService.save(orderItems);
							}
						}
					}
					//将商品的赠品添加到订单项中										
					if(promotionBind.size()>0){					
							for(int i=0;i<promotionBind.size();i++){
								Product  goods= productService.find(Long.parseLong(promotionBind.get(i)));
								OrderItem orderItems = new OrderItem();
								orderItems.setIsDelivery(false);
								orderItems.setName(goods.getName());
								orderItems.setIsReview(false);
								orderItems.setOrder(order);
								orderItems.setPrice(BigDecimal.ZERO);
								orderItems.setWeight(goods.getWeight());
								orderItems.setVersion(0L);
								orderItems.setProduct(goods);
								orderItems.setSn(goods.getSn());
								orderItems.setQuantity(1);
								orderItems.setShippedQuantity(0);
								orderItems.setThumbnail(goods.getThumbnail());
								orderItems.setType(goods.getType());
								orderItems.setReturnedQuantity(0);
								orderItemService.save(orderItems);
						}
					}
					//将商品添加到订单项中	
					OrderItem orderItems = new OrderItem();
					orderItems.setIsDelivery(false);
					orderItems.setName(product.getName());
					orderItems.setIsReview(false);
					orderItems.setOrder(order);
					orderItems.setPrice(goodsPrice);
					orderItems.setWeight(product.getWeight());
					orderItems.setThumbnail(product.getThumbnail());
					orderItems.setVersion(0L);
					orderItems.setProduct(product);
					orderItems.setSn(product.getSn());
					orderItems.setQuantity(goodsNumber);
					orderItems.setShippedQuantity(0);
					orderItems.setType(product.getType());
					orderItems.setReturnedQuantity(0);
					orderItemService.save(orderItems);
					//建一条订单记录
					OrderLog  orderLog = new OrderLog();
					orderLog.setModifyDate(new Date());
					orderLog.setOperator(admin);
					orderLog.setType(OrderLog.Type.create);
					orderLog.setOrder(order);
					orderLog.setVersion(0L);
					orderLogService.save(orderLog);
					result.put("reason", "恭喜你下单成功");
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, result);
				}
			//有合约机信息	
			}else if(contractId!=null&&phoneNumId!=null&&memberName!=null&&memberCardCode!=null&&cardFrontImge!=null&&cardBackImge!=null){
				PhoneNumber phoneNum =phoneNumberService.find(Long.parseLong(phoneNumId));
				ContractItem	contractItem =contractItemService.find(Long.parseLong(contractId));
				if(phoneNum.getIsSold().equals(PhoneNumber.PHONESTATE.sold)||phoneNum.getIsSold().equals(PhoneNumber.PHONESTATE.locked)){
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, "号码已经被出售或被锁定");
					return mapResult;	
				}
				if(!contractItem.getState()){
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, "该手机套餐不能使用");
					return mapResult;	
				}
				ordersPrice = ordersPrice.add(phoneNum.getPrice());
				//根据手机号获取会员信息				
				if(member==null){
					//创建新的member
					Member one= new Member();
					 //创建随机用户名
				   	String username = RandomValueUtils.randomStringValue(4) + phoneNumber;
					 //创建随机密码	
				   	String password = RandomValueUtils.randomStringValue(6) ;
					one.setUsername(username);
					one.setPassword(DigestUtils.md5Hex(password));
					one.setPhone(phoneNumber);
					one.setNickname(null);
					one.setPoint(0L);
					one.setAmount(BigDecimal.ZERO);
					one.setIsEnabled(true);
					one.setIsLocked(false);
					one.setLoginFailureCount(0);
					one.setRegisterIp(registerIp);
					one.setSafeKey(null);
					one.setExperience(0L);
					one.setGodMoney(BigDecimal.ZERO);
					one.setSimple(imple);
					one.setMemberRank(memberRankService.findDefault());
					memberService.save(one);
					//新建order表
					Order order = new Order();
					order.setAdmin(admin);
					order.setType(Order.Type.general);
					order.setMember(one);
					order.setStatus(Order.Status.pendingPayment);
					order.setAmount(ordersPrice);
					order.setOrganization(organization);
					order.setShippingMethod(shippingMethod);
					order.setShippedQuantity(0);
					order.setIsDelete(false);
					order.setIsOnline(false);
					order.setDeposit(BigDecimal.ZERO);
					order.setSn(snDao.generate(Sn.Type.order));
					order.setFee(BigDecimal.ZERO);
					order.setFreight(BigDecimal.ZERO);
					order.setShippingMethodName(shippingMethod.getName());
					order.setPromotionDiscount(BigDecimal.ZERO);
					order.setOffsetAmount(BigDecimal.ZERO);
					order.setAmountPaid(BigDecimal.ZERO);
					order.setRefundAmount(BigDecimal.ZERO);
					order.setGodMoneyCount(BigDecimal.ZERO);
					order.setGodMoneyPaid(BigDecimal.ZERO);
					order.setIsAllocatedStock(false);
					order.setIsUseCouponCode(false);
					order.setPhone(phoneNumber);
					order.setTax(BigDecimal.ZERO);
					order.setCouponCode(null);
					order.setOffsetAmount(BigDecimal.ZERO);
					order.setRewardPoint(rewardPoint);
					order.setExchangePoint(0L);
					order.setWeight(0);
					order.setCouponDiscount(BigDecimal.ZERO);
					order.setExchangePoint(0L);
					order.setQuantity(ordersQuantity);
					order.setShippedQuantity(0);
					order.setReturnedQuantity(0);
					order.setIsExchangePoint(false);
					order.setVersion(0L);
					order.setPrice(goodsMarketPrice);
					orderService.save(order);
					//组合商品和组合商品的价格不为空							
					if(promotion!=null&&promotionPrice!=null){
						//根据组合商品的Id填到订单项中	
						productId=promotion.split(",");
						for(int i=0;i<productId.length;i++){
							Product  goods= productService.find(Long.parseLong(productId[i]));
							if(goods==null){
								orderService.delete(order);
								memberService.delete(one);
								mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
								mapResult.put(UnivParameter.ERRORMESSAGE, "组合商品错误");
								return mapResult;
							}else{
								for(int y=0;y<bindVos.size();y++){
									if(bindVos.get(y).getProductId()==Long.parseLong(productId[i])){
										goodsBindPrice=bindVos.get(y).getPrice();
									}
								}
								OrderItem orderItems = new OrderItem();
								orderItems.setIsDelivery(false);
								orderItems.setName(goods.getName());
								orderItems.setIsReview(false);
								orderItems.setOrder(order);
								orderItems.setPrice(goodsBindPrice);
								orderItems.setWeight(goods.getWeight());
								orderItems.setVersion(0L);
								orderItems.setProduct(goods);
								orderItems.setSn(goods.getSn());
								orderItems.setQuantity(1);
								orderItems.setShippedQuantity(0);
								orderItems.setThumbnail(goods.getThumbnail());
								orderItems.setType(goods.getType());
								orderItems.setReturnedQuantity(0);
								orderItemService.save(orderItems);
							}
						}
					}
					//将商品的赠品添加到订单项中										
					if(promotionBind.size()>0){					
							for(int i=0;i<promotionBind.size();i++){
								Product  goods= productService.find(Long.parseLong(promotionBind.get(i)));
								OrderItem orderItems = new OrderItem();
								orderItems.setIsDelivery(false);
								orderItems.setName(goods.getName());
								orderItems.setIsReview(false);
								orderItems.setOrder(order);
								orderItems.setPrice(BigDecimal.ZERO);
								orderItems.setWeight(goods.getWeight());
								orderItems.setVersion(0L);
								orderItems.setProduct(goods);
								orderItems.setSn(goods.getSn());
								orderItems.setQuantity(1);
								orderItems.setShippedQuantity(0);
								orderItems.setThumbnail(goods.getThumbnail());
								orderItems.setType(goods.getType());
								orderItems.setReturnedQuantity(0);
								orderItemService.save(orderItems);
						}
					}
					//修改手机号码的信息
					phoneNum.setIsSold(PhoneNumber.PHONESTATE.sold);
					phoneNumberService.update(phoneNum);
					//将商品添加到订单项中	
					OrderItem orderItems = new OrderItem();
					orderItems.setIsDelivery(false);
					orderItems.setName(product.getName());
					orderItems.setIsReview(false);
					orderItems.setOrder(order);
					orderItems.setPrice(goodsPrice);
					orderItems.setWeight(product.getWeight());
					orderItems.setVersion(0L);
					orderItems.setProduct(product);
					orderItems.setSn(product.getSn());
					orderItems.setQuantity(goodsNumber);
					orderItems.setShippedQuantity(0);
					orderItems.setType(product.getType());
					orderItems.setReturnedQuantity(0);
					orderItemService.save(orderItems);
					//建一条订单记录
					OrderLog  orderLog = new OrderLog();
					orderLog.setModifyDate(new Date());
					orderLog.setOperator(admin);
					orderLog.setType(OrderLog.Type.create);
					orderLog.setOrder(order);
					orderLog.setVersion(0L);
					orderLogService.save(orderLog);
					//建一个新的购买人—套餐-手机号的记录		
					ContractPhoneNumberUserInfo  contractPhoneNumberUserInfo =new ContractPhoneNumberUserInfo();
					contractPhoneNumberUserInfo.setCardBackImg(cardBackImge);
					contractPhoneNumberUserInfo.setCardCode(memberCardCode);
					contractPhoneNumberUserInfo.setCardFrontImg(cardFrontImge);
					contractPhoneNumberUserInfo.setContractItem(contractItem);
					contractPhoneNumberUserInfo.setMember(one);
					contractPhoneNumberUserInfo.setPhoneNumber(phoneNum);
					contractPhoneNumberUserInfo.setUserName(username);
					contractPhoneNumberUserInfo.setVersion(0L);
					contractPhoneNumberUserInfoService.save(contractPhoneNumberUserInfo);
					result.put("reason", "合约机下单成功，欢迎你下次再来");
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, result);
					//有会员					
				}else{
					//新建order表
					Order order = new Order();
					order.setAdmin(admin);
					order.setType(Order.Type.general);
					order.setMember(member);
					order.setStatus(Order.Status.pendingPayment);
					order.setAmount(ordersPrice);
					order.setOrganization(organization);
					order.setShippingMethod(shippingMethod);
					order.setShippedQuantity(0);
					order.setIsDelete(false);
					order.setDeposit(BigDecimal.ZERO);
					order.setSn(snDao.generate(Sn.Type.order));
					order.setFee(BigDecimal.ZERO);
					order.setFreight(BigDecimal.ZERO);
					order.setShippingMethodName(shippingMethod.getName());
					order.setPromotionDiscount(BigDecimal.ZERO);
					order.setOffsetAmount(BigDecimal.ZERO);
					order.setAmountPaid(BigDecimal.ZERO);
					order.setRefundAmount(BigDecimal.ZERO);
					order.setGodMoneyCount(BigDecimal.ZERO);
					order.setGodMoneyPaid(BigDecimal.ZERO);
					order.setIsAllocatedStock(false);
					order.setIsUseCouponCode(false);
					order.setPhone(phoneNumber);
					order.setTax(BigDecimal.ZERO);
					order.setCouponCode(null);
					order.setIsOnline(false);
					order.setOffsetAmount(BigDecimal.ZERO);
					order.setRewardPoint(rewardPoint);
					order.setExchangePoint(0L);
					order.setWeight(0);
					order.setCouponDiscount(BigDecimal.ZERO);
					order.setExchangePoint(0L);
					order.setQuantity(ordersQuantity);
					order.setShippedQuantity(0);
					order.setReturnedQuantity(0);
					order.setIsExchangePoint(false);
					order.setVersion(0L);
					order.setPrice(goodsMarketPrice);
					orderService.save(order);
					//组合商品和组合商品的价格不为空							
					if(promotion!=null&&promotionPrice!=null){
						//根据组合商品的Id填到订单项中	
						productId=promotion.split(",");
						for(int i=0;i<productId.length;i++){
							Product  goods= productService.find(Long.parseLong(productId[i]));
							if(goods==null){
								orderService.delete(order);
								mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
								mapResult.put(UnivParameter.ERRORMESSAGE, "组合商品错误");
								return mapResult;
							}else{
								for(int y=0;y<bindVos.size();y++){
									if(bindVos.get(y).getProductId()==Long.parseLong(productId[i])){
										goodsBindPrice=bindVos.get(y).getPrice();
									}
								}
								OrderItem orderItems = new OrderItem();
								orderItems.setIsDelivery(false);
								orderItems.setName(goods.getName());
								orderItems.setIsReview(false);
								orderItems.setOrder(order);
								orderItems.setPrice(goodsBindPrice);
								orderItems.setWeight(goods.getWeight());
								orderItems.setVersion(0L);
								orderItems.setProduct(goods);
								orderItems.setSn(goods.getSn());
								orderItems.setQuantity(1);
								orderItems.setShippedQuantity(0);
								orderItems.setType(goods.getType());
								orderItems.setReturnedQuantity(0);
								orderItemService.save(orderItems);
							}
						}
					}
					//将商品的赠品添加到订单项中										
					if(promotionBind.size()>0){					
							for(int i=0;i<promotionBind.size();i++){
								Product  goods= productService.find(Long.parseLong(promotionBind.get(i)));
								OrderItem orderItems = new OrderItem();
								orderItems.setIsDelivery(false);
								orderItems.setName(goods.getName());
								orderItems.setIsReview(false);
								orderItems.setOrder(order);
								orderItems.setPrice(BigDecimal.ZERO);
								orderItems.setWeight(goods.getWeight());
								orderItems.setVersion(0L);
								orderItems.setProduct(goods);
								orderItems.setSn(goods.getSn());
								orderItems.setQuantity(1);
								orderItems.setShippedQuantity(0);
								orderItems.setType(goods.getType());
								orderItems.setReturnedQuantity(0);
								orderItemService.save(orderItems);
						}
					}
					//修改手机号码的信息
					phoneNum.setIsSold(PhoneNumber.PHONESTATE.sold);
					phoneNumberService.update(phoneNum);
					//将商品添加到订单项中	
					OrderItem orderItems = new OrderItem();
					orderItems.setIsDelivery(false);
					orderItems.setName(product.getName());
					orderItems.setIsReview(false);
					orderItems.setOrder(order);
					orderItems.setPrice(goodsPrice);
					orderItems.setWeight(product.getWeight());
					orderItems.setVersion(0L);
					orderItems.setProduct(product);
					orderItems.setSn(product.getSn());
					orderItems.setQuantity(goodsNumber);
					orderItems.setShippedQuantity(0);
					orderItems.setType(product.getType());
					orderItems.setReturnedQuantity(0);
					orderItemService.save(orderItems);
					//建一条订单记录
					OrderLog  orderLog = new OrderLog();
					orderLog.setModifyDate(new Date());
					orderLog.setOperator(admin);
					orderLog.setType(OrderLog.Type.create);
					orderLog.setOrder(order);
					orderLog.setVersion(0L);
					orderLogService.save(orderLog);
					//建一个新的购买人—套餐-手机号的记录		
					ContractPhoneNumberUserInfo  contractPhoneNumberUserInfo =new ContractPhoneNumberUserInfo();
					contractPhoneNumberUserInfo.setCardBackImg(cardBackImge);
					contractPhoneNumberUserInfo.setCardCode(memberCardCode);
					contractPhoneNumberUserInfo.setCardFrontImg(cardFrontImge);
					contractPhoneNumberUserInfo.setContractItem(contractItem);
					contractPhoneNumberUserInfo.setMember(member);
					contractPhoneNumberUserInfo.setPhoneNumber(phoneNum);
					contractPhoneNumberUserInfo.setUserName(memberName);
					contractPhoneNumberUserInfo.setVersion(0L);
					contractPhoneNumberUserInfoService.save(contractPhoneNumberUserInfo);
					result.put("reason", "恭喜你合约机下单成功");
					mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
					mapResult.put(UnivParameter.ERRORMESSAGE, result);
					}
			}else{
				mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
				mapResult.put(UnivParameter.ERRORMESSAGE, "合约机选择参数");
				return mapResult;
			}
		} catch (Exception e) {
			//存放错误的返回参数CODE--500
			mapResult.put(UnivParameter.LOGIC_COLLAPSECODE, UnivParameter.LOGIC_COLLAPSECODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;
	}

    /**
     * 自提订单选取
     * author: 南金豆
     *
     * @param shopId 门店编号
     * @param ordersType 订单状态【1：未完成(线上未付款配送方式自提)，2：已完成】
     * @param pageLoadType 分页方向【1：向上加载，2：向下加载】
     * @param pageRowsCount 每页加载数据条数
     * @param ordersId 起始订单编号
     * @param num 检索内容
     * @param type 检索方式【1：手机号 2：顶订单号】
     * @return
     * update 施长成
     */
	@Override
    public Map<String, Object> changeTakeOrders(String shopId, int ordersType,int pageLoadType, int pageRowsCount, String ordersId, String num, int type){
        return posIndexDao.changeTakeOrders(shopId,ordersType,pageLoadType , pageRowsCount , ordersId, num,type);
	}

	@Override
	public Map<String, Object> packetRefuseSeason() {
		Map<String,Object> refuseSeasonOne =new HashMap<String,Object>();
		Map<String,Object> refuseSeasonTwo =new HashMap<String,Object>();
		Map<String,Object> refuseSeasonThree =new HashMap<String,Object>();
		Map<String,Object> refuseSeasonFour =new HashMap<String,Object>();
		Map<String,Object> mapResult =new HashMap<String,Object>();
		List<Object> result =new ArrayList<Object>();
		Setting setting =SystemUtils.getSetting();
	   	//获取红包拒绝原因一
	   	String packetRefuseSeasonOne = setting.getPacketRefuseSeasonOne();
	   	//获取红包拒绝原因二
	   	String packetRefuseSeasonTwo = setting.getPacketRefuseSeasonTwo();
	   	//获取红包拒绝原因三
	   	String packetRefuseSeasonThree = setting.getPacketRefuseSeasonThree();
	   	//获取红包拒绝原因四
	   	String packetRefuseSeasonFour= setting.getPacketRefuseSeasonFour();
	   	refuseSeasonOne.put("refuseSeasonId", "1");
	   	refuseSeasonOne.put("refuseSeason", packetRefuseSeasonOne);
	   	result.add(refuseSeasonOne);
	   	refuseSeasonTwo.put("refuseSeasonId", "2");
	   	refuseSeasonTwo.put("refuseSeason", packetRefuseSeasonTwo);
	   	result.add(refuseSeasonTwo);
	   	refuseSeasonThree.put("refuseSeasonId", "3");
	   	refuseSeasonThree.put("refuseSeason", packetRefuseSeasonThree);
	   	result.add(refuseSeasonThree);
	   	refuseSeasonFour.put("refuseSeasonId", "4");
	   	refuseSeasonFour.put("refuseSeason", packetRefuseSeasonFour);
	   	result.add(refuseSeasonFour);
		//存放正确的返回参数CODE--1
		mapResult.put(UnivParameter.CODE,UnivParameter.DATA_CORRECTCODE);
		mapResult.put(UnivParameter.DATA,result);
		return mapResult;
	}

	@Override
	public Map<String, Object> getDeliveryCorpList() {
		return  posIndexDao.getDeliveryCorpList();
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
    public boolean finishedPayment(String paymentLogSn) throws ChannelException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        boolean isOk = false;
        PaymentLog paymentLog = paymentLogService.findBySn( paymentLogSn );
        if( paymentLog.getStatus()==PaymentLog.Status.wait ) {
            Charge charge = Charge.retrieve(paymentLog.getPingXXSN());
            if (null != charge) {

                boolean paid = charge.getPaid();
                String channel = charge.getChannel();

                //费用支付帐号信息，支付宝帐号或者微信帐号
                String buyerAcount = null;

                switch (channel) {
                    case "alipay_qr": {
                        System.out.println("支付宝扫码支付");
                        buyerAcount = (String) charge.getExtra().get("buyer_account");
                        break;
                    }
                    case "wx_pub_qr": {
                        System.out.println("微信扫码支付");
                        buyerAcount = (String) charge.getExtra().get("buyer_account");
                        break;
                    }
                }

                if (paid) {
                    //支付成功
                    paymentLog.setStatus(PaymentLog.Status.success);
                    paymentLogService.handle(paymentLogSn, PaymentLog.Status.success, buyerAcount);
                    isOk = true;
                } else {
                    //支付失败
                    paymentLog.setStatus(PaymentLog.Status.failure);
                    paymentLogService.handle(paymentLogSn, PaymentLog.Status.failure, buyerAcount);
                }

            }
        }else{
            if(paymentLog.getStatus() == PaymentLog.Status.success){
                isOk = true;
            }
        }
        return isOk;
    }

	@Override
	public Map<String, Object> getShopOrdersByCond(String shopId,
			int ordersType, int pageLoadType, int pageRowsCount,
			String ordersId, String num, int type) {
		return  posIndexDao.getShopOrdersByCond(shopId,ordersType,pageLoadType,pageRowsCount,ordersId,num,type);
	}

	@Override
	public Map<String, Object> orgList() {
		return posIndexDao.orgList();
	}

	@Override
	public Map<String, Object> InventorySearchQuanqu(String selectValue,
			int pageLoadType, int pageRowsCount,String content, int pageType) {
		return posIndexDao.InventorySearchQuanqu(selectValue,pageLoadType,content,pageRowsCount,pageType);
	}
	
	@org.springframework.transaction.annotation.Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public Map<String, Object> updateSendGoods(String adminId,
			List<Map<String, Object>> productSnList,List<Map<String, Object>> orderList1, String trackingSn,
			String deliveryCorpId, BigDecimal freight, String orderId) {
		Map<String,Object> mapResult = new HashMap<String,Object>();
		Map<String,Object> result = new HashMap<String,Object>();
		String resultReson ="";
		if(productSnList==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "商品序列号不可为空");
			return mapResult;
		}
		if(trackingSn==null||freight==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "物流公司运费或运单号不可为空");
			return mapResult;
		}
		Admin admin = adminService.find( Long.parseLong(adminId));
		if(admin==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "员工不存在");
			return mapResult;
		}
		//获得物流公司		
		DeliveryCorp delivery = deliveryCorpService.find(Long.parseLong(deliveryCorpId));
		if(delivery==null){
			mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, "物流公司不存在");
			return mapResult;
		}

        Organization organization = organizationDao.find(Long.parseLong(admin.getOrganization()));
        Order order =orderService.find( Long.parseLong(orderId));
        Set<StockLog> stockLogs = new HashSet<StockLog>();

        if (productSnList.size() != order.getQuantity()) {
            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, "缺少商品序列号，请填写完整");
            return mapResult;
        }

//        if (order.getStatus().equals(Order.Status.daiziti) || order.getStatus().equals(Order.Status.pendingShipment) ) {
//
//        }else{
//            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
//            mapResult.put(UnivParameter.ERRORMESSAGE, "订单不为待自提或待发货状态，商品不能发货");
//            return mapResult;
//        }

		try {
			Map<Product,List<StockLog>> stockMap = new HashMap<Product,List<StockLog>>();
			for (int i = 0; i < orderList1.size(); i++) {
                Map<String, Object> m = (Map<String, Object>) productSnList.get(i);
                String productSn = m.get("productSn").toString();
                StockLog log = stockLogService.findBySn(productSn);
                log.setState("1");
                stockLogService.update(log);
			}
            //productSn商品串号，ordersId订单号
            for (int i = 0; i < productSnList.size(); i++) {
                Map<String, Object> m = (Map<String, Object>) productSnList.get(i);
                String productId = m.get("productId").toString();
                String productSn = m.get("productSn").toString();

                /*循环校验串号是否正确*/
                StockLog stockLog = stockLogService.findBySnAndProId(productDao.find(Long.parseLong(productId)), productSn, organization);

                if (stockLog != null && stockLog.getState().equals("1")) {
                    stockLogs.add(stockLog);
                    if(!stockMap.containsKey(stockLog.getProduct())){
    					stockMap.put(stockLog.getProduct(), new ArrayList<StockLog>());
    				}
                    stockMap.get(stockLog.getProduct()).add(stockLog);
                    
                    stockLog.setState("5");
    				stockLogService.update(stockLog);
                } else {
                    //存放错误的返回参数CODE--0
                    mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
                    mapResult.put(UnivParameter.ERRORMESSAGE, "串号为" + productSn + "的商品不存在，请重新输入");
                    return mapResult;
                }
            }

            //向快递100发送订阅请求
            Boolean resp = KuaidiUtils.Express(delivery.getCode(), "江苏盐城", order.getArea() + order.getAddress(), trackingSn );
            if (!resp) {
                //存放错误的返回参数CODE--0
                mapResult.put(UnivParameter.CODE, UnivParameter.DATA_ERRORCODE);
                mapResult.put(UnivParameter.ERRORMESSAGE, "物流单号输入错误，请重新输入");
                return mapResult;
            }
            
            Shipping shipping = shippingService.find(order.getShippings().iterator().next().getId());
            shipping.setTrackingNo(trackingSn);
            shipping.setDeliveryCorp(delivery.getName());
            shipping.setFreight(freight);
            shipping.setDeliveryCorpCode(delivery.getCode());
            shipping.setDeliveryCorpUrl(delivery.getUrl());
            shipping.setOperator(admin.getName());
            shipping.setStockLogs(stockLogs);
            shippingService.update(shipping);
//            //生成发货单
//            Shipping shipping = new Shipping();
//            shipping.setAddress(order.getAddress());
//            shipping.setArea(order.getArea());
//            shipping.setConsignee(order.getConsignee());
//            shipping.setOrder(order);
//            shipping.setOperator(admin.getName());
//            shipping.setSn(snDao.generate(Sn.Type.shipping));
//            shipping.setTrackingNo(trackingSn);
//            shipping.setDeliveryCorp(delivery.getName());
//            shipping.setFreight(freight);
//            shipping.setDeliveryCorpCode(delivery.getCode());
//            shipping.setDeliveryCorpUrl(delivery.getUrl());
//            shipping.setPhone(order.getPhone());
//            shipping.setZipCode(order.getZipCode());
//            shipping.setShippingMethod(order.getShippingMethod());
//            shipping.setStockLogs(stockLogs);
//
//            shippingService.save(shipping);
            
            Iterator<Entry<Product, List<StockLog>>> it = stockMap.entrySet()
					.iterator();
            List<ShippingItem> shippingItem2 = shipping.getShippingItems();
            for (ShippingItem shippingItem : shippingItem2) {
            	while (it.hasNext()) {
//    				ShippingItem shippingItem = new ShippingItem();
    				Map.Entry<Product, List<StockLog>> pair = (Map.Entry<Product, List<StockLog>>) it
    						.next();
    				shippingItem.setIsDelivery(true);
    				shippingItem.setName(pair.getKey().getName());
    				shippingItem.setProduct(pair.getKey());
    				shippingItem.setSn(pair.getKey().getSn());
    				shippingItem.setQuantity(pair.getValue().size());
    				shippingItem.setSpecifications(pair.getKey().getSpecifications());
    				shippingItemService.update(shippingItem);
    				it.remove();
    			}
			}
			
            //修改订单数据
            order.setCompleteDate(new Date());
            order.setFreight(freight);
            order.setStatus(Order.Status.pendingReceive);
            order.setAdmin(admin);
            
            order.setShippedQuantity(order.getQuantity());
            orderService.update(order);
            //创建订单记录接口
            OrderLog orderLog = new OrderLog();
            orderLog.setModifyDate(new Date());
            orderLog.setOperator(admin);
            orderLog.setType(OrderLog.Type.shipping);
            orderLog.setOrder(order);
            orderLog.setVersion(0L);
            orderLogService.save(orderLog);

            result.put("reason", resultReson);
            //存放正确的返回参数CODE--1
            mapResult.put(UnivParameter.CODE, UnivParameter.DATA_CORRECTCODE);
            mapResult.put(UnivParameter.ERRORMESSAGE, result);
        } catch (Exception e) {
			//存放错误的返回参数CODE--500
			mapResult.put(UnivParameter.LOGIC_COLLAPSECODE, UnivParameter.LOGIC_COLLAPSECODE);
			mapResult.put(UnivParameter.ERRORMESSAGE, e.getMessage());
			return mapResult;
		}
		return mapResult;
	}
}
