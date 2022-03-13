import axios from '../axios'

/* 
 * 用户管理模块
 */

// 保存
export const save = (data) => {
    return axios({
        url: '/ums/user/save',
        method: 'post',
        data
    })
}
export const findByName = (data) => {
    return axios({
        url: '/ums/user/getByUserName/'+data,
        method: 'get',
    })
}
// 删除
export const batchDelete = (data) => {
    return axios({
        url: '/ums/user/delete',
        method: 'post',
        data
    })
}
// 分页查询
export const findPage = (data) => {
    return axios({
        url: '/ums/user/findPage',
        method: 'post',
        data
    })
}
// 查找用户的菜单权限标识集合
export const findPermissions = (params) => {
    return axios({
        url: '/ums/user/findPermissions',
        method: 'get',
        params
    })
}

