import { Swiper, SwiperSlide } from 'swiper/react';
import Image from 'next/image';
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/navigation';
import { Pagination, Navigation } from 'swiper';
export default function Advertise() {
  return (
    <Swiper
      pagination={true}
      // navigation={true}
      loop={true}
      modules={[Pagination, Navigation]}
      className="mySwiper -mt-6"
    >
      <SwiperSlide className="w-full">
        <Image
          className="w-full bg-y-gold"
          src={`/images/adv4.png`}
          alt="adv1"
          width={500}
          height={500}
          priority
        />
      </SwiperSlide>
      <SwiperSlide>
        <Image
          className="w-full"
          src={`/images/adv2.png`}
          alt="adv3"
          width={500}
          height={500}
          priority
        />
      </SwiperSlide>
      <SwiperSlide>
        <Image
          className="w-full"
          src={`/images/adv3.png`}
          alt="adv2"
          width={500}
          height={500}
          priority
        />
      </SwiperSlide>
    </Swiper>
  );
}
