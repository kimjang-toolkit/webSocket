import styled from 'styled-components';
import AddFileButton from '@assets/icons/addFileButton.svg';
import SendButton from '@assets/icons/sendButton.svg';
import { useEffect, useState } from 'react';
import { RootState } from '@/redux/store';
import { useSelector } from 'react-redux';
import { MessageFormat } from '@/types/types';
// import useDebounce from '@/hooks/useDebounce';

function ChatInputBar() {
  const [message, setMessage] = useState('');
  const { client } = useSelector((state: RootState) => state.webSocket);
  // const debouncedMessage = useDebounce(message, 300);
  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setMessage(event.target.value);
  };

  const handleSendMessage = () => {
    if (client) {
      const messageFormat: MessageFormat = {
        roomId: 1,
        content: message,
        customer: {
          id: 1,
          name: '효승이',
        },
      };
      const publishMessageBody = JSON.stringify(messageFormat);

      client.publish({
        destination: '/pub/chat/1',
        body: publishMessageBody,
        headers: {
          'content-type': 'application/json',
        },
      });
    }
  };
  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (event.key === 'Enter') {
      handleSendMessage();
      setMessage('');
    }
  };
  useEffect(() => {}, []);
  return (
    <ChatInputContainer>
      <AddFileButton />
      <InputBar value={message} onChange={handleChange} onKeyDown={handleKeyDown} placeholder="메시지를 입력하세요" />
      <SendButton />
    </ChatInputContainer>
  );
}

export default ChatInputBar;

const ChatInputContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 30px;
`;
const InputBar = styled.input`
  border-radius: 3.727px;
  background: #f7f7fc;
  width: 259.935px;
  padding: 5.59px 7.453px;
`;
