package com.example.prova_intermediaria.dto;

import com.example.prova_intermediaria.livro.Livro;

import java.time.LocalDate;

public record LivroDTO(
        Integer id,
        String titulo,
        String autor,
        String genero,
        Integer anoPublicacao,
        LocalDate dataCadastro
) {
    public static LivroDTO fromModel(Livro l) {
        if (l == null) return null;
        return new LivroDTO(
                l.getId(),
                l.getTitulo(),
                l.getAutor(),
                l.getGenero(),
                l.getAnoPublicacao(),
                l.getDataCadastro()
        );
    }

    public Livro toModel() {
        Livro l = new Livro();
        l.setId(this.id);
        l.setTitulo(this.titulo);
        l.setAutor(this.autor);
        l.setGenero(this.genero);
        l.setAnoPublicacao(this.anoPublicacao);
        l.setDataCadastro(this.dataCadastro);
        return l;
    }
}
