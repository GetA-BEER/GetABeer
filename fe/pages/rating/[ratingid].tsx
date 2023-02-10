import { FaArrowLeft } from 'react-icons/fa';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import axios from 'axios';
import RatingCard, {
  RatingCardProps,
} from '@/components/middleCards/RatingCard';
import CommentInput from '@/components/inputs/CommentInput';

export default function Rating() {
  const router = useRouter();
  const ratingId = router.query.ratingid;
  const [cardProps, setCardProps] = useState<RatingCardProps>();
  useEffect(() => {
    if (ratingId !== undefined) {
      axios.get(`/api/ratings/${ratingId}`).then((res) => {
        setCardProps(res.data);
      });
    }
  }, [ratingId]);
  return (
    <div className="m-auto h-screen max-w-4xl px-2">
      <FaArrowLeft
        onClick={() => router.back()}
        className="text-xl text-y-gray my-2"
      />
      <div className="flex justify-center mb-4 mt-8">
        <h1 className="text-2xl sm:text-3xl lg:text-4xl font-bold">
          제주 펠롱 에일
        </h1>
      </div>
      <div className="border border-y-lightGray rounded-lg px-3 py-4 m-2">
        {cardProps !== undefined ? (
          <RatingCard cardProps={cardProps} isMine={true} />
        ) : null}
        <div className="my-5">
          <CommentInput />
        </div>
      </div>
    </div>
  );
}
