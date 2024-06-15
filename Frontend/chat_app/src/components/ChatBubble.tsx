import styled from 'styled-components';

type dataType = {
  content: string;
  published: string;
};

interface ChatBubbleProps {
  data: dataType;
  isUsers: boolean;
}

const ChatBubble: React.FC<ChatBubbleProps> = ({ data, isUsers }) => {
  const hours = data.published.slice(0, 2);
  const minutes = data.published.slice(2, 4);
  return (
    <>
      <Bubble isUsers={isUsers}>
        <Content>{data.content}</Content>
        <Caption isUsers={isUsers}>
          {hours}:{minutes} Read
        </Caption>
      </Bubble>
    </>
  );
};

export default ChatBubble;

const Bubble = styled.div<{ isUsers: boolean }>`
  background: ${({ isUsers }) => (isUsers ? 'rgba(240, 240, 240, 0.87)' : '#002de3')};
  color: ${({ isUsers }) => (isUsers ? '#000' : '#fff')};
  padding: 10px;
  border-radius: ${({ isUsers }) => (isUsers ? '16px 16px 16px 0px' : '16px 16px 0px 16px')};
  width: 60%;
  max-height: 50%;
  align-self: ${({ isUsers }) => (isUsers ? 'flex-start' : 'flex-end')};
  display: flex;
  flex-direction: column;
`;
const Content = styled.p`
  word-break: break-all;
`;
const Caption = styled.p<{ isUsers: boolean }>`
  text-align: ${({ isUsers }) => (isUsers ? 'left' : 'right')};
  color: ${({ isUsers }) => (isUsers ? '#9C9C9C' : '#E1E1E1')};
  font-size: var(--font-size-xs);
`;
