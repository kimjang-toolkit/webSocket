import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import GlobalStyle from '@styles/GlobalStyle';
import ChatRoomPage from '@/pages/ChatRoomPage';
import { useDispatch } from 'react-redux';
import { AppDispatch } from '@/redux/store';
import { useEffect } from 'react';
import { initializeWebSocket } from '@/redux/webSocketSlice';
import ChatListPage from '@/pages/ChatListPage';
import { setUser } from '@/redux/userSlice';

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
    //임시 테스트를 위해 로컬스토리지로 
    const name = localStorage.getItem('userName');
    const id = Number(localStorage.getItem('userId'));
    dispatch(setUser({ name, id }));
  }, [dispatch]);

  return (
    <>
      <GlobalStyle />
      <RouterProvider router={router} />
    </>
  );
}

export default App;
