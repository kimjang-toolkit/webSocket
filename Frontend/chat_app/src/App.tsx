import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import GlobalStyle from '@styles/GlobalStyle';
import ChatRoomPage from '@/pages/ChatRoomPage';

import ChatRoomListPage from '@/pages/ChatRoomListPage';
import LoginPage from '@/pages/LoginPage';

import { chatLoader } from '@/pages/ChatRoomPage';

const router = createBrowserRouter([
  {
    path: '/',
    element: <ChatRoomListPage />,
  },
  { path: '/chat/:chat_roomID', element: <ChatRoomPage />, loader: chatLoader },
  { path: '/login', element: <LoginPage /> },
]);
function App() {
  return (
    <>
      <GlobalStyle />
      <RouterProvider router={router} />
    </>
  );
}

export default App;
