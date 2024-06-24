import Header from '@/components/Header';
import { SubHeading } from '@/styles/Common';
import styled from 'styled-components';

function SelectParticipants() {
  return (
    <Container>
      <Header title="대화상대 선택" isBackArrow={true} />
      <SubHeading $margin="12px 0px 8px 0px">친구</SubHeading>
    </Container>
  );
}

export default SelectParticipants;

const Container = styled.section`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: white;
`;
