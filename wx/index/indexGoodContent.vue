<!-- 首页优质内容 -->
<template>
	<view id="body">
		<u-divider text="校园热门活动"></u-divider>
		<view class="home_preferred page_box">
			<!-- box -->
			<view class="preferred_box flex">
				<!-- item -->
				<view class="item" v-for="(item,index) in activityList" :key="index" @click="infoBtn(item.id)">
					<navigator url="###" hover-class="none">
						<image :src="http + item.image" class="item_pic" mode="widthFix"></image>
						<view class="item_title">{{item.title}}</view>
					</navigator>
				</view>
				<!-- item -->
				
				
			</view>
			<!-- box -->
			
		</view>
	</view>
</template>

<script>
	import {
		http
	} from "../static/service/requestAddress/rtas.js"
	export default {
		name:"indexGoodContent",
		data() {
			return {
				activityList:[],
				http: http
			}
		},
		methods:{
			childOnShow() {
				uni.request({
					url: http + "/wx/getActivity",
					method: "GET",
					complete: (res) => {
						this.activityList = res.data.data.list
					}
				})
			},
			infoBtn(id){
				uni.navigateTo({
					url:'/activity/index_info?id='+id
				})
			}
		}
	}
</script> 

<style scoped>
	@import url("../static/css/componentsCss/index/indexGoodContent.css");
</style>