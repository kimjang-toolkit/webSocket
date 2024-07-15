import { getPresignedURL, putProfileImg } from '@/apis/user';
import { useRef } from 'react';
import styled from 'styled-components';

interface ProfileProps {
  imgUrl: string;
  userName: string | null;
  userId: number | null;
}
function ProfileBox({ imgUrl, userName, userId }: ProfileProps) {
  const fileInputRef = useRef<HTMLInputElement>(null);
  const handleUpdateImg = async () => {
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };
  const handleFileChange = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e?.target.files?.[0];
    if (file) {
      const presignedURL = await getPresignedURL(userId!);
      const res = await putProfileImg(presignedURL, file);
      console.log(res);
    }
  };
  return (
    <Container>
      <ProfileImgContainer onClick={handleUpdateImg}>
        <ProfileImg src={imgUrl} />
        <Overlay>
          <input
            ref={fileInputRef}
            type="file"
            style={{ display: 'none' }}
            accept="image/*"
            onChange={handleFileChange}
          />
          <OverlayText>사진선택</OverlayText>
        </Overlay>
      </ProfileImgContainer>
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
const ProfileImgContainer = styled.div`
  position: relative;
  width: 15%;
  border-radius: 50%;
  cursor: pointer;
  overflow: hidden;

  &:hover div {
    opacity: 1;
  }
`;

const ProfileImg = styled.img`
  width: 100%;
  border-radius: 50%;
`;

const Overlay = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  opacity: 0;
  transition: opacity 0.3s ease;
`;
const OverlayText = styled.span`
  color: white;
  font-size: 12px;
  font-weight: 600;
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
