import axios from "axios";

const rest = axios.create({
  baseURL: "http://localhost:8081/v1/subscription",
});

export const getPublicKey = async () => {
  try {
    return (await rest.get("/pubkey")).data.publicKey;
  } catch (err) {
    console.error(err.toJSON());
    throw new Error(err.message);
  }
};

export const postSubscription = async (subscription) => {
  try {
    return (await rest.post("", subscription)).data;
  } catch (err) {
    console.error(err.toJSON());
    throw new Error(err.message);
  }
};

export const deleteSubscription = async (endpoint) => {
  try {
    return (await rest.delete("", {data: {endpoint: endpoint}})).data;
  } catch (err) {
    console.error(err.toJSON());
    throw new Error(err.message);
  }
};