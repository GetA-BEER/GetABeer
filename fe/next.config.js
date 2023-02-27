/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
    domains: [
      'worldbeermarket.kr',
      't1.daumcdn.net',
      'assabeer.com',
      'getabeer.s3.ap-northeast-2.amazonaws.com',
    ],
  },
  reactStrictMode: true,
  env: {
    API_URL: process.env.API_URL,
  },
};

module.exports = nextConfig;
