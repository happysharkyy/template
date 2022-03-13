import axios from '../axios'
// 分页查询
export const findPage = (data) => {
    return axios({
        url: '/promotion/findPage',
        method: 'post',
        data
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/promotion/delete',
        method: 'post',
        data
    })
}
// 保存
export const save = (data) => {
    return axios({
        url: '/promotion/save',
        method: 'post',
        data
    })
}