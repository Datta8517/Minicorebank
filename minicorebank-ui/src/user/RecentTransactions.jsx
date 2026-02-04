import { useEffect, useState } from "react";
import { getLast10Transactions } from "../api/transactionApi";
import { Table, TableHead, TableRow, TableCell, TableBody } from "@mui/material";
import AppLayout from "../components/AppLayout";

export default function RecentTransactions() {
  const [txns, setTxns] = useState([]);

  useEffect(() => {
    getLast10Transactions()
      .then(res => setTxns(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <AppLayout title="Last 10 Transactions">
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Type</TableCell>
            <TableCell>Amount</TableCell>
            <TableCell>From</TableCell>
            <TableCell>To</TableCell>
            <TableCell>Time</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {txns.map(t => (
            <TableRow key={t.id}>
              <TableCell>{t.type}</TableCell>
              <TableCell>₹ {t.amount}</TableCell>
              <TableCell>{t.fromAccountId}</TableCell>
              <TableCell>{t.toAccountId}</TableCell>
              <TableCell>{new Date(t.createdAt).toLocaleString("en-IN", {
                             dateStyle: "medium",
                             timeStyle: "short"
                           })}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </AppLayout>
  );
}
