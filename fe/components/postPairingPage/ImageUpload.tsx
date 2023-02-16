import Image from 'next/image';
import { AiOutlinePlus, AiOutlineCloseCircle } from 'react-icons/ai';
import imageCompression from 'browser-image-compression';
import { useState } from 'react';

export default function ImageUpload() {
  interface FileData {
    lastModified: number;
    name: string;
    size: number;
    type: string;
  }

  const [uploadImg, setUploadImg] = useState<FileData | null>(null);
  const [preImg, setPreImg] = useState<string[]>([]);
  async function handleImageUpload(e: React.ChangeEvent<HTMLInputElement>) {
    if (e.target.files !== null) {
      const imageFile = e.target.files[0];
      console.log('imageFile', imageFile);
      const options = {
        //옵션 설정 필요
        maxSizeMB: 1,
        maxWidthOrHeight: 1920,
        useWebWorker: true,
      };
      console.log('1번위치');
      try {
        const compressedFile = await imageCompression(imageFile, options);
        setUploadImg(compressedFile);
        let tmpUrl = URL.createObjectURL(compressedFile);
        setPreImg([...preImg, tmpUrl]);
        console.log('compressedFile', compressedFile);
        console.log('tmpUrl', tmpUrl);
        // console.log(
        //   `compressedFile size ${compressedFile.size / 1024 / 1024} MB`
        // );
        await console.log('compressedFile', uploadImg);
      } catch (error) {
        console.log(error);
      }
    }
  }

  // 파일 삭제
  const handleDelete = (e: React.MouseEvent<HTMLElement>, idx: number) => {
    e.preventDefault();
    // const copyImg = uploadImg.slice();
    const copyUrl = preImg.slice();
    // copyImg.splice(idx, 1);
    copyUrl.splice(idx, 1);
    // setUploadImg(copyImg);
    setPreImg(copyUrl);
  };

  // 썸네일 사진 선택
  const selectThumnail = (idx: number) => {
    // let copyImg = image.slice();
    let copyUrl = preImg.slice();
    // let selectImg = copyImg.splice(idx, 1);
    let selectUrl = copyUrl.splice(idx, 1);
    // setUploadImg([...selectImg, ...copyImg]);
    setPreImg([...selectUrl, ...copyUrl]);
  };

  return (
    <div className="m-2">
      <div className="mt-4 mb-2 text-base font-semibold">사진</div>

      <form className="grid grid-cols-3 gap-2 h-[105px] md:h-64 sm:h-48">
        {/* first Image */}
        <div className="bg-y-cream flex justify-center items-center rounded-xl overflow-hidden relative">
          {preImg[0] === undefined ? (
            <>
              <label htmlFor="file">
                <AiOutlinePlus className="w-10 h-10" />
              </label>
              <input
                type="file"
                id="file"
                accept="image/*;camera"
                className="hidden"
                onChange={handleImageUpload}
              />
            </>
          ) : (
            <>
              <Image src={preImg[0]} alt="bg" width={500} height={500} />
              <span className="absolute bg-y-gold py-1 px-2 rounded-md text-xs left-1 top-1 text-white">
                대표
              </span>
              <span
                onClick={(e) => {
                  handleDelete(e, 0);
                }}
                className="absolute right-1 top-1 text-y-gold bg-y-cream rounded-full"
              >
                <AiOutlineCloseCircle className="w-6 h-6" />
              </span>
            </>
          )}
        </div>
        {/* second Image */}
        {preImg[0] === undefined ? (
          <div className="border-2 border-y-lightGray border-dashed flex justify-center items-center rounded-xl"></div>
        ) : (
          <div className="bg-y-cream flex justify-center items-center rounded-xl overflow-hidden relative">
            {preImg[1] !== undefined ? (
              <>
                <Image
                  src={preImg[1]}
                  alt="bg"
                  width={500}
                  height={500}
                  onClick={() => selectThumnail(1)}
                />
                <span
                  onClick={(e) => {
                    handleDelete(e, 1);
                  }}
                  className="absolute right-1 top-1 text-y-gold bg-y-cream rounded-full"
                >
                  <AiOutlineCloseCircle className="w-6 h-6" />
                </span>
              </>
            ) : (
              <>
                <label htmlFor="file">
                  <AiOutlinePlus className="w-10 h-10" />
                </label>
                <input
                  type="file"
                  id="file"
                  accept="image/*;camera"
                  className="hidden"
                  onChange={handleImageUpload}
                />
              </>
            )}
          </div>
        )}

        {/* third Image */}
        {preImg[1] === undefined ? (
          <div className="border-2 border-y-lightGray border-dashed flex justify-center items-center rounded-xl"></div>
        ) : (
          <div className="bg-y-cream flex justify-center items-center rounded-xl overflow-hidden relative">
            {preImg[2] !== undefined ? (
              <>
                <Image
                  src={preImg[2]}
                  alt="bg"
                  width={500}
                  height={500}
                  onClick={() => selectThumnail(2)}
                />
                <span
                  onClick={(e) => {
                    handleDelete(e, 2);
                  }}
                  className="absolute right-1 top-1 text-y-gold bg-y-cream rounded-full"
                >
                  <AiOutlineCloseCircle className="w-6 h-6" />
                </span>
              </>
            ) : (
              <>
                <label htmlFor="file">
                  <AiOutlinePlus className="w-10 h-10" />
                </label>
                <input
                  type="file"
                  id="file"
                  accept="image/*;camera"
                  className="hidden"
                  onChange={handleImageUpload}
                />
              </>
            )}
          </div>
        )}
      </form>
    </div>
  );
}