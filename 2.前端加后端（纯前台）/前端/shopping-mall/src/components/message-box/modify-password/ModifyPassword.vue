<template>
  <div>
    <div class="modify-password">
      <aside class="tips">
        <span>修改密码</span>
      </aside>
      <aside class="old-password">
        <span>请输入旧密码:</span>
        <div class="passwor-input">
          <article @click="tripFousEvent">
            <input class="hide-input" type="text" disabled v-model="oldPasswordTemplate" placeholder="请输入旧密码" />
            <div class="close-bold" @click.stop="oldPasswordReset">
              <el-icon :size="20">
                <CloseBold />
              </el-icon>
            </div>
            <div class="eye" @click.stop="oldPasswordOnShowAndHide">
              <el-icon :size="20" v-if="!oldPasswordIfShowOnView">
                <ViewPassword />
              </el-icon>
              <el-icon :size="20" v-if="oldPasswordIfShowOnView">
                <HidePassword />
              </el-icon>
            </div>
          </article>
          <input type="text" v-model="oldPassword" @input="oldPasswordChangeVal" />
        </div>
        <div class="error-tips" v-if="!oldPasswordFormat">
          {{ modifyPasswordErrorTipsInfo["errorTipsMessage"] }}
        </div>
      </aside>
      <div class="hr"></div>
      <aside class="new-password">
        <span>请输入新密码:</span>
        <div class="passwor-input">
          <article @click="tripFousEvent">
            <input class="hide-input" type="text" disabled v-model="newPasswordTemplate" placeholder="请输入新密码" />
            <div class="close-bold" @click.stop="newPasswordReset">
              <el-icon :size="20">
                <CloseBold />
              </el-icon>
            </div>
            <div class="eye" @click.stop="newPasswordOnShowAndHide">
              <el-icon :size="20" v-if="!newPasswordIfShowOnHeid">
                <ViewPassword />
              </el-icon>
              <el-icon :size="20" v-if="newPasswordIfShowOnHeid">
                <HidePassword />
              </el-icon>
            </div>
          </article>
          <input type="text" v-model="newPassword" @input="newPasswordChangeVal" />
        </div>
        <div class="error-tips" v-if="!newPasswordFormat">
          {{ modifyPasswordErrorTipsInfo["errorTipsMessage"] }}
        </div>
      </aside>
      <aside class="button">
        <button @click="closeMessageBox">取消</button>
        <button @click="determineEvent">确定</button>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { useSpecialConfig } from "@/stores/specialConfigStores/useSpecialConfigStore";
