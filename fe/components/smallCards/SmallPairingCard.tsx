import Image from 'next/image';
import Link from 'next/link';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper';
import 'swiper/css';
import 'swiper/css/pagination';
import { BiUser } from 'react-icons/bi';
import { FaRegCommentDots } from 'react-icons/fa';
import { useRecoilValue } from 'recoil';
import { noReview, NoReviewTypes } from '@/atoms/noReview';
import { useEffect, useState } from 'react';
import { TimeHandler } from '@/utils/TimeHandler';
import { CategoryMatcherToKor } from '@/utils/CategryMatcher';
import PairingThumbs from '../PairingThumbs';

export default function SmallPairingCard(props: { pairingProps: any }) {
  const [pairingList, setpairingList] = useState<any>([]);
  const noReviewState = useRecoilValue<NoReviewTypes[]>(noReview);
  const [randomNum, setRandomNum] = useState(0);
  const [collisions, setCollisions] = useState<boolean>(false);
  const [date, setDate] = useState<any>('');
  const initialDate = props?.pairingProps?.createdAt;

  // 페어링 리스트 초기화
  useEffect(() => {
    if (props.pairingProps !== undefined) {
      setpairingList(props?.pairingProps);
    }
  }, [props]);

  // 글이 없는 경우
  useEffect(() => {
    let randomTmp: number = Math.floor(Math.random() * 3);
    setRandomNum(randomTmp);
  }, []);

  useEffect(() => {
    if (initialDate !== undefined) {
      let tmpDate = TimeHandler(initialDate);
      setDate(tmpDate);
    }
  }, [initialDate]);

  // 더보기 길이 계산
  useEffect(() => {
    if (props?.pairingProps !== undefined) {
      let parent = document.getElementById('pairingParents');
      let imageChild = document.getElementById(
        `pairingImage${props?.pairingProps.pairingId}`
      );
      let deschild = document.getElementById(
        `pairingDescribe${props?.pairingProps.pairingId}`
      );

      if (parent !== null && deschild !== null && imageChild !== null) {
        let parentHeight = parent.offsetHeight;
        let tagChildHeight = imageChild.clientHeight;
        let deschildHeight = deschild.clientHeight;
        if (parentHeight <= tagChildHeight + deschildHeight)
          setCollisions(true);
      }
    }
  }, [collisions, props]);

  return (
    <>
      {pairingList?.length === 0 ? (
        <div className="noneContent">
          <Image
            className="m-auto pb-3 opacity-50"
            src="/images/logo.png"
            alt="logo"
            width={40}
            height={40}
          />
          등록된 페어링이 없습니다.
        </div>
      ) : (
        <Swiper
          className="w-full h-fit"
          slidesPerView={2.2}
          spaceBetween={10}
          modules={[Pagination]}
        >
          {pairingList?.map((el: any) => (
            <SwiperSlide key={el?.pairingId}>
              <div className="w-full ml-2 mb-2 rounded-lg bg-white text-y-black drop-shadow-lg text-[8px] border">
                {/* 페어링,닉네임 */}
                <div className="flex justify-between py-1 px-2">
                  <span className="flex justify-center items-center px-2 py-[2px] rounded-md bg-y-gold text-white">
                    {CategoryMatcherToKor(el?.category)}
                  </span>
                  <span className="flex justify-end items-center w-2/5">
                    <span className="w-[70%] text-end truncate pr-[2px]">
                      {el?.nickname}
                    </span>

                    <BiUser className="bg-y-brown text-white rounded-full w-4 h-4" />
                  </span>
                </div>
                {/* 사진,설명 */}
                <Link href={`/pairing/${el?.pairingId}`}>
                  <div
                    className={`p-2 h-28 w-full border-y-2 border-gray-200 leading-5 relative ${
                      collisions ? 'overflow-hidden' : ''
                    }`}
                    id="pairingParents"
                  >
                    {el?.thumbnail === '' || el?.thumbnail === null ? (
                      <div id={`pairingImage${el?.pairingId}`}></div>
                    ) : (
                      <div className="h-[77px] w-auto overflow-hidden ">
                        <Image
                          src={el?.thumbnail}
                          alt="img"
                          width={100}
                          height={100}
                          className="m-auto h-full w-auto select-none"
                          id={`pairingImage${el?.pairingId}`}
                          priority
                        />
                      </div>
                    )}
                    {el?.content === undefined ? (
                      <div
                        className="text-y-gray"
                        id={`pairingDescribe${el?.pairingId}`}
                      >
                        {noReviewState[randomNum]?.contents}
                      </div>
                    ) : collisions ? (
                      <>
                        <div id={`pairingDescribe${el?.pairingId}`}>
                          {el?.content}
                        </div>
                        <div className="absolute -bottom-[0.5px] right-1 px-1 bg-white">
                          ...<span className="text-y-gold">더보기</span>
                        </div>
                      </>
                    ) : (
                      <div id={`pairingDescribe${el?.pairingId}`}>
                        {el?.content}
                      </div>
                    )}
                  </div>
                </Link>
                {/* 날짜,코멘트수,엄지수 */}
                <div className="p-2 flex justify-between items-center text-[8px]">
                  <div className="text-y-gray">{date}</div>
                  <div className="flex">
                    <span className="flex justify-center">
                      <FaRegCommentDots className="mr-[2px] mt-[1px] w-3 h-3" />
                      {el?.commentCount}
                    </span>

                    <PairingThumbs
                      isUserLikes={el?.isUserLikes}
                      likeCount={el?.likeCount}
                      pairingId={el?.pairingId}
                    />
                  </div>
                </div>
              </div>
            </SwiperSlide>
          ))}
        </Swiper>
      )}
    </>
  );
}
