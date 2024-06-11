import { Client, Message } from '@stomp/stompjs';

function Home() {
  const message = {
    type: 'greeting',
    chat: 'Hello, world!',
  };
  const publishMessageBody = JSON.stringify(message);
  const client = new Client({
    brokerURL: 'ws://ec2-3-36-116-219.ap-northeast-2.compute.amazonaws.com/gs-guide-websocket',
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

  return <div></div>;
}

export default Home;
