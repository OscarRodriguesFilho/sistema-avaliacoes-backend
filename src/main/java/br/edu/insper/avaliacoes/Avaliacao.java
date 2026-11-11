package br.edu.insper.avaliacoes;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "avaliacoes")
public class Avaliacao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String emailAvaliador;
    @Column(nullable = false) private String emailAvaliado;
    @Column(nullable = false, length = 2000) private String comentarios;
    @Column(nullable = false) private Integer nota;
    @Column(nullable = false, updatable = false) private OffsetDateTime dataAvaliacao;

    protected Avaliacao() { }
    public Avaliacao(String avaliador, String avaliado, String comentarios, Integer nota) {
        this.emailAvaliador = avaliador; this.emailAvaliado = avaliado; this.comentarios = comentarios; this.nota = nota;
    }
    @PrePersist void setDataAvaliacao() { if (dataAvaliacao == null) dataAvaliacao = OffsetDateTime.now(); }
    public Long getId() { return id; }
    public String getEmailAvaliador() { return emailAvaliador; }
    public String getEmailAvaliado() { return emailAvaliado; }
    public String getComentarios() { return comentarios; }
    public Integer getNota() { return nota; }
    public OffsetDateTime getDataAvaliacao() { return dataAvaliacao; }
}
