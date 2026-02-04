import { useEffect, useState } from "react";
import {
  getAllUsers,
  deleteUser,
  updateUserStatus
} from "../api/adminApi";
import AppLayout from "../components/AppLayout";
import {
  Table, TableHead, TableRow, TableCell, TableBody,
  Button
} from "@mui/material";
import { useNavigate } from "react-router-dom";

export default function AllUsers() {
  const [users, setUsers] = useState([]);
  const navigate = useNavigate();

  const loadUsers = () => {
    getAllUsers()
      .then(res => {
        const normalized = res.data.map(u => ({
          ...u,
          active: u.active === true || u.active === "true"
        }));
        setUsers(normalized);
      })
      .catch(err => console.error(err));
  };

  useEffect(() => {
    loadUsers();
  }, []);

  const toggleStatus = async (user) => {
    const res = await updateUserStatus({
      userId: user.id,
      active: !user.active
    });
    console.log("RAW USER OBJECT:", user);
    const updated = {
      ...res.data,
      active: res.data.active === true || res.data.active === "true"
    };

    setUsers(prev =>
      prev.map(u => (u.id === user.id ? updated : u))
    );
  };

  const removeUser = async (userId) => {
    if (window.confirm("Are you sure to delete this user?")) {
      await deleteUser(userId);
      alert("User deleted");
      loadUsers();
    }
  };

  return (
    <AppLayout title="All Users">
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>User ID</TableCell>
            <TableCell>Username</TableCell>
            <TableCell>Role</TableCell>
            <TableCell>Status</TableCell>
            <TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {users.map(user => (
            <TableRow key={user.id}>
              <TableCell>{user.id}</TableCell>
              <TableCell>{user.username}</TableCell>
              <TableCell>{user.role}</TableCell>

              <TableCell>
                {user.active ? "ACTIVE" : "INACTIVE"}
              </TableCell>

              <TableCell>
                <Button
                  size="small"
                  variant="outlined"
                  onClick={() => toggleStatus(user)}
                >
                  {user.active ? "Deactivate" : "Activate"}
                </Button>
                &nbsp;
                <Button
                  size="small"
                  variant="contained"
                  color="info"
                  onClick={() =>
                    navigate(`/admin/users/${user.id}/transactions`)
                  }
                >
                  Transactions
                </Button>
                &nbsp;
                <Button
                  size="small"
                  variant="contained"
                  color="error"
                  onClick={() => removeUser(user.id)}
                >
                  Delete
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </AppLayout>
  );
}
