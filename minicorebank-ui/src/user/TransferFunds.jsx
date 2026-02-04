import { useState } from "react";
import { transferFunds } from "../api/transactionApi";
import AppLayout from "../components/AppLayout";
import { TextField, Button } from "@mui/material";

export default function TransferFunds() {
  const [fromAccountId, setFrom] = useState("");
  const [toAccountId, setTo] = useState("");
  const [amount, setAmount] = useState("");
  const [pin, setPin] = useState("");

  const submit = async () => {
    await transferFunds({
      fromAccountId,
      toAccountId,
      amount: Number(amount),
      pin
    });
    alert("Transfer successful");
  };

  return (
    <AppLayout title="Transfer Funds">
      <TextField label="From Account" fullWidth value={fromAccountId}
        onChange={e => setFrom(e.target.value)} />
      <br /><br />
      <TextField label="To Account" fullWidth value={toAccountId}
        onChange={e => setTo(e.target.value)} />
      <br /><br />
      <TextField label="Amount" type="number" fullWidth value={amount}
        onChange={e => setAmount(e.target.value)} />
      <br /><br />
            <TextField
              type="password"
              label="2FA PIN (required if enabled)"
              fullWidth value={pin}
              onChange={e => setPin(e.target.value)}
            />
            <br /><br />
      <Button variant="contained" onClick={submit}>
        Transfer
      </Button>
    </AppLayout>
  );
}
