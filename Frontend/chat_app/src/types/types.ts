export interface ParsedDateTime {
  year: number;
  month: number;
  day: number;
  hour: number;
  min: number;
  sec: number;
}

export interface Customer {
  id: number;
  name: string;
}

//서버에서 보내는 데이터
export interface MessageFormat {
  roomId: number;
  content: string;
  customer: Customer;
}
//서버에서 받은 메시지데이터 가공
export interface chatFormat {
  content: string;
  userId: number;
  createdDate: ParsedDateTime;
}
