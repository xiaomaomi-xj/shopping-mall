var AdminUrls = (function () {
    function AdminUrls() {
    }
    var _a;
    _a = AdminUrls;
    AdminUrls.baseUrl = "/admin/";
    AdminUrls.loginUrl = _a.baseUrl + "login";
    AdminUrls.refreshTokenUrl = _a.baseUrl + 'refresh-token';
    AdminUrls.checkLoginUrl = _a.baseUrl + "check-login";
    AdminUrls.modifyPasswordUrl = _a.baseUrl + 'modify-password';
    return AdminUrls;
}());
var ChatBoosUrls = (function () {
    function ChatBoosUrls() {
    }
    var _b;
    _b = ChatBoosUrls;
    ChatBoosUrls.baseUrl = '/chat_boos/';
    ChatBoosUrls.allDataUrl = _b.baseUrl + "get_all_data";
    ChatBoosUrls.readChatBoosUrl = _b.baseUrl + 'read_admin_chat_boos';
    ChatBoosUrls.sendChatBoosUrl = _b.baseUrl + 'send_admin_chat_boos';
    return ChatBoosUrls;
}());
var OrderUrls = (function () {
    function OrderUrls() {
    }
    var _c;
    _c = OrderUrls;
    OrderUrls.baseUrl = '/order/';
    OrderUrls.allDataUrl = _c.baseUrl + 'get_all_order_data';
    OrderUrls.modifyOrderShipStatusUrl = _c.baseUrl + 'modify_order_ship_statue';
    return OrderUrls;
}());
var UserUrls = (function () {
    function UserUrls() {
    }
    var _d;
    _d = UserUrls;
    UserUrls.userBaseUrl = '/user/';
    UserUrls.allUserDataUrl = _d.userBaseUrl + 'get_all_user';
    UserUrls.delUserUrl = _d.userBaseUrl + 'del_user';
    UserUrls.wechatBaseUrl = '/wechat/';
    UserUrls.allWechatDataUrl = _d.wechatBaseUrl + 'get_all_wechat';
    UserUrls.delWechatUrl = _d.wechatBaseUrl + 'del_wechat';
    return UserUrls;
}());
var GoodsUrls = (function () {
    function GoodsUrls() {
    }
    var _e;
    _e = GoodsUrls;
    GoodsUrls.baseUrl = '/goods/';
    GoodsUrls.allDataUrl = _e.baseUrl + 'get_all_goods';
    GoodsUrls.addGoodsUrl = _e.baseUrl + 'add_goods';
    GoodsUrls.modifyGoodsUrl = _e.baseUrl + 'modify_goods';
    GoodsUrls.delGoodsUrl = _e.baseUrl + 'del_goods';
    return GoodsUrls;
}());
var MerchantUrls = (function () {
    function MerchantUrls() {
    }
    var _f;
    _f = MerchantUrls;
    MerchantUrls.baseUrl = '/merchant/';
    MerchantUrls.adminAllDataUrl = _f.baseUrl + 'get_admin_all_merchant';
    MerchantUrls.delMerchantByIdUrl = _f.baseUrl + 'del_merchant_by_id';
    MerchantUrls.modifyMerchantUrl = _f.baseUrl + 'modify_merchant';
    MerchantUrls.addMerchantUrl = _f.baseUrl + 'add_merchant';
    return MerchantUrls;
}());
var ExtraConfigUrls = (function () {
    function ExtraConfigUrls() {
    }
    var _g;
    _g = ExtraConfigUrls;
    ExtraConfigUrls.baseUrl = '/extra_config/';
    ExtraConfigUrls.getConfigUrl = _g.baseUrl + 'get_extra_config';
    ExtraConfigUrls.fixExtraConfig = _g.baseUrl + 'fix_extra_config';
    return ExtraConfigUrls;
}());
var VipCardUrls = (function () {
    function VipCardUrls() {
    }
    var _h;
    _h = VipCardUrls;
    VipCardUrls.baseUrl = "/vip-card/";
    VipCardUrls.getAllVipCardUrl = _h.baseUrl + 'get-vip-card';
    VipCardUrls.addVipCardUrl = _h.baseUrl + 'add-vip-card';
    VipCardUrls.modifyVipCardBalanceUrl = _h.baseUrl + 'modify_vip-card-balance';
    VipCardUrls.modifyVipCardPasswordUrl = _h.baseUrl + 'modify-vip-card-password';
    VipCardUrls.delVipCardUrl = _h.baseUrl + 'del-vip-card';
    return VipCardUrls;
}());
var PageManageUrls = (function () {
    function PageManageUrls() {
    }
    var _j;
    _j = PageManageUrls;
    PageManageUrls.baseUrl = '/page_config/';
    PageManageUrls.getToggleViewUrl = _j.baseUrl + 'get_admin_toggle_view';
    PageManageUrls.addToggleViewUrl = _j.baseUrl + 'add_toggle_view';
    PageManageUrls.fixToggleViewUrl = _j.baseUrl + 'fix_toggle_view';
    PageManageUrls.delToggleViewUrl = _j.baseUrl + 'del_toggle_view';
    PageManageUrls.getRotationChartwUrl = _j.baseUrl + 'get_admin_rotation_chart';
    PageManageUrls.fixRotationChattUrl = _j.baseUrl + 'fix_rotation_chart';
    PageManageUrls.getGoodsAdUrl = _j.baseUrl + 'get_admin_goods_ad';
    PageManageUrls.fixGoodsAdUrl = _j.baseUrl + 'fix_goods_ad';
    PageManageUrls.getContainerModuleUrl = _j.baseUrl + 'get_admin_container_module';
    PageManageUrls.addContainerModuleUrl = _j.baseUrl + 'add_container_module';
    PageManageUrls.fixContainerModuleUrl = _j.baseUrl + 'fix_container_module';
    PageManageUrls.delContainerModuleUrl = _j.baseUrl + 'del_container_module';
    return PageManageUrls;
}());
export { AdminUrls, ChatBoosUrls, OrderUrls, UserUrls, GoodsUrls, MerchantUrls, ExtraConfigUrls, VipCardUrls, PageManageUrls };
