import styled from 'styled-components';

function Navbar() {
  return (
    <NavbarContainer>
      <NavItem></NavItem>
    </NavbarContainer>
  );
}

export default Navbar;

const NavbarContainer = styled.nav`
  display: flex;
  width: 100%;
  height: 50px;
  background: #fff;
  box-shadow: 0px 0px 6px 0px rgba(0, 0, 0, 0.12);
`;
const NavItem = styled.button``;
