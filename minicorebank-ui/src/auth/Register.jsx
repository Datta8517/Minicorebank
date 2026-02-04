import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { register } from "../api/authApi";

export default function Register() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const submit = async () => {
    if (!username || !password) {
      alert("Username and password required");
      return;
    }

    try {
      await register({ username, password });
      alert("Registration successful. Please login.");
      navigate("/login");
    } catch (err) {
      alert(err.response?.data?.message || "Registration failed");
    }
  };

  return (
    <div style={{ width: 300, margin: "100px auto" }}>
      <h2>Register</h2>

      <input
        placeholder="Username"
        value={username}
        onChange={e => setUsername(e.target.value)}
      />

      <br /><br />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={e => setPassword(e.target.value)}
      />

      <br /><br />

      <button onClick={submit}>Register</button>
    </div>
  );
}
