import { HiShare } from 'react-icons/hi';
import {
  FacebookShareButton,
  FacebookIcon,
  TwitterShareButton,
  TwitterIcon,
  LineShareButton,
  LineIcon,
} from 'react-share';
import KakaoShareButton from './KakaoShareButton';
import { useState } from 'react';
export default function ShareBtn() {
  const currentUrl = 'http://localhost:3000';
  const [showModal, setShowModal] = useState(false);
  return (
    <>
      <button
        type="button"
        onClick={() => setShowModal(true)}
        className="hover:text-y-gold mr-1 absolute"
      >
        <span className="mr-1 ">
          <HiShare className="inline" /> 공유하기
        </span>
      </button>
      {showModal ? (
        <div className="fixed bg-white z-[9] left-1/2 lg:ml-24 ml-16 w-fit px-2 py-1 mt-1 rounded-lg shadow-lg flex items-center">
          <KakaoShareButton />
          <FacebookShareButton style={{ marginRight: '1px' }} url={currentUrl}>
            <FacebookIcon
              round={true}
              borderRadius={24}
              className="w-6 h-6"
            ></FacebookIcon>
          </FacebookShareButton>
          <TwitterShareButton style={{ marginRight: '1px' }} url={currentUrl}>
            <TwitterIcon
              className="w-6 h-6"
              round={true}
              borderRadius={24}
            ></TwitterIcon>
          </TwitterShareButton>
          <LineShareButton url={currentUrl}>
            <LineIcon
              className="w-6 h-6"
              round={true}
              borderRadius={24}
            ></LineIcon>
          </LineShareButton>
          <button
            className="inset-0 fixed cursor-default -z-10"
            onClick={() => setShowModal(false)}
          ></button>
        </div>
      ) : (
        <></>
      )}
    </>
  );
}
