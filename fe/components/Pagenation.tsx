import { BsCaretLeftFill, BsCaretRightFill } from 'react-icons/bs';
import { IoPlayBack, IoPlayForward } from 'react-icons/io5';

type pageInfoProps = {
  page: number;
  totalPages: number;
  setPage: React.Dispatch<React.SetStateAction<number>>;
};

export default function Pagenation({
  page,
  totalPages,
  setPage,
}: pageInfoProps) {
  const pagenation = new Array(totalPages).fill(1);
  return (
    <div className="flex justify-center items-center mt-2">
      <button
        onClick={() => {
          setPage(1);
        }}
      >
        <IoPlayBack />
      </button>
      <button
        className="px-2"
        onClick={() => {
          if (page > 1) {
            setPage(page - 1);
          }
        }}
      >
        <BsCaretLeftFill />
      </button>
      {pagenation.map((el: number, idx) => {
        return (
          <button
            key={idx}
            value={el + idx}
            onClick={(e) => {
              setPage(Number(e.currentTarget.value));
            }}
            className={`w-8 h-8 rounded-[6px] text-sm mx-0.5 ${
              page === el + idx ? 'bg-y-gold' : 'bg-y-lightGray'
            }
            ${
              page < 3
                ? el + idx < 6
                  ? ''
                  : 'hidden'
                : page === el + idx - 2 ||
                  page === el + idx - 1 ||
                  page === el + idx ||
                  page === el + idx + 1 ||
                  page === el + idx + 2
                ? ''
                : page >= totalPages - 2
                ? el + idx > totalPages - 5
                  ? ''
                  : 'hidden'
                : 'hidden'
            }`}
          >
            {el + idx}
          </button>
        );
      })}
      <button
        className="px-2"
        onClick={() => {
          if (totalPages > page) {
            setPage(page + 1);
          }
        }}
      >
        <BsCaretRightFill />
      </button>
      <button
        onClick={() => {
          setPage(totalPages);
        }}
      >
        <IoPlayForward />
      </button>
    </div>
  );
}
