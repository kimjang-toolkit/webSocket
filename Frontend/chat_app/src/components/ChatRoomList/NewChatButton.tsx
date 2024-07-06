import { Button } from '@/styles/Common';

interface NewChatButtonProps {
  onClick: () => void;
}

function NewChatButton({ onClick }: NewChatButtonProps) {
  return <Button onClick={onClick}>채팅방 생성</Button>;
}

export default NewChatButton;
