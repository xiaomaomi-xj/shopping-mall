<template>
    <div class="drawer-box-root" :class="closeStyle[0]">
        <div class="left"></div>
        <div class="right" :class="closeStyle[1]">
            <el-icon :size="25" @click="closeDrawerBox">
                <CloseBoldMessage />
            </el-icon>
            <!-- 动态组件，让其他调用者传递组件,可以是已注册的组件名也可以是组件对象 -->
            <component :is="receiveComponent['component']" ref="child" @closeDrawerBox="closeDrawerBox"></component>
        </div>
    </div>
</template>

<script setup>
//接受一个动态组件，数据不能是代理数据，需要借助setup语法的markRaw
import { markRaw, reactive, nextTick, ref } from 'vue';
//关闭样式
const closeStyle = reactive(['drawer-box-close drawer-box-die', 'right-close']);
//接收到的组件，要使其不是响应的，减少开支
const receiveComponent = markRaw({ component: null });
//调用子组件方法
const child = ref(null);
//开启函数
function openDrawerBox(component) {
    //接收传过来的组件
    receiveComponent['component'] = component;
    //关闭body的滚动
    nextTick(() => {
        document.body.style.overflow = "hidden";
        closeStyle[0] = 'drawer-box-close';
        closeStyle[1] = 'right-close';
        //先让其存在，再更改样式，让其有过渡时间
        setTimeout(() => {
            closeStyle[0] = '';
            closeStyle[1] = '';
        });
    });
}
//关闭函数
function closeDrawerBox() {
    //调用子组件的重置方法
    child.value.resetEnableFun();
    //开启body的滚动
    nextTick(() => {
        document.body.style = '';
        //先不让他消失，让他有个过渡时间
        closeStyle[0] = 'drawer-box-close';
        closeStyle[1] = 'right-close';
        //过渡时间一过，再让他消失
        setTimeout(() => {
            closeStyle[0] = 'drawer-box-close drawer-box-die';
        }, 500);
    });
}
defineExpose({
    //只需要抛出开启函数就好
    openDrawerBox
});
</script>

<style lang="scss" scoped>
@import '@/assets/sass/zIndexValue.scss';
@import '@/assets/sass/colors.scss';

.drawer-box-close {
    opacity: 0;
}

.drawer-box-die {
    display: none !important;
}

.right-close {
    width: 0px !important;
    overflow: hidden;
}

.drawer-box-root {
    width: 100vw;
    height: 100vh;
    position: fixed;
    z-index: $drawerBoxZIndex;
    top: 0;
    left: 0;
    display: flex;
    color: $drawer-box-content-color;
    //贝兹曲线(两个点控制曲线)（0，0 第一个点）（0.9，0第二个点），这样就会形成先快再很慢
    //还是匀速得劲
    transition: all .3s cubic-bezier(0, 0, 1, 1);

    .left {
        height: 100%;
        flex: 1;
        background-color: $drawer-box-transparent-bgcolor;
    }

    .right {
        height: 100%;
        //黄金比例
        width: 38.2%;
        background-color: $drawer-box-content-bgcolor;
        box-shadow: -2px -2px 10px 10px $shadow-color;
        padding: 2em;
        transition: all .3s cubic-bezier(0, 0, 1, 1);

        svg {
            color: $drawer-box-close-color !important;
            cursor: pointer;

            &:hover {
                color: $drawer-box-close-hover-color !important;
            }
        }

    }
}
</style>