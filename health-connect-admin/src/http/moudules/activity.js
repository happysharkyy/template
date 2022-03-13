import axios from '../axios'
// 分页查询
export const findPage = (data) => {
    return axios({
        url: '/activity/findPage',
        method: 'post',
        data
    })
}
// 分页查询
export const findPageUser = (data) => {
    return axios({
        url: '/activity/findPageUser',
        method: 'post',
        data
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/activity/delete',
        method: 'post',
        data
    })
}
// 删除
export const batchDeleteUser = (data) => {
    return axios({
        url: '/activity/deleteUser',
        method: 'post',
        data
    })
}
// 保存
export const save = (data) => {
    return axios({
        url: '/activity/save',
        method: 'post',
        data
    })
}