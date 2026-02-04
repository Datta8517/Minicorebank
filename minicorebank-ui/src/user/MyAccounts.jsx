import { useEffect, useState } from "react";
import { getMyAccounts } from "../api/accountApi";
import { Table, TableHead, TableRow, TableCell, TableBody } from "@mui/material";
import AppLayout from "../components/AppLayout";

export default function MyAccounts() {
  const [accounts, setAccounts] = useState([]);

  useEffect(() => {
    getMyAccounts()
      .then(res => setAccounts(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <AppLayout title="My Accounts">
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Account ID</TableCell>
            <TableCell>Type</TableCell>
            <TableCell>Balance</TableCell>
            <TableCell>Status</TableCell>
            <TableCell>Approval</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {accounts.map(acc => (
            <TableRow key={acc.id}>
              <TableCell>{acc.id}</TableCell>
              <TableCell>{acc.type}</TableCell>
              <TableCell>₹ {acc.balance}</TableCell>
              <TableCell>{acc.status}</TableCell>
              <TableCell>{acc.approvalStatus}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </AppLayout>
  );
}
