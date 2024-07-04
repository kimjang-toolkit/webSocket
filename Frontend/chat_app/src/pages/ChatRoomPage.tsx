import ChatInputBar from '@/components/ChatRoom/ChatInputBar';
import ChatsWrapper from '@/components/ChatRoom/ChatsWrapper';
import Header from '@/components/Header';
import { useChatHistory } from '@/hooks/useChatHistory';
import { RootState } from '@/redux/store';
import { Main } from '@/styles/Common';
import { chatFormat } from '@/types/types';
import { ParsedDateTime } from '@/utils/parseDateTime';
import { useEffect, useRef, useState } from 'react';
import { useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { MessageFormat } from '@/types/types';

import styled from 'styled-components';

function ChatRoomPage() {
  const { client, isConnected } = useSelector((state: RootState) => state.webSocket);
  const [liveChats, setLiveChats] = useState<chatFormat[]>([]);
  const params = useParams();
  const user = useSelector((state: RootState) => state.user);
  const { data, error, isLoading, fetchNextPage, hasNextPage } = useChatHistory({
    roomId: params.roomId ?? '',
    userId: user.id ?? 0,
    timeLine: 'past',
  });

  const loadMoreRef = useRef<HTMLDivElement | null>(null);
  useEffect(() => {
    if (loadMoreRef.current) {
      const observer = new IntersectionObserver(
        ([entry]) => {
          if (entry.isIntersecting && hasNextPage) {
            fetchNextPage();
          }
        },
        { threshold: 1.0 },
      );
      observer.observe(loadMoreRef.current);
      return () => observer.disconnect();
    }
  }, [fetchNextPage, hasNextPage]);
  useEffect(() => {
    if (client && isConnected) {
      if (client.connected) {
        console.log('conneected?');
      }
      try {
        const subscription = client.subscribe(`/sub/chat/${params.roomId}`, (message) => {
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
        if (subscription) {
          console.log('subscribed');
          // subscription.unsubscribe();
        }
      } catch (error) {
        console.error('Error subscribing to topic:', error);
      }
    } else {
      console.warn('Client is not connected or client is null.');
    }
  }, [params.roomId]);

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
        destination: '/pub/chat/303',
        body: publishMessageBody,
        headers: {
          'content-type': 'application/json',
          Authorization: user.accessToken ?? '',
        },
      });
    }
  };

  if (isLoading) return <p>Loading,.,</p>;
  if (error) return <p>Error loading chat history</p>;
  return (
    <ChatRoomContainer>
      <Header title="채팅방" isBackArrow />
      <Main $marginTop="12px">
        <ChatsWrapper chatDatas={liveChats} loadMoreRef={loadMoreRef} data={data} />
      </Main>
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
