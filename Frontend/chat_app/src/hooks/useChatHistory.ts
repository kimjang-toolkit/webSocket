import { useInfiniteQuery } from '@tanstack/react-query';
import { fetchChatHistory } from '@/apis/chat';

interface ChatHistoryParams {
  roomId: string;
  timeLine: string;
}

export const useChatHistory = ({ roomId, timeLine }: ChatHistoryParams) => {
  return useInfiniteQuery({
    queryKey: [roomId, timeLine],
    queryFn: fetchChatHistory,
    getNextPageParam: (lastPage, pages) => {
      return lastPage.hasNext ? pages.length : undefined;
    },
    initialPageParam: 0,
  });
};
