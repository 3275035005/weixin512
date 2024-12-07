<template>
	<view>
		<!-- banner -->
		<view class="goods_banner" style=" text-align: center;">
			<image :src="http + activity.image" class="banner_image" />
		</view>
		<!-- banner -->

		<!-- info -->
		<view class="goods_info">

			<!-- title -->
			<view class="goods_info__title">{{activity.title}}</view>
			<!-- title -->

			<view class="goods_info__bottom flex" v-if="activity.seat != 0">

				<!-- people -->
				<view class="goods_info__people" v-if="activity.seat != 0">已参加 {{activity.seat}} 人</view>

			</view>
			<!-- bottom -->

		</view>
		<!-- info -->

		<!-- group -->
		<view class="goods_group" v-if="activity.seat != 0">

			<view class="goods_column">参与活动人</view>
			<!-- title -->

			<!-- content -->
			<view class="goods_group__content flex">
				<view class="goods_group__item" v-for="item in userList" wx:key="index">
					<image :src="item.avatar" class="goods_group__icon" />
					<view class="goods_group__text">{{item.name}}</view>
				</view>
			</view>
		</view>
		<!-- content -->
		<view class="goods_content" >
			<u-parse :content="content" @preview="preview" @navigate="navigate"></u-parse>
		</view>
		<!-- content -->
		<!-- footer -->
		<view class="goods_footer flex">
			<view class="left flex">
			    <view class="item">
					<button v-if="activity.activityCollectId == null" @click="isSubscription">
						<image src="/static/img/sc.svg" class="icon"></image>
						<view class="text">收藏</view>
					</button>
					<button v-else @click="isSubscription">
						<image src="/static/img/qx.svg" class="icon" ></image>
						<view class="text">取消收藏</view>
					</button>
			    </view>
			</view>
		    <view class="right">
		        <button class="buy" @click="buy">参加活动</button>
		    </view>
		</view>
		<!-- footer -->


	</view>
</template>
`
<script>
	import {
		http
	} from "../static/service/requestAddress/rtas.js"
	export default {

		data() {
			return {
				activity: {},
				userList:'',
				content: '',
				userId: '',
				http: http
			}
		},
		onLoad(option) {
			//获取用户信息
			let userInfo = uni.getStorageSync('user');
			this.userId = userInfo.id;
			this.getActivityById(option.id);
		},

		methods: {
			getActivityById(id) {
				uni.request({
					url: http + "/wx/getActivityById/" + id + "/" + this.userId,
					method: "GET",
					complete: (res) => {
						this.activity = res.data.data.row
						this.content = res.data.data.row.content.replace(/\<img/gi,
							'<img style="max-width:100%;height:auto" ')
						this.userList = res.data.data.row.userList
					}
				})
			},
			// 参加活动
			buy(){
				uni.request({
					url: http + "/wx/sendActivity/"+this.activity.id+"/"+this.userId,
					method: "POST",
					complete: (res) => {
						uni.hideLoading()
						uni.$u.toast(res.data.message)
						this.getActivityById(this.activity.id);
					}
				})
			},
			// 收藏和取消
			isSubscription() {
				uni.request({
					url: http + "/wx/activityCollect",
					method: "POST",
					data: {
						activityId: this.activity.id,
						userId: this.userId
					},
					complete: (res) => {
						uni.hideLoading()
						uni.$u.toast(res.data.message)
						if (res.data.data == null) { // 取消收藏
							this.activity.activityCollectId = null;
						} else { // 收藏
							this.activity.activityCollectId = res.data.data.data
						}
					}
				})
			},


		}
	}
</script>

<style>
	@import url(index_info.css);
</style>
