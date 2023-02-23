import Footer from '@/components/Footer';
import MiddleCard, { testBeer } from '@/components/middleCards/MiddleCard';
import SearchCard from '@/components/middleCards/SearchCard';
import MonthlyCard from '@/components/middleCards/MonthlyCard';
import Pagenation from '@/components/Pagenation';
import { useState } from 'react';
import CommentInput from '@/components/inputs/CommentInput';
import PageContainer from '@/components/PageContainer';
import Link from 'next/link';

export default function Test() {
  const [page, setPage] = useState<number>(1);
  const [input, setInput] = useState('');

  const post = () => {
    // console.log(input);
  };

  return (
    <PageContainer>
      <Link href={{ pathname: '/search', query: { q: '라거' } }}>
        <div>라거 검색결과</div>
      </Link>
      <Pagenation page={page} setPage={setPage} totalPages={10} />
      <MiddleCard cardProps={testBeer} />
      {/* <SearchCard cardProps={testBeer} idx={1} />
      <MonthlyCard cardProps={testBeer} idx={1} /> */}
      <CommentInput
        inputState={input}
        setInputState={setInput}
        postFunc={post}
      />
    </PageContainer>
  );
}
