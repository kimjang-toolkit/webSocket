import api from '@/apis/axiosInstance';

export const fetchChatHistory = async ({ pageParam = 0, queryKey }: { pageParam?: number; queryKey: any }) => {
  const [roomId, userId, timeLine] = queryKey;
  const { data } = await api.get(`/chat-room/chat/${roomId}`, {
    params: {
      userId: userId,
      page: pageParam,
      timeLine,
      size: 5,
    },
  });
  return data;
};

export const fetchChatList = async (userId: number | null) => {
  if (userId === null) throw new Error('User ID is null');
  const response = await api.get(`/chat-room?userId=${userId}`);
  return response.data;
};
