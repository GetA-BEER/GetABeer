import { useState } from 'react';
import { GoTriangleDown } from 'react-icons/go';

export default function SortgBox() {
  const sortList = ['추천순', '최신순', '댓글 많은 순'];
  const [showModal, setShowModal] = useState(false);
  const [checked, setChecked] = useState(false);
  const [category, setCategory] = useState('Sort');
  const onCategoryChange = (select: string) => {
    setChecked(true);
    setCategory(select);
    setShowModal(false);
    console.log(checked);
  };
  return (
    <>
      <button
        onClick={() => setShowModal(!showModal)}
        className={`${
          checked ? 'text-black' : 'text-y-gray'
        } flex items-center w-32 border border-y-gray pl-2 pr-3 py-1 text-xs rounded-md`}
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
    </>
  );
}
