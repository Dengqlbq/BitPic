<template>
	<div>
		<Scroll ref="scroll" :on-reach-bottom="reachBottom" height="700" :distance-to-edge="[0, -30]">
			<Card class="card" v-for="d in datas" :key="d.id" :padding=16>
				<img :src="d.url" @click="redirect(d.number)">
				<Divider />
				<div>
					<pre>标准授权: {{d.priceStandard}} 出售: {{d.saleStandard}} 状态: {{getStatus(d.status)}}</pre>
					<pre>扩展授权: {{d.pricePlus}} 出售: {{d.salePlus}}</pre>
					<p style="margin-bottom: 10px;"> {{d.keywords}} </p>
					<Button type="info" long @click="currentData = d; showUpdate = true;">更新</Button>
				</div>
			</Card>
		</Scroll>
		
		<Modal @on-ok="update" v-model="showUpdate" title="更新作品信息">
			<Input  v-model="currentPriceStandard" style="position: relative; width: 100%;">
				<span slot="prepend">标准授权:</span>
				<span slot="append"><Icon type="logo-yen" /></span>
			</Input>
			<Input  v-model="currentPricePlus" style="position: relative; width: 100%;">
				<span slot="prepend">扩展授权:</span>
				<span slot="append"><Icon type="logo-yen" /></span>
			</Input>
			<Input v-model="currentKeywords" style="position: relative; width: 100%;">
				<span slot="prepend">关键词:</span>
				<span slot="append">#号分隔</span>
			</Input>
			<i-switch size="large" @on-change="changeStatus">
				<span slot="open">上架</span>
				<span slot="close">下架</span>
			</i-switch>
		</Modal>
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
		width: 332px;
		float: left;
	}
</style>

<script>
import axios from 'axios'
	
	
export default {
	data () {
		return {
			dataUrl: 'http://localhost:8080/api/bitpic/user/picInfo/list/',
			imgServerUrl: 'http://localhost:8089/images/',
			defaultImgName: '0.png',
			
			datas: [],
			page: 0,
			numbers: [],
			
			updateUrl: 'http://localhost:8080/api/bitpic/user/picInfo/update',
			OPERATION_SUC_CODE: 4,
			showUpdate: false,
			currentData: '',
			currentPriceStandard: '',
			currentPricePlus: '',
			currentKeywords: '',
			currentStatus: '',
			STATUS: {
				U: 0,
				D: 1,
			}
		}
	},
	props: ['userId'],
	mounted () {
		axios.defaults.withCredentials = true;
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
		getImgUrl (number) {
			return this.imgServerUrl + this.userId + '/' + number + '/' + this.defaultImgName;
		},
		update () {
			let params = new FormData();
			params.append('userId', this.userId);
			params.append('number', this.currentData.number);
			params.append('priceStandard', this.currentPriceStandard);
			params.append('pricePlus', this.currentPricePlus);
			params.append('keywords', this.currentKeywords);
			params.append('status', this.currentStatus);
			
			axios.patch(this.updateUrl, params)
				.then(res => {
					if (res.data.code == this.OPERATION_SUC_CODE) {
						this.$Message.info('更新成功');
						this.currentData.priceStandard = this.currentPriceStandard;
						this.currentData.pricePlus = this.currentPricePlus;
						this.currentData.keywords = this.currentKeywords.split('#');
						this.currentData.status = this.currentStatus;
						this.currentPriceStandard = '';
						this.currentPricePlus = '';
						this.currentKeywords = '';
						this.currentStatus = '';
					} else {
						this.$Message.error(res.data.message);
					}
				})
				.catch(err => {
					console.log(err);
					this.$Message.error('更新失败');
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
		changeStatus (value) {
			this.currentStatus = value ? this.STATUS.U : this.STATUS.D;
		},
		getStatus (status) {
			return status == this.STATUS.U ? '在架' : '下架';
		},
		redirect (number) {
			this.$router.push('/detail/' + number);
		}
	},
}
</script>