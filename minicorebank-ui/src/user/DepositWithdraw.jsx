import { useState } from "react";
import { deposit, withdraw } from "../api/transactionApi";
import AppLayout from "../components/AppLayout";
import { TextField, Button } from "@mui/material";

export default function DepositWithdraw() {
  const [accountId, setAccountId] = useState("");
  const [amount, setAmount] = useState("");

  const handleDeposit = async () => {
    await deposit({ accountId, amount: Number(amount) });
    alert("Deposit successful");
  };

  const handleWithdraw = async () => {
    await withdraw({ accountId, amount: Number(amount) });
    alert("Withdraw successful");
  };

  return (
    <AppLayout title="Deposit / Withdraw">
      <TextField
        label="Account ID"
        fullWidth
        value={accountId}
        onChange={e => setAccountId(e.target.value)}
      />
      <br /><br />
      <TextField
        label="Amount"
        type="number"
        fullWidth
        value={amount}
        onChange={e => setAmount(e.target.value)}
      />
      <br /><br />

      <Button variant="contained" onClick={handleDeposit}>
        Deposit
      </Button>
      &nbsp;
      <Button color="error" variant="contained" onClick={handleWithdraw}>
        Withdraw
      </Button>
    </AppLayout>
  );
}
