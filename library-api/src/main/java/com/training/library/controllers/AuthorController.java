package com.training.library.controllers;

import com.training.library.IAuthorService;
import com.training.library.dtos.AuthorDto;
import com.training.library.dtos.AuthorViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    IAuthorService authorService;

    @GetMapping()
    public ResponseEntity<List<AuthorViewDto>> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String nationality
    ) {
        return new ResponseEntity<>(authorService.getAllAuthors(name, language, nationality), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        return new ResponseEntity<>(authorService.createAuthor(authorDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AuthorDto> updateAuthor(@RequestBody AuthorDto authorDto) {
        return new ResponseEntity<>(authorService.updateAuthor(authorDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity createAuthor(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
