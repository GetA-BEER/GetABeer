import PageContainer from '@/components/PageContainer';
import { FaArrowLeft } from 'react-icons/fa';
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
    // axios.get('/mypage/ratings').then((res) => {
    //   setRatingList(res.data.data);
    //   setTotalPages(res.data.pageInfo.totalPages);
    // });
    setRatingList([
      {
        beerId: 1,
        ratingId: 1,
        userId: 1,
        nickname: '최대열글자까지됩니다',
        //여기 유저 이미지 url도 들어와야함!
        star: 4.5,
        ratingTag: ['GOLD', 'SWEET', 'FLOWER', 'MIDDLE'],
        content: '맥주 향이 좋습니다.',
        likeCount: 2,
        commentCount: 0,
        createdAt: '2023-02-13T14:17:15.091737',
        modifiedAt: '2023-02-13T14:17:15.091737',
        isUserLikes: true,
      },
      {
        beerId: 2,
        ratingId: 2,
        userId: 1,
        nickname: '최대열글자까지됩니다',
        //여기 유저 이미지 url도 들어와야함!
        star: 3.5,
        ratingTag: ['GOLD', 'SWEET', 'FLOWER', 'MIDDLE'],
        content: '이것도 좋습니다!',
        likeCount: 0,
        commentCount: 0,
        createdAt: '2023-02-13T14:17:15.091737',
        modifiedAt: '2023-02-13T14:17:15.091737',
        isUserLikes: false,
      },
    ]);
  }, []);

  return (
    <PageContainer>
      <main className="px-2">
        <FaArrowLeft
          onClick={() => router.back()}
          className="text-xl text-y-gray mt-2"
        />
        <div className="flex justify-center my-4">
          <h1 className="text-2xl sm:text-3xl lg:text-4xl font-bold">
            나의 평가
          </h1>
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
