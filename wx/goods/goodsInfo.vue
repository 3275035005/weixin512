<template>
	<view id="body">
		<view class="navimg">
			<!-- 轮播图 -->
			<view class="u-demo-block">
				<u-swiper
					:list="goodsinfo.imageLists"
					previousMargin="30"
					nextMargin="30"
					circular
					autoplay="true"
					interval=4000
					radius="5"
					bgColor="#ffffff"
				></u-swiper>
			</view>
		</view>
		<view class="order-price-sc">
			<view class="left-price">
				<text>￥</text>
				<text style="line-height: 56rpx;">{{goodsinfo.money}}</text>
			</view>
			<view class="right-sc" @click="collectClick()">
				<u-icon :name="goodsinfo.goodsCollectId == null ? 'star':'star-fill'" color="#ffffff" size="28"></u-icon>
			</view>
		</view>
		<view class="order-conent">
			<view class="title">
				<text>商品名称:</text>
				<text style="margin-left: 24rpx;">{{goodsinfo.title}}</text>
			</view>
			<view class="content">
				<text>商品描述:</text>
				<text style="margin-left: 24rpx;">{{goodsinfo.content}}</text>
			</view>
			<view class="xt">
				<u-line color="#acacac"></u-line>
			</view>
			<view class="id">
				<text>商品编号:</text>
				<text style="margin-left: 24rpx;">{{goodsinfo.id}}</text>
			</view>
			<view class="xt">
				<u-line color="#acacac"></u-line>
			</view>
			<view class="type">
				<text>商品分类:</text>
				<text style="margin-left: 24rpx;">{{ordertype}}</text>
			</view>
			<view class="xt">
				<u-line color="#acacac"></u-line>
			</view>
			<view class="oldnew">
				<text>新旧程度:</text>
				<text style="margin-left: 24rpx;">{{goodsinfo.degree}}</text>
			</view>
			<view class="xt">
				<u-line color="#acacac"></u-line>
			</view>
			<view class="sscolor">
				<text>颜色:</text>
				<text style="margin-left: 24rpx;">{{goodsinfo.color}}</text>
			</view>
			<view class="xt">
				<u-line color="#acacac"></u-line>
			</view>
			<view class="issusername">
				<text>卖家名称:</text>
				<text style="margin-left: 24rpx;">{{goodsinfo.userName}}</text>
			</view>
			<view class="xt"> 
				<u-line color="#acacac"></u-line>
			</view>
		</view>
		<view class="cart">
			<!-- 立即购买 -->
			<view class="ljfk" style="float: left;">
				<text @click="sendGoods">立即购买</text>
			</view>
			<view class="ljfk" style="float: right;">
				<text @click="contact">联系卖家</text>
			</view>
		</view>
	</view>
</template>

<script>
	import {http} from "../static/service/requestAddress/rtas.js"
	export default {
		data() {
			return {
				//商品分类
				type:[
					{
						name:"电子产品",
					},
					{
						name:"生活用品",
					},
					{
						name:"学习用品",
					},
					{
						name:"交通工具",
					},
					{
						name:"其他",
					},
				],
				goodsinfo:{},
				goodsId:'',
				userId:'',
				ordertype:''
			}
		},
		onLoad(options){ //页面接收信息
			console.log("接收的页面信息是:"+options.id)
			this.goodsId = options.id;
			let userInfo = uni.getStorageSync('user');
			this.userId = userInfo.id;
			this.getGoodsInfo();
		},
		methods:{
			// 查询商品详情
			getGoodsInfo(){
				uni.request({
					url: http + "/wx/getGoodsById/" + this.goodsId+"/"+this.userId,
					method: "GET",
					complete: (res) => {
						let data = res.data.data.row;
						if(data != null){
							this.ordertype = this.type[parseInt(data.goodsType) - 1].name
							this.goodsinfo = data;
						}
						
						
					}
				})
			},
			sendGoods(){
				uni.request({
					url: http + "/wx/goodsOrder",
					method: "POST",
					data: {
						id: this.goodsId,
						getUserId: this.userId
					},
					complete: (res) => {
						uni.$u.toast(res.data.message)
						setTimeout(function() {
							uni.navigateBack({
								delta: 1
							})
						}, 2000);
					
					}
				})
			},
			// 收藏和取消收藏
			collectClick(){
				uni.request({
					url: http+"/wx/goodsCollect",
					method:"POST",
					data:{
						userId: this.userId,
						goodsId:this.goodsId
					},
					complete:(res) =>{
						uni.$u.toast(res.data.message)
						if(res.data.data== null){ // 取消收藏
							this.goodsinfo.goodsCollectId = null
						}else{ // 收藏
							this.goodsinfo.goodsCollectId = res.data.data.data
						}
					}
				})
			},
			//联系商家
			contact(){
				if(this.userId != this.goodsinfo.userId){
					
					let param = {
						id: this.goodsinfo.userId,
						name: this.goodsinfo.userName,
						avatar: this.goodsinfo.avatar
					}
					uni.navigateTo({
						url:"/see/chatpage?params="+encodeURIComponent(JSON.stringify(param))
					})
				}else{
					uni.$u.toast("您不能和自己聊天")
				}
			},
	
		
		}
	}
</script> 

<style scoped>
	@import url("../static/css/componentsCss/tzmarketindex/tzmarketInfo.css");
</style>