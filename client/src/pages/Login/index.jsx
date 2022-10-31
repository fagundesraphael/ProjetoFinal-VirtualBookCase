import React, { useState } from "react";
import "./styles.css";
import Logo from "../../assets/Logo.png";
import api from "../../services/api";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  async function login(e) {
    e.preventDefault();

    const data = {
      username,
      password,
    };
    try {
      const response = await api.post("api/auth/signin", data);
      localStorage.setItem("username", username);

      navigate("/books");
    } catch (err) {
      alert("Login failed! Try again!");
    }
  }

  return (
    <div className="login-container">
      <section className="form">
        <img src={Logo} alt="market logo" />
        <form onSubmit={login}>
          <h1 className="signh1">Acces your Account</h1>
          <div className="createAccount">
            <span className="signSpan">New to VirtualBookCase?</span>
            <br />
            <Link className="linksign" to="/signup">
              Create your account
            </Link>
          </div>
          <input
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />

          <button className="button" type="submit">
            Sign in
          </button>
        </form>
      </section>
    </div>
  );
}
