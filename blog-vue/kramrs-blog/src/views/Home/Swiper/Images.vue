<template>
	<div class="imgs">
		<ul v-show="displayedItems.length > 0">
			<li class="item" v-for="(carousel, index) of displayedItems"
				:key="index" :style="{'background-image': 'url(' + carousel.imgUrl + ')'}">
			</li>
		</ul>
	</div>
</template>

<script setup lang="ts">
import {getCarouselList} from "@/api/carousel";
import {Carousel} from "@/api/carousel/types";

const carouselList = ref<Carousel[]>([]);
const currentIndex = ref(0);
let intervalId: any = null;
// 计算属性，用于显示当前的子数组
const displayedItems = computed(() => {
	if (carouselList.value.length > 0) {
		return [carouselList.value[currentIndex.value]];
	} else {
		return [];
	}
});

// 开始遍历
const startIteration = () => {
	intervalId = setInterval(() => {
		currentIndex.value = (currentIndex.value + 1) % carouselList.value.length;
	}, 36000); // 动画结束时更新换下一张
};
onMounted(() => {
	getCarouselList().then(({data}) => {
		carouselList.value = data.data;
		startIteration();
	});
});
onUnmounted(() => {
	if (intervalId !== null) {
		clearInterval(intervalId);
	}
});
</script>

<style lang="scss" scoped>
@import "@/assets/styles/mixin.scss";

.imgs {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100vh;
	z-index: -9;
	background-color: #363636;
	overflow: hidden;

	.item {
		@include absolute;
		width: 100%;
		height: 100%;
		background: no-repeat 50% 50% / cover;
		opacity: 0;
		animation: imageAnimation 36s linear infinite 0s;
		backface-visibility: hidden;
		transform-style: preserve-3d;

		&:nth-child(2) {
			animation-delay: 6s;
		}

		&:nth-child(3) {
			animation-delay: 12s;
		}

		&:nth-child(4) {
			animation-delay: 18s;
		}

		&:nth-child(5) {
			animation-delay: 24s;
		}

		&:nth-child(6) {
			animation-delay: 30s;
		}
	}

	&::before {
		content: '';
		display: block;
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, .2);
		transition: all .2s ease-in-out 0s;
	}
}

@keyframes imageAnimation {
	0% {
		opacity: 0;
		animation-timing-function: ease-in;
	}

	2% {
		opacity: 1;
	}

	8% {
		opacity: 1;
		transform: scale(1.05);
		animation-timing-function: ease-out;
	}

	17% {
		opacity: 1;
		transform: scale(1.1);
	}

	25% {
		opacity: 1;
		transform: scale(1.1);
	}

	100% {
		opacity: 0;
	}
}
</style>
