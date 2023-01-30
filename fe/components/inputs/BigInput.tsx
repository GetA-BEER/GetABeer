type InputProps = {
  props: string;
  placeholder: string;
};

export default function BigInput({ props, placeholder }: InputProps) {
  return (
    <div>
      <form>
        <label className="block">
          <span className="block text-sm font-medium text-slate-700">
            Big Input
          </span>
          <input className="border-slate-200 placeholder-slate-400 contrast-more:border-slate-400 contrast-more:placeholder-slate-500" />
          <p className="mt-2 opacity-10 contrast-more:opacity-100 text-slate-600 text-sm">
            We need this to steal your identity.
          </p>
        </label>
      </form>
    </div>
  );
}
