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
        source: '/api/:slug*',
        destination: 'http://localhost:8080/api/:slug*',
      },
    ];
  },
};

module.exports = nextConfig;
