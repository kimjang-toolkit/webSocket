import React from 'react';
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
      <Bubble $isUsers={isUsers}>
        <Content>{data.content}</Content>
        <Caption $isUsers={isUsers}>
          {hours}:{minutes} Read
        </Caption>
      </Bubble>
    </>
  );
};

export default ChatBubble;
interface bubbleProps {
  $isUsers: boolean;
}
const Bubble = styled.div<bubbleProps>`
  background: ${(props) => (props.$isUsers ? 'rgba(240, 240, 240, 0.87)' : '#002de3')};
  color: ${(props) => (props.$isUsers ? '#000' : '#fff')};
  padding: 10px;
  border-radius: ${(props) => (props.$isUsers ? '16px 16px 16px 0px' : '16px 16px 0px 16px')};
  margin: 10px 0;
  width: 60%;
  max-height: 50%;
  align-self: ${(props) => (props.$isUsers ? 'flex-start' : 'flex-end')};
  display: flex;
  flex-direction: column;
`;
const Content = styled.p`
  word-break: break-all;
`;
const Caption = styled.p<bubbleProps>`
  text-align: ${(props) => (props.$isUsers ? 'left' : 'right')};
  color: ${(props) => (props.$isUsers ? '#9C9C9C' : '#E1E1E1')};
  font-size: var(--font-size-xs);
`;
