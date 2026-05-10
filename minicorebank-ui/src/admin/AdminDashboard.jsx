import { Grid, Card, CardContent, Typography, Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import AppLayout from "../components/AppLayout";

export default function AdminDashboard() {
  const navigate = useNavigate();

  const actions = [
    { label: "Create User", path: "/admin/create-user" },
    { label: "Create Account", path: "/admin/create-account" },
    { label: "Pending Approvals", path: "/admin/pending-accounts" },
    { label: "All Users", path: "/admin/users" },
    { label: "All Accounts", path: "/admin/accounts" },
    { label: "All Transactions", path: "/admin/transactions" }
  ];


  return (
    <AppLayout title="Admin Dashboard">
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
