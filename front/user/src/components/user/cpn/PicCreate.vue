<template>
	<div>
		<!-- 图片上传框 -->
		<div>
			<div style="position: relative; width: 50%; float: left;">
				<Upload
					ref="upload"
					:format="['jpg','jpeg','png']"
					:on-format-error="handleFormatError"
					:before-upload="beforeUpload"
					multiple
					type="drag"
					action="#">
					<div style="padding: 20px 0">
						<Icon type="ios-camera-outline" size="52" style="color: #3399ff"></Icon>
						<p>上传作品</p>
					</div>
				</Upload>
			</div>
			
			<div style="position: relative; width: 50%; float: left;">
				<Upload
					ref="uploadAuth"
					:format="['jpg','jpeg','png']"
					:on-format-error="handleFormatError"
					:before-upload="beforeUploadAuth"
					multiple
					type="drag"
					action="#">
					<div style="padding: 20px 0">
						<Icon type="ios-document-outline" size="52" style="color: #3399ff"/>
						<p>上传授权</p>
					</div>
				</Upload>
			</div>
		</div>
		
		<!-- 图片预览框 -->
		<Modal title="预览" v-model="previewImg">
			<img :src="previewImgUrl" v-if="previewImg" style="width: 100%;">
		</Modal>
		
		<!-- 待上传图片列表 -->
		<div class="img-preview-list" v-for="file in files" :key="file.id">
			<img :src="file.url">
			<div class="img-cover">
				<div class="icon-box">
					<Icon class="icon" type="ios-eye-outline" size="48" @click.native="preImg(file.url)"></Icon>
					<Icon class="icon" type="ios-trash-outline" size="48" @click.native="delImg(file, TYPE.IMG)"></Icon>
				</div>
			</div>
		</div>
		
		<Divider v-show="haveAuth" />
		
		<div class="img-preview-list" v-for="file in authFiles" :key="file.id">
			<img :src="file.url">
			<div class="img-cover">
				<div class="icon-box">
					<Icon class="icon" type="ios-eye-outline" size="48" @click.native="preImg(file.url)"></Icon>
					<Icon class="icon" type="ios-trash-outline" size="48" @click.native="delImg(file, TYPE.AUTH)"></Icon>
				</div>
			</div>
		</div>
		
		<Divider v-show="haveImg" />
		
		<!-- 信息填写 -->
		<div v-show="haveImg">
			<div style="position: relative; width: 50%; float: left;">
				<Input  v-model="priceStandard" style="position: relative; width: 100%;">
					<span slot="prepend">标准授权:</span>
					<span slot="append"><Icon type="logo-yen" /></span>
				</Input>
			</div>
			<div style="position: relative; width: 50%; float: left;">
				<Input  v-model="pricePlus" style="position: relative; width: 100%;">
					<span slot="prepend">扩展授权:</span>
					<span slot="append"><Icon type="logo-yen" /></span>
				</Input>
			</div>
			<Input v-model="keyword" style="position: relative; width: 100%;">
				<span slot="prepend">关键词:</span>
				<span slot="append">#号分隔</span>
			</Input>
		</div>
		
		<Divider v-show="haveImg" />
		
		<div style="text-align: center;" v-show="haveImg">
			<Button type="info" @click="upload" style="width: 150px;">上传</Button>
		</div>
		
	</div>
</template>

<style scoped>
    .img-preview-list {
        display: inline-block;
        width: 200px;
        height: 200px;
        text-align: center;
        line-height: 60px;
        border: 1px solid transparent;
        border-radius: 4px;
        overflow: hidden;
        background: #fff;
        position: relative;
        box-shadow: 0 1px 1px rgba(0,0,0,.2);
        margin-right: 4px;
    }
	
    .img-preview-list img {
        width: 100%;
        height: 100%;
    }
	
    .img-cover {
        display: none;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(0,0,0,.6);
    }
	
    .img-preview-list:hover .img-cover {
        display: block;
    }
	
    .img-cover i {
        color: #fff;
        font-size: 20px;
        cursor: pointer;
        margin: 0 2px;
    }
	
	.icon-box {
		position: relative;
		top: 32%;
	}
	
	.icon:hover {
		color: #1F9AF5;
	}
