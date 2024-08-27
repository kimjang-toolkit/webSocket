import ReactDOM from 'react-dom/client';
import App from '@/App';
import '@/index.css';
import { Provider } from 'react-redux';
import store from '@/redux/store';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

const queryClient = new QueryClient();

ReactDOM.createRoot(document.getElementById('root')!).render(
  <Provider store={store}>
    <QueryClientProvider client={queryClient}>
      <App />
    </QueryClientProvider>
  </Provider>,
);
