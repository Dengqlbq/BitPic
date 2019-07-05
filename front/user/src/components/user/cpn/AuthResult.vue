<template>
	<div>
		<Card style="width: 200px; height: 300px; background: #CCCCCC;" v-if="showData">
			<div style="width: 200px; height: 100px;">
				<img src="../../../../build/fail.png" >
				<div style="position: absolute; bottom: 0; height: 200px;">
					<p>原因:</p>
					<p>{{data.reason}}</p>
				</div>
			</div>
		</Card>
		
	</div>
</template>

<style scoped>
	img {
		width: 100px;
		height: 70px;
		position: absolute;
		top: 0;
		left: 0;
	}
</style>

<script>
import axios from 'axios'
export default {
	data () {
		return {
			dataUrl: 'http://localhost:8080/api/bitpic/user/check/author/',
			data: '',
			showData: false,
			OPERATION_SUCCESS_CODE: 4,
			AUTH_DENY_CODE: 2,
		}
	},
	props: ['userId'],
	mounted () {
		axios.defaults.withCredentials = true;
		axios.get(this.dataUrl + this.userId)
			.then(res => {
				if (res.data.data == null) {
					this.$Message.info('没有数据');
					return ;
				}
				this.showData = true;
				this.data = res.data.data;
			})
			.catch(err => {
				this.$Message.error('获取数据失败')
			})
	}
}
</script>