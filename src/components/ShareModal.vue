<template>
  <a-modal
    v-model:visible="visible"
    :footer="false"
    @cancel="closeModal"
  >
    <a-typography-link copyable>
      {{ link }}
    </a-typography-link>
    <div style="margin-bottom: 16px" />
    <h4> 手机扫码查看 </h4>
    <a-qrcode :value="link" />
  </a-modal>
</template>

<script setup lang="ts">

import { ref } from "vue";


interface Props{
  title: string
  link: string
}

const props = withDefaults(defineProps<Props>(), {
  title: ()=>'分享',
  link: ()=>'https://www.bilibili.com/video/av1406184064/?vd_source=18aac027e252b8b702b4ccedc0653bcf'
})

// 是否可见
const visible = ref(false)

// 打开弹窗
const openModal = () => {
  visible.value = true
}

// 关闭弹窗
const closeModal = () => {
  visible.value = false
}

// 给父组件跳出弹窗
defineExpose({
  openModal,
})

</script>

<style scoped>

</style>
