<template>
  <div id="baidu-image-search">
    <a-flex style="margin-bottom: 50px">
      <a-card style="width: 240px">
        <template #cover>
          <img
            style="height: 180px; object-fit: cover"
            :alt="picture.name"
            :src="picture.thumbnailUrl ?? picture.url"
          />
        </template>
      </a-card>
    </a-flex>
    <h3 style="color: brown">搜图结果:</h3>
    <a-list
      :grid="{ gutter: 16, xs: 1, sm: 2, md: 3, lg: 4, xl: 5, xxl: 6 }"
      :data-source="dataList"
    >
      <template #renderItem="{ item }">
        <a-list-item style="padding: 0">
          <!-- 单张图片 -->
          <a :href="item.fromUrl" target="_blank">
            <a-card>
              <template #cover>
                <img
                  style="height: 180px; object-fit: cover"
                  :src="item.thumbUrl"
                  loading="lazy"
                />
              </template>
            </a-card>
          </a>
        </a-list-item>
      </template>

    </a-list>

  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import { getPictureVoByIdUsingGet, searchPictureByPictureUsingPost } from "@/api/pictureController.ts";
import { message } from "ant-design-vue";


const route = useRoute()
const pictureId = computed(() => {
  return route.query?.pictureId
})
const picture = ref<API.PictureVO>({})

// 获取传来的图片ID对应的图片
const getOldPicture = async () => {
  const id = route.query?.pictureId
  if (id) {
    const res = await getPictureVoByIdUsingGet({
      id: id
    })
    if (res.data.code === 0 && res.data.data) {
      const data = res.data.data
      picture.value = data
    }
  }
}

onMounted(() => {
  getOldPicture()
})

const dataList = ref<API.ImageSearchResult[]>([])
// 获取搜图结果
const fetchData = async () => {
  const res = await searchPictureByPictureUsingPost({
    pictureId: pictureId.value
  })
  if (res.data.code === 0 && res.data.data) {
    dataList.value = res.data.data ?? []
  } else {
    message.error("获取dataList数据失败," + res.data.message)
  }
}

// 页面加载时请求一次
onMounted(() => {
  fetchData()
})


</script>

<style scoped>
</style>
