import ChatBubble from '@/components/ChatRoom/ChatBubble';
import { useChatHistory } from '@/hooks/useChatHistory';
import { formatDateTime } from '@/utils/formatDateTime';
import { useState } from 'react';
import { useInView } from 'react-intersection-observer';
import styled from 'styled-components';

interface ChatHistoryProps {
  roomId: string;
  userId: number;
}

function ChatHistory({ roomId, userId }: ChatHistoryProps) {
  const { data, error, status, fetchNextPage, isFetchingNextPage } = useChatHistory({
    roomId: roomId ?? '',
    userId: userId ?? 0,
    timeLine: 'recent',
  });


  return status === 'pending' ? (
    <div>Loading...</div>
  ) : status === 'error' ? (
    <div>{error.message}</div>
  ) : (
    <>
      {data?.pages.map((page) => {
        return (
          <PageWrapper key={page.currentPage}>
            {page.pastChats.map((chat, index) => {
              const createDate = formatDateTime(chat.createDate);
              return (
                <ChatBubble
                  key={index}
                  isOthers={chat.sender.id !== userId}
                  data={{ content: chat.content, createDate: createDate }}
                />
              );
            })}
          </PageWrapper>
        );
      })}
    </>
  );
}

export default ChatHistory;

const PageWrapper = styled.div`
  display: flex;
  flex-direction: column-reverse;
  gap: 8px;
`;
