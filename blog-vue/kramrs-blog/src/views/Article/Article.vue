<template>
	<div class="page-header" v-if="article">
		<div class="page-title">
			<h1 class="article-title">{{ article.articleTitle }}</h1>
			<div class="article-meta">
				<div class="first-meta">
          <span><svg-icon icon-class="calendar" style="margin-right:0.15rem;"></svg-icon>
            <span class="text">发表于 </span>{{ formatDate(article.createTime) }}
          </span>
					<span class="item" v-if="article.updateTime"><svg-icon icon-class="update"
																		   style="margin-right:0.15rem;"></svg-icon>
            <span class="text">更新于 </span>{{ formatDate(article.updateTime) }}
          </span>
					<span class="item"><svg-icon icon-class="eye" style="margin-right:0.15rem;"></svg-icon>
            <span class="text">阅读量 </span>{{ article.viewCount }}</span>
				</div>
				<div class="second-meta">
          <span><svg-icon icon-class="edit" size="0.9rem" style="margin-right:0.15rem;"></svg-icon>
            <span class="text">字数统计 </span>{{ count(wordNum) }} 字
          </span>
					<span class="item"><svg-icon icon-class="clock" style="margin-right:0.15rem;"></svg-icon>
            <span class="text">阅读时长 </span>{{ readTime }} 分钟
          </span>
					<span class="item">
            <svg-icon icon-class="category" style="margin-right:0.15rem;"></svg-icon>{{
							article.category.categoryName
						}}
          </span>
				</div>
			</div>
		</div>
		<img class="page-cover" :src="article.articleCover" alt="">
		<!-- 波浪 -->
		<Waves></Waves>
	</div>
	<div class="bg">
		<div class="main-container" v-if="article">
			<div class="left-container" :class="app.sideFlag ? 'w-full' : ''">
				<div class="article-container">
					<div class="post-TianliGPT">
						<div class="tianliGPT-title">
							<i class="tianliGPT-title-icon">
								<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
									 width="48px" height="48px" viewBox="0 0 48 48">
									<g id="&#x673A;&#x5668;&#x4EBA;" stroke="none" stroke-width="1" fill="none"
									   fill-rule="evenodd">
										<path
											d="M34.717885,5.03561087 C36.12744,5.27055371 37.079755,6.60373651 36.84481,8.0132786 L35.7944,14.3153359 L38.375,14.3153359 C43.138415,14.3153359 47,18.1768855 47,22.9402569 L47,34.4401516 C47,39.203523 43.138415,43.0650727 38.375,43.0650727 L9.625,43.0650727 C4.861585,43.0650727 1,39.203523 1,34.4401516 L1,22.9402569 C1,18.1768855 4.861585,14.3153359 9.625,14.3153359 L12.2056,14.3153359 L11.15519,8.0132786 C10.920245,6.60373651 11.87256,5.27055371 13.282115,5.03561087 C14.69167,4.80066802 16.024865,5.7529743 16.25981,7.16251639 L17.40981,14.0624532 C17.423955,14.1470924 17.43373,14.2315017 17.43948,14.3153359 L30.56052,14.3153359 C30.56627,14.2313867 30.576045,14.1470924 30.59019,14.0624532 L31.74019,7.16251639 C31.975135,5.7529743 33.30833,4.80066802 34.717885,5.03561087 Z M38.375,19.4902885 L9.625,19.4902885 C7.719565,19.4902885 6.175,21.0348394 6.175,22.9402569 L6.175,34.4401516 C6.175,36.3455692 7.719565,37.89012 9.625,37.89012 L38.375,37.89012 C40.280435,37.89012 41.825,36.3455692 41.825,34.4401516 L41.825,22.9402569 C41.825,21.0348394 40.280435,19.4902885 38.375,19.4902885 Z M14.8575,23.802749 C16.28649,23.802749 17.445,24.9612484 17.445,26.3902253 L17.445,28.6902043 C17.445,30.1191812 16.28649,31.2776806 14.8575,31.2776806 C13.42851,31.2776806 12.27,30.1191812 12.27,28.6902043 L12.27,26.3902253 C12.27,24.9612484 13.42851,23.802749 14.8575,23.802749 Z M33.1425,23.802749 C34.57149,23.802749 35.73,24.9612484 35.73,26.3902253 L35.73,28.6902043 C35.73,30.1191812 34.57149,31.2776806 33.1425,31.2776806 C31.71351,31.2776806 30.555,30.1191812 30.555,28.6902043 L30.555,26.3902253 C30.555,24.9612484 31.71351,23.802749 33.1425,23.802749 Z"
											id="&#x5F62;&#x72B6;&#x7ED3;&#x5408;" fill="#444444"
											fill-rule="nonzero"></path>
									</g>
								</svg>
							</i>
							<div class="tianliGPT-title-text">Kramrs's blog 文章摘要</div>
							<div id="tianliGPT-tag" class="tianliGPT-tag">
								TianliGPT
							</div>
						</div>
						<div id="tianliGPT-text" class="tianliGPT-explanation">
							生成中...<span class="blinking-cursor"></span>
						</div>
					</div>
					<MdPreview editorId="preview-only" :modelValue="article.articleContent"
							   :theme="isDark ? 'dark' : 'light'"/>
					<div class="article-post">
						<div class="tag-share">
							<router-link :to="`/tag/${tag.id}`" class="article-tag" v-for="tag in article.tagVOList"
										 :key="tag.id">
								<svg-icon icon-class="tag" size="0.8rem"></svg-icon>
								{{ tag.tagName }}
							</router-link>
							<Share class="share-info" :url="articleHref" :title="article.articleTitle"></Share>
						</div>
						<div class="reward">
							<button class="btn" :class="isLike(article.id)" @click="like">
								<svg-icon icon-class="like" size="0.9rem"></svg-icon>
								点赞
								<span>{{ article.likeCount }}</span>
							</button>
							<n-popover trigger="click" v-if="blog.blogInfo.siteConfig.isReward">
								<template #trigger>
									<button class="btn reward-btn">
										<svg-icon icon-class="qr_code" size="0.9rem"></svg-icon>
										打赏
									</button>
								</template>
								<div class="reward-all">
                  <span>
                    <img class="reward-img" v-lazy="blog.blogInfo.siteConfig.weiXinCode"/>
                    <div class="reward-desc">微信</div>
                  </span>
									<span style="margin-left: 0.3rem;">
                    <img class="reward-img" v-lazy="blog.blogInfo.siteConfig.aliCode"/>
                    <div class="reward-desc">支付宝</div>
                  </span>
								</div>
							</n-popover>
							<p class="tea" v-if="blog.blogInfo.siteConfig.isReward">请我喝[茶]~(￣▽￣)~*</p>
						</div>
						<div class="copyright">
							<ul>
								<li class="author">
									<svg-icon icon-class="author" size="0.9rem" style="margin-right:0.3rem"></svg-icon>
									<strong>本文作者： </strong>{{ blog.blogInfo.siteConfig.siteAuthor }}
								</li>
								<li class="link">
									<svg-icon icon-class="article_link" size="0.9rem"
											  style="margin-right:0.3rem"></svg-icon>
									<strong>本文链接：</strong>
									<a :href="articleHref">{{ articleHref }}</a>
								</li>
								<li class="license">
									<svg-icon icon-class="article_share" size="0.8rem"
											  style="margin-right:0.3rem"></svg-icon>
									<strong>版权声明： </strong>本站所有文章除特别声明外，均采用
									<a href="https://creativecommons.org/licenses/by-nc-sa/4.0/deed.zh" target="_blank">CC
										BY-NC-SA 4.0</a>
									许可协议。转载请注明文章出处！
								</li>
							</ul>
						</div>
						<!-- 上下文 -->
						<div class="post-nav">
							<div class="item" v-if="article.lastArticle">
								<router-link :to="`/article/${article.lastArticle?.id}`" class="post-cover"
											 :style="articleCover(article.lastArticle.articleCover)">
									<span class="post-last-next">上一篇</span>
									<h3 class="post-title">{{ article.lastArticle.articleTitle }}</h3>
								</router-link>
							</div>
							<div class="item" v-if="article.nextArticle">
								<router-link :to="`/article/${article.nextArticle?.id}`" class="post-cover"
											 :style="articleCover(article.nextArticle.articleCover)">
									<span class="post-last-next">下一篇</span>
									<h3 class="post-title">{{ article.nextArticle.articleTitle }}</h3>
								</router-link>
							</div>
						</div>
						<CommentList :comment-type="commentType"></CommentList>
					</div>
				</div>
			</div>
			<div class="right-container" :class="app.sideFlag ? 'hidden' : ''">
				<div class="side-card">
					<svg-icon icon-class="category"></svg-icon>
					目录
					<MdCatalog v-if="articleLoaded" editorId="preview-only" :scrollElement="scrollElement"/>
				</div>
			</div>
		</div>
	</div>
