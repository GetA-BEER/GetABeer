import axios from 'axios';

const instance = axios.create({
  baseURL: 'https://server.getabeer.co.kr/api',
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

export default instance;
