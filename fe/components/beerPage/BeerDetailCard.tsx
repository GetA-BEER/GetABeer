import Image from 'next/image';
import Link from 'next/link';
import { HiPencil, HiChartPie, HiShare } from 'react-icons/hi';
import { AiOutlineHeart } from 'react-icons/ai';
import { useRecoilState } from 'recoil';
import { currentBeer } from '@/atoms/currentBeer';
import { useEffect } from 'react';
import StarScore from './StarScore';

export const testBeer: any = {
  beerId: 1,
  beerDetailsBasic: {
    korName: '수정된 이름',
    engName: 'updated Name',
    country: '한국',
    thumbnail: 'https://worldbeermarket.kr/userfiles/prdimg/2101060009_M.jpg',
    abv: 4.5,
    ibu: 25,
  },
  beerCategoryTypes: ['WEIZEN', 'DUNKEL'],
  beerDetailsTopTags: null,
  beerDetailsStars: {
    totalAverageStars: 3.3,
    femaleAverageStars: 0.5,
    maleAverageStars: 2.5,
  },
  beerDetailsCounts: {
    totalStarCount: 50,
    femaleStarCount: 30,
    maleStarCount: 20,
    ratingCount: 15,
    pairingCount: 10,
  },
  isWishListed: null,
  similarBeers: [],
};

export default function BeerDetailCard({ cardProps }: any) {
  const [curBeer, setCurBeer] = useRecoilState(currentBeer);
  useEffect(() => {
    setCurBeer(testBeer);
  });
  console.log(curBeer);
  return (
    <div className="flex rounded-xl bg-white text-y-black border border-y-lightGray py-2 my-2 relative">
      <div className="flex m-auto">
        <Image
          className="pt-3 "
          alt={cardProps?.beerDetailsBasic.korName}
          src={cardProps?.beerDetailsBasic.thumbnail}
          width={100}
          height={200}
        />
        <div className="flex flex-col justify-center ml-1">
          <h1 className="font-bold text-2xl">
            {cardProps?.beerDetailsBasic.korName}
          </h1>
          <AiOutlineHeart className="absolute top-1 right-1.5 w-8 h-8" />
          <div className="text-xs flex flex-wrap">
            <span>
              {cardProps?.beerCategoryTypes.map((el: string, idx: number) => {
                return (
                  <span className="mr-0.5" key={idx}>
                    {el}
                  </span>
                );
              })}
            </span>
            <span className="mx-[1px]">
              / {cardProps?.beerDetailsBasic.country}
            </span>
            <span className="mx-[1px]">
              {cardProps?.beerDetailsBasic.abv}% /
            </span>
            <span>{cardProps?.beerDetailsBasic.ibu} IBU</span>
          </div>
          <div className="my-1 flex items-end">
            <StarScore score={cardProps?.beerDetailsStars.totalAverageStars} />
            <span className="text-xs text-y-gray">
              ({cardProps?.beerDetailsStars.totalAverageStars})
            </span>
          </div>
          <div className="mb-2 text-xs flex">
            <div className="mr-4">
              <Image
                src="/images/star.png"
                alt="star"
                width={13}
                height={13}
                className="mr-1 mb-[3px] text-y-gold drop-shadow-md inline"
              />
              {cardProps?.beerDetailsStars.femaleAverageStars} 여성
            </div>
            <div>
              <Image
                src="/images/star.png"
                alt="star"
                width={13}
                height={13}
                className="mr-1 mb-[3px] text-y-gold drop-shadow-md inline"
              />
              {cardProps?.beerDetailsStars.maleAverageStars} 남성
            </div>
          </div>
          <div className="text-xs">
            <Link href={'/postrating'} className="hover:text-y-gold mr-1">
              <HiPencil className="inline" /> 평가하기
            </Link>
            <Link href={'/postpairing'} className="hover:text-y-gold mr-1">
              <span className="mr-1">
                <HiChartPie className="inline" /> 페어링
              </span>
            </Link>
            <Link href={'/postpairing'} className="hover:text-y-gold mr-1">
              <span className="mr-1">
                <HiShare className="inline" /> 공유하기
              </span>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
}
