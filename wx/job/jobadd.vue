<!-- 增加帖子页面 -->
<template>
	<view id="body">
		<!-- 帖子内容 -->
		<view class="content-input">
			<u--textarea v-model="content" placeholder="填写兼职内容..."  count="true"  maxlength=500 ></u--textarea>
		</view>
		<!-- 发布按钮 -->
		<view class="fbbutton" @click="fbclick">
			<text class="fabu">发 布</text>
		</view>
		
	</view>
</template>

<script>
	import {http} from "../static/service/requestAddress/rtas.js"
	export default {
		data() {
			return {
				content:"",
				userId:''
			}
		},
		onLoad(){
			//获取用户信息
			let userInfo = uni.getStorageSync('user');
			this.userId = userInfo.id
		},
		methods:{
			

			//发布按钮
			async fbclick(){
				if(this.content == '' || this.content == undefined || this.content == null){
					uni.$u.toast("请填写兼职内容！");
					return;
				}
				uni.showLoading({
					title:"正在发布中",
					mask: true
				})
			
				uni.request({
					url: http+"/wx/sendJob",
					method:"POST",
					data:{
						content: this.content,
						userId: this.userId
					},
					complete:(res) =>{
						if(res.data.code == 20000){
							uni.$u.toast("发布兼职成功！")
							setTimeout(function() {
								uni.hideLoading()
								uni.reLaunch({
									url:"/job/index"
								})
							}, 1500);
							
						}else{
							uni.$u.toast("发布兼职失败！")
							uni.hideLoading()
						}
					}
				})
			}
		}
	}
</script> 

<style scoped>
	@import url("@/static/css/componentsCss/forum/forumadd.css");
</style>