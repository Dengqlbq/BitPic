<template>
	<div>
		<Scroll ref="scroll" :on-reach-bottom="reachBottom" height="700" :distance-to-edge="[0, -30]">
			<Card class="card" v-for="d in datas" :key="d.id" :padding=16>
				<img :src="d.url" @click="currentData = d; showDetail = true;">
				<Divider />
				<div>
					<p style="font-size: 20px; color: red;">总价: {{d.total}}</p>
					<p>创建于: {{d.createTime}}</p>
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
			imgServerUrl: 'http://localhost:8089/images/',
			defaultImgName: '0.png',
			
			datas: [],
			page: 0,
			orderIds: [],
			currentData: '',
			showDetail: false,
			
			OPERATION_SUC_CODE: 4,
			ORDER_STATUS: 2,
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
		}
	},
}
</script>