import { Path, UseFormRegister } from 'react-hook-form';
interface IFormValues {
  email: string;
  password: string;
  text: string;
}

type InputProps = {
  type: Path<IFormValues>;
  placeholder: string;
  inputState: string;
  register: UseFormRegister<IFormValues>;
  required: boolean;
  setInputState: React.Dispatch<React.SetStateAction<string>>;
};

const inputContainerClassName = 'text-sm font-light block mx-2 my-4';
const inputClassName =
  'border border-y-gray rounded-xl focus:outline-y-gold focus:ring-1 block w-full p-2.5 placeholder-slate-300';

export const Input = ({
  type,
  placeholder,
  inputState,
  register,
  required,
  setInputState,
}: InputProps) => {
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
        value={inputState}
        {...register(type, { required })}
        onChange={(e) => {
          onInputChange(e);
        }}
      />
    </section>
  );
};
