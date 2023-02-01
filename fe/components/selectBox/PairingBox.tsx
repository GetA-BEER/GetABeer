import { useState } from 'react';
import { GoTriangleDown } from 'react-icons/go';

export default function PairingBox() {
  const pairingList = [
    '튀김/부침',
    '구이/오븐',
    '생식/회',
    '마른안주/견과',
    '과자/디저트',
    '국/찜/찌개/탕',
    '기타',
  ];
  const [showModal, setShowModal] = useState(false);
  const [checked, setChecked] = useState(false);
  const [category, setCategory] = useState('카테고리');
  const onCategoryChange = (select: string) => {
    setChecked(true);
    setCategory(select);
    setShowModal(false);
    console.log(checked);
  };
  return (
    <div className="m-5">
      <div className="mt-6 mb-2 text-base font-semibold">페어링 카테고리</div>
      <button
        onClick={() => setShowModal(!showModal)}
        className={`${
          checked ? 'text-black' : 'text-y-gray'
        } flex items-center w-32 border border-y-gray pl-2 pr-3 py-1 text-xs rounded-md`}
      >
        <GoTriangleDown className="w-3 h-3 mb-[2px] mr-1 text-y-gray" />
        <span>{category}</span>
      </button>
      {showModal ? (
        <div className="relative w-32 h-0">
          <ul className="bg-white border-2 w-full text-xs rounded-lg x-20 z-20 absolute right-0">
            {pairingList.map((el: string, idx: number) => (
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
