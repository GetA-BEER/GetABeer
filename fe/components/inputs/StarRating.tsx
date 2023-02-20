import { useState } from 'react';
import { BsStarFill } from 'react-icons/bs';

type StarProps = {
  star: number;
  setStar: React.Dispatch<React.SetStateAction<number>>;
};

export default function StarRating({ star, setStar }: StarProps) {
  return (
    <div className="flex items-center cursor-pointer">
      <label htmlFor="star">
        <span className="flex text-5xl absolute">
          {[1, 2, 3, 4, 5].map((el) => {
            return <BsStarFill key={el} className="text-y-lightGray" />;
          })}
        </span>
        <span className="flex absolute">
          <span
            className="flex overflow-hidden"
            style={{ width: `${star * 20}%` }}
          >
            {[1, 2, 3, 4, 5].map((el) => {
              return (
                <BsStarFill
                  key={el}
                  className="flex-none text-5xl text-y-gold"
                />
              );
            })}
          </span>
        </span>
      </label>
      <input
        id="star"
        type="range"
        className="w-[250px] -m-4 h-[80px] absolute opacity-0 cursor-pointer"
        value={star}
        step={0.5}
        min={0}
        max={5}
        onChange={(e) => {
          setStar(Number(e.target.value));
        }}
      />
    </div>
  );
}
