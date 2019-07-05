<template>
	<div>
	<Modal v-model="isShow" width="360">
		<p slot="header" style="text-align:center;">
			<img style="position: relative; height: 100%; width: auto;" src="../../../build/logo.png" />
		</p>
		<div style="text-align:center">
			
			<!-- 登陆表单 -->
			<Form v-show="isLogin" ref="loginForm" :model="loginForm" :rules="loginRule">
				<FormItem prop="loginPhone">
					<Input type="text" v-model="loginForm.loginPhone" placeholder="Phone" autofocus>
						<Icon type="ios-call" slot="prepend"></Icon>
					</Input>
				</FormItem>
				
				<FormItem prop="loginPassword">
					<Input type="password" v-model="loginForm.loginPassword" placeholder="Password" @on-enter="handle1">
						<Icon type="ios-lock-outline" slot="prepend"></Icon>
					</Input>
				</FormItem>
			</Form>
			
			<!-- 注册表单 -->
			<Form v-show="!isLogin" ref="regForm" :model="regForm" :rules="regRule">
				<FormItem prop="regPhone">
					<Input type="text" v-model="regForm.regPhone" placeholder="Phone" autofocus>
						<Icon type="ios-call" slot="prepend"></Icon>
					</Input>
				</FormItem>
				
				<FormItem prop="regCode">
					<Input placeholder="输入验证码" v-model="regForm.regCode" style="position: relative; width: 70%;" />
					<Button @click="sendCode" :class="{disabled: !this.canSendCode}" type="info" style="position: relative; width: 28.5%;">{{codeBtnContent}}</Button>
				</FormItem>
				
				<FormItem prop="regUserName">
					<Input type="text" v-model="regForm.regUserName" placeholder="Username">
						<Icon type="ios-person-outline" slot="prepend"></Icon>
					</Input>
				</FormItem>
				
				<FormItem prop="regPassword">
					<Input type="password" v-model="regForm.regPassword" placeholder="Password">
						<Icon type="ios-lock-outline" slot="prepend"></Icon>
					</Input>
				</FormItem>
				
				<FormItem prop="regConfirm">
					<Input type="password" v-model="regForm.regConfirm" placeholder="Confirm">
						<Icon type="ios-lock" slot="prepend"></Icon>
					</Input>
				</FormItem>
				
				<FormItem prop="regAgree">
					<Checkbox v-model="regForm.agree"><a href="#">{{protocalText}}</a></Checkbox>
				</FormItem>
			</Form>
		</div>
		<div slot="footer">
			<Button ghost type="info" v-show="!isLogin" style="float: left;" @click="reset">
				<Icon type="ios-arrow-back" />
			</Button>
			<Button ghost type="info" v-show="isLogin" style="float: left;">
				忘记密码
			</Button>
			<Button ghost type="success" @click="handle1">{{ bt1 }}</Button>
			<Button ghost type="info" @click="handle2">{{ bt2 }}</Button>
			<!-- <Button ghost type="info" @click="testFun">验证</Button> -->
		</div>
	</Modal>
	
	<Modal
        v-model="photographers"
        title="摄影师注册"
		@on-ok="ok">
        <Collapse>
        	<Panel name="1">
        		摄影师协议
        		<p slot="content">以下称网站BitPic为甲方，摄影师为乙方
        		有个朋友给我留言，说生活总是忙碌。希拉里传记在桌上躺了很久，一直都没有看。想做的事和应该做的事总是那么矛盾。
        
        		没错，我们生活在一个混乱的年代。
        
        		每时每刻，无数琐事侵蚀着我们的生活。就在你打开这篇文章的时候，也许，你的客户又打进一个电话。那边的房租就要到期，下个月的英语考试近在眼前。淘宝京东的购物节，又占据了你一个晚上。
        
        		一边奔波于眼前的苟且，一边梦想着远方的田野。而你的手机里，还躺着未曾追完的电视剧。周末的舞蹈班和演讲班，一样都不能落下。仿佛只有这样用力地生活，才是通往成功的唯一出路。
        
        		然而忙碌并没有让我们生活得更好，反而让一切更糟。大部分人的碌碌无为，并不是因为做得太少，而恰恰是因为做得太多。
        
        		最重要的事只有一件
        
        		我曾经也是个忙忙碌碌的人，四处涉猎从不停息。工作中的完美主义让我如履薄冰，生怕一个不经意的纰漏，就会显示自己的无能。
        
        		直到多年后我才明白，很多我们孜孜以求的事情，其实并没有那么重要。著名的二八法则告诉我们，大部分人的主要成就，都来自于那些少数的重要事情。
        
        		我们部门去年招了一个新人，负责产品的采购计划和执行。然而没到两个月，她就提出了离职。原因是这份工作实在太累，她天天加班，已经没有了个人生活。
        
        		这个说法让我们惊讶不已。她的工作量其实并不大，又怎么会天天加班呢？
        
        		后来这份工作由一位管理培训生接手。他做起来轻松自如，毫不费力。
        
        		问起原因，他说，之前那个天天加班的人，是个吹毛求疵的完美主义，每一封非正式的邮件，都要反复推敲。这样事无巨细的工作方式，不累才怪。
        
        		而这位管理培训生，经过专业的考量和分析后，用大部分时间去做了一个项目。这个项目给公司节省了200万美金。他也因此获得了年度贡献奖。
        
        		优秀和平庸的差距，往往只在于一件事。优秀的人能把这一件事做好，而平庸的人做了太多事情却依然碌碌无为。
        
        		因为我们的时间和精力都是有限的。
        
        		成功的秘诀就在于一件事。你是否能把这件事情做好，决定了你能够走多远。肯德基做好了一份食谱，成为了一家伟大的公司。乔布斯做好了一款手机，颠覆了这个时代的传播方式。
        
        		在这个混乱而充满诱惑的时代里，我们并不需要做太多。只需要做好一件事情，你就能找到撬动整个地球的支点。
        
        		想做的事太多，应该做的事也太多。然而最重要的事情永远只有一件。
        
        		借用美国作家奥格·曼狄诺的一句名言：“一次只做一件事的人，才会领先于这个世界。” 
        		继续注册则视为同意协议</p>
        	</Panel>
        </Collapse>
		
		<p style="font-size: 20px;">以下为认证文件模板，可在注册后在个人中心->身份->提交认证 处下载然后提交</p>
		<img src="../../../build/a0.jpg" style="width: 500px;">
		
		<p>如需继续请点击确定</p>
    </Modal>
	</div>
