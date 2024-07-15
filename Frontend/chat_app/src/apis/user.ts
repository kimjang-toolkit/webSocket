import axios from 'axios';

export const getPresignedURL = async (userId: number) => {
  if (userId === null) throw new Error('Require userId');
  try {
    const response = await axios.get(`${import.meta.env.VITE_PROFILE_LAMBDA}/presignedURL/${userId}`);
    return response.data.url;
  } catch (error: any) {
    const status = error.response.status;
    const text = error.response.statusText;
    console.log(`status::${status}- ${text}`);
  }
};
