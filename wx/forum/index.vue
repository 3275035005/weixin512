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
			<view class="posting-card" v-for="(item,index) in confessionList" :key="index" @click="postcardchlick(item.id)">
				<!-- 用户头像 -->
				<view class="userImg">
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
						<text :class="item.confessionPraiseId == null ? 't-icon t-icon-dianzan':'t-icon t-icon-dianzan-copy'" style="width: 40rpx;height: 40rpx;" @click.stop="dianzanclick(item, index)"></text>
						<!-- <text class="dianzannum"  style="color: #3c9cff;">110</text> -->
					</view>
					<!-- 评论图标 -->
					<view class="comment">
						<text class="t-icon t-icon-pinglun" style="width: 40rpx;height: 40rpx;" @click.stop="commentclick(item.id)"></text>
						<!-- <text class="commentnum">100</text> -->
					</view>
				</view>
		</view>
		</view>
		<!-- 评论模态框 -->
		<view>
			<u-popup :show="show" :round="10" mode="bottom" @close="close" @open="open" >
				<view class="ct">
					<view class="ctview">
						<input placeholder="评论内容" v-model="cttext" />
					</view>
					<view class="plbutton" @click="plbutton">
						<text style="line-height: 68rpx;">发布评论</text>
					</view>
				</view>
			</u-popup>
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
				commentclickId:"",//点击的帖子id
				cttext:"",//评论内容
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
					url:http+"/wx/getConfessionList?userId="+this.userId+"&content="+ this.keyword,
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
			
			
			//添加帖子
			add(){	
				uni.navigateTo({
					url:"/forum/forumadd"
				})
			},
			
			//点击增加取消点赞
			dianzanclick(item, index){
				uni.request({
					url: http+"/wx/confessionPraise",
					method:"POST",
					data:{
						userId: this.userId,
						confessionId:item.id
					},
					complete:(res) =>{
						uni.$u.toast(res.data.message)
						if(res.data.data== null){ // 取消点赞成
							
							this.confessionList[index] = item;
						}else{ // 点赞
							item.confessionPraiseId = res.data.data.data
							this.confessionList[index] = item;
						}
					}
				})
			},
			//点击了评论回复
			commentclick(id){
				this.show = true
				this.commentclickId = id
			},
			
			//点击了兼职详情
			postcardchlick(id){
				uni.navigateTo({
					url:"/forum/info?id="+ id
				})
			},
			//打开模态框
			open(){
				this.show = true
			},
			//关闭模态框
			close(){
				this.show = false
			},
			
			//发布评论
			plbutton(){
				uni.showLoading({
					title:"正在评论中",
					mask: true
				})
				if(this.cttext == '' || this.cttext == undefined || this.cttext == null){
					uni.$u.toast("评论内容不能为空！");
					return;
				}
				uni.request({
					url: http+"/wx/sendConfessionComment",
					method:"POST",
					data:{
						content: this.cttext,
						userId: this.userId,
						type:'1',
						confessionId:this.commentclickId
					},
					complete:(res) =>{
						uni.hideLoading()
						if(res.data.code == 20000){
							uni.$u.toast("评论成功!")
							this.cttext = ""
							this.show = false
							
						}else{
							uni.$u.toast("评论失败!")
							this.show = false
						}
					}
				})
			}
		}
	}
</script> 

<style>
	@import url("/static/css/componentsCss/forum/forumindex.css");
</style>