import { useEffect, useState } from 'react';

function useDebounce<T>(value: T, delay: number): T {
  const [debounceValue, setDebounceValue] = useState<T>(value);

  useEffect(() => {
    const handler = setTimeout(() => {
      setDebounceValue(value);
    }, delay);
    //입력값이 변경될 때마다 delay 시간 후 debouncedValue업데이트
    return () => {
      clearTimeout(handler);
    };
  }, [value, delay]);
  return debounceValue;
}

export default useDebounce;
