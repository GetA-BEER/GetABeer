import MonthlyCard from '@/components/middleCards/MonthlyCard';

export default function MonthlyPage() {
  const beerProps = [
    {
      id: 1,
      korName: '가든 바이젠',
      category: ['에일'],
      country: '한국',
      abv: 4.1,
      ibu: 17.5,
      totalAverageStars: 4.9,
      totalStarCount: 55,
      beerTags: ['금색', '단맛', '과일향', '탄산 강'],
      thumbnail: 'https://worldbeermarket.kr/userfiles/prdimg/2101060009_M.jpg',
    },
    {
      id: 2,
      korName: '필라이트',
      category: ['에일'],
      country: '한국',
      abv: 4.1,
      ibu: 17.5,
      totalAverageStars: 4.8,
      totalStarCount: 45,
      beerTags: ['금색', '단맛', '과일향', '탄산 강'],
      thumbnail: 'https://worldbeermarket.kr/userfiles/prdimg/2211160004_R.jpg',
    },
    {
      id: 3,
      korName: '가든 바이젠',
      category: ['에일'],
      country: '한국',
      abv: 4.1,
      ibu: 17.5,
      totalAverageStars: 4.7,
      totalStarCount: 45,
      beerTags: ['금색', '단맛', '과일향', '탄산 강'],
      thumbnail: 'https://worldbeermarket.kr/userfiles/prdimg/2011190018_M.jpg',
    },
    {
      id: 4,
      korName: '가든 바이젠',
      category: ['에일'],
      country: '한국',
      abv: 4.1,
      ibu: 17.5,
      totalAverageStars: 4.6,
      totalStarCount: 38,
      beerTags: ['금색', '단맛', '과일향', '탄산 강'],
      thumbnail: 'https://worldbeermarket.kr/userfiles/prdimg/2101060009_M.jpg',
    },
    {
      id: 5,
      korName: '필라이트',
      category: ['에일'],
      country: '한국',
      abv: 4.1,
      ibu: 17.5,
      totalAverageStars: 4.5,
      totalStarCount: 29,
      beerTags: ['금색', '단맛', '과일향', '탄산 강'],
      thumbnail: 'https://worldbeermarket.kr/userfiles/prdimg/2211160004_R.jpg',
    },
  ];
  return (
    <div className="m-auto h-screen max-w-4xl">
      <div className="flex flex-col justify-center items-center my-6">
        <h1 className="font-bold text-2xl sm:text-3xl lg:text-4xl mb-3">
          이달의 맥주
        </h1>
        <h2 className="text-sm sm:text-[16px] lg:text-lg text-y-gray">
          지난 달 가장 높은 점수를 받은 이달의 맥주를 만나보세요
        </h2>
      </div>
      {beerProps.map((el, idx) => (
        <MonthlyCard key={el.id} cardProps={el} idx={idx} />
      ))}
    </div>
  );
}
