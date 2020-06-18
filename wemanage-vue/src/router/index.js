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
      component: Login
    }, {
      path: '/',
      name: 'index',
      component: Index,
      children: [
        {
          path: '/foo',
          name: 'foo',
          component: Foo
        }, {
          path: '/notFound',
          name: 'notFound',
          component: NotFound
        },{
          path: '/updown',
          name: 'updown',
          component: Updown
        },{
          path: '/user',
          name: 'user',
          component: User
        },{
          path: '/refreshCache',
          name: 'RefreshCache',
          component: RefreshCache
        },{
          path: '/permission',
          name: 'Permission',
          component: Permission
        }
      ]
    }
  ]
})
