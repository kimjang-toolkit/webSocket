import { useInfiniteQuery } from '@tanstack/react-query';
import { fetchChatHistory } from '@/apis/chat';

interface ChatHistoryParams {
  roomId: string;
  userId: number;
  timeLine: string;
}

export const useChatHistory = ({ roomId, userId, timeLine }: ChatHistoryParams) => {
  return useInfiniteQuery({
    queryKey: [roomId, userId,timeLine],
    queryFn: fetchChatHistory,
    getNextPageParam: (lastPage, pages) => {
      return lastPage.hasNext ? pages.length : undefined;
    },
    initialPageParam: 0,
  });
};
