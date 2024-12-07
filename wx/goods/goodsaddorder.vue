<!-- 跳蚤市场增加订单 -->
<template>
	<view id="body">
		<view class="order">
			<view class="title">
				<input placeholder="请输入商品标题" v-model="title" />
			</view>
			<view class="nav">
				<!-- 类型详细分类 -->
				<view class="type">
					<u-tabs :list="type" @click="typeclick" lineWidth="0" :inactiveStyle="inactiveStyle" :activeStyle="activeStyle"></u-tabs>
				</view>
			</view>
			<!-- 订单内容 -->
			<view class="contnt">
				<uni-section class="mb-10" color="#00d4d4" title="商品介绍" type="line" titleColor="#909399"></uni-section>
				<view class="content-input">
					<u--textarea v-model="content" placeholder="商品介绍"  count="true"  maxlength=400 ></u--textarea>
				</view>
			</view>
			<!-- 新旧程度 -->
			<uni-section class="mb-10" color="#00d4d4" title="商品新旧程度" type="line" titleColor="#909399"></uni-section>
			<view class="lodandnew-input">
				<u--textarea v-model="degree" placeholder="商品新旧程度"  count="true"  maxlength=10 ></u--textarea>
			</view>
			<!-- 商品颜色 -->
			<uni-section class="mb-10" color="#00d4d4" title="商品颜色" type="line" titleColor="#909399"></uni-section>
			<view class="color-input">
				<u--textarea v-model="color" placeholder="商品颜色"  count="true"  maxlength=10 ></u--textarea>
			</view>
			<!--图片信息-->
			<uni-section class="mb-10" title="商品图片信息(最多5张)" type="line" titleColor="#909399"></uni-section>
			<view class="img-input">
				<image :src="item" v-if="item" mode="" class="imglist" v-for="(item,index) in imageLists" :key="item" @tap="ViewImage(index)" @longpress="deleteImg(index)"></image>
				<view class="box">
					<text class="t-icon t-icon-Rrl_s_114" style="width: 80rpx; height: 80rpx;margin-top: 36rpx;" @click="addImages()"></text>
				</view>
			</view>
			<!-- 价格 -->
			<uni-section class="mb-10" title="商品价格" type="line" titleColor="#909399"></uni-section>
			<view class="price">
				<view class="price-input">
						<u--input 
						placeholder="请输入价格"  
						@input="checknumber"
						type = number
						v-model="price"  
						clearable></u--input> 
					</view>
				<text style="margin-left: 20rpx;color: #FF5500;position: relative;bottom: 13rpx;">元</text>
			</view>
			<!-- 发布按钮 -->
			<view class="fabubutton">
				<u-button 
					text="发             布"
					size="normal"
					type="primary"
					color="#FF5500"
					@click="fbclick"
				></u-button>
			</view>
		</view>
	</view>
</template>
 
<script>
	import {uploadImage} from '../js_sdk/yushijie-ossutil/ossutil/uploadFile.js'
	import {http} from "../static/service/requestAddress/rtas.js"
	export default {
		data() {
			return {
				title:"",//订单标题
				ordertype:true,//订单所属类型样式控制变量
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
				goodsType: '1',//商品类型
				weizhiflage:0,
				content:"",//订单内容
				norms:"",//商品规格
				lodandnew:"",//商品新旧程度
				color:"",//商品颜色
				degree:"",//商品型号
				order_index:[],//订单类型下标数组，用于发布封装订单
				bezhu:"",//订单备注
				imageLists:[],//图片数组
				receiveImgList: "",//上传图片返回的链接
				price:0,//价格
				inactiveStyle:{ //未选中样式
					color: '#606266',
					backgroundColor: '#f0f0f0',
					fontSize: '14px',
					borderWidth: '2rpx',
					borderRadius: '40rpx',
					padding: '4rpx 8rpx',
					textAlign: 'center'
				},
				activeStyle:{ //选中样式
					color: '#ffffff',
					backgroundColor: '#ff5500',
					fontSize: '14px',
					borderWidth: '2rpx',
					borderRadius: '40rpx',
					padding: '4rpx 8rpx',
					textAlign: 'center'
				}
			}
		},
		methods:{
			//订单类型
			typeclick(item){
				this.goodsType = item.id;
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
				}else if(this.price & this.price < 1){
					uni.$u.toast("价格最少为1元")
					return
				}else if(this.imageLists.length < 1){
					uni.$u.toast("图片不能为空")
					return
				}else{
					//上传图片
					//显示消息提示框
					uni.showLoading({
						title:"正在创建中",
						mask: true
					})
			var postData = {
				goodsType:this.goodsType,
				title: this.title,//订单标题
				content: this.content,//订单内容
				imageLists:this.imageLists,//订单图片
				state: '1',//订单状态
				remark: this.remark,//订单备注
				money: this.price,//订单价格
				userId: user.id,//发布用户
				degree:this.degree,
				color:this.color,
				type: this.ordertype,//订单所属类型
			}
			uni.request({
				url: http +'/wx/sendGoods',
				method:"POST",
				data:postData,
				complete:(res) =>{
					uni.hideLoading()
					if(res.data.code === 20000){
						uni.$u.toast("发布成功！")
						setTimeout(function() {
							uni.navigateBack({
								delta: 1
							})
						}, 2000);
					}else{
						uni.$u.toast("创建失败，请重试！")
					}
				}
			})
			
			}
			}
		}
	}
</script> 

<style scoped>
	@import url("../static/css/componentsCss/tzmarketindex/tzmarketaddorder.css");
	page {
		background-color: #f0f0f0;
	}
</style>