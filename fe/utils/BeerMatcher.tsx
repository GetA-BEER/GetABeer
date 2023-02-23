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

export const BeerCategoryMatcher = (str: string[]) => {
  switch (str) {
    case ['에일']:
      return 'ALE';
    case ['라거']:
      return 'LAGER';
    case ['밀맥주']:
      return 'WEIZEN';
    case ['흑맥주']:
      return 'DUNKEL';
    case ['필스너']:
      return 'PILSENER';
    case ['과일']:
      return 'FRUIT_BEER';
    case ['무알콜']:
      return 'NON_ALCOHOLIC';
    case ['기타']:
      return 'ETC';
  }
};
