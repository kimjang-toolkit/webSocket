import ChatRoomCard, { ChatRoomCardProps } from '@/components/ChatRoomList/ChatRoomCard';
import Header from '@/components/Header';
import Navbar from '@/components/Navbar';
import NewChatButton from '@/components/ChatRoomList/NewChatButton';
import ProfileBox from '@/components/ChatRoomList/ProfileBox';
import { RootState } from '@/redux/store';
import { Main, SubHeading } from '@/styles/Common';
import { useSelector } from 'react-redux';
import styled from 'styled-components';
import { useDispatch } from 'react-redux';
import { AppDispatch } from '@/redux/store';
import { useQuery } from '@tanstack/react-query';
import { fetchChatList } from '@/apis/chat';
import { useNavigate } from 'react-router-dom';
import { openModal } from '@/redux/modalSlice';
import Modal from '@/components/Modal/Modal';

const mockProfile = {
  imgUrl: 'src/assets/images/맹구.jpg',
};
const ChatRoomListPage = () => {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const user = useSelector((state: RootState) => state.user);
  const {
    data: chatList,
    error,
    isLoading,
  } = useQuery({
    queryKey: ['chatList', user.id],
    queryFn: ({ queryKey }) => fetchChatList(queryKey[1] as number),
    enabled: !!user.id, // user.id가 있을 때만 쿼리를 활성화
  });

  const handleChatRoomClick = (roomId: number) => {
    navigate(`/chat/${roomId}`);
  };

  const handleCreateChatRoom = () => {
    dispatch(
      openModal({
        type: 'CREATE_CHAT_ROOM',
      }),
    );
  };
  if (isLoading) {
    return <p>Loading...</p>;
  }

  if (error) {
    return <p>Error loading chat list</p>;
  }

  return (
    <ChatListContainer>
      <Modal />
      <Header title="채팅방 리스트" isBackArrow={false} />
      <ProfileBox imgUrl={mockProfile.imgUrl} userId={user.id} userName={user.name} />
      <SubHeading $margin="0px 0px 8px 0px">Chats</SubHeading>
      <Main $marginTop="0px">
        {chatList?.map((chat: ChatRoomCardProps) => (
          <ChatRoomCard
            key={`${chat.roomId}asdf`}
            imgUrl="src/assets/images/맹구.jpg"
            roomName={chat.roomName}
            roomId={chat.roomId}
            lastContent={chat.lastContent}
            lastChatTime={chat.lastChatTime}
            memberCnt={chat.memberCnt}
            unreadCnt={chat.unreadCnt}
            onClick={() => handleChatRoomClick(chat.roomId)}
          />
        ))}
      </Main>
      <NewChatButton onClick={handleCreateChatRoom} />
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
