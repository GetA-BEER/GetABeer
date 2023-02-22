import { UseFormRegister, RegisterOptions } from 'react-hook-form';
interface IFormValues {
  userBeerTags: string;
  gender: string;
  age: string;
  userBeerCategories: string;
}
type InputProps = {
  register: UseFormRegister<IFormValues>;
  rules?: RegisterOptions;
};
export default function BeerCategory({ rules, register }: InputProps) {
  const beerCategory = [
    {
      type: 'ALE',
      text: '에일',
    },
    {
      type: 'LAGER',
      text: '라거',
    },
    {
      type: 'WEIZEN',
      text: '바이젠',
    },
    {
      type: 'DUNKEL',
      text: '흑맥주',
    },
    {
      type: 'PILSENER',
      text: '필스너',
    },
    {
      type: 'FRUIT_BEER',
      text: '과일 맥주',
    },
    {
      type: 'NON_ALCOHOLIC',
      text: '논 알콜',
    },
  ];

  return (
    <div className="px-2 pt-2">
      <div className="text-sm">선호 맥주 (최대 2개까지 선택 가능)</div>
      <div className="grid grid-cols-4 my-2 gap-1 w-full items-center">
        {beerCategory.map((el: any, idx: number) => (
          <div key={idx.toString()}>
            <input
              type="checkbox"
              id={el.type}
              value={el.type}
              className="peer hidden"
              {...(register && register('userBeerCategories', rules))}
            />
            <label
              htmlFor={el.type}
              className="text-xs h-10 w-full inline-flex items-center justify-center cursor-pointer select-none rounded-xl p-1 text-center peer-checked: border-2 peer-checked:border-y-brown"
            >
              {el.text}
            </label>
          </div>
        ))}
      </div>
    </div>
  );
}
