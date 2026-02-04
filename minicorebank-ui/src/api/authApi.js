import api from "./axios";

export const login = (data) =>
  api.post("/auth/login", data);

export const register = (data) =>
  api.post("/auth/register", data);

export const logout = () => {
  const refreshToken = localStorage.getItem("refreshToken");

  return api.post("/auth/logout", {
    refreshToken
  });
};


export const twoFactorAuth =(data) =>
    api.post("/auth/2fa/setup")