<template>
	<view id="body" :style="{backgroundImage:'url(' + user.img + ')'}">
		<view class="usertop">
		</view>
		<view>
			<view class="userimg">
				<image :src="user.avatar" class="userimgleft"></image>
			</view>
			<image :src="user.avatar" class="userimgright"></image>
			<text class="username">{{user.name}}</text>
			<text class="userid">ID:{{user.id}}</text>
			<view class="userheart">
				<u-icon :name="heart" color="#eeeeee" size="24px" @click="userlike()"></u-icon>
			</view>
		</view>
		<view class="fillup">
		</view>
		<u-divider text="其他服务"></u-divider>
		<view>
			<u-grid :border="false" @click="click1" col="4" >
			    <u-grid-item v-for="(item,index) in Other":key="index">
			        <text :class="item.title":customStyle="{paddingTop:20+'rpx'}" style="margin-top: 20rpx; width: 64rpx; height: 64rpx;"></text>
			        <text class="grid-text" style="color:#000000;">{{item.name}}</text>
			    </u-grid-item>
			</u-grid>
			<u-toast ref="uToast" />
		</view>
		<view class="login">
			<u-button  type="primary" text="退出登录" @click="login"></u-button>
		</view>
	</view>
</template>
	
<script>
	import {socketurl} from "../../static/service/requestAddress/rtas.js"
	const app = getApp()
	export default {
			data() {
				return {
					heart: "heart",
					show:false,
					user:{},
					loginuser:{
						name:"",
						password:"",
					},
					Other: [
						{
							name:"我的收藏",
							title:"t-icon t-icon-shoucang",
							url:"/collect/index"
						},
						{
							name:"意见反馈",
							title:"t-icon t-icon-pingjia",
							url:"/feedback/index"
						},
						{
							name:"订单中心",
							title:"t-icon t-icon-dingdan1",
							url:"/myorder/order"
						},
						{
							name:"账号设置",
							title:"t-icon t-icon-shezhi",
							url:"/userSet/userSetindex"
						}
					],
				}
			},
			onShow(){
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
				this.user = user;
				
			},
			methods: {
				userlike(){
					if(this.heart == "heart" ){
						this.heart = "heart-fill"
					}else{
						this.heart = "heart"
					}
				},
				
				click1(name) {  //其他服务点击事件
					var path = (this.Other[name]).url
					uni.navigateTo({
						url:path
					})
				},
				//点击登录验证
				login(){
					//清除所有缓存
					uni.clearStorageSync()
					uni.$u.toast("退出登录成功!")
					uni.reLaunch({
						url: '/login/login'
					}) 
				},
				
			}
		}
</script>

<style>
	@import url("@/static/css/user.css");
	page {
		background-color: #f5f5f5;
	}
</style>