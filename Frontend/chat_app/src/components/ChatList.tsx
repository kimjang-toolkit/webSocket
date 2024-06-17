import styled from 'styled-components';

interface ChatListProps {
  imgUrl: string;
  userName: string;
  // timeStamp: string
  recentMessage: string;
  badgeCount: number;
}

const ChatList: React.FC<ChatListProps> = ({ imgUrl, userName, recentMessage, badgeCount }) => {
  return (
    <MainContainer>
      <ProfileImg src={imgUrl} />
      <InfoContainer>
        <InfoWrapper>
          <UserName>{userName}</UserName>
          <TimeStamp>now</TimeStamp>
        </InfoWrapper>
        <InfoWrapper>
          <RecentMessage>{recentMessage}</RecentMessage>
          <BadgeCount>{badgeCount}</BadgeCount>
        </InfoWrapper>
      </InfoContainer>
    </MainContainer>
  );
};

export default ChatList;

const MainContainer = styled.div`
  display: flex;
  width: 100%;
  height: 56px;
  gap: 12px;
`;
const ProfileImg = styled.img`
  border-radius: 16px;
  padding: 2px 2px;
`;
const InfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;
`;
const InfoWrapper = styled.div`
  display: flex;
  justify-content: space-between;
`;
const UserName = styled.p`
  font-size: 14px;
  font-style: normal;
  font-weight: 600;
  line-height: 24px;
`;
const RecentMessage = styled.div`
  color: #adb5bd;
  font-size: 12px;
  font-weight: 400;
  line-height: 20px;
`;
const TimeStamp = styled.p`
  font-size: 10px;
  font-weight: 400;
  line-height: 16px;
  color: #a4a4a4;
`;
const BadgeCount = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 20px;
  color: #001a83;
  font-size: 10px;
  font-weight: 600;
  line-height: 16px;
  border-radius: 40px;
  background: #d2d5f9;
`;
