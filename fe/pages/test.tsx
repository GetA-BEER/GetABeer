import Footer from '@/components/Footer';
import MiddleCard, { testBeer } from '@/components/middleCards/MiddleCard';
import MonthlyCard from '@/components/middleCards/MonthlyCard';
import BigCard from '@/components/middleCards/BigCard';
import Pagenation from '@/components/Pagenation';
import { useState } from 'react';
import CommentInput from '@/components/inputs/CommentInput';
import PageContainer from '@/components/PageContainer';

export default function Test() {
  const [page, setPage] = useState<number>(1);
  const [input, setInput] = useState('');

  const post = () => {
    console.log(input);
  };

  return (
    <PageContainer>
      <Pagenation page={page} setPage={setPage} totalPages={10} />
      <MiddleCard cardProps={testBeer} />
      <MonthlyCard cardProps={testBeer} idx={1} />
      <BigCard cardProps={testBeer} />
      <CommentInput
        inputState={input}
        setInputState={setInput}
        postFunc={post}
      />
    </PageContainer>
  );
}
