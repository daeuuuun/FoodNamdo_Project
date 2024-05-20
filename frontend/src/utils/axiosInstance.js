import axios from 'axios'

const BASE_URL = 'http://localhost:8080'

const axiosApi = (url, options) => {
    return axios.create({baseURL: url, ...options})
}

const axiosAuthApi = (url, options) => {
    const token = '토큰 값'
    return axios.create({
        baseURL: url,
        headers: {Authorization: 'Bearer ' + token},
        ...options,
    })
}

export const defaultInstance = axiosApi(BASE_URL)
export const authInstance = axiosAuthApi(BASE_URL)