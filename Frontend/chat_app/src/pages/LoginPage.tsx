import { useState } from 'react';
import styled from 'styled-components';
import { Main } from '@/styles/Common';
import { authenticateUser } from '@/apis/authentication';
import { useDispatch } from 'react-redux';
import { AppDispatch } from '@/redux/store';
import { setUser } from '@/redux/userSlice';
import { useNavigate } from 'react-router-dom';

function LoginPage() {
  let navigate = useNavigate();
  const [userEmail, setUserEmail] = useState('');
  const [password, setPassword] = useState('');
  const dispatch = useDispatch<AppDispatch>();
  const handleLogin = async () => {
    const response = await authenticateUser({ userEmail, password });
    if (response) {
      const { userData, jwtToken } = response;
      dispatch(setUser({ ...userData, accessToken: jwtToken }));
      navigate('/', { replace: true });
      setUserEmail('');
      setPassword('');
    }
  };

  const handleRegister = () => {};

  return (
    <Main $marginTop="15%">
      <Container>
        <Title>로그인</Title>
        <Form>
          <Input type="email" placeholder="이메일" value={userEmail} onChange={(e) => setUserEmail(e.target.value)} />
          <Input
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <Button onClick={handleLogin}>로그인</Button>
          <Button secondary onClick={handleRegister}>
            회원가입
          </Button>
        </Form>
      </Container>
    </Main>
  );
}

export default LoginPage;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Title = styled.h1`
  font-weight: 700;
  font-size: var(--font-size-xl);
  text-align: center;
  margin-bottom: 20px;
`;

const Form = styled.div`
  display: flex;
  flex-direction: column;
  width: 300px;
  gap: 10px;
`;

const Input = styled.input`
  padding: 10px;
  font-size: var(--font-size-md);
  border: 1px solid #ccc;
  border-radius: 4px;
`;

const Button = styled.button<{ secondary?: boolean }>`
  padding: 10px;
  font-size: var(--font-size-md);
  font-weight: bold;
  color: ${({ secondary }) => (secondary ? '#007BFF' : '#fff')};
  background-color: ${({ secondary }) => (secondary ? '#fff' : '#007BFF')};
  border: ${({ secondary }) => (secondary ? '1px solid #007BFF' : 'none')};
  border-radius: 4px;
  cursor: pointer;
  transition:
    background-color 0.3s,
    color 0.3s;

  &:hover {
    background-color: ${({ secondary }) => (secondary ? '#007BFF' : '#0056b3')};
    color: ${({ secondary }) => (secondary ? '#fff' : '#fff')};
  }
`;
