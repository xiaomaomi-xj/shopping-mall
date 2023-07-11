<template>
    <div>
        <main>
            <aside @click="toMainPageFun">{{ extraConfig['specialText'] }}</aside>
            <div>
                <span class="title">{{ titleText }}</span>
            </div>
            <div>
                <span class="link" @click="toMainPageFun">回到首页</span>
            </div>
        </main>
    </div>
</template>

<script>
import { mapState, mapActions } from 'pinia';
import { useSpecialConfig } from '@/stores/specialConfigStores/useSpecialConfigStore';
export default {
    name: 'PublicHeader',
    props: {
        titleText: {
            type: String,
            required: true,
            default: process.env.VUE_APP_LOADING_TEXT
        }
    },
    computed: {
        ...mapState(useSpecialConfig,['extraConfig']),
    },
    mounted() {
        //初始化，头部初始化，尾部就不要初始化了
        this.initExtraConfig();
    },
    methods: {

        ...mapActions(useSpecialConfig,['initExtraConfig']),

        toMainPageFun() {
            this.$router.push({ name: 'home' });
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

main {
    height: 5em;
    background-color: $public-title-bgcolor;
    display: flex;
    align-items: center;
    justify-content: center;

    aside {
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 10px;
        font-size: 1.5em;
        font-weight: bolder;
        background-color: $special-color;
        color: $icon-text-color;
        border-radius: 30%;
        cursor: pointer;
        margin-right: 1em;
    }

    div {
        width: 35%;

        &:last-of-type {
            display: flex;
            justify-content: flex-end;
        }

        span {
            color: $public-title-fgcolor;
        }

        .title {
            font-size: 1.8em;
            font-weight: bold;
        }

        .link {
            cursor: pointer;
            font-size: 1.2em;
            font-weight: bold;

            &:hover {
                color: $public-title-hover-color;
            }
        }
    }
}
</style>