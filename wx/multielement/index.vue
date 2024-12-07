<!-- 投诉建议-->
<template>
	<view id="body">
		<view class="order">
			<view class="title">
				<input placeholder="反馈标题" v-model="title" />
			</view>
			<!-- 反馈类型 -->
			<view class="type">
				<view v-for="(itme,index) in type" :key="index">
					<text :class="index == typeflage ? 'typetextok' : 'typetextno'" @click="typeclick(index)">{{itme}}</text>
				</view>
			</view>
			<!-- 信息内容 -->
			<view class="contnt">
				<uni-section class="mb-10" color="#3c9cff" title="描述问题" type="line" titleColor="#909399"></uni-section>
				<view class="content-input">
					<u--textarea v-model="content" placeholder="说点什么..."  count="true"  maxlength=400 ></u--textarea>
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
			<!-- 提交按钮 -->
			<view class="fabubutton">
				<u-button 
					text="提             交"
					size="normal"
					type="primary"
					color="#3c9cff"
					@click="tjclick"
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
				title:"",//订单标题
				type:["投诉","建议","其它"],//订单的分类类型
				typeflage:0,//反馈类型
				content:"",//订单内容
				imageLists:[],//图片数组
			}
		},
		methods:{
			//订单类型
			typeclick(index){
				this.typeflage = index
			},
			// 获取图片
			addImages() {
				var urlt 
				if(this.imageLists.length < 8){
					uni.chooseImage({
						count: 8, // 默认最多一次选择8张图
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
								if(this.imageLists.length < 8){
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
									uni.$u.toast("图片只能上传8张！")
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
					uni.$u.toast("图片只能上传8张！")
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
			async tjclick(){
				if(! uni.getStorageSync("user")){
					uni.$u.toast("登录后才能反馈喔")
					return
				}
				if(this.title.length <= 0){
					uni.$u.toast("反馈标题不能为空")
					return
				}else if(this.content.length <= 0){
					uni.$u.toast("反馈内容不能为空")
					return
				}else{
					//上传图片
					//显示消息提示框
					uni.showLoading({
						title:"正在创建中",
						mask: true
					})
					var path = "投诉反馈"//上传图片到oss的文件名
					var userid = uni.getStorageSync("user").id
					var data = {
						title:this.title,
						content:this.content,
						imageLists:this.imageLists,
						type:this.typeflage+1,
						userId: userid
					}
					uni.request({
						url:http+"/wx/sendFeedback",
						method:"POST",
						data:data,
						complete:(res) =>{
							if(res.data.code == 20000){
								uni.$u.toast("反馈成功，感谢您的反馈，谢谢!")
							}else{
								uni.$u.toast("反馈失败!")
							}
						}
					})
					uni.hideLoading()
					this.imageLists = []
					this.title = ""
					this.content = ""
					this.typeflage = 0
				}
			}
		}
	}
</script> 

<style>
	@import url("../static/css/componentsCss/multielement/index.css");
	page {
		background-color: #f0f0f0;
	}
	.leftbk{
		color: #ffffff;
		background-color: #3c9cff;
	}
	.rightbk{
		color: #606266;
		background-color: #f0f0f0;
	}
</style>