<!-- 失物招领增加订单 -->
<template>
	<view id="body">
		<view class="order">
			<view class="title">
				<input placeholder="请输入物品标题" v-model="title" />
			</view>
			<!-- 订单所属类型 -->
			<view class="ordertype">
				<text class="left" @click="btchlik" :class="ordertype == 1 ? 'leftbk':'rightbk'">失物招领</text>
				<text class="right"@click="btchlik" :class="ordertype  != 1 ? 'leftbk':'rightbk'" >寻物启事</text>
			</view>
			<!-- 订单类型 -->
			<view class="type">
				<view v-for="(itme,index) in type" :key="index">
					<text :class="index == typeflage ? 'typetextok' : 'typetextno'" @click="typeclick(itme, index)">{{itme.name}}</text>
				</view>
			</view>
			<!-- 订单内容 -->
			<view class="contnt">
				<uni-section class="mb-10" color="#00d4d4" title="描述物品内容" type="line" titleColor="#909399"></uni-section>
				<view class="content-input">
					<u--textarea v-model="content" placeholder="描述物品内容"  count="true"  maxlength=400 ></u--textarea>
				</view>
			</view>
			<!--图片信息-->
			<uni-section class="mb-10" title="图片信息(选填)" type="line" titleColor="#909399"></uni-section>
			<view class="img-input">
				<image :src="item" v-if="item" mode="" class="imglist" v-for="(item,index) in imageLists" :key="item" @tap="ViewImage(index)" @longpress="deleteImg(index)"></image>
				<view class="box">
					<text class="t-icon t-icon-Rrl_s_114" style="width: 80rpx; height: 80rpx;margin-top: 36rpx;" @click="addImages()"></text>
				</view>
			</view>
			<!-- 备注信息 -->
			<uni-section class="mb-10" title="备注信息(选填)" type="line" titleColor="#909399"></uni-section>
			<view class="beizhu-input">
				<u--textarea v-model="remark" placeholder="请输入备注内容"  count="true"  maxlength=100 ></u--textarea>
			</view>
			<!-- 物品当前位置 -->
			<uni-section class="mb-10" title="物品当前位置" type="line" titleColor="#909399" v-if="ordertype"></uni-section>
			<view class="weizhi"  v-if="ordertype">
				<view v-for="(itme,index) in weizhi" :key="index">
					<text :class="index == weizhiflage ? 'weizhiok' : 'weizhino'" @click="weizhiclick(itme, index)">{{itme.name}}</text>
				</view> 
			</view>
			<!-- 价格 -->
			<uni-section class="mb-10" title="是否有偿" type="line" titleColor="#909399"></uni-section>
			<view class="price">
				<view class="anonymous">
					<u-switch v-model="isMoney" activeColor=" #00d4d4" ></u-switch> 
				</view>
				<view class="price-input" v-if="isMoney">
					<u--input 
					placeholder="请输入价格"  
					@input="checknumber"
					type = number
					v-model="price"  
					clearable></u--input> 
				</view>
			<text style="margin-left: 20rpx;color: #00d4d4;position: relative;bottom: 13rpx;" v-if="isMoney">元</text>
			</view>
			<!-- 是否匿名 -->
			<uni-section class="mb-10" title="是否匿名" type="line" titleColor="#909399"></uni-section>
				<view class="niming">
					<u-switch v-model="hideName" activeColor=" #00d4d4" ></u-switch> 
				</view>
			<!-- 发布按钮 -->
			<view class="fabubutton">
				<u-button 
					text="发             布"
					size="normal"
					type="primary"
					color="#00d4d4"
					@click="fbclick"
				></u-button>
			</view>
		</view>
	</view>
</template>
 
