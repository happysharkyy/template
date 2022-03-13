import axios from '../axios'

export const getTodayAddUser = () => {
    return axios({
        url: '/sum/ums/user/getTodayAddUser/list',
        method: 'get',
    })
}
export const getTodayAddStar = () => {
    return axios({
        url: '/sum/star/getTodayAddStar',
        method: 'get',
    })
}
export const getTodayAddForward = () => {
    return axios({
        url: '/sum/forward/getTodayAddForward',
        method: 'get',
    })
}

export const getTodayAddComment = () => {
    return axios({
        url: '/sum/comment/getTodayAddComment',
        method: 'get',
    })
}

export const getTodayAddPost = () => {
    return axios({
        url: '/sum/post/getTodayAddPost',
        method: 'get',
    })
}
export const getMonthAddPost = () => {
    return axios({
        url: '/sum/post/getMonthAddPost',
        method: 'get',
    })
}
export const getRank = () => {
    return axios({
        url: '/sum/post/getRank',
        method: 'get',
    })
}
export const getViewRank = () => {
    return axios({
        url: '/sum/post/getViewRank',
        method: 'get',
    })
}
export const getTagRank = () => {
    return axios({
        url: '/sum/tag/getTagRank',
        method: 'get',
    })
}
