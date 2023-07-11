const mainPage = () => import( /* webpackChunkName: "public" */ '@/views/mainPage');
const loginPage = () => import( /* webpackChunkName: "public" */ '@/views/loginPage');
const registerPage = () => import( /* webpackChunkName: "public" */ '@/views/registerPage');
const searchOnResultPage = () => import( /* webpackChunkName: "public" */ '@/views/searchOnResultPage');
const GoodsSpecificsPage = () => import( /* webpackChunkName: "public" */ '@/views/goodsSpecificsPage');
const notFundPage = () => import( /* webpackChunkName: "public" */ '@/views/errPage/404.vue');
//主页面配置
const mainPageRouter = {
    path: '/',
    name: 'home',
    component: mainPage,
    meta: {
        title: '主页面'
    }
};
//用户登陆注册页面配置
const userPageRouter = [{
        //当只有/user的时候可以重定向
        path: '/user',
        name: 'user',
        redirect: '/user/login',
    },
    {
        path: "/user/login",
        name: "userLogin",
        component: loginPage,
        hidden: true,
        meta: {
            title: "用户登录",
            loginNoShow:true
        }
    },
    {
        path: "/user/register",
        name: "userRegister",
        component: registerPage,
        hidden: true,
        meta: {
            title: "用户注册",
            loginNoShow:true
        }
    }
];
//搜索界面
const searchPageRouter = {
    //让他带着参数(用户输入的内容)请求
    path: '/search',
    name: 'search',
    component: searchOnResultPage,
    meta: {
        title: '搜索结果页'
    }
};
//商品详情页
const goodsSpecificsPageRouter = {
    //根据商品的id进行跳转到不同的商品详情页位置
    path: '/goods-specifics/:goodsId(\\d+)',
    name: 'goodsSpecifics',
    component: GoodsSpecificsPage,
    meta: {
        //这个title可以根据商品的名称进行更改
        title: '商品详情页'
    }
};
//准确的404页面
const confirmNotFundPageRouter={
    path:'/404',
    name: "notFund",
    component:notFundPage,
    meta: {
        title: "页面不存在"
    }
};
//模糊的404页面
const vagueNotFundPageRouter = {
    //因为他没有准确的路径，就不用给他name了
    path: "/:pathMatch(.*)",
    component: notFundPage,
    meta: {
        title: "页面不存在"
    }
};

export {
    mainPageRouter,
    userPageRouter,
    searchPageRouter,
    goodsSpecificsPageRouter,
    confirmNotFundPageRouter,
    vagueNotFundPageRouter
};