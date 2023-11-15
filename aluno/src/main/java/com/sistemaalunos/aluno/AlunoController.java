package com.sistemaalunos.aluno;

import com.sistemaalunos.aluno.Aluno;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private final List<Aluno> alunos = new ArrayList<>();
    private long nextId = 1;

    @GetMapping
    public String listarAlunos(Model model) {
        model.addAttribute("alunos", alunos);
        return "listar_alunos";
    }

    @GetMapping("/novo")
    public String mostrarFormNovoAluno(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "adicionar_aluno";
    }

    @PostMapping
    public String adicionarAluno(@ModelAttribute Aluno aluno) {
        aluno.setId(nextId++);
        alunos.add(aluno);
        return "redirect:/alunos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormEditarAluno(@PathVariable("id") long id, Model model) {
        Optional<Aluno> alunoExistente = alunos.stream()
                .filter(a -> a.getId() == id)
                .findFirst();
        if (alunoExistente.isPresent()) {
            model.addAttribute("aluno", alunoExistente.get());
            return "editar_aluno";
        } else {
            return "redirect:/alunos";
        }
    }

    @PostMapping("/editar/{id}")
    public String editarAluno(@PathVariable("id") long id, @ModelAttribute Aluno aluno) {
        alunos.removeIf(a -> a.getId() == id);
        aluno.setId(id);
        alunos.add(aluno);
        return "redirect:/alunos";
    }

    @GetMapping("/remover/{id}")
    public String removerAluno(@PathVariable("id") long id) {
        alunos.removeIf(a -> a.getId() == id);
        return "redirect:/alunos";
    }
}
