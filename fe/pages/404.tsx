import PageContainer from '@/components/PageContainer';
import Image from 'next/image';
import { useRouter } from 'next/router';
import { IoChevronBack, IoChevronForward } from 'react-icons/io5';

export default function NotFoundPage() {
  const router = useRouter();
  return (
    <PageContainer>
      <div className="flex flex-col justify-center items-center mt-24">
        <Image
          alt="not found image"
          src={'/images/NotFound.png'}
          priority
          width={260}
          height={260}
        />
        <h2 className="text-lg lg:text-2xl mb-2 font-bold">
          페이지를 찾을 수 없습니다
        </h2>
        <h3 className="text-y-gray text-xs lg:text-sm my-0.5">
          존재하지 않은 주소를 입력하셨거나
        </h3>
        <h3 className="text-y-gray text-xs lg:text-sm my-0.5">
          요청하신 페이지의 주소가 변경, 삭제되어 찾을 수 없습니다
        </h3>
        <div className="flex mt-6">
          <button
            className="flex justify-center items-center px-5 text-y-gold hover:text-y-brown"
            onClick={() => {
              router.back();
            }}
          >
            <IoChevronBack />
            <span className="text-sm">이전 페이지로</span>
          </button>
          <button
            className="flex justify-center items-center px-5 text-y-gold hover:text-y-brown"
            onClick={() => {
              router.push('/');
            }}
          >
            <span className="text-sm">홈 페이지로</span>
            <IoChevronForward />
          </button>
        </div>
      </div>
    </PageContainer>
  );
}
