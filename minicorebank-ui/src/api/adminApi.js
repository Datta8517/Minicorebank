import api from "./axios";

// USERS
export const createUser = (data) =>
  api.post("/admin/create", data);

export const getAllUsers = () =>
  api.get("/admin/all-users");

export const deleteUser = (userId) =>
  api.delete(`/admin/delete-user/${userId}`);


//Done
export const updateUserStatus = (data) =>
  api.put("/admin/user/status", data);

// ACCOUNTS //Not working
export const createAccount = (data) =>
  api.post("/admin/create-accounts", data);

//Done
export const getAllAccounts = () =>
  api.get("/admin/all-accounts");

//Done
export const updateAccountStatus = (data) =>
  api.put("/accounts/account/status", data);

// APPROVALS // Done
export const getPendingAccounts = () =>
  api.get("/accounts/pending-accounts");

//Done
export const approveAccount = (accountId) =>
  api.put(`/accounts/approve/${accountId}`);

// TRANSACTIONS Done
export const getAllTransactions = () =>
  api.get("/admin/all-transactions");

//Done
export const getUserTransactions = (userId) =>
  api.get(`/admin/users/${userId}/transactions`);
