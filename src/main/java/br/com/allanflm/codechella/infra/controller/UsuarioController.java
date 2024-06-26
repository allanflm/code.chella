package br.com.allanflm.codechella.infra.controller;

import br.com.allanflm.codechella.domain.entities.usuario.Usuario;
import br.com.allanflm.codechella.application.usecases.AlteralUsuario;
import br.com.allanflm.codechella.application.usecases.CriarUsuario;
import br.com.allanflm.codechella.application.usecases.ExcluirUsuario;
import br.com.allanflm.codechella.application.usecases.ListarUsuarios;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final CriarUsuario criarUsuario;
    private final ListarUsuarios listarUsuarios;
    private final AlteralUsuario alteralUsuario;
    private final ExcluirUsuario excluirUsuario;

    public UsuarioController(CriarUsuario criarUsuario, ListarUsuarios listarUsuarios, AlteralUsuario alteralUsuario, ExcluirUsuario excluirUsuario) {
        this.criarUsuario = criarUsuario;
        this.listarUsuarios = listarUsuarios;
        this.alteralUsuario = alteralUsuario;
        this.excluirUsuario = excluirUsuario;
    }

    @PostMapping
    public UsuarioDto cadastrarUsuario(@RequestBody UsuarioDto dto) {
        Usuario salvo = criarUsuario.cadastrarUsuario(new Usuario(dto.cpf(), dto.nome(), dto.nascimento(),
                dto.email()));

        return new UsuarioDto(salvo.getCpf(), salvo.getNome(), salvo.getNascimento(), salvo.getEmail());

    }

    @GetMapping
    public List<UsuarioDto> listarUsuarios() {
        return listarUsuarios.obterTodosUsuario().stream()
                .map(u -> new UsuarioDto(u.getCpf(), u.getNome(), u.getNascimento(), u.getEmail()))
                .collect(Collectors.toList());
    }

    @PutMapping("/{cpf}")
    public UsuarioDto atualizarUsuario(@PathVariable String cpf, @RequestBody UsuarioDto dto){
        Usuario atualizado = alteralUsuario.alterarDadosUsuario(cpf, new Usuario(dto.cpf(), dto.nome(), dto.nascimento(), dto.email()));
        return new UsuarioDto(atualizado.getCpf(), atualizado.getNome(), atualizado.getNascimento(), atualizado.getEmail());
    }
    @DeleteMapping("/{cpf}")
    public void excluirUsuario(@PathVariable String cpf){
        excluirUsuario.excluirUsuario(cpf);
    }
}
