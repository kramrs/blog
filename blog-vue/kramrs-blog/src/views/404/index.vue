<template>
	<div class="page-header">
		<h1 class="page-title">页面不存在</h1>
		<img class="page-cover" :src="blog.blogInfo.siteConfig.aboutWallpaper"
			 alt="">
		<Waves></Waves>
	</div>
	<div class="bg">
		<div class="page-container">
			<div class="notfound">Σ( ° △ °|||)︴404！何も見えない</div>
		</div>
	</div>
</template>

<script setup lang="ts">
import {useBlogStore} from "@/store";

const {blog} = useBlogStore();
</script>

<style scoped>
.notfound {
	width: 18.75rem;
	height: 22.625rem;
	background: no-repeat bottom;
	text-align: center;
	margin: 6.25rem auto;
}
</style>
