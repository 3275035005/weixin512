<!-- 主页公告牌 -->
<template>
	<view id="body">
		<!-- 公告 -->
		<view class="news" style="margin-top: 8rpx;">
			<u-notice-bar :text="news" direction="column" bgColor="#ffffff" color="#3c9cff"></u-notice-bar>
		</view>
	</view>
</template>

<script>
	import {
		http
	} from "../static/service/requestAddress/rtas.js"
	export default {
		name: "indexPlacard",
		data() {
			return {
				news: []
			}
		},

		methods: {
			childOnShow() {
				uni.request({
					url: http + "/wx/getNotice",
					method: "GET",
					complete: (res) => {
						this.news= res.data.data.list
					}
				})
			}
		}

	}
</script>