</template>

<script setup lang="ts">
import {getArticle, likeArticle} from "@/api/article";
import {ArticleInfo, ArticlePagination} from "@/api/article/types";
import {CategoryVO} from "@/api/category/types";
import {useAppStore, useBlogStore, useUserStore} from "@/store";
import {formatDate} from "@/utils/date";
import {Share} from 'vue3-social-share';
import 'vue3-social-share/lib/index.css';
import {MdCatalog, MdPreview} from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import axios from "axios";
import {useDark} from "@vueuse/core";
import '@/assets/styles/tianli_gpt.css';

const user = useUserStore();
const app = useAppStore();
const blog = useBlogStore();
const route = useRoute();
const articleHref = window.location.href;
const scrollElement = document.documentElement;
const isDark = useDark();
const data = reactive({
	articleLoaded: false,
	wordNum: 0,
	readTime: 0,
	commentType: 1,
	article: {
		id: 0,
		articleCover: "",
		articleTitle: "",
		articleContent: "",
		articleType: 0,
		viewCount: 0,
		likeCount: 0,
		category: {} as CategoryVO,
		tagVOList: [],
		createTime: "",
		lastArticle: {} as ArticlePagination,
		nextArticle: {} as ArticlePagination,
		updateTime: ""
	} as ArticleInfo,
});
const {articleLoaded, wordNum, readTime, commentType, article} = toRefs(data);
const articleCover = computed(() => (cover: string) => 'background-image:url(' + cover + ')');
const isLike = computed(() => (id: number) => user.articleLikeSet.indexOf(id) != -1 ? "like-btn-active" : "like-btn");
const count = (value: number) => {
	if (value >= 1000) {
		return (value / 1000).toFixed(1) + "k";
	}
	return value;
};
const deleteHTMLTag = (content: string) => {
	return content
		.replace(/<\/?[^>]*>/g, "")
		.replace(/[|]*\n/, "")
		.replace(/&npsp;/gi, "");
};
const like = () => {
	if (!user.id) {
		app.setLoginFlag(true);
		return;
	}
	let id = article.value.id;
	likeArticle(id).then(({data}) => {
		if (data.flag) {
			//判断是否点赞
			if (user.articleLikeSet.indexOf(id) != -1) {
				article.value.likeCount -= 1;
			} else {
				article.value.likeCount += 1;
			}
			user.articleLike(id);
		}
	});
};

