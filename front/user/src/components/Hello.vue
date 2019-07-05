<template>
   <div id="app">
	    <Input id="search" v-model="keyword" search autofocus placeholder="探索BitPic..." size="large" @on-search="search"></Input>
   </div>
</template>

<style>
	.body {
		background: url(../../build/bg.jpg) no-repeat;
		background-size: 100%,100%;
		overflow: hidden;
	}
	
	#login{
		float: right;
		margin: 20px;	
	}
	
	#search{
		width: 60vw; 
		left: 20vw; 
		top: 35vh
	}
</style>

<script>
export default {
    name: 'App',
	data() {
		return {
			keyword: ''
		}
	},
	mounted() {
		document.getElementsByTagName("body")[0].className="body";
	},
	beforeDestroy: function() {
      document.body.removeAttribute("class","body");
	},
	methods:{
		search() {
			var url = 'list/' + this.keyword
			this.$router.push(url)
		}
	}
}
</script>