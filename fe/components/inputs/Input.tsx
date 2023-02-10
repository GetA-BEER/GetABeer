type InputProps = {
  type: string;
  placeholder: string;
  inputState: string;
  setInputState: React.Dispatch<React.SetStateAction<string>>;
};

const inputContainerClassName = 'text-sm font-light block mx-2 my-4';
const inputClassName =
  'border border-y-gray rounded-xl focus:outline-y-gold focus:ring-1 block w-full p-2.5 placeholder-slate-300';

export const Input = ({
  type,
  placeholder,
  inputState,
  setInputState,
}: InputProps) => {
  const onInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputState(e.target.value);
  };
  return (
    <section className={inputContainerClassName}>
      <input
        className={inputClassName}
        type={type}
        placeholder={placeholder}
        value={inputState}
        onChange={(e) => {
          onInputChange(e);
        }}
      />
    </section>
  );
};
