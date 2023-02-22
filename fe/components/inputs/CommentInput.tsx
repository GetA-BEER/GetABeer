import TextareaAutosize from 'react-textarea-autosize';

type InputProps = {
  inputState: string;
  setInputState: React.Dispatch<React.SetStateAction<string>>;
  postFunc: Function;
};
export default function CommentInput({
  inputState,
  setInputState,
  postFunc,
}: InputProps) {
  const onInputChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setInputState(e.target.value);
  };
  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    postFunc();
  };
  return (
    <form className="flex">
      <TextareaAutosize
        minRows={1}
        maxRows={6}
        className="w-full rounded-l-lg p-2 border border-y-lightGray focus:outline-y-gold placeholder-slate-300 font-light resize-none"
        placeholder="댓글을 남겨보세요"
        value={inputState}
        maxLength={1000}
        onChange={(e) => {
          onInputChange(e);
        }}
        onKeyUp={(e) => {
          if (e.key === 'Enter') {
            postFunc();
          }
        }}
      />
      <button
        className="w-[70px] bg-y-gold rounded-r-lg px-3 py-1 text-xs text-y-black hover:text-white"
        onClick={handleSubmit}
      >
        등록
      </button>
    </form>
  );
}
