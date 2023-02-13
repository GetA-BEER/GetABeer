export const ToDateString = (str: string) => {
  return str?.slice(0, 10).split('-').join('.');
};
