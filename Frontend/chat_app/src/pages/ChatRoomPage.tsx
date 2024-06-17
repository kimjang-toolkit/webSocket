import ChatInputBar from '@/components/ChatInputBar';
import Chats from '@/components/Chats';
import Header from '@/components/Header';
import { AppDispatch, RootState } from '@/redux/store';
import { Footer, Main } from '@/styles/Common';
import { Client } from '@stomp/stompjs';
import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import styled from 'styled-components';

const ChatRoomPage = () => {
  const message = {
    roomId: 1,
    content: '호식이 두마리 치킨 크크크 치킨은 회애!',
    customer: {
      id: 1,
      name: '효승이',
    },
  };
  const publishMessageBody = JSON.stringify(message);
  const client = new Client({
    brokerURL: `${import.meta.env.VITE_BROKER_URL}/gs-guide-websocket`,
    debug: (str) => {
      console.log(str);
    },
    // reconnectDelay: 5000,
    // heartbeatIncoming: 10000,
    // heartbeatOutgoing: 10000,
  });

  console.log('client created', Client);
  client.onConnect = () => {
    console.log('websocket이 열렸습니다.');
    client.subscribe('/sub/chat/1', (frame) => {
      console.log(`받았습니다 ${frame.body}`);
    });
    client.publish({
      destination: '/pub/chat/1',
      body: publishMessageBody,
      headers: {
        'content-type': 'application/json',
      },
    });
  };
  client.activate();
  // const dispatch = useDispatch<AppDispatch>();
  // const { client, isConnected } = useSelector((state: RootState) => state.webSocket);
  // console.log('client', client);
  // useEffect(() => {
  //   if (client && isConnected) {
  //     const subscription = client.subscribe(`/sub/chat/1`, (messag) => {
  //       console.log('Recieved message', message.body);
  //     });

  //     return () => {
  //       subscription.unsubscribe();
  //     };
  //   }
  // }, [dispatch, client, isConnected]);
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
