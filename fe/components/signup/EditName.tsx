import { UseFormRegister, RegisterOptions } from 'react-hook-form';

interface IFormValues {
  userBeerTags: Array<string>;
  gender: string;
  age: string;
  userBeerCategories: Array<string>;
  nickname: string;
  image: string[];
}
type InputProps = {
  register: UseFormRegister<IFormValues>;
  rules?: RegisterOptions;
};

export const EditName = ({ rules, register }: InputProps) => {
  return (
    <section className="flex w-full pl-2 pr-3 py-2 justify-between text-sm">
      <div className="self-center text-sm">닉네임</div>
      <input
        className="text-right rounded-xl h-8 w-51 p-2 focus:outline-y-gold focus:ring-1"
        type="text"
        placeholder="닉네임 변경"
        {...(register && register('nickname', rules))}
      />
    </section>
  );
};
