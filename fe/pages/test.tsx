import Footer from '@/components/Footer';
import MiddleCard, { testBeer } from '@/components/middleCards/MiddleCard';
import MonthlyCard from '@/components/middleCards/MonthlyCard';
import BigCard from '@/components/middleCards/BigCard';
import Pagenation from '@/components/Pagenation';
import { useState } from 'react';
import CommentInput from '@/components/inputs/CommentInput';
import PageContainer from '@/components/PageContainer';
import axios from 'axios';

export default function Test() {
  const [page, setPage] = useState<number>(1);
  const [input, setInput] = useState('');

  const post = () => {
    console.log(input);
  };

  return (
    <PageContainer>
      <button
        onClick={() => {
          axios
            .get(`/43/search?query=레몬`)
            .then((res) => console.log(res))
            .catch((err) => console.log(err));
        }}
      >
        레몬
      </button>
      <button
        onClick={() => {
          axios
            .get(`/43/beers/1`)
            .then((res) => console.log(res))
            .catch((err) => console.log(err));
        }}
      >
        1번 맥주
      </button>
      <button
        onClick={() => {
          axios
            .get(`http://localhost:8080/api/search?query=자몽`)
            .then((res) => console.log(res))
            .catch((err) => console.log(err));
        }}
      >
        자몽
      </button>
      <button
        onClick={() => {
          axios
            .get(`http://server.getabeer.co.kr/api/search?query=청포도`)
            .then((res) => console.log(res))
            .catch((err) => console.log(err));
        }}
      >
        청포도
      </button>
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
