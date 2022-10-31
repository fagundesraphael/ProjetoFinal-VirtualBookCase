package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.demo.models.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
    
    // Listando itens por status (named query)
    List<Book> findByAuthor(String author);

    //Pesquisando um book por id (named query)
    Book findById(long id);

    // Listando itens por id de usuário (query convencional)
    @Query(value = "SELECT * FROM books i WHERE i.username = :username", nativeQuery = true)
    List<Book> findByUsername(@Param("username") String username);

    // Listando todos os itens (named query)
    List<Book> findAll();

    // Excluindo um book (named query)
    void deleteById(long id);

}
