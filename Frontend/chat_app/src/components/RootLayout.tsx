import { Outlet } from 'react-router-dom';
import styled from 'styled-components';

const RootLayout: React.FC = () => {
  return (
    <Container>
      <Header />
      <Main>
        <Outlet />
      </Main>
      <Footer>{/* 푸터 */}</Footer>
    </Container>
  );
};

export default RootLayout;

const Container = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: #ffffff;
`;

const Header = styled.header`
  position: fixed;
  top: 0;
  width: 100%;
  display: flex;
  justify-content: space-between;
  z-index: 1000;
  background: aqua;
  // height: 50px;
  color: #fff;
  align-items: center;
`;

const Main = styled.main`
  padding: 0 12px;
  overflow-y: auto;
`;

const Footer = styled.footer`
  position: fixed;
  bottom: 0;
  width: 100%;
  z-index: 1000;
  background: aqua;
  color: #fff;
  text-align: center;
  height: 50px;
`;
