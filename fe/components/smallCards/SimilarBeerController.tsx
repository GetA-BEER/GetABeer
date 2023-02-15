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
  const [beerList, setBeerList] = useState<BeerInfo[]>(props.beerProps);
  return (
    <>
      <div className="flex justify-between mx-5 mt-6 font-semibold text-sm">
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