import { ref, nextTick, reactive } from "vue";
//密码是否显示
const oldPasswordIfShowOnView = ref(false);
const newPasswordIfShowOnHeid = ref(false);
//密码格式是否通过,默认通过
const oldPasswordFormat = ref(true);
const newPasswordFormat = ref(true);
//用户输入的密码内容
const oldPassword = ref("");
const oldPasswordTemplate = ref("");
const newPassword = ref("");
const newPasswordTemplate = ref("");
// 密码检验规则,动态错误提示文字
const modifyPasswordErrorTipsInfo = reactive({
  passwordRule: window.passwordRule,
  errorTipsMessage: window.errorTipsMessage
});
//就收到的函数
let callBackFun = ref(() => { });
//接受信息并修改
function acceptModifyPasswordErrorTipsInfo(data) {
  if (data['passwordRule']) {
    modifyPasswordErrorTipsInfo['passwordRule'] = data['passwordRule'];
  }
  if (data['errorTipsMessage']) {
    modifyPasswordErrorTipsInfo['errorTipsMessage'] = data['errorTipsMessage'];
  }
  callBackFun = data['callBackFun'];
}
//密码替换为特定的样式
const oldPasswordChangeVal = () => {
  //输入时进行密码合格检验
  oldPasswordFormat.value = modifyPasswordErrorTipsInfo["passwordRule"].test(
    oldPassword.value
  );
  //因为他是全局匹配，可以不使用全局匹配，但是可能会有人使用，所以把lastindex这个值设为0
  modifyPasswordErrorTipsInfo["passwordRule"].lastIndex = 0;
  if (oldPasswordIfShowOnView.value) {
    oldPasswordTemplate.value = oldPassword.value;
  } else {
    oldPasswordTemplate.value = useSpecialConfig().extraConfig['passwordTemplateText'].repeat(
      oldPassword.value.length
    );
  }
};
const newPasswordChangeVal = () => {
  //输入时进行密码合格检验
  newPasswordFormat.value = modifyPasswordErrorTipsInfo["passwordRule"].test(
    newPassword.value
  );
  //因为他是全局匹配，可以不使用全局匹配，但是可能会有人使用，所以把这个值设为0
  modifyPasswordErrorTipsInfo["passwordRule"].lastIndex = 0;
  if (newPasswordIfShowOnHeid.value) {
    newPasswordTemplate.value = newPassword.value;
  } else {
    newPasswordTemplate.value = useSpecialConfig().extraConfig['passwordTemplateText'].repeat(
      newPassword.value.length
    );
  }
};
//清空密码
function oldPasswordReset() {
  if (oldPassword.value.length !== 0) {
    oldPassword.value = "";
    oldPasswordChangeVal();
  }
}
function newPasswordReset() {
  if (newPassword.value.length !== 0) {
    newPassword.value = "";
    newPasswordChangeVal();
  }
}
//点击密码变换
function oldPasswordOnShowAndHide() {
  oldPasswordIfShowOnView.value = !oldPasswordIfShowOnView.value;
  if (oldPassword.value.length !== 0) {
    oldPasswordChangeVal();
  }
}
function newPasswordOnShowAndHide() {
  newPasswordIfShowOnHeid.value = !newPasswordIfShowOnHeid.value;
  if (newPassword.value.length !== 0) {
    newPasswordChangeVal();
  }
}
//密码框获取焦点
function tripFousEvent(el) {
  nextTick(() => {
    //拿到元素hide-input，让其获取焦点
    el.target.parentElement.nextElementSibling.focus();
  });
}
//重置方法（关闭和确定都要进行重置）
function resetData() {
  //密码是否显示
  oldPasswordIfShowOnView.value = false;
  newPasswordIfShowOnHeid.value = false;
  //密码格式是否通过,默认通过
  oldPasswordFormat.value = true;
  newPasswordFormat.value = true;
  //用户输入的密码内容
  oldPassword.value = "";
  oldPasswordTemplate.value = "";
  newPassword.value = "";
  newPasswordTemplate.value = "";
}
//调用父组件方法（父组件无需开放（defineExpose））
const emits = defineEmits(["closeMessageBox"]);
//取消按钮
function closeMessageBox() {
  resetData();
  emits("closeMessageBox");
}
//确定按钮
function determineEvent() {
  if (oldPasswordFormat.value && newPasswordFormat.value) {
    if (oldPassword.value.length === 0 && newPassword.value.length === 0) {
      //用户没有输入，直接退出即可
      resetData();
      emits("closeMessageBox");
    } else if (oldPassword.value.length === 0) {
      oldPasswordFormat.value = false;
    } else if (newPassword.value.length === 0) {
      newPasswordFormat.value = false;
    } else {
      //后续请求操作(异步没返回值)
      if (callBackFun(oldPassword.value, newPassword.value) != false) {
        resetData();
        //修改成功就关闭
        emits("closeMessageBox");
      }
    }
  }
}
defineExpose({
  acceptModifyPasswordErrorTipsInfo
});
</script>

<style lang="scss" scoped>
@import "@/assets/sass/colors.scss";

.modify-password {
  color: $message-box-text-fgcolor;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;

  .hr {
    width: 100%;
    height: 2px;
    background-color: $message-box-text-fgcolor;
  }

  aside {
    width: 100%;
    padding: 10px;

    span {
      font-size: 1em;
    }
  }

  .tips {
    width: 100%;
    padding: 0;
    font-size: 1.3em;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .button {
    margin-top: 10px;
    padding: 10px 5px;
    display: flex;
    justify-content: space-around;
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

  .passwor-input {
    width: 30em;
    background-color: $message-box-input-bgcolor;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 5px;
    position: relative;
    overflow: hidden;
    box-sizing: content-box;

    input {
      height: 100%;
      width: 0;
      padding: 5px;
      font-size: 1em;
      outline: none;
      border: none;
      font-size: 1.2em;
      background-color: transparent;
    }

    .hide-input {
      height: 100%;
      flex: 1;
      left: 0;
      top: 0;
      box-sizing: border-box;
      cursor: text;
      background-color: $password-input-bgcolor;
    }

    article {
      height: 100%;
      width: 100%;
      display: flex;
      position: absolute;
      top: 0;
      left: 0;
    }

    .eye {
      padding: 5px;
      height: 100%;
      width: 2em;
      display: flex;
      justify-content: center;
      align-content: center;
      box-sizing: border-box;
      cursor: pointer;

      svg {
        color: $message-box-view-heid-color !important;
      }

      &:hover svg {
        color: $message-box-view-heid-hover-color !important;
      }
    }

    .close-bold {
      padding: 5px;
      height: 100%;
      width: 2em;
      display: flex;
      justify-content: center;
      align-content: center;
      box-sizing: border-box;
      cursor: pointer;

      svg {
        color: $message-box-view-heid-color !important;
      }

      &:hover svg {
        color: $message-box-view-heid-hover-color !important;
      }
    }
  }

  .error-tips {
    width: 28.7em;
    font-size: 0.93em;
    color: $password-error-tops-color;
  }
}
</style>