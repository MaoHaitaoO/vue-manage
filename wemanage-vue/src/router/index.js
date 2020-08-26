import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/view/login'
import Foo from '@/view/foo'
import NotFound from '@/view/404'
import Index from '@/view/index'
import Updown from '@/view/updown'
import User from '@/view/user'
import RefreshCache from '@/view/refreshCache'
import Permission from '@/view/permission'

Vue.use(Router)

export default new Router({
  mode: 'history',  //去掉url中的#
  scrollBehavior: () => ({
    y: 0
  }),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import(Login)
    }, {
      path: '/',
      name: 'index',
      component: () => import(Index),
      children: [
        {
          path: '/foo',
          name: 'foo',
          component: () => import(Foo)
        }, {
          path: '/notFound',
          name: 'notFound',
          component: () => import(NotFound)
        }, {
          path: '/updown',
          name: 'updown',
          component: () => import(Updown)
        }, {
          path: '/user',
          name: 'user',
          component: () => import(User)
        }, {
          path: '/refreshCache',
          name: 'RefreshCache',
          component: () => import(RefreshCache)
        }, {
          path: '/permission',
          name: 'Permission',
          component: () => import(Permission)
        }
      ]
    }
  ]
})
