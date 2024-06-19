import { ParsedDateTime } from '@/types/types';

export function ParsedDateTime(dateTime: string): ParsedDateTime {
  const date = new Date(dateTime);
  return {
    year: date.getFullYear(),
    month: date.getMonth(),
    day: date.getDay(),
    hour: date.getHours(),
    min: date.getMinutes(),
    sec: date.getSeconds(),
  };
}
