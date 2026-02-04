import { useEffect, useState } from "react";
import { getAllTransactions } from "../api/adminApi";
import AppLayout from "../components/AppLayout";
import {
  Table, TableHead, TableRow, TableCell, TableBody
} from "@mui/material";

export default function AllTransactions() {
  const [txns, setTxns] = useState([]);

  useEffect(() => {
    getAllTransactions()
      .then(res => setTxns(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <AppLayout title="All Transactions">
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Transaction ID</TableCell>
            <TableCell>Username</TableCell>
            <TableCell>User ID</TableCell>
            <TableCell>Type</TableCell>
            <TableCell>Amount</TableCell>
            <TableCell>From Account</TableCell>
            <TableCell>To Account</TableCell>
            <TableCell>Date</TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {txns.map(txn => (
            <TableRow key={txn.id}>
              <TableCell>{txn.id}</TableCell>
              <TableCell>{txn.username}</TableCell>
              <TableCell>{txn.userId}</TableCell>
              <TableCell>{txn.type}</TableCell>
              <TableCell>₹ {txn.amount}</TableCell>
              <TableCell>{txn.fromAccountId || "-"}</TableCell>
              <TableCell>{txn.toAccountId || "-"}</TableCell>
              <TableCell>
                {new Date(txn.createdAt).toLocaleString()}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </AppLayout>
  );
}
