<script>
	import {socketurl} from "static/service/requestAddress/rtas.js"
	import websocket from "websocket.js" 
	export default {
		globalData:{
			websocket:new websocket(socketurl,5),
			system:[],//系统消息
			usermessage:[],//好友消息
			adduser:[],//添加好友信息
			tabbarNum:0,//初始化提示消失信息
			T:true,//是否开启关闭websocket，在上传头像时关闭
		},
		onLaunch: function() { 
			//监听系统消息
			uni.$on("system",res =>{
				//设置tabbar
				uni.setTabBarBadge({ //显示数字
					index: 1, //tabbar下标
					text:""+1,//数字
					success() { 
					},
					fail(e){ 
					}
				})
			})
			uni.$on("uploadimg",res=>{
				this.globalData.T = res
				console.log("监听到方法了："+res)
			})
		},
		onShow: function() {
			//获取缓存的用户信息
			if(this.globalData.T){
				var user = uni.getStorageSync("user")
				if(user){//有缓存的情况
					var T = JSON.parse(user)
					if(T.isfistlogin == true){//更新连接状态
						T.isfistlogin = false
						uni.setStorageSync("user",JSON.stringify(T))
					}
					console.log("app进入重连："+this.globalData.T)
					this.globalData.websocket.init(JSON.parse(user).id)
					uni.$u.toast("正在重连中...")
				}
			}
		},
		onHide: function() {
			if(this.globalData.T){
				this.globalData.websocket.closeWebsocket()
			}
		},
			
	}
</script>

<style lang="scss">
	/* 注意要写在第一行，同时给style标签加入lang="scss"属性 */
	@import "@/uni_modules/uview-plus/index.scss";
	@import "@/static/font/iconfont-weapp-icon.css"//引入阿里巴巴彩色矢量图标
</style> 
