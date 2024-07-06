import styled from 'styled-components';

interface MainProps {
  $marginTop: string;
}
interface SubHeading {
  $margin: string;
}
interface Button {
  $margin?: string;
}
export const Main = styled.div<MainProps>`
  margin-top: ${(props) => props.$marginTop};
  padding: 0 12px;
  overflow-y: auto;
  flex: 1;
`;

export const Input = styled.input`
  padding: 10px;
  font-size: var(--font-size-md);
  border: 1px solid #ccc;
  border-radius: 4px;
`;
export const SubHeading = styled.div<SubHeading>`
  font-weight: 600;
  font-size: var(--font-size-lg);
  line-height: 24px;
  padding: 0 12px;
  margin: ${(props) => props.$margin};
`;

export const Button = styled.button<Button>`
  display: flex;
  height: 40px;
  margin: ${(props) => props.$margin || '8px 12px'};
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 8px;
  background: #000;
  color: white;
`;
