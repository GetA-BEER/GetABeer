export default function BeerCategoryBtn() {
  const beerCategoryList = [
    'Ale',
    'IPA',
    'Larger',
    'Weizen',
    'Dunkel',
    'Pilsener',
  ];
  return (
    <div className="my-4">
      <div className="mx-3 mt-6 mb-2 font-bold">
        맥주<span className="text-y-gold ml-1">카테고리</span>
      </div>
      <div className="grid grid-cols-6 mx-2 gap-2 items-center font-bold">
        {beerCategoryList.map((el: string, idx: number) => (
          <div
            key={idx.toString()}
            className="flex justify-center items-center h-[54px] lg:h-32 md:h-28 sm:h-24  bg-y-gold text-[8px] lg:text-lg rounded-xl"
          >
            {el}
          </div>
        ))}
      </div>
    </div>
  );
}
