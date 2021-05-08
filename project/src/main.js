import Vue from 'vue'
import AccountInfo from './views/account/AccountInfo'
import router from './router'
import store from './store'
import iview from 'iview'
import './utils/validate'
import 'iview/dist/styles/iview.css'
import './plugins/element.js'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import '@/styles/index.scss' // global css

import App from './App'

import '@/icons' // icon



import '@/icons' // icon
import '@/permission' // permission control

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */
import { mockXHR } from '../mock'
if (process.env.NODE_ENV === 'production') {
  mockXHR()
}



Vue.prototype.$axios = axios
axios.defaults.baseURL = '/api'  //关键代码
Vue.use(ElementUI)

Vue.config.productionTip = false
Vue.use(iview)

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})

/*new Vue({
  router,
  store,
  render: h => h(app)
}).$mount('#app')*/
