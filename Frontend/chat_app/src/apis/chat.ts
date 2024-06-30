import { RootState } from '@/redux/store';
import axios from 'axios';
import { useSelector } from 'react-redux';

export const fetchChatHistory = async ({ pageParam = 0, queryKey }: { pageParam?: number; queryKey: any }) => {
  const [roomId, timeLine] = queryKey;
  const { data } = await axios.get(`${import.meta.env.VITE_SPRING_URL}/chat-room/chat/${roomId}`, {
    params: {
      page: pageParam,
      timeLine,
      roomExitTime: '2024-06-24 15:52:44',
      size: 5,
    },
  });
  return data;
};

export const fetchChatList = async () => {
  const { id, accessToken } = useSelector((state: RootState) => state.user);
  const response = await axios.get(`${import.meta.env.VITE_SPRING_URL}/chat-room?userId=${id}`, {
    headers: { Authorization: accessToken },
  });
  return response;
};
