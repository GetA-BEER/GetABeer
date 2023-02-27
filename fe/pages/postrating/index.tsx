import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import ColorTag from '@/components/tag/ColorTag';
import SmellTag from '@/components/tag/SmellTag';
import TasteTag from '@/components/tag/TasteTag';
import CarbonatinTag from '@/components/tag/CarbonationTag';
import BigInput from '@/components/inputs/BigInput';
import CloseBtn from '@/components/button/CloseBtn';
import SubmitBtn from '@/components/button/SubmitBtn';
import MiddleCard, {
  MiddleCardInfo,
} from '@/components/middleCards/MiddleCard';
import { TagMatcherToEng } from '@/utils/TagMatcher';
import axios from '@/pages/api/axios';
import StarRating from '@/components/inputs/StarRating';
import PageContainer from '@/components/PageContainer';
import { useRecoilState } from 'recoil';
import { currentBeer } from '@/atoms/currentBeer';

export default function PostRatingPage() {
  const router = useRouter();
  const [beerInfo] = useRecoilState(currentBeer);
  const [star, setStar] = useState(0);
  const [content, setContent] = useState('');
  const [color, setColor] = useState('');
  const [flavor, setFlavor] = useState('');
  const [taste, setTaste] = useState('');
  const [carbonation, setCarbonation] = useState('');
  const [isValid, setIsValid] = useState(false);

  const cardProps: MiddleCardInfo = {
    beerId: beerInfo?.beerId,
    thumbnail: beerInfo?.beerDetailsBasic?.thumbnail,
    korName: beerInfo?.beerDetailsBasic?.korName,
    category: beerInfo?.beerCategoryTypes,
    country: beerInfo?.beerDetailsBasic?.country,
    abv: beerInfo?.beerDetailsBasic?.abv,
    ibu: beerInfo?.beerDetailsBasic?.ibu,
    totalStarCount:
      beerInfo?.beerDetailsCounts?.totalStarCount ||
      beerInfo?.beerDetailsCounts?.femaleStarCount +
        beerInfo?.beerDetailsCounts?.maleStarCount,
    totalAverageStars: beerInfo?.beerDetailsStars?.totalAverageStars,
    beerTags: beerInfo?.beerDetailsTopTags || [],
  };

  useEffect(() => {
    if (
      star > 0 &&
      color !== '' &&
      flavor !== '' &&
      taste !== '' &&
      carbonation !== ''
    ) {
      setIsValid(true);
    } else {
      setIsValid(false);
    }
  }, [star, content, color, flavor, taste, carbonation]);

  const reset = () => {
    setStar(0);
    setContent('');
    setColor('');
    setFlavor('');
    setTaste('');
    setCarbonation('');
  };

  const handleSubmit = () => {
    const reqBody = {
      beerId: beerInfo.beerId,
      star,
      content,
      color: TagMatcherToEng(color),
      flavor: TagMatcherToEng(flavor),
      taste: TagMatcherToEng(taste),
      carbonation: TagMatcherToEng(carbonation),
    };
    axios
      .post('/api/ratings', reqBody)
      .then((res) => {
        router.back();
        reset();
      })
      .catch((err) => console.log(err));
  };

  return (
    <PageContainer>
      <main className="px-6">
        <MiddleCard cardProps={cardProps} />
        <div>
          <div className="mt-5">
            <div>별점</div>
            <div className="flex justify-start items-center">
              <div className="flex items-center h-10 mb-8 -mt-3">
                <StarRating star={star} setStar={setStar} />
              </div>
              <span className="text-2xl ml-[260px] mt-2">{star}</span>
            </div>
          </div>
          <div className="mt-3">
            <div>평가</div>
            <ColorTag setSelected={setColor} checked={undefined} />
            <SmellTag setSelected={setFlavor} checked={undefined} />
            <TasteTag setSelected={setTaste} checked={undefined} />
            <CarbonatinTag setSelected={setCarbonation} checked={undefined} />
          </div>
          <div className="mt-5">
            <div className="mb-3">리뷰</div>
            <BigInput
              placeholder="맥주에 대한 평가를 남겨주세요"
              inputState={content}
              setInputState={setContent}
            />
          </div>
          <div className="flex -ml-1">
            <div className="flex-1">
              <CloseBtn onClick={() => router.back()}>나가기</CloseBtn>
            </div>
            <div className="flex-1">
              {isValid ? (
                <SubmitBtn onClick={handleSubmit}>등록하기</SubmitBtn>
              ) : (
                <div className="flex justify-center items-center w-full h-11 rounded-xl m-2 bg-red-100 text-xs text-red-500 -ml-[1px]">
                  별점과 평가를 선택해주세요
                </div>
              )}
            </div>
          </div>
        </div>
      </main>
    </PageContainer>
  );
}
