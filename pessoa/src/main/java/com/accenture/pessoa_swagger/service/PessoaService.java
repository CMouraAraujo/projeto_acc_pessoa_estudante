package com.accenture.pessoa_swagger.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.pessoa_swagger.entity.Pessoa;
import com.accenture.pessoa_swagger.repository.PessoaRepository;
import com.accenture.pessoa_swagger.dto.ViaCepResponse;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private ViaCepService viaCepService;

    public List<Pessoa> getAll() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> getById(long id) {
        return pessoaRepository.findById(id);
    }

    // public Pessoa create(Pessoa pessoa) {

    //     String cepLimpo = pessoa.getCep().replace("-", "");

    //     ViaCepResponse endereco = viaCepService.buscarCep(cepLimpo);

    //     if (endereco == null || endereco.getCep() == null) {
    //         throw new RuntimeException("CEP inválido ou não encontrado");
    //     }

    //     pessoa.setCep(endereco.getCep());
    //     pessoa.setLogradouro(endereco.getLogradouro());
    //     pessoa.setComplemento(endereco.getComplemento());
    //     pessoa.setBairro(endereco.getBairro());
    //     pessoa.setCidade(endereco.getLocalidade());
    //     pessoa.setUf(endereco.getUf());

    //     return pessoaRepository.save(pessoa);
    // }

    private void preencherEndereco(Pessoa pessoa, ViaCepResponse endereco) {
        pessoa.setCep(endereco.getCep());
        pessoa.setLogradouro(endereco.getLogradouro());
        pessoa.setComplemento(endereco.getComplemento());
        pessoa.setBairro(endereco.getBairro());
        pessoa.setCidade(endereco.getLocalidade());
        pessoa.setUf(endereco.getUf());
    }

    public Pessoa create(Pessoa pessoa) {

    if (pessoa.getCep() == null || pessoa.getCep().isEmpty()) {
        throw new RuntimeException("CEP é obrigatório");
    }

    String cepLimpo = pessoa.getCep().replace("-", "");

    ViaCepResponse endereco = viaCepService.buscarCep(cepLimpo);

    if (endereco == null || Boolean.TRUE.equals(endereco.getErro())) {
        throw new RuntimeException("CEP inválido ou não encontrado");
    }

    preencherEndereco(pessoa, endereco);

    return pessoaRepository.save(pessoa);
    }

    public Optional<Pessoa> update(long id, Pessoa newPessoa) {
        Optional<Pessoa> oldPessoa = pessoaRepository.findById(id);

        if (oldPessoa.isPresent()) {
            Pessoa pessoa = oldPessoa.get();

            pessoa.setNome(newPessoa.getNome());

            if (newPessoa.getCep() != null && !newPessoa.getCep().isEmpty()) {

                String cepLimpo = newPessoa.getCep().replace("-", "");
                ViaCepResponse endereco = viaCepService.buscarCep(cepLimpo);

                if (endereco == null || endereco.getCep() == null) {
                    throw new RuntimeException("CEP inválido");
                }

                pessoa.setCep(endereco.getCep());
                pessoa.setLogradouro(endereco.getLogradouro());
                pessoa.setComplemento(endereco.getComplemento());
                pessoa.setBairro(endereco.getBairro());
                pessoa.setCidade(endereco.getLocalidade());
                pessoa.setUf(endereco.getUf());
            }

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