import ChatInputBar from '@/components/ChatInputBar';
import ChatRoom from '@/components/ChatRoom';
import Header from '@/components/Header';
import { RootState } from '@/redux/store';
import { Main } from '@/styles/Common';
import { chatFormat } from '@/types/types';
import { ParsedDateTime } from '@/utils/parseDateTime';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import styled from 'styled-components';

function ChatRoomPage() {
  const { client, isConnected } = useSelector((state: RootState) => state.webSocket);
  const [liveChats, setLiveChats] = useState<chatFormat[]>([]);
  useEffect(() => {
    if (client && isConnected) {
      try {
        client.onConnect = () => {
          const subscription = client.subscribe(`/sub/chat/303`, (message) => {
            const newChat = JSON.parse(message.body);
            setLiveChats((prev) => {
              const chat = {
                ...newChat,
                createDate: {
                  ...ParsedDateTime(newChat.createDate),
                },
              };
              return [...prev, chat];
            });
          });
          return () => {
            if (subscription) {
              subscription.unsubscribe();
            }
          };
        };
      } catch (error) {
        console.error('Error subscribing to topic:', error);
      }
    } else {
      console.warn('Client is not connected or client is null.');
    }
  }, [client, isConnected]);

  return (
    <ChatRoomContainer>
      <Header title="채팅방" isBackArrow />
      <Main>
        <ChatRoom chatDatas={liveChats} />
      </Main>
      <Footer>
        <ChatInputBar />
      </Footer>
    </ChatRoomContainer>
  );
}

export default ChatRoomPage;
const ChatRoomContainer = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;
const Footer = styled.div`
  padding: 8px 12px;
`;
