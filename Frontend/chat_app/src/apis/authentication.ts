import { User } from '@/types/types';
import axios from 'axios';

export const authenticateUser = async ({ userEmail, password }: { userEmail: string; password: string }) => {
  const encodedData = btoa(`${userEmail}:${password}`);
  try {
    const response = await axios.get(`${import.meta.env.VITE_SPRING_URL}/user`, {
      headers: { Authorization: `Basic ${encodedData}` },
    });
    const jwtToken: string = response.headers['authorization'];
    const userData: User = response.data;
    return { userData, jwtToken };
  } catch (error: any) {
    const status = error.response.status;
    const text = error.response.statusText;
    console.log(`status::${status}- ${text}`);
  }
};
