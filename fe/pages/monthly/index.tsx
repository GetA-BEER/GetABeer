import MonthlyCard, {
  MonthlyCardProps,
} from '@/components/middleCards/MonthlyCard';
import PageContainer from '@/components/PageContainer';
import axios from '@/pages/api/axios';
import Link from 'next/link';
import { useEffect, useState } from 'react';

export default function MonthlyPage() {
  const [monthlyBeerList, setMonthlyBeerList] = useState<MonthlyCardProps[]>(
    []
  );
  useEffect(() => {
    axios.get('/beers/monthly').then((res) => {
      console.log(res.data);
      setMonthlyBeerList(res.data);
    });
  }, []);
  return (
    <PageContainer>
      <div className="flex flex-col justify-center items-center my-6">
        <h1 className="font-bold text-2xl sm:text-3xl lg:text-4xl mb-3">
          이달의 맥주
        </h1>
        <h2 className="text-sm sm:text-[16px] lg:text-lg text-y-gray">
          지난 달 가장 높은 점수를 받은 이달의 맥주를 만나보세요
        </h2>
      </div>
      {monthlyBeerList.map((el, idx) => (
        <Link key={el.beerId} href={`/beer/${el.beerId}`}>
          <MonthlyCard cardProps={el} idx={idx} />
        </Link>
      ))}
    </PageContainer>
  );
}
