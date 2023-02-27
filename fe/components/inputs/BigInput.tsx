import { useState } from 'react';

type InputProps = {
  placeholder: string;
  inputState: string;
  setInputState: React.Dispatch<React.SetStateAction<string>>;
};

export default function BigInput({
  placeholder,
  inputState,
  setInputState,
}: InputProps) {
  const [inputLen, setInputLen] = useState<number | undefined>(0);
  const onInputChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setInputState(e.target.value);
    setInputLen(e.target.value.length);
  };

  return (
    <>
      <form className="font-light">
        <textarea
          className="w-full h-32 rounded-xl p-2 border border-y-gray focus:outline-y-gold placeholder-y-gray resize-none"
          placeholder={placeholder}
          value={inputState}
          maxLength={1000}
          onChange={(e) => {
            onInputChange(e);
          }}
        />
        <div className="text-[5px] text-right text-slate-300 bottom-3 right-2 -mt-1">
          {`(${inputLen}/1000)`}
        </div>
      </form>
    </>
  );
}
