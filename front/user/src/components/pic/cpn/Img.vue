<template>
	<div class="img-box" :style="getStyle()">
		
		<img :src="getImgUrl()" >
		
		<!-- 遮蔽层 -->
		<div @click="redirect" class="img-cover">
			<!-- 数据展示 -->
			<div class="data-show">
				<div style="float: left;">
					<span>{{imgData.authorName}}</span>
				</div>
				
				<div style="float: right; margin-right: 15px;">
					<Icon type="md-star" size="24"/>
					<span>{{imgData.collection}}</span>
				</div>
				
				<div style="float: right;  margin-right: 15px;">
					<Icon type="md-thumbs-up" size="24" />
					<span>{{imgData.like}}</span>
				</div>
			</div>
		</div>
		
	</div>
</template>

<style scoped>
	span {
		font-size: 15px;
		color: white;
	}
	
	.img-box {
		margin: 2px;
		background-color: violet;
		position: relative;
	}
	
	img {
		position: absolute;
		top: 0;
		width: 100%;
		height: 100%;
		vertical-align: bottom;
	}
	
	.img-cover {
		position: absolute; 
		width: 100%; 
		height: 100%; 
		top: 0; 
		bottom: 0;
		left: 0;
		opacity: 0;
		transition: 0.7s;
	}
	
	.img-cover:hover {
		opacity: 1;
		transition: 0.7s;
		cursor: pointer;
	}
	
	
	
	.data-show {
		background: linear-gradient(top, #ffffff, #000000);
		position: absolute;
		height: auto;
		bottom: 0;
		left: 0;
		right: 0;
	}
	
</style>

<script>
	export default {
		data () {
			return {
				imgServerUrl: 'http://localhost:8089/images/',
				imgFileName: '0.png',
				imgHeight: 259,
			}
		},
		props: ['imgData'],
		methods: {
			redirect () {
				this.$router.push('/detail/' + this.imgData.number);
			},
			getStyle () {
				var w = this.imgData.size[0] * this.imgHeight / this.imgData.size[1];
				return 'width: ' + w + 'px;' + 'height: ' + this.imgHeight + 'px; flex-grow: ' + w + ';'
			},
			getImgUrl () {
				return this.imgServerUrl + this.imgData.authorId + '/' + this.imgData.number + '/' + this.imgFileName;
			}
		}
	}
</script>