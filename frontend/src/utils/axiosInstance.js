import axios from 'axios'
import { BACKEND_SERVER_URL, IMAGE_SERVER_URL } from "../config/Config";

const backBaseUrl = BACKEND_SERVER_URL
const imageBaseUrl = IMAGE_SERVER_URL

const axiosApi = (url, options) => {
    return axios.create({ baseURL: url, ...options })
}

const axiosAuthApi = (url, options) => {
    const token = '토큰 값'
    return axios.create({
        baseURL: url,
        headers: { Authorization: 'Bearer ' + token },
        ...options,
    })
}

export const defaultBackInstance = axiosApi(backBaseUrl)
export const authBackInstance = axiosAuthApi(backBaseUrl)
export const defaultImageInstance = axiosApi(imageBaseUrl)
export const authImageInstance = axiosAuthApi(imageBaseUrl)