<template>
	<view>

		<view class="pic_list">
			<view class="item flex page_box" @click="handleNewsClick(item.id)" v-for="(item,index) in list" :key="index">
				<image :src="http + item.image" class="item_pic" mode="aspectFill"></image>
				<view class="item_box">
					<view class="item_title" >{{item.title}}</view>
					<view class="item_info flex" style="margin-top: 50rpx;">
						<view class="info_author">{{item.classifiedName}}</view>
						<view class="info_date">{{item.createTime}}</view>
					</view>
				</view>
			</view>
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
				list: [],
				userId:'',
				http: http
			}
		},
		onLoad(){
			//获取用户信息
			let userInfo = uni.getStorageSync('user');
			this.userId = userInfo.id;
		},
		onShow() {
			this.init();
		},
		methods: {
			init() {
				uni.request({
					url:http+"/wx/getActivityCollectByUserId/"+this.userId,
					method:"GET",
					complete:(res) =>{
						this.list = res.data.data.list;
					}
				})
			},
			
			// 跳转页面详情
			handleNewsClick(id) {
				uni.navigateTo({
					url:'/activity/index_info?id='+id
				})
			},
		}
	}
</script>

<style>
	@import url(index.css);
</style>