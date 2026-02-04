import { useState } from "react";
import { requestAccount } from "../api/accountApi";
import AppLayout from "../components/AppLayout";
import { Button, TextField } from "@mui/material";

export default function RequestAccount() {
  const [type, setType] = useState("");
  const [amount, setAmount] = useState("");

  const submit = async () => {
    await requestAccount({
      type,
      initialDeposit: Number(amount)
    });
    alert("Account request submitted");
  };

  return (
    <AppLayout title="Request New Account">
      <TextField
        label="Account Type"
        value={type}
        onChange={e => setType(e.target.value)}
      />
      <br /><br />
      <TextField
        label="Initial Deposit"
        type="number"
        value={amount}
        onChange={e => setAmount(e.target.value)}
      />
      <br /><br />
      <Button variant="contained" onClick={submit}>
        Submit
      </Button>
    </AppLayout>
  );
}
