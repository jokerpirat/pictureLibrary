<template>
  <div class="picture-search-form" >
    <!-- 搜索表单 -->
   <a-form name="searchForm" :model="searchParams" @finish="doSearch">
     <a-space>
       <a-form-item label="关键词" name="searchText">
         <a-input
           v-model:value="searchParams.searchText"
           placeholder="名称和简介搜索"
           allow-clear
         />
       </a-form-item>
       <a-form-item label="分类" name="category">
         <a-select
           v-model:value="searchParams.category"
           placeholder="爱选不选"
           style="width: 150px"
           :options="categoryOptions"
           allow-clear
         />
       </a-form-item>
       <a-form-item label="标签" name="tags">
         <a-select
           v-model:value="searchParams.tags"
           placeholder="爱选不选"
           style="width: 200px"
           mode="tags"
           :options="tagOptions"
           allow-clear
         />
       </a-form-item>
     </a-space>
      <a-form-item label="日期" name="dateRange">
        <a-range-picker
        show-time
        v-model="dateRange"
        :placeholder="['开始时间','结束时间']"
        format="YYYY/MM/DD HH:mm:ss"
        :presets="rangePresets"
        @change="onRangeChange"
        />
      </a-form-item>
      <a-form-item label="名称">
        <a-input
        v-model:value="searchParams.name"
        placeholder="请输入名称"
        allow-clear
        />
      </a-form-item>
     <a-space>
       <a-form-item label="宽度" name="picWidth">
         <a-input-number
         v-model:value="searchParams.picWidth"
         />
       </a-form-item>
       <a-form-item label="高度" name="picHeight">
         <a-input-number
         v-model:value="searchParams.picHeight"
         />
       </a-form-item>
     </a-space>
     <a-form-item label="简介" name="introduction">
       <a-input
       v-model:value="searchParams.introduction"
       placeholder="请输入简介"
       allow-clear
       />
     </a-form-item>
     <a-form-item label="格式" name="picFormat">
       <a-input
       v-model:value="searchParams.picFormat"
       placeholder="请输入格式"
       />
     </a-form-item>
      <a-form-item label="检测是否为人类">
        <a-switch v-model:checked="checked" />
      </a-form-item>
      <a-form-item label="Button">
        <a-space>
          <a-button type="primary" html-type="submit" >选好就点我</a-button>
          <a-button html-type="reset" @click="doClear">人，要重选么</a-button>
        </a-space>
        </a-form-item>
    </a-form>
  </div>
</template>
<script lang="ts" setup>
import { onMounted, reactive, ref } from "vue";
import { listPictureTagCategoryUsingGet } from "@/api/pictureController.ts";
import { message } from "ant-design-vue";
import dayjs from "dayjs";

const checked = ref(false);
interface Props {
  onSearch?: (searchParams: API.PictureQueryRequest) => void
}
const props = defineProps<Props>()
// 搜索条件
const searchParams = reactive<API.PictureQueryRequest>({})
// 搜索数据
const doSearch = () => {
  console.log(searchParams)
  props.onSearch?.(searchParams)
  if (checked.value) {
    alert("人，你好")
  } else {
    alert("诗人握持")
  }
}

// 标签和分类选项
const categoryOptions = ref<string[]>([])
const tagOptions = ref<string[]>([])

/**
 * 获取标签和分类选项
 * @param value
 */
const getTagCategoryOptions = async () => {
  const  res = await listPictureTagCategoryUsingGet()
  if (res.data.code === 0 && res.data.data) {
    tagOptions.value = (res.data.data.tagList ?? []).map((data: string) => {
      return {
        value: data,
        label: data,
      }
    })
    categoryOptions.value = (res.data.data.categoryList ?? []).map((data: string) => {
      return {
        value: data,
        label: data,
      }
    })
  } else {
    message.error("获取标签分类列表失败，" + res.data.message)
  }
}
onMounted(() => {
  getTagCategoryOptions()
})

const dateRange = ref<[]>([])

// 时间范围预设
const rangePresets = ref([
  { label: '过去一周', value: [dayjs().add(-7,'d'),dayjs()]},
  { label: '过去半月', value: [dayjs().add(-14,'d'),dayjs()]},
  { label: '过去一月', value: [dayjs().add(-30,'d'),dayjs()]},
  { label: '过去半年', value: [dayjs().add(-180,'d'),dayjs()]},
])

const onRangeChange = (dates:any[],dateStrings:string[]) => {
  if (dates?.length >= 2) {
    searchParams.startEditTime = dates[0].toDate()
    searchParams.endEditTime = dates[1].toDate()
  } else {
    searchParams.startEditTime = undefined
    searchParams.endEditTime = undefined
  }
}

// 清理
const doClear = () => {
  // 取消所有对象的值
  Object.keys(searchParams).forEach((key) => {
    searchParams[key] = undefined
  })
  // 日期筛选项单独清空，必须定义为空数组
  dateRange.value=[]
  // 清空后重新搜索
  props.onSearch?.(searchParams)
}




</script>
<style scoped>

</style>
