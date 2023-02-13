import Image from 'next/image';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/pagination';
import 'swiper/css/navigation';
import { Pagination, Navigation } from 'swiper';

export default function PairingImageCarousel(props: { imageList: any }) {
  return (
    <Swiper
      pagination={true}
      navigation={true}
      loop={true}
      modules={[Pagination, Navigation]}
      className="bg-y-lightGray overflow-hidden"
    >
      {props.imageList === undefined ? (
        <></>
      ) : (
        <div>
          {props.imageList.map((el: any) => (
            <SwiperSlide key={el.pairingImageId}>
              <div className=" h-full m-auto h-74">
                <Image
                  src={el.imageUrl}
                  alt="imageList"
                  width={300}
                  height={300}
                  className="border-4 border-red-500 bg-cover"
                  priority
                />
              </div>
            </SwiperSlide>
          ))}
        </div>
      )}
    </Swiper>
  );
}
