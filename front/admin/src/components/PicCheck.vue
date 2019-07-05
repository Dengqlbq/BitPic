<template>
	<div>
		<div>
			<div>
				<Button long type="info" @click="getData">加载数据</Button>
			</div>
			
			<Divider />
			
			<!-- <div v-for="d in datas" :key="d.id"> -->
				<Card class="card" v-for="d in datas" :key="d.id" :padding=7 >
					<img class="img" :src="getDefaultImgUrl(d)" @click="detailPic(d)" />
				</Card>
			<!-- </div> -->
		</div>
		
		<Modal title="详情" fullscreen v-model="showDetail">
			<div>
				<Card class="card" v-for="(f, index) in currentData.formats" :padding=7 :key="f">
					<img class="img" :src="getImgUrl(f, index, TYPE.IMG)" @click="showBiggerImg(getImgUrl(f, index, TYPE.IMG), index, TYPE.IMG)">
				</Card>
			</div>
			
			<Divider v-if="this.currentAuth" />
			
			<div v-if="this.currentAuth">
				<!-- <img :src="getImgUrl(f, index, TYPE.AUTH)" v-for="(f, index) in currentData.authFormats"> -->
				<Card class="card" v-for="(f, index) in currentData.authFormats" :padding=7 :key="f">
					<img class="img" :src="getImgUrl(f, index, TYPE.AUTH)" @click="showBiggerImg(getImgUrl(f, index, TYPE.AUTH), index, TYPE.AUTH)">
				</Card>
			</div>
			
			<div slot="footer">
				<Button type="success" @click="execute('accept')">通过</Button>
				<Button type="error" @click="showReason = true" style="float: left;">拒绝</Button>
			</div>
		</Modal>
		
		<Modal v-model="showDh" fullscreen>
			<img class="detail-img" :src="currentImgUrl" @click="showDh = false">
			<div slot="footer"></div>
		</Modal>
		
		<Modal v-model="showDv" :width=700>
			<img class="detail-img" :src="currentImgUrl" @click="showDv = false">
			<div slot="footer"></div>
		</Modal>
		
		<Modal @on-ok="reasonOk" title="审核不通过" v-model="showReason">
			<Input v-model="currentReason" placeholder="输入不通过原因" autofocus @on-enter="reasonOk"/>
		</Modal>
	</div>
</template>

<style scoped>
	.img {
		width: 300px;
		height: 200px;
		cursor: pointer;
	}
	
	.card {
		margin: 5px 10px 10px 10px;
		width: 316px;
		float: left;
	}
	
	.detail-img {
		position: relative;
		width: 100%;
		height: 100%;
	}
	
	
</style>

<script>
import axios from 'axios'	
	
export default {
	data () {
		return {
			acceptUrl: 'http://localhost:8080/api/bitpic/admin/pic/check/accept/',
			denyUrl: 'http://localhost:8080/api/bitpic/admin/pic/check/deny/',
			
			dataUrl: 'http://localhost:8080/api/bitpic/admin/pic/check/list/',
			page: 0,
			showReason: false,
			
			datas: [],
			
			detailImgUrl: '',
			detailImg: false,
			currentData: '',
			currentAuth: false,
			currentReason: '',
			currentImgDir: [],
			currentAuthDir: [],
			currentImgUrl: '',
			
			showDetail: false,
			showDh: false,
			showDv: false,
			
			DEFAULT_IMG_NAME: '0.',
			DEFAULT_AUTH_PATH: 'check/',
			OPERATION_SUCC_CODE: 4,
			TYPE: {
				IMG: 0,
				AUTH: 1,
			},
			DIRECTION: {
				H: 0,
				V: 1,
			},
			
			imgServerUrl: 'http://127.0.0.1:8089/store/',
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
		getDefaultImgUrl (data) {
			return this.imgServerUrl + data.userId + '/' + data.number + '/' + this.DEFAULT_IMG_NAME + data.formats[0];
		},
		getImgUrl (format, index, type) {
			var url = this.imgServerUrl + this.currentData.userId + '/' + this.currentData.number + '/';
			if (type == this.TYPE.AUTH) {
				url += this.DEFAULT_AUTH_PATH;
			} 
			url += index + '.' + format;
			
			return url;
		},
		detailPic (data) {
			this.currentData = data;
			this.currentAuth = !data.authFormats ? false : true;
			this.showDetail = true;
			
			this.currentImgDir = new Array(data.formats.length);
			for (var i = 0; i < data.formats.length; i ++) {
				var url = this.getImgUrl(data.formats[i], i, this.TYPE.IMG);
				this.setDirection(url, i, this.TYPE.IMG);
			}
			
			if (this.currentAuth) {
				this.currentAuthDir = new Array(data.authFormats.length);
				for (var i = 0; i < data.authFormats.length; i ++) {
					var url = this.getImgUrl(data.authFormats[i], i, this.TYPE.AUTH);
					this.setDirection(url, i, this.TYPE.AUTH);
				}
			}
		},
		reasonOk () {
			this.showReason = false;
			this.execute('deny');
		},
		execute (result) {
			
			let params = new FormData();
			params.append('userId', this.currentData.userId);
			params.append('number', this.currentData.number);
			
			var url = result == 'accept' ? this.acceptUrl : this.denyUrl;
			if (result == 'deny') {
				params.append('reason', this.currentReason);
			}
			
			axios.patch(url, params)
				.then(res => {
					if (res.data.code != this.OPERATION_SUCC_CODE) {
						this.$Message.error('操作失败');
					} else {
						this.showDetail = false;
						const dataList = this.datas;
						this.datas.splice(dataList.indexOf(this.currentData), 1);
						this.currentData = '';
						this.currentAuthDir = [];
						this.currentImgDir = [];
					}
				})
				.catch(err => {
					console.log(err);
					this.$Message.error('操作失败');
				})
		},
		setDirection (url, index, type){

			var img = new Image();
			img.src = url;

			if(img.complete){
				if (type == this.TYPE.IMG) {
					this.currentImgDir[index] = img.width > img.height ? this.DIRECTION.H : this.DIRECTION.V;

				} else {
					this.currentAuthDir[index] = img.width > img.height ? this.DIRECTION.H : this.DIRECTION.V;
				}
			}

		},
		showBiggerImg (url, index, type) {
			this.currentImgUrl = url;
			if (type == this.TYPE.IMG) {
				if (this.currentImgDir[index] == this.DIRECTION.H) {
					this.showDh = true;
				} else {
					this.showDv = true;
				}
			} else {
				if (this.currentAuthDir[index] == this.DIRECTION.H) {
					this.showDh = true;
				} else {
					this.showDv = true;
				}
			}
		}
	}
}
</script>