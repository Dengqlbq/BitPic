import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import List from '@/components/pic/List'
import Detail from '@/components/pic/Detail'
import Profile from '@/components/user/Profile'
import Wishes from '@/components/user/Wishes'
import ToLogin from '@/components/ToLogin'


Vue.use(Router)

export default new Router({
  routes: [
		{
			path: '/',
			name: 'Hello',
			component: Hello
		},
		{
			path: '/list/:keyword',
			name: 'List',
			component: List
		},
		{
			path: '/detail/:number',
			name: 'Detail',
			component: Detail
		},
		{
			path: '/profile',
			name: 'Profile',
			component: Profile
		},
		{
			path: '/wishes',
			name: 'Wishes',
			component: Wishes
		},
		{
			path: '/tologin/:before',
			name: 'ToLogin',
			component: ToLogin,
		}
  ]
})
