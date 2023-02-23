import PageContainer from '@/components/PageContainer';
import { IoChevronBack } from 'react-icons/io5';
import Link from 'next/link';
import { useRouter } from 'next/router';
import { useState, useEffect } from 'react';
import axios from '@/pages/api/axios';
import RatingCard, {
  RatingCardProps,
} from '@/components/middleCards/RatingCard';
import Pagenation from '@/components/Pagenation';

export default function MyRating() {
  const router = useRouter();
  const [ratingList, setRatingList] = useState<RatingCardProps[]>([]);
  const [page, setPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);

  useEffect(() => {
    axios.get('/api/mypage/ratings').then((res) => {
      setRatingList(res.data.data);
      setTotalPages(res.data.pageInfo.totalPages);
    });
  }, []);

  return (
    <PageContainer>
      <main className="px-2">
        <Link href={'/mypage'}>
          <button className="m-4">
            <IoChevronBack className="w-6 h-6" />
          </button>
        </Link>
        <div className="flex justify-center my-4">
          <h1 className="text-xl lg:text-2xl font-bold">나의 평가</h1>
        </div>
        <div className="mt-3">
          {ratingList.map((el: RatingCardProps) => {
            return (
              <Link key={el.ratingId} href={`/rating/${el.ratingId}`}>
                <div className="border border-y-lightGray rounded-lg px-3 py-4 m-2">
                  <RatingCard
                    cardProps={el}
                    isMine={false}
                    count={el.commentCount}
                  />
                </div>
              </Link>
            );
          })}
        </div>
        <Pagenation page={page} setPage={setPage} totalPages={totalPages} />
      </main>
    </PageContainer>
  );
}
