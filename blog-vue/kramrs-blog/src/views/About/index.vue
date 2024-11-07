<template>
	<div class="page-header">
		<h1 class="page-title">关于</h1>
		<img class="page-cover" :src="blog.blogInfo.siteConfig.aboutWallpaper"
			 alt=""/>
		<Waves></Waves>
	</div>
	<div class="bg">
		<div class="page-container">
			<div class="avatar-box">
				<img class="author-avatar" :src="blog.blogInfo.siteConfig.authorAvatar"/>
			</div>
			<MdPreview editorId="preview-only" :modelValue="blog.blogInfo.siteConfig.aboutMe"/>
		</div>
	</div>
</template>

<script setup lang="ts">
import {useBlogStore} from "@/store";
import {MdPreview} from "md-editor-v3";

const blog = useBlogStore();
</script>

<style scoped>
.author-avatar {
	width: 110px;
	height: 110px;
	border-radius: 50%;
	transition: all 0.5s;
}

.author-avatar:hover {
	transform: rotate(360deg);
}
</style>
