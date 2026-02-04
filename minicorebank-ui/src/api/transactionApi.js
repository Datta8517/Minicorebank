import api from "./axios";

export const transferFunds = (data) =>
  api.post("/transactions/transfer", data);

//not yet used
export const getTransactions = () =>
  api.get("/auth/my-transactions");

export const getLast10Transactions = () =>
  api.get("/auth/my-transactions/last10");

export const deposit = (data) =>
  api.post("/auth/deposit", data);

export const withdraw = (data) =>
  api.post("/auth/withdraw", data);