<template>
    <div>
        <main :style="`background-image: url(${extraConfig['loginBgImgUrl']});`">
            <div class="container">
                <div class="hollow-out" :style="`background-image: url(${extraConfig['loginBgImgUrl']});`">
                    <div class="left-text">
                        <span class="register">登录界面</span>
                        <span class="trademark">{{ computeStoreName }}</span>
                    </div>
                    <div class="right-form">
                        <div class="email-password-login" v-if="loginOption == '1'">
                            <h1>账号密码登录</h1>
                            <div class="user-email">
                                <input type="text" placeholder="请输入你的账号(邮箱)..." v-model="userEmail" @input="emailChangeVal"
                                    maxlength="25">
                            </div>
                            <div class="user-password">
                                <input class="hide-input" type="text" readonly v-model="passwordTemplate"
                                    @click="tripFousEvent" placeholder="请输入你的密码..." />
                                <div class="close-bold" @click.stop="passwordReset">
                                    <el-icon :size="20">
                                        <CloseBold />
                                    </el-icon>
                                </div>
                                <div class="eye" @click.stop="passwordOnShowAndHide">
                                    <el-icon :size="20" v-if="!passwordIfShowOnView">
                                        <ViewPassword />
                                    </el-icon>
                                    <el-icon :size="20" v-if="passwordIfShowOnView">
                                        <HidePassword />
                                    </el-icon>
                                </div>
                                <input type="text" class="show-input" v-model="password" @input="passwordChangeVal" />
                            </div>
                            <div class="user-operation">
                                <aside @click="toRegisterPageFun">前往注册页面</aside>
                                <aside @click="loginUser" class="main-text">登录</aside>
                                <aside @click="toMainPageFun">前往主页</aside>
                            </div>
                        </div>
                        <div class="email-check-code-login" v-if="loginOption == '2'">
                            <h1>账号验证码登录</h1>
                            <div class="user-email">
                                <input type="text" placeholder="请输入你的账号(邮箱)..." v-model="userEmail" @input="emailChangeVal"
                                    maxlength="25">
                            </div>
                            <div class="check-code">
                                <img :src="checkImg" alt="" @click="changeCheckCodeImg">
                                <input type="text" placeholder="请输入图片验证码..." v-model="checkCode" maxlength="6">
                            </div>
                            <div class="user-email-check">
                                <input type="text" placeholder="请输入邮箱验证码..." v-model="userEmailCode" maxlength="6">
                                <aside @click="postCheckCode" v-if="!notButtonShow">获取验证码 </aside>
                                <aside v-if="notButtonShow">{{ notPostTime }}s</aside>
                            </div>
                            <div class="user-operation">
                                <aside @click="toRegisterPageFun">前往注册页面</aside>
                                <aside @click="loginUserOnCheckCode" class="main-text">登录</aside>
                                <aside @click="toMainPageFun">前往主页</aside>
                            </div>
                        </div>
                        <div class="qr-code-login" v-if="loginOption == '3'">
                            <h1>微信扫码登录</h1>
                            <div class="qr-code-container">
                                <article>
                                    <img :src="qrCodeImg" alt="" @click="resetQrCode">
                                    <aside @click="resetQrCode">刷新二维码</aside>
                                </article>
                            </div>
                        </div>
                        <div class="login-option">
                            <div @click="changeOption('1')">账号密码登录</div>
                            <div @click="changeOption('2')" v-if="loginCheckCodeOpen">账号验证码登录</div>
                            <div @click="changeOption('3')" v-if="loginScanCodeOpen">微信扫码登录</div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</template>

