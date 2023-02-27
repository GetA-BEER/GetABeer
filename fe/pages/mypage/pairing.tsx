import PageContainer from '@/components/PageContainer';
import Image from 'next/image';
import PairingCardController from '@/components/pairing/PairingCardController';
import axios from '@/pages/api/axios';
import { useEffect, useState } from 'react';
import { IoChevronBack } from 'react-icons/io5';
import Link from 'next/link';
import Pagenation from '@/components/Pagenation';

export default function Pairing() {
  const [userNickname, setUserNickname] = useState<string>('');
  const [pariginCardPops, setPairingCardProps] = useState<any>();
  const [page, setPage] = useState<number>(1);
  const [totalPages, setTotalPages] = useState<number>(1);
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
      <main className="m-auto h-screen max-w-4xl">
        <Link href={'/mypage'}>
          <button className="ml-4">
            <IoChevronBack className="w-6 h-6" />
          </button>
        </Link>
        <div className="mb-4 text-center text-xl bg-white rounded-lg max-w-4xl font-semibold">
          {userNickname}나의 페어링
        </div>
        <PairingCardController pairingCardProps={pariginCardPops} />
        {pariginCardPops?.length ? (
          <Pagenation page={page} setPage={setPage} totalPages={totalPages} />
        ) : (
          <div className="flex flex-col justify-center items-center rounded-lg bg-y-lightGray py-5 m-2">
            <Image
              className="m-auto pb-3 opacity-50"
              src="/images/logo.png"
              alt="logo"
              width={40}
              height={40}
            />
            <span>등록된 페어링이 없습니다</span>
          </div>
        )}
        <div className="pb-24"></div>
      </main>
    </PageContainer>
  );
}
