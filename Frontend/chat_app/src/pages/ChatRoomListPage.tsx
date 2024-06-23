import ChatRoomCard from '@/components/ChatRoomList/ChatRoomCard';
import Header from '@/components/Header';
import Navbar from '@/components/Navbar';
import NewChatButton from '@/components/ChatRoomList/NewChatButton';
import ProfileBox from '@/components/ProfileBox';
import { RootState } from '@/redux/store';
import { Main } from '@/styles/Common';
import { useSelector } from 'react-redux';
import styled from 'styled-components';

const mockChatList = [
  { imgUrl: 'src/assets/images/맹구.jpg', userName: '맹구', recentMessage: '너무너무즐겁다', badgeCount: 3 },
  { imgUrl: 'src/assets/images/맹구.jpg', userName: '맹구', recentMessage: '너무너무즐겁다', badgeCount: 3 },
];
const mockProfile = {
  imgUrl: 'src/assets/images/맹구.jpg',
};
const ChatRoomListPage = () => {
  const user = useSelector((state: RootState) => state.user);
  return (
    <ChatListContainer>
      <Header title="채팅방 리스트" isBackArrow={false} />
      <ProfileBox imgUrl={mockProfile.imgUrl} userId={user.id} userName={user.name} />
      <SubHeading>Chats</SubHeading>
      <Main>
        {mockChatList.map((chatList, index) => (
          <ChatRoomCard
            key={index}
            imgUrl={chatList.imgUrl}
            userName={chatList.userName}
            recentMessage={chatList.recentMessage}
            badgeCount={chatList.badgeCount}
          />
        ))}
      </Main>
      <NewChatButton />
      <Navbar />
    </ChatListContainer>
  );
};

export default ChatRoomListPage;

const ChatListContainer = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;
const SubHeading = styled.div`
  font-weight: 600;
  font-size: var(--font-size-lg);
  line-height: 24px;
  padding: 0 12px;
`;
