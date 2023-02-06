import React, { useState } from 'react';
import TextareaAutosize from 'react-textarea-autosize';

export default function CommentInput() {
  const [inputState, setInputState] = useState<string | undefined>();
  const onInputChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setInputState(e.target.value);
  };
  const handleSubmit = (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    setInputState('');
    //추후 코멘트 post 요청 로직 들어가야 함...!
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
