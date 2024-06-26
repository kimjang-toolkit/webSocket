import ChatInputBar from '@/components/ChatRoom/ChatInputBar';
import ChatRoom from '@/components/ChatRoom/ChatRoom';
import Header from '@/components/Header';
import { useChatHistory } from '@/hooks/useChatHistory';
import { RootState } from '@/redux/store';
import { Main } from '@/styles/Common';
import { chatFormat } from '@/types/types';
import { ParsedDateTime } from '@/utils/parseDateTime';
import { useEffect, useRef, useState } from 'react';
import { useSelector } from 'react-redux';
import { useLoaderData } from 'react-router-dom';
import styled from 'styled-components';

export async function chatLoader({ params }: any) {
  const { data, error, isLoading, fetchNextPage, hasNextPage } = await useChatHistory({
    roomId: params.chat_roomID,
    timeLine: 'past',
  });
  return {data, error, isLoading, fetchNextPage, hasNextPage}
}

function ChatRoomPage() {
  const { client, isConnected } = useSelector((state: RootState) => state.webSocket);
  const [liveChats, setLiveChats] = useState<chatFormat[]>([]);

  // const { data, error, isLoading, fetchNextPage, hasNextPage } = useChatHistory({
  //   roomId: 303,
  //   timeLine: 'past',
  // });
  const {data, error, isLoading, fetchNextPage, hasNextPage}: any = useLoaderData();

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

  if (isLoading) return <p>Loading,.,</p>;
  if (error) return <p>Error loading chat history</p>;
  return (
    <ChatRoomContainer>
      <Header title="채팅방" isBackArrow />
      <Main $marginTop="12px">
        <ChatRoom chatDatas={liveChats} loadMoreRef={loadMoreRef} data={data} />
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
