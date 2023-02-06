import StarRatingComponent from 'react-rating-stars-component';
import { useState } from 'react';
import ColorTag from '@/components/tag/ColorTag';
import SmellTag from '@/components/tag/SmellTag';
import SparkleTag from '@/components/tag/sparkleTag';
import TasteTag from '@/components/tag/TasteTag';
import BigInput from '@/components/inputs/BigInput';
import CloseBtn from '@/components/button/CloseBtn';
import SubmitBtn from '@/components/button/SubmitBtn';

export default function PostRatingPage() {
  const [star, setStar] = useState(0);
  const [tagList, setTagList] = useState([]);
  const ratingChanged = (newRating: number) => {
    setStar(newRating);
  };
  return (
    <div className="m-auto h-screen max-w-4xl px-6">
      <form>
        <div>별점</div>
        <div className="flex justify-start items-center">
          <StarRatingComponent
            count={5}
            size={40}
            value={star}
            onChange={ratingChanged}
            isHalf={true}
            color="#DDDDDD"
            activeColor="#F1B31C"
          />
          <span className="text-2xl ml-6">{star}</span>
        </div>
        <div>평가</div>
        <ColorTag />
        <SmellTag />
        <SparkleTag />
        <TasteTag />
        <div>리뷰</div>
        <BigInput placeholder="맥주에 대한 평가를 남겨주세요" />
        <div className="flex">
          <div className="flex-1">
            <CloseBtn
              onClick={() => {
                console.log('나가기');
              }}
            >
              나가기
            </CloseBtn>
          </div>
          <div className="flex-1">
            <SubmitBtn
              onClick={() => {
                console.log('등록하기');
              }}
            >
              등록하기
            </SubmitBtn>
          </div>
        </div>
      </form>
    </div>
  );
}
