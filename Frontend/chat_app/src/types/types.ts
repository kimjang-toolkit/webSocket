export interface ParsedDateTimeProps {
  year: number;
  month: number;
  day: number;
  hour: number;
  min: number;
  sec: number;
}

export interface User {
  id: number;
  name: string;
}

//서버에 보내는 데이터
export interface MessageFormat {
  roomId: number;
  content: string;
  sender: User;
}
//서버에서 받은 메시지데이터 가공
export interface chatFormat {
  roomId: number;
  content: string;
  sender: User;
  createDate: ParsedDateTimeProps;
}
