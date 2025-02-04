import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
//一个状态存储一类要共享的数据（存一类常量）
export const useCounterStore = defineStore('counter', () => {
  const count = ref(0)
  //定义状态初始
  //定义变量的技术逻辑
  const doubleCount = computed(() => count.value * 2)
  //定义怎么更改状态
  function increment() {
    count.value++
  }

  //返回
  return { count, doubleCount, increment }
})



