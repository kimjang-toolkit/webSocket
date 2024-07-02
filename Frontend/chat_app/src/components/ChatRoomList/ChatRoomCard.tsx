import styled from 'styled-components';

export interface ChatRoomCardProps {
  imgUrl: string;
  roomId: number;
  roomName: string;
  memberCnt: number;
  lastChatTime: string;
  lastContent: string;
  unreadCnt: number;
  onClick?: () => void;
}

const ChatRoomCard: React.FC<ChatRoomCardProps> = ({
  imgUrl,
  roomId,
  roomName,
  lastContent,
  lastChatTime,
  memberCnt,
  unreadCnt,
  onClick,
}) => {
  return (
    <CardContainer onClick={onClick}>
      <ProfileImg src={imgUrl} />
      <InfoContainer>
        <InfoWrapper>
          <RoomName>{roomName}</RoomName>
          <TimeStamp>now</TimeStamp>
        </InfoWrapper>
        <InfoWrapper>
          <LastContent>{lastContent}</LastContent>
          <BadgeCount>{unreadCnt}</BadgeCount>
        </InfoWrapper>
      </InfoContainer>
    </CardContainer>
  );
};

export default ChatRoomCard;

const CardContainer = styled.div`
  display: flex;
  width: 100%;
  height: 56px;
  gap: 12px;
  padding: 4px 6px 4px 4px;
  margin: 2px 0;
  &:hover {
    background: #e9e9e9c1;
    cursor: pointer;
  }
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
  align-items: center;
`;
const RoomName = styled.p`
  font-size: 14px;
  font-style: normal;
  font-weight: 600;
  line-height: 24px;
`;
const LastContent = styled.div`
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
  width: 16px;
  color: #001a83;
  font-size: 10px;
  font-weight: 600;
  line-height: 16px;
  border-radius: 40px;
  background: #d2d5f9;
`;
