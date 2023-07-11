const shopCartPage = () => import( /* webpackChunkName: "private" */ '@/views/shopCartPage');
const orderPage = () => import( /* webpackChunkName: "private" */ '@/views/orderPage');
const chatBoosPage = () => import( /* webpackChunkName: "private" */ '@/views/chatOnBoosPage');
//购物车页面
const shopCartPageRouter = {
    path: '/shop-cart',
    name: 'shopCart',
    component: shopCartPage,
    meta: {
        title: '我的购物车',
        requiresCheck:true
    }
};
//订单页面
const orderPageRouter = {
    path: '/order',
    name: 'order',
    component: orderPage,
    meta: {
        title: '我的订单',
        requiresCheck:true
    }
};
//聊天界面
const chatBoosPageRouter = {
    path: '/chat-boos',
    name: 'chatBoos',
    component: chatBoosPage,
    meta: {
        title: '客服服务消息页',
        requiresCheck:true
    }
};
export {
    shopCartPageRouter,
    orderPageRouter,
    chatBoosPageRouter
};