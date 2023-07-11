<template>
  <div>
    <main class="message-box-main">
      <article class="message-box-main-body" :style="multipleMould === 'loading' ? 'padding: 0px 0px;background-color: transparent;box-shadow:none' : ''">
        <ModifyPassword v-show="multipleMould === 'password'" @closeMessageBox="closeMesssageBox" ref="passwordRuleInfo" />
        <TipsAlert v-show="multipleMould === 'tips'" @closeMessageBox="closeMesssageBox" ref="alertTipsInfo" />
        <LoadingMask v-show="multipleMould === 'loading'" />
      </article>
    </main>
  </div>
</template>

<script>
//定义组件名字
export default {
  name: "MessageBox",
};
</script>
  
<script setup>
import { nextTick, ref } from "vue";
import ModifyPassword from "./modify-password/ModifyPassword.vue";
import TipsAlert from "./tips-alert/TipsAlert.vue";
import LoadingMask from "./loading-mask/LoadingMask.vue";
//显示不同的内容
const multipleMould = ref("");
const passwordRuleInfo = ref("");
const alertTipsInfo = ref('');
function renderDomOnOpen() {
  //异步渲染dom
  nextTick(() => {
    let el = document.querySelector(".message-box-main");
    el.style.display = "flex";
    el.style.opacity = "0";
    setTimeout(() => {
      //为了能有个过度效果
      el.style.opacity = "1";
    });
    document.body.style = "overflow: hidden;";
  });
}
function renderDomOnClose() {
  //清除掉行级元素即可
  nextTick(() => {
    let el = document.querySelector(".message-box-main");
    el.style.opacity = "0";
    //也是为了有个过渡
    setTimeout(() => {
      el.style.display = "none";
    }, 500);
    document.body.style = "";
  });
}
//给子组件传递信息
function transmitMessages(data) {
  if (data !== undefined) {
    multipleMould.value = data["type"];
    if (data["type"] === "password") {
      //密码框
      passwordRuleInfo.value?.acceptModifyPasswordErrorTipsInfo(data);
    }
    if (data["type"] === "tips") {
      //提示框
      alertTipsInfo.value?.acceptAlertTipsInfo(data);
    }
  }
}
//开启
const openMessageBox = (data) => {
  renderDomOnOpen();
  //这里传入数据信息
  transmitMessages(data);
};

//关闭
const closeMesssageBox = () => {
  renderDomOnClose();
};


defineExpose({
  openMessageBox,
  closeMesssageBox
});
</script>
<style lang="scss" scoped>
@import "@/assets/sass/zIndexValue.scss";
@import "@/assets/sass/colors.scss";

.message-box-main {
  transition: all 0.3s ease-out;
  opacity: 0;
  width: 100vw;
  height: 100vh;
  position: fixed;
  right: 0;
  bottom: 0;
  background-color: $message-box-transparent-bgcolor;
  z-index: $messageBoxZIndex;
  background-attachment: fixed;
  display: none;
  justify-content: center;
  align-items: center;
}

.message-box-main-body {
  background-color: $message-box-main-bgcolor;
  padding: 20px 40px;
  display: flex;
  justify-content: space-around;
  flex-direction: column;
  align-items: center;
  border-radius: 10px;
  box-shadow: 2px 2px 10px 10px $shadow-color;
}
</style>