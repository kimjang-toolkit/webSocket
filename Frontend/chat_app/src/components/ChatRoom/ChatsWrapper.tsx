import { chatFormat } from '@/types/types';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux/store';
import { Main } from '@/styles/Common';
import ChatLive from '@/components/ChatRoom/ChatLive';
import ChatHistory from '@/components/ChatRoom/ChatHistory';
import { useChatHistory } from '@/hooks/useChatHistory';
import { useInView } from 'react-intersection-observer';
import { useEffect, useRef, useState } from 'react';

interface ChatWrapperProps {
  chatDatas: chatFormat[];
  roomId: string;
}

function ChatWrapper({ chatDatas, roomId }: ChatWrapperProps) {
  const user = useSelector((state: RootState) => state.user);
  const { data, error, status, fetchNextPage, isFetchingNextPage } = useChatHistory({
    roomId: roomId ?? '',
    userId: user.id ?? 0,
    timeLine: 'past',
  });
  const [isFirstRender, setIsFirstRender] = useState(true);

  const { ref, inView } = useInView({
    threshold: 0.5,
    rootMargin: '100px',
  });
  const scrollContainerRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    if (isFirstRender && scrollContainerRef.current) {
      scrollContainerRef.current.scrollTop = 0; //col-reverse임으로 0일때 맨 아래이다.
      setIsFirstRender(false); // 첫 렌더링 이후 상태를 변경
      return; // 첫 렌더링 시에는 fetchNextPage를 호출하지 않음
    } else if (inView) {
      fetchNextPage();
      console.log('fetching');
    }
  }, [inView]);

  return (
    <Wrapper ref={scrollContainerRef} $marginTop="12px">
      <ChatLive userId={user.id!} chatDatas={chatDatas} />
      <ChatHistory userId={user.id!} chatDatas={data} error={error} status={status} />
      <div ref={ref}>{isFetchingNextPage && 'Loading'}</div>
    </Wrapper>
  );
}

export default ChatWrapper;

const Wrapper = styled(Main)`
  display: flex;
  flex-direction: column-reverse;
  background: white;
  gap: 8px;
`;
