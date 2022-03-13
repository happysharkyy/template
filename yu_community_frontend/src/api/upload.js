import request from '@/utils/request'
export function upload(file) {
    return request({
      url: '/upload/file',
      method: 'post',
      data:file
    })
  }
  
  
  