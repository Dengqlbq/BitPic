<template>
	<div>
		<!-- 头部 -->
		<Head v-on:notLogin="notLogin" ref="head"></Head>
		
		<!-- 登陆注册框 -->
		<Login v-on:loginSuc="loginSuc" v-on:regSuc="regSuc" ref="login"></Login>
		
		<!-- 装饰分隔栏 -->
		<div style="height: 8px; background: #EEE;"></div>
		
		<div class="wish-window">
			<div class="wish-title">
				My Wishes
			</div>
			
			<Scroll :on-reach-bottom="reachBottom" height=625>
				<Card dis-hover v-for="(data, index) in datas" :key="data.id" style="margin: 16px 0; height: 232px;">
					<div class="img-box">
						<img :src="data.url" @click="redirect(data.number)">
					</div>
					<div class="data-box">
						<p>授权类型: <a href="#">{{getAuthType(data.type)}}</a></p>
						<p>价格: <a href="#">{{data.price}}</a></p>
					</div>
					<div class="action-box">
						<Checkbox class="check-box" @click.native="select(index)" v-model="data.selected">{{text}}</Checkbox>
						<div class="action-button-box">
							<Button type="info" @click="moveWishToColl(index)">移入收藏盒子</Button>
							<Button type="error" @click="deleteWish(index)">删除</Button>
						</div>
					</div>
				</Card>
			</Scroll>
			
			<Divider />
			
			<div class="result-box">
				<div class="result-data-box">
					<Checkbox @click.native="selectAllOrNot" v-model="all" class="check-box" style="position: absolute; top: 0; left: 0;">{{allSelText}}</Checkbox>
					<p style="position: absolute; left: 0; bottom: 0;">已选: {{count}}</p>
				</div>
				
				<div class="result-action-box">
					<p class="price-box">总价: {{total}}</p>
					<div class="result-button-box">
						<Button size="large" type="error" @click="currentDelType = DEL_TYPE.A; showDel = true;">删除</Button>
						<Button size="large" type="info" @click="moveWishToColl(1000)">移入收藏盒子</Button>
						<Button size="large" type="success" @click="accounts">结算</Button>
					</div>
				</div>
			</div>
			
			<Modal
				v-model="showDel"
				title="警告"
				@on-ok="ok">
				<p>确认删除所愿心愿吗？</p>
			</Modal>
			
			<Modal
				:closable="false"
				v-model="showAccount"
				title="创建订单">
				<Card v-for="data in newDatas" :key="data.id">
					<img :src="data.url">
					<span style="font-size: 20px; color: #1F9AF5;">{{getAuthType(data.type)}}</span>
					<span style="font-size: 20px; color: red;">{{data.price}}元</span>
				</Card>
				<div slot="footer">
					<p style="float: left; color: red">总价: {{getOrderTotal()}}</p>
					<Button type="error" @click="cancel">取消</Button>
					<Button type="success" @click="createOrder">确认</Button>
				</div>
			</Modal>
		</div>
	</div>
</template>

<style scoped>
	.wish-window {
		margin-top: 20px;
		margin-left: 20vw;
		margin-right: 20vw;
		height: 90vh;
	}
	
	.wish-title {
		font-size: 48px;
	}
	
	p {
		font-size: 20px;
	}
	
	.img-box {
		float: left;
	}
	
	.data-box {
		position: relative;
		float: left;
		margin-left: 20px;
		width: 40%;
		height: 200px;
	}
	
	.action-box {
		position: relative;
		float: right;
		height: 200px;
		width: 200px;
	}
	
	.check-box {
		position: absolute;
		top: 0;
		right: 0;
	}
	
	.action-button-box {
		position: absolute;
		right: 0;
		bottom: 0;
	}
	
	.result-box {
		position: relative;
		margin-top: 20px;
		height: 80px;
		background: bisque;
	}
	
	.result-data-box {
		position: relative;
		float: left;
		height: 80px;
		width: 200px;
		margin-left: 10px;
	}
	
	.result-action-box {
		position: relative;
		float: right;
		height: 80px;
		width: 300px;
		margin-right: 10px;
	}
	
	.price-box {
		position: absolute;
		top: 0;
		right: 0;
	}
	
	.result-button-box {
		position: absolute;
		right: 0;
		bottom: 0;
	}
	
	img {
		width: auto;
		height: 200px;
		cursor: pointer;
	}
	
