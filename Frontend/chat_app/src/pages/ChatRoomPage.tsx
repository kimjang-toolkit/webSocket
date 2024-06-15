import ChatInputBar from '@/components/ChatInputBar';
import Chats from '@/components/Chats';
import Header from '@/components/Header';
import styled from 'styled-components';
import { Client } from '@stomp/stompjs';

const ChatRoomPage = () => {
  const message = {
    type: 'greeting',
    chat: 'Hello, world!',
  };
  const publishMessageBody = JSON.stringify(message);
  const client = new Client({
    brokerURL: `${import.meta.env.VITE_BROKER_URL}/gs-guide-websocket`,
    debug: (str) => {
      console.log(str);
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });

  client.onConnect = () => {
    console.log('websocket이 열렸습니다.');
    client.subscribe('/topic/greetings', (frame) => {
      console.log(`받았습니다 ${frame.body}`);
    });
    client.publish({
      destination: '/app/hello',
      body: publishMessageBody,
      headers: {
        'content-type': 'application/json',
      },
    });
  };
  client.activate();
  return (
    <ChatRoomContainer>
      <Header title="채팅방" />
      <Chats />
      <ChatInputBar />
    </ChatRoomContainer>
  );
};

export default ChatRoomPage;
const ChatRoomContainer = styled.div`
  display: flex;
  flex-direction: column;
  background: pink;
  height: 100%;
`;
