//此文件存储所有异步请求所需要的链接地址
//管理员账号相关的接口
class AdminUrls {
    private static baseUrl = "/admin/";
    public static loginUrl = this.baseUrl + "login";
    public static refreshTokenUrl = this.baseUrl + 'refresh-token';
    public static checkLoginUrl = this.baseUrl + "check-login";
    public static modifyPasswordUrl = this.baseUrl + 'modify-password';
}
//聊天url
class ChatBoosUrls {
    private static baseUrl = '/chat_boos/';
    public static allDataUrl = this.baseUrl + "get_all_data";
    public static readChatBoosUrl = this.baseUrl + 'read_admin_chat_boos';
    public static sendChatBoosUrl = this.baseUrl + 'send_admin_chat_boos';
}
//订单url
class OrderUrls {
    private static baseUrl = '/order/';
    public static allDataUrl = this.baseUrl + 'get_all_order_data';
    public static modifyOrderShipStatusUrl = this.baseUrl + 'modify_order_ship_statue';
}
//用户url
class UserUrls {
    private static userBaseUrl = '/user/';
    public static allUserDataUrl = this.userBaseUrl + 'get_all_user';
    public static delUserUrl = this.userBaseUrl + 'del_user';
    private static wechatBaseUrl = '/wechat/';
    public static allWechatDataUrl = this.wechatBaseUrl + 'get_all_wechat';
    public static delWechatUrl = this.wechatBaseUrl + 'del_wechat';
}
//商品url
class GoodsUrls {
    private static baseUrl = '/goods/';
    public static allDataUrl = this.baseUrl + 'get_all_goods';
    public static addGoodsUrl = this.baseUrl + 'add_goods';
    public static modifyGoodsUrl = this.baseUrl + 'modify_goods';
    public static delGoodsUrl = this.baseUrl + 'del_goods';
}
//商户url
class MerchantUrls {
    private static baseUrl = '/merchant/';
    public static adminAllDataUrl = this.baseUrl + 'get_admin_all_merchant';
    public static delMerchantByIdUrl = this.baseUrl + 'del_merchant_by_id';
    public static modifyMerchantUrl = this.baseUrl + 'modify_merchant';
    public static addMerchantUrl = this.baseUrl + 'add_merchant';
}
//特殊配置
class ExtraConfigUrls {
    private static baseUrl = '/extra_config/';
    public static getConfigUrl = this.baseUrl + 'get_extra_config';
    public static fixExtraConfig = this.baseUrl + 'fix_extra_config';
}
//购物卡url
class VipCardUrls {
    private static baseUrl = "/vip-card/";
    public static getAllVipCardUrl = this.baseUrl + 'get-vip-card';
    public static addVipCardUrl = this.baseUrl + 'add-vip-card';
    public static modifyVipCardBalanceUrl = this.baseUrl + 'modify_vip-card-balance';
    public static modifyVipCardPasswordUrl = this.baseUrl + 'modify-vip-card-password';
    public static delVipCardUrl = this.baseUrl + 'del-vip-card';
}
//界面url
class PageManageUrls {
    private static baseUrl = '/page_config/';
    public static getToggleViewUrl = this.baseUrl + 'get_admin_toggle_view';
    public static addToggleViewUrl = this.baseUrl + 'add_toggle_view';
    public static fixToggleViewUrl = this.baseUrl + 'fix_toggle_view';
    public static delToggleViewUrl = this.baseUrl + 'del_toggle_view';
    public static getRotationChartwUrl = this.baseUrl + 'get_admin_rotation_chart';
    public static fixRotationChattUrl = this.baseUrl + 'fix_rotation_chart';
    public static getGoodsAdUrl = this.baseUrl + 'get_admin_goods_ad';
    public static fixGoodsAdUrl = this.baseUrl + 'fix_goods_ad';
    public static getContainerModuleUrl = this.baseUrl + 'get_admin_container_module';
    public static addContainerModuleUrl = this.baseUrl + 'add_container_module';
    public static fixContainerModuleUrl = this.baseUrl + 'fix_container_module';
    public static delContainerModuleUrl = this.baseUrl + 'del_container_module';
}
export {
    AdminUrls,
    ChatBoosUrls,
    OrderUrls,
    UserUrls,
    GoodsUrls,
    MerchantUrls,
    ExtraConfigUrls,
    VipCardUrls,
    PageManageUrls
};