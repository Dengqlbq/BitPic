<template>
	<div class="layout">
        <Layout :style="{minHeight: '100vh'}">
            <Sider collapsible :collapsed-width="0" v-model="isCollapsed">
                <Menu active-name="1-2" theme="dark" width="auto" :class="menuitemClasses">
                    <MenuItem name="author" @click.native="author = true; pic = false;">
                        <Icon type="ios-navigate"></Icon>
                        <span>摄影师审核</span>
                    </MenuItem>
                    <MenuItem name="pic" @click.native="author = false; pic = true;">
                        <Icon type="ios-navigate"></Icon>
                        <span>作品审核</span>
                    </MenuItem>
                </Menu>
            </Sider>
            <Layout>
                <Header :style="{background: '#fff', boxShadow: '0 2px 3px 2px rgba(0,0,0,.1)'}">
					<div style="text-align: center;">
						<h1>BitPic管理后台</h1>
					</div>
				</Header>
                <Content :style="{padding: '0 16px 16px'}">
                    <Breadcrumb :style="{margin: '16px 0'}">
                        <BreadcrumbItem>Home</BreadcrumbItem>
                        <BreadcrumbItem>Components</BreadcrumbItem>
                    </Breadcrumb>
                    <Card>
                        <div style="height: 85vh">
							<AuthorCheck v-if="author"> </AuthorCheck>
							
							<PicCheck v-if="pic"> </PicCheck>
						</div>
                    </Card>
                </Content>
            </Layout>
        </Layout>
    </div>
</template>

<style scoped>
	.layout-con{
        height: 100%;
        width: 100%;
    }
    .menu-item span{
        display: inline-block;
        overflow: hidden;
        width: 69px;
        text-overflow: ellipsis;
        white-space: nowrap;
        vertical-align: bottom;
        transition: width .2s ease .2s;
    }
    .menu-item i{
        transform: translateX(0px);
        transition: font-size .2s ease, transform .2s ease;
        vertical-align: middle;
        font-size: 16px;
    }
    .collapsed-menu span{
        width: 0px;
        transition: width .2s ease;
    }
    .collapsed-menu i{
        transform: translateX(5px);
        transition: font-size .2s ease .2s, transform .2s ease .2s;
        vertical-align: middle;
        font-size: 22px;
    }
</style>

<script>
import axios from 'axios'	
import AuthorCheck from './AuthorCheck.vue'
import PicCheck from './PicCheck.vue'

export default {
	data () {
		return {
			author: false,
			pic: false,
			isCollapsed: false,
		};
	},
	components: {
		'AuthorCheck': AuthorCheck,
		'PicCheck': PicCheck,
	},
	computed: {
	    menuitemClasses: function () {
	        return [
	            'menu-item',
	            this.isCollapsed ? 'collapsed-menu' : ''
	        ]
	    }
	},
	methods: {
		
	}
}
</script>