import { useEffect, useState } from "react";
import {
  getAllAccounts,
  updateAccountStatus
} from "../api/adminApi";
import AppLayout from "../components/AppLayout";
import {
  Table, TableHead, TableRow, TableCell, TableBody,
  Button, MenuItem, Select
} from "@mui/material";

export default function AllAccounts() {
  const [accounts, setAccounts] = useState([]);

  const loadAccounts = () => {
    getAllAccounts()
      .then(res => setAccounts(res.data))
      .catch(err => console.error(err));
  };

  useEffect(() => {
    loadAccounts();
  }, []);

  const changeStatus = async (accountId, status) => {
    await updateAccountStatus({ accountId, status });
    alert("Account status updated");
    loadAccounts();
  };

  return (
    <AppLayout title="All Accounts">
      <Table>
        <TableHead>
          <TableRow>
          <TableCell>Account Holder</TableCell>
            <TableCell>Account ID</TableCell>
            <TableCell>User ID</TableCell>
            <TableCell>Type</TableCell>
            <TableCell>Balance</TableCell>
            <TableCell>Status</TableCell>
            <TableCell>Approval</TableCell>
            <TableCell>Change Status</TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {accounts.map(acc => (
            <TableRow key={acc.id}>
              <TableCell>{acc.username}</TableCell>
              <TableCell>{acc.id}</TableCell>
              <TableCell>{acc.userId}</TableCell>
              <TableCell>{acc.type}</TableCell>
              <TableCell>₹ {acc.balance}</TableCell>
              <TableCell>{acc.status ?? "N/A"}</TableCell>
              <TableCell>{acc.approvalStatus ?? "PENDING"}</TableCell>
              <TableCell>
                <Select
                  size="small"
                  value={acc.status}
                  onChange={(e) =>
                    changeStatus(acc.id, e.target.value)
                  }
                >
                  <MenuItem value="ACTIVE">ACTIVE</MenuItem>
                  <MenuItem value="FROZEN">FROZEN</MenuItem>
                  <MenuItem value="INACTIVE">INACTIVE</MenuItem>
                  <MenuItem value="CLOSED">CLOSED</MenuItem>
                </Select>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </AppLayout>
  );
}
