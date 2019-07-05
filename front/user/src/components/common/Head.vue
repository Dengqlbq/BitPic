<template>
	<Menu mode="horizontal" theme="light" active-name="1">

		<!-- logo -->
		<div class="logo left">
			<img class="logo-img" src="../../../build/logo.png" />
		</div>
		
		<!-- 分割线 -->
		<div class="left">
			<p> | </p>
		</div>
		
		<!-- 搜索框 -->
		<div class="left">
			<Input size="large" icon="ios-search" enter-button v-model="keyword" @on-enter="search" placeholder="探索 BitPic..." />
		</div>
		
		<!-- 登陆前 -->
		<div v-show="!hadLogin" class="user right">
			<Avatar @click.native="notLogin" class="userFace" icon="ios-person" />
			<Button class="no-padding" type="text" size="large">登陆</Button>
		</div>
		
		<!-- 登陆后 -->
		<div v-show="hadLogin" class="user right">			
			<Dropdown @on-click="userFunc">
					<Avatar class="userFace" :src="avatarUrl" />
				<DropdownMenu slot="list">
					<DropdownItem name="profile">个人中心</DropdownItem>
					<DropdownItem name="order">订单管理</DropdownItem>
					<DropdownItem name="collection">收藏盒子</DropdownItem>
					<DropdownItem name="pic">我的作品</DropdownItem>
					<DropdownItem name="logout">退出</DropdownItem>
				</DropdownMenu>
			</Dropdown>
		</div>
		
		<div v-show="hadLogin" class="user right">		
			<Dropdown @on-click="wishFunc">
				<Badge :count="wishCount" class="wish-count">	
					<Avatar class="userFace" icon="ios-heart" />
				</Badge>
				<Button class="no-padding" type="text" size="large">心愿单</Button>
				<DropdownMenu slot="list">
					<DropdownItem name="list">查看</DropdownItem>
					<DropdownItem name="clear">清空</DropdownItem>
				</DropdownMenu>
			</Dropdown>
		</div>
		
		<div v-show="hadLogin" class="user right">
			<Dropdown @on-click="walletFunc">
				<Avatar class="userFace" icon="ios-cash" />
				<Button class="no-padding" type="text" size="large">钱包</Button>
				<DropdownMenu slot="list">
					<DropdownItem name="balance">余额: {{balance}}</DropdownItem>
					<DropdownItem name="recharge">充值</DropdownItem>
					<DropdownItem name="cash">提现</DropdownItem>
				</DropdownMenu>
			</Dropdown>
		</div>
		
		<Modal
			v-model="showPay"
			title="充值">
			<img src="../../../build/pay.jpg" class="pay-img">
			<Input v-model="currentAmount" placeholder="扫描二维码进行充值" autofocus @on-enter="chargeBalance"/>
			<div slot="footer">
				<Button type="info" @click="chargeBalance">确定</Button>
				<Button type="text">取消</Button>
			</div>
		</Modal>
		
		<Modal
			v-model="showCash"
			title="提现">
			<Input v-model="t" placeholder="请输入银行卡号" autofocus />
			<Input v-model="currentAmount" placeholder="请输入提现金额" @on-enter="decrBalance"/>
			<div slot="footer">
				<Button type="info" @click="decrBalance">确定</Button>
				<Button type="text">取消</Button>
			</div>
		</Modal>
	</Menu>
</template>

<style scoped>
	
	.wish-count {
		display: initial;
	}
	
	.left {
		float: left;
	}
	
	.right {
		float: right;
	}
	
	.no-padding {
		padding-right: 0;
		padding-left: 0;
	}
	
	.logo {
		height: 100%;
		position: relative;
		margin-left: 20px;

	}
	
	/deep/ .ivu-input {
		border: none;
	}
	
	.logo-img {
		top: 15%;
		position: relative;
		height: 70%;
		width: 140px;
	}
	
	.user {
		margin-right: 20px;
	}
	
	.userFace {
		background-color: #87d068;
		cursor: pointer;
	}
	
	.pay-img {
		width: 300px;
		height: 300px;
		margin-left: 93px;
	}
	
