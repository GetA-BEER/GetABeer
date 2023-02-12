import Image from 'next/image';
import { AiOutlinePlus } from 'react-icons/ai';
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
  return (
    <div className="m-5">
      <div className="mt-4 mb-2 text-base font-semibold">사진</div>

      <div className="grid grid-cols-3 gap-2 h-[105px] md:h-64 sm:h-48">
        {/* first Image */}
        <div className="bg-y-cream flex justify-center items-center rounded-xl overflow-hidden ">
          <form>
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
              <Image src={preImg[0]} alt="bg" width={500} height={500} />
            )}
          </form>
        </div>
        {/* second Image */}
        {preImg[0] === undefined ? (
          <div className="border-2 border-y-lightGray border-dashed flex justify-center items-center rounded-xl"></div>
        ) : (
          <div className="bg-y-cream flex justify-center items-center rounded-xl overflow-hidden ">
            <form>
              {preImg[1] !== undefined ? (
                <Image src={preImg[1]} alt="bg" width={500} height={500} />
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
            </form>
          </div>
        )}

        {/* third Image */}
        {preImg[1] === undefined ? (
          <div className="border-2 border-y-lightGray border-dashed flex justify-center items-center rounded-xl"></div>
        ) : (
          <div className="bg-y-cream flex justify-center items-center rounded-xl overflow-hidden ">
            <form>
              {preImg[2] !== undefined ? (
                <Image src={preImg[2]} alt="bg" width={500} height={500} />
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
            </form>
          </div>
        )}
      </div>
    </div>
  );
}
