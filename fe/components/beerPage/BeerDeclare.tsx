import { RatingCardProps } from '@/components/middleCards/RatingCard';

export interface BeerInfo {
  beerId: number;
  beerDetailsBasic: {
    korName: string;
    engName: string;
    country: string;
    thumbnail: string;
    abv: number;
    ibu: number;
  };
  beerCategoryTypes: string[];
  beerDetailsTopTags: string[] | null;
  beerDetailsStars: {
    totalAverageStars: number;
    femaleAverageStars: number;
    maleAverageStars: number;
  };
  beerDetailsCounts: {
    totalStarCount: number;
    femaleStarCount: number;
    maleStarCount: number;
    ratingCount: number;
    pairingCount: number;
  };
  isWishListed: boolean;
  similarBeers: BeerInfo[];
}

export interface RatingInfo {
  data: RatingCardProps[];
  pageInfo: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
    beerId: number;
    beerKorName: string;
    beerEngName: string;
  };
}

export interface PairingCardProps {
  beerId: number;
  korName: string;
  pairingId: number;
  nickname: string;
  userImage: string;
  content: string;
  thumbnail: string;
  category: string;
  likeCount: number;
  commentCount: number;
  isUserLikes: boolean;
  createdAt: string;
  modifiedAt: string;
}

export interface PairingInfo {
  data: PairingCardProps[];
  pageInfo: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
    beerId: number;
    beerKorName: string;
    beerEngName: string;
  };
}

export interface SimilarBeerProps {
  beerId: number;
  korName: string;
  country: string;
  beerCategories: [
    {
      beerCategoryType: string;
    }
  ];
  averageStar: number;
  starCount: number;
  thumbnail: string;
  abv: number;
  ibu: number | null;
}
