import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import Books from "./pages/Books";
import NewBooks from "./pages/AddBook";
import SignUp from "./pages/SignUp";
export default function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/books" element={<Books />} />
        <Route path="/books/new/:bookId" element={<NewBooks />} />
        <Route path="/signup" element={<SignUp />} />
      </Routes>
    </BrowserRouter>
  );
}
