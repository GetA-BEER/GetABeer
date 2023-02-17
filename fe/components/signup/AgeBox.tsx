import { useState } from 'react';
import { GoTriangleDown } from 'react-icons/go';

export default function AgeBox() {
  const pairingList = [
    '20대',
    '30대',
    '40대',
    '50대',
    '60대 이상',
    '공개 안함',
  ];
  const [showModal, setShowModal] = useState(false);
  const [checked, setChecked] = useState(false);
  const [category, setCategory] = useState('연령대');
  const onCategoryChange = (select: string) => {
    setChecked(true);
    setCategory(select);
    setShowModal(false);
    console.log(checked);
  };
  return (
    <div className="flex justify-between">
      <div className="m-2 self-center text-sm">연령대</div>
      <div className="m-2">
        <button
          onClick={() => setShowModal(!showModal)}
          className={`${
            checked ? 'text-black' : 'text-y-gray'
          } flex items-center w-52 h-10 border border-y-gray pl-2 pr-3 py-1 text-xs rounded-xl cursor-pointer`}
        >
          <GoTriangleDown className="w-4 h-4 mb-[2px] mr-1 text-y-gray" />
          <span>{category}</span>
        </button>
        {showModal ? (
          <div className="relative w-52 h-0">
            <ul className="bg-white border w-full text-xs rounded-lg x-20 z-20 absolute right-0">
              {pairingList.map((el: string, idx: number) => (
                <li
                  key={idx.toString()}
                  onClick={() => {
                    onCategoryChange(el);
                  }}
                  className="pl-3 py-1 m-1 rounded-lg hover:bg-gray-200 cursor-pointer"
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
    </div>
  );
}
