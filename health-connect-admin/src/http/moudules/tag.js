import axios from '../axios'

/* 
 * 用户管理模块
 */

// 保存
export const save = (data,topicId) => {
    return axios({
        url: '/tag/save/'+topicId,
        method: 'post',
        data
    })
}
export const findByName = (data) => {
    return axios({
        url: '/tag/getByUserName/'+data,
        method: 'get',
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/tag/delete',
        method: 'post',
        data
    })
}
// 分页查询
export const findPage = (data) => {
    return axios({
        url: '/tag/findPage',
        method: 'post',
        data
    })
}
