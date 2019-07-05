<template>
	<div>
		<!-- 头部 -->
		<Head v-on:notLogin="notLogin" ref="head"></Head>
		
		<!-- 登陆注册框 -->
		<Login v-on:loginSuc="loginSuc" v-on:regSuc="regSuc" ref="login"></Login>
		
		<!-- 装饰分隔栏 -->
		<div style="height: 8px; background: #EEE;"></div>
		
		<div style="margin-left: 15vw; margin-right: 15vw;">
			<!-- 封面和作品详情 -->
			<div style="overflow: hidden; margin-top: 20px;"> 
				<div style="float: left;" id="firstImgWindow">
					<img class="first-img" :src="data.urls[0][0]" :bigImgUrl="data.urls[0][1]" @click="showBigImg">
				</div>
				
				<div style="float: right;">
					<Card class="card">
						<h3 class="card-title">作品信息</h3>
						<p class="card-text">编号：{{data.number}}</p>
						<p class="card-text">版权：{{data.authorName}}</p>
						<p class="card-last">格式：{{formats.toString().toUpperCase()}}</p>
						<Button ghost @click="handleColl" size="large" icon="ios-star-half" type="info" class="card-button">{{collText}}</Button>
						<Button ghost @click="addLike" :disabled="!canLike" size="large" icon="md-thumbs-up" type="info">点赞</Button>
						<Divider />
						<p class="card-text permision-text">                   
							<span class="permision-span" @click="changePrice(PERMISION_TYPE.S)">标准授权</span>
							<Divider type="vertical"></Divider>
							<span class="permision-span" @click="changePrice(PERMISION_TYPE.P)">扩展授权</span>       
							<Divider type="vertical"></Divider>
							<Icon type= "ios-star-half"></Icon>               
						</p>
						<Table height=200 stripe :columns="columns" :data="imgData"></Table>
						<Divider />
						<div>
						<Button size="large" :disabled="!canWish" type="warning" @click="addWish">加入心愿单</Button>
						<span v-show="!currentPrice == 0" class="price-span">价格: {{currentPrice}} 元</span>
						</div>
					</Card> 
				</div>
			</div>
			
			<!-- 组内其他作品 -->
			<div style="overflow: hidden; margin-top: 10vh;" v-if="data.group">
				<h2>组内作品:</h2>
				<div class="img-window" id="imgWindow">
					<div class="img-box" :style="getStyle(data.sizePixel[index])" v-for="(url, index) in data.urls" :key="url.id">
						<img class="group-img" :bigImgUrl="url[1]" :src="url[0]" @click="showBigImg">
					</div>
				</div>
			</div>
			
		</div>
	</div>
</template>

<style scoped>
	.first-img {
		width: 20vw;
		height: auto;
		cursor: pointer;
	}
	
	.group-img {
		max-width: 100%;
		min-width: 100%;
		height: 250px;
		    object-fit: cover;
		vertical-align: bottom;
		cursor: pointer;
	}
	
	.img-window {
		display: flex;/*显示模式设置为弹性盒子*/
	    flex-wrap: wrap;/*进行强制换行*/
	}
	
	.img-window:after{            
	    /*对最后一个伪元素进行最大限度伸缩*/            
	    content: '';            
	    flex-grow: 9999999999999999999999;        
	} 
	
	.img-box {
		margin: 2px;
		position: relative;
		height: 250px;
		flex-grow: 1;
		background-color: violet;
	}
	
	.card {
		float: right; 
		width: 35vw; 
		height: auto
	}

	/* 卡片标题 */
	.card-title {
		margin-bottom: 15px;
	}

	/* 卡片内一般文字间隔 */
	.card-text {
		margin-bottom: 5px;
	}

	/* 卡片上半部分最后一行字 */
	.card-last {
		margin-bottom: 10px;
	}

	/* 卡片内按钮 */
	.card-button {
		margin-right: 5px; 
		font-size: 13px
	}

	/* 授权字样 */
	.permision-text {
		font-size: 20px;
	}
	
	.permision-span {
		cursor: pointer;
	}
	
	.permision-span:hover {
		color: #1F9AF5;
		font-size: 25px;
		transition: .5s;
	}
	
	.price-span {
		float: right; 
		margin-right: 40px; 
		color: red; 
		font-size: 25px;
	}
