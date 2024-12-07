<template>
	<view class="body">
		<!-- 陌生人列表信息 -->
		<view class="userlist" v-for="(item1) in UserList" :key="item1.id">
			<view class="userlist-nav" @click="userlistchlick(item1)">
				<u-avatar class="img-avatar" shape="square" size="35" :src="item1.receiveAvatar" fontSize="26"></u-avatar>
				<text class="userlist-text">{{item1.receiveUserName}}</text>
				<text class="tip" >{{item1.createTime}}</text>
				<!-- 获取最后一条消息 -->
				<text class="indexmessage" >{{item1.content}}</text>
				<u-line margin="10px 0px 0px 0px" hairline="true"></u-line>
			</view>
		</view>
		<view class="loading" >
			<u-loadmore :status="status" :line="line" :nomoreText="nomoreText" /> 
		</view>
	</view>
</template>

<script>
	import {http} from "../static/service/requestAddress/rtas.js"
	const app = getApp()
	export default {
		data() {  
			return {
				userid :"", //登录用户id
				UserList:[],//消息列表
				status: "nomore",
				nomoreText:'没有更多消息了',
				line:true,
			}
		},
		onLoad(){
			let user = uni.getStorageSync('user');
			if(user){
				this.userid = user.id
				this.init()
			}else{
				uni.reLaunch({
					url: "/pages/login/index"
				})
			}
		},
		onShow() {
			
			this.init()
			
		},
		methods:{
			init(){
				uni.request({
					url: http + "/wx/getUserChatList/"+this.userid,
					method: "GET",
					complete: (res) => {
						this.UserList = res.data.data.list
						}
				})
			},
			userlistchlick(item){
				
				let param = {
					id: item.receiveUserId,
					name: item.receiveUserName,
					avatar: item.receiveAvatar
				}
				uni.navigateTo({
					url:"/see/chatpage?params="+encodeURIComponent(JSON.stringify(param))
				})
				
			
			}
		}
	}
</script>

<style>
	@import url("../static/css/see.css");
</style>