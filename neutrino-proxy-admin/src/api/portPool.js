import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/port-pool/page',
    method: 'get',
    params: query
  })
}

export function portPoolList() {
  return request({
    url: '/port-pool/list',
    method: 'get'
  })
}

export function updateEnableStatus(id, enable) {
  return request({
    url: '/port-pool/update/enable-status',
    method: 'post',
    data: {
      id: id,
      enable: enable
    }
  })
}

export function createPortPool(data) {
  return request({
    url: '/port-pool/create',
    method: 'post',
    data
  })
}

export function deletePortPool(id) {
  return request({
    url: '/port-pool/delete',
    method: 'post',
    params: {
      id: id
    }
  })
}
