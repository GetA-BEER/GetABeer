import SmallBeer from './SmallBeer';
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

export default function SmallBeerController({ beerProps }: any) {
  const [beerList, setBeerList] = useState<BeerInfo[]>(beerProps);
  return (
    <>
      <div className="m-4 text-base font-semibold">
        인기 많은 <span className="text-y-brown">맥주</span>
      </div>
      <div className="w-full">
        <Swiper
          className="w-full h-fit"
          slidesPerView={2.4}
          spaceBetween={20}
          modules={[Pagination]}
        >
          {beerList?.map((props: BeerInfo, index: number) => (
            <>
              <SwiperSlide key={props.id}>
                <SmallBeer {...props} key={props.id} />
              </SwiperSlide>
              {(props.idx = index)}
            </>
          ))}
        </Swiper>
      </div>
    </>
  );
}
