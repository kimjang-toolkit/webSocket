import styled from 'styled-components';
import AddFileButton from '@assets/icons/addFileButton.svg';
import SendButton from '@assets/icons/sendButton.svg';
import { useState } from 'react';

// import useDebounce from '@/hooks/useDebounce';

interface ChatInputBarProps {
  onKeyDown: (message: string) => void;
}

function ChatInputBar({ onKeyDown }: ChatInputBarProps) {
  const [message, setMessage] = useState('');
  // const debouncedMessage = useDebounce(message, 300);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setMessage(event.target.value);
  };

  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (event.key === 'Enter') {
      onKeyDown(message);
      setMessage('');
    }
  };
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
  padding: 8px 12px;
`;
const InputBar = styled.input`
  border-radius: 3.727px;
  background: #f7f7fc;
  width: 259.935px;
  padding: 5.59px 7.453px;
`;
