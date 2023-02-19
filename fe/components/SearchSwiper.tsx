import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';

type SearchSwiperProps = {
  list: string[];
};

export default function SearchSwiper({ list }: SearchSwiperProps) {
  return (
    <ul className="font-light">
      <Swiper spaceBetween={5} slidesPerView={4.5}>
        {list.map((el, idx) => {
          return (
            <SwiperSlide key={idx}>
              <li className="bg-y-cream rounded-lg p-0.5">{el}</li>
            </SwiperSlide>
          );
        })}
      </Swiper>
    </ul>
  );
}
