<!-- 失物招领详细信息 -->
<template>
	<view id="body">
		<view class="order">
			<!-- 头部样式 -->
			<view class="top">
				<text class="fk">{{flagetext}}</text>
			</view>
			<!-- 订单内容 -->
			<view class="order-conent">
				<view>
					<text class="order-title">物品类型:</text>
					<text class="order-title-text">{{ordertype}}</text>
				</view>
				<view>
					<text class="order-title">物品标题:</text>
					<text class="order-title-text">{{order.title}}</text>
				</view>
				<view>
					<text class="order-conent-ct">物品内容:</text>
					<text class="order-conentct-text">{{order.content}}</text>
				</view>
				<view>
					<text class="order-remarks">物品备注:</text>
					<text class="order-remarks-text">{{order.remarks}}</text>
				</view>
				<!-- 订单信息 -->
				<view>
					<text class="order-user">发布用户:</text>
					<text class="username">{{order.userName}}</text>
				</view>
				<view>
					<text class="order-time">发布时间:</text>
					<text class="createtime">{{order.createTime}}</text>
				</view>
				<view>
					<text class="order-time">物品当前位置:</text>
					<text class="createtime">{{weizitext}}</text>
				</view>
				<view>
					<text class="zf-time">是否有偿:</text>
					<text class="zftype" :style="{color:paidflage?'#3c9cff':'#ff5500'}">{{paidftext}}</text>
				</view>
				<!-- 按钮 -->
				<view style="padding-bottom: 40rpx;" v-if="paidflage">
					<text class="pricetext">支付金额:</text>
					<text class="price">{{order.money}}元</text>
				</view>
				<!-- 图片信息 -->
				<view style="padding-bottom: 20rpx;margin-left: 40rpx;margin-right: 40rpx;">
					<u-divider text="图片信息" textColor="#3c9cff" lineColor="#3c9cff"></u-divider>
				</view>
				<view class="order-imglist">
					<view>
						<image :src="item" mode="" class="imglist" v-for="(item,index) in imagesList" :key="index"
							@tap="ViewImage(index)"></image>
					</view>
				</view>
			</view>
		</view>
		<!-- 付款按钮 -->
		<view class="fabubutton" v-if="order.state == '1'">
			<u-button :text="butText" size="normal" type="primary" @click="fkclick"></u-button>
		</view>
		<view class="fabubutton1">
			<u-button text="返  回  失  物  招  领  中  心" size="normal" type="primary" @click="toswzl"></u-button>
		</view>
	</view>
</template>

<script>
	import {
		http
	} from '../static/service/requestAddress/rtas.js'
	export default {
		data() {
			return {

				type: ["电子产品", "生活用品", "学习用品", "其他"], //物品的分类类型
				ordertype: "", //物品类型
				weizhi: ["本人代管", "遗失处", "其他"],
				weizitext: "", //物品当前所在位置文本
				imagesList: [], //物品图片信息
				flagetext: "认领中",
				paidflage: false, //是否有偿
				paidftext: "否", //是否有偿文本
				butText: '认领',
				order: {},
				userName:'',// 发布人
				id: '',
				userId: '',
			}
		},
		onLoad(options) { //页面接收信息
			this.id = options.id;
			let userInfo = uni.getStorageSync('user');
			this.userId = userInfo.id;
			this.getLostFoundById();
		},
		methods: {

			// 查询详情
			getLostFoundById() {
				uni.request({
					url: http + "/wx/getLostFoundById/" + this.id,
					method: "GET",
					complete: (res) => {
						let data = res.data.data.row;
						if (data != null) {
							this.order = data
							// 修改头部标题
							if (data.type === '1') { // 失物招领
								this.flagetext = data.state == '1' ? '认领中' : '已认领',
								this.butText = '认领'
							} else { // 寻物启示
								this.flagetext = data.state == '1' ? '寻物中' : '寻物成功',
								this.butText = '还物'
							}
							console.log(data)
							this.ordertype = this.type[parseInt(data.goodsType) - 1]
							this.weizitext = this.weizhi[parseInt(data.location) - 1]
							if (data.isMoney == '0') { //是否有偿 否
								this.paidflage = false
								this.paidftext = "否"
							} else { // 是
								this.paidflage = true
								this.paidftext = "是"
							}
							this.imagesList = data.imageLists
							if(data.hideName === '1' ){ // 匿名
								this.userName == '匿名'
							}else{
								this.userName == data.userName
							}
							
						}
					}
				})
			},
			//预览图片
			ViewImage(index) {
				uni.previewImage({
					urls: this.imagesList,
					current: index
				});
			},

			// 认领
			fkclick() {
				uni.showLoading({
					title:"提交中",
					mask: true
				})
				uni.request({
					url: http+"/wx/LostFoundOrder",
					method:"POST",
					data:{
						id: this.order.id,
						getUserId: this.userId
					},
					complete:(res) =>{
						uni.$u.toast(res.data.message)
						setTimeout(function() {
							uni.hideLoading()
							uni.reLaunch({
								url: "/shiwuzhaoling/swzlindex"
							})
						}, 1500);
					}
				})
			},
			//返回按钮
			toswzl() {
				uni.redirectTo({
					url: "/shiwuzhaoling/swzlindex"
				})
			}
		}
	}
</script>

<style>
	@import url("../static/css/componentsCss/shiwuzhaoling/swzlOrderInfo.css");

	page {
		background-color: #f0f0f0;
	}
</style>
