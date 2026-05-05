package com.accenture.pessoa_swagger.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.pessoa_swagger.entity.Pessoa;
import com.accenture.pessoa_swagger.repository.PessoaRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> getAll() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> getById(long id) {
        return pessoaRepository.findById(id);
    }

    public Pessoa create(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Optional<Pessoa> update(long id, Pessoa newPessoa) {
        Optional<Pessoa> oldPessoa = pessoaRepository.findById(id);

        if (oldPessoa.isPresent()) {
            Pessoa pessoa = oldPessoa.get();
            pessoa.setNome(newPessoa.getNome());
            return Optional.of(pessoaRepository.save(pessoa));
        }

        return Optional.empty();
    }

    public boolean delete(long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);

        if (pessoa.isPresent()) {
            pessoaRepository.delete(pessoa.get());
            return true;
        }

        return false;
    }
}