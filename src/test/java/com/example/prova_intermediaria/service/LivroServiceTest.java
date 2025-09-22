package com.example.prova_intermediaria.service;

import com.example.prova_intermediaria.dto.LivroDTO;
import com.example.prova_intermediaria.livro.Livro;
import com.example.prova_intermediaria.repository.LivroRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

    @Mock
    LivroRepository repo;

    @InjectMocks
    LivroService service;

    @Test
    void create_setaDataCadastroSeNull_eSalva() {
        LivroDTO dto = new LivroDTO(null, "Clean Code", "Robert C. Martin", "Técnico", 2008, null);
        when(repo.save(any(Livro.class))).thenAnswer(inv -> inv.getArgument(0));

        LivroDTO salvo = service.create(dto);

        ArgumentCaptor<Livro> cap = ArgumentCaptor.forClass(Livro.class);
        verify(repo).save(cap.capture());
        assertThat(cap.getValue().getDataCadastro()).isNotNull();
        assertThat(salvo.titulo()).isEqualTo("Clean Code");
    }

    @Test
    void delete_idInexistente_naoLanca() {
        when(repo.existsById(999)).thenReturn(false);
        service.delete(999);
        verify(repo, never()).deleteById(anyInt());
    }

    @Test
    void delete_existente_deleta() {
        when(repo.existsById(1)).thenReturn(true);
        service.delete(1);
        verify(repo).deleteById(1);
    }

    @Test
    void listAll_mapLeveParaDTO() {
        Livro l = new Livro();
        l.setId(10);
        l.setTitulo("Sapiens");
        l.setAutor("Yuval Noah Harari");
        l.setGenero("História");
        l.setAnoPublicacao(2011);
        l.setDataCadastro(LocalDate.now());

        when(repo.findAll()).thenReturn(List.of(l));

        List<LivroDTO> out = service.listAll();
        assertThat(out).hasSize(1);
        assertThat(out.get(0).titulo()).isEqualTo("Sapiens");
    }

    @Test
    void getById_ok() {
        Livro l = new Livro();
        l.setId(1);
        l.setTitulo("DDD");
        l.setAutor("Eric Evans");
        l.setGenero("Técnico");
        l.setAnoPublicacao(2003);
        l.setDataCadastro(LocalDate.now());

        when(repo.findById(1)).thenReturn(Optional.of(l));

        LivroDTO out = service.getById(1);
        assertThat(out.id()).isEqualTo(1);
    }
}
