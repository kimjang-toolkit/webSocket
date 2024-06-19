import ChatInputBar from '@/components/ChatInputBar';
import Chats from '@/components/ChatRoom';
import Header from '@/components/Header';
import { RootState } from '@/redux/store';
import { Footer, Main } from '@/styles/Common';
import { MessageFormat } from '@/types/types';
import { ParsedDateTime } from '@/utils/parseDateTime';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import styled from 'styled-components';

const ChatRoomPage = () => {
  const { client, isConnected } = useSelector((state: RootState) => state.webSocket);
  const [liveChats, setLiveChats] = useState<MessageFormat[]>([]);
  useEffect(() => {
    if (client && isConnected) {
      console.log('Client is connected, subscribing to topic...');

      try {
        client.onConnect = () => {
          const subscription = client.subscribe(`/sub/chat/1`, (message) => {
            let newChat = JSON.parse(message.body);
            setLiveChats((prev) => {
              newChat = {
                content: newChat.content,
                userId: newChat.customer.id,
                createdDate: {
                  ...ParsedDateTime(newChat.createDate),
                },
              };
              return [...prev, newChat];
            });
          });
          console.log(client);
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
        <Chats />
      </Main>
      <Footer>
        <ChatInputBar />
      </Footer>
    </ChatRoomContainer>
  );
};

export default ChatRoomPage;
const ChatRoomContainer = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;
