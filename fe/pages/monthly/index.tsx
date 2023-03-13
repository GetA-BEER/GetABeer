import MonthlyCard, {
  MonthlyCardProps,
} from '@/components/middleCards/MonthlyCard';
import PageContainer from '@/components/PageContainer';
import axios from '@/pages/api/axios';
import { useEffect, useState } from 'react';

export default function MonthlyPage() {
  const [monthlyBeerList, setMonthlyBeerList] = useState<MonthlyCardProps[]>(
    []
  );
  useEffect(() => {
    axios
      .get('/api/beers/monthly')
      .then((res) => {
        setMonthlyBeerList(res.data);
      })
      .catch((err) => console.log(err));
  }, []);
  return (
    <PageContainer>
      <div className="flex flex-col justify-center items-center my-6 mx-2">
        <h1 className="font-bold text-xl lg:text-2xl mb-3">이달의 맥주</h1>
        <h2 className="text-sm lg:text-[16px] text-y-gray">
          지난 달 가장 높은 점수를 받은 이달의 맥주를 만나보세요
        </h2>
      </div>
      {monthlyBeerList.map((el, idx) => (
        <MonthlyCard key={el.beerId} cardProps={el} idx={idx} />
      ))}
    </PageContainer>
  );
}
