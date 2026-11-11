package br.edu.insper.avaliacoes;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {
    private final AvaliacaoRepository repository;
    public AvaliacaoController(AvaliacaoRepository repository) { this.repository = repository; }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<Avaliacao> listar() { return repository.findAll(Sort.by(Sort.Direction.DESC, "dataAvaliacao")); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Avaliacao cadastrar(@Valid @RequestBody CriarAvaliacaoRequest request) {
        return repository.save(new Avaliacao(request.emailAvaliador(), request.emailAvaliado(), request.comentarios(), request.nota()));
    }
}
