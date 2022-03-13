import axios from '../axios'
  
export const upload = (data) => {
    return axios({
        url: '/upload/file',
        method: 'post',
        data
    })
}