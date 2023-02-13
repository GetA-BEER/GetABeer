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
import { TagMatcherToEng, TagMatcherToKor } from '@/utils/TagMatcher';
import axios from 'axios';

export default function EditRatingPage() {
  const router = useRouter();
  const ratingId = router.query.ratingid;
  const [star, setStar] = useState(0);
  const [content, setContent] = useState('');
  const [color, setColor] = useState('');
  const [flavor, setFlavor] = useState('');
  const [taste, setTaste] = useState('');
  const [carbonation, setCarbonation] = useState('');

  useEffect(() => {
    if (ratingId !== undefined) {
      axios.get(`/api/ratings/${ratingId}`).then((res) => {
        setStar(res.data.star);
        setContent(res.data.content);
        setColor(res.data.ratingTag[0]);
        setFlavor(res.data.ratingTag[2]);
        setTaste(res.data.ratingTag[1]);
        setCarbonation(res.data.ratingTag[3]);
      });
    }
  }, [ratingId]);

  const ratingChanged = (newRating: number) => {
    setStar(newRating);
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
    axios.patch(`/api/ratings/${ratingId}`, reqBody).then((res) => {
      router.replace(`/rating/${ratingId}`);
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
          <ColorTag setSelected={setColor} checked={TagMatcherToKor(color)} />
          <SmellTag setSelected={setFlavor} checked={TagMatcherToKor(flavor)} />
          <TasteTag setSelected={setTaste} checked={TagMatcherToKor(taste)} />
          <CarbonatinTag
            setSelected={setCarbonation}
            checked={TagMatcherToKor(carbonation)}
          />
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
            <SubmitBtn onClick={handleSubmit}>수정하기</SubmitBtn>
          </div>
        </div>
      </div>
    </div>
  );
}
