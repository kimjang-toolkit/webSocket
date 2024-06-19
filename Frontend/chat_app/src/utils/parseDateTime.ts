import { ParsedDateTime } from '@/types/types';

export function ParsedDateTime(dateTime: string): ParsedDateTime {
  const date = new Date(dateTime);
  date.setHours(date.getHours() + 9);
  return {
    year: date.getFullYear(),
    month: date.getMonth(),
    day: date.getDay(),
    hour: date.getHours(),
    min: date.getMinutes(),
    sec: date.getSeconds(),
  };
}
