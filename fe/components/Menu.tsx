import { useState } from 'react';
import { HiMenu } from 'react-icons/hi';

export default function Menu() {
  const [showModal, setShowModal] = useState(false);
  const beerCategoryList: string[] = [
    'Ale',
    'Dunkel',
    'IPA',
    'Pilsender',
    'Lager',
    'Weizen',
    'etc',
  ];
  const pairingList: string[] = [
    '튀김/부침',
    '구이/오븐',
    '생식/회',
    '마른안주/견과',
    '과자/디저트',
    '국/찜/찌개/탕',
    '기타',
  ];

  return (
    <>
      <HiMenu
        className="m-auto text-3xl py-[1px] "
        onClick={() => setShowModal(!showModal)}
      />

      {showModal ? (
        <div className="fixed bottom-[53px] bg-y-gold right-0 w-1/2 md:w-1/3 h-full  rounded-lg text-start p-8 text-black">
          <div className="pt-12 text-xl font-bold">Beer</div>
          <ul className="list-disc pl-4 py-1 pb-3">
            {beerCategoryList.map((el: string, idx: number) => (
              <li key={idx.toString()} className="py-1">
                {el}
              </li>
            ))}
          </ul>
          <div className="text-xl font-bold py-3 border-t-2">Pairing</div>
          <ul className="list-disc pl-4 py-1">
            {pairingList.map((el: string, idx: number) => (
              <li key={idx.toString()} className="py-1">
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
