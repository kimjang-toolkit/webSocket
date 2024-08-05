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

export const putProfileImg = async (url: string, file: File) => {
  try {
    if (!url) {
      throw new Error('Require presignedURl');
    }
    const response = await axios.put(url, file, {
      headers: {
        'Content-Type': file.type,
      },
    });
    return true;
  } catch (error: any) {
    const status = error.response.status;
    const text = error.response.statusText;
    console.log(`status::${status}- ${text}`);
  }
};
