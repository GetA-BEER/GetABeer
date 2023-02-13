import SmallPairingCard from './SmallPairingCard';
import React, { useState } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper';
import 'swiper/css';
import 'swiper/css/pagination';
import Link from 'next/link';

// export interface PairingCardInfo {
//   id: number;
//   pairing: string;
//   nickName: string;
//   description?: string;
//   date: string;
//   comments: number;
//   thumbs: number;
//   image?: string;
// }

export default function SmallCardController(props: { pairProps: any }) {
  const [smallPairingList, setSmallPairingList] = useState<any>(
    props.pairProps
  );

  return (
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
  );
}
