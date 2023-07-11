<template>
    <div>
        <main>
            <span>{{ brandInfo['message'] }}</span>
        </main>
    </div>
</template>

<script>
    export default{
        props:{
            brandInfo:{
                type:Object,
                required: true,
                default: process.env.VUE_APP_LOADING_TEXT
            }
        }
    }
</script>

<style lang="scss" scoped>
@import '@/assets/sass/colors.scss';
div{
    width: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 10px;
    box-sizing: border-box;
    main{
        width: 80%;
        display: flex;
        justify-content: center;
        align-items: center;
        span{
            font-weight: bolder;
            font-size: 1.2em;
            color: $title-fgcolor;
        }
    }
}
</style>