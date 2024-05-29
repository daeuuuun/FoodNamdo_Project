import axios from 'axios';
import { BACKEND_SERVER_URL, IMAGE_SERVER_URL } from "../config/Config";

const backBaseUrl = BACKEND_SERVER_URL;
const imageBaseUrl = IMAGE_SERVER_URL;

const axiosApi = (url, options) => {
    return axios.create({ baseURL: url, ...options });
};

const axiosAuthApi = (url, options) => {
    const instance = axios.create({
        baseURL: url,
        headers: { Authorization: 'Bearer ' + localStorage.getItem("accessToken") },
        ...options,
    });

    // 요청 인터셉터 설정
    instance.interceptors.request.use(config => {
        const token = localStorage.getItem("accessToken");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    });

    // 응답 인터셉터 설정
    instance.interceptors.response.use(
        response => response,
        async error => {
            const originalRequest = error.config;

            if (error.response.status === 401 && !originalRequest._retry) {
                originalRequest._retry = true;

                try {
                    const refreshToken = localStorage.getItem("refreshToken");
                    const response = await axios.post(`${backBaseUrl}/usermanagement/refreshToken`, { accessToken: localStorage.getItem("accessToken"), refreshToken:localStorage.getItem("refreshToken") });

                    const newAccessToken = response.data.accessToken;
                    localStorage.setItem("accessToken", newAccessToken);

                    axios.defaults.headers.common['Authorization'] = 'Bearer ' + newAccessToken;
                    originalRequest.headers['Authorization'] = 'Bearer ' + newAccessToken;

                    return axios(originalRequest);
                } catch (refreshError) {
                    // 리프레시 토큰이 만료된 경우 처리
                    console.error('리프레시 토큰이 만료되었습니다.', refreshError);
                    // 로그아웃 또는 리프레시 토큰 재발급 페이지로 리디렉션 등 필요한 처리
                }
            }

            return Promise.reject(error);
        }
    );

    return instance;
};

export const defaultBackInstance = axiosApi(backBaseUrl);
export const authBackInstance = axiosAuthApi(backBaseUrl);
export const defaultImageInstance = axiosApi(imageBaseUrl);
export const authImageInstance = axiosAuthApi(imageBaseUrl);
