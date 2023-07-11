<template>
  <div>
    <div class="message-info" v-for="(messageInfo, key) in messageInfos" :key="key"
      :style="`transition: all ${messageInfo['transition']}s ease-out;opacity: ${messageInfo['opacity']};transform: translateX(-50%) translateY(${messageInfo['moveVl']}%);background-color: ${messageInfo['backgroundColor']};`">
      <div class="tips">
        <span>{{ messageInfo["message"] }}</span>
      </div>
      <div class="close" @click="closeMessage(key)">
        <el-icon :size="20">
          <CloseBoldMessage />
        </el-icon>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
//要提示的消息和样式
const messageInfos = reactive([]);
//第一个消息默认的高度
let moveVl = 40;
//删除指定的message
function deleteAppointMessage(key) {
  //因为每次messageInfos值发生变化，都会重新渲染，如果不把transition改为0，组件就会有先向下再向上的过度时间
  messageInfos[key]["opacity"] = 0;
  setTimeout(() => {
    messageInfos.splice(key, 1);
    //从删除的位置开始往后都要依次减少移动数据
    messageInfos.map((messageInfo, k) => {
      if (k >= key) {
        messageInfo["transition"] = 0;
        setTimeout(() => {
          messageInfo["transition"] = 0.6;
          messageInfo["moveVl"] -= 150;
        });
      }
    }, 600);
  }, 600);
}
//自动消失函数
function autoHide() {
  setTimeout(() => {
    messageInfos.map((messageInfo) => {
      messageInfo["moveVl"] -= 140;
      if (messageInfo["moveVl"] === -100) {
        messageInfo["opacity"] = 0;
      }
    });
    //位置恢复
    moveVl -= 140;
    if (moveVl === 40) {
      //清空,留个过渡时间
      setTimeout(() => {
        messageInfos.splice(0);
      }, 600);
    }
  }, 3000);
}
//打开函数
function openMessage(data) {
  const messageInfoData = {};
  if (data["type"] === "success") {
    messageInfoData["backgroundColor"] = "#2c3f2c";
  }
  if (data["type"] === "error") {
    messageInfoData["backgroundColor"] = "#3f2928";
  }
  if (data["type"] === "warning") {
    messageInfoData["backgroundColor"] = "#453c29";
  }
  messageInfoData["message"] = data["message"];
  messageInfoData["moveVl"] = moveVl;
  messageInfoData["opacity"] = 1;
  messageInfoData["transition"] = 0.6;
  moveVl += 140;
  messageInfos.push(messageInfoData);
  autoHide();
}
//关闭提示
function closeMessage(key) {
  //删除函数
  deleteAppointMessage(key);
}
defineExpose({
  openMessage,
});
</script>

<style lang="scss" scoped>
@import "@/assets/sass/colors.scss";
@import "@/assets/sass/zIndexValue.scss";

.message-info {
  transition: all 0.6s ease-out;
  position: fixed;
  max-width: 32em;
  top: 0;
  left: 50%;
  transform: translateX(-50%) translateY(50%);
  color: $message-text-fgcolor;
  background-color: $message-main-bgcolor;
  z-index: $messageZIndex;
  box-shadow: 2px 2px 10px 10px $shadow-color;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;

  .tips {
    width: 100%;
    word-break: break-all;
    padding: 20px 0px 20px 20px;
  }

  .close {
    padding: 5px 30px;
    height: 100%;
    width: 3em;
    display: flex;
    justify-content: center;
    align-content: center;
    box-sizing: border-box;
    cursor: pointer;

    svg {
      color: $message-close-color !important;
    }

    &:hover svg {
      color: $message-close-hover-color !important;
    }
  }
}
</style>