<script>
	import {http} from '../static/service/requestAddress/rtas.js'
	export default {
		data() {
			return {
				
				ordertype:1,//订单所属类型样式控制变量
				goodsType:'', //商品类型
				typeflage:0,//商品类型
				type: [{
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
				title:"",//订单标题
				content:"",//订单内容
				imageLists:[],//图片数组
				remark:"",//订单备注
				weizhiflage:0,
				location:'',
				order_index:[],//订单类型下标数组，用于发布封装订单
				
				weizhi:[{
						name: "本地代管",
						id:'1'
					},
					{
						name: "遗失处",
						id:'2'
					},
					{
						name: "其它",
						id:'3'
					},],
				isMoney:false,//是否有偿
				hideName:false,//是否匿名
				
				receiveImgList: "",//上传图片返回的链接
				price:0,//价格
			}
		},
		methods:{
			//订单所属类型
			btchlik(){
				if (this.ordertype === 1) {
					this.ordertype = 2
				} else {
					this.ordertype = 1
				}
			},
			//订单类型
			typeclick(item,index){
				this.typeflage = index;
				this.goodsType = item.id;
			},
			weizhiclick(item, index){
				this.weizhiflage = index,
				this.location = item.id;
			},
			// 获取图片
			addImages() {
				var urlt 
				if(this.imageLists.length < 8){
					uni.chooseImage({
						count: 3, // 默认最多一次选择8张图
						sourceType: ['album', 'camera'], //图片来源
						success: res => {
							// 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
							var tempFilePaths = res.tempFilePaths;
							//支持多图上传
							var falge = false//是否有不合法数据格式标记
							for (var i = 0; i < res.tempFilePaths.length; i++) {
								//对上传的图片校验
								var str = tempFilePaths[i].slice(tempFilePaths[i].length - 3,tempFilePaths[i].length)
								var strhz = ["jpg","png","gif"]
								var T = strhz.indexOf(str)
								if(T == -1){
									falge = true
									continue
								}
								if(this.imageLists.length < 3){
									//显示消息提示框
									uni.showLoading({
										mask: true
									})
									uni.getFileSystemManager().readFile({
									  filePath: tempFilePaths[0],
									  encoding: 'base64',
									  success: r => {
										  this.imageLists.push("data:image/jpeg;base64,"+r.data)
									  },
									  fail: (errr) => {
									    uni.hideLoading()
									  }
									})
								}else{
									uni.$u.toast("图片只能上传3张！")
									break
								}
							}
							setTimeout(function () {
								uni.hideLoading();
								if(falge){
									uni.$u.toast("获取成功，以过滤格式不正确图片！")
								}else{
									uni.$u.toast("图片获取成功！")
								}
							}, 500)
						}
					})
				}else{
					uni.$u.toast("图片只能上传3张！")
				}
			},
			//预览图片
			ViewImage(index) {
				uni.previewImage({
					urls: this.imageLists,
					current: index
				});
			},
			//图片长按删除
			deleteImg(index){
				var list = []
				for(var i = 0;i<this.imageLists.length;i++){
					if(index == i){
						continue
					}else{
						list.push(this.imageLists[i])
					}
				}
				this.imageLists = list
			},
			//价格输入框检查只能为数字
			checknumber(value){
				var price1 = value.toString().match(/^\d+(?:\.\d{0,2})?/) 
					this.$nextTick(() => { 
						this.price = price1 && price1[0]
				})
			},
			
			async fbclick(){
				//1.发布信息检查
				var user = uni.getStorageSync('user')
				if(! user){
					uni.$u.toast("您还未登录，登录才能发布订单")
					return
				}else if (this.title == "" || this.title.length == 0 || this.title.length > 10){
					uni.$u.toast("标题不能为空，并且长度小于10")
					return
				}else if(this.content == "" || this.content.length == 0){
					uni.$u.toast("内容不能为空")
					return
				}else if(this.isMoney & this.price < 1){
					uni.$u.toast("价格最少为1元")
					return
				}else{
					//上传图片
					//显示消息提示框
					uni.showLoading({
						title:"正在创建中",
						mask: true
					})
					//封装请求对象
					var hidename = '0'//匿名
					if(this.hideName == true){
						hidename = '1'
					}
					var paid = '0' //是否有偿
					if(this.isMoney == true){
						paid = '1'
					}
					var postData = {
						type:this.ordertype,//订单类型
						goodsType:this.goodsType,
						title:this.title,//订单标题
						content:this.content,//订单内容
						imageLists:this.imageLists,//订单图片
						state: '1',//订单状态
						remark: this.remark,//订单备注
						money:this.price,//订单价格
						hideName: hidename,//是否匿名
						userId: user.id,//发布用户
						location:this.location,//物品所在位置
						isMoney: paid,//是否有偿
						type: this.ordertype,//订单所属类型
					}
					 uni.request({
						url: http +'/wx/sendLostFound',
						method:"POST",
						data:postData,
						complete:(res) =>{
							uni.hideLoading()
							if(res.data.code === 20000){
								uni.$u.toast("发布成功！")
								uni.redirectTo({ 
									url:"/shiwuzhaoling/swzlOrderInfo?data="+ JSON.stringify(res.data.data.row)
								})
							}else{
								uni.$u.toast("订单创建失败，请重试！")
							}
						}
					})
				}
			},
		}
	}
</script> 

<style scoped>
	@import url("../static/css/componentsCss/shiwuzhaoling/swzlAddOrder.css");
	page {
		background-color: #f0f0f0;
	}
	.leftbk{
		color: #ffffff;
		background-color: #00d4d4;
	}
	.rightbk{
		color: #606266;
		background-color: #f0f0f0;
	}
</style>