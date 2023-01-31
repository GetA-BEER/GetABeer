import { useState } from 'react';
import { GoTriangleDown } from 'react-icons/go';

export default function PairingBox() {
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
    <>
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
            <li
              onClick={() => {
                onCategoryChange('튀김/부침');
              }}
              className="pl-3 py-1"
            >
              튀김/부침
            </li>
            <li
              onClick={() => {
                onCategoryChange('구이/오븐');
              }}
              className="pl-3 py-1"
            >
              구이/오븐
            </li>
            <li
              onClick={() => {
                onCategoryChange('생식/회');
              }}
              className="pl-3 py-1"
            >
              생식/회
            </li>
            <li
              onClick={() => {
                onCategoryChange('마른안주/견과');
              }}
              className="pl-3 py-1"
            >
              마른안주/견과
            </li>
            <li
              onClick={() => {
                onCategoryChange('과자/디저트');
              }}
              className="pl-3 py-1"
            >
              과자/디저트
            </li>
            <li
              onClick={() => {
                onCategoryChange('국/찜/찌개/탕');
              }}
              className="pl-3 py-1"
            >
              국/찜/찌개/탕
            </li>{' '}
            <li
              onClick={() => {
                onCategoryChange('기타');
              }}
              className="pl-3 py-1"
            >
              기타
            </li>
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
