<template>
    <div class="chat-boos-root">
        <header>
            <ChatBoosHeader :titleText="titleText"/>
        </header>
        <main>
            <ChatBoosMain />
        </main>
        <footer>
            <ChatBoosFooter />
        </footer>
    </div>
</template>

<script>
import ChatBoosHeader from '@/components/publicHeaderAndFooter/header';
import ChatBoosMain from './main';
import ChatBoosFooter from '@/components/publicHeaderAndFooter/footer';
export default {
    name: 'ChatBoosPage',
    data() {
        return {
            titleText:'客服服务消息页'
        }
    },
    components: {
        ChatBoosHeader,
        ChatBoosMain,
        ChatBoosFooter
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

.chat-boos-root {
    display: flex;
    justify-content: center;
    flex-direction: column;
    min-height: 100vh;
    main {
        width: 100%;
        background-color: $chat-boos-main-bgcolor;
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: flex-start;
    }
}
</style>