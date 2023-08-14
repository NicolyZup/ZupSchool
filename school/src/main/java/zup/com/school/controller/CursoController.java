package zup.com.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zup.com.school.model.CursoModel;
import zup.com.school.model.dtos.CursoDTO;
import zup.com.school.service.CursoService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/zupSchool/cursos")
public class CursoController {

    @Autowired
    CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<CursoDTO>> listarCursos(){
        List<CursoModel> cursos = cursoService.listar();
        List<CursoDTO> cursosDTOS = new ArrayList<>();

        for(CursoModel curso : cursos){
            CursoDTO dto = new CursoDTO();
            dto.setId(curso.getId());
            dto.setNome(curso.getNome());
            dto.setCargaHoraria(curso.getCargaHoraria());
            cursosDTOS.add(dto);
        }

        return ResponseEntity.ok(cursosDTOS);
    }

    @PostMapping
    public ResponseEntity<CursoDTO> cadastrarCurso(@RequestBody CursoModel cursoModel){
        CursoModel novoCurso = cursoService.cadastrar(cursoModel);

        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(novoCurso.getId());
        cursoDTO.setNome(novoCurso.getNome());
        cursoDTO.setCargaHoraria(novoCurso.getCargaHoraria());

        return new ResponseEntity<>(cursoDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> excluirCurso(@PathVariable Long id){
        cursoService.excluir(id);
        return ResponseEntity.ok().body("Curso excluído com sucesso!");
    }
}
