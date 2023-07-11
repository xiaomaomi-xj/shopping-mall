(()=>{
    //头像
    window.noSureSexImgUrl='/img/noSureSex.png';
    window.manOnSexImgUrl='/img/manOnSex.jpg';
    window.girlOnSexImgUrl='/img/girlOnSex.jpg';
    window.customerService='/img/customerService.jpg';
    //验证码
    window.checkCodeDatas=[
        {
            img:'/img/checkCode/1230.png',
            code:'9301'
        },
        {
            img:'/img/checkCode/1231.png',
            code:'6690'
        },
        {
            img:'/img/checkCode/1232.png',
            code:'8279'
        },
        {
            img:'/img/checkCode/1233.png',
            code:'8370'
        },
        {
            img:'/img/checkCode/1234.png',
            code:'0869'
        }
    ];
    //二维码，这个用不到
    window.qrCodeImgUrl='/img/qrCode.png';
    //出错背景图
    window.registerBgImgUrl='/img/test.jpg';
    window.loginBgImgUrl='/img/test.jpg';
    //密码模板
    window.passwordTemplateText='💎';
    //密码规则
    window.passwordRule=/^[0-9a-z]{6,20}$/gi;
    window.errorTipsMessage='密码最少为6位,最多为20位，允许数字和大小写字母';
    window.tipsMessage='你确定要这样操作吗？';
    window.messageText='提示信息';
    //邮箱规则
    window.emailRule=/^[a-z0-9]{5,}@[a-z0-9]+\.[a-z0-9]{2,}$/gi;
    window.emptyShopCartTips='哦！你的购物车空空如也！快去添加商品吧！！！';
    window.emptyOrderTips='哦！你还没有购买过物品！快去购买吧！！！';
    window.emptySearchTips='很抱歉！没有搜到你想要的物品，去首页看看其他物品吧！！！';
    //机器人聊天，就是随机返回一条信息
    window.chatMessageRobot='https://v1.hitokoto.cn/';
})()