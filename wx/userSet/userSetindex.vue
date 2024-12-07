<template>
	<view class="body">
		<!-- 头像 -->
		<view class="userimg" @click="userimgclick">
			<text class="userimgText">头像</text>
			<view class="img1">
				<image style="width: 120rpx; height: 120rpx;border-radius: 200rpx;overflow: hidden;" :src="img"></image>
				<!-- <u-avatar shape="circle" size="60" :src="img">
				</u-avatar> -->
			</view>
		</view>
		<!-- 名称 -->
		<view class="name">
			<text class="nameleft">昵称</text>
			<view class="nametext">
				<u--input placeholder="请输入昵称" v-model="name"></u--input>
			</view>
		</view>
		<view class="phone">
			<text class="phoneleft">手机号</text>
			<view class="phonetext">
				<u--input placeholder="请输入手机号" v-model="phone"></u--input>
			</view>
		</view>
		<view class="button" @click="update">
			<text>修 改</text>
		</view>
	</view>
</template>

<script>
	import upload from "../uni_modules/uview-plus/libs/config/props/upload.js"
	import {
		http
	} from "../static/service/requestAddress/rtas.js"
	const app = getApp()
	export default {
		data() {
			return {
				name: "",
				userId:'',
				username: "",
				phone: "",
				img: "",
				imagesList: [], //图片数组
			}
		},
		onShow() {
			let user =  uni.getStorageSync("user")
			this.userId = user.id
			this.name = user.name
			this.phone = user.phone
			this.img = user.avatar
		},
		methods: {
			// 获取图片
			userimgclick() {
				uni.$emit("uploadimg", false)
				var urlt
				var that = this
				var promise = new Promise(function(resolve, reject) {
					if (that.imagesList.length <= 1) {
						uni.chooseImage({
							count: 1, // 默认最多一次选择8张图
							sourceType: ['album', 'camera'], //图片来源
							success: res => {
								// 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
								var tempFilePaths = res.tempFilePaths;
								//支持多图上传
								var falge = false //是否有不合法数据格式标记
								for (var i = 0; i < res.tempFilePaths.length; i++) {
									//对上传的图片校验
									var str = tempFilePaths[i].slice(tempFilePaths[i].length - 3,
										tempFilePaths[i].length)
									var strhz = ["jpg", "png", "gif"]
									var T = strhz.indexOf(str)
									if (T == -1) {
										falge = true
										continue
									}
									if (that.imagesList.length <= 1) {
										//显示消息提示框
										uni.showLoading({
											mask: true
										})
										uni.getFileSystemManager().readFile({
										  filePath: tempFilePaths[0],
										  encoding: 'base64',
										  success: r => {
											  that.imagesList.push("data:image/jpeg;base64,"+r.data)
										  },
										  fail: (errr) => {
										    uni.hideLoading()
										  }
										})
									} else {
										uni.$u.toast("头像只能上传1张！")
										break
									}
								}
								setTimeout(function () {
									uni.hideLoading();
									if(falge){
										uni.$u.toast("获取成功，以过滤格式不正确图片！")
									}else{
										console.log(that.imagesList[0])
										that.img = that.imagesList[0]
										uni.$u.toast("图片获取成功！")
									}
								}, 500)
								
								resolve()
							}
						})
					} else {
						uni.$u.toast("头像只能上传1张！")
					}
				})
				
			},
			//修改用户信息
			update() {
				
				//发送请求
				uni.request({
					url: http + "/wx/updateUserinfo",
					method: "PUT",
					data: {
						id : this.userId,
						name:this.name,
						phone: this.phone,
						avatar : this.img
					},
					complete: (res) => {
						uni.$u.toast("修改成功")
						uni.setStorage({
							key: 'user',
							data: res.data.data.row,
							success: (res) => {}
						})
					}
				})
			},
			
		}
	}
</script>

<style scoped>
	@import url("../static/css/componentsCss/userSet/userSetindex.css");
</style>
