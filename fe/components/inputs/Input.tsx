import { useState } from 'react';

type InputProps = {
  type: string;
  placeholder: string;
};

const inputContainerClassName = 'text-sm font-light block mx-2 my-4';
const inputClassName =
  'border border-y-gray rounded-xl focus:outline-y-gold focus:ring-1 block w-full p-2.5 placeholder-slate-300';

export const Input = ({ type, placeholder }: InputProps) => {
  const [inputState, setInputState] = useState<string | undefined>();
  const onInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputState(e.target.value);
    console.log(inputState);
  };
  return (
    <section className={inputContainerClassName}>
      <input
        className={inputClassName}
        type={type}
        placeholder={placeholder}
        onChange={(e) => {
          onInputChange(e);
        }}
      />
    </section>
  );
};
