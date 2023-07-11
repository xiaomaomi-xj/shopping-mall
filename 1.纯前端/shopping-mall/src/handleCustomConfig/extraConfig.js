import extraConfigJson from '/public/customConfig/页面上的特殊配置.json';
//全局特别配置
const imgBaseUrl = '/customConfig/customImg/';
extraConfigJson['loginBgImgUrl'] = imgBaseUrl + extraConfigJson['loginBgImgUrl'];
extraConfigJson['registerBgImgUrl'] = imgBaseUrl + extraConfigJson['registerBgImgUrl'];
export { extraConfigJson };