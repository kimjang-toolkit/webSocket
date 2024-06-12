import { Client } from '@stomp/stompjs';
import ChatBubble from '../components/ChatBubble';
import styled from 'styled-components';
import ChatInputBar from '../components/ChatInputBar';
function Home() {
  const message = {
    type: 'greeting',
    chat: 'Hello, world!',
  };
  const publishMessageBody = JSON.stringify(message);
  const client = new Client({
    brokerURL: `${import.meta.env.VITE_BROKER_URL}/gs-guide-websocket`,
    debug: (str) => {
      console.log(str);
    },
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });

  client.onConnect = () => {
    console.log('websocket이 열렸습니다.');
    client.subscribe('/topic/greetings', (frame) => {
      console.log(`받았습니다 ${frame.body}`);
    });
    client.publish({
      destination: '/app/hello',
      body: publishMessageBody,
      headers: {
        'content-type': 'application/json',
      },
    });
  };
  client.activate();

  return (
    <ChatRoom>
      <ChatContainer>
        <ChatBubble
          isUsers={true}
          data={{ content: 'Hello world This is fucking crazy night you', published: '1640' }}
        />
        <ChatBubble isUsers={false} data={{ content: 'Hello world', published: '1640' }} />
        <ChatBubble isUsers={false} data={{ content: 'Hello world', published: '1640' }} />
        <ChatBubble isUsers={false} data={{ content: 'Hello world', published: '1640' }} />
      </ChatContainer>
      <ChatInputBar />
    </ChatRoom>
  );
}

export default Home;

const ChatRoom = styled.main`
  display: flex;
  flex-direction: column;
  background: aqua;
`;
const ChatContainer = styled.section`
  background: gray;
  display: flex;
  flex-direction: column;
`;
