<template>
  <div>
    <LoginUi v-if="select" :storeInfo="storeInfo" :unReadNum="messageDatas.filter(v => v['isUnRead']).length"
      :shoppingNumInShoppingCart="shoppingNumInShoppingCart" :alreadyBuysGoodsNum="alreadyBuysGoodsNum"
      :currentUserinfo="currentUserinfo" />
    <NoLoginUi v-if="!select" :storeInfo="storeInfo" />
  </div>
</template>

<script>
import LoginUi from "./login";
import NoLoginUi from "./noLogin";
import { mapState, mapActions } from 'pinia';
import { useOrderGoods } from "@/stores/orderPageStores/useOrderGoodsStore";
import { useShopCart } from "@/stores/shopCartPageStores/useShopCartStore";
import { useChatBoos } from "@/stores/chatBoosPageStores/useChatBoosStore";
import { useUsers } from "@/stores/loginAndRegistPageStores/useUserStore";
import { useSpecialConfig } from "@/stores/specialConfigStores/useSpecialConfigStore";
export default {
  name: "mainPageHeader",
  data() {
    return {
      //商店的名称信息
      storeInfo: {
        //是否存在需要高亮显示且用“”包裹的字体
        existsSpecialText: true,
        //如果没有请留空
        SpecialText: process.env.VUE_APP_MAJOR_TEXT,
        //商店名字（如果没有特殊字，就是商店全名，如果有就是商店名字+特殊字=商店全名）,特殊字需要用^做占位符
        storeName: process.env.VUE_APP_LOADING_TEXT + "^" + ".^..",
      },
      //默认是未登录的状态下
      select: false,
      //下面都是登录后的数据
      //用户信息
      currentUserinfo: {
      },
      alreadyBuysGoodsNum: 0,
      shoppingNumInShoppingCart: 0,
      messageDatas: [],

      //销毁事件，清除任务id
      messageTaskId: -1
    };
  },
  components: {
    LoginUi,
    NoLoginUi,
  },
  computed: {
    //额外的配置
    ...mapState(useSpecialConfig, ['extraConfig']),
  },
  mounted() {
    //不同的页面要执行一次,处于一个页面的不需要执行
    this.initExtraConfig();
    //商标名字
    this.storeInfo['existsSpecialText'] = this.extraConfig['storeNameHaveSpecial'];
    this.storeInfo['SpecialText'] = this.extraConfig['specialText'];
    this.storeInfo['storeName'] = this.extraConfig['storeName'];

    this.initOrderGoodsDatas();
    this.initGoodsDatas();
    this.initMessageDatas();
    //判断当前是否已经登陆
    this.select = this.getTokenTolocalStorageOnCheck();
    //只有登陆时开启
    if (this.select) {
      //获取当前用户信息
      this.currentUserinfo = this.getUserDataOnToken();
      //获取当前订单的数量
      this.alreadyBuysGoodsNum = this.getcurrentUserOrder().length;
      //获取当前用户购物车的数量
      this.shoppingNumInShoppingCart = this.getCurrentUserShopCart().length;
      //获取当前用户的消息
      this.messageDatas = this.getcurrentUserMessage();
      //开启监听
      this.listenReceiveMessageEvent();
    } else {
      //没登陆
    }

  },

  unmounted(){
    //销毁界面事件
    clearInterval(this.messageTaskId);
  },

  methods: {
    ...mapActions(useOrderGoods, ['initOrderGoodsDatas', 'getcurrentUserOrder']),
    ...mapActions(useShopCart, ['initGoodsDatas', 'getCurrentUserShopCart']),
    ...mapActions(useChatBoos, ['initMessageDatas', 'getcurrentUserMessage']),
    ...mapActions(useUsers, ['getTokenTolocalStorageOnCheck', 'getUserDataOnToken']),
    ...mapActions(useSpecialConfig, ['initExtraConfig']),

    //监听事件，监听是否收到信息
    listenReceiveMessageEvent() {
      //3s执行一次
      this.messageTaskId = setInterval(() => {
        this.messageDatas = this.getcurrentUserMessage();
      }, 3000);
    }
  },
};
</script>

<style lang="scss" scoped></style>