import SmallPairingCard from './SmallPairingCard';
import React, { useState, useEffect } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper';
import 'swiper/css';
import 'swiper/css/pagination';
import Link from 'next/link';
import Image from 'next/image';

export default function SmallCardController(props: { pairProps: any }) {
  const [smallPairingList, setSmallPairingList] = useState<any>(
    props?.pairProps
  );

  useEffect(() => {
    setSmallPairingList(props?.pairProps);
  }, [props.pairProps]);

  return (
    <>
      {smallPairingList?.length === 0 ? (
        <div className="noneContent">
          <Image
            className="m-auto pb-3 opacity-50"
            src="/images/logo.png"
            alt="logo"
            width={40}
            height={40}
          />
          등록된 페어링이 없습니다.
        </div>
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
