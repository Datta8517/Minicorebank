import { Grid, Card, CardContent, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import AppLayout from "../components/AppLayout";

export default function UserDashboard() {
  const navigate = useNavigate();

  const actions = [
    { label: "Request New Account", path: "/user/request-account" },
    { label: "My Accounts", path: "/user/accounts" },
    { label: "Last 10 Transactions", path: "/user/transactions/recent" },
    { label: "All Transactions", path: "/user/transactions" },
    { label: "Deposit / Withdraw", path: "/user/deposit" },
    { label: "Transfer Funds", path: "/user/transfer" },
    { label: "Setup 2FA", path: "/user/2fa" }
  ];

  return (
    <AppLayout title="User Dashboard">
      <Grid container spacing={3}>
        {actions.map((a, i) => (
          <Grid item xs={12} sm={6} md={4} key={i}>
            <Card>
              <CardContent>
                <Typography variant="h6">{a.label}</Typography>
                <Button
                  variant="contained"
                  sx={{ mt: 2 }}
                  onClick={() => navigate(a.path)}
                >
                  Open
                </Button>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </AppLayout>
  );
}
