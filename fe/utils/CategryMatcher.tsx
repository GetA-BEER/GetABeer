export const CategoryMatcherToKor = (str: string) => {
  switch (str) {
    case 'ALL':
      return '전체';
    case 'FRIED':
      return '튀김/부침';
    case 'GRILL':
      return '구이/오븐';
    case 'STIR':
      return '볶음/조림';
    case 'FRESH':
      return '생식/회';
    case 'DRY':
      return '마른안주/견과';
    case 'SNACK':
      return '과자/디저트';
    case 'SOUP':
      return '국/찜/찌개/탕';
    case 'ETC':
      return '기타';
  }
};

export const CategoryMatcherToEng = (str: string) => {
  switch (str) {
    case '전체':
      return 'ALL';
    case '튀김/부침':
      return 'FRIED';
    case '구이/오븐':
      return 'GRILL';
    case '볶음/조림':
      return 'STIR';
    case '생식/회':
      return 'FRESH';
    case '마른안주/견과':
      return 'DRY';
    case '과자/디저트':
      return 'SNACK';
    case '국/찜/찌개/탕':
      return 'SOUP';
    case '기타':
      return 'ETC';
  }
};
