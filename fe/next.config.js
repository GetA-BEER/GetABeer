/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
    domains: ['worldbeermarket.kr', 't1.daumcdn.net', 'assabeer.com'],
  },
  reactStrictMode: true,
  env: {
    API_URL: process.env.API_URL,
  },
};

module.exports = nextConfig;
