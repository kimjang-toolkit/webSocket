import axios from 'axios';

export const fetchChatHistory = async ({ pageParam = 0, queryKey }: { pageParam?: number, queryKey: any }) => {
  const [roomId, timeLine] = queryKey;
  const { data } = await axios.get(`/chat-room/chat/${roomId}`, {
    params: {
      page: pageParam,
      timeLine,
    },
  });
  return data;
};
