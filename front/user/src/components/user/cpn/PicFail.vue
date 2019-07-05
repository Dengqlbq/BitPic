<template>
	<div>
		<Scroll ref="scroll" :on-reach-bottom="reachBottom" height="700" :distance-to-edge="[0, -30]">
			<Card class="card" :padding=6 v-for="d in datas" :key="d.id">
				<div style="position: relative;">
				<img class="fail-img" src="../../../../build/fail.png">
				<img :src="d.url">
				<p>审核于: {{d.updateTime}}</p>
				<p style="color: red;">原因: {{d.reason}}</p>
				</div>
			</Card>
		</Scroll>
	</div>
</template>

<style scoped>
	img {
		width: 300px;
		height: 200px;
		cursor: pointer;
	}
	
	.fail-img {
		width: 200px;
		height: 140px;
		position: absolute;
		top: 30px;
		left: 45px;
	}
	
	.card {
		margin: 5px 10px 10px 10px;
		width: 316px;
		float: left;
	}
</style>

<script>
import axios from 'axios'
	
	
export default {
	data () {
		return {
			dataUrl: 'http://localhost:8080/api/bitpic/user/check/pic/',
			imgServerUrl: 'http://localhost:8089/images/',
			defaultImgName: '0.png',
			
			datas: [],
			page: 0,
			numbers: [],
			
			STATUS_CODE: 2,
		}
	},
	props: ['userId'],
	mounted () {
		axios.defaults.withCredentials = true;
		this.getData();
	},
	methods: {
		getData () {
			
			var url = this.dataUrl + this.userId + '/' + this.STATUS_CODE + '/' + this.page;
			this.page ++;
			
			axios.get(url)
				.then(res => {
					if (res.data.code == 4) {
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
							
								var url = this.imgServerUrl + this.userId + '/' + data[k].number + '/' + this.defaultImgName;
								data[k].url = url;
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
		}
	},
}
</script>