<template>
	<div style="margin: 20px;">
		<Card style="height: 400px;">
			<div style="width: 200px; float: left;">
				<p>昵称: {{userData.name}} <a href="#" @click="chName = true">修改</a></p>
				<p>手机: {{userData.phone}} <a href="#" @click="changePhoneOrPassword(userData.phone, CH_TYPE.PHONE)">修改</a></p>
				<p>密码: ********** <a href="#" @click="changePhoneOrPassword(userData.phone, CH_TYPE.PASSWORD)">修改</a></p>
				<div v-show="userData.photographer">
					<span>摄影师认证: </span>
					<span v-if="userData.certificated">已认证 <Icon type="ios-lock-outline" color="green" /></span>
					<span v-else>未认证</span>
				</div>
			</div>
			
			<div style="float: right; text-align: center;">
				<img style="width: 100px; height: 100px; border-radius:50%; overflow:hidden;" :src="avatarUrl">
				<p><a href="#" @click="showCutter = true">修改头像</a></p>
			</div>
		</Card>
		
		<!-- 图片裁剪 -->
		<Avatar
			v-show="showCutter"
			return-type="file" 
			@cancel="showCutter = false"
			@enter="uploadAvatar" 
		>
		</Avatar>
		
		<!-- 修改用户名 -->
		<Modal @on-ok="changeUserInfo(CH_TYPE.NAME)" v-model="chName" width="360">
			<p slot="header" style="text-align:center">
				<img style="position: relative; height: 100%; width: auto;" src="../../../../build/logo.png" />
			</p>
			<Input v-model="tName" placeholder="输入新用户名" />
		</Modal>
		
		<!-- 验证码 -->
		<Modal v-model="chCode" width="360">
			<p slot="header" style="text-align:center">
				<img style="position: relative; height: 100%; width: auto;" src="../../../../build/logo.png" />
			</p>
			<Input v-model="tCode" placeholder="输入验证码" />
			<div slot="footer">
				<Button type="info" @click="changeSomething">确定</Button>
				<Button type="error" @click="chCode = false">取消</Button>
			</div>
		</Modal>
		
		<!-- 修改密码 -->
		<Modal v-model="chPassword" width="360">
			<p slot="header" style="text-align:center">
				<img style="position: relative; height: 100%; width: auto;" src="../../../../build/logo.png" />
			</p>
			<Input v-model="tNewPassword" type="password" placeholder="输入新密码" />
			<Input v-model="tConfirm" type="password" placeholder="再次输入密码" />
			<div slot="footer">
				<Button type="info" @click="changeUserInfo(CH_TYPE.PASSWORD)">确定</Button>
				<Button type="error" @click="chPassword = false">取消</Button>
			</div>
		</Modal>
		
		<!-- 修改手机号 -->
		<Modal v-model="chPhone" width="360">
			<p slot="header" style="text-align:center">
				<img style="position: relative; height: 100%; width: auto;" src="../../../../build/logo.png" />
			</p>
			<Input v-model="tPhone" placeholder="输入新手机号" />
			<Input v-model="tCode" placeholder="输入验证码" />
			<Button type="info" :class="{disabled: !this.canSendCode}" @click="sendCode(CODE_TYPE.PHONE)">{{codeBtnContent}}</Button>
			<div slot="footer">
				<Button type="info" @click="changeUserInfo(CH_TYPE.PHONE)">确定</Button>
				<Button type="error" @click="chPhone = false">取消</Button>
			</div>
		</Modal>
	</div>
</template>

<style scoped>
	p, span {
		font-size: 15px;
	}
	
	.disabled {
		background-color: #ddd;
		border: none;
		color:#57a3f3;
		cursor: not-allowed;
	}
	
</style>

<script>
import Avatar from './avatar-cutter/avatar-cutter.vue'
import axios from 'axios'
import sha256 from 'crypto-js/sha256'

