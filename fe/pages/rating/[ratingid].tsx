import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';
import axios from '@/pages/api/axios';
import RatingCard, {
  RatingCardProps,
} from '@/components/middleCards/RatingCard';
import CommentInput from '@/components/inputs/CommentInput';
import PageContainer from '@/components/PageContainer';
import SpeechBalloon from '@/components/SpeechBalloon';
import { RatingComment } from '@/components/SpeechBalloon';
import { useRecoilValue } from 'recoil';
import { accessToken, userId } from '@/atoms/login';
import Swal from 'sweetalert2';
import Link from 'next/link';
import BackBtn from '@/components/button/BackPageBtn';
import { useRecoilState } from 'recoil';

export default function Rating() {
  const router = useRouter();
  const ratingId = router.query.ratingid;
  const USERID = useRecoilValue(userId);
  const [isLogin, setIsLogin] = useState(false);
  const [isMine, setIsMine] = useState(false);
  const [TOKEN] = useRecoilState(accessToken);
  const config = {
    headers: { Authorization: TOKEN, 'Content-Type': 'application/json' },
    withCredentials: true,
  };

  useEffect(() => {
    if (TOKEN === '') {
    } else {
      setIsLogin(true);
    }
  }, [TOKEN]);
  const [cardProps, setCardProps] = useState<RatingCardProps>();
  const [inputState, setInputState] = useState<string>('');
  const [ratingCommentList, setRatingCommentList] = useState<
    RatingComment[] | null
  >(null);

  useEffect(() => {
    if (ratingId !== undefined) {
      axios
        .get(`/api/ratings/${ratingId}`)
        .then((res) => {
          setCardProps(res.data);
          setRatingCommentList(res.data.ratingCommentList);
          if (res.data.userId === USERID) {
            setIsMine(true);
          }
        })
        .catch((err) => console.log(err));
    }
  }, [ratingId, USERID]);

  const postRatingComment = () => {
    if (inputState !== '') {
      const reqBody = {
        ratingId: Number(ratingId),
        content: inputState,
      };
      axios
        .post('/api/ratings/comments', reqBody, config)
        .then((res) => {
          if (ratingCommentList === null) {
            setRatingCommentList([res.data]);
          } else {
            setRatingCommentList([res.data, ...ratingCommentList]);
          }
          setInputState('');
        })
        .catch((err) => console.log(err));
    }
  };

  const deleteRatingComment = (ratingCommentId: number) => {
    axios
      .delete(`/api/ratings/comments/${ratingCommentId}`, config)
      .then((res) => {
        if (ratingCommentList !== null) {
          const filtered = ratingCommentList.filter((el) => {
            return el.ratingCommentId !== ratingCommentId;
          });
          setRatingCommentList(filtered);
        }
      })
      .catch((err) => console.log(err));
  };

  return (
    <PageContainer>
      <div className="px-2">
        <BackBtn />
        <Link href={`/beer/${cardProps?.beerId}`}>
          <div className="flex justify-center mb-4 mt-8">
            <h1 className="text-xl lg:text-2xl font-semibold">
              {cardProps?.korName}
            </h1>
          </div>
        </Link>
        <div className="border border-y-lightGray rounded-lg px-2 py-4">
          {cardProps !== undefined ? (
            <RatingCard
              cardProps={cardProps}
              isMine={isMine}
              count={ratingCommentList ? ratingCommentList?.length : 0}
            />
          ) : null}
          <div>
            <div className="px-2 my-5">
              <CommentInput
                inputState={inputState}
                setInputState={setInputState}
                postFunc={
                  isLogin
                    ? postRatingComment
                    : () => {
                        Swal.fire({
                          text: '로그인이 필요한 서비스 입니다.',
                          showCancelButton: true,
                          confirmButtonColor: '#f1b31c',
                          cancelButtonColor: '#A7A7A7',
                          confirmButtonText: '로그인',
                          cancelButtonText: '취소',
                        }).then((result) => {
                          if (result.isConfirmed) {
                            router.push({
                              pathname: '/login',
                            });
                          }
                        });
                      }
                }
              />
            </div>
          </div>
          <div className="mr-1 -ml-1">
            {ratingCommentList === null
              ? null
              : ratingCommentList.map((el) => {
                  return (
                    <SpeechBalloon
                      key={el.ratingCommentId}
                      props={el}
                      isMine={USERID === el.userId}
                      deleteFunc={deleteRatingComment}
                    />
                  );
                })}
          </div>
        </div>
      </div>
    </PageContainer>
  );
}
