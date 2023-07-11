<template>
    <div>
        <main :style="`background-image: url(${extraConfig['registerBgImgUrl']});`">
            <div class="container">
                <div class="hollow-out" :style="`background-image: url(${extraConfig['registerBgImgUrl']});`">
                    <div class="left-text">
                        <span class="register">注册界面</span>
                        <span class="trademark">{{ computeStoreName }}</span>
                    </div>
                    <div class="right-form">
                        <h1>注册</h1>
                        <div class="user-name">
                            <input type="text" placeholder="请输入你的昵称..." v-model="userName" maxlength="10">
                        </div>
                        <div class="user-email-container">
                            <input type="text" placeholder="请输入你的邮箱..." v-model="userEmail" @input="emailChangeVal"
                                maxlength="25">
                            <div class="check-code">
                                <img :src="checkImg" alt="" @click="changeCheckCodeImg">
                                <input type="text" placeholder="请输入图片验证码..." v-model="checkCode" maxlength="6">
                            </div>
                            <div class="user-email-check">
                                <input type="text" placeholder="请输入邮箱验证码..." v-model="userEmailCode" maxlength="6">
                                <aside @click="postCheckCode" v-if="!notButtonShow">获取验证码 </aside>
                                <aside v-if="notButtonShow">{{ notPostTime }}s</aside>
                            </div>
                        </div>
                        <div class="user-sex">
                            <div class="left-radio" @click="userSex = '1'">
                                <span>男:</span>
                                <div class="radio-box">
                                    <div class="box-full" v-if="userSex == '1'"></div>
                                </div>
                            </div>
                            <div class="space"></div>
                            <div class="right-radio" @click="userSex = '0'">
                                <span>女:</span>
                                <div class="radio-box">
                                    <div class="box-full" v-if="userSex == '0'"></div>
                                </div>
                            </div>
                        </div>
                        <div class="user-password">
                            <input class="hide-input" type="text" readonly v-model="passwordTemplate" @click="tripFousEvent"
                                placeholder="请输入你的密码..." />
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
                            <aside @click="toLoginPageFun">前往登录页面</aside>
                            <aside @click="registerUser" class="main-text">注册</aside>
                            <aside @click="toMainPageFun">前往主页</aside>
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
import { useUserRegister } from '@/stores/userStores/useUserRegisterStore';
export default {
    name: 'RegisterPage',
    data() {
        return {
            //表单数据
            userName: '',
            userEmail: '',
            //邮箱收到的验证码
            userEmailCode: '',
            checkCode: '',
            //性别
            userSex: '1',
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

            //发送完邮箱验证码，更换按钮倒计时
            notPostTime: 0,
            notButtonShow: false
        }
    },
    computed: {
        ...mapState(useSpecialConfig, ['extraConfig']),
        ...mapState(useUserUtil, ['emailId', 'checkImg']),
        ...mapState(useUserRegister, ['emailCodeTaskId']),
        //商家名字
        computeStoreName() {
            return this.extraConfig['storeNameHaveSpecial'] ? this.extraConfig['storeName'].replaceAll('^', this.extraConfig['specialText']) : this.extraConfig['storeName'];
        }
    },
    mounted() {
        //初始化特殊配置
        this.initExtraConfig();
        //获取验证码图片,以及倒计时过期图片
        this.getCheckImgUrl();
    },
    unmounted() {
        //界面销毁事件
        this.clearTaskId();
        this.clearErrorCheckImgId();
    },
    methods: {
        //拿到pinia方法
        ...mapActions(useSpecialConfig, ['initExtraConfig']),
        ...mapActions(useUserUtil, ['getCheckImgUrl', 'sendEmail', 'clearErrorCheckImgId']),
        ...mapActions(useUserRegister, ['register', 'setTaskId', 'clearTaskId']),
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
            this.setTaskId(emailCodeTaskId);
        },

        //点击更换图片验证码
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
        //发送邮箱验证码
        postCheckCode() {
            this.checkCode = this.checkCode.trim();
            if (!this.emailFormat) {
                //图片验证码验证码
                this.$selfMessage.openMessage({
                    type: 'error',
                    message: '你的邮箱格式不正确！!'
                });
            } else if (this.checkCode.length === 0) {
                //图片验证码验证码
                this.$selfMessage.openMessage({
                    type: 'error',
                    message: '请输入图片验证码!'
                });
            } else {
                //后端交互的方式
                this.sendEmail(this.userEmail, this.checkCode);
                //倒计时
                this.notPostTimeFun();
            }
        },
        //去掉空格
        handelSpace() {
            this.userName = this.userName.trim();
            this.userEmail = this.userEmail.trim();
            this.userEmailCode = this.userEmailCode.trim();
            this.userSex = this.userSex.trim();
            this.password = this.password.trim();
        },
        //注册
        registerUser() {
            this.handelSpace();
            if (this.userName.length === 0) {
                this.$selfMessage.openMessage({
                    type: 'error',
                    message: '请填写你的昵称！'
                });
            } else if (this.userEmail.length === 0) {
                this.$selfMessage.openMessage({
                    type: 'error',
                    message: '请填写你的邮箱！'
                });
            } else if (this.userEmailCode.length === 0) {
                this.$selfMessage.openMessage({
                    type: 'error',
                    message: '请填写你的邮箱验证码！'
                });
            } else if (this.userSex.length === 0) {
                this.$selfMessage.openMessage({
                    type: 'error',
                    message: '请选择你的性别！'
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
                    //进行注册
                    this.register(this.userName, this.userEmail, this.userSex, this.password, this.emailId, this.userEmailCode);
                }
            }
        },
        //前往登录页面
        toLoginPageFun() {
            this.$router.push({ name: 'userLogin' });
        },
        //前往主页
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
        background-color: $register-bgcolor;
        //通过阴影扩展半径让其暗下来
        box-shadow: 0px 0px 10px 10000px $register-shadow-color;
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
                background-color: $register-right-bgcolor;
                display: flex;
                justify-content: space-around;
                align-items: center;
                flex-direction: column;

                h1 {
                    font-size: 3em;
                }

                div {
                    overflow: hidden;
                    border: 2px solid $register-bgcolor;
                    border-radius: 10px;
                    box-sizing: border-box;
                    width: 95%;
                    background-color: $register-transparent-input-bgcolor;
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

                .user-email-container {
                    input {
                        border-bottom: 2px solid $register-bgcolor;
                    }

                    .user-email-check {
                        font-size: .8em;
                        width: 100%;
                        border: none;
                        padding: 5px 10px 10px 10px;
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        background-color: transparent;

                        input {
                            border-radius: 10px;
                            border: 2px solid $register-bgcolor;
                            width: 60%;
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

                    .check-code {
                        width: 100%;
                        font-size: .8em;
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        border: none;
                        padding: 10px 10px 5px 10px;
                        background-color: transparent;

                        img {
                            border-radius: 10px;
                            width: 15%;
                            height: 100%;
                        }

                        input {
                            width: 80%;
                            border-radius: 10px;
                            border: 2px solid $register-bgcolor;
                        }
                    }
                }

                .user-sex {
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    padding: 0px;
                    font-size: 1.6em;
                    font-weight: bolder;

                    div {
                        width: auto;
                        padding: 10px;
                        border: none;
                        background-color: transparent;
                        display: flex;
                        justify-content: center;
                        align-items: center;

                        .radio-box {
                            padding: 0;
                            width: 1em;
                            height: 1em;
                            border: 1px solid $register-bgcolor;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            border-radius: 50%;
                            box-sizing: border-box;

                            .box-full {
                                background-color: $register-bgcolor;
                            }
                        }
                    }

                    .left-radio {
                        cursor: pointer;
                    }

                    .right-radio {
                        cursor: pointer;
                    }

                    .space {
                        width: 3em;
                        //为none就是直接没鼠标了
                        // cursor: none;
                    }
                }

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

                    .main-text {
                        font-size: 1.8em;
                        font-weight: bolder;
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