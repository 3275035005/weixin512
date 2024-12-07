<!-- 论坛兼职详情页面 -->
<template>
	<view id="body">
		<!-- 动态详情头部 -->
		<view class="info-top">
			<!-- 用户头像 -->
			<view class="userImg" @click="userlistchlick()">
				<image :src="post.avatar" class="user-logo"></image>
			</view>
			<!-- 用户名称 -->
			<view class="user-name">
				<text>{{post.userName}}</text>
			</view>
			<!-- 兼职时间 -->
			<view class="createTime">
				<text>{{post.createTime}}</text>
			</view>
			<!-- 删除图标 -->
			<view class="deleteicon" @click="deletepost">
				<text class="t-icon t-icon-gengduo1" style="width: 48rpx; height: 48rpx;"></text>
			</view>
			<!-- 兼职内容 -->
			<view class="content">
				<text>{{post.content}}</text>
			</view>
		</view>
		<!-- 评论回复 -->
		<view class="commentview">
			<view style="padding-top: 20rpx;">
				<u-divider text="评论内容"></u-divider>
			</view>
			<!-- 评论数 -->
			<view class="comment">
				<text class="comment-text">评论</text>
				<text class="comment-text-num">{{commentnum}}</text>
			</view>
			<!-- 点赞数 -->
			<view class="praise">
				<text class="praise-text">赞</text>
				<text class="praise-num">{{praisenum}}</text>
			</view>
			<!-- 水平线 -->
			<view class="line" v-if="fistcommentListInfo.length > 0"> 
				<u-line color="#d5d7da"></u-line>
			</view>
			<!-- 详细评论回复信息 -->
			<view class="comment-content" v-if="fistcommentListInfo.length > 0">
				<!-- 评论 -->
				<view class="comment-fistcommentList" v-for="(item,index) in fistcommentListInfo" :key="index" >
					<!-- 具体评论类容 -->
					<view class="fistcomment">
						<!-- 评论用户头像 -->
						<view class="fistcomment-userimg">
							<image :src="item.userAvatar" class="user-logo1"></image>
						</view>
						<!-- 评论用户名称 -->
						<view class="fistcomment-username">
							<text class="fistcomment-usernametext">{{item.userName}}</text>
						</view>
						<!-- 评论时间 -->
						<view class="fistcomment-time">
							<text>{{item.createTime}}</text>
						</view>
						<!-- 评论内容 -->
						<view class="fistcomment-content" @click="commentclick(item)">
							<text class="content-text">{{item.content}}</text>
						</view>
					</view>
					<!-- 该评论回复信息 -->
					<view class="answer"  v-if="item.childComment.length > 0 ">
						<view class="answerList" v-for="(item1, index1) in item.childComment" :key="index1" @click.stop="answerclick(item1)">
							<!-- 回复用户名称 -->
							<view class="answer-username">
								<text>{{item1.userName}}</text>
							</view>
							<text class="center-text">回复</text>
							<!-- 回复的用户名称 -->
							<view class="answer-tousername">{{item1.replyUserName}}</view>
							<text>:  </text> 
							<!-- 回复的类容 -->
							<view class="answer-content">
								<text>{{item1.content}}</text>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- 评论模态框 -->
		<view>
			<u-popup :show="show" :round="10" mode="bottom" @close="close" @open="open" >
				<view class="ct">
					<view class="ctview"> 
						<input :placeholder="commenttext" v-model="cttext" />
					</view>
					<view class="plbutton" @click="hubutton">
						<text style="line-height: 68rpx;">立即回复</text>
					</view>
				</view>
			</u-popup>
		</view>
		<view>
			<u-modal :show="tzshow" :closeOnClickOverlay="true" @confirm="tzdelete" @close="tzplclose">
				<text>确定删除该兼职?</text>
			</u-modal>
		</view>
	</view>
</template>

