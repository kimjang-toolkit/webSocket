import styled from 'styled-components';
import HomeIcon from '@assets/icons/home.svg';
import RecentChatIcon from '@assets/icons/recentChat.svg';
import AlarmIcon from '@assets/icons/alarm.svg';

function Navbar() {
  return (
    <NavbarContainer>
      <NavItem>
        <HomeIcon />
        <NavItemText>홈</NavItemText>
      </NavItem>
      <NavItem>
        <RecentChatIcon />
        <NavItemText>최근 채팅방</NavItemText>
      </NavItem>
      <NavItem>
        <AlarmIcon />
        <NavItemText>알람!</NavItemText>
      </NavItem>
    </NavbarContainer>
  );
}

export default Navbar;

const NavbarContainer = styled.nav`
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  height: 50px;
  background: #fff;
  box-shadow: 0px 0px 6px 0px rgba(0, 0, 0, 0.12);
`;
const NavItemText = styled.p`
  font-size: var(--font-size-xs);
  font-weight: 600;
`;
const NavItem = styled.button`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
`;
