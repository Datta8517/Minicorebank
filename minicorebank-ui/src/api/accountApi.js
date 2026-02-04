import api from "./axios";

export const getMyAccounts = () =>
  api.get("/accounts/my-account");

export const requestAccount = (data) =>
  api.post("/accounts/create", data);
