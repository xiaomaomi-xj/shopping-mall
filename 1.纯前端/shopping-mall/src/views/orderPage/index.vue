<template>
    <div class="order-root">
        <header>
            <OrderHeader :titleText="titleText"/>
        </header>
        <main>
            <OrderMain />
        </main>
        <footer>
            <OrderFooter />
        </footer>
    </div>
</template>

<script>
import OrderHeader from '@/components/publicHeaderAndFooter/header';
import OrderMain from './main';
import OrderFooter from '@/components/publicHeaderAndFooter/footer';
export default {
    name: 'OrderPage',
    data() {
        return {
            titleText:'我的订单'
        }
    },
    components: {
        OrderHeader,
        OrderMain,
        OrderFooter
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

.order-root {
    display: flex;
    justify-content: center;
    flex-direction: column;
    min-height: 100vh;
    main {
        width: 100%;
        background-color: $order-main-bgcolor;
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: flex-start;
    }
}
</style>