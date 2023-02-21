import Image from 'next/image';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/navigation';
import { Pagination, Navigation } from 'swiper';

export default function PairingImageCarousel(props: { imageList: any }) {
  return (
    <>
      {props?.imageList?.length === 0 ? (
        <></>
      ) : (
        <Swiper
          pagination={true}
          navigation={true}
          loop={true}
          modules={[Pagination, Navigation]}
          className="overflow-clip h-72"
        >
          <div>
            {props?.imageList?.map((el: any) => (
              <SwiperSlide key={el.pairingImageId} className="">
                <Image
                  src={el.imageUrl}
                  alt="imageList"
                  width={300}
                  height={300}
                  className="m-auto h-full w-auto"
                  priority
                />
              </SwiperSlide>
            ))}
          </div>
        </Swiper>
      )}
    </>
  );
}
