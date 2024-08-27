import { chatFormat } from '@/types/types';
import ChatBubble from './ChatBubble';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux/store';
import { formatDateTime } from '@/utils/formatDateTime';

interface chatRoomProps {
  chatDatas: chatFormat[];
  loadMoreRef: React.RefObject<HTMLDivElement>;
}

function ChatWrapper({ chatDatas, loadMoreRef }: chatRoomProps) {
  const user = useSelector((state: RootState) => state.user);
  return (
    <Wrapper>
      <div ref={loadMoreRef} />
      {/* 이전 채팅 */}
      {data.pages.map((page: { pastChats: any[] }) =>
        page.pastChats.map((chat, index) => {
          const createDate = formatDateTime(chat.createDate);
          return (
            <ChatBubble
              key={index}
              isOthers={chat.sender.id !== user.id}
              data={{ content: chat.content, createDate: createDate }}
            />
          );
        }),
      )}
      {/* 실시간 채팅 */}
      {chatDatas.map((chat, index) => {
        const createDate = formatDateTime(chat.createDate);

        return (
          <ChatBubble
            key={index + data.pages.length}
            isOthers={chat.sender.id !== user.id}
            data={{ content: chat.content, createDate: createDate }}
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
