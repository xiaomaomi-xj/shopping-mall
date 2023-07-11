<template>
    <div class="current-user-info-box">
        <div class="input-box">
            <img :src="currentUserinfo['userSex'] === '1' ? manOnSexImgUrl : girlOnSexImgUrl" alt="">
            <div class="user-email">
                <span>账号：</span>
                <aside>{{ currentUserinfo['userEmail'] }}</aside>
            </div>
            <div class="user-name">
                <span>用户名：</span>
                <div class="input-disble" v-if="!enableModify">
                    {{ currentUserinfo['userName'] }}
                </div>
                <div class="input" v-if="enableModify">
                    <input type="text" v-model="currentUserinfo['userName']" maxlength="10" />
                </div>
            </div>
            <div class="user-sex">
                <span>性别：</span>
                <div class="radio-box" v-if="enableModify">
                    <div class="man" @click="modifySexFun(1)">
                        <span>男：</span>
                        <div class="left-box">
                            <div class="left-full" v-if="currentUserinfo['userSex'] == 1"></div>
                        </div>
                    </div>
                    <div class="girl" @click="modifySexFun(0)">
                        <span>女：</span>
                        <div class="right-box">
                            <div class="right-full" v-if="currentUserinfo['userSex'] == 0"></div>
                        </div>
                    </div>
                </div>
                <div class="radio-disble" v-if="!enableModify">
                    {{ currentUserinfo['userSex'] == 1 ? '男' : '女' }}
                </div>
            </div>
        </div>
        <div class="button-box">
            <aside @click="enableModifyFun">修改</aside>
            <aside @click="saveModifyFun">保存</aside>
        </div>
    </div>
</template>

<script>
import { mapActions } from 'pinia';
import { useUsers } from '@/stores/loginAndRegistPageStores/useUserStore';
export default {
    data() {
        return {
            currentUserinfo: {
            },
            //系统头像
            manOnSexImgUrl: window.manOnSexImgUrl,
            girlOnSexImgUrl: window.girlOnSexImgUrl,
            //是否启用了修改
            enableModify: false,
        }
    },
    mounted() {
        //获取用户赋值,不要让其关联
        this.currentUserinfo = {...this.getUserDataOnToken()};
    },
    methods: {
        //拿到pinia方法,获取当前用户
        ...mapActions(useUsers, ['getUserDataOnToken', 'modifyUserInfo']),

        //重置函数
        resetEnableFun() {
            //这里就是如果直接关闭，并没有保存，就直接重读一遍数据
            this.currentUserinfo = {...this.getUserDataOnToken()};
            this.enableModify = false;
        },
        //保存了数据
        saveModifyFun() {
            //逻辑函数传过来，保存后不能一直确定
            if (this.enableModify == true) {
                if (this.currentUserinfo['userName'].trim().length === 0) {
                    this.$selfMessage.openMessage({
                        type: 'error',
                        message: '用户名不能为空！'
                    });
                } else {
                    //处理业务逻辑
                    this.modifyUserInfo(this.currentUserinfo);
                    //因为这个组件相当于是在抽屉（drawerBox）里面执行，而他挂载的是另一个实例，所以也要把组件给他use一下
                    this.$selfMessage.openMessage({
                        type: 'success',
                        message: '用户信息修改保存成功！'
                    });
                    //关闭修改
                    this.enableModify = false;
                    //关闭抽屉
                    this.$emit('closeDrawerBox');
                }
            }
        },
        //性别选择函数
        modifySexFun(sexId) {
            //因为不是字符串出了点问题，要必须是字符串
            this.currentUserinfo['userSex'] = sexId.toString();
        },
        //开启修改
        enableModifyFun() {
            this.enableModify = true;
        },
    },
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/zIndexValue.scss';
@import '@/assets/sass/colors.scss';

.current-user-info-box {
    .input-box {
        display: flex;
        justify-content: space-around;
        align-items: center;
        flex-direction: column;
        margin: 3em;

        img {
            width: 6em;
            height: 6em;
            border-radius: 50%;
        }

        div {
            font-size: 1.5em;
            margin-top: 2em;
        }

        .user-name {
            display: flex;
            align-items: center;

            .input {
                margin: 0;
                display: flex;
                align-items: center;
                border: 2px solid $border-color;
                padding: .3em;
                border-radius: 10px;
                min-width: 10em;

                input {
                    color: $drawer-box-content-color;
                    background-color: transparent;
                    outline: none;
                    border: none;
                    font-size: .6em;
                    height: 100%;
                    width: 100%;
                }
            }

            .input-disble {
                margin: 0;
                border: none;
                font-size: 1em;
                padding: .3em;
                min-width: 10em;
                border-radius: 10px;
                background-color: $drawer-box-disble-bgcolor;
            }
        }

        .user-email {
            display: flex;
            align-items: center;

            aside {
                margin: 0;
                border: none;
                font-size: 1em;
                padding: .3em;
                min-width: 10em;
                border-radius: 10px;
                background-color: $drawer-box-alway-disble-bgcolor;
            }

        }

        .user-sex {
            margin-bottom: 2em;
            display: flex;
            align-items: center;

            .radio-box {
                margin: 0;
                display: flex;
                align-items: center;
                border: 2px solid $drawer-box-content-color;
                padding: .3em;
                border-radius: 10px;
                min-width: 9em;
                justify-content: space-around;

                div {
                    margin: 0;
                    font-size: .6em;
                }

                .man {
                    display: flex;
                    align-items: center;
                    cursor: pointer;

                    .left-box {
                        width: 2em;
                        height: 2em;
                        border: 1px solid $drawer-box-content-color;
                        border-radius: 50%;
                        display: flex;
                        justify-content: center;
                        align-items: center;

                        .left-full {
                            width: 1.8em;
                            height: 1.8em;
                            border-radius: 50%;
                            background-color: $drawer-box-content-color;
                        }
                    }
                }

                .girl {
                    display: flex;
                    align-items: center;
                    cursor: pointer;

                    .right-box {
                        width: 2em;
                        height: 2em;
                        border: 1px solid $drawer-box-content-color;
                        border-radius: 50%;
                        display: flex;
                        justify-content: center;
                        align-items: center;

                        .right-full {
                            width: 1.8em;
                            height: 1.8em;
                            border-radius: 50%;
                            background-color: $drawer-box-content-color;
                        }
                    }
                }
            }

            .radio-disble {
                margin: 0;
                border: none;
                font-size: 1em;
                padding: .3em;
                min-width: 10em;
                border-radius: 10px;
                background-color: $drawer-box-disble-bgcolor;
            }
        }
    }

    .button-box {
        display: flex;
        justify-content: space-around;
        align-items: center;
        height: 3em;
        margin: 3em;

        aside {
            width: 8em;
            height: 100%;
            border-radius: 10px;
            background-color: $button-a-bgcolor;
            color: $button-a-fgcolor;
            display: flex;
            justify-content: center;
            align-items: center;
            font-weight: bolder;
            transition: .5s;
            cursor: pointer;

            &:last-of-type {
                background-color: $button-a-fgcolor;
                color: $button-a-bgcolor;

                &:hover {
                    color: $button-a-fgcolor;
                    background-color: $button-a-bgcolor;
                }
            }

            &:hover {
                color: $button-a-bgcolor;
                background-color: $button-a-fgcolor;
            }
        }
    }
}
</style>