import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import GlobalStyle from '@styles/GlobalStyle';
import ChatRoomPage from '@/pages/ChatRoomPage';
import { useDispatch } from 'react-redux';
import { AppDispatch } from '@/redux/store';
import { useEffect } from 'react';
import { initializeWebSocket } from '@/redux/webSocketSlice';
import ChatListPage from '@/pages/ChatListPage';

const router = createBrowserRouter([
  {
    path: '/',
    element: <ChatListPage />,
  },
  { path: '/chat', element: <ChatRoomPage /> },
]);
function App() {
  const dispatch = useDispatch<AppDispatch>();

  useEffect(() => {
    dispatch(initializeWebSocket());
  }, [dispatch]);

  return (
    <>
      <GlobalStyle />
      <RouterProvider router={router} />
    </>
  );
}

export default App;
