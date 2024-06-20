import ChatInputBar from '@/components/ChatInputBar';
import Chats from '@/components/ChatRoom';
import Header from '@/components/Header';
import { RootState } from '@/redux/store';
import { Footer, Main } from '@/styles/Common';
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
      console.log('Client is connected, subscribing to topic...');

      try {
        client.onConnect = () => {
          const subscription = client.subscribe(`/sub/chat/1`, (message) => {
            const newChat = JSON.parse(message.body);
            console.log('newChat', newChat);
            setLiveChats((prev) => {
              // newChat.createDate = { ...ParsedDateTime(newChat.createDate) };
              const chat = {
                content: newChat.content,
                userId: newChat.customer.id,
                createdDate: {
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
        <Chats chatDatas={liveChats} />
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
