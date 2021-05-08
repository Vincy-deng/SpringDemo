import { get, post } from './axios'
import request from '@/utils/request'


export function getAccountList1() {
  return request({
    url: '/AccountController/findAllAccountList',
    method: 'post',
    //params: data
  })
}

export function getAccountList(data){
  return request({
    url: '/AccountController/list',
    method: 'get',
    params: data
  })
}

export function delAccount(data){
  return request({
    url: '/AccountController/delAccount',
    method: 'post',
    params: data
  })
}

export function getRightTree(){
  return request({
    url: '/RightController/getTree',
    method: 'post',
    //params: data
  })
}

export function getDepartmentList(){
  return request({
    url: '/AccountController/findDepartmentList',
    method: 'post',
    //params: data
  })
}

export function addAccount(data){
  return request({
    url: '/AccountController/addAccount',
    method: 'post',
    params: data
  })
}

export function findDespById(data){
  return request({
    url: '/AccountController/findDespById',
    method: 'post',
    params: data
  })
}

export function updateAccount(data){
  return request({
    url: '/AccountController/updateAccount',
    method: 'post',
    params: data
  })
}


export function login(data){
  return request({
    url: '/AccountController/login',
    method: 'post',
    params: data
  })
}

export function logout(data){
  return request({
    url: '/AccountController/logout',
    method: 'post',
    params: data
  })
}

export function getInfo(data){
  return request({
    url: '/AccountController/getInfo',
    method: 'post',
    params: data
  })
}

export function findAgentById(data){
  return request({
    url: '/AccountController/findAgentById',
    method: 'post',
    params: data
  })
}

export function findAllAccount(){
  return request({
    url: '/AccountController/findAllAccount',
    method: 'post'
  })
}
