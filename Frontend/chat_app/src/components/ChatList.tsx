import styled from 'styled-components';

const ChatList = () => {
  return (
    <MainContainer>
      <ProfileImg />
      <InfoWrapper>
        <UserName>Athalia Putri</UserName>
      </InfoWrapper>
    </MainContainer>
  );
};

export default ChatList;

const MainContainer = styled.div``;
const ProfileImg = styled.image``;
const InfoWrapper = styled.div``;
const UserName = styled.p``;
const RecentMessage = styled.div``;
const TimeStamp = styled.p``;
const CountBadge = styled.div``;
