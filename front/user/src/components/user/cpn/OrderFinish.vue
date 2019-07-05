<template>
	<div>
		<Scroll ref="scroll" :on-reach-bottom="reachBottom" height="700" :distance-to-edge="[0, -30]">
			<Card class="card" v-for="d in datas" :key="d.id" :padding=16>
				<img :src="d.url" @click="currentData = d; showDetail = true;">
				<Divider />
				<div>
					<p style="font-size: 20px; color: red;">总价: {{d.total}}</p>
					<p>创建于: {{d.createTime}}</p>
					<Divider />
					<Button type="error" :disabled="d.hadDownload" @click="showRefund = true; currentData = d;">退款</Button>
					<Button type="success" style="float: right;" @click="download(d)">下载</Button>
				</div>
			</Card>
		</Scroll>
		
		<Modal
			v-model="showDetail"
			title="订单详情">
			<Card v-for="detail in currentData.orderDetails" :key="detail.id">
				<img :src="detail.url">
				<span style="font-size: 20px; color: #1F9AF5;">{{getAuthType(detail.type)}}</span>
				<span style="font-size: 20px; color: red;">{{detail.price}}元</span>
			</Card>
			<div slot="footer"></div>
		</Modal>
		
		<Modal
			v-model="showRefund"
			title="警告"
			@on-ok="refund">
			<p>确定要退款吗？</p>
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
			dataUrl: 'http://localhost:8080/api/bitpic/user/order/list/',
			downloadUrl: 'http://localhost:8080/api/bitpic/user/file/download/',
			refundUrl: 'http://localhost:8080/api/bitpic/user/order/refund/',
			imgServerUrl: 'http://localhost:8089/images/',
			defaultImgName: '0.png',
			
			datas: [],
			page: 0,
			orderIds: [],
			currentData: '',
			showDetail: false,
			
			showRefund: false,
			
			OPERATION_SUC_CODE: 4,
			ORDER_STATUS: 0,
			AUTH_TYPE: {
				S: 0,
				P: 1,
			},
		}
	},
	props: ['userData'],
	mounted () {
		axios.defaults.withCredentials = true;
		this.getData();
	},
	methods: {
		getData () {
			
			var url = this.dataUrl + this.userData.id + '/'+  this.ORDER_STATUS + '/' + this.page;
			this.page ++;
			
			axios.get(url)
				.then(res => {
					if (res.data.code == 4) {
						var data = res.data.data;
						if (!data || data.length == 0) {
							this.page = 0;
							this.$Message.info('没有更多数据');
							return ;
						}
						var count = 0;
						for (var k in data) {
							if (!this.exists(data[k].id)) {
								this.orderIds.push(data[k].id);
								
								for (var i in data[k].orderDetails) {
									var od = data[k].orderDetails[i];
									data[k].orderDetails[i].url = this.imgServerUrl + od.authorId + '/' + od.number + '/' + this.defaultImgName;
								}
								
								data[k].url = data[k].orderDetails[0].url;
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
		exists (id) {
			for (var n in this.orderIds) {
				if (this.orderIds[n] == id) {
					return true;
				}
			}
			return false;
		},
		getAuthType (type) {
			return type == this.AUTH_TYPE.S ? '标准授权' : '扩展授权';
		},
		download (data) {
			data.hadDownload = true;
			var url = this.downloadUrl + this.userData.id +  '/' + data.id;
			window.location.href = url;
		},
		refund () {
			let params = new FormData();
			params.append('userId', this.userData.id);
			params.append('orderId', this.currentData.id);
			
			axios.patch(this.refundUrl, params)
				.then(res => {
					if (res.data.code == this.OPERATION_SUC_CODE) {
						this.$Message.info('退款成功');
						this.$emit('updateBalance', this.currentData.total);
						this.showRefund = false;
						this.datas.splice(this.datas.indexOf(this.currentData), 1);
					} else {
						this.$Message.error(res.data.message);
					}
				})
				.catch(err => {
					console.log(err);
					this.$Message.error('退款失败');
				})
		}
	},
}
</script>