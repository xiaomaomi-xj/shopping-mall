<template>
    <div>
        <main>
            <div class="gizmo-404-box">
                <div class="cloud" :class="darkCloudStyle">
                    <div class="c1" :class="darkCloudStyle"></div>
                    <div class="c2" :class="darkCloudStyle"></div>
                    <div class="c3" :class="darkCloudStyle"></div>
                </div>
                <ul class="raindrop">
                    <li v-for="(liEl, key) in lisEl" :key="key"
                        :style="`left: ${liEl['leftV']}px;animation-delay: ${0.3 * liEl['delayV']}s;`"></li>
                </ul>
                <div class="ground" :class="darkCloudStyle"></div>
            </div>
            <aside>
                <span class="title">{{ computeStoreName }}&nbsp;&nbsp;-&nbsp;-&nbsp;-&nbsp;404</span>
                <span class="content">没有这个页面哦！</span>
                <article>
                    <span class="link" @click="toMainPageFun">回到首页>>></span>
                </article>
            </aside>
        </main>
    </div>
</template>

<script>
import { mapState, mapActions } from 'pinia';
import { useSpecialConfig } from '@/stores/specialConfigStores/useSpecialConfigStore';
export default {
    name: 'NotFundPage',
    data() {
        return {
            //雨滴个数和随机延迟秒数
            lisEl: [],
            //乌云
            darkCloudStyle: ''
        }
    },
    computed: {
        ...mapState(useSpecialConfig, ['extraConfig']),
        //商家名字
        computeStoreName() {
            return this.extraConfig['storeNameHaveSpecial'] ? this.extraConfig['storeName'].replaceAll('^', this.extraConfig['specialText']) : this.extraConfig['storeName'];
        }
    },
    mounted() {
        this.initExtraConfig();
        this.modifyPos();
    },
    methods: {

        ...mapActions(useSpecialConfig, ['initExtraConfig']),

        //修改雨滴位置
        modifyPos() {
            setTimeout(() => {
                this.darkCloudStyle = 'dark-cloud';
            });
            setTimeout(() => {
                for (let i = 0; i < 170; i += 6) {
                    this.lisEl.push({
                        leftV: i,
                        delayV: Math.floor(Math.random() * 100)
                    });
                }
            }, 10000);
        },
        //跳转页面
        toMainPageFun() {
            this.$router.push({ name: 'home' });
        }
    },
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

.dark-cloud {
    background-color: $not-fund-dark-cloud-bgcolor !important;

    &::after {
        background-color: $not-fund-dark-cloud-bgcolor !important;
    }
}

main {
    width: 100vw;
    height: 100vh;
    color: $not-fund-main-color;
    background-color: $not-fund-main-bgcolor;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    flex-direction: column;

    aside {
        display: flex;
        align-items: center;
        flex-direction: column;
        border: 2px solid $not-fund-main-border-color;
        padding: 3em;
        border-radius: 10px;
        box-shadow: 0px 0px 10px 10px $not-fund-shadow-color;

        .title {
            font-size: 3em;
            font-weight: bolder;
        }

        .content {
            font-size: 2em;
            font-weight: bolder;
            margin-top: 1em;
        }

        article {
            margin-top: 1em;
            border-top: 1px solid $not-fund-main-border-color;
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 5em;

            .link {
                font-size: 1.2em;
                color: $special-color;
                font-weight: bolder;
                cursor: pointer;
            }
        }
    }

    .gizmo-404-box {
        margin-top: 5em;
        margin-bottom: 2em;
        position: relative;

        .cloud {
            width: 200px;
            height: 50px;
            background-color: $not-fund-cloud-bgcolor;
            border-radius: 20px;
            position: relative;
            transition: 10s;

            div {
                position: absolute;
                transition: 10s;

            }

            .c1 {
                width: 60px;
                height: 60px;
                background-color: $not-fund-cloud-bgcolor;
                border-radius: 100%;
                bottom: 0px;
                left: -5px;

                &::after {
                    content: '';
                    position: absolute;
                    background-color: $not-fund-cloud-bgcolor;
                    width: 30px;
                    height: 30px;
                    border-radius: 100%;
                    left: 50px;
                    transition: 10s;

                }
            }

            .c2 {
                width: 80px;
                height: 80px;
                background-color: $not-fund-cloud-bgcolor;
                left: 60px;
                border-radius: 100%;
                bottom: 0;
            }

            .c3 {
                width: 60px;
                height: 60px;
                background-color: $not-fund-cloud-bgcolor;
                border-radius: 100%;
                left: 145px;
                bottom: 0;

                &::after {
                    content: '';
                    position: absolute;
                    background-color: $not-fund-cloud-bgcolor;
                    width: 30px;
                    height: 30px;
                    border-radius: 100%;
                    left: -20px;
                    transition: 10s;
                }
            }

        }

        .ground {
            margin-top: 100px;
            margin-bottom: 2em;
            width: 200px;
            height: 10px;
            background-color: $not-fund-cloud-bgcolor;
            //椭圆
            border-radius: 100%;
            transition: 10s;
        }

        .raindrop {
            position: absolute;
            display: flex;
            transition: 10s;

            li {
                list-style: none;
                position: absolute;
                margin: 10px;
                display: block;
                width: 6px;
                height: 10px;
                background-color: $not-fund-dark-cloud-bgcolor;
                border-radius: 100%;
                top: -30px;
                animation-name: rain-move;
                animation-duration: 1.1s;
                //无限循环
                animation-iteration-count: infinite;
                //匀速
                animation-timing-function: cubic-bezier(0.6, -0.07, 1, 1);
                //延迟

            }
        }
    }
}

//下雨动画
@keyframes rain-move {
    100% {
        top: 90px;
    }
}
</style>