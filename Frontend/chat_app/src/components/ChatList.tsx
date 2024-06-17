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
  background: gray;
`;
const ProfileImg = styled.img`
  border-radius: 16px;
`;
const InfoContainer = styled.div`
  display: flex;
  flex-direction: cloumn;
`;
const InfoWrapper = styled.div`
  display: flex;
  justify-content: space-between;
`;
const UserName = styled.p``;
const RecentMessage = styled.div``;
const TimeStamp = styled.p``;
const CountBadge = styled.div``;
