<template>
  <div>
    <LoginUi v-if="loginStatus" :storeInfo="extraConfig" :unReadNum="chatBoosDatas.filter(v => v['isUnRead'] && v['fromUser']==0).length"
      :shoppingNumInShoppingCart="shopCartGoodsData.length" :alreadyBuysGoodsNum="orderGoodsData.length"
      :currentUserinfo="currentUserData" :loginType="loginType" />
    <NoLoginUi v-if="!loginStatus" :storeInfo="extraConfig" />
  </div>
</template>

<script>
import LoginUi from "./login";
import NoLoginUi from "./noLogin";
import { mapState, mapActions } from 'pinia';
import { useSpecialConfig } from '@/stores/specialConfigStores/useSpecialConfigStore';
import { useUserCheck } from "@/stores/userStores/useUserCheckStore";
import { useChatBoos } from '@/stores/chatBoosPageStores/useChatBoosStore';
import { useShopCart } from "@/stores/shopCartPageStores/useShopCartStore";
import { useOrderGoods } from "@/stores/orderPageStores/useOrderGoodsStore";
export default {
  name: "mainPageHeader",
  components: {
    LoginUi,
    NoLoginUi,
  },
  computed: {
    ...mapState(useSpecialConfig, ['extraConfig']),
    ...mapState(useUserCheck, ['loginStatus', 'loginType', 'currentUserData']),
    ...mapState(useChatBoos, ['chatBoosDatas']),
    ...mapState(useShopCart, ['shopCartGoodsData']),
    ...mapState(useOrderGoods, ['orderGoodsData'])
  },
  mounted() {
    //初始化
    this.initExtraConfig();
    //用户状态,附带回调，更新一些数据
    this.checkLoginUserCallBack(() => {
      //购物车数据
      this.getShopCartDataFun();
      //订单数据
      this.getOrderDataFun();
    }, () => {
      //聊天数据关闭
      this.clearTaskId();
    });
    //开启监听
    this.listenReceiveMessageEvent();
  },
  unmounted() {
    //界面销毁，清除任务
    this.clearTaskId();
  },
  methods: {
    ...mapActions(useSpecialConfig, ['initExtraConfig']),
    ...mapActions(useUserCheck, ['checkLoginUserCallBack']),
    ...mapActions(useChatBoos, ['getMessageFun', 'setTaskId', 'clearTaskId']),
    ...mapActions(useShopCart, ['getShopCartDataFun']),
    ...mapActions(useOrderGoods, ['getOrderDataFun']),
    //监听事件，监听是否收到信息
    listenReceiveMessageEvent() {
      //5s执行一次
      let messageTaskId = setInterval(() => {
        this.checkLoginUserCallBack(() => {
          //登录成功才接受消息
          this.getMessageFun();
        }, () => {
          //什么都不做
        });
      }, 5000);
      this.setTaskId(messageTaskId);
    }
  },
};
</script>

<style lang="scss" scoped></style>