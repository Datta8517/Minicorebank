import { useEffect, useState } from "react";
import { getUserTransactions } from "../api/adminApi";
import { useParams } from "react-router-dom";
import AppLayout from "../components/AppLayout";
import {
  Table, TableHead, TableRow, TableCell, TableBody
} from "@mui/material";

export default function UserTransactions() {
  const { userId } = useParams();
  const [txns, setTxns] = useState([]);

  useEffect(() => {
    getUserTransactions(userId)
      .then(res => setTxns(res.data))
      .catch(err => console.error(err));
  }, [userId]);

  return (
    <AppLayout title={`Transactions of User: ${userId}`}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Transaction ID</TableCell>
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
