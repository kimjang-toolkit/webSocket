import { fetchItems } from '@/apis/item';
import { useInfiniteQuery } from '@tanstack/react-query';
import { useEffect } from 'react';
import styled from 'styled-components';
import { useInView } from 'react-intersection-observer';
function TestPage() {
  const { data, error, status, fetchNextPage, isFetchingNextPage } = useInfiniteQuery({
    queryKey: ['items'],
    queryFn: fetchItems,
    initialPageParam: 0,
    getNextPageParam: (lastPage) => lastPage.nextPage,
  });

  const { ref, inView } = useInView({
    threshold: 0.5,
    rootMargin: '300px',
  });

  useEffect(() => {
    if (inView) {
      fetchNextPage();
    }
  }, [inView, fetchNextPage]);

  return status === 'pending' ? (
    <div>Loading...</div>
  ) : status === 'error' ? (
    <div>{error.message}</div>
  ) : (
    <Container>
      <div ref={ref}>{isFetchingNextPage && 'Loading'}</div>
      {data?.pages.map((page) => {
        return (
          <PageWrapper key={page.currentPage}>
            {page.data.map((item) => {
              return <Card key={item.id}>{item.name}</Card>;
            })}
          </PageWrapper>
        );
      })}
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
  flex-direction: column;
  gap: 1rem;
`;
const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
`;
