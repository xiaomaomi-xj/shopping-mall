import pageConfigJson from '/public/customConfig/页面上的数据配置.json';
import {
    allGoodsDataJson
} from './allGoodsDatas';
import { userConfigJson } from './userConfig';
const toggleViewData = pageConfigJson['toggleViewData'];
const rotationChartData = pageConfigJson['rotationChartData'];
const goodsAdData = pageConfigJson['goodsAdData'];
const containerModuleData = pageConfigJson['containerModuleData'];
const orderData = pageConfigJson['orderData'];
toggleViewData.map(v => {
    //填充数据，那goodsid充当下标
    v['goodsDatas'] = v['goodsDatas'].map(v1 => allGoodsDataJson[v1 - 1])
});
rotationChartData['goodsDatas'] = rotationChartData['goodsDatas'].map(v => allGoodsDataJson[v - 1]);
goodsAdData['goodsDatas'] = goodsAdData['goodsDatas'].map(v => allGoodsDataJson[v - 1]);
containerModuleData.map(v => {
    v['specialGoodsInfo'] = allGoodsDataJson[v['specialGoodsInfo'] - 1];
    v['topGoodsInfo'] = v['topGoodsInfo'].map(v1 => allGoodsDataJson[v1 - 1]);
    v['bottomGoodsInfo'] = v['bottomGoodsInfo'].map(v1 => allGoodsDataJson[v1 - 1]);
});
orderData.map(v => {
    v['goodsData'] = allGoodsDataJson[v['goodsData'] - 1];
    v['totalPrice'] = v['goodsData']['goodsPrice'] * v['goodsNum'];
})
const orderDataBak=[];
for (const userInfo of userConfigJson) {
    for (const order of orderData) {
        orderDataBak.push({
            ...order,
            userEmail:userInfo['userEmail']
        });
    }
}
export {
    toggleViewData,
    rotationChartData,
    goodsAdData,
    containerModuleData,
    orderDataBak
};