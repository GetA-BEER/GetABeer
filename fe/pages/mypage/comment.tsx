import PageContainer from '@/components/PageContainer';
import { useState, useEffect } from 'react';
import BackBtn from '@/components/button/BackPageBtn';
import axios from '@/pages/api/axios';
import Link from 'next/link';
import Image from 'next/image';
import SpeechBalloon from '@/components/SpeechBalloon';
import { RatingComment, PairingComment } from '@/components/SpeechBalloon';
import { userNickname } from '@/atoms/login';
import { useRecoilState } from 'recoil';

export default function MyComment() {
  const [userName] = useRecoilState(userNickname);
  const [ratingCommentList, setRatingCommentList] = useState<RatingComment[]>(
    []
  );
  const [pairingCommentList, setPairingCommentList] = useState<
    PairingComment[]
  >([]);

  const deleteRatingComment = (ratingCommentId: number) => {
    axios
      .delete(`/api/ratings/comments/${ratingCommentId}`)
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

  const deletePairingComment = (pairingCommentId: number) => {
    axios
      .delete(`/api/pairings/comments/${pairingCommentId}`)
      .then((res) => {
        if (pairingCommentList !== null) {
          const filtered = pairingCommentList.filter((el) => {
            return el.pairingCommentId !== pairingCommentId;
          });
          setPairingCommentList(filtered);
        }
      })
      .catch((err) => console.log(err));
  };

  const [curTab, setCurTab] = useState(0);
  const tabArr = [
    {
      name: '평가',
      content: (
        <MyRatingComment
          ratingCommentList={ratingCommentList}
          deleteRatingComment={deleteRatingComment}
        />
      ),
    },
    {
      name: '페어링',
      content: (
        <MyPairingComment
          pairingCommentList={pairingCommentList}
          deletePairingComment={deletePairingComment}
        />
      ),
    },
  ];

  useEffect(() => {
    axios
      .get(`/api/mypage/comment/rating`)
      .then((res) => {
        setRatingCommentList(res.data.data);
      })
      .catch((err) => console.log(err));
    axios
      .get(`/api/mypage/comment/pairing`)
      .then((res) => {
        setPairingCommentList(res.data.data);
      })
      .catch((err) => console.log(err));
  }, []);

  return (
    <PageContainer>
      <main className="px-2">
        <BackBtn />
        <div className="flex justify-center my-4">
          <h1 className="text-xl lg:text-2xl font-bold">
            <span className="text-y-brown">{userName}님</span>의 댓글
          </h1>
        </div>
        <ul className="flex justify-around mb-4">
          {tabArr.map((el, idx) => {
            return (
              <li
                key={idx}
                className={
                  curTab === idx
                    ? 'text-y-brown border-b-2 border-y-brown px-2'
                    : 'text-y-gray px-2'
                }
                onClick={() => setCurTab(idx)}
              >
                {el.name}
              </li>
            );
          })}
        </ul>
        {tabArr[curTab].content}
      </main>
    </PageContainer>
  );
}

export const MyRatingComment = ({
  ratingCommentList,
  deleteRatingComment,
}: {
  ratingCommentList: RatingComment[];
  deleteRatingComment: Function;
}) => {
  return (
    <div>
      {ratingCommentList.length === 0 ? (
        <div className="noneContent py-8">
          <Image
            className="m-auto pb-3 opacity-50"
            src="/images/logo.png"
            alt="logo"
            width={40}
            height={40}
          />
          등록된 댓글이 없습니다.
        </div>
      ) : (
        ratingCommentList.map((el) => {
          return (
            <Link key={el.ratingCommentId} href={`/rating/${el.ratingId}`}>
              <SpeechBalloon
                props={el}
                isMine={true}
                deleteFunc={deleteRatingComment}
              />
            </Link>
          );
        })
      )}
    </div>
  );
};

export const MyPairingComment = ({
  pairingCommentList,
  deletePairingComment,
}: {
  pairingCommentList: PairingComment[];
  deletePairingComment: Function;
}) => {
  return (
    <div>
      {pairingCommentList.length === 0 ? (
        <div className="noneContent py-8">
          <Image
            className="m-auto pb-3 opacity-50"
            src="/images/logo.png"
            alt="logo"
            width={40}
            height={40}
          />
          등록된 댓글이 없습니다.
        </div>
      ) : (
        pairingCommentList.map((el) => {
          return (
            <Link key={el.pairingCommentId} href={`/pairing/${el.pairingId}`}>
              <SpeechBalloon
                props={el}
                isMine={true}
                deleteFunc={deletePairingComment}
              />
            </Link>
          );
        })
      )}
    </div>
  );
};
