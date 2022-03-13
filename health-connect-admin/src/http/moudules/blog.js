import axios from '../axios'

/* 
 * 用户管理模块
 */


export const findByName = (data) => {
    return axios({
        url: '/post/getByUserName/'+data,
        method: 'get',
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/post/delete',
        method: 'post',
        data
    })
}
// 置顶
export const batchTop = (id) => {
    return axios({
        url: '/post/top/'+id,
        method: 'get',
      })
}
// 保存
export const save = (data) => {
    return axios({
        url: '/post/save',
        method: 'post',
        data
    })
}
// 分页查询
export const findPage = (data) => {
    return axios({
        url: '/post/findPage',
        method: 'post',
        data
    })
}
// 浏览
export function getTopic(id) {
    return axios({
      url: '/post/findById/'+id,
      method: 'get',
    })
}