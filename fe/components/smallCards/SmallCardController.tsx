import SmallCard from './SmallCard';
import React, { useEffect, useState } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper';
import 'swiper/css';
import 'swiper/css/pagination';
import Link from 'next/link';

export default function SmallCardController(props: { cardProps: any }) {
  const [cardPropsList, setCardPropsList] = useState<any>(props?.cardProps);
  useEffect(() => {
    setCardPropsList(props?.cardProps);
  }, [props.cardProps]);

  return (
    <>
      {cardPropsList?.length === 0 ? (
        <div className="noneContent">등록된 코멘트가 없습니다</div>
      ) : (
        <Swiper
          className="w-full h-fit"
          slidesPerView={2.2}
          spaceBetween={10}
          modules={[Pagination]}
        >
          {cardPropsList?.map((cardProps: any) => (
            <SwiperSlide key={cardProps.ratingId}>
              <Link href={`/rating/${cardProps.ratingId}`}>
                <SmallCard cardProps={cardProps} />
              </Link>
            </SwiperSlide>
          ))}
        </Swiper>
      )}
    </>
  );
}
