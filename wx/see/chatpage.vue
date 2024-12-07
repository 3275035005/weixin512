<template>
	<view class="body">
		<scroll-view :scroll-y="true" :scroll-into-view="scrollToView" :show-scrollbar="true"
			:scroll-with-animation="true" class="message">
			<view class="list" v-for="(item,index) in message" :key="index">
				<view class="left" v-if="item.receiveUserId!=receiveUserId">
					<view class="user-chat" :id="'msg'+index">
						<view class="left-userimg">
							<u-avatar shape="square" size="35" :src="receiveAvatar" fontSize="26"></u-avatar>
						</view>
						<view class="left-chat-icon"></view>
						<text class="left-usertext">{{item.content}}</text>
					</view>
				</view>
				<view class="right" v-else>
					<view class="my-chat" :id="'msg'+index">
						<view class="right-userimg">
							<u-avatar shape="square" size="35" :src="avatar" fontSize="26"></u-avatar>
						</view>
						<!-- 对话框小样式 -->
						<view class="right-chat-icon">
						</view>
						<text class="right-usertext">{{item.content}}</text>
					</view>
				</view>
			</view>
		</scroll-view>
		<!-- 底部对话框 -->
		<view class="bottom">
			<view class="input">
				<input placeholder="请输入内容" class="input-input" v-model="value" />
			</view>
			<button class="myuserbutton" @click="sendmessage">发送</button>
		</view>
	</view>
</template>

<script>
	import {
		http
	} from "../static/service/requestAddress/rtas.js"
	const app = getApp()
	export default {
		data() {
			return {
				receiveAvatar:'',
				receiveUserId: '', // 接收消息用户id
				userId: '', // 发送消息用户id
				avatar:'', // 发送用户头像
				value: "", // 输入消息内容

				message: [], //渲染页面的消息数组
				total: 0, //再该页面时收到的消息数，用于退出该页面清楚对应的tabber数量
				scrollToView: "", //滚动
			}
		},
		onLoad(options) {
			let receiveUser =  JSON.parse(decodeURIComponent(options.params));
			uni.setNavigationBarTitle({
				title: receiveUser.name,
			})
			this.receiveUserId = receiveUser.id
			this.receiveAvatar = receiveUser.avatar
			console.log(receiveUser)
			uni.setNavigationBarColor({
				frontColor: '#000000', //前景颜色值，包括按钮、标题、状态栏的颜色，仅支持 #ffffff 和 #000000
			})

			//获取缓存用户信息
			let user = uni.getStorageSync('user')
			this.userId = user.id,
			this.avatar = user.avatar,
			console.log(user)
			//获取消息
			this.getmessage()
			// this.onSystem()
		},
		//结束
		onUnload() {
			// var UserList = JSON.parse(uni.getStorageSync("userfriendlist"))
			// for (var i = 0; i < UserList.length; i++) {
			// 	if (this.options.id == UserList[i].id) {
			// 		this.$set(UserList[i], "totalMessage", 0)
			// 		//更新缓存
			// 		var str = UserList
			// 		uni.setStorageSync("userfriendlist", JSON.stringify(str))
			// 		this.$forceUpdate()
			// 		break
			// 	}
			// }
			// //设置tabbar的值
			// var num = uni.getStorageSync("tabbar")
			// if (str != "null") {
			// 	num = num - this.total
			// 	uni.setStorageSync("tabbar", num)
			// }
		},
		methods: {
			//查询消息
			onSystem() {

			},
			//获取消息
			getmessage() {
				uni.request({
					url: http + "/wx/getUserChatBySendUserAndReceiveUser/" + this.userId + "/" + this.receiveUserId,
					method: "GET",
					complete: (res) => {
						this.message = res.data.data.list
						console.log(this.message);
					}
				})

			},
			//发送消息
			sendmessage() {
				if(this.value == '' || this.value == undefined || this.value == null){
					uni.$u.toast("请填写发送内容！");
					return;
				}
				
				uni.showLoading({
					title:"发送中",
					mask: true
				})
			
				let parem = {
					content: this.value,
					sendUserId: this.userId,
					receiveUserId: this.receiveUserId
				}
				uni.request({
					url: http+"/wx/sendUserChat",
					method:"POST",
					data: parem,
					complete:(res) =>{
						uni.hideLoading()
						this.message.push(parem);
						this.value = null //置空输入框
						this.end()
					}
				})

				

			},
			//发送接收消息滚动到底部
			end() {
				//设置消息滚动到底部
				this.$nextTick(() => {
					//这里将 最后一条聊天的下标'chatItem_' + index 赋值给 刚刚绑定的scrollIntoIndex。这样就可以实现滚动到底部
					var num = this.message.length - 1
					console.log("num值是:" + num)
					this.scrollToView = 'msg' + num
					console.log("点击后的id值是：" + this.scrollToView)
				});
			}
		}
	}
</script>

<style>
	@import url("../static/css/chatpage.css");
</style>
