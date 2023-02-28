import {
  BeerCountryMatcherToKor,
  BeerCategoryMatcherToKor,
} from '@/utils/BeerMatcher';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/pagination';
import { Pagination } from 'swiper';
import Link from 'next/link';
import Image from 'next/image';
import { useState, useEffect } from 'react';
import { RecommendBeerType } from '@/utils/TypeInfo';

export default function RecommendBeer({
  recommendBeer,
}: RecommendBeerType[] | any) {
  const [beerInfo, setBeerInfo] = useState<RecommendBeerType[] | undefined>();
  useEffect(() => {
    if (recommendBeer !== undefined) setBeerInfo(recommendBeer);
  }, [recommendBeer]);

  return (
    <div className="w-full ">
      <Swiper
        className="w-full h-fit"
        slidesPerView={2.4}
        spaceBetween={20}
        modules={[Pagination]}
      >
        {beerInfo?.map((el: any, idx: number) => (
          <SwiperSlide key={idx}>
            <Link href={`/beer/${el.beerId}`}>
              <div className="rounded-2xl w-full m-2 border bg-white text-y-black drop-shadow-lg text-[5px] overflow-hidden">
                <div
                  className={`${
                    idx % 2 === 0 ? 'bg-y-cream' : 'bg-y-lemon'
                  } p-4 rounded-t-2xl`}
                >
                  <div className="text-base font-semibold truncate">
                    {el?.korName}
                  </div>
                  <div className="truncate">
                    {el?.beerCategories === null ? (
                      <></>
                    ) : (
                      <span>{BeerCategoryMatcherToKor(el?.category)} / </span>
                    )}
                    {el?.country === null || el?.abv ? (
                      <></>
                    ) : (
                      <span>
                        {BeerCountryMatcherToKor(el?.country)} / {el?.abv}%
                      </span>
                    )}
                    {el?.ibu === null ? <></> : <span>{el?.ibu}IBU</span>}
                  </div>
                </div>
                {el?.thumbnail === null ? (
                  <></>
                ) : (
                  <Image
                    className="pt-3 rounded-2xl m-auto select-none"
                    alt="Beer"
                    src={el?.thumbnail}
                    width={300}
                    height={300}
                    priority
                  />
                )}
              </div>
            </Link>
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
}
