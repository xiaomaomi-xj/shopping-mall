<template>
    <div class="current-user-info-box">
        <div class="input-box">
            <aside class="head">
                <img :src="currentUserInfo['headImgUrl']" :class="enableModify ? 'img-pointer' : ''" @click="uploadHeadImg"
                    alt="">
                <input type="file" class="current-user-info-box-file-input" accept=".bmp,.jpg,.png,.gif,.webp,.jpeg,.ico">
                <span class="row" v-if="enableModify" @click="uploadHeadImg"></span>
                <span class="cloum" v-if="enableModify" @click="uploadHeadImg"></span>
            </aside>
            <div class="user-email">
                <span>账号：</span>
                <aside>{{ currentUserInfo['userAccount'] }}</aside>
            </div>
            <div class="user-name">
                <span>用户名：</span>
                <div class="input-disble" v-if="!enableModify">
                    {{ currentUserInfo['userName'] }}
                </div>
                <div class="input" v-if="enableModify">
                    <input type="text" v-model="currentUserInfo['userName']" maxlength="10" />
                </div>
            </div>
            <div class="user-sex">
                <span>性别：</span>
                <div class="radio-box" v-if="enableModify">
                    <div class="man" @click="modifySexFun(1)">
                        <span>男：</span>
                        <div class="left-box">
                            <div class="left-full" v-if="currentUserInfo['userSex'] == 1"></div>
                        </div>
                    </div>
                    <div class="girl" @click="modifySexFun(0)">
                        <span>女：</span>
                        <div class="right-box">
                            <div class="right-full" v-if="currentUserInfo['userSex'] == 0"></div>
                        </div>
                    </div>
                </div>
                <div class="radio-disble" v-if="!enableModify">
                    {{ currentUserInfo['userSex'] == 1 ? '男' : '女' }}
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
import { mapActions, mapState } from 'pinia';
import { useUserCheck } from '@/stores/userStores/useUserCheckStore';
import { useUserModify } from '@/stores/userStores/useUserModifyStore';
export default {
    data() {
        return {
            //个人信息
            currentUserInfo: {},
            //是否启用了修改
            enableModify: false,
            headImgInput: null
        }
    },
    computed: {
        ...mapState(useUserCheck, ['currentUserData']),
    },
    mounted() {
        //初始化
        this.initCurrentUserInfo();
        this.startEventFun();
    },
    methods: {
        //拿到pinia方法
        ...mapActions(useUserModify, ['modifyUserInfoFun']),
        //开启监听函数
        startEventFun() {
            this.$nextTick(() => {
                const fileInput = document.querySelector('.current-user-info-box-file-input');
                //开启监听,只监听一次就行了
                fileInput.addEventListener('change', () => {
                    this.handleFileData(fileInput).then((v) => {
                        //修改头像
                        this.currentUserInfo['headImgUrl'] = v;
                    }, e => {
                        if (e == '未选择') {
                            //未选择的不要报错
                            return;
                        }
                        this.$selfMessage.openMessage({
                            type: 'error',
                            message: e
                        });
                    });
                });
            });
        },
        //初始化函数,因为加载主页面，这个值就已经有了
        initCurrentUserInfo() {
            this.currentUserInfo['headImgUrl'] = this.currentUserData['headImgUrl'];
            this.currentUserInfo['userSex'] = this.currentUserData['userSex'];
            this.currentUserInfo['userName'] = this.currentUserData['userName'];
            this.currentUserInfo['userAccount'] = this.currentUserData['userEmail'];
            //没有头像的情况下，要直接按照性别来显示
            if (this.currentUserInfo['headImgUrl'] == null || this.currentUserInfo['headImgUrl'] == '' || this.currentUserInfo['headImgUrl'].trim().length == 0) {
                if (this.currentUserInfo['userSex'] == '1') {
                    this.currentUserInfo['headImgUrl'] = window.manOnSexImgUrl;
                } else {
                    this.currentUserInfo['headImgUrl'] = window.girlOnSexImgUrl;
                }
            }
        },
        //修改函数，主要用于修改能产生直观的感觉,但是此函数并不做真正的修改
        modifyCurrentUserInfo() {
            //如果是默认头像就不更改头像
            if (this.currentUserInfo['headImgUrl'] == window.girlOnSexImgUrl || this.currentUserInfo['headImgUrl'] == window.manOnSexImgUrl) {
                if (this.currentUserInfo['userSex'] == '1') {
                    this.currentUserInfo['headImgUrl'] = window.manOnSexImgUrl;
                } else {
                    this.currentUserInfo['headImgUrl'] = window.girlOnSexImgUrl;
                }
            }
        },
        //重置函数,由抽屉组件调用
        resetEnableFun() {
            this.enableModify = false;
            //关闭的时候初始化
            this.initCurrentUserInfo();
        },
        //上传头像
        uploadHeadImg() {
            //只有开启了修改才做处理
            if (this.enableModify) {
                this.$nextTick(() => {
                    const fileInput = document.querySelector('.current-user-info-box-file-input');
                    //模拟点击
                    fileInput.click();
                });
            }
        },
        //处理上传的文件数据
        handleFileData(fileInput) {
            const rule = ['.bmp', '.jpg', '.png', '.gif', '.webp', '.jpeg', '.ico'];
            //图片大小1mb
            const imgSize = 1048576;
            return new Promise((res, rej) => {
                let file = fileInput.files[0];
                if (file == undefined) {
                    rej('未选择');
                }
                let isAccept = false;
                let fileName = file['name'];
                let fileSize = file['size'];
                rule.map(v => {
                    if (fileName.endsWith(v)) {
                        if (fileSize <= imgSize) {
                            isAccept = true;
                        }
                    }
                });
                if (isAccept) {
                    let fr = new FileReader();
                    fr.onload = function () {
                        res(fr.result);
                    }
                    fr.readAsDataURL(file);
                } else {
                    rej('不支持的文件后缀名,或者文件大小不得超过1mb！');
                }
            });
        },
        //保存了数据
        saveModifyFun() {
            //逻辑函数传过来，保存后不能一直确定
            if (this.enableModify == true) {
                if (this.currentUserInfo['userName'].trim().length === 0) {
                    this.$selfMessage.openMessage({
                        type: 'error',
                        message: '用户名不能为空！'
                    });
                } else {
                    this.modifyUserInfoFun(
                        this.currentUserInfo['headImgUrl'],
                        this.currentUserInfo['userAccount'],
                        this.currentUserInfo['userName'],
                        this.currentUserInfo['userSex'],
                        () => {
                            //关闭修改
                            //关闭抽屉
                            this.enableModify = false;
                            this.$emit('closeDrawerBox');
                        }
                    );
                }
            }
        },
        //模拟单选框，修改性别
        modifySexFun(sex) {
            this.currentUserInfo['userSex'] = sex;
            this.modifyCurrentUserInfo();
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

        .head {
            position: relative;

            .current-user-info-box-file-input {
                width: 0;
                height: 0;
            }

            img {
                width: 6em;
                height: 6em;
                border-radius: 50%;
            }

            .img-pointer {
                cursor: pointer;
            }

            .row {
                display: flex;
                position: absolute;
                width: 30%;
                height: 5px;
                background-color: $drawer-box-transparent-bgcolor;
                left: 50%;
                top: 50%;
                transform: translateX(-50%) translateY(-50%);
                cursor: pointer;
            }

            .cloum {
                display: flex;
                position: absolute;
                width: 5px;
                height: 30%;
                background-color: $drawer-box-transparent-bgcolor;
                left: 50%;
                top: 50%;
                transform: translateX(-50%) translateY(-50%);
                cursor: pointer;
            }
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