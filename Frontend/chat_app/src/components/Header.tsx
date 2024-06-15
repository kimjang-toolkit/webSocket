import styled from 'styled-components';
import BackArrow from '@assets/icons/backArrow.svg';
interface HeaderProps {
  title: string;
  isBackArrow: boolean;
}

const Header: React.FC<HeaderProps> = ({ isBackArrow, title }) => {
  return (
    <HeaderContainer>
      <button>
        <BackArrow />
      </button>
      {title}
    </HeaderContainer>
  );
};

export default Header;

const HeaderContainer = styled.header`
  width: 100%;
  display: flex;
  background: aqua;
  height: 50px;
  align-items: center;
`;
