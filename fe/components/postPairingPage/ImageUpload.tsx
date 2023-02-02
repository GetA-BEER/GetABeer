import { AiOutlinePlus } from 'react-icons/ai';
export default function ImageUpload() {
  return (
    <div className="m-5">
      <div className="mt-4 mb-2 text-base font-semibold">사진</div>

      <div className="grid grid-cols-3 gap-2 h-[105px] md:h-64 sm:h-48">
        <div className="bg-y-cream flex justify-center items-center rounded-xl">
          <form method="post">
            <button>
              <AiOutlinePlus className="w-10 h-10" />
            </button>
            {/* <input
              type="file"
              id="chooseFile"
              name="chooseFile"
              accept="image/*"
            /> */}
          </form>
        </div>
        {/*
        <div className="bg-y-cream flex justify-center items-center rounded-xl">
          <AiOutlinePlus className="w-10 h-10" />
        </div>
        <div className="bg-y-cream flex justify-center items-center rounded-xl">
          <AiOutlinePlus className="w-10 h-10" />
        </div> */}
      </div>
    </div>
  );
}
