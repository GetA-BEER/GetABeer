import axios from './axios';

export const useFetch = async (url: string) => {
  try {
    const response = await axios.get(url);
    return response.data;
  } catch (error) {
    console.warn(error);
    return error;
  }
};
