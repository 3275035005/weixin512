<template>
	<view id="body">
		<view class="nav">
			<u-subsection :list="list" mode="button" :current="current" @change="change"></u-subsection>
		</view>
		<!-- 订单分类 -->
		<view class="top">
			<view class="top-left">
				 <u-tabs :list="classify" @click="fl()"></u-tabs>
			</view>
		</view>
		<!-- 订单页面渲染 -->
		 <view id="order">
		 	<view class="order-card" v-for="(item,index) in orderList" :key="item.id" @click="ordeclick(index)">
				<!-- 不是助农服务订单的情况 -->
			<view>
		 		<view class="order-top">
		 			<text class="ordertitle">{{item.title}}</text>
		 			<text class="ordertype">{{ordertypeidtext}}</text>
		 			<text class="orderprice">{{item.price}}元</text>
		 			<view class="line">
		 				<u-line color="#7d7e80"></u-line>
		 			</view>
		 		</view>
		 		<view class="order-body">
		 			<view class="userimg">
		 				<u-avatar :src="item.issueUserImg" size="100rpx"></u-avatar>
		 			</view>
		 			<text class="ordername">{{item.issueUserName}}</text>
		 			<text class="ordercontent">{{item.content}}</text>
		 			<text class="date">发布时间:     {{item.createTime}}</text>
					<view v-if="current == 0">
						<!-- 不是跳蚤，助农订单 -->
						<view v-if="ordertype < 4">
							<!-- 当state为1时 -->	
							<text class="center" v-if="item.state == 1" @click.stop="cancelorder(index)" style="background-color:#ff5500;">取消订单</text>
							<text class="left" v-if="item.state == 1" style="background-color: #00d4d4;">已付款</text>
							<text class="right" @click.stop="orderweight(index)"  v-if="item.state == 1" style="background-color:#3c9cff;">增加权重</text>
							<text class="right" v-if="item.state == 2" style="background-color: #00d4d4;">已被接单</text>
							<text class="center" v-if="item.state == 3" style="background-color:#00d4d4;">订单已完成</text>
							<text class="right" v-if="item.state == 3" @click.stop="fulfilorder(index)" style="background-color: #3c9cff;">确认完结</text>
							<text class="right" v-if="item.state == 4" style="background-color:#3c9cff;">订单已完结</text>
							<text class="right" v-if="item.state == 5"  style="background-color:#00d4d4;">以退款</text> 
						</view>
						<view>
							<text class="right"  v-if="item.state == -1"  style="background-color:#3c9cff;">已下架</text>
							<text class="right" @click.stop="cancelorder2(index)"  v-if="item.state == 0"  style="background-color:#ff5500;">下架</text>
							<text class="left" v-if="item.state == 0" style="background-color:#00d4d4;">已上架</text>
							<text class="center" @click.stop="orderweight(index)"  v-if="item.state == 0" style="background-color:#3c9cff;">增加权重</text>
							<text class="right" v-if="item.state == 1" style="background-color:#00d4d4;">买家已付款</text>
							<text class="right" v-if="item.state == 3" style="background-color:#00d4d4;">订单已完结</text>
						</view>
						<view v-if="ordertype == 5">
							<text class="right"  v-if="item.state == -1"  style="background-color:#3c9cff;">已下架</text>
							<text class="right" @click.stop="cancelorder2(index)"  v-if="item.state == 1"  style="background-color:#ff5500;">下架</text>
							<text class="left" v-if="item.state == 1" style="background-color:#00d4d4;">已上架</text>
							<text class="center" @click.stop="orderweight(index)"  v-if="item.state == 1" style="background-color:#3c9cff;">增加权重</text>
						</view>
		 		</view>
				<view v-if="current == 1">
					<view v-if="ordertype < 4">
						<text class="left" v-if="item.state == 2"  style="background-color:#00d4d4;">已接单</text>  
						<text class="center" v-if="item.state == 2" style="background-color: #ff5500;" @click.stop="cancelorder2(index)">取消接单</text>  
						<text class="right" v-if="item.state == 2" @click.stop="belonguserOvserOrder(index)" style="background-color:#3c9cff;">完成订单</text>
						<text class="right" v-if="item.state == 3" style="background-color:#00d4d4;">以完成订单</text>
						<text class="right" v-if="item.state == 4" style="background-color:#00d4d4;">订单以完结</text>
						<text class="right" v-if="item.state == 5" >以退款</text> 
					</view>
					<view v-if="ordertype  == 4">
						<text class="right" v-if="item.state == -1"  style="background-color:#00d4d4;">商品已下架</text>
						<text class="left" v-if="item.state == 1"style="background-color:#00d4d4;">已付款</text>
						<text class="center" v-if="item.state == 1" style="background-color:#ff5500;" @click.stop="cancelorder(index)">取消订单</text>
						<text class="right" v-if="item.state == 1" @click.stop="belonguserOvserOrder(index)" style="background-color:#3c9cff;">完成订单</text>
						<text class="right" v-if="item.state == 3"style="background-color:#00d4d4;" >订单已完成</text>
					</view>
					<view v-if="ordertype  == 5">
						<text class="center" v-if="item.state == 0"  style="background-color:#00d4d4;">未付款</text>
						<text class="right" v-if="item.state == 0"  style="background-color:#3c9cff;">立即付款</text>
						<text class="left" v-if="item.state == 1"style="background-color:#00d4d4;">已付款</text>
						<text class="center" v-if="item.state == 1" style="background-color:#ff5500;" @click.stop="cancelorder(index)">取消订单</text>
						<text class="right" v-if="item.state == 1" @click.stop="belonguserOvserOrder(index)" style="background-color:#3c9cff;">完成订单</text>
						<text class="right" v-if="item.state == 3"style="background-color:#00d4d4;" >订单已完成</text>
					</view>
				</view>
		 	</view>
			</view>
			<!-- 是助农服务订单的情况 -->
			<view v-if="ordertype == 5 & current == 1" class="contentorderlist">
				<text class="order-id">订单编号:</text>
				<text class="idtext">{{item.id}}</text>
				<text class="order-id-fz" @click="copy">复制</text> 
				<!-- 订单介绍 -->
				<view style="margin-top: 12rpx;">
					<text class="order-price">订单总价格:</text>
					<text class="order-pricetext" style="color: #ff5500; margin-left: 16rpx;">{{item.price}}元</text>
				</view>
				<view class="orderjiesao">
					<text class="js">订单内容:</text>
				</view>
				<!-- 订单包含商品信息 -->
				<view class="order-card1" >
					<view class="ordercardimg">
						<u-avatar :src="item.contentorder.indeximg" size="120rpx"></u-avatar>
					</view>
					<!-- 订单标题 -->
					<view class="order-title">
						<text class="text">{{item.contentorder.title}}</text>
					</view>
					<!-- 订单介绍 -->
					<view class="order-content">
						<text class="contenttext">{{item.contentorder.content}}</text>
					</view>
					<!-- 订单数量 -->
					<view class="order-num">
						<text class="numtext">数量:   x {{item.contentorder.buynum}}</text>
					</view>
					<!-- 订单价格 -->
					<view class="order-price">
						<text class="pricetext">{{item.contentorder.totalprice}}元</text>
					</view>
					<view class="moertext" v-if="item.orderList.length > 1">...</view>
					<!-- <view class="moerbutton">详细信息</view> -->
				</view>
				<text class="center"  v-if="item.state == 0"  style="background-color:#00d4d4;">未付款</text>
				<text class="right"  v-if="item.state == 0"  style="background-color:#3c9cff;">立即付款</text>
				<text class="left"  v-if="item.state == 1"  style="background-color:#00d4d4;">已付款</text>
				<text class="center"  v-if="item.state == 1"  style="background-color:#ff5500;" @click.stop="cancelorder(index)">取消订单</text>
				<text class="right"  v-if="item.state == 1"  style="background-color:#3c9cff;" @click.stop="belonguserOvserOrder(index)">完成订单</text>
				<text class="right"  v-if="item.state == 3"  style="background-color:#00d4d4;">已完成</text>
				<text class="right"  v-if="item.state == 4"  style="background-color:#ff5500;">问题订单</text>
				<text class="right"  v-if="item.state == 5"  style="background-color:#00d4d4;">已退款</text>
			</view>
			</view>
		 </view>
		 <view class="loading" >
		 	<u-loadmore :status="status" :line="line" :nomoreText="nomoreText" /> 
		 </view>
	</view>
	</view>
