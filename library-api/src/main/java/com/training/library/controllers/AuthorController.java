package com.training.library.controllers;

import com.training.library.IAuthorService;
import com.training.library.dtos.AuthorDto;
import com.training.library.dtos.AuthorViewDto;
import com.training.library.enums.Language;
import com.training.library.enums.Nationality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private IAuthorService authorService;

    @GetMapping()
    public ResponseEntity<List<AuthorViewDto>> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Language language,
            @RequestParam(required = false) Nationality nationality
    ) {
        return new ResponseEntity<>(authorService.getAllAuthors(name, language, nationality), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        return new ResponseEntity<>(authorService.createAuthor(authorDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Integer id, @RequestBody AuthorDto authorDto) {
        return new ResponseEntity<>(authorService.updateAuthor(id, authorDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAuthor(@PathVariable Integer id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
