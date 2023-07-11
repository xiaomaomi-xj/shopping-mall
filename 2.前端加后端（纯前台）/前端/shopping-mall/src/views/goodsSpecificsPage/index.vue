<template>
    <div class="goods-specifice-root">
        <header>
            <GoodsSpecificeHeader :titleText="titleText"/>
        </header>
        <main>
            <GoodsSpecificeMain />
        </main>
        <footer>
            <GoodsSpecificeFooter />
        </footer>
    </div>
</template>

<script>
import GoodsSpecificeHeader from '@/components/publicHeaderAndFooter/header';
import GoodsSpecificeMain from './main';
import GoodsSpecificeFooter from '@/components/publicHeaderAndFooter/footer';
export default {
    name: 'GoodsSpecificePage',
    data() {
        return {
            titleText:'商品详情页'
        }
    },
    components: {
        GoodsSpecificeHeader,
        GoodsSpecificeMain,
        GoodsSpecificeFooter
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

.goods-specifice-root {
    display: flex;
    justify-content: center;
    flex-direction: column;
    min-height: 100vh;
    main {
        width: 100%;
        background-color: $goods-specifics-main-bgcolor;
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: flex-start;
    }
}
</style>