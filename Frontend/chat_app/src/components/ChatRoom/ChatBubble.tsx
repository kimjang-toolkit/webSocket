import styled from 'styled-components';
type dataType = {
  content: string;
  createDate: string;
};

interface ChatBubbleProps {
  data: dataType;
  isOthers: boolean;
}

function ChatBubble({ data, isOthers }: ChatBubbleProps) {
  return (
    <>
      <Bubble $isOthers={isOthers}>
        <Content>{data.content}</Content>
        <Caption $isOthers={isOthers}>{data.createDate} Read</Caption>
      </Bubble>
    </>
  );
}

export default ChatBubble;
interface bubbleProps {
  $isOthers: boolean;
}
const Bubble = styled.div<bubbleProps>`
  background: ${(props) => (props.$isOthers ? 'rgba(240, 240, 240, 0.87)' : '#002de3')};
  color: ${(props) => (props.$isOthers ? '#000' : '#fff')};
  padding: 10px;
  border-radius: ${(props) => (props.$isOthers ? '16px 16px 16px 0px' : '16px 16px 0px 16px')};
  margin: 2px 0;
  width: 60%;
  max-height: 50%;
  align-self: ${(props) => (props.$isOthers ? 'flex-start' : 'flex-end')};
  display: flex;
  flex-direction: column;
`;
const Content = styled.p`
  word-break: break-all;
`;
const Caption = styled.p<bubbleProps>`
  text-align: ${(props) => (props.$isOthers ? 'left' : 'right')};
  color: ${(props) => (props.$isOthers ? '#9C9C9C' : '#E1E1E1')};
  font-size: var(--font-size-xs);
`;
