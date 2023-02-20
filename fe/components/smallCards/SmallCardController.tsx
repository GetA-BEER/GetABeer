import SmallCard from './SmallCard';
import React, { useEffect, useState } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper';
import Image from 'next/image';
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
        <div className="noneContent">
          <Image
            className="m-auto pb-3 opacity-50"
            src="/images/logo.png"
            alt="logo"
            width={40}
            height={40}
          />
          등록된 평가가 없습니다.
        </div>
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
