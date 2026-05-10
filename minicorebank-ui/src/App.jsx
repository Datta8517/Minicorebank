import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./auth/Login";
import UserDashboard from "./user/UserDashboard";
import AdminDashboard from "./admin/AdminDashboard.jsx";
import ProtectedRoute from "./components/ProtectedRoute";
import Logout from "./auth/Logout";
import MyAccounts from "./user/MyAccounts";
import RecentTransactions from "./user/RecentTransactions";
import RequestAccount from "./user/RequestAccount";
import DepositWithdraw from "./user/DepositWithdraw";
import TransferFunds from "./user/TransferFunds";
import UserAllTransactions from "./user/AllTransactions";
import TwoFactorAuthentication from "./user/TwoFactorAuthentication";

import CreateUser from "./admin/CreateUser";
import CreateAccount from "./admin/CreateAccount";
import PendingAccounts from "./admin/PendingAccounts";
import AllAccounts from "./admin/AllAccounts";
import AllUsers from "./admin/AllUsers";
import AllTransactions from "./admin/AllTransactions"
import UserTransactions from "./admin/UserTransactions"
import Register from "./auth/Register"




export default function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* Default */}
        <Route path="/" element={<Navigate to="/login" />} />

        {/* Auth */}
        <Route path="/login" element={<Login />} />
        <Route path="/logout" element={<Logout />} />

        {/* User */}
        <Route
          path="/user"
          element={
            <ProtectedRoute role="CUSTOMER">
              <UserDashboard />
            </ProtectedRoute>
          }
        />

        {/* Admin */}
        <Route
          path="/admin"
          element={
            <ProtectedRoute role="ADMIN">
              <AdminDashboard />
            </ProtectedRoute>
          }
        />

        {/* My accounts */}
        <Route
          path="/user/accounts"
          element={
            <ProtectedRoute role="CUSTOMER">
              <MyAccounts />
            </ProtectedRoute>
          }
        />

        {/* Last 10 Transactions */}
        <Route
          path="/user/transactions/recent"
          element={
            <ProtectedRoute role="CUSTOMER">
              <RecentTransactions />
            </ProtectedRoute>
          }
        />

        {/* All Transactions */}
        <Route
          path="/user/transactions"
          element={
            <ProtectedRoute role="CUSTOMER">
              <UserAllTransactions />
            </ProtectedRoute>
          }
        />

        {/* Request to create new Account */}
        <Route
          path="/user/request-account"
          element={
            <ProtectedRoute role="CUSTOMER">
              <RequestAccount />
            </ProtectedRoute>
          }
        />

        <Route
          path="/user/deposit"
          element={
            <ProtectedRoute role="CUSTOMER">
              <DepositWithdraw />
            </ProtectedRoute>
          }
        />

        <Route
          path="/user/transfer"
          element={
            <ProtectedRoute role="CUSTOMER">
              <TransferFunds />
            </ProtectedRoute>
          }
        />

        <Route
          path="/user/2fa"
          element={
            <ProtectedRoute role="CUSTOMER">
              <TwoFactorAuthentication />
            </ProtectedRoute>
          }
        />


        <Route
            path="/admin/create-user"
            element={
                <ProtectedRoute role="ADMIN">
                    <CreateUser />
                </ProtectedRoute>
            }
        />

        <Route path="/register" element={<Register />} />

        <Route path="/admin/create-account" element={<ProtectedRoute role="ADMIN"><CreateAccount /></ProtectedRoute>} />
        <Route path="/admin/pending-accounts" element={<ProtectedRoute role="ADMIN"><PendingAccounts /></ProtectedRoute>} />
        <Route path="/admin/users" element={<ProtectedRoute role="ADMIN"><AllUsers /></ProtectedRoute>} />
        <Route path="/admin/accounts" element={<ProtectedRoute role="ADMIN"><AllAccounts /></ProtectedRoute>} />
        <Route path="/admin/transactions" element={<ProtectedRoute role="ADMIN"><AllTransactions /></ProtectedRoute>} />
        <Route path="/admin/users/:userId/transactions" element={<ProtectedRoute role="ADMIN"><UserTransactions /></ProtectedRoute>} />




      </Routes>
    </BrowserRouter>
  );
}
