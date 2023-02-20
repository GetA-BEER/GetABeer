import { UseFormRegister, RegisterOptions } from 'react-hook-form';

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
const list = [
  {
    type: 'MALE',
    list: '남',
  },
  {
    type: 'FEMALE',
    list: '여',
  },
  {
    type: 'REFUSE',
    list: '공개 안함',
  },
];

export default function GenderBtn({ rules, register }: InputProps) {
  return (
    <div className="flex justify-between">
      <div className="m-2 self-center text-sm">성별</div>
      <div className="h-10 w-52 m-2 p-1 grid grid-cols-3 mx-2 items-center rounded-xl border border-y-gray">
        {list.map((el: any, idx: number) => (
          <div key={idx.toString()}>
            <input
              type="radio"
              id={el.type}
              value={el.type}
              className="peer hidden"
              {...(register && register('gender', rules))}
            />
            <label
              htmlFor={el.type}
              className="text-xs block cursor-pointer select-none rounded-xl p-2 text-center peer-checked:bg-y-gold"
            >
              {el.list}
            </label>
          </div>
        ))}
      </div>
    </div>
  );
}
