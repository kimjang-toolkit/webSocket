import ChatBubble from '@/components/ChatRoom/ChatBubble';
import { chatFormat } from '@/types/types';
import { formatDateTime } from '@/utils/formatDateTime';
import styled from 'styled-components';

interface ChatLiveProps {
  chatDatas: chatFormat[];
  userId: number;
}
function ChatLive({ chatDatas, userId }: ChatLiveProps) {
  return (
    <ChatLiveWrapper>
      {chatDatas.map((chat, index) => {
        const createDate = formatDateTime(chat.createDate);
        return (
          <ChatBubble
            key={index}
            isOthers={chat.sender.id !== userId}
            data={{ content: chat.content, createDate: createDate }}
          />
        );
      })}
    </ChatLiveWrapper>
  );
}

export default ChatLive;

const ChatLiveWrapper = styled.div`
  display: flex;
  flex-direction: column;
`;
