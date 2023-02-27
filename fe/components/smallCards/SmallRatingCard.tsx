import { BiUser } from 'react-icons/bi';
import { FaRegCommentDots } from 'react-icons/fa';
import { FiThumbsUp } from 'react-icons/fi';
import Image from 'next/image';
import { useRecoilValue } from 'recoil';
import { noReview, NoReviewTypes } from '@/atoms/noReview';
import SmallTag from '@/components/smallCards/SmallTag';
import { TimeHandler } from '@/utils/TimeHandler';
import React, { useEffect, useState } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Pagination } from 'swiper';
import 'swiper/css';
import 'swiper/css/pagination';
import Link from 'next/link';

export default function SmallRatingCard(props: { ratingProps: any }) {
  const [RatingList, setRatingPropsList] = useState<any>();
  const noReviewState = useRecoilValue<NoReviewTypes[]>(noReview);
  const [starScore, setStarScore] = useState<number>(props?.ratingProps?.star);
  const [collisions, setCollisions] = useState<boolean>(false);
  const [randomNum, setRandomNum] = useState(0);
  const [date, setDate] = useState<any>('');
  const initialDate = props?.ratingProps?.createdAt;

  // ratingPropsList 초기화
  useEffect(() => {
    if (props?.ratingProps !== undefined) {
      setRatingPropsList(props?.ratingProps);
    }
  }, [props.ratingProps]);

  // 글 작성 없는 경우
  useEffect(() => {
    let randomTmp: number = Math.floor(Math.random() * 3);
    setRandomNum(randomTmp);
    if (starScore) {
      const starNum = Number(starScore?.toFixed(2));
      setStarScore(starNum);
    } else {
      setStarScore(0);
    }
  }, [starScore]);

  // 날짜 초기화
  useEffect(() => {
    if (initialDate !== undefined) {
      let tmpDate = TimeHandler(initialDate);
      setDate(tmpDate);
    }
  }, [initialDate]);

  // 더보기 설정
  useEffect(() => {
    if (RatingList! == undefined) {
      let parent = document.getElementById('overParents');
      let tagChild = document.getElementById(`overTags${RatingList?.ratingId}`);
      let deschild = document.getElementById(
        `overDescribe${RatingList?.ratingId}`
      );
      if (parent !== null && deschild !== null && tagChild !== null) {
        let parentHeight = parent.offsetHeight;
        let tagChildHeight = tagChild.clientHeight;
        let deschildHeight = deschild.clientHeight;
        if (parentHeight <= tagChildHeight + deschildHeight)
          setCollisions(true);
      }
    }
  }, [collisions, RatingList]);

  return (
    <>
      {RatingList?.length === 0 ? (
        <div className="noneContent">
          <Image
            className="m-auto pb-3 opacity-50"
            src="/images/logo.png"
            alt="logo"
            width={40}
            height={40}
          />
          등록된 평가가 없습니다.
        </div>
      ) : (
        <Swiper
          className="w-full h-fit"
          slidesPerView={2.2}
          spaceBetween={10}
          modules={[Pagination]}
        >
          {RatingList?.map((el: any) => (
            <SwiperSlide key={el?.ratingId}>
              <Link href={`/rating/${el?.ratingId}`}>
                <div className="w-full rounded-lg ml-2 mb-2 bg-white text-y-black drop-shadow-lg text-xs border">
                  {/* 별점,닉네임 */}
                  <div className="flex justify-between py-1 px-2">
                    <span className="flex justify-center items-center">
                      <Image
                        src="/images/star.png"
                        alt="star"
                        width={20}
                        height={20}
                        className="mr-1 mb-[3px] text-y-gold drop-shadow-md  select-none"
                        priority
                      />
                      {starScore}
                    </span>
                    <span className="flex justify-center items-center">
                      {el?.nickname}
                      <BiUser className="ml-1 bg-y-brown text-white rounded-full w-4 h-4" />
                    </span>
                  </div>
                  {/* 태그, 설명 */}
                  <div
                    className={`py-2 px-1 h-28 w-full border-y-2 text-xs relative ${
                      collisions ? 'overflow-hidden' : ''
                    }`}
                    id="overParents"
                  >
                    <div id={`overTags${el?.ratingId}`}>
                      <SmallTag tags={el?.ratingTag} />
                    </div>
                    {el?.content === undefined ? (
                      <div className="text-y-gray ">
                        {noReviewState[randomNum]?.contents}
                      </div>
                    ) : collisions ? (
                      <>
                        <div
                          className="text-xs leading-5 h-fit relative"
                          id={`overDescribe${el?.ratingId}`}
                        >
                          {el?.content}
                        </div>
                        <div className="absolute -bottom-[0.5px] right-1 px-1 bg-white">
                          ...<span className="text-y-gold">더보기</span>
                        </div>
                      </>
                    ) : (
                      <div
                        className="text-xs leading-5 h-fit"
                        id={`overDescribe${el?.ratingId}`}
                      >
                        {el?.content}
                      </div>
                    )}
                  </div>
                  {/* 날짜,코멘트수,엄지수 */}
                  <div className="p-2 flex justify-between items-center text-[8px]">
                    <div className="text-y-gray">{date}</div>
                    <div className="flex">
                      <span className="flex justify-center">
                        <FaRegCommentDots className="mr-[2px] mt-[1px] w-3 h-3" />
                        {el?.commentCount}
                      </span>
                      <span className="ml-1 flex justify-center">
                        <FiThumbsUp className="w-3 h-3" />
                        {el?.likeCount}
                      </span>
                    </div>
                  </div>
                </div>
              </Link>
            </SwiperSlide>
          ))}
        </Swiper>
      )}
    </>
  );
}
