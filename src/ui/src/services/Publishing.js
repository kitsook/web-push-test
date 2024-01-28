import axios from "axios";

const rest = axios.create({
  baseURL: "http://localhost:8081/v1/message",
});

export const postMessage = async (message) => {
  try {
    return (await rest.post("", message)).data;
  } catch (err) {
    console.error(err.toJSON());
    throw new Error(err.message);
  }
};
