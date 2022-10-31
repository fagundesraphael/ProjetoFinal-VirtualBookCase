package com.example.demo.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Book;
import com.example.demo.repositories.*;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class BookController{

    @Autowired
    private BookRepository bookRepository;

    // Criando um novo book
    @ApiOperation(value = "Registrando novo book", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book, HttpServletRequest httpRequest){

        //Obtendo o nome do usuário do Token JWT do Header
        Principal principal = httpRequest.getUserPrincipal();

        book.setUsername(principal.getName());

        Book _book = bookRepository.save(book);

        return new ResponseEntity<Book>(_book, HttpStatus.OK);
    };

    // Buscando um book
    @ApiOperation(value = "Buscando book por Id", consumes = "application/json", produces = "application/json")
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") long id){

        Book book = bookRepository.findById(id);
        
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }
    
    //Listando todos os itens
    @ApiOperation(value = "Listando todos os itens", consumes = "application/json", produces = "application/json")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/books")
    public ResponseEntity<List<Book>> listBooks(){

        List<Book> books = bookRepository.findAll();

        if(books.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    // Pesquisando books por username com Query Parameters
    @ApiOperation(value = "Listando itens por username - ADMIN", consumes = "application/json", produces = "application/json")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/books")
    public ResponseEntity<List<Book>> adminListBooksByUserId(@RequestParam(required = false) String username){

        List<Book> books = bookRepository.findByUsername(username); 

        if(books.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    // Pesquisando books por usuário logado
    @ApiOperation(value = "Listando itens por username de usuário logado", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/books/user")
    public ResponseEntity<List<Book>> listBooksByUserId(HttpServletRequest httpRequest){

        // Obtendo username do Token
        Principal principal = httpRequest.getUserPrincipal();

        List<Book> books = bookRepository.findByUsername(principal.getName()); 

        if(books.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    // Pesquisando books por status com Query Parameters
    @ApiOperation(value = "Listando books por status", consumes = "application/json", produces = "application/json")
    @GetMapping("/books/author")// .../books/status?status=true/false
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Book>> listBooksByStatus(@RequestParam String author){

        List<Book> books = bookRepository.findByAuthor(author);

        if(books.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
    }

    // Removendo um book
    @ApiOperation(value = "Excluindo um book", consumes = "application/json", produces = "application/json")
    @DeleteMapping("/books/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteBook(@PathVariable("id") long id){

        bookRepository.deleteById(id);
    
        return new ResponseEntity<Object>("Item excluído com sucesso", HttpStatus.OK);

    }

    // Atualizando um book
    @ApiOperation(value = "Atualizando informações de um livro", consumes = "application/json", produces = "application/json")
    @PutMapping("/books/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Book> updateBook(@PathVariable("id") long id,@Valid @RequestBody Book book){

        Book _book = bookRepository.findById(id);

        if(_book == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        book.setId(id);
        bookRepository.save(book);

        return new ResponseEntity<Book>(_book, HttpStatus.OK);
    }
}
