// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import iView from 'iview'
import 'iview/dist/styles/iview.css' 
import Vuex from 'vuex'
import createPersiste from 'vue-savedata'

Vue.use(Vuex)
Vue.use(iView)
Vue.config.productionTip = false

const store = new Vuex.Store({
	plugins: [createPersiste()],
	state: {
		hadLogin: false,
	},
  mutations: {
		loginSuc: state => state.hadLogin = true,
		logout: state => state.hadLogin = false,
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
