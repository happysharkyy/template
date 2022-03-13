import axios from '../axios'
export function fetchStarByTopicId(params) {
    return axios({
      url: '/star/get_stars',
      method: 'get',
      params
    })
  }
  export function saveStar(data){
    return axios({
      url: '/star/save_star',
      method: 'post',
      data
    })
  }
  
