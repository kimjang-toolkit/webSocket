export interface Customer {
  id: number;
  name: string;
}
export interface MessageFormat {
  roomId: number;
  content: string;
  customer: Customer;
}
