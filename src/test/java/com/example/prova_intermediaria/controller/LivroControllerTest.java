package com.example.prova_intermediaria.controller;

import com.example.prova_intermediaria.dto.LivroDTO;
import com.example.prova_intermediaria.service.LivroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LivroControllerTest {

    @InjectMocks
    LivroController controller;

    @Mock
    LivroService service;

    ObjectMapper om = new ObjectMapper();

    private MockMvc mvc() {
        return MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void post_create_retorna201() throws Exception {
        LivroDTO req = new LivroDTO(null, "DDD", "Eric Evans", "Técnico", 2003, null);
        LivroDTO resp = new LivroDTO(1, req.titulo(), req.autor(), req.genero(), req.anoPublicacao(), LocalDate.now());

        when(service.create(any(LivroDTO.class))).thenReturn(resp);

        mvc().perform(post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titulo", is("DDD")));
    }

    @Test
    void get_list_retorna200() throws Exception {
        LivroDTO l = new LivroDTO(10, "Sapiens", "Yuval Noah Harari", "História", 2011, LocalDate.now());
        when(service.listAll()).thenReturn(List.of(l));

        mvc().perform(get("/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo", is("Sapiens")));
    }

    @Test
    void delete_retorna204() throws Exception {
        mvc().perform(delete("/livros/{id}", 123))
                .andExpect(status().isNoContent());
    }
}
