import { useEffect } from 'react';
import { Outlet, useSubmit } from 'react-router-dom';
import Cookies from 'js-cookie';
import { useSelector } from 'react-redux';
import { RootState } from '@/redux/store';
import { initializeWebSocket } from '@/redux/webSocketSlice';
import { useDispatch } from 'react-redux';
import { AppDispatch } from '@/redux/store';

function RootLayout() {
  const token = Cookies.get('refreshToken');
  const submit = useSubmit();
  const user = useSelector((state: RootState) => state.user);
  const dispatch = useDispatch<AppDispatch>();

  useEffect(() => {
    if (token === 'EXPIRED') {
      submit(null, { action: '/logout', method: 'post' });
      console.log('Expired');
    }

    if (user.id && user.accessToken) {
      dispatch(initializeWebSocket({ userId: user.id, accessToken:user.accessToken }));
    }
  }, [token, submit]);

  return (
    <>
      {/* {navigation.state === 'loading' && <p>Loading...</p>} */}
      <Outlet />
    </>
  );
}

export default RootLayout;
