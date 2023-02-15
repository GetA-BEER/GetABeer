import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper';
import 'swiper/css';
import 'swiper/css/pagination';

export default function BeerCategoryBtn() {
  const beerCategoryList = [
    '에일',
    '라거',
    '밀맥주',
    '흑맥주',
    '필스너',
    '과일',
    '무알콜',
    '기타',
  ];
  //   ALE(),
  // LAGER(),
  // WEIZEN(),
  // DUNKEL(),
  // PILSENER(),
  // FRUIT_BEER(),
  // NON_ALCOHOLIC()
  return (
    <div className="my-4">
      <div className="mx-3 mt-6 mb-2 font-bold">
        맥주<span className="text-y-brown ml-1">카테고리</span>
      </div>
      <Swiper
        className="w-full h-fit"
        slidesPerView={5.5}
        spaceBetween={1}
        modules={[Pagination]}
      >
        <div className="select-none">
          {beerCategoryList.map((el: string, idx: number) => (
            <SwiperSlide key={idx}>
              <div className="ml-2 font-bold flex justify-center items-center h-[58px] lg:h-32 md:h-28 sm:h-24  bg-y-gold text-[10px] lg:text-md rounded-xl hover:text-y-cream">
                {el}
              </div>
            </SwiperSlide>
          ))}
        </div>
      </Swiper>
    </div>
  );
}
