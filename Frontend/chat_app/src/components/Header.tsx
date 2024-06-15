import styled from 'styled-components';
import BackArrowIcon from '@assets/icons/backArrow.svg';
interface HeaderProps {
  title: string;
  isBackArrow: boolean;
}

const Header: React.FC<HeaderProps> = ({ isBackArrow, title }) => {
  return (
    <HeaderContainer>
      <BackButton>{isBackArrow && <BackArrowIcon />}</BackButton>
      <Title>{title}</Title>
    </HeaderContainer>
  );
};

export default Header;

const HeaderContainer = styled.header`
  width: 100%;
  display: flex;
  height: 50px;
  background: #fff;
  box-shadow: 0px 0px 6px 0px rgba(0, 0, 0, 0.12);
  padding: 12px 8px;
  gap: 16px;
`;
const BackButton = styled.button`
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 0;

  svg {
    width: 24px;
    height: 24px;
  }
`;

const Title = styled.p`
  padding-top: 1.5px;
  font-size: 18px;
  font-weight: bold;
  color: #333;
`;
