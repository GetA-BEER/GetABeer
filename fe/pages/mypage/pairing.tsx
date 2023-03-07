import PageContainer from '@/components/PageContainer';
import Image from 'next/image';
import PairingCardController from '@/components/pairing/PairingCardController';
import axios from '@/pages/api/axios';
import { useEffect, useState } from 'react';
import { IoChevronBack } from 'react-icons/io5';
import Pagenation from '@/components/Pagenation';
import { useRouter } from 'next/router';
import { accessToken, userNickname } from '@/atoms/login';
import { useRecoilState } from 'recoil';

export default function Pairing() {
  const [pariginCardPops, setPairingCardProps] = useState<any>();
  const [page, setPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);
  const [TOKEN] = useRecoilState(accessToken);
  const [username] = useRecoilState(userNickname);
  const router = useRouter();

  useEffect(() => {
    if (TOKEN === '') {
      router.push('/');
    }
  }, [TOKEN, router]);

  useEffect(() => {
    axios
      .get(`/api/mypage/pairing`)
      .then((response) => {
        setPairingCardProps(response.data.data);
        setTotalPages(response.data.pageInfo.totalPages);
      })
      .catch((error) => console.log(error));
  }, []);

  return (
    <PageContainer>
      <main className="m-auto h-screen max-w-4xl relative">
        <button
          type="button"
          onClick={() => {
            router.back();
          }}
          className="ml-4 absolute"
        >
          <IoChevronBack className="w-6 h-6" />
        </button>
        <div className="mb-4 text-center text-xl bg-white rounded-lg max-w-4xl font-semibold">
          <span className="text-y-brown">{username}님</span>의 페어링
        </div>
        <PairingCardController pairingCardProps={pariginCardPops} />
        {pariginCardPops?.length ? (
          <Pagenation page={page} setPage={setPage} totalPages={totalPages} />
        ) : (
          <div className="noneContent py-8">
            <Image
              className="m-auto pb-3 opacity-50"
              src="/images/logo.png"
              alt="logo"
              width={40}
              height={40}
            />
            등록된 페어링이 없습니다.
          </div>
        )}
        <div className="pb-24"></div>
      </main>
    </PageContainer>
  );
}
