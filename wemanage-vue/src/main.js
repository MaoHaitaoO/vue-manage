// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import Vueawesome from 'vue-awesome'
import 'vue-awesome/icons/flag'
import Icon from 'vue-awesome/components/Icon'
import store from './store'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import VueI18n from 'vue-i18n'
import enLocale from 'element-ui/lib/locale/lang/en'
import zhLocale from 'element-ui/lib/locale/lang/zh-CN'
import ElementLocale from 'element-ui/lib/locale'
import apiConfig from '../config/api.config'
import JsEncrypt from 'jsencrypt'

Vue.config.productionTip = false
Vue.use(Vueawesome)
Vue.use('icon', Icon)
Vue.use(ElementUI);
Vue.use(VueI18n);
Vue.prototype.$axios = axios;

axios.defaults.baseURL = apiConfig.baseUrl;
axios.defaults.timeout = 30000;

axios.interceptors.request.use(
  config => {
    store.state.isLoading = true;
    config.headers = {
      "token": window.sessionStorage.getItem('access_token'),
      'Content-Type': 'application/x-www-form-urlencoded',
      // 'Content-Type': 'application/json',
      // "Access-Control-Allow-Origin": "*",
      // "Access-Control-Allow-Headers": "Content-Type, Content-Length, Authorization, Accept, X-Requested-With , yourHeaderFeild",
      // "Access-Control-Allow-Methods": "PUT,POST,GET,DELETE,OPTIONS"
    }
    return config;
  },
  error => {
    store.state.isLoading = false;
    ElementUI.Message.error({
      message: '网络不给力,请稍后再试'
    })
    return Promise.reject(error)
  }
)

axios.interceptors.response.use(
  response => {
    store.state.isLoading = false;
    if (response.data.code === '401') {
      ElementUI.MessageBox.alert(response.data.message, '提示', {
        confirmButtonText: '确定',
        callback: action => {
          router.push({
              path: "/login"
            }
          )
        }
      });
    }

    if (response.data.code != null && response.data.code != '' && response.data.code !== '401' && response.data.code !== '200') {
      ElementUI.MessageBox.alert(response.data.message, '提示', {
        confirmButtonText: '确定',
      });
    }
    return response;
  },
  error => {
    store.state.isLoading = false;
    ElementUI.Message.error({
      message: '网络不给力,请稍后再试'
    })
    return Promise.reject(error)
  }
);

// TODO 待验证 和拦截器是否有冲突，token失效需要提示
router.beforeEach((to, from, next) => {
  if (to.matched.length === 0) {
    next('/notFound');
  } else {
    let token = window.sessionStorage.getItem('access_token');
    if (token) {
      next();
    } else {
      if (to.path === '/login') {
        next();
      } else {
        next({
          path: '/login'
        })
      }
    }
  }
});

Vue.prototype.$RSAEncrypt = function (publicKey, data) {
  let encrypt = new JsEncrypt()
  encrypt.setPublicKey(publicKey)
  let result = encrypt.encrypt(data)
  return result
}

const messages = {
  en: {
    message: 'hello',
    ...enLocale
  },
  zh: {
    message: '你好',
    ...zhLocale
  }
}
// Create VueI18n instance with options
const i18n = new VueI18n({
  locale: 'en', // set locale
  messages, // set locale messages
})

ElementLocale.i18n((key, value) => i18n.t(key, value))

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: {App},
  template: '<App/>'
})
