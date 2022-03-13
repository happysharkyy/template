import axios from '../axios'
// 分页查询
export const getImageAll = (data) => {
    return axios({
        url: '/upload/getImageAll',
        method: 'get'
    })
}
// 删除
export const batchDelete = (id) => {
    return axios({
        url: '/upload/delete/'+id,
        method: 'post'
    })
}
