import api from "./axios";

export const login = (data) =>
  api.post("/api/auth/login", data);

export const register = (data) =>
  api.post("/api/auth/register", data);

export const logout = () => {
  const refreshToken = localStorage.getItem("refreshToken");

  return api.post("/api/auth/logout", {
    refreshToken
  });
};


export const twoFactorAuth =(data) =>
    api.post("/api/auth/2fa/setup")
