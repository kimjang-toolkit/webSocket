import styled from 'styled-components';

interface HeaderProps {
  title: string;
}

const Header: React.FC<HeaderProps> = ({ title }) => {
  return <HeaderContainer>{title}</HeaderContainer>;
};

export default Header;

const HeaderContainer = styled.header`
  width: 100%;
  display: flex;
  justify-content: space-between;
  background: aqua;
  height: 50px;
  color: #fff;
  align-items: center;
`;
