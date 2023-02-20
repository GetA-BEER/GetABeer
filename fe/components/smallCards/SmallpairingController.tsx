import SmallPairingCard from './SmallPairingCard';
import React, { useState, useEffect } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper';
import 'swiper/css';
import 'swiper/css/pagination';
import Link from 'next/link';

export default function SmallCardController(props: { pairProps: any }) {
  const [smallPairingList, setSmallPairingList] = useState<any>(
    props?.pairProps
  );
  console.log('smallPairingList?.length', smallPairingList.length);
  useEffect(() => {
    setSmallPairingList(props?.pairProps);
  }, [props.pairProps]);

  return (
    <>
      {smallPairingList?.length === 0 ? (
        <div className="noneContent">등록된 페어링이 없습니다</div>
      ) : (
        <Swiper
          className="w-full h-fit"
          slidesPerView={2.2}
          spaceBetween={10}
          modules={[Pagination]}
        >
          {smallPairingList?.map((pairingProps: any, idx: number) => (
            <SwiperSlide key={pairingProps.pairingId}>
              <Link href={`/pairing/${pairingProps.pairingId}`}>
                <SmallPairingCard pairingProps={pairingProps} />
              </Link>
            </SwiperSlide>
          ))}
        </Swiper>
      )}
    </>
  );
}
