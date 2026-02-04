import { useState } from "react";
import { createUser } from "../api/adminApi";
import AppLayout from "../components/AppLayout";
import { TextField, Button, MenuItem } from "@mui/material";

export default function CreateUser() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("CUSTOMER");

  const submit = async () => {


    try {
          await createUser({ username, password, role });
              alert("User created");
              setUsername("");
              setPassword("");

        } catch (err) {
          console.error(err);
          alert("Login failed");
        }
  };

  return (
    <AppLayout title="Create User">
      <TextField
        label="Username"
        fullWidth
        value={username}
        onChange={e => setUsername(e.target.value)}
      />
      <br /><br />

      <TextField
        label="Password"
        type="password"
        fullWidth
        value={password}
        onChange={e => setPassword(e.target.value)}
      />
      <br /><br />

      <TextField
        select
        label="Role"
        fullWidth
        value={role}
        onChange={e => setRole(e.target.value)}
      >
        <MenuItem value="CUSTOMER">CUSTOMER</MenuItem>
        <MenuItem value="ADMIN">ADMIN</MenuItem>
      </TextField>
      <br /><br />

      <Button variant="contained" onClick={submit}>
        Create User
      </Button>
    </AppLayout>
  );
}
