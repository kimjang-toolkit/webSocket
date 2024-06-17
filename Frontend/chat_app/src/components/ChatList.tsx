import styled from 'styled-components';

const ChatList = () => {
  return (
    <MainContainer>
      <ProfileImg src="src/assets/images/맹구.jpg" />
      <InfoContainer>
        <InfoWrapper>
          <UserName>Athalia Putri</UserName>
          <TimeStamp>now</TimeStamp>
        </InfoWrapper>
        <InfoWrapper>
          <RecentMessage>Good morning, did you sleep well?</RecentMessage>
          <CountBadge>3</CountBadge>
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
`;
const ProfileImg = styled.img`
  border-radius: 16px;
  padding: 2px 2px;
`;
const InfoContainer = styled.div`
  display: flex;
  flex-direction: column;
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
const CountBadge = styled.div`
  display: flex;
  align-items: center;

  color: #001a83;
  font-size: 10px;
  font-weight: 600;
  line-height: 16px;
  border-radius: 40px;
  background: #d2d5f9;
`;
