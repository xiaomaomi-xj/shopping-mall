<template>
  <div>
    <div class="tips-alert">
      <aside class="tips">
        <span>温馨提示:</span>
      </aside>
      <aside class="message">
        <span>
          {{ tipsMessage['tipsMessage'] }}</span>
      </aside>
      <aside class="button">
        <button @click="closeMessageBox">取消</button>
        <button @click="determineEvent">确定</button>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { reactive,ref } from "vue";
//要提示的信息
const tipsMessage = reactive({
  tipsMessage: window.tipsMessage
});
//接受到的方法
let callBackFun=ref(()=>{});
//调用父组件方法（父组件无需开放（defineExpose））
const emits = defineEmits(["closeMessageBox"]);
//取消按钮
function closeMessageBox() {
  emits("closeMessageBox");
}
//确定按钮
function determineEvent() {
  //相应的逻辑
  callBackFun();
  //关闭
  closeMessageBox();
}
//接受信息并修改
function acceptAlertTipsInfo(data) {
  if (data['tipsMessage']) {
    tipsMessage['tipsMessage'] = data['tipsMessage']
  }
  callBackFun=data['callBackFun'];
}
defineExpose({
  acceptAlertTipsInfo
});
</script>

<style lang="scss" scoped>
@import "@/assets/sass/colors.scss";
.tips-alert {
  width: 100%;
  color: $message-box-text-fgcolor;

  .tips {
    width: 100%;
    font-size: 1.3em;
    display: flex;
    justify-content: flex-start;
    align-items: center;
  }

  .message {
    margin: 20px 10px;
    font-size: 1em;
    width: 20em;
    max-height: 20em;
    word-break: break-all;
    text-align: center;
    overflow-y: scroll;
  }

  .button {
    margin-top: 10px;
    padding: 10px 5px;
    display: flex;
    justify-content: space-between;
    align-items: center;

    button {
      transition: 0.5s;
      outline: none;
      border: none;
      padding: 5px 10px;
      font-size: 1.2em;
      margin: 0 10px;
      color: $button-a-fgcolor;
      background-color: $button-a-bgcolor;
      border-radius: 5px;
      cursor: pointer;

      &:hover {
        color: $button-a-bgcolor;
        background-color: $button-a-fgcolor;
      }

      &:last-of-type {
        color: $button-a-bgcolor;
        background-color: $button-a-fgcolor;

        &:hover {
          color: $button-a-fgcolor;
          background-color: $button-a-bgcolor;
        }
      }
    }
  }
}
</style>