import { UseFormRegister, RegisterOptions } from 'react-hook-form';
import { IoCamera } from 'react-icons/io5';
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

export const EditImg = ({ rules, register }: InputProps) => {
  return (
    <section>
      <input
        id="file"
        className="peer hidden"
        type="file"
        {...(register && register('image', rules))}
        accept="image/*"
      />
      <label htmlFor="file" className="cursor-pointer">
        <IoCamera className="w-8 h-8 bg-white border border-gray-300 rounded-full p-1 text-y-brown" />
      </label>
    </section>
  );
};
