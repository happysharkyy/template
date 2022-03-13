import axios from '../axios'
// 分页查询
export const findPage = (data) => {
    return axios({
        url: '/series/findPage',
        method: 'post',
        data
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/series/delete',
        method: 'post',
        data
    })
}
// 保存
export const save = (data) => {
    return axios({
        url: '/series/save',
        method: 'post',
        data
    })
}
// 浏览
export function getTopic(id) {
    return axios({
      url: '/series/findById/'+id,
      method: 'get',
    })
}