<!-- 论坛页面 -->
<template>
	<view id="body">
		<!-- 顶部导航 -->
		<view class="nav">
			<!-- 搜索 -->
			<view class="search"> 
				<u-search placeholder="请输入搜索内容" v-model="keyword" :showAction="false" @search="search"></u-search>
			</view>
			<!-- 发布图标 -->
			<view class="fabu" @click="add"> 
				<text class="t-icon t-icon-fabu2" style="width: 52rpx;height: 52rpx;"></text>
			</view>
		</view>
		<!-- 帖子 -->
		<view class="posting">
			<view class="posting-card" v-for="(item,index) in confessionList" :key="index">
				<!-- 用户头像 -->
				<view class="userImg"  @click="userlistchlick(item)">
					<image :src="item.avatar" class="user-logo"></image>
				</view>
				<!-- 用户名称 -->
				<view class="user-name">
					<text>{{item.userName}}</text>
				</view>
				<view class="createTime">
					<text>{{item.createTime}}</text>
				</view>
				<!-- 内容 -->
				<view class="content">
					<text>{{item.content}}</text>
				</view>
				<view class="dingwei">
					<!-- 点赞 -->
					<view class="dianzan">
						<text :class="item.jobPraiseId == null ? 't-icon t-icon-dianzan':'t-icon t-icon-dianzan-copy'" style="width: 40rpx;height: 40rpx;" @click.stop="dianzanclick(item, index)"></text>
					</view>
				</view>
		</view>
		</view>
	</view>
</template>

<script>
	import {http} from "../static/service/requestAddress/rtas.js"
	export default {
		data() {
			return {
				keyword:"",//搜索框内容
				confessionList:[],//数据数组
				dianzantb: "",//点赞样式 
				show:false,
				ss:false,//是否是搜索标记
				userId:''
			}
		},
		onLoad(){
			//获取用户信息
			let userInfo = uni.getStorageSync('user');
			this.userId = userInfo.id;
			this.getConfessionList();
		},
		methods:{
			
			// 兼职列表查询
			getConfessionList(){
				uni.request({
					url:http+"/wx/getJobList?userId="+this.userId+"&content="+ this.keyword,
					method:"GET",
					complete:(res) =>{
						this.confessionList = res.data.data.list;
					}
				})
			},
			//搜过方法
			search(){
				if(this.keyword == "" | this.keyword.length  == 0){
					uni.$u.toast("搜索内容不能为空")
					return
				}
				this.getConfessionList();
				
			},
			
			
			//发布兼职
			add(){	
				uni.navigateTo({
					url:"/job/jobadd"
				})
			},
			
			//点击增加取消点赞
			dianzanclick(item, index){
				uni.request({
					url: http+"/wx/jobPraise",
					method:"POST",
					data:{
						userId: this.userId,
						jobId:item.id
					},
					complete:(res) =>{
						uni.$u.toast(res.data.message)
						if(res.data.data== null){ // 取消点赞成
							this.confessionList[index] = item;
						}else{ // 点赞
							item.jobPraiseId = res.data.data.data
							this.confessionList[index] = item;
						}
					}
				})
			},
			// 跳转聊天页面
			userlistchlick(item){
				if(this.userId != item.userId){
					
					let param = {
						id: item.userId,
						name: item.userName,
						avatar: item.avatar
					}
					uni.navigateTo({
						url:"/see/chatpage?params="+encodeURIComponent(JSON.stringify(param))
					})
				}else{
					uni.$u.toast("您不能和自己聊天")
				}
				
			}
		}
	}
</script> 

<style>
	@import url("/static/css/componentsCss/forum/forumindex.css");
	.dianzan {
		left: 610rpx;
	}
</style>