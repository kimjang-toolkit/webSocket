import store from '@/redux/store';
import axios from 'axios';

const api = axios.create({
  baseURL: `${import.meta.env.VITE_SPRING_URL}`,
});

export default api;

//요청인터셉터
api.interceptors.request.use(
  (config) => {
    const state = store.getState();
    const { accessToken } = state.user;

    if (accessToken) {
      config.headers['Authorization'] = `${accessToken}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);
