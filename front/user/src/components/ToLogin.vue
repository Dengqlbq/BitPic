<template>
	<div>
		<Head v-on:notLogin="notLogin" ref="head"></Head>
		<Login v-on:loginSuc="loginSuc" v-on:regSuc="regSuc" ref="login"></Login>
	</div>
</template>

<style scoped>
	
</style>

<script>
import Login from './common/Login.vue'
import Head from './common/Head.vue'

export default {
	data () {
		return {

		}
	},
	mounted () {
		this.$refs.login.show();
	},
	components: {
		'Login': Login,
		'Head': Head,
	},
	methods: {
		notLogin () {
			this.$refs.login.show();
		},
		loginSuc (userData) {
			this.$store.commit('loginSuc', userData);
			this.$refs.head.setUserData(userData);
			this.$router.push(this.getRedirectUrl());
		},
		regSuc (userData) {
			this.$store.commit('loginSuc', userData);
			this.$refs.head.setUserData(userData);
			this.$router.push(this.getRedirectUrl());
		},
		getRedirectUrl () {
			var b = this.$route.params.before;
			if (b == 'profile') {
				return '/profile';
			} else if (b == 'wishes') {
				return '/wishes';
			} else {
				return '/detail/' + b;
			}
		}
	}
}
</script>