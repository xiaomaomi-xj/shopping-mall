import allGoodsDataJson from '/public/customConfig/全部的商品数据.json';

//生成唯一id
let goodsId = 0;
const imgBaseUrl = '/customConfig/customImg/';

//引入的json会自动转成对象
//处理自定义配置的数据
allGoodsDataJson.map(v=>{
    v['goodsId']=++goodsId;
    v['imgUrl']= imgBaseUrl + v['imgUrl'];
    v['rotationGoodsImgs']=v['rotationGoodsImgs'].map(v=>imgBaseUrl + v);
});
export { allGoodsDataJson };