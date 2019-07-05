// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import iView from 'iview'
import 'iview/dist/styles/iview.css' // 使用 CSS
import Vuex from 'vuex'
import createPersiste from 'vue-savedata'

// Vue.use(Viewer, {url: 'big'});
// Viewer.setDefaults({
//   Options: { "inline": true, "button": true, "navbar": true, "title": true, "toolbar": true, "tooltip": true, "movable": true, "zoomable": true, "rotatable": true, "scalable": true, "transition": true, "fullscreen": true, "keyboard": true, "url": "big" }
// });


Vue.use(Vuex)

Vue.use(iView)

Vue.config.productionTip = false

const store = new Vuex.Store({
	plugins: [createPersiste()],
	state: {
		userData: '',
		hadLogin: false,
	},
    mutations: {
		loginSuc (state, data) {
			state.hadLogin = true;
			state.userData = data;
		},
		logout (state) {
			state.hadLogin = false;
			state.userData = '';
		},
		updateAvatar: state => state.userData.haveAvatar = true,
		updateName (state, name) {
			state.userData.name = name;
		},
		updatePhone (state, phone) {
			state.userData.phone = phone;
		},
		updateBalance (state, amount) {
			state.userData.balance += amount;
		},
		addWish: state => state.userData.wishCount ++,
		delWish: state => state.userData.wishCount --,
		clearWish: state => state.userData.wishCount = 0,
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  components: { App },
  template: '<App/>'
})
