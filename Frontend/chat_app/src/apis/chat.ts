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

export const createChatRoom = async ({ participants, roomName }: { participants: number[]; roomName: string }) => {
  if (participants.length === 0) throw new Error('No particpants');
  const response = await api.post(`/chat-room`, {
    participants,
    roomName,
  });
  if (response.status == 200) return response.data;
  else return false;
};
