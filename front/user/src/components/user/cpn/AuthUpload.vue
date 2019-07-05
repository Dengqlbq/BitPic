<template>
	<div>
		<Upload
			multiple
			type="drag"
			:action="uploadUrl"
			:data="uploadData"
			:with-credentials="true">
			<div style="padding-top: 5vh; padding-bottom: 5vh; height: 20vh;">
				<Icon type="ios-cloud-upload" size="60" style="color: #3399ff"></Icon>
				<p style="font-size: 16px;">点击或拖拽文件到此</p>
			</div>
		</Upload>
		
		<div>
			<a href="#">下载模板</a>
			<a href="#">验证要求</a>
		</div>
	</div>	
</template>

<style scoped>
	
</style>

<script>
export default {
	data () {
		return {
			uploadUrl: 'http://localhost:8080/api/bitpic/user/file/upload/authenticate'
		}
	},
	props: ['uploadData'],
}
</script>