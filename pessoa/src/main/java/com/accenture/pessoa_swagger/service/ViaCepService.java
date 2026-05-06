package com.accenture.pessoa_swagger.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.accenture.pessoa_swagger.dto.ViaCepResponse;

@Service
public class ViaCepService {

    private final String URL = "https://viacep.com.br/ws/%s/json/";
    private final RestTemplate restTemplate = new RestTemplate();

    public ViaCepResponse buscarCep(String cep) {
        try {
            String url = String.format(URL, cep);

            ViaCepResponse response = restTemplate.getForObject(url, ViaCepResponse.class);

            if (response != null && Boolean.TRUE.equals(response.getErro())) {
                throw new RuntimeException("CEP inválido");
            }

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar ViaCEP", e);
        }
    }
}