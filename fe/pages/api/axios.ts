import axios from 'axios';

let instance = axios.create({
  baseURL: process.env.API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

<<<<<<< HEAD
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
=======
>>>>>>> 210f325c2c5ab385cb9061094de8a7ce5924a266
export default instance;
