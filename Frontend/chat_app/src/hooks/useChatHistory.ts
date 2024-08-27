import { useInfiniteQuery } from '@tanstack/react-query';
import { fetchChatHistory } from '@/apis/chat';

interface ChatHistoryParams {
  roomId: string;
  userId: number;
  timeLine: string;
}

export const useChatHistory = ({ roomId, userId, timeLine }: ChatHistoryParams) => {
  return useInfiniteQuery({
    queryKey: [roomId, userId, timeLine],
    queryFn: fetchChatHistory,
    getNextPageParam: (lastPage) => {
      return lastPage.hasNext ? lastPage.page + 1 : undefined;
    },
    initialPageParam: 0,
  });
};
