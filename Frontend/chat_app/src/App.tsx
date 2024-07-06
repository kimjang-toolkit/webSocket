import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import GlobalStyle from '@styles/GlobalStyle';
import ChatRoomPage from '@/pages/ChatRoomPage';

import ChatRoomListPage from '@/pages/ChatRoomListPage';
import LoginPage from '@/pages/LoginPage';
import Modal from '@/components/Modal/Modal';

const router = createBrowserRouter([
  {
    path: '/',
    element: <ChatRoomListPage />,
  },
  { path: '/chat/:roomId', element: <ChatRoomPage /> },
  { path: '/login', element: <LoginPage /> },
]);
function App() {
  return (
    <>
      <GlobalStyle />
      <Modal />
      <RouterProvider router={router} />
    </>
  );
}

export default App;
