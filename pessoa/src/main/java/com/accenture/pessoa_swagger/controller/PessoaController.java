package com.accenture.pessoa_swagger.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accenture.pessoa_swagger.entity.Pessoa;
import com.accenture.pessoa_swagger.service.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> getAll() {
        return pessoaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getById(@PathVariable long id) {
        Optional<Pessoa> pessoa = pessoaService.getById(id);

        return pessoa
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Pessoa> create(@Valid @RequestBody Pessoa pessoa) {
        Pessoa saved = pessoaService.create(pessoa);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable long id,
                                         @Valid @RequestBody Pessoa pessoa) {

        Optional<Pessoa> updated = pessoaService.update(id, pessoa);

        return updated
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        boolean deleted = pessoaService.delete(id);

        if (deleted)
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}