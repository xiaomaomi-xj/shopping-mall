<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="4" :offset="2">
        <div class="grid-content">
          <span class="title" v-html="computeStoreName"></span>
        </div>
      </el-col>
      <el-col :span="2" :offset="6">
        <div class="grid-content">
          <el-badge :value="alreadyBuysGoodsNum" class="item" :max="99" type="warning">
            <span class="link" @click="toOrderPageFun">我的订单<el-icon :size="18">
                <GoodsFilled />
              </el-icon></span>
          </el-badge>
        </div>
      </el-col>
      <el-col :span="2">
        <div class="grid-content">
          <el-badge :value="shoppingNumInShoppingCart" class="item" :max="99" type="success">
            <span class="link" @click="toShopCartPageFun">购物车<el-icon :size="16">
                <ShoppingCart />
              </el-icon></span>
          </el-badge>
        </div>
      </el-col>
      <el-col :span="2">
        <div class="grid-content">
          <el-badge :value="unReadNum" :max="99" class="item">
            <span class="link" @click="toChatBoosPageFun">
              联系客服<el-icon :size="18">
                <ChatDotSquare />
              </el-icon></span>
          </el-badge>
        </div>
      </el-col>
      <el-col :span="4">
        <div class="grid-content user-info">
          <div class="block" @click="operateUserInfoIfShow">
            <el-avatar :size="40" :src="computeResultCurrentInfo['headImgUrl']"></el-avatar>
          </div>
          <span class="userText" @click="operateUserInfoIfShow">{{
            computeResultCurrentInfo["userName"]
          }}<span class="arrow" :style="
  switchStatus
    ? `transform: translateX(10px) translateY(0px) rotateZ(90deg)`
    : ''
"><el-icon :size="20">
                <ArrowRightBold />
              </el-icon></span></span>
          <ToggleViewUi :switchStatus="switchStatus" :currentUserinfo="computeResultCurrentInfo"
            @click="operateUserInfoIfShow" />
        </div>
      </el-col>
    </el-row>
  </div>
</template>
  
<script>
import ToggleViewUi from "./components/ToggleViewUi.vue";
export default {
  name: "LoginUi",
  props: {
    unReadNum: {
      type: Number,
      required: true,
      default: 0,
    },
    shoppingNumInShoppingCart: {
      type: Number,
      required: true,
      default: 0,
    },
    alreadyBuysGoodsNum: {
      type: Number,
      required: true,
      default: 0
    },
    currentUserinfo: {
      type: Object,
      required: true,
    },
    storeInfo: {
      type: Object,
      required: true,
    },
    loginType: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      //账号操作开关,并且此开关控制ui是否显示
      switchStatus: false,
      //系统头像
      manOnSexImgUrl: window.manOnSexImgUrl,
      girlOnSexImgUrl: window.girlOnSexImgUrl
    };
  },
  computed: {
    //计算商标的值,如果这样写函数，必须有返回值
    computeStoreName() {
      //因为这里面含有标签，需要使用v-html
      return this.storeInfo["storeNameHaveSpecial"] == '1'
        ? this.storeInfo["storeName"].replaceAll(
          "^",
          `<span>"${this.storeInfo["specialText"]}"</span>`
        )
        : this.storeInfo["storeName"];
    },
    //因为有两种用户模式，所以利用一下计算属性
    computeResultCurrentInfo() {
      const resultCurrentInfo = {};
      //未登录就给未登录头像
      resultCurrentInfo['headImgUrl'] = window.noSureSexImgUrl;
      if (this.loginType === 'QR_CODE') {
        resultCurrentInfo['type'] = 'QR_CODE';
        resultCurrentInfo['headImgUrl'] = this.currentUserinfo['headImgUrl'];
        resultCurrentInfo['userSex'] = this.currentUserinfo['sex'];
        resultCurrentInfo['userName'] = this.currentUserinfo['nickName'];
        resultCurrentInfo['userAccount'] = this.currentUserinfo['openId'];
      } else if (this.loginType === 'EMAIL_CODE') {
        resultCurrentInfo['type'] = 'EMAIL_CODE';
        resultCurrentInfo['headImgUrl'] = this.currentUserinfo['headImgUrl'];
        resultCurrentInfo['userSex'] = this.currentUserinfo['userSex'];
        resultCurrentInfo['userName'] = this.currentUserinfo['userName'];
        resultCurrentInfo['userAccount'] = this.currentUserinfo['userEmail'];
      }
      //没有头像的情况下，要直接按照性别来显示
      if (resultCurrentInfo['headImgUrl'] == null || resultCurrentInfo['headImgUrl'] == '' || resultCurrentInfo['headImgUrl'].trim().length == 0) {
        if (resultCurrentInfo['userSex'] == '1') {
          resultCurrentInfo['headImgUrl'] = window.manOnSexImgUrl;
        } else {
          resultCurrentInfo['headImgUrl'] = window.girlOnSexImgUrl;
        }
      }
      return resultCurrentInfo;
    }
  },
  methods: {
    //账户操作是否显示函数
    operateUserInfoIfShow() {
      this.switchStatus = !this.switchStatus;
    },
    //跳转页面
    toOrderPageFun() {
      this.$router.push({ name: 'order' });
    },
    toShopCartPageFun() {
      this.$router.push({ name: 'shopCart' });
    },
    toChatBoosPageFun() {
      this.$router.push({ name: 'chatBoos' });
    }
  },
  components: {
    ToggleViewUi
  },
};
</script>
  
<style lang="scss" scoped>
@import "@/assets/sass/colors.scss";

.el-row {
  margin-bottom: 20px;
  padding-top: 10px;
  padding-bottom: 10px;

  &:last-child {
    margin-bottom: 0;
  }
}

.el-col {
  border-radius: 4px;
  display: flex;
  justify-content: center;
  align-content: center;
}

.grid-content {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 4px;
  text-align: center;
  color: $title-fgcolor;

  .userText {
    padding-left: 8px;
    cursor: pointer;
    color: $title-fgcolor;

    &:hover {
      color: $title_hover_fgcolor;
    }
  }

  .block {
    cursor: pointer;

    //给予兄弟节点样式
    &:hover+.userText {
      color: $title_hover_fgcolor;
    }
  }

  .arrow {
    transform: translateX(10px) translateY(5px) rotateZ(0deg);
    display: inline-block;
    transition: 0.5s;
  }
}

.title {
  font-size: 1.6em;
  font-weight: bold;
  color: $title-hover-fgcolor;
  cursor: default;

  //这里是解决v-html,没有样式的问题
  //深度选择器
  ::v-deep(span) {
    color: $special-color;
  }
}

.link {
  width: 100%;
  height: 50%;

  i {
    //调整图标和字体不整齐的问题
    vertical-align: middle;
  }

  &:hover {
    cursor: pointer;
    color: $title-hover-fgcolor;
  }
}
</style>