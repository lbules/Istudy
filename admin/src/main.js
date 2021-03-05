import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'

Vue.config.productionTip = false
Vue.prototype.$ajax = axios;

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')

console.log("环境："+process.env.NODE_ENV);
