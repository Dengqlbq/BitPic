<template>
	<div>
		<Scroll ref="scroll" :on-reach-bottom="reachBottom" height="700" :distance-to-edge="[0, -30]">
			<Card class="card" :padding=6 v-for="(d, index) in datas" :key="d.id">
				<img :src="d.url" @click="redirect(index)">
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
			imgServerUrl: 'http://localhost:8089/images/',
			defaultImgName: '0.png',
			
			dataUrl: 'http://localhost:8080/api/bitpic/user/collections/list/',
			page: 0,
			numbers: [],
			
			datas: [],
		}
	},
	props: ['userId'],
	mounted () {
		axios.defaults.withCredentials = true;
		this.getData();
		this.getData();
	},
	methods: {
		getData () {
			var url = this.dataUrl + this.userId + '/' + this.page;
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
								
								var url = this.imgServerUrl + data[k].authorId + '/' + data[k].number + '/' + this.defaultImgName;
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
		},
		redirect (index) {
			this.$router.push('/detail/' + this.datas[index].number);
		}
	},
}
</script>