import Image from 'next/image';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/navigation';
import { Pagination, Navigation } from 'swiper';
import { useState } from 'react';

export default function PairingImageCarousel() {
  // const [imageList, setImageList] = useState(imageProps?.imageProps);
  console.log(11111111111111111);
  // console.log('imageList', imageList);
  return (
    <Swiper
      pagination={true}
      // navigation={true}
      loop={true}
      modules={[Pagination, Navigation]}
      className="mySwiper"
    >
      rrrrrrrrrrrrrr
      {/* {imageList === undefined ? (
        <></>
      ) : (
        <>
          {imageList[0]?.image1 === undefined ? (
            <></>
          ) : (
            <SwiperSlide>
              <Image
                className="w-4/5 m-auto"
                src={imageList[0]?.image1}
                alt="adv1"
                width={250}
                height={250}
              />
              <div className="absolute text-white left-20 bottom-10 text-2xl">
                여기에 Get A Beer 첫번째 광고가 들어갑니다
              </div>
            </SwiperSlide>
          )}
          {imageList[0]?.image2 === undefined ? (
            <></>
          ) : (
            <SwiperSlide>
              <Image
                className="w-4/5 m-auto"
                src={imageList[0]?.image2}
                alt="adv2"
                width={250}
                height={250}
              />
              <div className="absolute text-white left-20 bottom-10 text-2xl">
                여기에 Get A Beer 두번째 광고가 들어갑니다
              </div>
            </SwiperSlide>
          )}
          {imageList[0]?.image3 === undefined ? (
            <></>
          ) : (
            <SwiperSlide>
              <Image
                className="w-4/5 m-auto"
                src={imageList[0]?.image3}
                alt="adv3"
                width={300}
                height={300}
              />
              <div className="absolute text-white left-20 bottom-10 text-2xl">
                여기에 Get A Beer 세번째 광고가 들어갑니다
              </div>
            </SwiperSlide>
          )}
        </>
      )} */}
    </Swiper>
  );
}
