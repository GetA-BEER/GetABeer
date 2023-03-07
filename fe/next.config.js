/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
    domains: ['getabeer.s3.ap-northeast-2.amazonaws.com'],
  },
  reactStrictMode: true,
  env: {
    API_URL: process.env.API_URL,
  },
};

module.exports = nextConfig;
