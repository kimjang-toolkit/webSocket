import ChatRoomCard from '@/components/ChatRoomList/ChatRoomCard';
import Header from '@/components/Header';
import Navbar from '@/components/Navbar';
import NewChatButton from '@/components/ChatRoomList/NewChatButton';
import ProfileBox from '@/components/ChatRoomList/ProfileBox';
import { RootState } from '@/redux/store';
import { Main, SubHeading } from '@/styles/Common';
import { useSelector } from 'react-redux';
import styled from 'styled-components';
import SelectParticipants from '@/components/SelectParticipants';
import { useEffect } from 'react';
import { initializeWebSocket } from '@/redux/webSocketSlice';
import { useDispatch } from 'react-redux';
import { AppDispatch } from '@/redux/store';

const mockChatList = [
  { imgUrl: 'src/assets/images/맹구.jpg', userName: '맹구', recentMessage: '너무너무즐겁다', badgeCount: 3 },
  { imgUrl: 'src/assets/images/맹구.jpg', userName: '맹구', recentMessage: '너무너무즐겁다', badgeCount: 3 },
];
const mockProfile = {
  imgUrl: 'src/assets/images/맹구.jpg',
};
const ChatRoomListPage = () => {
  const dispatch = useDispatch<AppDispatch>();
  const user = useSelector((state: RootState) => state.user);
  useEffect(() => {
    dispatch(initializeWebSocket());
  }, [dispatch]);

  return (
    <ChatListContainer>
      <Header title="채팅방 리스트" isBackArrow={false} />
      <ProfileBox imgUrl={mockProfile.imgUrl} userId={user.id} userName={user.name} />
      <SubHeading $margin="0px 0px 8px 0px">Chats</SubHeading>
      <Main $marginTop="0px">
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
      <SelectParticipants />
    </ChatListContainer>
  );
};

export default ChatRoomListPage;

const ChatListContainer = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;
