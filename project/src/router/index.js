import Vue from 'vue'
import Router from 'vue-router'
import { getToken} from '@/utils/auth'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children: [{
      path: 'home',
      name: 'home',
      component: () => import('@/views/home/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },
  {
    path: '/account',
    component: Layout,
    redirect: '/account',
    children: [{
      path: 'account',
      name: 'account',
      component: () => import('@/views/account/AccountITest'),
      meta: { title: '用户测试', icon: 'dashboard' }
    },{
      path: 'Manager',
      name: 'Manager',
      component: () => import('@/views/account/Manager'),
      meta: { title: '用户测试', icon: 'dashboard' }
    },{
      path: 'login',
      name: 'login',
      component: () => import('@/views/account/login'),
      meta: { title: '用户测试', icon: 'dashboard' }
    },
    ]
  },
  {
    path: '/channels',
    component: Layout,
    redirect: '/channels',
    children: [{
      path: 'channels',
      name: 'channels',
      component: () => import('@/views/channels/index'),
      meta: { title: '用户测试', icon: 'dashboard' }
    }]
  },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

export const Account = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/account',
    component: Layout,
    redirect: '/account',
    children: [
      {
        path: 'account',
        name: 'account',
        component: () => import('@/views/account/AccountITest'),
        meta: { title: '测试1', icon: 'table' }
      },
      {
        path: 'Manager',
        name: 'Manager',
        component: () => import('@/views/account/Manager'),
        meta: { title: '测试2', icon: 'table' }
      },
      {
        path: 'login',
        name: 'login',
        component: () => import('@/views/account/login'),
        meta: { title: '测试3', icon: 'table' }
      },
    ]
  },{
    path: '/channels',
    component: Layout,
    redirect: '/channels',
    children: [
      {
        path: 'channels',
        name: 'channels',
        component: () => import('@/views/channels/index'),
        meta: { title: '测试2', icon: 'table' }
      },
    ]
  }
]

/*const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})*/

const createRouter = () => new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

router.beforeEach((to,from,next)=>{
  console.log("getToken()角色权限值",getToken())
  const dd = getToken()
  if (dd == 2){
    //创建路由
    router.options.routes = Manager
  }
  else if (dd == 3 ){
    router.options.routes = appointment
  }else if (dd == 1){
    router.options.routes = Account
  }else{
    router.options.routes = constantRoutes
  }
  next();
})

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
