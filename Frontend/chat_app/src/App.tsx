import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import GlobalStyle from '@styles/GlobalStyle';
import ChatRoomPage from '@/pages/ChatRoomPage';

import ChatRoomListPage from '@/pages/ChatRoomListPage';
import LoginPage from '@/pages/LoginPage';

const router = createBrowserRouter([
  {
    path: '/',
    element: <ChatRoomListPage />,
  },
  { path: '/chat', element: <ChatRoomPage /> },
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
