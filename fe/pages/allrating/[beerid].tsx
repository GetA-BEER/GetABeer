import RatingCard, {
  RatingCardProps,
} from '@/components/middleCards/RatingCard';
import SortBox, { Sort } from '@/components/selectBox/SortBox';
import axios from 'axios';
import { useRouter } from 'next/router';
import { useState, useEffect } from 'react';
import Link from 'next/link';
import { FaArrowLeft } from 'react-icons/fa';

export default function AllRating() {
  const router = useRouter();
  const beerId = router.query.beerid;
  const [sort, setSort] = useState<Sort>('mostlikes');
  const [ratingList, setRatingList] = useState<RatingCardProps[]>([]);
  useEffect(() => {
    if (beerId !== undefined) {
      axios
        .get(`/api/ratings/${sort}?beerId=${beerId}&page=1&size=5`)
        .then((res) => {
          setRatingList(res.data.data);
        });
    }
  }, [beerId, sort]);

  return (
    <div className="m-auto h-screen max-w-4xl px-2">
      <FaArrowLeft
        onClick={() => router.back()}
        className="text-xl text-y-gray mt-2"
      />
      <div className="flex justify-center my-4">
        <h1 className="text-2xl sm:text-3xl lg:text-4xl font-bold">
          제주 펠롱 에일
        </h1>
      </div>
      <SortBox setSort={setSort} />
      <div className="mt-3">
        {ratingList.map((el: RatingCardProps) => {
          return (
            <Link key={el.ratingId} href={`/rating/${el.ratingId}`}>
              <div className="border border-y-lightGray rounded-lg px-3 py-4 m-2">
                <RatingCard cardProps={el} isMine={false} />
              </div>
            </Link>
          );
        })}
      </div>
    </div>
  );
}
