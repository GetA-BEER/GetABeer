/** @type {import('tailwindcss').Config} */

module.exports = {
  content: [
    './app/**/*.{js,ts,jsx,tsx}',
    './pages/**/*.{js,ts,jsx,tsx}',
    './components/**/*.{js,ts,jsx,tsx}',

    // Or if using `src` directory:
    './src/**/*.{js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {
      fontFamily: {
        sans: ['Gmarket Sans', 'Arial', 'sans-serif'],
        // sans가 제일 기본 상속 폰트이므로 전체 폰트바꾸려면 sans재지정후 맨앞에 원하는 폰트 넣기
      },
      colors: {
        'y-brown': '#CD5F03',
        'y-cream': '#FCF2E9',
        'y-gold': '#F1B31C',
        'y-lightGray': '#DDDDDD',
        'y-yellow': '#FCEA2B',
        'y-lemon': '#FFFBD4',
        'y-gray': '#A7A7A7',
        'y-black': '#000000',
      },
    },
    keyframes: {
      upper: {
        '0%': { transform: 'translateY(20px)' },
        '100%': { transform: 'translateY(0px)' },
      },
    },
    animation: {
      upper: 'upper 0.1s linear',
    },
  },
  plugins: [],
};
