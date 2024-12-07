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
		</view>
		<view class="order">
			<!-- 左侧内容 -->
			<view class="order-card-left">
				<view class="order-card" v-for="(item,index) in activityList" :key="index"
					@click="infoClick(item.id)">
					<view class="orderimg">
						<image :src="http+item.image" class="img"></image>
					</view>
					<view class="order-conent">
						<view class="title">
							<text>{{item.title}}</text>
						</view>
					</view>
				</view>
			</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {http} from '../static/service/requestAddress/rtas.js'
	export default {
		data() {
			return {
				keyword: "", // 搜索内容
				indexType: '',	
				type: [],
				clientX: '',
				flag: false,
				tb: true,
				http: http,
				activityList: [],
				classifiedId:'-1',
			}
		},
	
		onShow() {
			this.getClassifiedAll();
			this.getActivityList();
		},
		
		methods: {
			// 查询分类
			getClassifiedAll(){
				let that = this;
				//获取数据
				uni.request({
					url: http + "/wx/getClassifiedAll",  
					method:"GET",
					complete:(res) =>{
						that.type =  [{id:'-1', name:'全部'}, ...res.data.data.list]
					}
				})
			},
			// 查询数据
			getActivityList(){
				let that = this;
				//获取数据
				uni.request({
					url: http + "/wx/getActivityListByClassifiedId?id="+this.classifiedId+"&title="+this.keyword,  
					method:"GET",
					complete:(res) =>{
						that.activityList = res.data.data.list
					}
				})
			},
			//搜索功能
			search() {
				if (this.keyword == "") {
					uni.$u.toast("搜索内容不能为空")
					return
				}
				this.getActivityList();
			},
			
			// 类型切换
			fl(item) {
				this.classifiedId = item.id;
				this.getActivityList();
			},
			
			
			
			// 跳转详情
			infoClick(index) {
				uni.navigateTo({
					url: "/activity/index_info?id=" + index
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
	@import url("index.css");

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