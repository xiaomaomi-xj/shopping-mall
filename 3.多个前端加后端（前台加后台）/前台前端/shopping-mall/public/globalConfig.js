(() => {
    //这里就可以设置全局属性,在.env文件里面设置也行，只是拿取的方式不一样，这个用window拿，那个用process.env.[name]
    window.noSureSexImgUrl = '/img/noSureSex.png';
    window.manOnSexImgUrl = '/img/manOnSex.jpg';
    window.girlOnSexImgUrl = '/img/girlOnSex.jpg';
    //客服的头像
    window.customerService = '/img/customerService.jpg';
    //验证码，前加载使用
    window.loadingCheckImg="/img/lodingCheck.png";
    //二维码，初始值图片
    window.qrCodeImgUrl = '/img/qrCode.png';
    //注册背景图片(//背景图片最好尺寸为1920x1080，或者尺寸比例是这个，效果才会好看)
    window.registerBgImgUrl = '/img/test.jpg';
    //登录背景图片(//背景图片最好尺寸为1920x1080，或者尺寸比例是这个，效果才会好看)
    window.loginBgImgUrl = '/img/test.jpg';
    //密码框替换符，一个标志就行，如（*,?,$,#,@），表情符号也可以
    window.passwordTemplateText = '💎';
    //默认密码规则（6位到20位，允许0-9，大小写字母a-z）（正则表达)
    window.passwordRule = /^[0-9a-z]{6,20}$/gi;
    //不符合密码规则提示信息
    window.errorTipsMessage = '密码最少为6位,最多为20位，允许数字和大小写字母';
    //提示框的内容（这样就是全局的，但是每个提示框的内容是不一样的）
    window.tipsMessage = '你确定要这样操作吗？';
    //提示信息
    window.messageText = '提示信息';
    //邮箱规则(@符号前面必须有五位及以上字母或者数字，@后面至少有一个点,点后面必须有字母数字2位以上)
    window.emailRule = /^[a-z0-9]{5,}@[a-z0-9]+\.[a-z0-9]{2,}$/gi;
    //空购物车提示
    window.emptyShopCartTips = '哦！你的购物车空空如也！快去添加商品吧！！！';
    //空订单提示
    window.emptyOrderTips = '哦！你还没有购买过物品！快去购买吧！！！';
    //搜索为空提示
    window.emptySearchTips = '很抱歉！没有搜到你想要的物品，去首页看看其他物品吧！！！';
    //后端地址
    window.localBaseUrl = 'http://localhost:8088/shopping-mall';
})()