<template>
    <div class="search-result-root">
        <header>
            <SearchResultHeader :titleText="titleText"/>
        </header>
        <main>
            <SearchResultMain />
        </main>
        <footer>
            <SearchResultFooter />
        </footer>
    </div>
</template>

<script>
import SearchResultHeader from '@/components/publicHeaderAndFooter/header';
import SearchResultMain from './main';
import SearchResultFooter from '@/components/publicHeaderAndFooter/footer';
export default {
    name: 'SearchResultPage',
    data() {
        return {
            titleText:'搜索结果页'
        }
    },
    components: {
        SearchResultHeader,
        SearchResultMain,
        SearchResultFooter
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

.search-result-root {
    display: flex;
    justify-content: center;
    flex-direction: column;
    min-height: 100vh;
    main {
        width: 100%;
        background-color: $search-result-main-bgcolor;
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: flex-start;
    }
}
</style>