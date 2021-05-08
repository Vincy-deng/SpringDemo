import { get, post } from './axios'
import request from '@/utils/request'

// 获取程序配置
export function getConfig () {
  return get('static/config.json', null, { baseURL: './' })
}

// 获取用户信息（统一认证登录的用户）
export function getLoginInfo () {
  return get('/oauth/getLoginInfo')
}

export function getAccountaList() {
  return request({
    url: '/TChannelsEntity/findAllChannelsEntity',
    method: 'post',
    //params: data
  })
  //return post('/TChannelsEntity/findAllChannelsEntity')
}

// 登录
export function login (params) {
  return post('/oauth/login', params)
}

// 退出
export function logout (params) {
  return get('/oauth/logout', params)
}
