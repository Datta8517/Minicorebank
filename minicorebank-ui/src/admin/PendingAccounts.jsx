import { useEffect, useState } from "react";
import { getPendingAccounts, approveAccount } from "../api/adminApi";
import AppLayout from "../components/AppLayout";
import {
  Table, TableHead, TableRow, TableCell, TableBody,
  Button
} from "@mui/material";

export default function PendingAccounts() {
  const [accounts, setAccounts] = useState([]);

  const loadPendingAccounts = () => {
    getPendingAccounts()
      .then(res => setAccounts(res.data))
      .catch(err => console.error(err));
  };

  useEffect(() => {
    loadPendingAccounts();
  }, []);

  const approve = async (accountId) => {
    await approveAccount(accountId);
    alert("Account approved");
    loadPendingAccounts(); // refresh list
  };

  return (
    <AppLayout title="Pending Account Approvals">
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Account ID</TableCell>
            <TableCell>User ID</TableCell>
            <TableCell>Type</TableCell>
            <TableCell>Balance</TableCell>
            <TableCell>Status</TableCell>
            <TableCell>Action</TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {accounts.map(acc => (
            <TableRow key={acc.id}>
              <TableCell>{acc.id}</TableCell>
              <TableCell>{acc.userId}</TableCell>
              <TableCell>{acc.type}</TableCell>
              <TableCell>₹ {acc.balance}</TableCell>
              <TableCell>{acc.approvalStatus}</TableCell>
              <TableCell>
                <Button
                  variant="contained"
                  color="success"
                  onClick={() => approve(acc.id)}
                >
                  Approve
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </AppLayout>
  );
}
