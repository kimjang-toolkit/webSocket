import store from '@/redux/store';
import { initUser } from '@/redux/userSlice';
import { redirect } from 'react-router-dom';

export function action() {
  store.dispatch(initUser());
  return redirect('/login');
}
