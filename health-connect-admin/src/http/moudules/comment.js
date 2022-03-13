import axios from '../axios'

/* 
 * 用户管理模块
 */

// 保存
export const save = (data) => {
    return axios({
        url: '/comment/save',
        method: 'post',
        data
    })
}
export const findByName = (data) => {
    return axios({
        url: '/comment/getByUserName/'+data,
        method: 'get',
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/comment/delete',
        method: 'post',
        data
    })
}
// 分页查询
export const findPage = (data) => {
    return axios({
        url: '/comment/findPage',
        method: 'post',
        data
    })
}
