import { chatFormat } from '@/types/types';
import ChatBubble from './ChatBubble';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux/store';

interface chatRoomProps {
  chatDatas: chatFormat[];
  loadMoreRef: React.RefObject<HTMLDivElement>;
  data: any;
}

function ChatWrapper({ chatDatas, loadMoreRef, data }: chatRoomProps) {
  const user = useSelector((state: RootState) => state.user);
  return (
    <Wrapper>
      <div ref={loadMoreRef} />
      {data.pages.map((page: { pastChats: any[] }) =>
        page.pastChats.map((chat, index) => {
          const hour = String(chat.createDate?.hour).padStart(2, '0');
          const minute = String(chat.createDate.min).padStart(2, '0');
          return (
            <ChatBubble
              key={index}
              isOthers={chat.sender.id !== user.id}
              data={{ content: chat.content, published: `${hour}${minute}` }}
            />
          );
        }),
      )}
      {chatDatas.map((chat, index) => {
        const hour = String(chat.createDate?.hour).padStart(2, '0');
        const minute = String(chat.createDate.min).padStart(2, '0');
        return (
          <ChatBubble
            key={index + data.pages.length}
            isOthers={chat.sender.id !== user.id}
            data={{ content: chat.content, published: `${hour}${minute}` }}
          />
        );
      })}
    </Wrapper>
  );
}

export default ChatWrapper;

const Wrapper = styled.main`
  display: flex;
  flex-direction: column;
  background: white;
  gap: 8px;
`;
