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

export default function SimilarBeer({ similarBeer }: any) {
  const [beerInfo, setBeerInfo] = useState<any>();
  useEffect(() => {
    if (similarBeer !== undefined) setBeerInfo(similarBeer);
  }, [similarBeer]);

  return (
    <div className="w-full ">
      <div className="mx-3 mt-6 text-base font-semibold">
        <span className="text-black mr-1">비슷한</span>
        <span className="text-y-brown">맥주</span>
      </div>
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
                    {el?.beerCategories === null ||
                    el?.beerCategories.length === 0 ? (
                      <></>
                    ) : (
                      <span>
                        {el?.beerCategories.map(
                          (category: any, idx: number) => (
                            <span key={idx}>
                              {BeerCategoryMatcherToKor(
                                category.beerCategoryType
                              )}{' '}
                              /
                            </span>
                          )
                        )}
                      </span>
                    )}
                    <span className="ml-1">
                      {BeerCountryMatcherToKor(el?.country)} / {el?.abv}%
                    </span>
                    {el?.ibu === null ? <></> : <span>{el?.ibu}IBU</span>}
                  </div>
                </div>
                <Image
                  className="pt-3 rounded-2xl m-auto select-none"
                  alt="Beer"
                  src={el?.thumbnail}
                  width={300}
                  height={300}
                  priority
                />
              </div>
            </Link>
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
}