<script>
import { mapState, mapActions } from 'pinia';
import { useSpecialConfig } from '@/stores/specialConfigStores/useSpecialConfigStore';
import { useUserUtil } from '@/stores/userStores/useUserUtilStore';
import { useWechat } from '@/stores/userStores/useWechatStore';
import { useUserLogin } from '@/stores/userStores/useUserLoginStore';
export default {
    name: 'LoginPage',
    data() {
        return {
            //表单数据
            userEmail: '',
            //图片验证码
            checkCode: '',
            //邮箱验证码
            userEmailCode: '',
            //密码是否显示
            passwordIfShowOnView: false,
            //密码
            password: '',
            //密码模板
            passwordTemplate: '',
            //密码规则
            passwordErrorTipsInfo: {
                passwordRule: window.passwordRule,
                errorTipsMessage: window.errorTipsMessage
            },
            //密码格式是否通过
            passwordFormat: false,
            //邮箱格式是否通过
            emailFormat: false,
            //登录方式的切换：1，账号密码，2：账号验证码，3：扫码
            loginOption: '1',
            //账号验证码是否启用
            loginCheckCodeOpen: true,
            //扫码是否启用
            loginScanCodeOpen: true,

            //发送完邮箱验证码，更换按钮倒计时
            notPostTime: 0,
            notButtonShow: false
        }
    },
    computed: {
        ...mapState(useSpecialConfig, ['extraConfig']),
        ...mapState(useUserUtil, ['emailId', 'checkImg']),
        ...mapState(useUserLogin, ['emailCodeTaskId', 'scanCodeTaskId']),
        ...mapState(useWechat, ['qrCodeImg']),
        //商家名字
        computeStoreName() {
            return this.extraConfig['storeNameHaveSpecial'] ? this.extraConfig['storeName'].replaceAll('^', this.extraConfig['specialText']) : this.extraConfig['storeName'];
        }
    },
    mounted() {
        //初始化特殊配置
        this.initExtraConfig();
        //获取验证码图片
        this.getCheckImgUrl();
        //获取登录二维码图片
        this.getWechatLoginImgUrl();
        //开启轮询检测是否登录成功
        this.loginUserOnqr();
    },

    unmounted() {
        //界面销毁清除掉任务id
        this.clearTaskId();
        this.clearErrorCheckImgId();
        this.clearErrorWechatLoginImgId();
    },

    methods: {
        //pinia方法
        ...mapActions(useSpecialConfig, ['initExtraConfig']),
        ...mapActions(useUserUtil, ['getCheckImgUrl', 'clearErrorCheckImgId', 'sendEmail']),
        ...mapActions(useUserLogin, ['loginOnEmailAndPasswordFun', 'loginOnEmailCodeFun', 'setEmailCodeTaskId', 'setScanCodeTaskId', 'clearTaskId']),
        ...mapActions(useWechat, ['getWechatLoginImgUrl', 'clearErrorWechatLoginImgId', 'pollingCheckLogin']),
        //时间倒计时,更换按钮
        notPostTimeFun() {
            //60s倒计时
            this.notPostTime = 60;
            this.notButtonShow = true;
            let emailCodeTaskId = setInterval(() => {
                this.notPostTime--;
                if (this.notPostTime <= 0) {
                    //显示发送按钮，取消任务
                    this.notButtonShow = false;
                    this.clearTaskId(emailCodeTaskId);
                }
            }, 1000);
            this.setEmailCodeTaskId(emailCodeTaskId);
        },
        //改变验证码图片
        changeCheckCodeImg() {
            //获取验证码图片
            this.getCheckImgUrl();
        },
        //清空密码
        passwordReset() {
            if (this.password.length !== 0) {
                this.password = "";
                this.passwordChangeVal();
            }
        },
        //密码显示和影藏
        passwordOnShowAndHide() {
            this.passwordIfShowOnView = !this.passwordIfShowOnView;
            if (this.password.length !== 0) {
                this.passwordChangeVal();
            }
        },
        //文本框监听事件
        passwordChangeVal() {
            //输入时进行密码合格检验
            this.passwordFormat = this.passwordErrorTipsInfo['passwordRule'].test(this.password);
            //因为他是全局匹配，可以不使用全局匹配，但是可能会有人使用，所以把lastindex这个值设为0
            this.passwordErrorTipsInfo["passwordRule"].lastIndex = 0;
            if (this.passwordIfShowOnView) {
                this.passwordTemplate = this.password;
            } else {
                this.passwordTemplate = this.extraConfig['passwordTemplateText'].repeat(this.password.length);
            }
        },
        //邮箱监听事件
        emailChangeVal() {
            //进行检验
            this.emailFormat = window.emailRule.test(this.userEmail);
            //因为他是全局匹配，可以不使用全局匹配，但是可能会有人使用，所以把lastindex这个值设为0
            window.emailRule.lastIndex = 0;
        },
        //获取焦点事件
        tripFousEvent(el) {
            this.$nextTick(() => {
                el.target.nextElementSibling.nextElementSibling.nextElementSibling.focus();
            });
        },
        //去掉空格
        handelSpace() {
            this.userEmail = this.userEmail.trim();
            this.password = this.password.trim();
            this.checkCode = this.checkCode.trim();
            this.userEmailCode = this.userEmailCode.trim();
        },
        //刷新二维码
        resetQrCode() {
            //获取登录二维码图片
            this.getWechatLoginImgUrl();
        },
        //重置函数，切换选项卡，要把之前的数据清除掉
        changeOption(id) {
            if (this.loginOption !== id) {
                this.loginOption = id;
                //表单数据
                this.userEmail = '';
                //图片验证码
                this.checkCode = '';
                //邮箱验证码
                this.userEmailCode = '';
                //密码是否显示
                this.passwordIfShowOnView = false;
                //密码
                this.password = '';
                //密码模板
                this.passwordTemplate = '';
            }
        },
        //获取验证码
        postCheckCode() {
            this.handelSpace();
            if (this.checkCode.length === 0) {
                this.$selfMessage.openMessage({
                    type: 'error',
                    message: '请输入图像验证码！'
                });
            } else {
                if (!this.emailFormat) {
                    //邮箱格式没通过
                    this.$selfMessage.openMessage({
                        type: 'warning',
                        message: '你的邮箱格式不正确！'
                    });
                } else {
                    //发送邮箱
                    this.sendEmail(this.userEmail, this.checkCode);
                    //倒计时，防止多次提交
                    this.notPostTimeFun();
                }
            }
        },
        //普通登录
        loginUser() {
            this.handelSpace();
            if (this.userEmail.length === 0) {
                this.$selfMessage.openMessage({
                    type: 'error',
                    message: '请填写你的邮箱！'
                });
            } else if (this.password.length === 0) {
                this.$selfMessage.openMessage({
                    type: 'error',
                    message: '请填写你的密码！'
                });
            } else {
                if (!this.emailFormat) {
                    //邮箱格式没通过
                    this.$selfMessage.openMessage({
                        type: 'warning',
                        message: '你的邮箱格式不正确！'
                    });
                } else if (!this.passwordFormat) {
                    //密码格式没通过
                    this.$selfMessage.openMessage({
                        type: 'warning',
                        message: this.passwordErrorTipsInfo['errorTipsMessage']
                    });
                } else {
                    //后端请求的方式
                    this.loginOnEmailAndPasswordFun(this.userEmail, this.password);
                }
            }
        },
        //验证码登录
        loginUserOnCheckCode() {
            this.handelSpace();
            if (this.userEmail.length === 0) {
                this.$selfMessage.openMessage({
                    type: 'error',
                    message: '请填写你的邮箱！'
                });
            } else if (this.userEmailCode.length === 0) {
                this.$selfMessage.openMessage({
                    type: 'error',
                    message: '请填写你的邮箱验证码！'
                });
            } else {
                if (!this.emailFormat) {
                    //邮箱格式没通过
                    this.$selfMessage.openMessage({
                        type: 'warning',
                        message: '你的邮箱格式不正确！'
                    });
                } else {
                    //后端请求的方式
                    this.loginOnEmailCodeFun(this.userEmail, this.emailId, this.userEmailCode);
                }
            }
        },
        //二维码登录
        loginUserOnqr() {
            let scanCodeTaskId = setInterval(() => {
                //5秒检测一次是否登录成功
                this.pollingCheckLogin();
            }, 3000);
            this.setScanCodeTaskId(scanCodeTaskId);
        },
        //跳转页面
        toRegisterPageFun() {
            this.$router.push({ name: 'userRegister' });
        },
        toMainPageFun() {
            this.$router.push({ name: 'home' });
        }
    },
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

//文本框提示字的颜色
input:-moz-placeholder {
    color: transparent;
}

input:-ms-input-placeholder {
    color: transparent;
}

input::-webkit-input-placeholder {
    color: transparent;
}

main {
    width: 100%;
    height: 100vh;
    background-size: 120%;
    background-position: 0 0;
    background-repeat: no-repeat;
    animation-name: move;
    animation-duration: 20s;
    //无限循环
    animation-iteration-count: infinite;
    //前后交替
    animation-direction: alternate;
    //匀速
    animation-timing-function: linear;
    display: flex;
    justify-content: center;
    align-items: center;

    .container {
        width: 50%;
        height: 66.6%;
        border-radius: 10px;
        background-color: $login-bgcolor;
        //通过阴影扩展半径让其暗下来
        box-shadow: 0px 0px 10px 10000px $login-shadow-color;
        overflow: hidden;

        .hollow-out {
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: space-around;
            align-items: center;

            .left-text {
                width: 20%;
                height: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
                box-sizing: border-box;

                span {
                    margin: 10px;
                    font-size: 3em;
                    writing-mode: vertical-rl;
                    font-weight: 900;
                }

                .trademark {
                    font-size: 2.5em;
                }
            }

            .right-form {
                width: 80%;
                height: 100%;
                background-color: $login-right-bgcolor;
                display: flex;
                justify-content: space-around;
                align-items: center;
                flex-direction: column;

                h1 {
                    font-size: 3em;
                }

                div {
                    overflow: hidden;
                    border: 2px solid $login-bgcolor;
                    border-radius: 10px;
                    box-sizing: border-box;
                    width: 95%;
                    background-color: $login-transparent-input-bgcolor;
                }

                input[type=text] {
                    background-color: transparent;
                    width: 97%;
                    padding: 10px;
                    font-size: 1.5em;
                    outline: none;
                    border: none;
                    font-weight: bolder;
                    color: transparent;
                }

                .email-password-login {
                    height: 80%;
                    width: 100%;
                    display: flex;
                    justify-content: space-around;
                    align-items: center;
                    flex-direction: column;
                    border: none;
                    background-color: transparent;

                    .user-password {
                        display: flex;
                        justify-content: center;
                        align-items: center;

                        .hide-input {
                            width: 84%;
                        }

                        .show-input {
                            width: 0;
                            padding: 0;
                        }

                        div {
                            width: 8%;
                            height: 100%;
                            border: none;
                            background-color: transparent;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                        }

                        .eye {

                            cursor: pointer;

                            svg {
                                color: $message-box-view-heid-color !important;
                            }

                            &:hover svg {
                                color: $message-box-view-heid-hover-color !important;
                            }
                        }

                        .close-bold {

                            cursor: pointer;

                            svg {
                                color: $message-box-view-heid-color !important;
                            }

                            &:hover svg {
                                color: $message-box-view-heid-hover-color !important;
                            }
                        }
                    }

                }

                .user-operation {
                    padding: 10px;
                    display: flex;
                    justify-content: space-around;
                    align-items: center;
                    background-color: transparent;
                    border: none;

                    aside {
                        font-size: 1em;
                        font-weight: bold;
                        background-color: $login-transparent-button-bgcolor;
                        padding: 15px;
                        border-radius: 10px;
                        width: 25%;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        cursor: pointer;

                        &:hover {
                            background-color: $login-transparent-button-hover-bgcolor;
                        }
                    }

                    .main-text {
                        font-size: 1.8em;
                        font-weight: bolder;
                    }
                }

                .email-check-code-login {
                    height: 80%;
                    width: 100%;
                    display: flex;
                    justify-content: space-around;
                    align-items: center;
                    flex-direction: column;
                    border: none;
                    background-color: transparent;

                    .check-code {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        border: none;
                        background-color: transparent;

                        img {
                            border-radius: 10px;
                            width: 15%;
                            height: 90%;
                        }

                        input {
                            width: 80%;
                            border-radius: 10px;
                            border: 2px solid $register-bgcolor;
                            background-color: $login-transparent-input-bgcolor;
                        }
                    }

                    .user-email-check {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        background-color: transparent;
                        border: none;

                        input {
                            border-radius: 10px;
                            border: 2px solid $register-bgcolor;
                            width: 60%;
                            background-color: $login-transparent-input-bgcolor;
                        }

                        aside {
                            font-size: 1.2em;
                            font-weight: bold;
                            background-color: $register-transparent-button-bgcolor;
                            padding: 15px;
                            border-radius: 10px;
                            width: 25%;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            cursor: pointer;

                            &:hover {
                                background-color: $register-transparent-button-hover-bgcolor;
                            }
                        }
                    }
                }

                .qr-code-login {
                    height: 80%;
                    width: 100%;
                    display: flex;
                    justify-content: space-around;
                    align-items: center;
                    flex-direction: column;
                    border: none;
                    background-color: transparent;

                    .qr-code-container {
                        height: 70%;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        background-color: transparent;
                        font-size: 1.5em;
                        border: none;

                        article {
                            width: 60%;
                            height: 90%;
                            border-radius: 10px;
                            border: 2px solid $register-bgcolor;
                            display: flex;
                            justify-content: space-around;
                            align-items: center;
                            flex-direction: column;
                            overflow: scroll;

                            img {
                                width: 8em;
                                height: 8em;
                                cursor: pointer;
                            }

                            aside {
                                font-size: 1em;
                                font-weight: bold;
                                background-color: $register-transparent-button-bgcolor;
                                padding: 15px;
                                border-radius: 10px;
                                width: 50%;
                                display: flex;
                                justify-content: center;
                                align-items: center;
                                cursor: pointer;

                                &:hover {
                                    background-color: $register-transparent-button-hover-bgcolor;
                                }
                            }
                        }

                    }
                }

                .login-option {
                    border: none;
                    width: 90%;
                    display: flex;
                    justify-content: space-around;
                    align-items: center;
                    background-color: transparent;

                    div {
                        border: none;
                        width: auto;
                        cursor: pointer;
                        font-size: 1.5em;
                        font-weight: bolder;
                        border-radius: 10px;
                        padding: 10px;
                        background-color: $login-transparent-button-bgcolor;

                        &:hover {
                            background-color: $login-transparent-button-hover-bgcolor;
                        }
                    }
                }
            }

            //实现伪镂空的效果
            background-clip: text;
            color: transparent;
            background-size: 240%;
            background-position: 35.8% 25%;
            animation-name: son-move;
            animation-duration: 20s;
            //无限循环
            animation-iteration-count: infinite;
            //前后交替
            animation-direction: alternate;
            //匀速
            animation-timing-function: linear;
        }
    }
}

@keyframes move {
    25% {
        background-position: 40% 0;
    }

    50% {
        background-position: 40% 40%;
    }

    75% {
        background-position: 0% 40%;
    }

    100% {
        background-position: 0 0;
    }
}

@keyframes son-move {
    25% {
        //47-36=11   45-25=20
        background-position: 47% 25%;
    }

    50% {
        background-position: 47% 45%;
    }

    75% {
        background-position: 36% 45%;
    }

    100% {
        background-position: 36% 25%;
    }
}
</style>