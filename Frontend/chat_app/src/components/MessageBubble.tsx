import React from 'react';
import styled from 'styled-components';
interface MessageProps {
  content: string;
  sender: string;
}

const MessageBubble: React.FC<MessageProps> = ({ content, sender }) => {
  return <Bubble sender={sender}>{content}</Bubble>;
};

export default MessageBubble;

const Bubble = styled.div<{ sender: string }>`
  background: ${(sender) => (sender ? '#002de3' : ' rgba(240, 240, 240, 0.87)')};
  color: ${({ sender }) => (sender ? '#fff' : '#000')};
  padding: 10px;
  border-radius: ${({ sender }) => (sender ? '16px 16px 16px 0px' : '16px 16px 0px 16px')};
  margin: 10px 0;
  width: 60%;
  max-height: 50%;
  align-self: ${({ sender }) => (sender ? 'flex-end' : 'flex-start')};
`;
