<template>
    <div>
        <main
            :style="switchStatus ? computeCurrentUserInfo['type'] == 'QR_CODE' ? `height:120px;` : `height: 180px;` : `height: 0px;`">
            <aside class="box-card">
                <div class="text item" v-if="computeCurrentUserInfo['type'] == 'QR_CODE'" @click="bindAccount">绑定邮箱账号
                </div>
                <div class="text item" v-if="computeCurrentUserInfo['type'] == 'EMAIL_CODE'" @click="operateUserInfo">个人信息
                </div>
                <div class="text item" v-if="computeCurrentUserInfo['type'] == 'EMAIL_CODE'" @click="operateModifyPassword">
                    修改密码</div>
                <div class="text item" @click="operateExit">退出登录</div>
            </aside>
        </main>
    </div>
</template>

<script>
import currentUserInfoVue from '@/components/current-user-info/currentUserInfo.vue';
import bindAccountVue from '@/components/bing-account/bindAccount.vue';
import { mapActions } from 'pinia';
import { useUserCheck } from '@/stores/userStores/useUserCheckStore';
import { useUserModify } from '@/stores/userStores/useUserModifyStore';
export default {
    props: {
        switchStatus: {
            type: Boolean,
            required: true,
            default: false
        },
        currentUserinfo: {
            type: Object,
            required: true,
            default: null
        },
    },
    data() {
        return {
        }
    },
    computed: {
        //接一下用户信息
        computeCurrentUserInfo() {
            return this.currentUserinfo;
        }
    },
    methods: {
        //pinia方法
        ...mapActions(useUserCheck, ['logOutUser']),
        ...mapActions(useUserModify, ['modifyPasswordFun']),

        //这里添加@click.stop事件可以解决点击关闭的问题,但是感觉关闭也挺好的
        //绑定账号
        bindAccount() {
            this.$selfDrawer.openDrawerBox(bindAccountVue);
        },
        //个人信息
        operateUserInfo() {
            //直接把个人信息组件传过去，让其动态加载组件，这里就不用注册了
            this.$selfDrawer.openDrawerBox(currentUserInfoVue);
        },
        //修改密码
        operateModifyPassword() {
            this.$selfMessageBox.openMessageBox({
                type: 'password',
                passwordRule: window.passwordRule,
                errorTipsMessage: window.errorTipsMessage,
                callBackFun: (oldPassword, newPassword) => {
                    if (oldPassword === newPassword) {
                        this.$selfMessage.openMessage({
                            type: 'warning',
                            message: '旧密码与新密码不能一致！'
                        });
                        return false;
                    }else{
                        //修改密码,不用返回值，因为他会刷新页面
                        this.modifyPasswordFun(oldPassword,newPassword);
                    }
                }
            });
        },
        //退出
        operateExit() {
            //调用自己定义的messageBox来提示
            this.$selfMessageBox.openMessageBox({
                type: 'tips',
                tipsMessage: '你确定要退出吗？',
                callBackFun: () => {
                    this.logOutUser();
                }
            });
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

main {
    transition: .5s;
    position: absolute;
    top: 100%;
    right: 10%;
    width: 200px;
    background-color: $title_bgcolor;
    border-radius: 0 0 10px 10px;
    z-index: 3;
    overflow: hidden;
}

.text {
    font-size: 1em;
}

.item {
    height: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;

    &:hover {
        color: $title-hover-fgcolor;
        background-color: $title-hover-bgcolor;
    }

    &:active {
        background-color: $title-active-bgcolor;
    }
}
</style>