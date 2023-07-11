package com.shoppingMall;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.shoppingMall.constant.EnableConstantEnum;
import com.shoppingMall.constant.OrderStateEnum;
import com.shoppingMall.dao.*;
import com.shoppingMall.entity.po.*;
import com.shoppingMall.pay.core.constant.MerchantTypeEnum;
import com.shoppingMall.pay.vippay.dao.VipCardDao;
import com.shoppingMall.pay.vippay.entity.po.VipCard;
import com.shoppingMall.properties.SelfConfigPropertiesBean;
import com.shoppingMall.utils.PasswordUtil;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 对数据进行初始化，如果有步骤配置错了，可以从第一步再开始执行一遍
 *
 * @author : xingxiaojian
 * @version : v1.0
 * @date : 2023/3/26
 */
@SpringBootTest
public class 初始化配置 {
    /**
     * 首先第一步，就是进行配置，配置好后执行
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String placeholder = "###{%s}";
        //----------------------------------------------------------------------------------------------------
        // 备份文件路径，用于清除掉配置文件（如果一开始如果配置错了，修改好再运行就行了）
        //----------------------------------------------------------------------------------------------------
        Path propertiesBakPath = Path.of("src/main/resources/config/bak/application.properties");
        Path ymlBakPath = Path.of("src/main/resources/config/bak/application.yml");
        Path bannerBakPath = Path.of("src/main/resources/config/bak/banner.txt");
        Path configClassBakPath = Path.of("src/main/resources/config/bak/JasyptConfiguration.java");
        //----------------------------------------------------------------------------------------------------
        // 需要配置的文件路径
        //----------------------------------------------------------------------------------------------------
        Path propertiesPath = Path.of("src/main/resources/application.properties");
        Path ymlPath = Path.of("src/main/resources/application.yml");
        Path bannerPath = Path.of("src/main/resources/banner.txt");
        Path configClassPath = Path.of("src/main/java/com/shoppingMall/config/JasyptConfiguration.java");
        Path sqlPath = Path.of("src/main/resources/config/shopping-mall.sql");
        //----------------------------------------------------------------------------------------------------
        //----------------------------------------------------------------------------------------------------
        //----------------------------------------------------------------------------------------------------
        // 下面的这些内容就需要填你自己的
        // (如果不知道怎么配置请访问：https://gitee.com/xiaomaomi-xj/shopping-mall/tree/master/2.%E5%89%8D%E7%AB%AF%E5%8A%A0%E5%90%8E%E7%AB%AF%EF%BC%88%E7%BA%AF%E5%89%8D%E5%8F%B0%EF%BC%89/%E5%90%8E%E7%AB%AF/shopping-mall)
        //----------------------------------------------------------------------------------------------------
        //----------------------------------------------------------------------------------------------------
        //-------------------------------------------sql
        //你的sql账号(根据你的需要，一般不用改)
        String sqlUser = "root";
        //你的sql密码
        String sqlPassword = "你自己的sql密码";
        //你的sql地址(根据你的需要，一般不用改)
        String sqlHost = "localhost";
        //你的sql端口(根据你的需要，一般不用改)
        String sqlPort = "3306";
        //-------------------------------------------redis
        //redis地址(根据你的需要，一般不用改)
        String redisHost = "localhost";
        //redis端口(根据你的需要，一般不用改)
        String redisPort = "6379";
        //redis密码
        String redisPassword = "你自己的redis密码";
        //-------------------------------------------邮箱
        //邮箱账号（发送验证码就是通过这个邮箱发送）
        String emailUser = "你自己的邮箱号码";
        //邮箱授权码
        String emailPassword = "你自己的邮箱授权码";
        //邮箱主机地址（这个根据平台不同，地址不同，建议直接使用163邮箱，这里就不用改了）
        String emailHost = "smtp.163.com";
        //邮箱端口号（根据ssl端口号也不一样，建议直接使用163邮箱，这里就不用改了）
        String emailPort = "465";
        //-------------------------------------------self
        //你的商城名字(例如：一人之下)
        String storeName = "你自己的商城名字";
        //对密码加密的盐（随便字母数字等，例如：xiaomaomi）
        String passwordSalt = "xiaomaomi";
        //配置文件加密后的包裹名字（建议用大写字母，汉字等等，，也可以是你的名字，例如：防偷窥）
        String configClassName = "防偷窥";
        //你的配置文件密码（随便写，字母数字等等都行，例如：xiaomaomi-xj）
        String propertiesPassword = "xiaomaomi-xj";
        //-------------------------------------------微信
        //微信测试公帐号（appID）
        String wechatAppId = "你自己的微信测试公众号appId";
        //微信测试公帐号（appsecret）
        String wechatAppSecret = "你自己的微信测试公众号appsecret";
        //微信测试公帐号回调地址，这里就不用公网，直接在同一局域网下（比如你手机给电脑开个热点），然后填写在这个局域网下你的电脑的ip地址就行了
        String wechatRedirectUrl = "http://" + "你自己的电脑ip地址" + ":8088";
        //----------------------------------------------------------------------------------------------------
        //----------------------------------------------------------------------------------------------------
        //----------------------------------------------------------------------------------------------------
        // 下方就是代码执行初始化文件
        //----------------------------------------------------------------------------------------------------
        Runtime.getRuntime().exec(String.format("cmd.exe /c mysql -h%s -P%s -u%s -p%s < %s", sqlHost, sqlPort, sqlUser, sqlPassword, new File(sqlPath.toUri()).getAbsolutePath()));
        System.out.println("如果执行第二步报错，请先单独cmd执行这条语句："+ String.format("mysql -h%s -P%s -u%s -p < %s", sqlHost, sqlPort, sqlUser, new File(sqlPath.toUri()).getAbsolutePath()));
		String propertiesBakText = Files.readString(propertiesBakPath, StandardCharsets.UTF_8);
        Files.writeString(propertiesPath, propertiesBakText, StandardCharsets.UTF_8);
        String ymlBakText = Files.readString(ymlBakPath, StandardCharsets.UTF_8);
        Files.writeString(ymlPath, ymlBakText, StandardCharsets.UTF_8);
        String bannerBakText = Files.readString(bannerBakPath, StandardCharsets.UTF_8);
        Files.writeString(bannerPath, bannerBakText, StandardCharsets.UTF_8);
        String configClassBakText = Files.readString(configClassBakPath, StandardCharsets.UTF_8);
        Files.writeString(configClassPath, configClassBakText, StandardCharsets.UTF_8);
        String propertiesText = Files.readString(propertiesPath, StandardCharsets.UTF_8);
        propertiesText = propertiesText.replace(String.format(placeholder, "propertiesPassword"), propertiesPassword);
        Files.writeString(propertiesPath, propertiesText, StandardCharsets.UTF_8);
        String configClassText = Files.readString(configClassPath, StandardCharsets.UTF_8);
        configClassText = configClassText.replace(String.format(placeholder, "configClassName"), configClassName);
        Files.writeString(configClassPath, configClassText, StandardCharsets.UTF_8);
        String bannerText = Files.readString(bannerPath, StandardCharsets.UTF_8);
        bannerText = bannerText.replace(String.format(placeholder, "storeName"), storeName);
        Files.writeString(bannerPath, bannerText, StandardCharsets.UTF_8);
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(propertiesPassword);
        redisPassword = configClassName + "(" + textEncryptor.encrypt(redisPassword) + ")";
        sqlPassword = configClassName + "(" + textEncryptor.encrypt(sqlPassword) + ")";
        emailPassword = configClassName + "(" + textEncryptor.encrypt(emailPassword) + ")";
        passwordSalt = configClassName + "(" + textEncryptor.encrypt(passwordSalt) + ")";
        wechatAppId = configClassName + "(" + textEncryptor.encrypt(wechatAppId) + ")";
        wechatAppSecret = configClassName + "(" + textEncryptor.encrypt(wechatAppSecret) + ")";
        String ymlText = Files.readString(ymlPath, StandardCharsets.UTF_8);
        ymlText = ymlText
                .replace(String.format(placeholder, "sqlUser"), sqlUser)
                .replace(String.format(placeholder, "sqlPassword"), sqlPassword)
                .replace(String.format(placeholder, "sqlHost"), sqlHost)
                .replace(String.format(placeholder, "sqlPort"), sqlPort)
                .replace(String.format(placeholder, "redisHost"), redisHost)
                .replace(String.format(placeholder, "redisPort"), redisPort)
                .replace(String.format(placeholder, "redisPassword"), redisPassword)
                .replace(String.format(placeholder, "emailUser"), emailUser)
                .replace(String.format(placeholder, "emailPassword"), emailPassword)
                .replace(String.format(placeholder, "emailHost"), emailHost)
                .replace(String.format(placeholder, "emailPort"), emailPort)
                .replace(String.format(placeholder, "storeName"), storeName)
                .replace(String.format(placeholder, "passwordSalt"), passwordSalt)
                .replace(String.format(placeholder, "wechatAppId"), wechatAppId)
                .replace(String.format(placeholder, "wechatAppSecret"), wechatAppSecret)
                .replace(String.format(placeholder, "wechatRedirectUrl"), wechatRedirectUrl)
                .replace(String.format(placeholder, "configClassName"), configClassName);
        Files.writeString(ymlPath, ymlText, StandardCharsets.UTF_8);
    }

    //=======================================================================================
    //=======================================================================================
    //                       接下来就是往数据库插入内容
    //=======================================================================================
    //=======================================================================================

    @Autowired
    MerchantDao merchantDao;
    @Autowired
    VipCardDao vipCardDao;
    @Autowired
    UserDao userDao;
    @Autowired
    ExtraConfigDao extraConfigDao;
    @Autowired
    ContainerModuleDao containerModuleDao;
    @Autowired
    ToggleViewDao toggleViewDao;
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    ImgDao imgDao;
    @Autowired
    OrderDao orderDao;
    @Autowired
    SelfConfigPropertiesBean selfConfigPropertiesBean;

    /**
     * 第二步：商户初始化
     */
    @Test
    public void initMerchant() {
        // 支付宝沙箱配置，觉得麻烦不想用的，可以把这一段注释(从这根线到下面横线的地方)
        //---------------------------------------------------------------------------------------------------------------------------------
        //绑定的商家账号（PID）
        String merchantCode = "你自己沙箱绑定的商户号";
        //商户名字（你也可以换个名字）
        String merchantName = "支付宝沙箱支付";
        //appID
        String appId = "你自己的沙箱的appId";
        //支付宝公钥
        String publicKey = "你自己的沙箱配置的支付宝公钥";
        //应用私钥
        String privateKey = "你自己的沙箱配置的应用私钥";
        //接口内容加密方式
        String enctyptPassword = "你自己的沙箱配置的接口内容加密密码";
        //支付宝网关地址(你可以看看你用的哪一个。新版沙箱和老版沙箱是不同的，而且现在新版的还没做好，直接选择老版的就好)
        String serverUrl = "https://openapi.alipaydev.com/gateway.do";
        //支付结果回调地址，一般情况下不用更改
        String callBackUrl = "http://localhost:8088/shopping-mall/alipay/callback";
        merchantDao.save(new Merchant(
                IdUtil.getSnowflakeNextId(),
                merchantCode,
                merchantName,
                MerchantTypeEnum.ALIPAY.getCode(),
                appId,
                publicKey,
                privateKey,
                enctyptPassword,
                serverUrl,
                callBackUrl
        ));
        //----------------------------------------------------end--------------------------------------------------------------------------
        //vip购物卡支付，无需任何配置
        merchantDao.save(new Merchant(
                IdUtil.getSnowflakeNextId(),
                null,
                "购物卡支付",
                MerchantTypeEnum.VIP_PAY.getCode(),
                null,
                null,
                null,
                null,
                "http://localhost:8088/shopping-mall/vip_pay/to_pay_html",
                "http://localhost:8088/shopping-mall/vip_pay/callback"
        ));
    }

