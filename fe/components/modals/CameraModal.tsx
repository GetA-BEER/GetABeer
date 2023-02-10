import { useState } from 'react';
import { FiCamera } from 'react-icons/fi';
import { BsImages } from 'react-icons/bs';
import { AiFillCamera, AiOutlineClose } from 'react-icons/ai';

export default function CameraModal() {
  const [showModal, setShowModal] = useState(false);
  const onCameraClick = () => {
    setShowModal(true);
    console.log('보여야 하는데', showModal);
  };

  return (
    <>
      <div onClick={() => onCameraClick()} className="hover:text-y-brown">
        <FiCamera className="m-auto text-3xl py-[1px]" />
        <div className="text-[8px]">이미지 검색</div>
      </div>
      {showModal ? (
        <div className="fixed grid grid-cols-3 gap-2 bottom-16 left-1/2 -ml-24 text-y-brown">
          <div
            onClick={() => setShowModal(false)}
            className="bg-white p-1 rounded-full w-14 h-14 border-2 border-y-brown flex justify-center items-center"
          >
            <AiOutlineClose className="w-5 h-5" />
          </div>
          {/* <form> */}
          <div className="bg-white p-1 rounded-full w-14 h-14 border-2 border-y-brown -mt-8">
            <label htmlFor="file">
              <BsImages className="w-5 h-5 block m-auto mt-[6px] -mb-1" />
              <span className="text-[8px] w-fit m-auto">갤러리</span>
            </label>
            <input type="file" id="file" accept="image/*" className="hidden" />
          </div>

          <div className="bg-white p-1 rounded-full w-14 h-14 border-2 border-y-brown">
            <label htmlFor="camera">
              <AiFillCamera className="w-6 h-6 block m-auto mt-1 -mb-1" />
              <span className="text-[8px] w-fit m-auto">촬영</span>
            </label>
            <input
              type="file"
              id="camera"
              accept="image/*"
              capture="environment"
              className="hidden"
            />
          </div>
          {/* </form> */}
        </div>
      ) : (
        <></>
      )}{' '}
    </>
  );
}
{
  /* <button
className="inset-0 fixed cursor-default"
onClick={() => setShowModal(false)}
></button> */
}
