import styled from 'styled-components';

interface ProfileProps {
  imgUrl: string;
  userName: string | null;
  userId: number | null;
}
function ProfileBox({ imgUrl, userName, userId }: ProfileProps) {
  return (
    <Container>
      <ProfileImg src={imgUrl} />
      <InfoContainer>
        <Name>{userName}</Name>
        <ID>{userId}</ID>
      </InfoContainer>
    </Container>
  );
}

export default ProfileBox;

const Container = styled.div`
  width: 100%;
  height: 74px;
  display: flex;
  gap: 12px;
  padding: 12px 18px;
`;
const ProfileImg = styled.img`
  width: 15%;
  border-radius: 50%;
  padding: 2px 2px;
`;
const InfoContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;
`;
const Name = styled.p`
  font-weight: 600;
`;
const ID = styled.p`
  font-size: var(--font-size-xs);
  color: rgba(0, 0, 0, 0.5);
  font-weight: 400;
`;
