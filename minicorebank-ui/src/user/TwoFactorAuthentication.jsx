import { useState } from "react";
import api from "../api/axios";
import AppLayout from "../components/AppLayout";

export default function TwoFactorAuthentication() {
  const [userId, setUserId] = useState("");
  const [pin, setPin] = useState("");
  const [confirmPin, setConfirmPin] = useState("");
  const [loading, setLoading] = useState(false);

  const submit = async () => {
    if (pin.length !== 4 && pin.length !== 6) {
      alert("PIN must be 4 or 6 digits");
      return;
    }

    if (pin !== confirmPin) {
      alert("PINs do not match");
      return;
    }

    try {
      setLoading(true);
      await api.post("/auth/2fa/setup", {userId, pin });
      alert("2FA enabled successfully");
      setPin("");
      setConfirmPin("");
    } catch (err) {
      alert(err.response?.data?.message || "Failed to enable 2FA");
    } finally {
      setLoading(false);
    }
  };

  return (
    <AppLayout title="Enable Two-Factor Authentication">
      <p>
        Once enabled, you will be asked for a PIN during login and sensitive
        operations like transfers and withdrawals.
      </p>

      <input
        placeholder="User Id"
        value={userId}
        onChange={e => setUserId(e.target.value)}
      />
        <br /><br />
      <input
        type="password"
        placeholder="Enter 4 or 6 digit PIN"
        value={pin}
        onChange={e => setPin(e.target.value)}
      />

      <br /><br />

      <input
        type="password"
        placeholder="Confirm PIN"
        value={confirmPin}
        onChange={e => setConfirmPin(e.target.value)}
      />

      <br /><br />

      <button onClick={submit} disabled={loading}>
        {loading ? "Enabling..." : "Enable 2FA"}
      </button>
    </AppLayout>
  );
}
