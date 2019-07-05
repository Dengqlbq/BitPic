<template>
	<div>
		<div>
			<div>
				<Button long type="info" @click="getData">加载数据</Button>
			</div>
			
			<Divider />
			
			<!-- <div v-for="d in datas" :key="d.id"> -->
				<Card class="card" v-for="d in datas" :key="d.id" :padding=7 >
					<img :src="getUrl(d.userId)" @click="detailImage(d)"/>
				</Card>
			<!-- </div> -->
		</div>
		
		<Modal title="详情" v-model="detailImg" :styles="{top: '20px'}">
			<img :src="detailImgUrl" v-if="detailImg" class="detail-img">
			<div slot="footer">
				<Button type="success" @click="execute('accept')">通过</Button>
				<Button type="error" @click="showReason = true" style="float: left;">拒绝</Button>
			</div>
		</Modal>
		
		<Modal @on-ok="reasonOk" title="审核不通过" v-model="showReason">
			<Input v-model="currentReason" placeholder="输入不通过原因" autofocus @on-enter="reasonOk"/>
		</Modal>
	</div>
</template>

<style scoped>
	img {
		width: 200px;
		height: 300px;
		cursor: pointer;
	}
	
	.card {
		margin: 5px 10px 10px 10px;
		width: 216px;
		float: left;
	}
	
	.detail-img {
		position: relative;
		width: 100%;
		height: 700px
	}
</style>

<script>
import axios from 'axios'	
	
export default {
	data () {
		return {
			acceptUrl: 'http://localhost:8080/api/bitpic/admin/author/check/accept/',
			denyUrl: 'http://localhost:8080/api/bitpic/admin/author/check/deny/',
			
			dataUrl: 'http://localhost:8080/api/bitpic/admin/author/check/list/',
			page: 0,
			showReason: false,
			
			datas: [],
			
			detailImgUrl: '',
			detailImg: false,
			currentData: '',
			currentReason: '',
			
			OPERATION_SUCC_CODE: 4,
			
			imgServerUrl: 'http://localhost:8089/store/',
			defaultAuthPathFileName: '/authenticate/authenticate.png',
		}
	},
	mounted () {
		axios.default.withCredentials = true;
		this.getData();
	},
	methods: {
		getData () {
			var url = this.dataUrl + this.page;
			this.page ++;
			
			axios.get(url)
				.then(res => {
					if (res.data.code == this.OPERATION_SUCC_CODE) {
						var data = res.data.data;
						if (data.length == 0) {
							this.page = 0;
							this.$Message.info('没有更多数据');
							return ;
						}
						for (var k in data) {
							this.datas.push(data[k]);
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
		getUrl (userId) {
			return this.imgServerUrl + userId + this.defaultAuthPathFileName;
		},
		detailImage (data) {
			this.currentData = data;
			this.detailImg = true;
			this.detailImgUrl = this.getUrl(data.userId);
		},
		reasonOk () {
			this.showReason = false;
			this.execute('deny');
		},
		execute (result) {
			
			let params = new FormData();
			params.append('userId', this.currentData.userId);
			
			var url = result == 'accept' ? this.acceptUrl : this.denyUrl;
			if (result == 'deny') {
				params.append('reason', this.currentReason);
			}
			
			axios.patch(url, params)
				.then(res => {
					if (res.data.code != this.OPERATION_SUCC_CODE) {
						this.$Message.error('操作失败');
					} else {
						this.detailImg = false;
						const dataList = this.datas;
						this.datas.splice(dataList.indexOf(this.currentData), 1);
						this.currentData = '';
						
					}
				})
				.catch(err => {
					console.log(err);
					this.$Message.error('操作失败');
				})
		}
	}
}
</script>