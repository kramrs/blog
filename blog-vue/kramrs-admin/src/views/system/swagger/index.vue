<template>
  <i-frame v-model:src="url"></i-frame>
</template>

<script setup lang="ts">
import iFrame from '@/components/iFrame/index.vue'
import {ref} from 'vue';

const url = ref(window.location.origin + "/api/doc.html");
</script>
