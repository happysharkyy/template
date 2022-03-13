import axios from '../axios'

/* 
 * 用户管理模块
 */

// 保存
// 保存
export const save = (data) => {
    return axios({
        url: '/billboard/save',
        method: 'post',
        data
    })
}
export const findByName = (data) => {
    return axios({
        url: '/billboard/getByUserName/'+data,
        method: 'get',
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/billboard/delete',
        method: 'post',
        data
    })
}
// 分页查询
export const findPage = (data) => {
    return axios({
        url: '/billboard/findPage',
        method: 'post',
        data
    })
}
