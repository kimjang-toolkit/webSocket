import { fetchItems } from '@/apis/item';
import { useInfiniteQuery } from '@tanstack/react-query';
import { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { useInView } from 'react-intersection-observer';

function TestPage() {
  const { data, error, status, fetchNextPage, isFetchingNextPage } = useInfiniteQuery({
    queryKey: ['items'],
    queryFn: fetchItems,
    initialPageParam: 0,
    getNextPageParam: (lastPage) => lastPage.nextPage,
  });

  const [isFirstRender, setIsFirstRender] = useState(true);
  const { ref, inView } = useInView({
    threshold: 0.5,
    rootMargin: '100px',
  });
  const scrollContainerRef = useRef(null);

  useEffect(() => {
    if (isFirstRender && scrollContainerRef.current) {
      setTimeout(() => {
        scrollContainerRef.current.scrollTop = scrollContainerRef.current.scrollHeight;
      }, 0); // DOM이 완전히 렌더링된 후 스크롤을 맨 아래로 이동

      setIsFirstRender(false); // 첫 렌더링 이후 상태를 변경
      return; // 첫 렌더링 시에는 fetchNextPage를 호출하지 않음
    }

    if (inView) {
      fetchNextPage();
      console.log('fetching');
    }
  }, [inView, fetchNextPage, isFirstRender]);

  return status === 'pending' ? (
    <div>Loading...</div>
  ) : status === 'error' ? (
    <div>{error.message}</div>
  ) : (
    <Container ref={scrollContainerRef}>
      {data?.pages.map((page) => {
        return (
          <PageWrapper key={page.currentPage}>
            {page.data.map((item) => {
              return <Card key={item.id}>{item.name}</Card>;
            })}
          </PageWrapper>
        );
      })}
      <div ref={ref}>{isFetchingNextPage && 'Loading'}</div>
    </Container>
  );
}

export default TestPage;

const Card = styled.div`
  border-radius: 20px;
  background-color: gray;
  padding: 20px;
`;
const PageWrapper = styled.div`
  background: white;
  display: flex;
  flex-direction: column-reverse;
  gap: 1rem;
`;
const Container = styled.div`
  display: flex;
  flex-direction: column-reverse;
  gap: 1rem;
  height: 100vh;
  overflow-y: auto;
`;
