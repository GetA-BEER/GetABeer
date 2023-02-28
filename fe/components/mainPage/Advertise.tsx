import { Swiper, SwiperSlide } from 'swiper/react';
import Image from 'next/image';
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/navigation';
import { Pagination, Navigation, Autoplay } from 'swiper';
export default function Advertise() {
  return (
    <Swiper
      pagination={true}
      // navigation={true}
      loop={true}
      modules={[Pagination, Navigation, Autoplay]}
      autoplay={{ delay: 2000 }}
      className="mySwiper -mt-6"
    >
      <SwiperSlide className="w-full">
        <Image
          className="w-full bg-y-gold"
          src={`/images/advertise/main.png`}
          alt="adv1"
          width={500}
          height={500}
          priority
        />
      </SwiperSlide>
      <SwiperSlide>
        <Image
          className="w-full"
          src={`/images/advertise/main2.png`}
          alt="adv2"
          width={500}
          height={500}
          priority
        />
      </SwiperSlide>
      <SwiperSlide>
        <Image
          className="w-full"
          src={`/images/advertise/search.png`}
          alt="adv3"
          width={500}
          height={500}
          priority
        />
      </SwiperSlide>
      <SwiperSlide>
        <Image
          className="w-full"
          src={`/images/advertise/rating.png`}
          alt="adv4"
          width={500}
          height={500}
          priority
        />
      </SwiperSlide>
      <SwiperSlide>
        <Image
          className="w-full"
          src={`/images/advertise/pairing.png`}
          alt="adv5"
          width={500}
          height={500}
          priority
        />
      </SwiperSlide>
      <SwiperSlide>
        <Image
          className="w-full"
          src={`/images/advertise/map.png`}
          alt="adv6"
          width={500}
          height={500}
          priority
        />
      </SwiperSlide>
    </Swiper>
  );
}
