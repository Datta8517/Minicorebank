import { useState } from "react";
import { createAccount } from "../api/adminApi";
import AppLayout from "../components/AppLayout";
import { TextField, Button } from "@mui/material";

export default function CreateAccount() {
  const [userId, setUserId] = useState("");
  const [type, setType] = useState("");
  const [amount, setAmount] = useState("");

  const submit = async () => {

    await createAccount({
      userId,
      type,
      initialDeposit: Number(amount)
    });
    alert("Account created");
  };

  return (
    <AppLayout title="Create Account">
      <TextField
        label="User ID"
        fullWidth
        value={userId}
        onChange={e => setUserId(e.target.value)}
      />
      <br /><br />
      <TextField
        label="Account Type"
        fullWidth
        value={type}
        onChange={e => setType(e.target.value)}
      />
      <br /><br />

      <TextField
        label="Initial Deposit"
        type="number"
        fullWidth
        value={amount}
        onChange={e => setAmount(e.target.value)}
      />
      <br /><br />

      <Button variant="contained" onClick={submit}>
        Create Account
      </Button>
    </AppLayout>
  );
}
