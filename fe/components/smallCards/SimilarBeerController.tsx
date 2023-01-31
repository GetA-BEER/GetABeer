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

export default function SimilarBeerController({ beerProps }: any) {
  const [beerList, setBeerList] = useState<BeerInfo[]>(beerProps);
  return (
    <>
      <div className="m-4 text-base font-semibold">비슷한 맥주</div>
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
                <SimilarBeer {...props} key={props.id} />
              </SwiperSlide>
              {/* {(props.idx = index)} */}
            </>
          ))}
        </Swiper>
      </div>
    </>
  );
}
