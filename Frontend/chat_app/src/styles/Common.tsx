import styled from 'styled-components';

interface MainProps {
  $marginTop: string;
}
interface SubHeading {
  $margin: string;
}
export const Main = styled.div<MainProps>`
  margin-top: ${(props) => props.$marginTop};
  padding: 0 12px;
  overflow-y: auto;
  flex: 1;
`;

export const SubHeading = styled.div<SubHeading>`
  font-weight: 600;
  font-size: var(--font-size-lg);
  line-height: 24px;
  padding: 0 12px;
  margin: ${(props) => props.$margin};
`;