export default {
	data () {
		return {
			// 修改相关
			chName: false,
			chPhone: false,
			chPassword: false,
			tName: '',
			tPhone: '',
			tNewPassword: '',
			tConfirm: '',
			something: '',
			changeUserInfoUrl: 'http://localhost:8080/api/bitpic/user/userInfo/update',
			CH_TYPE: {
				PHONE: 0,
				PASSWORD: 1,
				NAME: 2,
			},
			
			// 验证码
			code: '',
			chCode: false,
			tCode: '',
			canSendCode: 'true',
			waitTime: 60,
			codeBtnContent: '发送验证码',
			sendCodeUrl: 'http://localhost:8080/api/bitpic/admin/sms/sendCode',
			CODE_TYPE: {
				PHONE: 0,
				PASSWORD: 1
			},
			
			// 头像相关
			showCutter: false,
			changeAvatarUrl: 'http://localhost:8080/api/bitpic/user/file/upload/avatar',
			imgServerUrl: 'http://localhost:8089/images/',
			avatarFilePathName: '/avatar/avatar.png',
			avatarUrl: 'http://localhost:8089/images/avatar/default.png',
		}
	},
	mounted () {
		axios.defaults.withCredentials = true;
		if (this.userData.haveAvatar) {
			this.avatarUrl = this.imgServerUrl + this.userData.id + this.avatarFilePathName;
		}
	},
	props: ['userData'],
	components: {
		'Avatar': Avatar,
	},
	methods: {
		uploadAvatar (file) {
			let params = new FormData();
			params.append('file', file);
			params.append('userId', this.userData.id);
			
			axios.post(this.changeAvatarUrl, params)
				.then(res => {
					if (res.data.code == 4) {
						this.$Message.success('头像上传成功')
						this.showCutter = false;
						this.avatarUrl = file.url;
						this.$emit('changeAvatar', file.url);
						if (!this.userData.haveAvatar) {
							this.userData.haveAvatar = true;
							this.$store.commit('updateAvatar');
						}
					} else {
						this.$Message.error(res.data.message);
					}
				})
				.catch(err => {
					this.$Message.error('头像上传失败')
				})
		},
		changeUserInfo (type) {
			let params = new FormData();
			params.append('userId', this.userData.id);
			params.append('type', type);
			
			if (type == this.CH_TYPE.PHONE) {
				if (this.tCode != this.code) {
					this.$Message.error('验证码错误');
					return ;
				}
				this.chPhone = false;
				params.append('phone', this.tPhone);
			} else if (type == this.CH_TYPE.NAME) {
				this.chName = false;
				params.append('name', this.tName);
			} else {
				if (this.tNewPassword != this.tConfirm) {
					this.$Message.error('密码不一致');
					return ;
				}
				this.chPassword = false;
				params.append('newPassword', this.encrypt(this.tNewPassword));
				this.tNewPassword = '';
				this.tConfirm = '';
			}
			
			axios.patch(this.changeUserInfoUrl, params)
				.then(res => {
					if (res.data.code == 4) {
						this.$Message.success('修改成功')
						if (type == this.CH_TYPE.PHONE) {
							this.userData.phone = this.tPhone;
							this.$store.commit('updatePhone', this.tPhone);
						}
						if (type == this.CH_TYPE.NAME) {
							this.userData.name = this.tName;
							this.$store.commit('updateName', this.tName);
						} 
					} else {
						this.$Message.error(res.data.message);
					}
				})
				.catch (err => {
					console.log(err)
					this.$Message.error('修改失败	')
				})
		},
		changeSomething () {
			if (this.tCode == this.code) {
				if (this.something == this.CH_TYPE.PHONE) {
					this.chPhone = true;
				} else {
					this.chPassword = true;
				}
				
				this.chCode = false;
				this.tCode = '';
			} else {
				this.$Message.error('验证码错误')
			}
		},
		changePhoneOrPassword (phone, type) {
			this.something = type;
			this.realSendCode(phone, type);
			this.chCode = true;
		},
		realSendCode (phone, type) {
			let params = new FormData();
			params.append('phone', phone);
			params.append('type', type);
			
			axios.post(this.sendCodeUrl, params)
				.then(res => {
					if (res.data.code == 4) {
						this.code = res.data.data;
					} else {
						this.$Message.error("发送验证码失败");
						this.chCode = false;
					}	
				})
				.catch(err => {
					console.log(err)
					this.$Message.error("发送验证码失败");
					this.chCode = false;
				})
		},
		sendCode (type) {
			if (this.canSendCode) {
				this.realSendCode(this.tPhone, type);
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
		encrypt (value) {
			return sha256(value).toString();
		}
	}
}
</script>