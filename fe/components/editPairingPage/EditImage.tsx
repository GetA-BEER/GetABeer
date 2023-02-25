import Image from 'next/image';
import { AiOutlinePlus, AiOutlineCloseCircle } from 'react-icons/ai';
import imageCompression from 'browser-image-compression';

export default function EditImage({
  imageData,
  setImageData,
  url,
  setUrl,
  type,
  setType,
}: any) {
  interface FileData {
    lastModified: number;
    name: string;
    size: number;
    type: string;
  }

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
        setImageData([...imageData, compressedFile]);
        let tmpUrl = URL.createObjectURL(compressedFile);
        setUrl([...url, tmpUrl]);
        setType([...type, 'file']);
      } catch (error) {}
    }
  }

  // 파일 삭제
  const handleDelete = (e: React.MouseEvent<HTMLElement>, idx: number) => {
    e.preventDefault();

    const copyUrl = url.slice();
    const copyUploadUrl = imageData.slice();
    const copyType = type.slice();

    copyUrl.splice(idx, 1);
    copyUploadUrl.splice(idx, 1);
    copyType.splice(idx, 1);

    setUrl(copyUrl);
    setImageData(copyUploadUrl);
    setType(copyType);
  };

  // 썸네일 사진 선택
  const selectThumnail = (idx: number) => {
    let copyUrl = url.slice();
    let copyUploadUrl = imageData.slice();
    let copyType = type.slice();

    let selectUrl = copyUrl.splice(idx, 1);
    let selectUploadUrl = copyUploadUrl.splice(idx, 1);
    let selectType = copyType.splice(idx, 1);

    setUrl([...selectUrl, ...copyUrl]);
    setImageData([...selectUploadUrl, ...copyUploadUrl]);
    setType([...selectType, ...copyType]);
  };

  return (
    <div className="m-2">
      <div className="mt-4 mb-2 text-base font-semibold">사진</div>

      <form className="grid grid-cols-3 gap-2 h-[105px] md:h-64 sm:h-48">
        {/* first Image */}
        <div className="bg-y-cream flex justify-center items-center rounded-xl overflow-hidden relative">
          {url[0] === undefined ? (
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
              <Image src={url[0]} alt="bg" width={500} height={500} priority />
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
        {url[0] === undefined ? (
          <div className="border-2 border-y-lightGray border-dashed flex justify-center items-center rounded-xl"></div>
        ) : (
          <div className="bg-y-cream flex justify-center items-center rounded-xl overflow-hidden relative">
            {url[1] !== undefined ? (
              <>
                <Image
                  src={url[1]}
                  alt="bg"
                  width={500}
                  height={500}
                  onClick={() => selectThumnail(1)}
                  priority
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
        {url[1] === undefined ? (
          <div className="border-2 border-y-lightGray border-dashed flex justify-center items-center rounded-xl"></div>
        ) : (
          <div className="bg-y-cream flex justify-center items-center rounded-xl overflow-hidden relative">
            {url[2] !== undefined ? (
              <>
                <Image
                  src={url[2]}
                  alt="bg"
                  width={500}
                  height={500}
                  onClick={() => selectThumnail(2)}
                  priority
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
