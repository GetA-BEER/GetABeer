export default function BeerCategory() {
  const beerCategory = [
    'Ale',
    'IPA',
    'Lager',
    'Weizen',
    'Dunkel',
    'Pilsener',
    'Non-Alcoholic',
  ];

  return (
    <div className="mx-2 mt-4">
      <div className="text-sm">선호 맥주 (최대 2개까지 선택 가능)</div>
      <div className="grid grid-cols-4 my-2 gap-2 w-full items-center">
        {beerCategory.map((el: string, idx: number) => (
          <div key={idx.toString()}>
            <input
              type="checkbox"
              name="category"
              id={el}
              value={el}
              className="peer hidden"
            />
            <label
              htmlFor={el}
              className="text-xs h-10 w-full inline-flex items-center justify-center cursor-pointer select-none rounded-xl p-1 text-center peer-checked: border-2 peer-checked:border-y-brown"
            >
              {el}
            </label>
          </div>
        ))}
      </div>
    </div>
  );
}