    /**
     * 第三步：VIP支付，购物卡数据，可以多执行几次，执行一次生成一个购物卡数据
     */
    @Test
    public void initVipCard() {
        String salt = selfConfigPropertiesBean.getConfig().getPassword().getSalt();
        vipCardDao.save(new VipCard(
                IdUtil.getSnowflakeNextId(),
                //购物卡卡号（随机生成，需要到数据库看）
                IdUtil.getSnowflakeNextId() + RandomUtil.randomString("0123456789", 10),
                //购物卡密码（123456）
                PasswordUtil.getPassword("123456", salt),
                //金额（100000元）
                100000f
        ));
    }

    /**
     * 最后一步：这个就是我们前端配置好的图片，配置文件，在这里进行持久化，直接运行即可
     */
    @Test
    public void initData() throws IOException {
        //数据文件路径
        Path allGoodsDataPath = Path.of("src/main/resources/config/全部的商品数据.json");
        Path userDataPath = Path.of("src/main/resources/config/用户数据.json");
        Path pageDataPath = Path.of("src/main/resources/config/页面上的数据配置.json");
        Path extraDataPath = Path.of("src/main/resources/config/页面上的特殊配置.json");
        String allGoodsDataText = Files.readString(allGoodsDataPath, StandardCharsets.UTF_8);
        String userDataText = Files.readString(userDataPath, StandardCharsets.UTF_8);
        String pageDataText = Files.readString(pageDataPath, StandardCharsets.UTF_8);
        String extraDataText = Files.readString(extraDataPath, StandardCharsets.UTF_8);
        List<goods> goodsList = JSONUtil.toList(allGoodsDataText, goods.class);
        extra extra = JSONUtil.toBean(extraDataText, extra.class);
        List<user> users = JSONUtil.toList(userDataText, user.class);
        pageConfig pageConfig = JSONUtil.toBean(pageDataText, pageConfig.class);
        List<container> containerModuleData = pageConfig.getContainerModuleData();
        List<toggle> toggleViewData = pageConfig.getToggleViewData();
        List<order> orderData = pageConfig.getOrderData();
        rotation rotationChartData = pageConfig.getRotationChartData();
        goodsAd goodsAdData = pageConfig.getGoodsAdData();
        String salt = selfConfigPropertiesBean.getConfig().getPassword().getSalt();
        //商品数据
        List<Goods> goodsPoList = new ArrayList<>();
        for (goods goods : goodsList) {
            long snowflakeNextId = IdUtil.getSnowflakeNextId();
            Goods goodsPo = new Goods(
                    snowflakeNextId,
                    goods.getGoodsName(),
                    goods.getGoodsDescribe(),
                    goods.getGoodsPrice(),
                    goods.getMaxBuyNum(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            goodsPoList.add(goodsPo);
            //图片数据
            List<Img> imgPoList = new ArrayList<>();
            String imgUrl = goods.getImgUrl();
            Img img = new Img(
                    IdUtil.getSnowflakeNextId(),
                    imgUrl,
                    snowflakeNextId,
                    null
            );
            imgPoList.add(img);
            List<String> rotationGoodsImgs = goods.getRotationGoodsImgs();
            rotationGoodsImgs.forEach(v -> {
                if (v.equals(imgUrl)) {
                    img.setRotationGoods(snowflakeNextId);
                } else {
                    Img img1 = new Img(
                            IdUtil.getSnowflakeNextId(),
                            v,
                            null,
                            snowflakeNextId
                    );
                    imgPoList.add(img1);
                }
            });
            imgDao.saveAll(imgPoList);
        }
        //主要界面
        for (container container : containerModuleData) {
            long snowflakeNextId = IdUtil.getSnowflakeNextId();
            ContainerModule containerModule = new ContainerModule(
                    snowflakeNextId,
                    container.getTitleName()
            );
            Goods goods = goodsPoList.get(container.getSpecialGoodsInfo() - 1);
            goods.setContainerSpecialGoods(snowflakeNextId);
            List<Integer> topGoodsInfo = container.getTopGoodsInfo();
            topGoodsInfo.forEach(v -> {
                Goods goods1 = goodsPoList.get(v - 1);
                goods1.setContainerTopGoods(snowflakeNextId);
            });
            List<Integer> bottomGoodsInfo = container.getBottomGoodsInfo();
            bottomGoodsInfo.forEach(v -> {
                Goods goods1 = goodsPoList.get(v - 1);
                goods1.setContainerBottomGoods(snowflakeNextId);
            });
            containerModuleDao.save(containerModule);
        }
        //标题界面
        for (toggle toggle : toggleViewData) {
            long snowflakeNextId = IdUtil.getSnowflakeNextId();
            ToggleView toggleView = new ToggleView(
                    snowflakeNextId,
                    toggle.getTitleName()
            );
            List<Integer> goodsDatas = toggle.getGoodsDatas();
            goodsDatas.forEach(v -> {
                Goods goods = goodsPoList.get(v - 1);
                goods.setToggleViewGoods(snowflakeNextId);
            });
            toggleViewDao.save(toggleView);
        }
        //轮播图
        List<Integer> goodsDatas = rotationChartData.getGoodsDatas();
        goodsDatas.forEach(v -> {
            Goods goods = goodsPoList.get(v - 1);
            goods.setRotationChartGoods("check");
        });
        //广告数据
        List<Integer> goodsDatas1 = goodsAdData.getGoodsDatas();
        goodsDatas1.forEach(v -> {
            Goods goods = goodsPoList.get(v - 1);
            goods.setAdGoods("check");
        });
        //特殊配置数据
        String registerBgImgUrl = extra.getRegisterBgImgUrl();
        String loginBgImgUrl = extra.getLoginBgImgUrl();
        long snowflakeNextId1 = IdUtil.getSnowflakeNextId();
        long snowflakeNextId2 = IdUtil.getSnowflakeNextId();
        imgDao.save(new Img(
                snowflakeNextId1,
                registerBgImgUrl,
                null,
                null
        ));
        imgDao.save(new Img(
                snowflakeNextId2,
                loginBgImgUrl,
                null,
                null
        ));
        ExtraConfig extraConfig = new ExtraConfig(
                IdUtil.getSnowflakeNextId(),
                extra.isStoreNameHaveSpecial() ? EnableConstantEnum.ON.getCode() : EnableConstantEnum.OFF.getCode(),
                extra.getSpecialText(),
                extra.getStoreName(),
                extra.getPasswordTemplateText(),
                extra.getBelowPageText(),
                snowflakeNextId1,
                snowflakeNextId2
        );
        extraConfigDao.save(extraConfig);
        //用户数据
        for (user user : users) {
            long snowflakeNextId = IdUtil.getSnowflakeNextId();
            User user1 = new User(
                    snowflakeNextId,
                    null,
                    user.getUserName(),
                    user.getUserEmail(),
                    user.getUserSex(),
                    PasswordUtil.getPassword(user.getPassword(), salt)
            );
            //每个用户都要有订单数据
            for (order order : orderData) {
                Goods goods = goodsPoList.get(order.getGoodsData() - 1);
                Order order1 = new Order(
                        IdUtil.getSnowflakeNextId(),
                        snowflakeNextId,
                        goods.getGoodsId(),
                        null,
                        order.getGoodsNum(),
                        order.getShipStatus(),
                        goods.getGoodsPrice() * order.getGoodsNum(),
                        OrderStateEnum.COMPLET.getState(),
                        null
                );
                orderDao.save(order1);
            }
            userDao.save(user1);
        }
        //保存商品数据
        goodsDao.saveAll(goodsPoList);
    }

    //所有顺利运行完后，就可以去ShoppingMallApplication直接启动就行了

    //-------------------------------------------------------------------------------
    // 下面这些内部私有类与json文件相对应，用于做数据的转换，无需在意他们的存在
    //-------------------------------------------------------------------------------

    private static class goods {
        private String imgUrl;
        private String goodsName;
        private String goodsDescribe;
        private float goodsPrice;
        private int maxBuyNum;
        private List<String> rotationGoodsImgs;

        goods(String imgUrl, String goodsName, String goodsDescribe, float goodsPrice, int maxBuyNum, List<String> rotationGoodsImgs) {
            this.imgUrl = imgUrl;
            this.goodsDescribe = goodsDescribe;
            this.goodsName = goodsName;
            this.goodsPrice = goodsPrice;
            this.maxBuyNum = maxBuyNum;
            this.rotationGoodsImgs = rotationGoodsImgs;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsDescribe() {
            return goodsDescribe;
        }

        public void setGoodsDescribe(String goodsDescribe) {
            this.goodsDescribe = goodsDescribe;
        }

        public float getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(float goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public int getMaxBuyNum() {
            return maxBuyNum;
        }

        public void setMaxBuyNum(int maxBuyNum) {
            this.maxBuyNum = maxBuyNum;
        }

        public List<String> getRotationGoodsImgs() {
            return rotationGoodsImgs;
        }

        public void setRotationGoodsImgs(List<String> rotationGoodsImgs) {
            this.rotationGoodsImgs = rotationGoodsImgs;
        }

        @Override
        public String toString() {
            return "goods{" +
                    "imgUrl='" + imgUrl + '\'' +
                    ", goodsName='" + goodsName + '\'' +
                    ", goodsDescribe='" + goodsDescribe + '\'' +
                    ", goodsPrice='" + goodsPrice + '\'' +
                    ", maxBuyNum=" + maxBuyNum +
                    ", rotationGoodsImgs=" + rotationGoodsImgs +
                    '}';
        }
    }

    private static class user {
        private String userName;
        private String userEmail;
        private int userSex;
        private String password;

        public user(String userName, String userEmail, int userSex, String password) {
            this.userName = userName;
            this.userEmail = userEmail;
            this.userSex = userSex;
            this.password = password;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public int getUserSex() {
            return userSex;
        }

        public void setUserSex(int userSex) {
            this.userSex = userSex;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "user{" +
                    "userName='" + userName + '\'' +
                    ", userEmail='" + userEmail + '\'' +
                    ", userSex='" + userSex + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    private static class extra {
        private boolean storeNameHaveSpecial;
        private String specialText;
        private String storeName;
        private String loginBgImgUrl;
        private String registerBgImgUrl;
        private String passwordTemplateText;
        private String belowPageText;

        public extra(boolean storeNameHaveSpecial, String specialText, String storeName, String loginBgImgUrl, String registerBgImgUrl, String passwordTemplateText, String belowPageText) {
            this.storeNameHaveSpecial = storeNameHaveSpecial;
            this.specialText = specialText;
            this.storeName = storeName;
            this.loginBgImgUrl = loginBgImgUrl;
            this.registerBgImgUrl = registerBgImgUrl;
            this.passwordTemplateText = passwordTemplateText;
            this.belowPageText = belowPageText;
        }

        public boolean isStoreNameHaveSpecial() {
            return storeNameHaveSpecial;
        }

        public void setStoreNameHaveSpecial(boolean storeNameHaveSpecial) {
            this.storeNameHaveSpecial = storeNameHaveSpecial;
        }

        public String getSpecialText() {
            return specialText;
        }

        public void setSpecialText(String specialText) {
            this.specialText = specialText;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getLoginBgImgUrl() {
            return loginBgImgUrl;
        }

        public void setLoginBgImgUrl(String loginBgImgUrl) {
            this.loginBgImgUrl = loginBgImgUrl;
        }

        public String getRegisterBgImgUrl() {
            return registerBgImgUrl;
        }

        public void setRegisterBgImgUrl(String registerBgImgUrl) {
            this.registerBgImgUrl = registerBgImgUrl;
        }

        public String getPasswordTemplateText() {
            return passwordTemplateText;
        }

        public void setPasswordTemplateText(String passwordTemplateText) {
            this.passwordTemplateText = passwordTemplateText;
        }

        public String getBelowPageText() {
            return belowPageText;
        }

        public void setBelowPageText(String belowPageText) {
            this.belowPageText = belowPageText;
        }

        @Override
        public String toString() {
            return "extra{" +
                    ", storeNameHaveSpecial=" + storeNameHaveSpecial +
                    ", specialText='" + specialText + '\'' +
                    ", storeName='" + storeName + '\'' +
                    ", loginBgImgUrl='" + loginBgImgUrl + '\'' +
                    ", registerBgImgUrl='" + registerBgImgUrl + '\'' +
                    ", passwordTemplateText='" + passwordTemplateText + '\'' +
                    ", belowPageText='" + belowPageText + '\'' +
                    '}';
        }
    }

    private static class toggle {
        private String titleName;
        private List<Integer> goodsDatas;

        toggle(String titleName, List<Integer> goodsDatas) {
            this.titleName = titleName;
            this.goodsDatas = goodsDatas;
        }

        public List<Integer> getGoodsDatas() {
            return goodsDatas;
        }

        public String getTitleName() {
            return titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }

        public void setGoodsDatas(List<Integer> goodsDatas) {
            this.goodsDatas = goodsDatas;
        }

        @Override
        public String toString() {
            return "toggle{" +
                    "titleName='" + titleName + '\'' +
                    ", goodsDatas=" + goodsDatas +
                    '}';
        }

    }

    private static class rotation {
        private List<Integer> goodsDatas;

        rotation(List<Integer> goodsDatas) {
            this.goodsDatas = goodsDatas;
        }

        public List<Integer> getGoodsDatas() {
            return goodsDatas;
        }

        public void setGoodsDatas(List<Integer> goodsDatas) {
            this.goodsDatas = goodsDatas;
        }

        @Override
        public String toString() {
            return "rotation{" +
                    "goodsDatas=" + goodsDatas +
                    '}';
        }
    }

    private static class goodsAd {
        private List<Integer> goodsDatas;

        goodsAd(List<Integer> goodsDatas) {
            this.goodsDatas = goodsDatas;
        }

        public List<Integer> getGoodsDatas() {
            return goodsDatas;
        }

        public void setGoodsDatas(List<Integer> goodsDatas) {
            this.goodsDatas = goodsDatas;
        }

        @Override
        public String toString() {
            return "goodsAd{" +
                    "goodsDatas=" + goodsDatas +
                    '}';
        }
    }

    private static class container {
        private String titleName;
        private Integer specialGoodsInfo;
        private List<Integer> topGoodsInfo;
        private List<Integer> bottomGoodsInfo;

        public container(String titleName, int specialGoodsInfo, List<Integer> topGoodsInfo, List<Integer> bottomGoodsInfo) {
            this.titleName = titleName;
            this.specialGoodsInfo = specialGoodsInfo;
            this.topGoodsInfo = topGoodsInfo;
            this.bottomGoodsInfo = bottomGoodsInfo;
        }

        public String getTitleName() {
            return titleName;
        }

        public int getSpecialGoodsInfo() {
            return specialGoodsInfo;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }

        public void setSpecialGoodsInfo(Integer specialGoodsInfo) {
            this.specialGoodsInfo = specialGoodsInfo;
        }

        public void setTopGoodsInfo(List<Integer> topGoodsInfo) {
            this.topGoodsInfo = topGoodsInfo;
        }

        public void setBottomGoodsInfo(List<Integer> bottomGoodsInfo) {
            this.bottomGoodsInfo = bottomGoodsInfo;
        }

        public List<Integer> getTopGoodsInfo() {
            return topGoodsInfo;
        }

        public List<Integer> getBottomGoodsInfo() {
            return bottomGoodsInfo;
        }

        @Override
        public String toString() {
            return "container{" +
                    "titleName='" + titleName + '\'' +
                    ", specialGoodsInfo=" + specialGoodsInfo +
                    ", topGoodsInfo=" + topGoodsInfo +
                    ", bottomGoodsInfo=" + bottomGoodsInfo +
                    '}';
        }
    }

    private static class order {
        private int goodsData;
        private int goodsNum;
        private int shipStatus;

        public order(int goodsData, int goodsNum, int shipStatus) {
            this.goodsData = goodsData;
            this.goodsNum = goodsNum;
            this.shipStatus = shipStatus;
        }

        public int getGoodsData() {
            return goodsData;
        }

        public void setGoodsData(int goodsData) {
            this.goodsData = goodsData;
        }

        public void setGoodsNum(int goodsNum) {
            this.goodsNum = goodsNum;
        }

        public void setShipStatus(int shipStatus) {
            this.shipStatus = shipStatus;
        }

        public int getGoodsNum() {
            return goodsNum;
        }

        public int getShipStatus() {
            return shipStatus;
        }

        @Override
        public String toString() {
            return "order{" +
                    "goodsData=" + goodsData +
                    ", goodsNum=" + goodsNum +
                    ", shipStatus=" + shipStatus +
                    '}';
        }
    }

    private static class pageConfig {
        private List<toggle> toggleViewData;
        private rotation rotationChartData;
        private goodsAd goodsAdData;
        private List<container> containerModuleData;
        private List<order> orderData;

        public pageConfig(List<toggle> toggleViewData, rotation rotationChartData, goodsAd goodsAdData, List<container> containerModuleData, List<order> orderData) {
            this.toggleViewData = toggleViewData;
            this.rotationChartData = rotationChartData;
            this.goodsAdData = goodsAdData;
            this.containerModuleData = containerModuleData;
            this.orderData = orderData;
        }

        public List<toggle> getToggleViewData() {
            return toggleViewData;
        }

        public rotation getRotationChartData() {
            return rotationChartData;
        }

        public void setToggleViewData(List<toggle> toggleViewData) {
            this.toggleViewData = toggleViewData;
        }

        public void setRotationChartData(rotation rotationChartData) {
            this.rotationChartData = rotationChartData;
        }

        public void setGoodsAdData(goodsAd goodsAdData) {
            this.goodsAdData = goodsAdData;
        }

        public void setContainerModuleData(List<container> containerModuleData) {
            this.containerModuleData = containerModuleData;
        }

        public void setOrderData(List<order> orderData) {
            this.orderData = orderData;
        }

        public goodsAd getGoodsAdData() {
            return goodsAdData;
        }

        public List<container> getContainerModuleData() {
            return containerModuleData;
        }

        public List<order> getOrderData() {
            return orderData;
        }

        @Override
        public String toString() {
            return "pageConfig{" +
                    "toggleViewData=" + toggleViewData +
                    ", rotationChartData=" + rotationChartData +
                    ", goodsAdData=" + goodsAdData +
                    ", containerModuleData=" + containerModuleData +
                    ", orderData=" + orderData +
                    '}';
        }
    }
}
