import { get, post } from './axios'
import request from '@/utils/request'

export function getChannelsList(data){
  return request({
    url: '/TChannelsEntity/list',
    params: data,
    method: 'get'
  })
}
