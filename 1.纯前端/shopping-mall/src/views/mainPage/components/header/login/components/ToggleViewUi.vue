<template>
    <div>
        <main :style="switchStatus ? 'height: 180px;' : 'height: 0px;'">
            <aside class="box-card">
                <div class="text item" @click="operateUserInfo">个人信息</div>
                <div class="text item" @click="operateModifyPassword">修改密码</div>
                <div class="text item" @click="operateExit">退出登录</div>
            </aside>
        </main>
    </div>
</template>

<script>
import currentUserInfo from '@/components/current-user-info/currentUserInfo.vue';
import { mapActions } from 'pinia';
import { useUsers } from '@/stores/loginAndRegistPageStores/useUserStore';
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
        },
    },
    data() {
        return {
        }
    },
    methods: {

        //pinia
        ...mapActions(useUsers, ['modifyUserInfo', 'checkUserPassword', 'logOutUser']),

        //这里添加@click.stop事件可以解决点击关闭的问题,但是感觉关闭也挺好的
        //个人信息
        operateUserInfo() {
            //直接把个人信息组件传过去，让其动态加载组件，这里就不用注册了
            this.$selfDrawer.openDrawerBox(currentUserInfo);
        },
        //修改密码
        operateModifyPassword() {
            //调用自己定义的messageBox来提示
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
                    }
                    //结构，克隆，改变指针
                    const userInfo = { ...this.currentUserinfo };
                    //传过来密码，如果正确才可以修改
                    userInfo['password'] = oldPassword;
                    if (this.checkUserPassword(userInfo)) {
                        //修改为新密码
                        userInfo['password'] = newPassword;
                        this.modifyUserInfo(userInfo);
                        this.$selfMessage.openMessage({
                            type: 'success',
                            message: '修改密码成功！'
                        });
                        //token不变，所以要跳转到登陆页面
                        this.logOutUser();
                        this.$router.push({ name: 'userLogin' });
                        return true;
                    } else {
                        this.$selfMessage.openMessage({
                            type: 'error',
                            message: '旧密码错误！'
                        });
                        return false;
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
                    //(this.$router.go())相当于刷新当前页面，但是这样相当于数据初始化，所以我们可以直接去往登录页
                    this.$router.push({ name: 'userLogin' });
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