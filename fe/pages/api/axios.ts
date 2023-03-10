import axios from 'axios';

let instance = axios.create({
  baseURL: process.env.API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

// if (typeof window !== 'undefined') {
//   // Perform localStorage action
//   let tmpTOKEN: any = localStorage.getItem('recoil-persist');
//   tmpTOKEN = JSON.parse(tmpTOKEN);
//   const TOKEN = tmpTOKEN.accessToken;
//   if (TOKEN !== '') {
//     instance = axios.create({
//       baseURL: process.env.API_URL,
//       headers: {
//         Authorization: TOKEN,
//         'Content-Type': 'application/json',
//       },
//       withCredentials: true,
//     });
//   }
// }
export default instance;
