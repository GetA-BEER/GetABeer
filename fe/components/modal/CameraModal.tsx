import { useState, useEffect } from 'react';
import { FiCamera } from 'react-icons/fi';
import { BsImages } from 'react-icons/bs';
import { AiFillCamera } from 'react-icons/ai';
import imageCompression from 'browser-image-compression';
import Image from 'next/image';
import { useRouter } from 'next/router';
import { useRecoilState } from 'recoil';
import { searchingImage } from '@/atoms/searchingImage';
import axios from '@/pages/api/axios';

export default function CameraModal() {
  interface FileData {
    lastModified: number;
    name: string;
    size: number;
    type: string;
  }

  const router = useRouter();
  const [showModal, setShowModal] = useState(false);
  const [style, setStyle] = useState('pc');
  const [, setSearchResultList] = useRecoilState(searchingImage);

  async function handleImageUpload(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.target.files !== null) {
      const imageFile = e.target.files[0];
      const options = {
        //옵션 설정 필요
        maxSizeMB: 4,
        maxWidthOrHeight: 1920,
        useWebWorker: true,
      };
      try {
        const compressedFile = await imageCompression(imageFile, options);
        const config = {
          headers: {
            'content-type': 'multipart/form-data',
          },
          withCredentials: true,
        };
        const formData = new FormData();
        formData.append('image', compressedFile);
        router.push('/search/image');
        setShowModal(false);
        axios
          .post(`/api/search/image`, formData, config)
          .then((res) => {
            setSearchResultList(res.data);
          })
          .catch((error) => console.log(error));
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
            className="fixed p-5 w-full bottom-44 left-0 z-[1]"
          >
            <div className="w-fit m-auto border-dashed border-2 border-yellow-200 p-2">
              <Image
                className="w-3/5 m-auto"
                src="/images/example.png"
                alt="notice"
                width={80}
                height={80}
              />
              <div className="text-yellow-200 mt-1 text-sm font-light">
                위와 같이 <br />
                정확한 사진으로 검색하세요
              </div>
            </div>
          </button>
          <form className="fixed bottom-16 left-1/2 -ml-16">
            <div className="grid grid-cols-2 gap-2 text-y-brown upper">
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
                className="inset-0 fixed cursor-default -z-10 bg-[rgba(0,0,0,0.6)] "
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
