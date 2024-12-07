import {http} from 'static/service/requestAddress/rtas.js'
class websocket{
	constructor(url,time){
		this.url = url
		this.userId = null//连接的用户id
		this.data = null//ping的文本
		this.isCreate = false // WebSocket 是否创建成功
		this.isConnect = false // WebSocket是否是已经连接
		this.isInitiative = false // 是否是用户主动断开,默认不是
		this.timeoutNumber = time // 心跳检测间隔
		this.heartbeatTimer = null // 心跳检测定时器
		this.reconnectTimer = null // 断线重连定时器
		this.websocketExample = null // websocket实例
		this.againTime = 3 // 重连等待时间(单位秒)
		this.http = http
	}
	//初始化方法
	init(userid){
		var that = this
		var T = false //是否初始化成功
		this.userId = userid
		this.websocketExample = uni.connectSocket({
			url:that.url+userid,
			success:(res) => {
				that.isCreate = true
				T = true
				//建立链接
			},
			fail:(res) => {
			   that.isCreate = false
			   T = false
			}
		})
		if(T){
			this.createSocket()
		}
	}
    //创建websocket连接
    createSocket() {
       if (this.isCreate) {
         // 监听 WebSocket 连接打开事件
         try {
           this.websocketExample.onOpen(() => {
             this.isConnect = true
             clearInterval(this.heartbeatTimer)
             clearTimeout(this.reconnectTimer)
             // 打开心跳检测
             this.heartbeatCheck()
           })
           // 监听 WebSocket 接受到服务器的消息事件
           this.websocketExample.onMessage((res) => {
			   res = JSON.parse(res.data)
			//收到的系统消息
			if(res.isSystem == 0){
				//将消息传入自定义事件
				var num = 0//tabbar数量
				var tabbarnum = uni.getStorageSync('tabbar')
				var systemlist = JSON.parse(uni.getStorageSync("system"))
				//处理接收的系统消息 
				var T = JSON.parse(res.messagecontent)
				console.log("T"+JSON.stringify(T))	
				T.read = false
				T.createTime = (T.createTime).substring(0,T.createTime.lastIndexOf(":"))
				systemlist.push(T)
				//将接收的公告信息加入缓存
				uni.setStorageSync("system",JSON.stringify(systemlist))
				num = tabbarnum + 1
				//更新缓存中的tabbar
				uni.setStorageSync("tabbar",num)
				uni.setTabBarBadge({ //显示数字
					index: 1, //tabbar下标
					text: ""+ num,//数字
					success() {
					},
					fail(e){ 
					} 
				})
				//增加监听事件
				uni.$emit("system",0)
			}
			//收到好友的消息
			if(res.isSystem == 1){
				//将消息添加进用户消息缓存
				var list = uni.getStorageSync("userMessage")
				var T = []
				if(list){
					T = JSON.parse(list)
					T.push(res)
					uni.setStorageSync("userMessage",JSON.stringify(T))
				}else{
					T.push(res)
					uni.setStorageSync("userMessage",JSON.stringify(T))
				}
				//获取用户列表缓存，消息总数加一
				var userlist =JSON.parse(uni.getStorageSync("userfriendlist"))
				var flage = false
				for(var j = 0 ;j < userlist.length;j++){
					if(userlist[j].id == res.sendUser){
						userlist[j].lastmessage = res.messagecontent
						userlist[j].totalMessage += 1
						flage = true
						break
					} 
				}
				//添加陌生人的消息
				if(! flage){
					//将陌生人添加到好友列表缓存
					var UserFlage = {
						address: null,
						createTime: null,
						id: res.sendUser,
						img: res.sendUserImg,
						isStranger: true,
						lastmessage: res.messagecontent,
						limit: null,
						name: res.sendUserName,
						password: null,
						phone: null,
						sex: null,
						state: null,
						totalMessage: 1,
						updateTime: null,
						userFriend: null,
					}
					userlist.push(UserFlage)
					uni.setStorageSync("userfriendlist",JSON.stringify(userlist))
			}
			//更新tabbar
			var tabbarnum = uni.getStorageSync('tabbar')
			tabbarnum += 1
			//更新缓存中的tabbar
			uni.setStorageSync("tabbar",tabbarnum)
			uni.$emit("usermessage",res)
			}
			//添加好友信息
			if(res.isSystem == 2){
				var adduser =  uni.getStorageSync('offlineUserMessage')
				var T = []
				if(adduser){
					T = JSON.parse(adduser)
					T.push(res)
					uni.setStorageSync("offlineUserMessage",JSON.stringify(T))
				}else{
					T.push(res)
					uni.setStorageSync("offlineUserMessage",JSON.stringify(T))
				}
				//更新tabbar
				var tabbarnum = uni.getStorageSync('tabbar')
				tabbarnum += 1
				uni.setTabBarBadge({ //显示数字
					index: 1, //tabbar下标
					text: ""+ tabbarnum,//数字
					success() {
					},
					fail(e){ 
					} 
				})
				//更新缓存中的tabbar
				uni.setStorageSync("tabbar",tabbarnum)
			}
			//收到订单通知
			if(res.isSystem == 6){
				console.log("收到了订单通知"+JSON.stringify(res))
				var T = []
				var ordermsg = uni.getStorageSync("ordermsg")
				if(ordermsg.length == 0 | ordermsg == null){
					res.read = false
					T.push(res)
					uni.setStorageSync("ordermsg",JSON.stringify(T))
				}else{
					T = JSON.parse(uni.getStorageSync("ordermsg"))
					res.read = false
					T.push(res)
					uni.setStorageSync("ordermsg",JSON.stringify(T))
				}
				//更新tabbar
				var tabbarnum = uni.getStorageSync('tabbar')
				tabbarnum += 1
				uni.setTabBarBadge({ //显示数字
					index: 1, //tabbar下标
					text: ""+ tabbarnum,//数字
					success() {
					},
					fail(e){ 
					} 
				})
				//更新缓存中的tabbar
				uni.setStorageSync("tabbar",tabbarnum)
				//增加监听事件
				uni.$emit("ordermsg",0)
			}
			//上线提示
			if(res.isSystem == 4){
				//判断用户是首次上线还是重连
				var user = JSON.parse(uni.getStorageSync('user'))
				var systemList = []//所有公告数据
				var usermessageList = []//用户离线消息
				var flage =  false//删除用户离线信息是否成功
				var num = 0//tabbar数量
				var httpurl = this.http
				var promis =  new Promise(function(resolve,reject){
					//先获取所有公告数据
					uni.request({
						url:httpurl+"wt/list",
						method:'GET',
						complete:(res)=>{
							if(res.data.code == 20021){
								var list = res.data.date
								// 对时间处理
								for(var i = 0;i<list.length;i++){
									list[i].createTime = (list[i].createTime).substring(0,list[i].createTime.lastIndexOf(":"))
								}
								systemList = list
								resolve()
								}else{
									uni.$u.toast("获取系统公告失败!")
								}
							}
					})
				})
				promis.then(res=>{
					var promis1 = new Promise(function(resolve,reject){
						//获取离线时的好友信息
						uni.request({
							url:httpurl+"user/userOffline/"+user.id,
							method:'GET',
							complete:(res)=>{
								if(res.data.code == 20021){
									usermessageList = res.data.date
									uni.request({
										url:httpurl+"user/deleteuseroffline/"+user.id,
										method:"DELETE",
										complete: (res) => {
											if(res.data.code == 20031){
												flage = true
												console.log("111")
												resolve()
											}else{
												flage = false
											}
										}
									})
								}
							}
						})
					})
					promis1.then(res=>{
						//首次登录的情况
						if(user.isfistlogin == true){
							//设置系统公告缓存
							for(var i = 0; i< systemList.length;i++){
								//添加是否已读属性
								systemList[i].read = false
							}
							uni.setStorageSync("system",JSON.stringify(systemList))
							num += systemList.length
							//设置离线好友信息缓存
							if(usermessageList.length > 0 & flage){
								uni.setStorageSync("offlineUserMessage",JSON.stringify(usermessageList))
								num += usermessageList.length
							}
							// //此处直接设置tabbar会失败，因为tabbar还未加载
							uni.setStorageSync("tabbar",num)
							uni.$u.toast("连接服务器成功")
							
						}else{
							//获取缓存公告信息
							var system = []
							system = JSON.parse(uni.getStorageSync("system"))
							//有新公告的情况
							if(system.length < systemList.length){
								var T = false
								for(var i = 0;i<systemList.length;i++){
									if(i == system.length - 1){
										T = true
										continue
									}
									if(T){
										//添加是否已读属性
										systemList[i].read = false
										system.push(systemList[i])
									}
								}
								//更新系统公告
								uni.setStorageSync("system",JSON.stringify(system))
								num += systemList.length - system.length
							}
							//有新离线消息的情况
							if(usermessageList.length > 0 & flage ){
								//先判断缓存中有离线消息没
								var messsage = uni.getStorageSync("offlineUserMessage")
								if(messsage.length > 0){
									var a = JSON.parse(messsage)
									messsage =  a.concat(usermessageList)
									//更新缓存
									uni.setStorageSync("offlineUserMessage",JSON.stringify(messsage))
								}else{
									uni.setStorageSync("offlineUserMessage",JSON.stringify(usermessageList))
								}
								num += usermessageList.length
							}
							//设置tabbar
							if(num > 0){
								//获取缓存的tabbar数量
								var tabbarnum = uni.getStorageSync('tabbar')
								num += tabbarnum
								uni.setTabBarBadge({ //显示数字
									index: 1, //tabbar下标
									text: ""+ num,//数字
									success() {
										//更新缓存中的tabbar
										uni.setStorageSync("tabbar",num)
									},
									fail(e){ 
									    console.log("设置tabbar失败"+JSON.stringify(e))
									}
								})
							}
							uni.$u.toast("重新连接服务器成功")
						}
					})
				})
			}
           })
           // 监听 WebSocket 连接关闭事件
           this.websocketExample.onClose(() => {
             console.log('WebSocket 关闭了')
             this.isConnect = false
             this.reconnect()
           })
           // 监听 WebSocket 错误事件
           this.websocketExample.onError((res) => {
             this.isInitiative = false//是否是用户主动断开
           })
         }catch (error) {
           uni.$u.toast('WebSocket出现了异常!')
         }
       }else{
			uni.$u.toast('WebSocket 初始化失败!')
       }
    }
	// 发送消息
	sendMsg(value) {
		if(value.isSystem != 3){
		}
		var T = true//是否显示消息发送成功
		if(value.isSystem == 3){
			T = false
		}
		const param = JSON.stringify(value)
		return new Promise((resolve, reject) => {
			this.websocketExample.send({
			data: param,
			success() {
				if(T){
					uni.$u.toast('消息发送成功')
				}
				resolve(true)
			},
			fail(error) {
				uni.$u.toast("消息发送失败")
				reject(error) 
				}
			})
		})
	}
	// 开启心跳检测
	heartbeatCheck() {
	this.data = {
		sendUser:this.userId,
		receiveUser:this.userId,
		isSystem:3,//是否是系统消息,默认0系统消息，1好友消息,2添加好友，3,心跳校测,4,上线提示
		messagecontent:"ping"
	}
	this.heartbeatTimer = setInterval(() => {
	    this.sendMsg(this.data)
	}, this.timeoutNumber * 1000)
	}
	
	// 重新连接
	reconnect() {
		// 停止发送心跳
		clearTimeout(this.reconnectTimer)
		clearInterval(this.heartbeatTimer)
		// 如果不是人为关闭的话，进行重连
		if (!this.isInitiative) {
			this.reconnectTimer = setTimeout(() => {
			this.init(this.userId)
			}, this.againTime * 1000)
		}
	}
	//关闭链接
	closeWebsocket(){
		var that = this
		this.websocketExample.close({
		    success(){
				that.data = null
				that.isCreate = false
				that.isConnect = false
				that.isInitiative = true
				that.websocketExample = null
				clearInterval(that.heartbeatTimer)
				clearTimeout(that.reconnectTimer)
				console.log('关闭 WebSocket 成功')
		    },
		    fail(){
				console.log('关闭 WebSocket 失败')
		    }
		})
	}
}

export default  websocket