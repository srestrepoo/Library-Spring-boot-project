package com.training.library.controllers;

import com.training.library.IAuthorService;
import com.training.library.dtos.Author.AuthorDto;
import com.training.library.dtos.Author.AuthorViewDto;
import com.training.library.dtos.Author.FilterAuthorDto;
import com.training.library.enums.LanguageEnum;
import com.training.library.enums.NationalityEnum;
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
            @RequestParam(required = false) LanguageEnum language,
            @RequestParam(required = false) NationalityEnum nationality
    ) {
        FilterAuthorDto filterAuthorDto =
                FilterAuthorDto.builder()
                        .name(name).nationality(nationality).nativeLanguage(language).build();
        return new ResponseEntity<>(authorService.getAllAuthorsView(filterAuthorDto), HttpStatus.OK);
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
