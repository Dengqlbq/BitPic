<template>
    <div style="height: 100vh;">
		<!-- 头部 -->
		<Head v-on:notLogin="notLogin" ref="head"></Head>
		
		<!-- 登陆注册框 -->
		<Login v-on:loginSuc="loginSuc" v-on:regSuc="regSuc" ref="login"></Login>
		
		<!-- 装饰分隔栏 -->
		<div style="height: 8px; background: #EEE;"></div>
		
		<Layout>
                <Sider hide-trigger :style="{background: '#fff', height: '93vh'}">
                    <Menu active-name="1-2" theme="light" width="auto" :open-names="['1']">
						<Submenu name="profile">
						    <template slot="title">
						        <Icon type="ios-navigate"></Icon>
						        个人
						    </template>
						    <MenuItem name="aboutMe" v-on:changeAvatar="changeAvatar" @click.native="openMenu('aboutMe')">资料</MenuItem>
						</Submenu>
                        <Submenu name="auth" v-show="userData.photographer && !userData.certificated">
                            <template slot="title">
                                <Icon type="ios-navigate"></Icon>
                                身份
                            </template>
                            <MenuItem name="authUpload" @click.native="openMenu('authUpload')">提交认证</MenuItem>
                            <MenuItem name="authResult" @click.native="openMenu('authResult')">认证结果</MenuItem>
                        </Submenu>
                        <Submenu name="order">
                            <template slot="title">
                                <Icon type="ios-keypad"></Icon>
                                订单
                            </template>
                            <MenuItem name="orderWait" @click.native="openMenu('orderWait')">待付款</MenuItem>
                            <MenuItem name="orderfinish" @click.native="openMenu('orderFinish')">已完成</MenuItem>
							<MenuItem name="orderCancel" @click.native="openMenu('orderCancel')">已取消</MenuItem>
							<MenuItem name="orderRefund" @click.native="openMenu('orderRefund')">已退款</MenuItem>
                        </Submenu>
                        <Submenu name="pic" v-show="userData.photographer && userData.certificated">
                            <template slot="title">
                                <Icon type="ios-analytics"></Icon>
                                作品
                            </template>
                            <MenuItem name="picMy" @click.native="openMenu('picMy')">我的作品</MenuItem>
							<MenuItem name="picCreate" @click.native="openMenu('picCreate')">发布作品</MenuItem>
                            <MenuItem name="picWait" @click.native="openMenu('picWait')">待审核</MenuItem>
							<MenuItem name="picFail" @click.native="openMenu('picFail')">审核失败</MenuItem>
                        </Submenu>
						<Submenu name="collection">
						    <template slot="title">
						        <Icon type="ios-analytics"></Icon>
						        收藏
						    </template>
						    <MenuItem name="coll" @click.native="openMenu('coll')">我的收藏</MenuItem>
						</Submenu>
                    </Menu>
                </Sider>
                <Layout :style="{padding: '0 24px 24px'}">
                    <Breadcrumb :style="{margin: '24px 0'}">
                        <BreadcrumbItem>Home</BreadcrumbItem>
                        <BreadcrumbItem>Components</BreadcrumbItem>
                    </Breadcrumb>
                    <Content :style="{padding: '24px', background: '#fff'}">
						
						<div v-if="menu.aboutMe">
							<AboutMe v-on:changeAvatar="changeAvatar" :userData="userData"></AboutMe>
						</div>
						
						<div v-if="menu.authUpload">
							<AuthUpload :uploadData="getUploadAuthData()"> </AuthUpload>
						</div>
						
						<div v-if="menu.authResult">
							<AuthResult :userId="userData.id"> </AuthResult>
						</div>
						
						<div v-if="menu.orderWait">
							<OrderWait v-on:updateBalance="updateBalance" :userData="userData"> </OrderWait>
						</div>
						
						<div v-if="menu.orderFinish">
							<OrderFinish v-on:updateBalance="updateBalance" :userData="userData"> </OrderFinish>
						</div>
						
						<div v-if="menu.orderCancel">
							<OrderCancel :userData="userData"> </OrderCancel>
						</div>
						
						<div v-if="menu.orderRefund">
							<OrderRefund :userData="userData"> </OrderRefund>
						</div>
						
						<div v-if="menu.picMy">
							<PicMy :userId="userData.id"> </PicMy>
						</div>
						
						<div v-if="menu.picCreate">
							<PicCreate :uploadData="getUploadPicData()"> </PicCreate>
						</div>
						
						<div v-if="menu.picWait">
							<PicWait :userId="userData.id"> </PicWait>
						</div>
						
						<div v-if="menu.picFail">
							<PicFail :userId="userData.id"> </PicFail>
						</div>
						
						<div v-if="menu.coll">
							<Coll :userId="userData.id"> </Coll>
						</div>
                    </Content>
                </Layout>
            </Layout>
    </div>

</template>


<script>
import Head from '../common/Head.vue'
import Login from '../common/Login.vue'
import OrderWait from './cpn/OrderWait.vue'
import OrderFinish from './cpn/OrderFinish.vue'
import OrderCancel from './cpn/OrderCancel.vue'
import OrderRefund from './cpn/OrderRefund.vue'
import PicCreate from './cpn/PicCreate.vue'
import PicWait from './cpn/PicWait.vue'
import PicFail from './cpn/PicFail.vue'
import PicMy from './cpn/PicMy.vue'
import AuthUpload from './cpn/AuthUpload.vue'
import AboutMe from './cpn/AboutMe.vue'
import AuthResult from './cpn/AuthResult.vue'
import Coll from './cpn/Coll.vue'

export default {
	data () {
		return {
			t1: false,
			td: '',
			menu: {
				// 个人信息
				aboutMe: false,
				// 身份认证
				authUpload: false,
				authResult: false,
				// 订单
				orderWait: false,
				orderFinish: false,
				orderCancel: false,
				orderRefund: false,
				// 作品
				picMy: false,
				picCreate: false,
				picWait: false,
				picFail: false,
				// 收藏
				coll: false
			}
		}
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
			this.$router.push('/tologin/profile');
		}
	},
	components: {
		'Head': Head,
		'Login': Login,
		'OrderWait': OrderWait,
		'OrderFinish': OrderFinish,
		'OrderCancel': OrderCancel,
		'OrderRefund': OrderRefund,
		'PicCreate': PicCreate,
		'PicWait': PicWait,
		'PicFail': PicFail,
		'PicMy': PicMy,
		'AuthUpload': AuthUpload,
		'AboutMe': AboutMe,
		'AuthResult': AuthResult,
		'Coll': Coll,
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
		openMenu (key) {
			for (var k in this.menu) {
				this.menu[k] = false;
			}
			this.menu[key] = true;
		},
		getUploadAuthData () {
			return {
				userId: this.userData.id,
			}
		},
		getUploadPicData () {
			var s = Math.floor(Math.random()*1000);
			return {
				userId: this.userData.id,
				seed: s,
			}
		},
		changeAvatar (url) {
			this.$refs.head.changeAvatar(url);
		},
		updateBalance (amount) {
			this.$refs.head.updateBalance(amount);
		}
	}
}
</script>
