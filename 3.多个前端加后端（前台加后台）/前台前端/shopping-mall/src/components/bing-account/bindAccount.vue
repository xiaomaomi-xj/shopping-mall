<template>
    <div class="drawer-bind-account">
        <div class="email-password">
            <h1>绑定邮箱账号</h1>
            <div class="user-email">
                <input type="text" placeholder="请输入你的账号(邮箱)..." v-model="userEmail" @input="emailChangeVal" maxlength="25">
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
                <aside @click="closeDrawerFun">关闭</aside>
                <aside @click="bindAccountFun">进行绑定</aside>
            </div>
            <div class="tips">
                <p>[注]:绑定邮箱后的详情:</p>
                <ul>
                    <li>名字、性别都将以邮箱账号为主。</li>
                    <li>之后扫码登录，都将登录此邮箱账号。</li>
                    <li>如果邮箱账号没有头像,会使用微信头像,否则使用邮箱账号。</li>
                    <li>当然对于聊天数据，购物车数据，订单数据，两者会叠加，并不会消失的！</li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions, mapState } from 'pinia'
import { useSpecialConfig } from '@/stores/specialConfigStores/useSpecialConfigStore';
import { useUserModify } from '@/stores/userStores/useUserModifyStore';
export default {
    data() {
        return {
            //邮箱
            userEmail: '',
            //密码
            password: '',
            //密码模板
            passwordTemplate: '',
            //密码显示或隐藏
            passwordIfShowOnView: false,
            //密码规则
            passwordErrorTipsInfo: {
                passwordRule: window.passwordRule,
                errorTipsMessage: window.errorTipsMessage
            },
            //密码格式是否通过
            passwordFormat: false,
            //邮箱格式是否通过
            emailFormat: false
        }
    },
    computed: {
        ...mapState(useSpecialConfig, ['extraConfig']),
    },
    methods: {
        ...mapActions(useUserModify, ['bindAccount']),

        //邮箱输入改变监听函数
        emailChangeVal() {
            //进行检验
            this.emailFormat = window.emailRule.test(this.userEmail);
            window.emailRule.lastIndex = 0;
        },
        //密码输入改变监听函数
        passwordChangeVal() {
            //输入时进行密码合格检验
            this.passwordFormat = this.passwordErrorTipsInfo['passwordRule'].test(this.password);
            this.passwordErrorTipsInfo["passwordRule"].lastIndex = 0;
            if (this.passwordIfShowOnView) {
                this.passwordTemplate = this.password;
            } else {
                this.passwordTemplate = this.extraConfig['passwordTemplateText'].repeat(this.password.length);
            }
        },
        //获取密码输入框焦点
        tripFousEvent(el) {
            this.$nextTick(() => {
                el.target.nextElementSibling.nextElementSibling.nextElementSibling.focus();
            });
        },
        //清空内容函数
        passwordReset() {
            if (this.password.length !== 0) {
                this.password = "";
                this.passwordChangeVal();
            }
        },
        //密码的显示和隐藏
        passwordOnShowAndHide() {
            this.passwordIfShowOnView = !this.passwordIfShowOnView;
            if (this.password.length !== 0) {
                this.passwordChangeVal();
            }
        },
        //替换空格
        handelSpace() {
            this.userEmail = this.userEmail.trim();
            this.password = this.password.trim();
        },
        //绑定
        bindAccountFun() {
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
                    this.$selfMessage.openMessage({
                        type: 'warning',
                        message: '你的邮箱格式不正确！'
                    });
                } else if (!this.passwordFormat) {
                    this.$selfMessage.openMessage({
                        type: 'warning',
                        message: this.passwordErrorTipsInfo['errorTipsMessage']
                    });
                } else {
                    //进行绑定
                    this.bindAccount(this.userEmail, this.password, () => { this.closeDrawerFun(); });
                }
            }
        },
        //关闭
        closeDrawerFun() {
            this.$emit('closeDrawerBox');
        },
        //重置函数
        resetEnableFun() {
            //表单数据
            this.userEmail = '';
            //密码是否显示
            this.passwordIfShowOnView = false;
            //密码
            this.password = '';
            //密码模板
            this.passwordTemplate = '';
        }
    },
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/zIndexValue.scss';
@import '@/assets/sass/colors.scss';

.drawer-bind-account {
    width: 100%;
    height: 80%;
    background-color: $drawer-box-content-bgcolor;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;

    h1 {
        font-size: 2em;
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
        color: $drawer-box-content-color;
    }

    .email-password {
        width: 100%;
        height: 100%;
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
                    color: $drawer-box-content-color !important;
                }

                &:hover svg {
                    color: $message-box-view-heid-hover-color !important;
                }
            }

            .close-bold {
                cursor: pointer;

                svg {
                    color: $drawer-box-content-color !important;
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
            font-size: 1.2em;

            aside {
                font-size: 1em;
                font-weight: bold;
                background-color: $drawer-box-button-bgcolor;
                padding: 15px;
                border-radius: 10px;
                width: 25%;
                display: flex;
                justify-content: center;
                align-items: center;
                transition: .5s;
                cursor: pointer;

                &:hover {
                    background-color: $drawer-box-button-hover-bgcolor;
                }
            }
        }
    }
    .tips{
        font-weight: bold;
        color: $drawer-box-tips-color;
        padding: .6em;
        p{
            font-size: 1.1em;
        }
        ul{
            font-size: .9em;
            margin-left: 2em;
            list-style: circle;
            li{
                margin-top: .2em;
            }
        }
    }
}
</style>