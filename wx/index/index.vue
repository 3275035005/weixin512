<template>
	<view id="body">
		<indexPlacard ref="indexPlacard"></indexPlacard>
		<indexCarousel ref="indexCarousel"></indexCarousel>
		<indexclassify></indexclassify>
		<view style="width: 696rpx;margin: 0 auto;">
			<indexGoodContent ref="indexGoodContent"></indexGoodContent>
		</view>
	</view>
</template>
<script>
	import {
		http
	} from "../static/service/requestAddress/rtas.js"
	import indexclassify from 'index/indexClassify'
	import indexPlacard from 'index/indexPlacard'
	import indexCarousel from "index/indexCarousel"
	import indexGoodContent from "index/indexGoodContent"
	export default {
		//注册组件
		components: {
			indexclassify,
			indexPlacard,
			indexCarousel,
			indexGoodContent,
		},
		data() {
			return {

			}
		},
		onLoad(){
			// uni.navigateTo({
			// 	url:'/forum/index'
			// })
		},

		onShow() {
			
			let user = uni.getStorageSync("user")
			if (user == undefined || user == null || user == '') {
				uni.showToast({
					title: '登录失效',
					icon: 'none',
					duration: 2000
				})
				setTimeout(function() {
					uni.reLaunch({
						url: '/login/login'
					})

				}, 2000);
			}
			this.$refs.indexPlacard.childOnShow();
			this.$refs.indexCarousel.childOnShow();
			this.$refs.indexGoodContent.childOnShow();
		},
		
	}
</script>

<style scoped>
	@import url("../static/css/componentsCss/index/index.css");

	page {
		background-color: #f5f5f5;
	}
</style>