let tianliGPTIsRunning = false;
const aiShowAnimation = (text: string) => {
	const element = document.querySelector(".tianliGPT-explanation");

	if (!element) {
		return;
	}
	if (tianliGPTIsRunning) {
		return;
	}

	tianliGPTIsRunning = true;
	const typingDelay = 25;
	const punctuationDelayMultiplier = 6;

	element.style.display = "block";
	element.innerHTML = "生成中..." + '<span class="blinking-cursor"></span>';

	//给AItag添加动画
	const aiTag = document.querySelector('.tianliGPT-tag');
	aiTag.classList.add('loadingAI');

	let animationRunning = true;
	let currentIndex = 0;
	let initialAnimation = true;
	let lastUpdateTime = performance.now();

	const animate = () => {
		if (currentIndex < text.length && animationRunning) {
			const currentTime = performance.now();
			const timeDiff = currentTime - lastUpdateTime;

			const letter = text.slice(currentIndex, currentIndex + 1);
			const isPunctuation = /[，。！、？,.!?]/.test(letter);
			const delay = isPunctuation ? typingDelay * punctuationDelayMultiplier : typingDelay;

			if (timeDiff >= delay) {
				element.innerText = text.slice(0, currentIndex + 1);
				lastUpdateTime = currentTime;
				currentIndex++;

				if (currentIndex < text.length) {
					element.innerHTML =
						text.slice(0, currentIndex) +
						'<span class="blinking-cursor"></span>';
				} else {
					element.innerHTML = text;
					element.style.display = "block";
					tianliGPTIsRunning = false;
					observer.disconnect(); // 暂停监听
					//给AItag停止动画
					const aiTag = document.querySelector('.tianliGPT-tag');
					aiTag.classList.remove('loadingAI');
				}
			}
			requestAnimationFrame(animate);
		}
	}

	// 使用IntersectionObserver对象优化ai离开视口后暂停的业务逻辑，提高性能
	const observer = new IntersectionObserver((entries) => {
		animationRunning = entries[0].isIntersecting; // 标志变量更新
		if (animationRunning && initialAnimation) {
			setTimeout(() => {
				requestAnimationFrame(animate);
			}, 200);
		}
	}, {threshold: 0});
	let post_ai = document.querySelector('.post-TianliGPT');
	observer.observe(post_ai); //启动新监听
}
const postTianliGPT = (title: string, text: string) => {
	axios.post("https://summary.tianli0.top/", {
		"content": text,
		"key": "rUHcufcQi4U",
		"title": title,
		"url": window.location.href,
		"user_openid": "1940"
	}).then(res => {
		console.log("文章摘要返回内容", res);
		const data = res.data;
		if (data.summary != null && data.summary !== "") {
			aiShowAnimation(data.summary);
		} else {
			aiShowAnimation(data.err_msg);
		}
	});
};
onMounted(() => {
	getArticle(Number(route.params.id)).then(({data}) => {
		article.value = data.data;
		document.title = article.value.articleTitle;
		wordNum.value = deleteHTMLTag(article.value.articleContent).length;
		readTime.value = Math.round(wordNum.value / 400);
		articleLoaded.value = true;
		postTianliGPT(article.value.articleTitle, article.value.articleContent);
	});
});
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.article-container {
	border-radius: 0.5rem;
	overflow: hidden;
	box-shadow: 0 0 1rem var(--box-bg-shadow);
}

