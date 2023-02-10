import { useEffect, useState } from 'react';
import { GoTriangleDown } from 'react-icons/go';

export type Sort = 'recency' | 'mostlikes' | 'mostcomments';

type Props = {
  setSort: React.Dispatch<React.SetStateAction<Sort>>;
};

export default function SortBox({ setSort }: Props) {
  const sortList = ['추천순', '최신순', '댓글 많은 순'];
  const [showModal, setShowModal] = useState(false);
  const [category, setCategory] = useState('추천순');

  useEffect(() => {
    if (category === '추천순') {
      setSort('mostlikes');
    } else if (category === '최신순') {
      setSort('recency');
    } else if (category === '댓글 많은 순') {
      setSort('mostcomments');
    }
  }, [category, setSort]);

  const onCategoryChange = (select: string) => {
    setCategory(select);
    setShowModal(false);
  };
  return (
    <div className="m-2">
      <button
        onClick={() => setShowModal(!showModal)}
        className="flex items-center w-32 border border-y-gray pl-2 pr-3 py-1 text-xs rounded-md"
      >
        <GoTriangleDown className="w-3 h-3 mt-[1px] mr-1 text-y-gray" />
        <span>{category}</span>
      </button>
      {showModal ? (
        <div className="relative w-32 h-0">
          <ul className="bg-white border-2 w-full text-xs rounded-lg x-20 z-20 absolute right-0">
            {sortList.map((el: string, idx: number) => (
              <li
                key={idx.toString()}
                onClick={() => {
                  onCategoryChange(el);
                }}
                className="pl-3 py-1"
              >
                {el}
              </li>
            ))}
          </ul>
          <button
            className="inset-0 fixed cursor-default"
            onClick={() => setShowModal(false)}
          ></button>
        </div>
      ) : (
        <></>
      )}
    </div>
  );
}
