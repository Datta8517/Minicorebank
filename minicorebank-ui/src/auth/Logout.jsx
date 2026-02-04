import { useEffect } from "react";
import { logout } from "../api/authApi";
import { useNavigate } from "react-router-dom";

export default function Logout() {
  const navigate = useNavigate();

  useEffect(() => {
    const doLogout = async () => {
      try {
        await logout(); // sends refreshToken
      } catch (e) {
        console.warn("Logout failed on server, clearing local session");
      } finally {
        localStorage.removeItem("token");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("role");
        navigate("/login");
      }
    };

    doLogout();
  }, [navigate]);

  return null;
}

