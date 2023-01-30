export default function SmallCard(props: string, placeholder: string) {
  props = 'dddddddddddddddddddddd';
  return (
    <div>
      <form>
        <label className="block">
          <span className="block text-sm font-medium text-slate-700">
            Social Security Number
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
