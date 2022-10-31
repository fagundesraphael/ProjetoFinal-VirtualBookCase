import React, { useState, useEffect } from "react";
import "./styles.css";
import { useNavigate } from "react-router";
import { Link } from "react-router-dom";
import { FiPower, FiEdit, FiTrash2 } from "react-icons/fi";
import api from "../../services/api";
import Logo from "../../assets/Logo.png";

export default function Books() {
  const [books, setBooks] = useState([]);
  const [page, setPage] = useState(0);

  const username = localStorage.getItem("username");

  const navigate = useNavigate();

  async function logout() {
    localStorage.clear();
    navigate("/");
  }

  async function editBook(id) {
    try {
      navigate(`/books/new/${id}`);
    } catch (e) {
      alert("Edit failed! Try again!");
    }
  }
  async function deleteBook(id) {
    try {
      await api.delete(`api/books/${id}`);
      setBooks(books.filter((book) => book.id !== id));
    } catch (e) {
      alert("Delete failed! TryAgain!");
    }
  }

  async function fetchMoreBooks() {
    const response = await api.get("api/books", {
      params: {
        page: page,
        limit: 4,
        direction: "asc",
      },
    });
    setBooks([...books, ...response.data]);
    setPage(page + 1);
  }

  useEffect(() => {
    fetchMoreBooks();
  }, []);

  return (
    <div className="container">
      <header>
        <img src={Logo} alt="Market logo" />
        <span>
          Welcome, <strong>{username.toUpperCase()}</strong>!
        </span>
        <Link className="button" to="/books/new/0">
          Add New Book
        </Link>
        <button type="button" className="button-btn" onClick={logout}>
          <FiPower size={18} color="#251FC5" />
        </button>
      </header>

      <h1>Registered Books</h1>
      <ul>
        {books.map((book) => (
          <li key={book.id}>
            <strong>Title</strong>
            <p>{book.title}</p>
            <strong>Author</strong>
            <p>{book.author}</p>
            <strong>Price</strong>
            <p>
              {Intl.NumberFormat("pt-BR", {
                style: "currency",
                currency: "BRL",
              }).format(book.price)}
            </p>
            <strong>Publication Year:</strong>
            <p>{book.publishedAt}</p>

            <button
              onClick={() => editBook(book.id)}
              className="button-btn"
              type="button"
            >
              <FiEdit size={20} color="#251FC5" />
            </button>
            <button
              onClick={() => deleteBook(book.id)}
              type="button"
              className="button-btn"
            >
              <FiTrash2 size={20} color="#251FC5" />
            </button>
          </li>
        ))}
      </ul>
      <button
        className="button button-btn"
        onClick={fetchMoreBooks}
        type="button"
      >
        Load More
      </button>
    </div>
  );
}
