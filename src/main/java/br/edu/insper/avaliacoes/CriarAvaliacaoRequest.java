package br.edu.insper.avaliacoes;
import jakarta.validation.constraints.*;

public record CriarAvaliacaoRequest(
    @NotBlank @Email String emailAvaliador,
    @NotBlank @Email String emailAvaliado,
    @NotBlank @Size(max = 2000) String comentarios,
    @NotNull @Min(0) @Max(10) Integer nota
) { }
