<!-- 失物招领页面 -->
<template>
	<view id="body"  @touchstart="start" @touchend="end">
		<!-- 搜索 -->
		<view class="search">
			<u-search placeholder="请输入搜索内容" v-model="keyword" :showAction="false" @search="search"></u-search>
		</view>
		<!-- 导航条 -->
		<view class="nav">
			<!-- 类型详细分类 -->
			<view class="type">
				<u-tabs :list="type" @click="fl" lineColor="#00d4d4" :inactiveStyle="{fontSize:'28rpx',}"
					:activeStyle="{fontSize:'28rpx'}"></u-tabs>
			</view>
			<!-- 订单所属类型 -->
			<view class="ordertype">
				<text class="left" @click="btchlik" :class="ordertype == 1 ? 'leftbk':'rightbk'">失物招领</text>
				<text class="right" @click="btchlik" :class="ordertype  != 1 ? 'leftbk':'rightbk'">寻物启事</text>
			</view>
		</view>
		<!-- 订单列表 -->
		<view class="order">
			<!-- 左侧内容 -->
			<view class="order-card-left">
				<view class="order-card" v-for="(item,index) in orderListleft" :key="index"
					@click="orderleftclick(item.id)">
					<view class="orderimg" v-if="item.imageLists != 0">
						<image :src="item.imageLists[0]" class="img"></image>
					</view>
					<view class="order-conent">
						<view class="title">
							<text>{{item.title}}</text>
						</view>
						<view class="type">
							<text>类型:</text>
							<text>{{type[item.goodsType].name}}</text>
						</view>
					</view>
					<!-- 发布订单用户信息 -->
					<view class="order-user">
						<view class="username">
							<text>{{item.userName}}</text>
						</view>
						<view class="date">
							<text>{{item.createTime}}</text>
						</view>
						<view class="userimg">
							<u-avatar :src="item.avatar" size="92rpx"></u-avatar>
						</view>
					</view>
					<!-- 认领按钮 -->
					<view class="order-button">
						<text>认领</text>
					</view>
				</view>
			</view>
			<!-- 右侧列表 -->
			<view class="order-card-right">
				<view class="order-card" v-for="(item,index) in orderListright" :key="index"
					@click="orderleftclick(item.id)">
					<view class="orderimg" v-if="item.imageLists != 0">
						<image :src="item.imageLists[0]" class="img" v-if="item.imageLists != 0"></image>
					</view>
					<view class="order-conent">
						<view class="title">
							<text>{{item.title}}</text>
						</view>
						<view class="type">
							<text>类型:</text>
							<text>{{type[item.goodsType].name}}</text>
						</view>
					</view>
					<!-- 发布订单用户信息 -->
					<view class="order-user">
						<view class="username">
							<text>{{item.userName}}</text>
						</view>
						<view class="date">
							<text>{{item.createTime}}</text>
						</view>
						<view class="userimg">
							<u-avatar :src="item.avatar" size="92rpx"></u-avatar>
						</view>
					</view>
					<!-- 规划按钮 -->
					<view class="order-button">
						<text>归还</text>
					</view>
				</view>
			</view>
		</view>

		<!-- 右侧发布标签 -->
		<view class="tb" v-show="tb"></view>
		<view class="bottom" :class="flag ? 'left1':'right1'">
			<text class="t-icon t-icon-zengjia" style="width: 96rpx;height: 96rpx;" @click="addorder"></text>
		</view>
	</view>
</template>

<script>
	import {http} from '../static/service/requestAddress/rtas.js'
	export default {
		data() {
			return {
				keyword: "", // 搜索内容
				ordertype: 1,
				goodsType: '',// 物品类型(1电子产品 2生活用品 3学习用品 4其它)
				
				oldList: [],
				
				type: [
					{
							name: "全部产品",
							id:''
					},
					{
						name: "电子产品",
						id:'1'
					},
					{
						name: "生活用品",
						id:'2'
					},
					{
						name: "学习用品",
						id:'3'
					},
					{
						name: "其他",
						id:'4'
					},
				],
				clientX: '',
				flag: false,
				tb: true,
				orderListleft: [],
				orderListright: [],
				status: "loading",
				line: true,
				nomoreText: "没有更多了",
				num: 0, //切换失物招领内容对应的下标 
				typenum: 0, //切换订单类型下标
				ss: false, //是否是搜索标记
				ssnum: 0, //搜索分页下标
				sskey: "", //上一次搜索的key
			}
		},
	
		onShow() {
			
			this.getLostFound();
		},
		
		methods: {
			// 查询数据
			getLostFound(){
				let that = this;
				//获取数据
				uni.request({
					url: http + "/wx/getLostFound?type="+this.ordertype+"&goodsType="+this.goodsType+"&title="+this.keyword,  
					method:"GET",
					complete:(res) =>{
						let data = res.data;
						if(data.code == 20000){
							that.orderListleft = [];
							that.orderListright = [];
							let datalist = data.data.list
							for(var i = 0; i < datalist.length;i++){
								if(this.ordertype===1){
									datalist[i].createTime = (datalist[i].createTime).substring(0,datalist[i].createTime.lastIndexOf(":"))
									if(datalist[i].hideName == '1'){
										datalist[i].userName= '匿名'
										datalist[i].avatar= ''
									}
									that.orderListleft.push(datalist[i])
								}else{
									datalist[i].createTime = (datalist[i].createTime).substring(0,datalist[i].createTime.lastIndexOf(":"))
									if(datalist[i].hideName == '1'){
										datalist[i].userName= '匿名'
										datalist[i].avatar= ''
									}
									that.orderListright.push(datalist[i])
								}
							}
							console.log(that.orderListleft);
							console.log(that.orderListright);
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
				this.getLostFound();
			},
			//切换失物招领 和 寻物启事
			btchlik() {
				if (this.ordertype === 1) {
					this.ordertype = 2
				} else {
					this.ordertype = 1
				}
				this.getLostFound();
			},
			
			fl(item) { //订单类型点击按钮（电子产品。。。）
				this.goodsType = item.id;
				this.getLostFound();
			},
			
			
			
			// 增加订单
			addorder() {
				uni.navigateTo({
					url: "/shiwuzhaoling/swzlAddOrder"
				})
			},
			
			//请求
			orderleftclick(id) {
				uni.navigateTo({
					url: "/shiwuzhaoling/swzlOrderInfo?id="+id
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

<style scoped>
	@import url("../static/css/componentsCss/shiwuzhaoling/swzlindex.css");

	.left1 {
		position: fixed;
		right: 8rpx;
		bottom: 16%;
		transition: all 0.5s;
	}

	.right1 {
		position: fixed;
		right: -96rpx;
		bottom: 16%;
		transition: all 0.5s;
		opacity: 0;
	}
</style>