.article-post {
	margin: 0 2rem;
	padding-bottom: 1rem;
}

.article-title {
	font-weight: 500;
	font-size: 2.5rem;
	letter-spacing: 0.125rem;
	text-align: center;
	color: var(--header-text-color);
}

.article-meta {
	@include flex;
	flex-direction: column;
	font-size: 0.875rem;

	.item {
		margin-left: 0.625rem;
	}
}

.tag-share {
	display: flex;
	align-items: center;

	.share-info {
		margin-left: auto;
	}
}

.reward {
	margin: 1.25rem auto;
	padding: 0.625rem 0;
	text-align: center;

	.btn {
		border-radius: 0.3125rem;
		color: var(--grey-0);
		cursor: pointer !important;
		padding: 0 0.9375rem;
		font: inherit;
	}

	.like-btn-active {
		background: var(--primary-color);
	}

	.like-btn {
		background: #999;
	}

	.reward-btn {
		position: relative;
		margin-left: 1rem;
		background: var(--primary-color);
	}

	.tea {
		font-size: 0.8125em;
		color: var(--grey-5);
		margin-top: 0.5rem;
	}
}

.reward-all {
	display: flex;
	align-items: center;
}

.reward-img {
	width: 150px;
	height: 150px;
	display: block;
}

.reward-desc {
	margin: -5px 0;
	color: #858585;
	text-align: center;
}

.copyright {
	font-size: 0.75em;
	padding: 1rem 2rem;
	margin-bottom: 2.5rem;
	border-radius: 0.625rem;
	background: var(--grey-2);
	color: var(--grey-6);
}

.post-nav {
	display: flex;
	margin-bottom: 2.5rem;
	border-radius: 0.625rem;
	overflow: hidden;

	.item {
		width: 50%;
	}

	.post-cover {
		display: flex;
		flex-direction: column;
		color: var(--header-text-color);
		padding: 1.25rem 2.5rem;
		background-size: cover;
		animation: blur 0.8s ease-in-out forwards;

		&:before {
			content: "";
			position: absolute;
			width: 100%;
			height: 100%;
			background: linear-gradient(135deg, #434343, #000);
			opacity: 0.5;
			transition: all 0.2s ease-in-out 0s;
			z-index: -1;
			top: 0;
			left: 0;
		}
	}

	.post-last-next {
		font-size: 0.8125rem;
	}
}

.post-cover:hover::before {
	opacity: 0.4;
}

@media (max-width: 767px) {
	.article-title {
		font-size: 1.5rem;
	}

	.article-meta .text {
		display: none;
	}

	.article-post {
		margin: 0 0.5rem;
	}

	.post-nav {
		flex-direction: column;
	}

	.post-nav .item {
		width: 100%;
	}

	.reward-img {
		width: 105px;
		height: 105px;
	}

}
</style>
