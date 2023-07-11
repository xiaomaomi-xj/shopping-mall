<template>
    <div>
        <ContentView :brandInfo="brandInfo" />
    </div>
</template>

<script>
import { mapState } from 'pinia';
import { useSpecialConfig } from '@/stores/specialConfigStores/useSpecialConfigStore';
import ContentView from './ContentView.vue';
export default {
    name: 'mainPageFooter',
    data() {
        return {
            //底部栏要显示的信息
            brandInfo: {
                message: process.env.VUE_APP_LOADING_TEXT
            }
        }
    },
    components: {
        ContentView
    },
    computed: {
        ...mapState(useSpecialConfig,['extraConfig']),
    },
    mounted(){
        this.brandInfo['message']=this.extraConfig['belowPageText'];
    },
}
</script>

<style lang="scss" scoped></style>