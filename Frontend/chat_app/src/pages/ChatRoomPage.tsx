import ChatInputBar from '@/components/ChatRoom/ChatInputBar';
import Header from '@/components/Header';
import { RootState } from '@/redux/store';
import { chatFormat } from '@/types/types';
import { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { MessageFormat } from '@/types/types';

import styled from 'styled-components';
import ChatWrapper from '@/components/ChatRoom/ChatsWrapper';

function ChatRoomPage() {
  const { client, isConnected } = useSelector((state: RootState) => state.webSocket);
  const [liveChats, setLiveChats] = useState<chatFormat[]>([]);
  const params = useParams();
  const user = useSelector((state: RootState) => state.user);

  //채팅방 구독
  useEffect(() => {
    if (client && isConnected && params.roomId) {
      const subscription = client.subscribe(`/sub/chat/${params.roomId}`, (message) => {
        const newChat = JSON.parse(message.body);
        setLiveChats((prev) => [...prev, newChat]);
      });

      return () => {
        console.log('unsubscribe 실행');
        subscription.unsubscribe();
      };
    }
  }, [client, isConnected, params.roomId]);

  const handleSendMessage = (message: string) => {
    if (client) {
      const messageFormat: MessageFormat = {
        roomId: Number(params.roomId),
        content: message,
        sender: {
          id: user.id ?? 0,
          name: user.name ?? 'undefined',
        },
      };
      const publishMessageBody = JSON.stringify(messageFormat);
      client.publish({
        destination: `/pub/chat/${params.roomId}`,
        body: publishMessageBody,
        headers: {
          'content-type': 'application/json',
          // Authorization: user.accessToken ?? '',
        },
      });
    }
  };

  return (
    <ChatRoomContainer>
      <Header title="채팅방" isBackArrow />
      <ChatWrapper chatDatas={liveChats} roomId={params.roomId!} />
      <Footer>
        <ChatInputBar onKeyDown={handleSendMessage} />
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
