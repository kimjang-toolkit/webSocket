import ChatList from '@/components/ChatList';
import Header from '@/components/Header';
import { Footer, Main } from '@/styles/Common';
import styled from 'styled-components';

const ChatListPage = () => {
  return (
    <ChatListContainer>
      <Header title="채팅방 리스트" isBackArrow={false} />
      <div> user</div>
      <Main>
        <ChatList />
      </Main>
      <Footer></Footer>
    </ChatListContainer>
  );
};

export default ChatListPage;

const ChatListContainer = styled.div``;
