import { UseFormRegister, RegisterOptions } from 'react-hook-form';
import { useEffect } from 'react';
export type Sort =
  | 'TEENAGER'
  | 'TWENTIES'
  | 'THIRTIES'
  | 'FORTIES'
  | 'OVER'
  | 'REFUSE';
interface IFormValues {
  beerTagType: string;
  gender: string;
  age: string;
  userBeerCategories: string;
}
type InputProps = {
  register: UseFormRegister<IFormValues>;
  rules?: RegisterOptions;
};
export default function AgeBox({ rules, register }: InputProps) {
  const pairingList = [
    {
      type: 'REFUSE',
      age: '공개안함',
    },
    {
      type: 'TWENTIES',
      age: '20대',
    },
    {
      type: 'THIRTIES',
      age: '30대',
    },
    {
      type: 'FORTIES',
      age: '40대',
    },
    {
      type: 'OVER',
      age: '50대 이상',
    },
  ];

  return (
    <div className="flex justify-between">
      <div className="m-2 self-center text-sm">연령대</div>
      <div className="m-2">
        <select
          className="bg-white border  border-y-gray p-3 text-xs rounded-xl h-10 w-52 "
          {...(register && register('age', rules))}
        >
          {pairingList.map((el: any, idx: number) => (
            <option className="pl-3 py-1" key={idx.toString()} value={el.type}>
              {el.age}
            </option>
          ))}
        </select>
      </div>
    </div>
  );
}