</style>

<script>
import axios from 'axios'
import Head from '../common/Head.vue'
import Login from '../common/Login.vue'

export default {
	data () {
		return {
			dataUrl: 'http://localhost:8080/api/bitpic/user/wish/list/',
			imgServerUrl: 'http://localhost:8089/images/',
			delWishUrl: 'http://localhost:8080/api/bitpic/user/wish/delete/',
			addCollUrl: 'http://localhost:8080/api/bitpic/user/collections/add/',
			updateWishUrl: 'http://localhost:8080/api/bitpic/user/wish/update/',
			createOrderUrl: 'http://localhost:8080/api/bitpic/user/order/create/',
			defaultImgName: '0.png',
			page: 0,
			
			datas: [],
			numbers: [],
			
			text: '',
			allSelText: '全选',
			all: false,
			count: 0,
			total: 0,
			
			showDel: false,
			currentIndex: '',
			currentDelType: 0,
			DEL_TYPE: {
				S: 0,
				A: 1,
			},
			
			selectedWishDatas: [],
			newDatas: [],
			showAccount: false,
			wishListString: '',
			
			OPERATION_SUC_CODE: 4,
			AUTH_TYPE: {
				S: 0,
				P: 1,
			}
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
		if (!this.hadLogin) {
			this.$router.push('/tologin/wishes');
		}
		
		axios.defaults.withCredentials = true;
		this.getData();
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
		getData () {
			var url = this.dataUrl + this.userData.id + '/' + this.page;
			this.page ++;
			
			axios.get(url)
				.then(res => {
					if (res.data.code == this.OPERATION_SUC_CODE) {
						var data = res.data.data;
						if (data.length == 0) {
							this.page = 0;
							this.$Message.info('没有更多数据');
							return ;
						}
						
						var count = 0;
						for (var k in data) {
							if (!this.exists(data[k].number)) {
								this.numbers.push(data[k].number);
							
								var url = this.imgServerUrl + data[k].authorId + '/' + data[k].number + '/' + this.defaultImgName;
								data[k].url = url;
								data[k].selected = false;
								this.datas.push(data[k]);
							
								count ++;
							}
						}
						if (count == 0) {
							this.$Message.info('没有更多数据');
						}
					} else {
						this.$Message.error('获取数据失败');
					}
				})
				.catch(err => {
					console.log(err);
					this.$Message.error('获取数据失败');
				})
		},
		reachBottom () {
			return new Promise(resolve => {
					setTimeout(() => {
						this.getData();
						resolve();
					}, 1000);
				});
		},
		exists (number) {
			for (var n in this.numbers) {
				if (this.numbers[n] == number) {
					return true;
				}
			}
			return false;
		},
		getAuthType (type) {
			return type == this.AUTH_TYPE.S ? '标准授权' : '扩展授权';
		},
		select (index) {
			if (this.datas[index].selected) {
				if (this.all) {
					this.all = false;
					this.allSelText = '全选';
				}
				this.count --;
				this.total -= this.datas[index].price;
			} else {
				this.count ++;
				this.total += this.datas[index].price;
			}
			this.datas[index].selected = !this.datas[index].selected;
		},
		selectAllOrNot () {
			if (this.all) {
				this.all = false;
				this.allSelText = '全选';
				for (var i in this.datas) {
					this.datas[i].selected = false;
				}
				this.count = 0;
				this.total = 0;
			} else {
				this.all = true;
				this.allSelText = '取消全选';
				for (var i in this.datas) {
					this.datas[i].selected = true;
				}
				this.count = this.datas.length;
				this.total = 0;
				for (var i in this.datas) {
					this.total += this.datas[i].price;
				}
			}
		},
		redirect (number) {
			this.$router.push('/detail/' + number);
		},
		ok () {
			if (this.currentDelType == this.DEL_TYPE.S) {
				this.deleteSingle(this.currentIndex);
			} else {
				this.deleteAllSel();
			}
		},
		cancel () {
			this.showAccount = false;
			this.selectedWishDatas = [];
			this.newDatas = [];
			this.wishListString = '';
		},
		deleteWish (index) {
			this.currentDelType = this.DEL_TYPE.S;
			this.currentIndex = index;
			this.showDel = true;
		},
		deleteSingle (index) {
			if (this.datas[index].selected) {
				this.count --;
				this.total -= this.datas[index].price;
			}
			
			var url = this.delWishUrl + this.userData.id + '/' + this.datas[index].number;
			this.datas.splice(index, 1);
			
			axios.delete(url)
			this.$refs.head.delWish();
		},
		deleteAllSel () {
			for (var i = 0; i < this.datas.length; i ++) {
				if (this.datas[i].selected) {
					var url = this.delWishUrl + this.userData.id + '/' + this.datas[i].number;
					this.count --;
					this.total -= this.datas[i].price;
					axios.delete(url);
					this.datas.splice(i, 1);
					i --;
				}
			}
			this.$refs.head.clearWish();
		},
		moveWishToColl (index) {
			if (index > 999) {
				for (var i = 0; i < this.datas.length; i ++) {
					if (this.datas[i].selected) {
						this.count --;
						this.total -= this.datas[i].price;
						
						var url = this.delWishUrl + this.userData.id + '/' + this.datas[i].number;
						axios.delete(url);
						
						this.moveToColl(i)
						this.datas.splice(i, 1);
						i --;
					}
				}
				this.$refs.head.clearWish();
			} else {
					if (this.datas[index].selected) {
						this.count --;
						this.total -= this.datas[index].price;
						
						var url = this.delWishUrl + this.userData.id + '/' + this.datas[index].number;
						axios.delete(url);
						
						this.moveToColl(index)
						this.$refs.head.delWish();
						this.datas.splice(index, 1);
					}
				}
		},
		moveToColl (index) {
			
			let params = new FormData();
			params.append('userId', this.userData.id);
			params.append('number', this.datas[index].number);
			axios.post(this.addCollUrl, params);
			
			
		},
		accounts () {
			var wishListStr = '[';
			for(var i in this.datas) {
				if (this.datas[i].selected) {
					wishListStr += '{number:' + this.datas[i].number + ',type:' + this.datas[i].type + '},';
					this.selectedWishDatas.push(this.datas[i]);
				}
			}
			wishListStr = wishListStr.substring(0, wishListStr.length - 1);
			wishListStr += ']';
			
			this.wishListString = wishListStr;
			
			let params = new FormData();
			params.append('userId', this.userData.id);
			params.append('wishList', wishListStr);
			
			axios.post(this.updateWishUrl, params)
				.then(res => {
					if (res.data.code == this.OPERATION_SUC_CODE) {
						var data = res.data.data;
						for (var i in data) {
							data[i].url = this.imgServerUrl + data[i].authorId + '/' + data[i].number + '/' + this.defaultImgName;
							this.newDatas.push(data[i]);
						}
						this.showAccount = true;
						
					} else {
						this.$Message.error(res.data.message);
					}
				})
				.catch(err => {
					console.log(err);
					this.$Message.error('未知错误')
				})
		},
		createOrder () {
			let params = new FormData();
			params.append('userId', this.userData.id);
			params.append('wishList', this.wishListString);
			
			axios.post(this.createOrderUrl, params)
				.then(res => {
					if (res.data.code == this.OPERATION_SUC_CODE) {
						this.$Message.success('订单创建成功，请尽快支付');
						
						for (var i in this.selectedWishDatas) {
							this.datas.splice(this.datas.indexOf(this.selectedWishDatas[i]), 1);
							this.$refs.head.delWish();
						}
						
						this.cancel();
						this.count = 0;
						this.total = 0;
						this.all = false;
					} else {
						this.$Message.error(res.data.message);
					}
				})
				.catch(err => {
					console.log(err);
					this.$Message.error('创建订单失败!');
				})
			
		},
		getOrderTotal () {
			var total = 0;
			this.newDatas.forEach(d => total += d.price);
			return total;
		}
	}
}
</script>