import { useEffect } from 'react';
import { Outlet, useSubmit } from 'react-router-dom';
import Cookies from 'js-cookie';

function RootLayout() {
  const token = Cookies.get('refreshToken');
  const submit = useSubmit();

  useEffect(() => {
    if (token === 'EXPIRED') {
      submit(null, { action: '/logout', method: 'post' });
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
