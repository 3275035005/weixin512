<template>
	<view>

		<!-- logo -->
		<view class="login_logo flex center">
			<view style="font-size: 50rpx;">校园墙注册</view>
		</view>
		<!-- logo -->

		<!-- form -->
		<view class="login_form flex">
			<view class="login_box">

				<!-- item -->
				<view class="item flex radius">
					<view class="item_left flex">
						<image class="item_icon" src="http://go10010.cn/01_dw_crm/app/static/icon_login_01.svg"></image>
					</view>
					<input class="item_input" v-model="form.username" type="text" placeholder="请输入账号"
						placeholder-class="placeholder" />
				</view>
				<!-- item -->

				<!-- item -->
				<view class="item flex radius">
					<view class="item_left flex">
						<image class="item_icon" src="http://go10010.cn/01_dw_crm/app/static/icon_login_02.svg"></image>
					</view>
					<input v-model="form.password" class="item_input" type="password" placeholder="请输入密码"
						placeholder-class="placeholder" />
				</view>
				<!-- item -->
				<!-- item -->
				<view class="item flex radius">
					<view class="item_left flex">
						<image class="item_icon" src="http://go10010.cn/01_dw_crm/app/static/icon_login_02.svg"></image>
					</view>
					<input  v-model="form.phone" class="item_input" type="text" placeholder="请输入手机号"
						placeholder-class="placeholder" />
				</view>
				<!-- item -->
				<!-- item -->
				<view class="item flex radius">
					<view class="item_left flex">
						<image class="item_icon" src="http://go10010.cn/01_dw_crm/app/static/icon_login_02.svg"></image>
					</view>
					<input  v-model="form.name" class="item_input" type="text" placeholder="请输入昵称"
						placeholder-class="placeholder" />
				</view>
				<!-- item -->
				<!-- button -->
				<view class="login_button">
					<button class="button" @click="sumbit">注册</button>
				</view>
				<!-- button -->

			</view>
		</view>
		<!-- form -->

	</view>
</template>

<script>
	import {http} from '../static/service/requestAddress/rtas.js'
	export default {
		data() {
			return {

				// login
				agree_checked: true,

				// login
				form: {
					username: "",
					password: "",
					phone: "",
					name: ""
				},




			}
		},
		methods: {

			// 提交
			sumbit() {

				if (this.form.username == "") {
					uni.showToast({
					    title: '请输入账号',
					    icon: 'none',
					    duration: 2000
					})
					return false
				}
				
				if (this.form.password == "") {
					uni.showToast({
					    title: '请输入密码',
					    icon: 'none',
					    duration: 2000
					})
					return false
				}
				if (this.form.phone == "") {
					uni.showToast({
					    title: '请输入手机号',
					    icon: 'none',
					    duration: 2000
					})
					return false
				}
				if (this.form.name == "") {
					uni.showToast({
					    title: '请输入昵称',
					    icon: 'none',
					    duration: 2000
					})
					return false
				}
				
				uni.request({
				    url: http +`/wx/register`,
					method:"POST",
					data:this.form,
				    header: {
				       'content-type': 'application/json'
				    },
				    success: (res) => {
						if(res.data.success){
							uni.showToast({
							    title: '注册成功',
							    icon: 'none',
							    duration: 2000
							})
							setTimeout(function() {
								uni.reLaunch({
									url: '/login/login'
								})
							
							}, 2000);
							
							
						}else{
							uni.showToast({
							    title: res.data.message,
							    icon: 'none',
							    duration: 2000
							})
						}
						
				    }
				});

			}

		}
	}
</script>

<style>
	page{background: url(/static/img/logo_back.png) no-repeat center top; background-size: 100%;}
	
page{line-height: 1;}
.flex{display: flex; align-items: center; flex-wrap: wrap;}
.radius{border-radius: 20rpx;}
.button{padding: 0; background: none;}
.button::after{border: none;}
.box{padding: 0 26rpx;}
.center{justify-content: center;}

	/* logo */
	.login_logo{height: 40vh;}
	.login_logo__pic{height: 380rpx;}
	
	
	/* box */
	.login_box{width: 100%;}
	
	
	/* form */
	.login_form{padding: 0 70rpx; align-items: center; height: 40vh;}
	.login_form .item{background: #F5F9FF; height: 100rpx; margin-bottom: 32rpx; font-size: 28rpx; position: relative;}
	.login_form .item_icon{width: 40rpx; height: 40rpx; margin: 0 20rpx;}
	.login_form .item_input{font-family:'Din'; font-size: 32rpx; }
	.login_form .item_code{color: #666; position: absolute; right: 26rpx; top: 0; height: 100rpx; line-height: 100rpx;}
	
	
	/* button */
	.login_button{width: 400rpx; margin: 0 auto; background: #6B5DFF; border-radius: 100rpx; height: 100rpx; margin-top: 60rpx;}
	.login_button button{color: #fff; font-size: 32rpx; height: 100rpx; line-height: 100rpx;}
	
	
	/* agree */
	.login_agree{justify-content: center; height: 180rpx; position: fixed; bottom: 0; left: 0; width: 100%;}
	.login_agree .agree_icon{background: url(http://go10010.cn/01_cloud_honor/uniapp_static/icon_checked.svg) no-repeat; width: 28rpx; height: 28rpx; background-size: cover;}
	.login_agree .agree_icon.active{background: url(http://go10010.cn/01_cloud_honor/uniapp_static/icon_checked_active.svg) no-repeat; width: 28rpx; height: 28rpx; background-size: cover;}
	.login_agree .agree_text{font-size: 28rpx; color: #BCC3D0; margin-left: 16rpx;}
	.login_agree .agree_text text{color: #6B5DFF;}
	
</style>