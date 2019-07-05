<template>
	<div>
		<!-- 头部 -->
		<Head v-on:notLogin="notLogin" v-on:refreshData="refreshData" ref="head"></Head>
		
		<!-- 登陆注册框 -->
		<Login v-on:loginSuc="loginSuc" v-on:regSuc="regSuc" ref="login"></Login>
		
		<!-- 装饰分隔栏 -->
		<div style="height: 8px; background: #EEE;"></div>
		
		<!-- 筛选栏 -->
		<Menu mode="horizontal" theme="light" style="z-index: 1; margin-bottom: 10px;">
                <Dropdown class="dropdown" @on-click="changeMenuAndGetData">
					<Icon type="ios-keypad" />
                        {{ format }}
                    <Icon type="ios-arrow-down"></Icon>
                    <DropdownMenu slot="list">
						<DropdownItem v-for="f in formats" :key="f.id" :name="f.name">{{f.value}}</DropdownItem>
                    </DropdownMenu>
                </Dropdown>

                <Dropdown class="dropdown" @on-click="changeMenuAndGetData">
						<Icon type="md-compass" />
                        {{ direction }}
                        <Icon type="ios-arrow-down"></Icon>
                    <DropdownMenu slot="list">
                        <DropdownItem v-for="d in directions" :key="d.id" :name="d.name">{{d.value}}</DropdownItem>
                    </DropdownMenu>
                </Dropdown>

                <Dropdown class="dropdown" @on-click="changeMenuAndGetData">
					<Icon type="ios-color-palette" />
                        {{category}}
                        <Icon type="ios-arrow-down"></Icon>
                    <DropdownMenu slot="list">
                        <DropdownItem v-for="c in categories" :key="c.ic" :name="c.name">{{c.value}}</DropdownItem>
                    </DropdownMenu>
                </Dropdown>

                <Dropdown class="dropdown" @on-click="changeMenuAndGetData">
						<Icon type="md-briefcase" />
                        {{group}}
                        <Icon type="ios-arrow-down"></Icon>
                    <DropdownMenu slot="list">
                        <DropdownItem v-for="g in groups" :key="g.ig" :name="g.name">{{g.value}}</DropdownItem>
                    </DropdownMenu>
                </Dropdown>
        </Menu>
		
	 	<div class="img-window">
			<Img v-for="data in datas"  :key="data.id" :imgData="data"> </Img>
		</div>
		
	</div>
</template>

<style scoped>	
	
	.dropdown {
		margin-left: 20px;
	}
    
	.img-window {
		display: flex;/*显示模式设置为弹性盒子*/
        flex-wrap: wrap;/*进行强制换行*/
	}
	
	.img-window:after{            
        /*对最后一个伪元素进行最大限度伸缩*/            
        content: '';            
        flex-grow: 9999999999999999999999;        
    }   
</style>

<script>
import axios from 'axios'
import Head from '../common/Head.vue'
import Login from '../common/Login.vue'
import Img from './cpn/Img.vue'

export default{
	data() {
		return {
			// 筛选
			keyword: '',
			format: "格式",
			direction: "方向",
			category: '类别',
			group: '组图',
			
			formats: [
				{
					name: 'fm-jpg',
					value: 'JPG'
				},
				{
					name: 'fm-png',
					value: 'PNG'
				},
				{
					name: 'fm-jpeg',
					value: 'JPEG'
				}
			],
			directions: [
				{
					name: 'dir-h',
					value: '横向'
				},
				{
					name: 'dir-v',
					value: '纵向'
				}
			],
			categories: [
				{
					name: 'ca-art',
					vlaue: '艺术'
				},
				{
					name: 'ca-building',
					value: '建筑',
				},
				{
					name: 'ca-landscape',
					value: '风景',
				},
				{
					name: 'ca-people',
					value: '人物'
				},
				{
					name: 'ca-macro',
					value: '微距'
				}
			],
			groups: [
				{
					name: 'g-y',
					value: '是组图'
				},
				{
					name: 'g-n',
					value: '非组图'
				}
			],
			
			DIRECTION_CODE: {
				'H': 0,
				'V': 1,
			},
			
			searchUrl: 'http://localhost:8080/api/bitpic/picShow/list?keyword=',
			
			datas: [],
			
			OPERATION_SUC_CODE: 4,
		}
    },
	components: {
		'Login': Login,
		'Head': Head,
		'Img': Img,
	},
	mounted() {
		if (this.hadLogin) {
			this.loginSuc(this.userData);
		}
		this.keyword = this.$route.params.keyword;
		this.getData();
	},
	computed: {
		hadLogin () {
			return this.$store.state.hadLogin;
		},
		userData () {
			return this.$store.state.userData;
		}
	},
	methods: {
		changeMenuAndGetData (name) {
			var prefix = name.split("-")[0]
			if (prefix == 'fm') {
				for (var f in this.formats) {
					if (name == this.formats[f]['name']) {
						this.format = this.formats[f]['value'];
					}
				}
			} else if (prefix == 'dir') {
				for (var d in this.directions) {
					if (name == this.directions[d]['name']) {
						this.direction = this.directions[d]['value'];
					}
				}
			} else if (prefix == 'ca') {
				for (var c in this.categories) {
					if (name == this.categories[c]['name']) {
						this.category = this.categories[c]['value'];
					}
				}
			} else {
				for (var g in this.groups) {
					if (name == this.groups[g]['name']) {
						this.group = this.groups[g]['value'];
					}
				}
			}
			
			this.datas = [];
			this.getData();
		},
		getData () {
			var url = this.searchUrl + this.keyword;
			if (this.format != '格式') {
				url += '&format=' + this.format;
			}
			if (this.direction != '方向') {
				if (this.direction == '横向') {
					url += '&direction=' + this.DIRECTION_CODE.H;
				} else {
					url += '&direction=' + this.DIRECTION_CODE.V;
				}
			}
			if (this.category != '类别') {
				url += '&category=' + this.category;
			}
			if (this.group != '组图') {
				if (this.group == '是组图') {
					url += '&group=' + 'true';
				} else {
					url += '&group=' + 'false';
				}
			}
			
			axios.get(url)
				.then(res => {
					if (res.data.code == this.OPERATION_SUC_CODE) {
						var data = res.data.data;
						if (data == null) {
							this.$Message.info('没有更多数据');
						} else {
							for (var k in data) {
								this.datas.push(data[k]);
							}
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
		notLogin () {
			this.$refs.login.show();
		},
		loginSuc (userData) {
			this.$store.commit('loginSuc', userData);
			this.$refs.head.setUserData(userData);
		},
		regSuc (userData) {
			this.$store.commit('loginSuc', userData);
			this.loginSuc(userData);
		},
		refreshData (keyword) {
			this.keyword = keyword;
			this.datas = [];
			this.getData();
		},
	}
}
</script>