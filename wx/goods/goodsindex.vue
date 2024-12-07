<!-- 跳蚤市场主页 -->
<template>
	<view id="body" @touchstart="start" @touchend="end">
		<!-- 搜索 -->
		<view class="search"> 
			<u-search placeholder="请输入搜索内容" v-model="keyword" :showAction="false" @search="search"></u-search>
		</view>
		<!-- 导航条 -->
		<view class="nav">
			<!-- 类型详细分类 -->
			<view class="type">
				<u-tabs :list="type" @click="fl" lineColor="#ff5500":inactiveStyle="{fontSize:'28rpx',}" :activeStyle="{fontSize:'28rpx'}"></u-tabs>
			</view>
		</view>
		<!-- 订单 -->
		<view class="orderlist">
			<view class="order-card" v-for="(item,index) in orderList" :key="index" @click="orderleftclick(item.id)">
				<!-- 订单图片 -->
				<view class="orderimg">
					<image :src="item.imageLists[0]" class="img"></image>
				</view>
				<view class="title">
					<text>{{item.title}}</text>
				</view>
				<view class="time">
					<text>{{item.createTime}}</text>
				</view>
				<view class="user-name">
					<text>{{item.userName}}</text>
				</view>
				<view class="price">
					<text>￥</text>
					<text style="font-size: 16px;font-weight: 600;">{{item.money}}</text>
				</view>
			</view>
		</view>
		
		<!-- 右侧发布标签 -->
		<view class="tb" v-show="tb"></view>
		<view class="bottom" :class="flag ? 'left1':'right1'"> 
			<text class="t-icon t-icon-zengjia1" style="width: 96rpx;height: 96rpx;" @click="addorder"></text>
		</view>
	</view>
</template>

<script>
	import {http} from "../static/service/requestAddress/rtas.js"
	export default {
		data() {
			return {
				keyword:"",
				goodsType:'',
				type:[
					{
							name: "全部产品",
							id:''
					},
					{
						name:"电子产品",
						id:'1'
					},
					{
						name:"生活用品",
						id:'2'
					},
					{
						name:"学习用品",
						id:'3'
					},
					{
						name:"其他",
						id:'4'
					},
				],
				orderList:[],//订单列表
				clientX:'',
				flag:false, 
				tb:true,
				status:"nomore",
				line:true,
				nomoreText:"没有更多了",
				page:0,//分页值
				typenum:0,//分类类型值
				keyword:"",//搜索内容
			}
		},
		onShow() {
			this.getGoods();
		},
		methods: {
			// 查询数据
			getGoods(){
				let that = this;
				//获取数据
				uni.request({
					url: http + "/wx/getGoods?goodsType="+this.goodsType+"&title="+this.keyword,  
					method:"GET",
					complete:(res) =>{
						let data = res.data;
						if(data.code == 20000){
							that.orderList = data.data.list							
						}else{
							uni.$u.toast("获取数据失败!")
						}
					}
				})
			},
			//搜索功能
			search() {
				if (this.keyword == "") {
					uni.$u.toast("搜索内容不能为空")
					return
				}
				this.getGoods();
			},
			
			fl(item) { //订单类型点击按钮（电子产品。。。）
				this.goodsType = item.id;
				this.getGoods();
			},
			
			
			// 增加订单
			addorder() {
				uni.navigateTo({
					url: "/goods/goodsaddorder"
				})
			},
			
			//请求
			orderleftclick(id) {
				uni.navigateTo({
					url: "/goods/goodsInfo?id="+id
				})
			},
			start(e) {
				this.clientX = e.changedTouches[0].clientX;
			},
			end(e) {
				const T = e.changedTouches[0].clientX - this.clientX
				if (T < 0) {
					this.tb = false
					this.flag = true
				}
				if (T > 0) {
					this.flag = false
					setTimeout(() => {
						this.tb = true
					}, 100)
				}
			}
		}
	}
</script> 

<style>
	@import url("../static/css/componentsCss/tzmarketindex/tzmarketindex.css");
	.left1{
		position: fixed;
		right: 8rpx; 
		bottom: 16%; 
		transition: all 0.5s; 
	}
	.right1{
		position: fixed;
		right: -96rpx;
		bottom: 16%;
		transition: all 0.5s; 
		opacity: 0;
	}
</style>