</style>

<script>
	import axios from 'axios'
    export default {
        data () {
            return {
                files: [],
				authFiles: [],
                previewImgUrl: '',
                previewImg: false,
				haveImg: false,
				haveAuth: false,
				toUpAuth: true,
				
				// 上传相关
				uploadPicUrl: 'http://localhost:8080/api/bitpic/user/file/upload/pictures',
				uploadPicInfoUrl: 'http://localhost:8080/api/bitpic/user/picInfo/create',
				priceStandard: 0,
				pricePlus: 0,
				keyword: '',
				TYPE: {
					IMG: 0,
					AUTH: 1,
				}
            }
        },
		props: ['uploadData'],
		mounted () {
			this.$Message.info('点击选择文件或拖拽文件上传')
		},
        methods: {
			reset () {
				this.files = [];
				this.authFiles = [];
				this.previewImgUrl = '';
				this.previewImg = false;
				this.haveImg = false;
				this.haveAuth = false;
				this.toUpAuth = true;
				this.priceStandard = 0;
				this.pricePlus = 0;
				this.keyword = '';
			},
            preImg (url) {
                this.previewImgUrl = url;
                this.previewImg = true;
            },
            delImg (file, type) {
				var fl;
				if (type == this.TYPE.IMG) {
					fl = this.files;
				} else {
					fl = this.authFiles;
				}
				fl.splice(fl.indexOf(file), 1);
				if (fl.length == 0) {
					this.showUploadBut = false;
				}
            },
            handleFormatError (file) {
                this.$Notice.warning({
                    title: 'The file format is incorrect',
                    desc: 'File format of ' + file.name + ' is incorrect, please select jpg or png.'
                });
            },
			beforeUpload (file) {
				if (!this.haveImg) {
					this.haveImg = true;
				}
				this.setImgUrlAndPush(file, this.TYPE.IMG);
				return false;
				
			},
			beforeUploadAuth (file) {
				if (!this.haveAuth) {
					this.haveAuth = true;
				}
				this.setImgUrlAndPush(file, this.TYPE.AUTH)
				return false;
			},
			setImgUrlAndPush (file, type) {
				const _this = this;
				const reader = new FileReader();
				var _file = file
				// blob会消耗掉文件
				const blob = new Blob([file], {type:"text/plain"});
				reader.readAsDataURL(blob);
				reader.onloadend = function (file) {
					_file.url = this.result
					if (type == _this.TYPE.IMG) {
						_this.files.push(_file);
					} else {
						_this.authFiles.push(_file);
					}
					
				}
			},
			upload () {
				this.uploadPic(this.TYPE.IMG);
				this.uploadPicInfo();
				// this.uploadPic(this.TYPE.AUTH);
				// 应该销毁组件再重建，这里做个样子
			},
			uploadPic (type) {
				let params = new FormData();
				// 必须这样添加文件
				if (type == this.TYPE.IMG) {
					this.files.forEach(file => params.append('files', file));
				} else {
					if (this.authFiles.length == 0) {
						return ;
					}
					this.authFiles.forEach(file => params.append('files', file));
				}
				
				
				params.append('userId', this.uploadData.userId);
				params.append('seed', this.uploadData.seed);
				params.append('type', type);
				
				axios.defaults.withCredentials = true;
				axios.post(this.uploadPicUrl, params)
					.then(res => {
						this.$Message.success('作品上传成功');
						if (this.toUpAuth) {
							this.toUpAuth = false;
							this.uploadPic(this.TYPE.AUTH);
						}
					})
					.catch(err => {
						this.$Message.error('作品上传失败')
					});
			},
			uploadPicInfo () {
				let params = new URLSearchParams();
				params.append('userId', this.uploadData.userId);
				params.append('seed', this.uploadData.seed);
				params.append('priceStandard', this.priceStandard);
				params.append('pricePlus', this.pricePlus);
				params.append('keywords', this.keyword);
				
				axios.post(this.uploadPicInfoUrl, params)
					.then(res => {
						// this.$Message.success('作品等待审核中')
					})
					.catch(err => {
						this.$Message.error('信息上传失败')
					})
			}
        },
    }
</script>