</style>
<script>
import axios from 'axios'

export default {
	data() {
		return {
			hadLogin: false,
			// 用户数据
			wishCount: 0,
			balance: 0,
			userId: '',
			// 头像相关
			avatarUrl: 'http://localhost:8089/images/avatar/default.png',
			imgServerUrl: 'http://localhost:8089/images/',
			avatarPathFileName: '/avatar/avatar.png',
			// 登出
			logoutUrl: 'http://localhost:8080/api/bitpic/user/authenticate/logout/',
			
			chargeUrl: 'http://localhost:8080/api/bitpic/user/wallet/recharge',
			cashUrl: 'http://localhost:8080/api/bitpic/user/wallet/cash',
			showPay: false,
			showCash: false,
			currentAmount: 0,
			t: 0,
			
			keyword: '',
		}
	},
	// 此处为了解决detail中生命周期问题，临时
	computed: {
		hadLogins () {
			return this.$store.state.hadLogin;
		},
		userData () {
			return this.$store.state.userData;
		}
	},
	mounted () {
		if (this.hadLogins) {
			this.setUserData(this.userData);
		}
	},
	methods: {
		reset () {
			this.hadLogin = false;
			this.wishCount = 0;
			this.balance = 0;
			this.userId = '';
		},
		notLogin () {
			this.$emit('notLogin');
		},
		setUserData (data) {
			this.hadLogin = true;
			this.userId = data.id;
			this.wishCount = data.wishCount;
			this.balance = data.balance;
			if (data.haveAvatar) {
				this.avatarUrl = this.imgServerUrl + this.userId + this.avatarPathFileName;
			}
		},
		walletFunc (name) {
			if (name == 'recharge') {
				this.showPay = true;
			} else if (name == 'cash') {
				this.showCash = true;
			}
		},
		wishFunc (name) {
			if (name == 'list') {
				this.$router.push('/wishes');
			}
		},
		userFunc (name) {
			if (name == 'logout') {
				axios.get(this.logoutUrl + this.userId);
				this.reset();
				localStorage.clear();
				this.$store.commit('logout');
				this.$router.push('/tologin/profile');
			} else if (name == 'profile') {
				this.$router.push('/profile')
			}
		},
		chargeBalance () {
			let params = new FormData();
			params.append('userId', this.userId);
			params.append('amount', this.currentAmount);
			
			axios.patch(this.chargeUrl, params)
				.then(res => {
					if (res.data.code == 4) {
						this.$Message.info('充值成功');
						this.updateBalance(parseFloat(this.currentAmount));
						this.showPay = false;
					} else {
						this.$Message.error(res.data.message);
					}
				})
				.catch(err => {
					console.log(err);
					this.$Message.error('充值失败!');
				})
		},
		decrBalance () {
			if (this.balance < this.currentAmount) {
				this.$Message.error('余额不足!')
				return ;
			}
			
			let params = new FormData();
			params.append('userId', this.userId);
			params.append('amount', this.currentAmount);
			
			axios.patch(this.chargeUrl, params)
				.then(res => {
					if (res.data.code == 4) {
						this.$Message.info('提现成功，将于2小时内到账');
						this.updateBalance(-parseFloat(this.currentAmount));
						this.showCash = false;
					} else {
						this.$Message.error(res.data.message);
					}
				})
				.catch(err => {
					console.log(err);
					this.$Message.error('充值失败');
				})
		},
		updateBalance (amount) {
			this.balance += amount;
			this.$store.commit('updateBalance', amount);
		},
		addWish () {
			this.wishCount ++;
			this.$store.commit('addWish');
		},
		delWish () {
			this.wishCount --;
			this.$store.commit('delWish');
		},
		clearWish () {
			this.wishCount = 0;
			this.$store.commit('clearWish');
		},
		changeAvatar (url) {
			this.avatarUrl = url;
			this.$store.commit('updateAvatar');
		},
		search () {
			var path = this.$route.path;
			if (path.startsWith('/list')) {
				this.$emit('refreshData', this.keyword);
			} else {
				this.$router.push('/list/' + this.keyword);
			}
		}
	}
}
</script>
