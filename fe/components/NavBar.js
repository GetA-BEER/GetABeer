import { AiOutlineTrophy } from 'react-icons/ai';
import { FiCamera, FiMapPin } from 'react-icons/fi';
import { HiMenu } from 'react-icons/hi';
import { BiUser } from 'react-icons/bi';

export default function NavBar() {
  return (
    <>
      <nav className="w-full absolute bottom-0 z-20 ">
        <div className="max-w-4xl grid grid-cols-5 px-5 bg-y-gray text-y-black text-center py-1">
          <div className="hover:text-y-brown">
            <AiOutlineTrophy className="m-auto text-3xl py-[1px]" />
            <div className="text-[5px]">이달의 맥주</div>
          </div>
          <div className="hover:text-y-brown">
            <FiMapPin className="m-auto text-3xl py-[1px]" />
            <div className="text-[5px]">지도</div>
          </div>
          <div className="hover:text-y-brown">
            <FiCamera className="m-auto text-3xl py-[1px]" />
            <div className="text-[5px]">이미지 검색</div>
          </div>
          <div className="hover:text-y-brown">
            <BiUser className="m-auto text-3xl py-[1px]" />
            <div className="text-[5px]">마이페이지</div>
          </div>
          <div className="hover:text-y-brown">
            <HiMenu className="m-auto text-3xl py-[1px]" />
            <div className="text-[5px]">메뉴</div>
          </div>
        </div>
      </nav>
    </>
  );
}
