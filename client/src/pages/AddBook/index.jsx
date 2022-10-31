import React, { useState, useEffect } from "react";
import "./style.css";
import Logo from "../../assets/Logo.png";
import { Link, useParams } from "react-router-dom";
import api from "../../services/api";
import { FiArrowLeft } from "react-icons/fi";
import { useNavigate } from "react-router";

export default function AddBook() {
  const newLocal = useState(null);
  const [id, setId] = newLocal;
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");
  const [price, setPrice] = useState("");
  const [publishedAt, setPublishedAt] = useState("");

  const { bookId } = useParams();

  const username = localStorage.getItem("username");

  const navigate = useNavigate();

  async function loadBook() {
    try {
      const response = await api.get(`/api/books/${bookId}`);
      setId(response.data.id);
      setTitle(response.data.title);
      setAuthor(response.data.author);
      setPrice(response.data.price);
      setPublishedAt(response.data.publishedAt);
    } catch (err) {
      alert("Error recovering book! Try again!");
      navigate("/books");
    }
  }

  useEffect(() => {
    if (bookId === "0") return;
    else loadBook();
  }, [bookId]);

  async function saveOrUpdate(e) {
    e.preventDefault();

    const data = {
      id,
      title,
      author,
      price,
      publishedAt,
    };

    try {
      if (bookId === "0") {
        await api.post(`api/books/`, data);
      } else {
        data.id = id;
        await api.put(`api/books/${id}`, data);
      }
      navigate("/books");
    } catch (err) {
      alert("Error while recording book! Try again!!");
    }
  }

  return (
    <div className="add-book-container">
      <div className="content">
        <section className="form">
          <img src={Logo} alt="your logo" />
          <h1>{bookId === "0" ? "Add new" : "Update"} Book</h1>
          <p>
            Enter the book information and click on
            {bookId === "0" ? " 'Add' " : "'Update'"}!
          </p>
          <Link className="backward button-btn" to="/books">
            <FiArrowLeft size={16} color="251fc5" />
            <strong> Back to Books</strong>
          </Link>
        </section>
        <form onSubmit={saveOrUpdate}>
          <input
            placeholder="Title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
          <input
            placeholder="Author"
            value={author}
            onChange={(e) => setAuthor(e.target.value)}
          />
          <input
            placeholder="Price"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
          />
          <input
            placeholder="Publication Year"
            value={publishedAt}
            onChange={(e) => setPublishedAt(e.target.value)}
          />
          <button className="button button-btn" type="submit">
            {bookId === "0" ? "Add" : "Update"}
          </button>
        </form>
      </div>
    </div>
  );
}
