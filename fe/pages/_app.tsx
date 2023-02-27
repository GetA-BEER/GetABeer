import Header from '@/components/Header';
import '@/styles/globals.css';
import type { AppProps } from 'next/app';
import { RecoilRoot } from 'recoil';
import NavBar from '@/components/NavBar';
import Login from '@/pages/login/index';

import { AppContextType } from 'next/dist/shared/lib/utils';
declare global {
  interface Window {
    Kakao: any;
  }
}

export default function App({ Component, pageProps }: AppProps) {
  return (
    <RecoilRoot>
      <Header />
      <Component {...pageProps} />
      <NavBar />
    </RecoilRoot>
  );
}
// App.getInitialProps = async (appContext: AppContextType) => {
//   const appProps = await App.getInitialProps(appContext);

//   const { ctx } = appContext;
//   const allCookies = cookies(ctx);
// };
