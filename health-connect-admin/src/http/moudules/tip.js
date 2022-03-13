import axios from '../axios'
// 分页查询
export const findPage = (data) => {
    return axios({
        url: '/tip/findPage',
        method: 'post',
        data
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/tip/delete',
        method: 'post',
        data
    })
}
// 保存
export const save = (data) => {
    return axios({
        url: '/tip/save',
        method: 'post',
        data
    })
}