import axios from 'axios';

export const sendLoginRequest = async ({ email, password }: { email: string; password: string }) => {
  try {
    const res = await axios.post(`${import.meta.env.VITE_SPRING_URL}/login`, {
      email,
      pwd: password,
    });
    return res.data;
  } catch (err: any) {
    const status = err.response.status;
    const text = err.response.statusText;
    console.log(`status::${status}- ${text}`);
  }
};

export const reissueAccessTokenRequest = async (refreshToken: string) => {
  try {
    const response = await axios.post(`${import.meta.env.VITE_SPRING_URL}/refresh-token`, {
      refreshToken,
    });
    return response.data; // 응답 데이터 반환
  } catch (error) {
    console.error('Failed to refresh access token:', error);
    throw error;
  }
};
