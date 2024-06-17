import ChatList from '@/components/ChatList';
import Header from '@/components/Header';
import { Footer, Main } from '@/styles/Common';
import styled from 'styled-components';

const mockChatList = [
  { imgUrl: 'src/assets/images/맹구.jpg', userName: '맹구', recentMessage: '너무너무즐겁다', badgeCount: 3 },
  { imgUrl: 'src/assets/images/맹구.jpg', userName: '맹구', recentMessage: '너무너무즐겁다', badgeCount: 3 },
  { imgUrl: 'src/assets/images/맹구.jpg', userName: '맹구', recentMessage: '너무너무즐겁다', badgeCount: 3 },
  { imgUrl: 'src/assets/images/맹구.jpg', userName: '맹구', recentMessage: '너무너무즐겁다', badgeCount: 3 },
  { imgUrl: 'src/assets/images/맹구.jpg', userName: '맹구', recentMessage: '너무너무즐겁다', badgeCount: 3 },
  { imgUrl: 'src/assets/images/맹구.jpg', userName: '맹구', recentMessage: '너무너무즐겁다', badgeCount: 3 },
];

const ChatListPage = () => {
  return (
    <ChatListContainer>
      <Header title="채팅방 리스트" isBackArrow={false} />
      <div> user</div>
      <Main>
        {mockChatList.map((chatList) => (
          <ChatList
            imgUrl={chatList.imgUrl}
            userName={chatList.userName}
            recentMessage={chatList.recentMessage}
            badgeCount={chatList.badgeCount}
          />
        ))}
      </Main>
      <Footer></Footer>
    </ChatListContainer>
  );
};

export default ChatListPage;

const ChatListContainer = styled.div``;
