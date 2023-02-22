export const BeerCategoryMatcherToKor = (str: string) => {
  switch (str) {
    case 'ALE':
      return '에일';
    case 'LAGER':
      return '라거';
    case 'WEIZEN':
      return '밀맥주';
    case 'DUNKEL':
      return '흑맥주';
    case 'PILSENER':
      return '필스너';
    case 'FRUIT_BEER':
      return '과일';
    case 'NON_ALCOHOLIC':
      return '무알콜';
    case 'ETC':
      return '기타';
  }
};

export const BeerCountryMatcherToKor = (str: string) => {
  switch (str) {
    case 'Korea':
      return '한국';
    case 'Germany':
      return '독일';
    case '':
      return '';
  }
};
