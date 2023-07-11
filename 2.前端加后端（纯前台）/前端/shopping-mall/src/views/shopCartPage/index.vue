<template>
    <div class="shop-cart-root">
        <header>
            <ShopCartHeader :titleText="titleText"/>
        </header>
        <main>
            <ShopCartMain />
        </main>
        <footer>
            <ShopCartFooter />
        </footer>
    </div>
</template>

<script>
import ShopCartHeader from '@/components/publicHeaderAndFooter/header';
import ShopCartMain from './main';
import ShopCartFooter from '@/components/publicHeaderAndFooter/footer';
export default {
    name: 'shopCartPage',
    data() {
        return {
            titleText:'我的购物车'
        }
    },
    components: {
        ShopCartHeader,
        ShopCartMain,
        ShopCartFooter
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

.shop-cart-root {
    display: flex;
    justify-content: center;
    flex-direction: column;
    min-height: 100vh;
    main {
        width: 100%;
        background-color: $shop-cart-main-bgcolor;
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: flex-start;
    }
}
</style>