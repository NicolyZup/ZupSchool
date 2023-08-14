package zup.com.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zup.com.school.model.AlunoModel;
import zup.com.school.model.dtos.AlunoDTO;
import zup.com.school.service.AlunoService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/zupSchool/alunos")
public class AlunoController {

    @Autowired
    AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<AlunoDTO>> listarAlunos(){
        List<AlunoModel> alunos = alunoService.listar();
        List<AlunoDTO> alunosDTOS = new ArrayList<>();

        for(AlunoModel aluno : alunos){
            AlunoDTO dto = new AlunoDTO();
            dto.setId(aluno.getId());
            dto.setNome(aluno.getNome());
            dto.setIdade(aluno.getIdade());
            dto.setEmail(aluno.getEmail());
            alunosDTOS.add(dto);
        }

        return ResponseEntity.ok(alunosDTOS);
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> cadastrarAluno(@RequestBody AlunoModel alunoModel){
        AlunoModel novoAluno = alunoService.cadastrar(alunoModel);

        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(novoAluno.getId());
        alunoDTO.setNome(novoAluno.getNome());
        alunoDTO.setIdade(novoAluno.getIdade());
        alunoDTO.setEmail(novoAluno.getEmail());

        return new ResponseEntity<>(alunoDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletarAluno(@PathVariable Long id){
        alunoService.excluir(id);
        return ResponseEntity.ok().body("Aluno(a) excluído(a) com sucesso!");
    }
}
