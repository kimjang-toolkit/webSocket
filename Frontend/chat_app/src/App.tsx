import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import GlobalStyle from '@styles/GlobalStyle';
import ChatRoomPage from '@/pages/ChatRoomPage';
import ChatRoomListPage from '@/pages/ChatRoomListPage';
import LoginPage from '@/pages/LoginPage';
import RootLayout from '@/pages/Root';
import { action as logoutAction } from '@/pages/Logout';

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    //errorElement: <ErrorPage/>,
    id: 'root',
    children: [
      { index: true, element: <ChatRoomListPage /> },
      {
        path: 'login',
        element: <LoginPage />,
      },
      { path: 'chat/:roomId', element: <ChatRoomPage /> },
      {
        path: 'logout',
        action: logoutAction,
      },
    ],
  },
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
