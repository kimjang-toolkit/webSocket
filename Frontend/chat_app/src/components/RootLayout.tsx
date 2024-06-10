import { Outlet } from 'react-router-dom';
import styled from 'styled-components';

const RootLayout: React.FC = () => {
  return (
    <Container>
      <Header>
        <Nav>{/* 네비게이션 링크 */}</Nav>
      </Header>
      <Main>
        <Outlet />
      </Main>
      <Footer>{/* 푸터 */}</Footer>
    </Container>
  );
};

export default RootLayout;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  min-height: 100vh;
`;

const Header = styled.header`
  background: #333;
  color: #fff;
  padding: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;

  @media (max-width: 768px) {
    flex-direction: column;
  }
`;

const Nav = styled.nav`
  display: flex;
  gap: 1rem;

  @media (max-width: 768px) {
    flex-direction: column;
    gap: 0.5rem;
  }
`;

const Main = styled.main`
  flex: 1;
  padding: 1rem;

  @media (max-width: 768px) {
    padding: 0.5rem;
  }
`;

const Footer = styled.footer`
  background: #333;
  color: #fff;
  padding: 1rem;
  text-align: center;

  @media (max-width: 768px) {
    padding: 0.5rem;
  }
`;
