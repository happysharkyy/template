import axios from '../axios'
export function fetchForwardByTopicId(params) {
    return axios({
      url: '/forward/get_forwards',
      method: 'get',
      params
    })
  }
  export function saveForward(data){
    return axios({
      url: '/forward/save_forward',
      method: 'post',
      data
    })
  }