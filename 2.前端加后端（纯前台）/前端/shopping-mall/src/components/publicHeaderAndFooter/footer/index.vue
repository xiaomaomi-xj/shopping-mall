<template>
    <div>
        <main>
            <span>{{ extraConfig['belowPageText'] }}</span>
        </main>
    </div>
</template>

<script>
import { mapState } from 'pinia';
import { useSpecialConfig } from '@/stores/specialConfigStores/useSpecialConfigStore';
export default {
    name: 'PublicFooter',
    computed: {
        ...mapState(useSpecialConfig,['extraConfig']),
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';

div {
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 10px;
    box-sizing: border-box;
    background-color: $public-title-bgcolor;

    main {
        width: 80%;
        display: flex;
        justify-content: center;
        align-items: center;

        span {
            font-weight: bolder;
            font-size: 1.2em;
            color: $title-fgcolor;
        }
    }
}
</style>

