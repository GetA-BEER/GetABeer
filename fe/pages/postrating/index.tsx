import StarRatingComponent from 'react-rating-stars-component';
import React, { useState, useEffect } from 'react';
import { useRouter } from 'next/router';
import ColorTag from '@/components/tag/ColorTag';
import SmellTag from '@/components/tag/SmellTag';
import TasteTag from '@/components/tag/TasteTag';
import CarbonatinTag from '@/components/tag/CarbonationTag';
import BigInput from '@/components/inputs/BigInput';
import CloseBtn from '@/components/button/CloseBtn';
import SubmitBtn from '@/components/button/SubmitBtn';
import MiddleCard, { testBeer } from '@/components/middleCards/MiddleCard';
import { TagMatcherToEng } from '@/utils/TagMatcher';
import axios from 'axios';

export default function PostRatingPage() {
  const router = useRouter();

  const [star, setStar] = useState(0);
  const [content, setContent] = useState('');
  const [color, setColor] = useState('');
  const [flavor, setFlavor] = useState('');
  const [taste, setTaste] = useState('');
  const [carbonation, setCarbonation] = useState('');
  const [isValid, setIsValid] = useState(false);

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

  const ratingChanged = (newRating: number) => {
    setStar(newRating);
  };

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
      beerId: 1,
      userId: 1,
      star,
      content,
      color: TagMatcherToEng(color),
      flavor: TagMatcherToEng(flavor),
      taste: TagMatcherToEng(taste),
      carbonation: TagMatcherToEng(carbonation),
    };
    axios.post('/api/ratings', reqBody).then((res) => {
      console.log(res);
      reset();
    });
  };

  return (
    <div className="m-auto h-screen max-w-4xl px-6">
      <MiddleCard cardProps={testBeer} />
      <div>
        <div className="mt-5">
          <div>별점</div>
          <div className="flex justify-start items-center">
            <StarRatingComponent
              count={5}
              size={50}
              value={star}
              onChange={ratingChanged}
              isHalf={true}
              color="#DDDDDD"
              activeColor="#F1B31C"
            />
            <span className="text-2xl ml-6">{star}</span>
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
    </div>
  );
}
