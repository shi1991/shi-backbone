package com.puyuntech.ycmall.util;
/**
 * 
 * 共通参数 
 * Created on 2015-8-27 上午9:44:44 
 * @author 严志森
 * 
 * update 施长成 改名
 */

public class CommonParameter {
		/**
		 * 忘记密码 缓存的key的前缀
		 */
		public static final String PASSWORD = "password_";
		
		
		/**
		 * 用户登录 缓存的key的前缀
		 */
		public static final String MEMBER_LEGIN = "member_";


		/**
		 * 头像图片路径
		 */
		public static String DEFAULT_HEAD_PATH = "/upload/head/";
		
		/**
		 * 用户身份证图片路径
		 */
		public static String DEFAULT_USERCARD_PATH = "/upload/card/";
		
		/**
		 * 评论图片路径
		 */
		public static String DEFAULT_REVIEW_PATH = "/upload/review/";
		
		/**
		 * 用户默认头像
		 */
		public static String USER_LOGO = "/resources/shop/default/img/icon/headPortrait_bj.png";
		
		/**
		 * 用户默认头像
		 */
		public static String DEFAULT_IMAGE = "/resources/shop/default/img/default/default_img_cool_00.png";
		
		/**
		 *  猜你喜欢关键字
		 */
		public static String KEYWORD = "iphone";
		
		/**
		 *  猜你喜欢轮播数量
		 */
		public static int MAYBELOVE_PAGESIZE = 8;
		
		/**
		 * 公用头部的 key
		 */
		public static String COMMON_HEAD_TITLE = "title";
		
		/**
		 * 公用头部 每个单独子页面的 头部名
		 */
		public static String TITLE_ORDER = "订单确认页面";
		
		/**
		 * 我的账户
		 */
		public static String TITLE_MYCOUNT = "我的账户";
		/**
		 * 神人中心
		 */
		public static String TITLE_USERCENTER = "神人中心";
		/**
		 * 个人信息修改
		 */
		public static String TITLE_USERINFO_MODIFY = "个人信息修改";
		
		/**
		 * 商品详情
		 */
		public static String TITLE_GOOD_DETAIL = "商品详情";
		
		/**
		 * 神币充值
		 */
		public static String TITLE_GOD_Exchange = " 神币充值";
		
		/**
		 * 套餐/号码选择
		 */
		public static String TITLE_SELECT_PHONENUMBER = "套餐/号码选择";
		
		/**
		 * 门店分布
		 */
		public static String DRUGSTORE_DISTRIBUTION = "门店分布";
		
		/**
		 * 帮助中心
		 */
		public static String HELP_CENTER = "帮助中心";
		
		/**
		 *收银台
		 */
		public static String CASHIER_DESK= "收银台";
		
		/**
		 * 购物车
		 */
		public static String TITLE_CART = "购物车";
		
		/**
		 * 收藏夹
		 */
		public static String TITLE_FAV_CART = "收藏夹";

        /**
         * APP下载
         */
        public static String TITLE_DOWN_APP = "APP下载";

        /**
         * 秒杀 验证Url 是否有效的key
         */
        public static String SECKILL_KEY  = "seckillKey";
        
        /**
         * 首页 友情链接显示的最大数量
         */
        public static String FRIENDLINK_LIMITCOUNT = "20";
        
        
        /**
         * 密码找回
         */
        public static String TITLE_RE_PASSWORD = "找回密码";
        
        
        /**
         * 缓存 Cache 名称
         */
        public static String CACHE_NAME_VCODE = "emailCodeToken";
        
        /**
         * 登录Token
         */
        public static String LOGIN_TOKEN = "memberLoginToken";
        
        /**
         * 微信扫码支付Id POS数据统计
         */
        public static String WEIXIN_PAYMENT= "wxPubQrPaymentPlugin";
        
        /**
         * 现金支付Id POS数据统计
         */
        public static String CASH_PAYMENT= "cashline";
        
        /**
         * 银联支付 Id POS数据统计
         */									 
        public static String UNION_PAYMENT= "upacpline";
        
        /**
         * 支付宝扫码支付 Id POS数据统计
         */									 
        public static String ALIPAY_PAYMENT= "alipayQrPaymentPlugin";

        /**
         * pingpp 管理平台对应的 API key
         */
        public static String PINGXX_APIKEY = "sk_live_i1uLe1aLWTGKCKaT00X9CuPS";
        /**
         * pingpp 管理平台对应的应用 ID
         */
        public static String PINGXX_APPID = "app_nDmjDCujHOa9bnDu";

        /**
         * 短信 验证码模版 编号（唯一）
         */
        public static String SMSTEMPLATE_VERI_CODE = "63631";

        /**
         * 短信 发货通知模版 编号（唯一）
         */
        public static String SMSTEMPLATE_DELIVER_GOOD = "62247";
}
