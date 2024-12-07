<!-- 首页轮播图组件 -->
<template>
	<view id="body">
		<!-- 轮播图 -->
		<view class="u-demo-block">
			<u-swiper
				:list="imglist"
				previousMargin="10"
				nextMargin="10"
				circular
				autoplay="true"
				interval=3000
				radius="5"
				bgColor="#ffffff"
			></u-swiper>
		</view>
	</view>
</template>

<script>
	import {
		http
	} from "../static/service/requestAddress/rtas.js"
	export default {
		data() {
			return {
				imglist: [],
			}
		},
		methods: {
			childOnShow() {
				uni.request({
					url: http + "/wx/getBanner",
					method: "GET",
					complete: (res) => {
						let list = res.data.data.list
						for (let i = 0; i < list.length; i++) {
							this.imglist.push(http+list[i])
						}
						
					}
				})
			}
		}
	}
</script> 

<style scoped>
	@import url("../static/css/componentsCss/index/indexCarousel.css");
</style>