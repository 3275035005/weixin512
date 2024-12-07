<template>
	<view>

		<!-- list -->
		<view class="allowance_list">
		
		    <!-- item -->
		    <view class="allowance_item shadow flex" v-for="(item,index) in list" :key="index">
		        
		        <view class="allowance_item__content"> 
		            <view class="allowance_item__title">{{item.content}}</view>
		            <view class="allowance_item__date">{{item.createTime}}</view>
		        </view>
		 
		    </view>
		    <!-- itemm -->
		
		</view>
		<!-- list -->
		
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
				userId:''
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
					url:http+"/wx/getFeedbackByUserId/"+this.userId,
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