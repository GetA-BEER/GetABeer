import PageContainer from '@/components/PageContainer';
import { useState, useEffect } from 'react';
import BackBtn from '@/components/button/BackPageBtn';
import axios from '@/pages/api/axios';
import Link from 'next/link';
import Image from 'next/image';
import SpeechBalloon from '@/components/SpeechBalloon';
import { RatingComment, PairingComment } from '@/components/SpeechBalloon';

export default function MyComment() {
  const [userNickname, setUserNickname] = useState('');
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
    axios.delete(`/api/pairings/comments/${pairingCommentId}`).then((res) => {
      if (pairingCommentList !== null) {
        const filtered = pairingCommentList.filter((el) => {
          return el.pairingCommentId !== pairingCommentId;
        });
        setPairingCommentList(filtered);
      }
    });
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
    axios.get(`/api/mypage/comment/rating`).then((res) => {
      setRatingCommentList(res.data.data);
    });
    axios.get(`/api/mypage/comment/pairing`).then((res) => {
      setPairingCommentList(res.data.data);
    });
  }, []);

  return (
    <PageContainer>
      <main className="px-2">
        <BackBtn />
        <div className="flex justify-center my-4">
          <h1 className="text-xl lg:text-2xl font-bold">
            {userNickname}님의 댓글
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
      {ratingCommentList === null ? (
        <div className="flex flex-col justify-center items-center rounded-lg bg-y-lightGray py-5 m-2">
          <Image
            className="m-auto pb-3 opacity-50"
            src="/images/logo.png"
            alt="logo"
            width={40}
            height={40}
          />
          <span>등록된 댓글이 없습니다</span>
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
      {pairingCommentList === null ? (
        <div className="flex flex-col justify-center items-center rounded-lg bg-y-lightGray py-5 m-2">
          <Image
            className="m-auto pb-3 opacity-50"
            src="/images/logo.png"
            alt="logo"
            width={40}
            height={40}
          />
          <span>등록된 댓글이 없습니다</span>
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
