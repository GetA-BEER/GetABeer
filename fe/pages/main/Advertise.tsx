import { Swiper, SwiperSlide } from 'swiper/react';
import Image from 'next/image';
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/navigation';
import { Pagination, Navigation } from 'swiper';
export default function Advertise() {
  return (
    <Swiper
      pagination={{
        dynamicBullets: true,
      }}
      // navigation={true}
      modules={[Pagination, Navigation]}
      className="mySwiper"
    >
      <SwiperSlide>
        <Image
          className="w-full"
          src="https://t1.daumcdn.net/cfile/tistory/99D743505B5482502A"
          alt="adv1"
          width={500}
          height={500}
          priority
        />
      </SwiperSlide>
      <SwiperSlide>
        <Image
          className="w-full"
          src="https://t1.daumcdn.net/cfile/tistory/9959DC505B5482521B"
          alt="adv2"
          width={500}
          height={500}
          priority
        />
      </SwiperSlide>
      <SwiperSlide>
        <Image
          className="w-full"
          src="https://t1.daumcdn.net/cfile/tistory/9921B8475B54825706"
          alt="adv3"
          width={500}
          height={500}
          priority
        />
      </SwiperSlide>
    </Swiper>
  );
}
