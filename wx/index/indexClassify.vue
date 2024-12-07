<!-- 主页分类组件 -->
<template>
	<view id="body">
		<view>
		    <u-grid :border="false" @click="click" col="3">
		        <u-grid-item v-for="(baseListItem,baseListIndex) in baseList":key="baseListIndex">
		            <text :class="baseListItem.title":customStyle="{paddingTop:20+'rpx'}" style="margin-top: 20rpx; width: 64rpx; height: 64rpx;"></text>
		            <text class="grid-text" style="color:#000000;">{{baseListItem.name}}</text>
		        </u-grid-item>
		    </u-grid>
		    <u-toast ref="uToast" />
		</view>
	</view>
</template> 

<script>
	export default {
		name:"indexclassify",
		data() {
			return {
				baseList: [
					{
						name: '校园活动',
						title: 't-icon t-icon-shequ',
					},
				    {
				        name: '失物招领',
				        title: 't-icon t-icon-shiwuzhaoling'
				    },  
					{
						name: '校园兼职',
						title:'t-icon t-icon-renwu'
					},
					{
					    name: '二手市场',
					    title: 't-icon t-icon-xinniantubiaomoban-15'
					},
					{
						name:'校园论坛',
						title:'t-icon t-icon-luntanzixun'
					},
					{
						name:'意见反馈',
						title:'t-icon t-icon-tousujianyi'
					}
				],
		}
	},
	methods:{
		click(name) {
			
			var urlList = [
				"/activity/index",
				"/shiwuzhaoling/swzlindex",
				"/job/index",
				"/goods/goodsindex",
				"/forum/index",
				"/multielement/index"
			]
			
			uni.navigateTo({ 
				url:urlList[name]
			})
		}, 
	}
}
</script>
	
<style scoped>
	@import url("../static/css/componentsCss/index/indexClassify.css");
</style>