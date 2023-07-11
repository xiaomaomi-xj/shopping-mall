<template>
    <div>
        {{ tipsTextOnEmpty }}
        <span class="link" @click="toMainPageFun">回到首页>>></span>
    </div>
</template>

<script>
export default {
    name: 'EmptyGoodsPage',
    props: {
        tipsTextOnEmpty: {
            type: String,
            required: true,
            default: ''
        }
    },
    methods: {
        toMainPageFun() {
            this.$router.push({ name: 'home' });
        }
    },
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

div {
    color: $title-bgcolor;
    width: 100%;
    display: flex;
    justify-content: space-around;
    align-items: center;
    font-size: 1.5em;
    height: 10em;
    flex-direction: column;

    .link {
        color: $special-color;
        cursor: pointer;
    }
}
</style>