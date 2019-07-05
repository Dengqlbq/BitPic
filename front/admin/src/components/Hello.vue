<template>
	<div>
		<div style="text-align: center; margin: 100px;">
			<h1>BitPic管理后台</h1>
		</div>
		<div>
			<div style="margin: 20px auto; width: 300px;">
				<Form ref="form" :model="form" :rules="rule">
					<FormItem prop="phone">
						<Input type="text" v-model="form.phone" placeholder="Phone" autofocus>
							<Icon type="ios-person-outline" slot="prepend"></Icon>
						</Input>
					</FormItem>
					<FormItem prop="password">
						<Input type="password" v-model="form.password" placeholder="Password" @on-enter="login">
							<Icon type="ios-lock-outline" slot="prepend"></Icon>
						</Input>
					</FormItem>
					<FormItem>
						<Button type="primary" long @click="login">登陆</Button>
					</FormItem>
				</Form>
			</div>
		</div>
	</div>
</template>

<style scoped>
	
</style>

<script>
import axios from 'axios'
import sha256 from 'crypto-js/sha256'

export default {
	data () {
		return {
			loginUrl: 'http://localhost:8080/api/bitpic/admin/valid/login',
			redirectUrl: '/manager',
			LOGIN_SUCC_CODE: 0,
			
			form: {
				phone: '',
				password: '',
			},
			rule: {
                    phone: [
                        { required: true, message: '请输入手机号', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { type: 'string', min: 6, message: '密码不短于6位', trigger: 'blur' }
                    ]
            },
		}
	},
	methods: {
		login () {
			this.$refs['form'].validate((valid) => {
				if (valid) {
					axios.defaults.withCredentials = true;
					
					let params = new FormData();
					params.append('phone', this.form.phone);
					params.append('password', this.encrypt(this.form.password));
					
					axios.post(this.loginUrl, params)
						.then(res => {
							if (res.data.code == this.LOGIN_SUCC_CODE) {
								this.$Message.success('登陆成功');
								this.$router.push(this.redirectUrl);
							}
							else {
								this.$Message.error('登陆失败');
							}
						})
						.catch(err => {
							console.log(err);
							this.$Message.error('登陆失败');
						})
				} else {
					this.$Message.error('数据校验失败')
				}
			})
		},
		encrypt (value) {
			return sha256(value).toString();
		}
	}
}
</script>