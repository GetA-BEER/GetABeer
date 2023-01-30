import { AiOutlineTrophy } from 'react-icons/ai';
import { FiCamera, FiMapPin } from 'react-icons/fi';
import { HiMenu } from 'react-icons/hi';
import { BiUser } from 'react-icons/bi';
import { useState } from 'react';
export default function NavBar() {
  const [navState, setNavState] = useState(0);
  const onNavChange = (state: number): void => {
    setNavState(state);
  };

  return (
    <>
      <nav className="w-full fixed bottom-0 z-20 border-t border-gray-200">
        <div className="max-w-4xl grid grid-cols-5 px-5 bg-white text-center py-1">
          <div
            onClick={() => onNavChange(1)}
            className={`${navState === 1 ? 'text-y-brown' : 'text-y-black'}`}
          >
            <AiOutlineTrophy className="m-auto text-3xl py-[1px]" />
            <div className="text-[8px]">이달의 맥주</div>
          </div>
          <div
            onClick={() => onNavChange(2)}
            className={`${navState === 2 ? 'text-y-brown' : 'text-y-black'}`}
          >
            <FiMapPin className="m-auto text-3xl py-[1px]" />
            <div className="text-[8px]">지도</div>
          </div>
          <div
            onClick={() => onNavChange(3)}
            className={`${navState === 3 ? 'text-y-brown' : 'text-y-black'}`}
          >
            <FiCamera className="m-auto text-3xl py-[1px]" />
            <div className="text-[8px]">이미지 검색</div>
          </div>
          <div
            onClick={() => onNavChange(4)}
            className={`${navState === 4 ? 'text-y-brown' : 'text-y-black'}`}
          >
            <BiUser className="m-auto text-3xl py-[1px]" />
            <div className="text-[8px]">마이페이지</div>
          </div>
          <div
            onClick={() => onNavChange(5)}
            className={`${navState === 5 ? 'text-y-brown' : 'text-y-black'}`}
          >
            <HiMenu className="m-auto text-3xl py-[1px]" />
            <div className="text-[8px]">메뉴</div>
          </div>
        </div>
      </nav>
    </>
  );
}
