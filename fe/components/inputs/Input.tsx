import { Path, UseFormRegister, RegisterOptions } from 'react-hook-form';

interface IFormValues {
  email: string;
  password: string;
  name: string;
  text: string;
  passwordConfirm: string;
}

type InputProps = {
  type: string;
  name: Path<IFormValues>;
  placeholder: string;
  register: UseFormRegister<IFormValues>;
  rules?: RegisterOptions;
};

const inputContainerClassName = 'text-sm font-light block p-2';
const inputClassName =
  'border border-y-gray rounded-xl focus:outline-y-gold focus:ring-1 block w-full p-2.5 placeholder-slate-300';

export const Input = ({
  name,
  rules,
  type,
  placeholder,
  register,
}: InputProps) => {
  return (
    <section className={inputContainerClassName}>
      <input
        className={inputClassName}
        type={type}
        placeholder={placeholder}
        {...(register && register(name, rules))}
      />
    </section>
  );
};
