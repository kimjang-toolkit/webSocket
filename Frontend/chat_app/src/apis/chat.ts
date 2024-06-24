import axios from 'axios';

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
