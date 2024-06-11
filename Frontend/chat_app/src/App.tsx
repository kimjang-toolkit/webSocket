import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Home from './pages/Home';
import RootLayout from './components/RootLayout';
import GlobalStyle from './styles/GlobalStyle';

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    children: [
      {
        path: '',
        element: <Home />,
      },
    ],
  },
]);
function App() {
  return (
    <>
      <GlobalStyle />
      <RouterProvider router={router} />;
    </>
  );
}

export default App;
