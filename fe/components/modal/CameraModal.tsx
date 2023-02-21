import { useState, useEffect } from 'react';
import { FiCamera } from 'react-icons/fi';
import { BsImages } from 'react-icons/bs';
import { AiFillCamera } from 'react-icons/ai';
import imageCompression from 'browser-image-compression';
import Image from 'next/image';

export default function CameraModal() {
  interface FileData {
    lastModified: number;
    name: string;
    size: number;
    type: string;
  }

  const [showModal, setShowModal] = useState(false);
  const [uploadImg, setUploadImg] = useState<FileData | null>(null);
  const [style, setStyle] = useState('pc');

  async function handleImageUpload(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.target.files !== null) {
      const imageFile = e.target.files[0];
      console.log(imageFile);
      const options = {
        //옵션 설정 필요
        maxSizeMB: 4,
        maxWidthOrHeight: 1920,
        useWebWorker: true,
      };
      try {
        const compressedFile = await imageCompression(imageFile, options);
        setUploadImg(compressedFile);
        console.log(
          `compressedFile size ${compressedFile.size / 1024 / 1024} MB`
        );
        // await uploadToServer(compressedFile);
        await console.log(uploadImg);
      } catch (error) {
        console.log(error);
      }
    }
  }

  useEffect(() => {
    if (window.innerWidth >= 1024) {
      setStyle('pc');
    } else {
      setStyle('mobile');
    }
    window.addEventListener('resize', windowResize);
    return () => {
      window.removeEventListener('resize', windowResize);
    };
  }, []);
  const onCameraClick = () => {
    setShowModal(true);
  };

  const windowResize = () => {
    if (window.innerWidth >= 1024) {
      setStyle('pc');
    } else {
      setStyle('mobile');
    }
  };

  return (
    <>
      {style === 'pc' ? (
        <form className="hover:text-y-brown">
          <label htmlFor="camera">
            <FiCamera className="m-auto text-3xl py-[1px]" />
            <div className="text-[8px]">이미지 검색</div>
          </label>
          <input
            type="file"
            id="camera"
            accept="image/*"
            capture="environment"
            className="hidden"
            onChange={handleImageUpload}
          />
        </form>
      ) : (
        <div onClick={() => onCameraClick()} className="hover:text-y-brown">
          <FiCamera className="m-auto text-3xl py-[1px]" />
          <div className="text-[8px]">이미지 검색</div>
        </div>
      )}
      {showModal ? (
        <>
          <button
            type="button"
            onClick={() => setShowModal(false)}
            className="fixed p-5 w-full bottom-40 left-0 z-[1]"
          >
            <div className="w-fit m-auto border-dashed border-2 border-yellow-200 p-2">
              <Image
                className="w-3/5 m-auto"
                src="/images/example.png"
                alt="adv1"
                width={80}
                height={80}
              />
              <div className="text-yellow-200 mt-1 text-sm font-light">
                위와 같이 <br />잘 나온 사진으로 검색하세요
              </div>
            </div>
          </button>
          <form className="fixed bottom-16 left-1/2 -ml-16 animate-upper">
            <div className="grid grid-cols-2 gap-2 text-y-brown ">
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
                  onChange={handleImageUpload}
                />
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
                  onChange={handleImageUpload}
                />
              </div>

              <button
                type="button"
                className="inset-0 fixed cursor-default -z-10 bg-[rgba(0,0,0,0.6)]"
                onClick={() => setShowModal(false)}
              ></button>
            </div>
          </form>
        </>
      ) : (
        <></>
      )}{' '}
    </>
  );
}
