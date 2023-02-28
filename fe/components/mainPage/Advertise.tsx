import { Swiper, SwiperSlide } from 'swiper/react';
import Image from 'next/image';
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/navigation';
import { Pagination, Navigation, Autoplay } from 'swiper';
export default function Advertise() {
  const imageList = [
    `/images/advertise/main.png`,
    `/images/advertise/search.png`,
    `/images/advertise/rating.png`,
    `/images/advertise/pairing.png`,
    `/images/advertise/map.png`,
  ];
  return (
    <Swiper
      pagination={true}
      // navigation={true}
      loop={true}
      modules={[Pagination, Navigation, Autoplay]}
      autoplay={{ delay: 2000 }}
      className="mySwiper -mt-6"
    >
      {imageList.map((image: string, idx: number) => (
        <SwiperSlide className="w-full" key={idx}>
          <Image
            className="w-full bg-y-gold"
            src={image}
            alt="adv1"
            width={500}
            height={500}
            priority
          />
        </SwiperSlide>
      ))}
    </Swiper>
  );
}
