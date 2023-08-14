package zup.com.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zup.com.school.model.CursoModel;
import zup.com.school.model.ProfessorModel;
import zup.com.school.model.dtos.CursoDTO;
import zup.com.school.model.dtos.ProfessorDTO;
import zup.com.school.service.CursoService;
import zup.com.school.service.ProfessorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/zupSchool/professores")
public class ProfessorController {

    @Autowired
    ProfessorService professorService;
    @Autowired
    CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<ProfessorDTO>> listarProfessores(){
        List<ProfessorModel> professores = professorService.listar();
        List<ProfessorDTO> professoresDTOS = new ArrayList<>();

        for(ProfessorModel professor : professores){
            ProfessorDTO dto = new ProfessorDTO();

            CursoDTO cursoDTO = new CursoDTO();
            cursoDTO.setId(professor.getCurso().getId());
            cursoDTO.setNome(professor.getCurso().getNome());
            cursoDTO.setCargaHoraria(professor.getCurso().getCargaHoraria());

            dto.setId(professor.getId());
            dto.setNome(professor.getNome());
            dto.setIdade(professor.getIdade());
            dto.setSalario(professor.getSalario());
            dto.setCurso(cursoDTO);
            professoresDTOS.add(dto);
        }

        return ResponseEntity.ok(professoresDTOS);
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> cadastrarProfessor(@RequestBody ProfessorModel professorModel){
        ProfessorModel novoProfessor = professorService.cadastrar(professorModel);
        Optional<CursoModel> cursoModel = cursoService.buscarPorId(professorModel.getCurso().getId());

        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(cursoModel.get().getId());
        cursoDTO.setNome(cursoModel.get().getNome());
        cursoDTO.setCargaHoraria(cursoModel.get().getCargaHoraria());

        ProfessorDTO professorDTO = new ProfessorDTO();
        professorDTO.setId(novoProfessor.getId());
        professorDTO.setNome(novoProfessor.getNome());
        professorDTO.setIdade(novoProfessor.getIdade());
        professorDTO.setSalario(novoProfessor.getSalario());
        professorDTO.setCurso(cursoDTO);

        return new ResponseEntity<>(professorDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletarProfessor(@PathVariable Long id){
        professorService.excluir(id);
        return ResponseEntity.ok().body("Professor(a) excluído(a) com sucesso!");
    }
}
