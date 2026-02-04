import { AppBar, Toolbar, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";

export default function AppLayout({ title, children }) {
  const navigate = useNavigate();

  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" sx={{ flexGrow: 1 }}>
            {title}
          </Typography>
          <Button color="inherit" onClick={() => navigate("/logout")}>
            Logout
          </Button>
        </Toolbar>
      </AppBar>

      <div style={{ padding: "20px" }}>
        {children}
      </div>
    </>
  );
}
