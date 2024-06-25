package com.api.forumHub.domain.topico;

import com.api.forumHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "topicos")
@NoArgsConstructor
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensagem;

    @CreatedDate
    private LocalDateTime dataCriacao;

    private String curso;

    @ManyToOne
    @JoinColumn(columnDefinition = "autor_id", referencedColumnName = "id")
    private Usuario autor;

    public Topico(DadosRegistroTopico dados, Usuario autor) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.curso = dados.curso();
        this.autor = autor;
    }

    public void atualizar(DadosAtualizacaoTopico dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.curso = dados.curso();
    }
}
