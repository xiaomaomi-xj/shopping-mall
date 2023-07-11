<template>
    <div>
        <ContentView :belowPageText="extraConfig['belowPageText']" />
    </div>
</template>

<script>
import ContentView from './ContentView.vue';
import { mapState } from 'pinia';
import { useSpecialConfig } from '@/stores/specialConfigStores/useSpecialConfigStore';
export default {
    name: 'mainPageFooter',
    components: {
        ContentView
    },
    computed: {
        ...mapState(useSpecialConfig,['extraConfig']),
    }
}
</script>

<style lang="scss" scoped></style>