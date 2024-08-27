import ChatBubble from '@/components/ChatRoom/ChatBubble';
import { formatDateTime } from '@/utils/formatDateTime';
import styled from 'styled-components';

interface ChatHistoryProps {
  userId: number;
  chatDatas: any;
  status: any;
  error: any;
}

function ChatHistory({ userId, chatDatas, status, error }: ChatHistoryProps) {
  return status === 'pending' ? (
    <div>Loading...</div>
  ) : status === 'error' ? (
    <div>{error.message}</div>
  ) : (
    <>
      {chatDatas?.pages.map((page) => {
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
