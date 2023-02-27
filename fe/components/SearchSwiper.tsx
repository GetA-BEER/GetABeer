import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import { useRouter } from 'next/router';
import { SearchMatcherToEng } from '@/utils/SearchMatcher';

type SearchSwiperProps = {
  list: string[];
  setIsSearching: React.Dispatch<React.SetStateAction<boolean>>;
};

export default function SearchSwiper({
  list,
  setIsSearching,
}: SearchSwiperProps) {
  const router = useRouter();
  return (
    <ul className="font-light">
      <Swiper spaceBetween={5} slidesPerView={4.5}>
        {list.map((el, idx) => {
          return (
            <SwiperSlide key={idx}>
              <li
                className="bg-y-cream rounded-lg p-0.5"
                onClick={() => {
                  router.push({
                    pathname: '/search',
                    query: { q: SearchMatcherToEng(el) },
                  });
                  setIsSearching(false);
                }}
              >
                {el}
              </li>
            </SwiperSlide>
          );
        })}
      </Swiper>
    </ul>
  );
}
