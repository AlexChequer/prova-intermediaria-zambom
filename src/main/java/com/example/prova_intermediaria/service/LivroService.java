package com.example.prova_intermediaria.service;

import com.example.prova_intermediaria.dto.LivroDTO;
import com.example.prova_intermediaria.livro.Livro;
import com.example.prova_intermediaria.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class LivroService {

    private final LivroRepository repo;

    public LivroService(LivroRepository repo) {
        this.repo = repo;
    }

    public LivroDTO create(LivroDTO dto) {
        Livro entidade = dto.toModel();
        if (entidade.getDataCadastro() == null) {
            entidade.setDataCadastro(LocalDate.now());
        }
        Livro salvo = repo.save(entidade);
        return LivroDTO.fromModel(salvo);
    }

    public void delete(Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    public List<LivroDTO> listAll() {
        return repo.findAll().stream().map(LivroDTO::fromModel).toList();
    }

    public LivroDTO getById(Integer id) {
        Livro l = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Livro não encontrado"));
        return LivroDTO.fromModel(l);
    }
}