</template>

<script>
	import {http} from "../static/service/requestAddress/rtas.js"
	export default {
		data(){
			return{
				list:['我发布的订单/商品','我承接/购买的订单'],
				current:0,
				classify:[
					{
						name: '帮我买',
						url:http+"buyorder/getuserissue/",//用户已发布订单
						URL:http+"buyorder/getissueList/",//用户已承接订单
						url2:http+"buyorder/cancelorder/",//接单用户取消接单
						url3:http+"buyorder/issueoverorder/",//发布用户完成订单
						url4:http+"buyorder/accomplishOrder/" ,//接单用户完成订单
				    }, 
					{
				        name: '帮我做',
						url:http+"workorder/getuserissue/",
						URL:http+"workorder/getissueList/",//用户已承接订单
						url2:http+"workorder/cancelorder/",//接单用户取消接单
						url3:http+"workorder/issueoverorder/",//发布用户完成订单
						url4:http+"workorder/accomplishOrder/" ,//接单用户完成订单
				    },
					{
				        name: '帮我送',
						url:http+"sendorder/getuserissue/",
						URL:http+"sendorder/getissueList/",//用户已承接订单
						url2:http+"sendorder/cancelorder/",//接单用户取消接单
						url3:http+"sendorder/issueoverorder/",//发布用户完成订单
						url4:http+"sendorder/accomplishOrder/" ,//接单用户完成订单
				    }, 
					{
						name:"需求订单",
						url:http+"need/getuserissue/",
						URL:http+"need/getissueList/",//用户已承接订单
						url2:http+"need/cancelorder/",//接单用户取消接单
						url3:http+"need/issueoverorder/",//发布用户完成订单
						url4:http+"need/accomplishOrder/" ,//接单用户完成订单
					},
					{
						name:"跳蚤市场订单",
						url:http+"tzmarket/getuserissue/",//用户发布订单
						URL:http+"tzmarket/getuserbuyorder/",//用户购买订单
						url2:http+"tzmarket/userrefund/",//发布用户取消接单
						url4:http+"tzmarket/issueoverorder/" //购买用户完成订单
					},
					{
						name:"助农服务订单",
						url:http+"nongsuke/getuserissuegoods/",//用户所有订单
						URL:http+"nongsukellid/",//用户购买订单
						url2:http+"nongsuke/userrefund/",//发布用户取消订单
						url4:http+"nongsuke/issueoverorder/" //购买用户完成订单
					},
					],
					status:"nomore",
					nomoreText:"没有更多了",
					line:true,
					ordertypeidtext:"",//订单类型文本
					oldList:[],//接收的请求数据
					orderList:[
						{
							indexImg: 'http://127.0.0.1:9999/files/download?fileName=1710610492502.jpg',
							title:'鞋子',
							createTime:'2024-10-10',
							issueUserName:'小凯',
							likeuser:'10',
							price:'99.99',
							state: 1
						}
					],
					ordertype:0,//订单的类型
			}
		},
		created(){
			//获取登录用户信息
			// if(! uni.getStorageSync("user")){
			// 	uni.$u.toast("登录后才能获取订单信息喔")
			// 	return
			// }else{
			// 	//用户id
			// 	var userid = JSON.parse(uni.getStorageSync("user")).id
			// 	//获取数据
			// 	uni.request({
			// 		url:this.classify[0].url+userid, 
			// 		method:"GET",
			// 		complete:(res) =>{
			// 			if(res.data.code == 20021){
			// 				this.oldList = res.data.date //将获取的数据存入旧数据的集合
			// 				const datalist = res.data.date
			// 				//对数据处理
			// 				for(var i = 0; i < datalist.length;i++){
			// 					if(datalist[i].createTime != ""){
			// 						datalist[i].createTime = (datalist[i].createTime).substring(0,datalist[i].createTime.lastIndexOf(":"))
			// 					}
			// 				}
			// 				this.orderList = datalist
			// 				this.ordertypeidtext = "帮我买"
			// 				this.status = "nomore"
			// 				console.log("数据是："+JSON.stringify(this.orderList))
			// 			}else{
			// 				uni.$u.toast("获取数据失败!")
			// 			}
			// 		}
			// 	})
			// }
		},
		methods: {
			//主分类点击事件
			change(index){
				this.current = index
				if(! uni.getStorageSync("user")){
					uni.$u.toast("登录后才能获取订单信息喔")
				}else{
					//获取承接,购买订单数据
					if(index == 1){
						//用户id
						var userid = JSON.parse(uni.getStorageSync("user")).id
						this.orderList= []
						this.oldList = []
						this.status="loading"
						console.log("请求的地址："+this.classify[this.ordertype].URL+userid)
						//获取数据
						uni.request({
							url:this.classify[this.ordertype].URL+userid,
							method:"GET",
							complete:(res)=>{
								if(res.data.code == 20021){
									if(res.data.date == null){
										this.status = "nomore"
										this.ordertypeidtext = this.classify[this.ordertype].name
										uni.$u.toast("获取数据成功")
										return
									}
									//获取助农服务订单的情况
									if(this.ordertype == 5){
										var contentorder = {}
										 var data = res.data.date
										 for(var i = 0;i<data.length;i++){
											 var T = JSON.parse(data[i].orderlist)
											 contentorder = T[0]
											 data[i].orderList = T
											 data[i].contentorder = contentorder
											 this.orderList.push(data[i])
										 }
										 this.status = "nomore"
										 this.ordertypeidtext = this.classify[this.ordertype].name//订单分类名称
									}else{
										const datalist = res.data.date
										this.oldList = res.data.date //将获取的数据存入旧数据的集合
										for(var i = 0; i < datalist.length;i++){
											datalist[i].createTime = (datalist[i].createTime).substring(0,datalist[i].createTime.lastIndexOf(":"))
										}
										this.orderList = datalist
										this.status = "nomore"
										this.ordertypeidtext = this.classify[this.ordertype].name//订单分类名称
									}
								}else{
									uni.$u.toast("获取数据失败!")
								}
							}
						})
					}else{
						// 获取用户发布,商家订单
						var userid = JSON.parse(uni.getStorageSync("user")).id
						this.orderList= []
						this.status="loading"
						//获取数据
						uni.request({ 
							url:this.classify[this.ordertype].url+userid,
							method:"GET",
							complete:(res) =>{
								if(res.data.code == 20021){
									if(res.data.date == null){
										this.status = "nomore"
										this.ordertypeidtext = this.classify[this.ordertype].name
										uni.$u.toast("获取数据成功")
										return
									}
									this.oldList = res.data.date //将获取的数据存入旧数据的集合
									const datalist = res.data.date
									//对数据处理
									for(var i = 0; i < datalist.length;i++){
										datalist[i].createTime = (datalist[i].createTime).substring(0,datalist[i].createTime.lastIndexOf(":"))
									}
									this.orderList = datalist
									this.status = "nomore"
									this.ordertypeidtext = this.classify[this.ordertype].name
								}else{
									uni.$u.toast("获取数据失败!")
								}
							}
						})
					}
				}
				console.log("点击了:"+this.list[index])
			},
			fl(itme){
				if(! uni.getStorageSync("user")){
					uni.$u.toast("登录后才能获取订单信息喔")
				}else{ 
				if(this.current == 0){
					//用户id
					var userid = JSON.parse(uni.getStorageSync("user")).id
					this.orderList= []
					this.ordertype = itme.index
					this.status="loading"
					//获取数据
					uni.request({
						url:this.classify[itme.index].url+userid,
						method:"GET",
						complete:(res) =>{
							if(res.data.code == 20021){
								if(res.data.date == null){
									this.status = "nomore"
									this.ordertypeidtext = this.classify[this.ordertype].name
									uni.$u.toast("获取数据成功")
									return
								}
								this.oldList = res.data.date //将获取的数据存入旧数据的集合
								const datalist = res.data.date
								//对数据处理
								for(var i = 0; i < datalist.length;i++){
									datalist[i].createTime = (datalist[i].createTime).substring(0,datalist[i].createTime.lastIndexOf(":"))
								}
								this.orderList = datalist
								this.status = "nomore"
								this.ordertypeidtext = this.classify[itme.index].name
							}else{
								uni.$u.toast("获取数据失败!")
							}
						}
					})
			}else{
				//用户id
				var userid = JSON.parse(uni.getStorageSync("user")).id
				this.orderList= []
				this.ordertype = itme.index
				this.status="loading"
				//获取数据
				uni.request({
					url:this.classify[this.ordertype].URL+userid,
					method:"GET",
					complete:(res) =>{
						if(res.data.code == 20021){
							if(res.data.date == null){
								this.status = "nomore"
								this.ordertypeidtext = this.classify[this.ordertype].name
								uni.$u.toast("获取数据成功")
								return
							}
							if(this.ordertype == 5){
								 var data = res.data.date
								 var contentorder = {}
								 for(var i = 0;i<data.length;i++){
									 var T = JSON.parse(data[i].orderlist)
									 contentorder = T[0]
									 data[i].orderList = T
									 data[i].contentorder = contentorder
									 this.orderList.push(data[i])
								 }
								 this.status = "nomore"
								 this.ordertypeidtext = this.classify[this.ordertype].name//订单分类名称
							}else{
								this.oldList = res.data.date //将获取的数据存入旧数据的集合
								const datalist = res.data.date
								//对数据处理
								for(var i = 0; i < datalist.length;i++){
									datalist[i].createTime = (datalist[i].createTime).substring(0,datalist[i].createTime.lastIndexOf(":"))
								}
								this.orderList = datalist
								this.status = "nomore"
								this.ordertypeidtext = this.classify[this.ordertype].name
							}
						}else{
							uni.$u.toast("获取数据失败!")
						}
					}
				})
				}
			}
			},
			//发布用户取消订单，购买用户退款
			cancelorder(index){
				var userid = 0;
				//获取登录用户信息
				if(! uni.getStorageSync("user")){
					uni.$u.toast("登录后才能获取订单信息喔")
					return
				}else{//用户登录后的情况
					userid = JSON.parse(uni.getStorageSync("user"))
					console.log("点击了用户取消订单")
					if(this.orderList[index].state == 2){
						uni.$u.toast('已接单订单不能取消喔')
						return
					}else if(this.orderList[index].state == 5){
						uni.$u.toast('该订单以退过款了喔')
						return
 					}else if(this.orderList[index].state == -1){
						uni.$u.toast("该订单已经取消了喔")
						return
					}else{
						if(this.ordertype < 4){
						//发起退款请求
						uni.request({
							url:http+"wxpay/tk/"+this.orderList[index].id+"/"+(this.ordertype+1),
							method:"POST",
							complete:(res)=>{
								if(res.data.code == 20011){
									this.orderList[index].state = 5
									uni.$u.toast('退款成功，快去查看吧')
								}else{
									uni.$u.toast(res.data.msg)
								}
							}
						})
						}else{
							uni.request({
								url:http+"wxpay/tk/"+this.orderList[index].id+"/"+(this.ordertype+1),
								method:"POST",
								complete:(res)=>{
									if(res.data.code == 20011){
										var a = []
										this.orderList = a
										uni.$u.toast('取消订单成功')
									}else{
										uni.$u.toast(res.data.msg)
									}
								}
							})
						}
					}
				}
			},
			//增加权重
			orderweight(index){
				uni.$u.toast("管理员暂时关闭了功能")
			},
			//接单用户取消接单
			cancelorder2(index){
				var userid = JSON.parse(uni.getStorageSync("user")).id
				if(this.orderList[index].state == 3){
					uni.$u.toast('已完成订单不能取消喔')
				}else if(this.orderList[index].state == 5){
					uni.$u.toast('已退款订单不能取消喔')
				}else{
					uni.request({
						url:this.classify[this.ordertype].url2+this.orderList[index].id+"/"+userid,
						method:"PUT",
						complete:(res)=>{
							if(res.data.code == 20041){
								var datalist = []
								for(var i = 0 ;i<this.orderList.length;i++){
									if(this.orderList[i].id = this.orderList[index].id){
										continue
									}else{ 
										datalist.push(this.orderList[i])
									}
								}
								this.orderList = datalist
								if(this.ordertype < 4){
									uni.$u.toast("取消接单成功")
									this.orderList[index].state = 0
									return
								}
								this.orderList[index].state = 1
								uni.$u.toast("取消订单成功")
							}else{
								uni.$u.toast(res.data.msg)
							}
						}
					})
				}
			},
			// 发单用户完成订单
			fulfilorder(index){
				console.log("发单用户点击了完成订单")
				var user = JSON.parse(uni.getStorageSync("user"))
				uni.request({
					url:this.classify[this.ordertype].url3+this.orderList[index].id+"/"+user.id,
					method:"PUT",
					complete:(res)=>{
						if(res.data.code == 20041){
							this.orderList[index].state = 4
							uni.$u.toast("完成订单成功")
						}else{
							uni.$u.toast(res.data.msg)
						}
					}
				})
			},  
			//接单,购买用户完成订单
			belonguserOvserOrder(index){
				if(this.ordertype < 4){
					uni.request({
						url:this.classify[this.ordertype].url4+this.orderList[index].id,
						method:"PUT",
						complete:(res)=>{
							if(res.data.code == 20041){
								this.orderList[index].state = 3
								uni.$u.toast("订单以成功,等待发单用户确认")
							}else{
								uni.$u.toast(res.data.msg)
							}
						}
					})
				}else{
					uni.request({
						url:this.classify[this.ordertype].url4+this.orderList[index].id+"/"+JSON.parse(uni.getStorageSync("user")).id,
						method:"PUT",
						complete:(res)=>{
							if(res.data.code == 20041){
								this.orderList[index].state = 3
								uni.$u.toast("订单以完结")
							}else{
								uni.$u.toast(res.data.msg)
							}
						}
					})
				}
				
			},
			//订单点击事件
			ordeclick(index){
				console.log("订单信息是："+this.ordertype)
				if(this.ordertype < 4){
					uni.navigateTo({
						url:"/pages/massage/orderDetails?data="+JSON.stringify(this.orderList[index])
					})
					return
				}else if(this.ordertype == 4){
					uni.navigateTo({
						url:"/tzmarket/tzmarketInfo?data="+JSON.stringify(this.orderList[index])
					})
				}else{
					uni.navigateTo({
						url:"/shoppingcart/shoppingAllInfo?data="+JSON.stringify(this.orderList[index])
					})
				}
			}
		}
	
	}
</script>
<style scoped>
	@import url("@/static/css/order.css");
</style>