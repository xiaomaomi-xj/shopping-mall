<template>
    <div>
        <main @click="togoodsSpecificsPageFun(goodsAdData['goodsId'])">
            <img :src="goodsAdData['imgUrl']" alt="">
        </main>
    </div>
</template>

<script>
export default {
    props: {
        goodsAdData: {
            type: Object,
            required: true,
            default: new Object()
        }
    },
    methods: {
        //跳转页面
        togoodsSpecificsPageFun(goodsId) {
            this.$router.push({ name: 'goodsSpecifics', params: { goodsId } });
        }
    }
}
</script>

<style lang="scss" scoped>
div {
    width: 100%;
    cursor: pointer;

    main {
        border-radius: 10px;
        overflow: hidden;
        width: 100%;
        height: 160px;

        img {
            width: 100%;
            height: 100%;
        }
    }
}
</style>