import SimilarBeer from './SimilarBeer';
import React, { useState } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper';
import 'swiper/css';
import 'swiper/css/pagination';

export interface BeerInfo {
  id: number;
  idx?: number;
  title: string;
  category: string;
  country: string;
  level: number;
  ibu: number;
  image: string;
}

export default function SimilarBeerController(props: {
  beerProps: BeerInfo[];
}) {
  const BeerList = [
    {
      id: 1,
      title: '가든 바이젠',
      category: '에일',
      country: '한국',
      level: 4.1,
      ibu: 17.5,
      image: 'https://worldbeermarket.kr/userfiles/prdimg/2101060009_M.jpg',
    },
    {
      id: 2,
      title: '필라이트',
      category: '에일',
      country: '한국',
      level: 4.1,
      ibu: 17.5,
      image: 'https://worldbeermarket.kr/userfiles/prdimg/2211160004_R.jpg',
    },
    {
      id: 3,
      title: '가든 바이젠',
      category: '에일',
      country: '한국',
      level: 4.1,
      ibu: 17.5,
      image: 'https://worldbeermarket.kr/userfiles/prdimg/2011190018_M.jpg',
    },
  ];
  const [beerList, setBeerList] = useState<BeerInfo[]>(props.beerProps);
  return (
    <>
      <div className="flex justify-between mx-5 mb-4 mt-6 font-semibold text-sm">
        비슷한 맥주
      </div>
      <div className="w-full">
        <Swiper
          className="w-full h-fit"
          slidesPerView={2.4}
          spaceBetween={20}
          modules={[Pagination]}
        >
          {beerList?.map((similarBeer: BeerInfo, idx: number) => (
            <SwiperSlide key={similarBeer.id}>
              <SimilarBeer similarBeer={similarBeer} />
            </SwiperSlide>
          ))}
        </Swiper>
      </div>
    </>
  );
}
