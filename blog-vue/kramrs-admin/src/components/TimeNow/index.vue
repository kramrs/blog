<template>
  <NormalFooterToolbar>{{ text }}</NormalFooterToolbar>
</template>

<script setup lang="ts">
import {computed, onBeforeUnmount, ref} from 'vue';
import dayjs from 'dayjs';
import {NormalFooterToolbar} from 'md-editor-v3';

const weekNames = {
  'en-US': ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
  'zh-CN': ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期日']
};

const time = ref(dayjs().format('YYYY/MM/DD HH:mm:ss'));

const text = computed(() => {

  const weekday = dayjs().day();
  return `${time.value} ${weekNames['zh-CN'][weekday > 0 ? weekday - 1 : 6]}`;
});

const timerId = setInterval(() => {
  time.value = dayjs().format('YYYY/MM/DD HH:mm:ss');
}, 1_000);

onBeforeUnmount(() => {
  clearInterval(timerId);
});
</script>

<script lang="ts">
export default {
  name: 'TimeNow'
};
</script>
