import styled from 'styled-components';

interface ParticipantsCardProps {
  imgUrl: string;
  userName: string;
  checked: boolean;
}

function ParticipantsCard({ imgUrl, userName, checked }: ParticipantsCardProps) {
  return (
    <MainContainer>
      <ProfileImg />
      <UserName />
    </MainContainer>
  );
}

export default ParticipantsCard;

const MainContainer = styled.div`
  display: grid;
  grid-template-columns: 1fr 3fr 1fr;
  width: 100%;
  height: 56px;
  gap: 12px;
  margin: 2px 0;
`;

const ProfileImg = styled.img`
  border-radius: 16px;
  padding: 2px 2px;
`;
const UserName = styled.p`
  font-size: 14px;
  font-style: normal;
  font-weight: 600;
  line-height: 24px;
`;
