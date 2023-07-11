<template>
    <div>
        <main>
            <el-icon :size="50">
                <RefreshIcon />
            </el-icon>
        </main>
    </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';
    main{
        .el-icon{
            animation-name: loading;
            animation-duration: 2s;
            //无限循环
            animation-iteration-count: infinite;
            //匀速
            animation-timing-function: linear;
            transform: rotateX(180deg);
        }
        svg{
            color: $loading-color !important;
            
        }
    }
    @keyframes loading{
        100%{
            transform: rotateX(180deg) rotateZ(-360deg);
        }
    }
</style>