<script>
	import {http} from "../static/service/requestAddress/rtas.js"
	export default {
		data() {
			return {
				//标签类型
				fistcommentListInfo:[],//评论数据
				show:false,
				
				commenttext:"",//回复给那个用户的文本
				tzshow:false,
				
				confessionId:'', // 兼职id
				commentnum:0, // 评论数量
				praisenum:0, // 点赞数量
				post:{}, // 详情信息
				userId:'',//  用户id
				cttext:"",//回复内容
				// 一级
				commentclickId:"",//点击回复的一级评论id
				replyUserId:'', // 回复用户id
			}
		},
		onLoad(options){
			this.confessionId = options.id;
			let userInfo = uni.getStorageSync('user');
			this.userId = userInfo.id;
			this.getConfessionById();
			this.getConfessionComment();
		},
		methods:{
			// 查询详情
			getConfessionById(){
				uni.request({
					url: http+"/wx/getConfessionById/"+this.confessionId,
					method:"GET",
					complete:(res) =>{
						this.commentnum = res.data.data.row.comment;
						this.praisenum = res.data.data.row.praise;
						this.post = res.data.data.row.confession;
					}
				})
			},
			// 通过兼职id查询兼职详情评论内容
			getConfessionComment(){
				uni.request({
					url: http+"/wx/getConfessionComment/"+this.confessionId,
					method:"GET",
					complete:(res) =>{
						this.fistcommentListInfo = res.data.data.list
					}
				})
			},
			
			//评论点击事件 一级评论
			commentclick(item){
				this.commentclickId = item.id
				this.commenttext = "回复给  :  "+item.userName
				this.show = true
				this.replyUserId = item.userId;
				console.log(item.userId)
			},
			//回复点击事件 二级评论
			answerclick(item){
				this.commentclickId = item.parentId
				this.commenttext = "回复给  :  "+item.userName
				this.show = true,
				this.replyUserId = item.userId
					console.log(item.userId)
			},
			//打开模态框
			open(){
				this.show = true
			},
			//关闭模态框
			close(){
				this.show = false
			},
			//发布回复
			hubutton(){
				var that = this
				//显示消息提示框
				uni.showLoading({
					title:"正在回复中",
					mask: true
				})
				if(this.cttext == "" | this.cttext .length == 0){
					uni.$u.toast("回复内容不能为空")
					return
				}
				uni.request({
					url: http+"/wx/sendConfessionComment",
					method:"POST",
					data:{
						userId: this.userId,
						confessionId:this.confessionId,
						content:this.cttext,
						type: '2',
						parentId: this.commentclickId,
						replyUserId: this.replyUserId
					},
					complete:(res) =>{
						uni.$u.toast(res.data.message)
						this.show = false,
						this.cttext = '',
						this.commentnum+=1
						this.getConfessionComment();
					}
				})
				
				
				this.show = false
				this.cttext = ""
				uni.hideLoading()
			},
		
			
			//打开删除兼职模态框
			deletepost(){
				if(this.post.userId == this.userId){
					this.tzshow = true
				}else{
					uni.$u.toast("只能删除自己的兼职")
				}
			},
			//关闭删除兼职模态框
			tzplclose(){
				this.tzshow = false
			}, 
			//删除兼职
			tzdelete(){
				uni.request({
					url:http+"/wx/delConfession/"+this.post.id,
					method:"DELETE",
					complete:(res)=>{
						if(res.data.code == 20000){
							uni.$u.toast("删除成功")
							this.tzshow = false
							uni.reLaunch({
								url:"/forum/index"
							})
						}else{
							uni.$u.toast("删除失败!")
						}
						
					}
				})
			},
			
			// 跳转聊天页面
			userlistchlick(){
				if(this.userId != this.post.userId){
					
					let param = {
						id: this.post.userId,
						name: this.post.userName,
						avatar: this.post.avatar
					}
					uni.navigateTo({
						url:"/see/chatpage?params="+encodeURIComponent(JSON.stringify(param))
					})
				}
				
			}
		}
	}
</script> 

<style>
	@import url("../static/css/componentsCss/forum/foruminfo.css");
</style>