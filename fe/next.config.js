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
  async rewrites() {
    return [
      {
        source: '/43/:path',
        destination: 'http://43.201.79.99:8080/api/:path',
      },
    ];
  },
  env: {
    API_URL: process.env.API_URL,
  },
};

module.exports = nextConfig;
