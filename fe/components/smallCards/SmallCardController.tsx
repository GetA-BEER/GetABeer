import SmallCard from './SmallCard';
import React, { useState } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper';
import 'swiper/css';
import 'swiper/css/pagination';

// export interface SmallCardInfo {
//   id: number;
//   star: number;
//   nickName: string;
//   description?: string;
//   date: string;
//   comments: number;
//   thumbs: number;
//   tags: string[];
// }

export default function SmallCardController(props: { cardProps: any }) {
  const [cardPropsList, setCardPropsList] = useState<any>(props.cardProps);
  return (
    <Swiper
      className="w-full h-fit"
      slidesPerView={2.2}
      spaceBetween={10}
      modules={[Pagination]}
    >
      {cardPropsList?.map((cardProps: any) => (
        <SwiperSlide key={cardProps.ratingId}>
          <SmallCard cardProps={cardProps} />
        </SwiperSlide>
      ))}
    </Swiper>
  );
}
