import axios from '../axios'
// 分页查询
export const findPage = (data) => {
    return axios({
        url: '/sysMessage/findPage',
        method: 'post',
        data
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/sysMessage/delete',
        method: 'post',
        data
    })
}
