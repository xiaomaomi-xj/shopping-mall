//此文件存储，各个响应需要用到的参数值，以便于代码有更好的提示
//验证登录的参数值
interface CheckLoginType {
    value: boolean;
}
//登录token返回值
interface LoginType {
    token: string;
    refreshToken: string;
}
//商品类型
interface GoodsType {
    goodsId: string;
    imgUrl: string;
    goodsName: string;
    goodsDescribe: string;
    goodsPrice: number;
    maxBuyNum: number;
    rotationGoodsImgs: Array<string>;
}
//订单类型
interface OrderType {
    orderId: string;
    userId: string;
    goodsName: string;
    merchantName: string;
    goodsNum: number;
    shipStatus: number;
    totalPrice: number;
    orderState: string;
}
//聊天类型
interface ChatBoosType {
    userId: string;
    fromUser: number;
    message: string;
    isUnRead: number;
}
//用户类型
interface UserType {
    userId: string;
    userHeadUrl: string;
    userName: string;
    userAccount: string;
    userSex: number;
    bindId: string;
    userType: string;
}
//特殊配置
interface ExtraConfigType {
    extraId: string;
    storeNameHaveSpecial: boolean;
    specialText: string;
    storeName: string;
    passwordTemplateText: string;
    belowPageText: string;
    registerBgImgUrl: string;
    loginBgImgUrl: string;
}
//商户类型
interface MerchantType {
    merchantId: string;
    merchantCode: string;
    merchantName: string;
    merchantType: string;
    payPlatformAppId: string;
    publicKey: string;
    privateKey: string;
    encryptPassword: string;
    serverUrl: string;
    callbackUrl: string
}
//购物卡类型
interface VipCardType {
    vipCardId: string;
    vipCardAccount: string;
    vipCardBalance: string;
}
//界面数据类型
interface ToggleViewType {
    toggleViewId: string;
    titleName: string;
    goodsIds: Array<string>
}
interface ContainerModuleType {
    containerModuleId: string;
    titleName: string;
    specialGoodsInfo: string;
    topGoodsInfo: Array<string>;
    bottomGoodsInfo: Array<string>;
}
interface PageCheckType {
    goodsIds: Array<string>
}
export {
    CheckLoginType,
    LoginType,
    GoodsType,
    OrderType,
    ChatBoosType,
    UserType,
    ExtraConfigType,
    MerchantType,
    VipCardType,
    ToggleViewType,
    ContainerModuleType,
    PageCheckType
};