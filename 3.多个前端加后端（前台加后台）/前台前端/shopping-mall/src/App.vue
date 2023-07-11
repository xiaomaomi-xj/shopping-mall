<template>
  <!-- 页面跳转过渡 -->
  <router-view v-slot="{ Component, route }" v-wechat-title='$route.meta.title'>
    <transition name="all" appear>
        <component :is="Component" :key="route.path" />
    </transition>
  </router-view>

</template>

<script>

</script>

<style lang="scss">
@import '@/assets/sass/colors.scss';

.all-enter-active,
.all-leave-active {
  transition: opacity 0.5s ease;
}

.all-enter-from,
.all-leave-to {
  opacity: 0;
}

* {
  padding: 0;
  margin: 0;
}

::-webkit-scrollbar {
  width: 0;
}
</style>