</style>

<script>
import axios from 'axios'
import Head from '../common/Head.vue'
import Login from '../common/Login.vue'
import 'viewerjs/dist/viewer.css'
import Viewer from 'viewerjs';
	
export default {
	data () {
		return {
			dataUrl: 'http://localhost:8080/api/bitpic/picShow/detail/',
			data: '',
			number: '',
			formats: [],

			delCollectionUrl: 'http://localhost:8080/api/bitpic/user/collections/delete/',
			collectionUrl: 'http://localhost:8080/api/bitpic/user/collections/add',
			likeUrl: 'http://localhost:8080/api/bitpic/user/like/incr',

			wishUrl: 'http://localhost:8080/api/bitpic/user/wish/add',
			currentType: -1,
			currentPrice: 0,
			PERMISION_TYPE: {
				S: 0,
				P: 1,
			},
			
			imgServerUrl: 'http://localhost:8089/images/',
			bigThumNameSuffix: 'big',
			defaultThumName: '.png',
			
			canColl: true,
			canLike: true,
			canWish: true,
			collText: '收藏',
			
			OPERATION_SUC_CODE: 4,
			DIRECTION: {
				H: 0,
				V: 1,
			},
			
			viewer: '',
			firstClick: true,
			groupImgHeight: 250,
			
			columns: [
                    {
                        title: '序号',
                        key: 'num'
                    },
                    {
                        title: '格式',
                        key: 'format'
                    },
                    {
                        title: '宽高(像素)',
                        key: 'sizePixel'
                    },
					{
						title: '宽高(厘米)',
						key: 'sizeCentimetre'
					},
					{
						title: 'DPI',
						key: 'dpi'
					}
            ],
			imgData: [],
		}
	},
	components: {
		'Login': Login,
		'Head': Head,
	},
	computed: {
		hadLogin () {
			return this.$store.state.hadLogin;
		},
		userData () {
			return this.$store.state.userData;
		}
	},
	mounted () {
		axios.defaults.withCredentials = true;
		this.number = this.$route.params.number;
		
		axios.get(this.dataUrl + this.number)
			.then(res => {
				if (res.data.code == this.OPERATION_SUC_CODE) {
					var data = res.data.data;
					var authorId = data.authorId;
					data.urls = new Array();
					
					this.imgData = new Array(data.formats.length);
					for (var i = 0; i < data.formats.length; i ++) {
						if (!this.exists(data.formats[i])) {
							this.formats.push(data.formats[i]);
						}
						
						data.urls[i] = new Array(2);
						var surl = this.imgServerUrl + authorId + '/' + this.number + '/' + i + this.defaultThumName;
						var burl = this.imgServerUrl + authorId + '/' + this.number + '/' + i + this.bigThumNameSuffix + this.defaultThumName;
						data.urls[i][0] = surl;
						data.urls[i][1] = burl;
						
						this.imgData[i] = {
							num: i,
							format: data.formats[i].toUpperCase(),
							sizePixel:  data.sizePixel[i][0] + ' x ' + data.sizePixel[i][1],
							sizeCentimetre: data.sizeCentimetre[i][0] + ' x ' + data.sizeCentimetre[i][1],
							dpi: data.dpi[i]
						}
					}
					
					this.data = data;
				} else {
					this.$Message.error(res.data.message);
				}
			})
			.catch(err => {
				console.log(err);
				this.$Message.error('获取数据失败')
			})
	},
	methods: {
		notLogin () {
			this.$refs.login.show();
		},
		loginSuc (userData) {
			this.$store.commit('loginSuc', userData);
			this.$refs.head.setUserData(userData);
		},
		regSuc (userData) {
			this.$store.commit('loginSuc', userData);
			this.loginSuc(userData);
		},
		exists (format) {
			for (var f in this.formats) {
				if (this.formats[f] == format) {
					return true;
				}
			}
			return false;
		},
		getStyle (size) {
			var w = size[0] * this.groupImgHeight / size[1];
			return 'width: ' + w + 'px;' + 'height: ' + this.groupImgHeight + 'px; flex-grow: ' + w + ';'
		},
		showBigImg () {
			if (this.firstClick) {
				this.firstClick = false;
				
				var s = 'imgWindow';
				if (!this.data.group) {
					s = 'firstImgWindow';
				}
				const ViewerDom = document.getElementById(s);
				const viewer = new Viewer(ViewerDom, {url: 'bigImgUrl'});
				this.viewer = viewer;
				// 因为封面图和其他图不在
				this.viewer.show();
			} else {
				// 因为封面图和其他图不在
				this.viewer.show();
			}
		},
		changePrice (type) {
			this.currentType = type;
			if (type == this.PERMISION_TYPE.S) {
				this.currentPrice = this.data.priceStandard;
			} else {
				this.currentPrice = this.data.pricePlus;
			}
		},
		addWish () {
			if (!this.hadLogin) {
				this.$router.push('/tologin/' + this.data.number);
				return ;
			}
			
			if (this.currentType == -1) {
				this.$Message.info('请选择授权类型');
				return ;
			}
			
			this.canWish = false;
			let params = new FormData();
			params.append('userId', this.userData.id);
			params.append('number', this.data.number);
			params.append('type', this.currentType);
			
			axios.post(this.wishUrl, params)
				.then(res => {
					if (res.data.code == this.OPERATION_SUC_CODE) {
						this.$Message.success('已添加至心愿单');
						this.$refs.head.addWish();
					} else {
						this.$Message.error(res.data.message);
					}
				})
				.catch(err => {
					console.log(err);
					this.$Message.error('添加心愿失败!');
				})
		},
		handleColl () {
			if (!this.hadLogin) {
				this.$router.push('/tologin/' + this.data.number);
				return ;
			}
			
			if (this.canColl) {
				this.canColl = false;
				this.collText = '取消收藏';
				
				let params = new FormData();
				params.append('userId', this.userData.id);
				params.append('number', this.data.number);
				
				axios.post(this.collectionUrl, params)
					.then(res => {
						if (res.data.code == this.OPERATION_SUC_CODE) {
							this.$Message.success('已添加至收藏盒子');
						} else {
							this.$Message.error(res.data.message);
						}
					})
					.catch(err => {
						console.log(err);
						this.$Message.error('添加收藏失败!');
					})
			} else {
				this.canColl = true;
				this.collText = '收藏';
				
				var url = this.delCollectionUrl + this.userData.id + '/' + this.data.number;
				axios.delete(url)
					.then(res => {
						if (res.data.code == this.OPERATION_SUC_CODE) {
							this.$Message.success('已从收藏盒子中删除');
						} else {
							this.$Message.error(res.data.message);
						}
					})
					.catch(err => {
						console.log(err);
						this.$Message.error('删除收藏失败!');
					})
			}
			
		},
		addLike () {
			if (!this.hadLogin) {
				this.$router.push('/tologin/' + this.data.number);
				return ;
			}
			
			this.canLike = false;
			let params = new FormData();
			params.append('userId', this.userData.id);
			params.append('number', this.data.number);
			
			axios.patch(this.likeUrl, params)
				.then(res => {
					if (res.data.code == this.OPERATION_SUC_CODE) {
						this.$Message.success('已赞');
					} else {
						this.$Message.error(res.data.message);
					}
				})
				.catch(err => {
					console.log(err);
					this.$Message.error('点赞失败!');
				})
		}
	}
}
</script>