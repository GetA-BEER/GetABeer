import PopularBeer from './PopularBeer';
import React, { useState } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';

import 'swiper/css';
import 'swiper/css/pagination';

import { Pagination } from 'swiper';

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

export default function PopularBeerController(props: {
  beerProps: BeerInfo[];
}) {
  const [beerList, setBeerList] = useState<BeerInfo[]>(props.beerProps);
  return (
    <>
      <div className="mx-3 mt-6 mb-2 text-base font-semibold">
        <span className="text-y-brown">인기많은</span>
        <span className="text-black">맥주</span>
      </div>
      <div className="w-full">
        <Swiper
          className="w-full h-fit"
          slidesPerView={2.4}
          spaceBetween={20}
          modules={[Pagination]}
        >
          {beerList?.map((popularBeer: BeerInfo, idx: number) => (
            <SwiperSlide key={popularBeer.id}>
              <PopularBeer popularBeer={popularBeer} idx={idx} />
            </SwiperSlide>
          ))}
        </Swiper>
      </div>
    </>
  );
}
