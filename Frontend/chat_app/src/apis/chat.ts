import api from '@/apis/axiosInstance';

export const fetchChatHistory = async ({ pageParam = 0, queryKey }: { pageParam?: number; queryKey: any }) => {
  const [roomId, timeLine] = queryKey;
  const { data } = await api.get(`/chat-room/chat/${roomId}`, {
    params: {
      page: pageParam,
      timeLine,
      roomExitTime: '2024-06-24 15:52:44',
      size: 5,
    },
  });
  return data;
};

export const fetchChatList = async (userId: string | null) => {
  if (userId === null) throw new Error('User ID is null');
  const response = await api.get(`/chat-room?userId=${userId}`);
  return response.data;
};
