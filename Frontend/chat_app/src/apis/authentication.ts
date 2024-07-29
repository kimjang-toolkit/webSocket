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