</template>

<style>
	.disabled {
		background-color: #ddd;
		border: none;
		color:#57a3f3;
		cursor: not-allowed;
	}
</style>

<script>
import sha256 from 'crypto-js/sha256'
import axios from 'axios'
export default {
	data () {
		const validateConfirm = (rule, value, callback) => {
			if (this.isLogin) {
				callback();
			} else if (value == '') {
				callback(new Error('请再次输入密码'));
			} else if (value != this.regForm.regPassword) {
				callback(new Error('密码不一致'));
			} else {
				callback();
			}
		};
		const validateUserName = (rule, value, callback) => {
			if (this.isLogin) {
				callback();
			} else if (value == '') {
				callback(new Error('请输入用户名'));
			} else {
				callback();
			}
		};
		const validateAgree = (rule, value, callback) => {
			if (!this.isLogin && !this.regForm.agree) {
				callback(new Error('请同意用户协议'));
			} else {
				callback();
			}
		};
		const validateCode = (rule, value, callback) => {
			if (value != this.code) {
				callback(new Error('验证码错误'))
			} else {
				callback();
			}
		}
		return {
			// 初始化数据
			isShow: false,
			bt1: '登陆',
			bt2: '注册',
			isLogin: true,
			protocalText: '同意用户协议',
			
			photographers: false,
			
			// 操作成功结果代码
			loginCode: 0,
			regCode: 2,
			operationCode: 4,
			
			// 验证码相关
			code: '',
			codeBtnContent: '发送验证码',
			waitTime: 60,
			canSendCode: true,
			
			// api
			loginUrl: 'http://localhost:8080/api/bitpic/user/authenticate/login',
			registerUrl: 'http://localhost:8080/api/bitpic/user/authenticate/register',
			codeUrl: 'http://localhost:8080/api/bitpic/admin/sms/sendCode',
			
			// 表单相关
			loginForm: {
				loginPhone: '',
				loginPassword: ''
			},
			regForm: {
				regPhone: '',
				regCode: '',
				regUserName: '',
				regPassword: '',
				regConfirm: '',
				regAgree: false
			},
			regType: 0,
			TYPE: {
				U: 0,
				P: 1,
			},
			loginRule: {
				loginPhone: [
					{ required: true, message: '请输入手机号', trigger: 'blur'},
					{ type: 'string', min: 1, max: 11, message: '请输入正确手机号', trigger: 'blur'}
				],
				loginPassword: [
					{ required: true, message: '请输入密码', trigger: 'blur' },
                    { type: 'string', min: 6, message: '密码长度不小于6位', trigger: 'blur' }
				],
			},
			regRule: {
				regPhone: [
					{ required: true, message: '请输入手机号', trigger: 'blur'},
					{ type: 'string', min: 1, max: 11, message: '请输入正确手机号', trigger: 'blur'}
				],
				regUserName: [
					{ required: true, message: '请输入用户名', trigger: 'blur'},
					{ type: 'string', min: 3, message: '用户名长度不能小于3位', trigger: 'blur'}
				],
				regPassword: [
					{ required: true, message: '请输入密码.', trigger: 'blur' },
					{ type: 'string', min: 6, message: '密码长度不得小于6位', trigger: 'blur' }
				],
				regConfirm: [
					{ validator: validateConfirm, trigger: 'blur' }
				],
				regAgree: [
					{ validator: validateAgree, trigger: 'blur' }
				],
				regCode: [
					{ validator: validateCode, trigger: 'blur' }
				]
			}
		}
	},
	methods: {
		show () {
			this.isShow = true;
			axios.defaults.withCredentials = true;
		},
		reset () {
			this.isLogin = true;
			this.bt1 = '登陆';
			this.bt2 = '注册';
			this.loginForm.loginPhone = '';
			this.loginForm.loginPassword = '';
			this.regForm.regPhone = '';
			this.regForm.regCode = '';
			this.regForm.regPassword = '';
			this.regForm.regConfirm = '';
			this.regForm.regAgree = false;
		},
		handle1 () {
			if (this.isLogin) {
				this.login();
			} else {
				this.register();
			}
		},
		handle2 () {
			if (this.isLogin) {
				this.isLogin = false;
				this.bt1 = "注册";
				this.bt2 = "摄影师注册";
			} else {
				this.photographers = true;
			}
		},
		login () {
			this.$refs['loginForm'].validate((valid) => {
				if (valid) {
					var params = new URLSearchParams();
					params.append('phone', this.loginForm.loginPhone);
					params.append('password', this.encrypt(this.loginForm.loginPassword));
					
					
					axios.post(this.loginUrl, params)
						.then(res => {
							var data = res.data;
							if (data.code == this.loginCode) {
								this.$emit('loginSuc', data.data);
								this.isShow = false;
							} else {
								this.$Message.error('登陆失败');
							}
						})
						.catch(err => {
							console.log(err)
							this.$Message.error('登陆失败');
						})
				}
			})
		},
		register () {
			this.$refs['regForm'].validate((valid) => {
				if (valid) {
					var params = new URLSearchParams();
					params.append('phone', this.regForm.regPhone);
					params.append('password', this.encrypt(this.regForm.regPassword));
					params.append('name', this.regForm.regUserName);
					params.append('type', this.regType);
					
					axios.post(this.registerUrl, params)
						.then(res => {
							var data = res.data;
							if (data.code == this.regCode) {
								this.$emit('regSuc', data.data);
								this.isShow = false;
							} else {
								console.log(data)
								this.$Message.error(data.message);
							}
						})
				}
			})
		},
		sendCode () {
			if (this.canSendCode) {
				// TODO 验证手机号
				this.realSendCode();
				this.canSendCode = false;
				let clock = window.setInterval(() => {
					this.codeBtnContent = '等待' + this.waitTime + 's';
					this.waitTime --;
					if (this.waitTime <= 0) {
						window.clearInterval(clock);
						this.codeBtnContent = '发送验证码';
						this.waitTime = 60;
						this.canSendCode = true;
					}
				}, 1000);
			}
		},
		realSendCode () {
			let params = new URLSearchParams();
			params.append('phone', this.regForm.regPhone);
			params.append('type', 2);
			
			axios.post(this.codeUrl, params)
				.then(res => {
					var data = res.data;
					if (data.code == this.operationCode) {
						this.code = data.data;
					} else {
						this.$Message.error("发送验证码失败");
					}	
				})				
		},
		encrypt (value) {
			return sha256(value).toString();
		},
		ok () {
			this.protocalText = '注册为摄影师';
			this.regType = this.TYPE.P;
			this.photographers = false;
		}
	}
}

	</script>