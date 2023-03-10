import axios from 'axios';

let instance = axios.create({
  baseURL: process.env.API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a18285c5d47bbe8aaf902edbbb9ab5b1fbaebc76
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
<<<<<<< HEAD
=======
>>>>>>> 210f325c2c5ab385cb9061094de8a7ce5924a266
=======

>>>>>>> a18285c5d47bbe8aaf902edbbb9ab5b1fbaebc76
export default instance;
