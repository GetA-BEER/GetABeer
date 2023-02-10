import { useState } from 'react';
import { FiCamera } from 'react-icons/fi';
import { BsImages } from 'react-icons/bs';
import { AiFillCamera, AiOutlineClose } from 'react-icons/ai';

export default function CameraModal() {
  interface FileData {
    lastModified: number;
    name: string;
    size: number;
    type: string;
  }

  const [showModal, setShowModal] = useState(false);
  const [uploadImg, setUploadImg] = useState<FileData | null>(null);

  const onCameraClick = () => {
    setShowModal(true);
  };

  const imageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files !== null) {
      const imageFile = e.target.files[0];
      setUploadImg(imageFile);
      console.log(imageFile);
    }
  };

  return (
    <>
      <div onClick={() => onCameraClick()} className="hover:text-y-brown">
        <FiCamera className="m-auto text-3xl py-[1px]" />
        <div className="text-[8px]">이미지 검색</div>
      </div>
      {showModal ? (
        <form className="fixed bottom-16 left-1/2 -ml-16 animate-upper">
          <div className="grid grid-cols-2 gap-2  text-y-brown ">
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
                onChange={imageUpload}
              />
            </div>

            <div className="bg-white p-1 rounded-full w-14 h-14 border-2 border-y-brown">
              <label htmlFor="file">
                <BsImages className="w-5 h-5 block m-auto mt-[6px] -mb-1" />
                <span className="text-[8px] w-fit m-auto">갤러리</span>
              </label>
              <input
                type="file"
                id="file"
                accept="image/*"
                className="hidden"
                onChange={imageUpload}
              />
            </div>
            <button
              type="button"
              className="inset-0 fixed cursor-default -z-10"
              onClick={() => setShowModal(false)}
            ></button>
          </div>
        </form>
      ) : (
        <></>
      )}{' '}
    </>
  );
}
