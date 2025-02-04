<template>
  <div id="basicLayout">
    <a-layout style="min-height: 100vh">
      <a-layout-header class="header"><GlobalHeader/></a-layout-header>
      <a-layout-content class="content">
        <router-view/>
      </a-layout-content>
      <a-layout-footer class="footer">
        <a href="https://www.codefather.cn" target="_blank">88岁边学边哭</a>
      </a-layout-footer>
    </a-layout>
  </div>
</template>
<script setup lang="ts">

import GlobalHeader from "@/components/GlobalHeader.vue";
</script>

<style scoped>
#basicLayout .header{
  padding-inline: 20px;
  background: #eeeeee;
  color: unset;
  margin-bottom: 16px;
}
#basicLayout .content{
  padding: 20px;
  background: linear-gradient(to right, #fefefe,#fff);
  margin-bottom: 28px;
}
#basicLayout .footer{
  background: #eeeeee;
  padding: 16px;
  position: fixed;
  left: 0;
  right: 0;
  bottom: 0;
  text-align: center;
}
</style>
