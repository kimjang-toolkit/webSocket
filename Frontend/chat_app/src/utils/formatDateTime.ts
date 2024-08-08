export function formatDateTime(dateTimeStr: string) {
  const inputDate = new Date(dateTimeStr);
  const now = new Date();
  const isThisYear = inputDate.getFullYear() === now.getFullYear();
  const isToday = isThisYear && inputDate.getMonth() === now.getMonth() && inputDate.getDate() === now.getDate();

  if (isToday) {
    const hours = inputDate.getHours();
    const minutes = inputDate.getMinutes().toString().padStart(2, '0');
    const period = hours >= 12 ? '오후' : '오전';
    const formattedHours = hours % 12 === 0 ? 12 : hours % 12;
    return `${period} ${formattedHours}:${minutes}`;
  } else {
    const year = inputDate.getFullYear();
    const month = inputDate.getMonth() + 1; // 월은 0부터 시작하므로 1을 더함
    const day = inputDate.getDate();
    return `${isThisYear ? `${year}년` : year} ${month}월 ${day}일`;
  }
}
