import React, { useState } from "react";
import "./styles.css";
import Logo from "../../assets/Logo.png";

import api from "../../services/api";
import { useNavigate } from "react-router";

export default function SignUp() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [role_admin, setRoleAdm] = useState("admin");
  const [role_user, setRole] = useState("user");

  const navigate = useNavigate();

  async function signup(e) {
    e.preventDefault();
    const data = {
      username,
      password,
      email,
    };
    try {
      const response = await api.post("api/auth/signup", data);

      navigate("/");
    } catch (err) {
      alert("Invalid user!");
    }
  }

  return (
    <div className="account-container">
      <div className="side-container">
        <section key={"form-2"} className="form-create">
          <img src={Logo} alt="market logo" />
          <form onSubmit={signup}>
            <div className="container-radio">
              <h1 className="h1-radio">Create your Account</h1>
              <span className="span-radio">Are you a customer or seller?</span>

              <ul className="ul">
                <li className="li">
                  <input
                    type="radio"
                    id="f-option"
                    name="selector"
                    value={role_user}
                  />
                  <label htmlFor="f-option">customer</label>

                  <div className="check"></div>
                </li>

                <li className="li">
                  <input
                    type="radio"
                    id="s-option"
                    name="selector"
                    value={role_admin}
                  />
                  <label htmlFor="s-option">seller</label>

                  <div className="check">
                    <div className="inside"></div>
                  </div>
                </li>
              </ul>
            </div>
            <input
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            <input
              placeholder="E-mail"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <button className="button" type="submit">
              SignUp
            </button>
          </form>
        </section>
      </div>
    </div>
  );
}
