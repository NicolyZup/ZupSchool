package zup.com.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zup.com.school.model.AlunoModel;
import zup.com.school.model.CursoModel;
import zup.com.school.model.MatriculaModel;
import zup.com.school.model.dtos.AlunoDTO;
import zup.com.school.model.dtos.CursoDTO;
import zup.com.school.model.dtos.MatriculaDTO;
import zup.com.school.service.AlunoService;
import zup.com.school.service.CursoService;
import zup.com.school.service.MatriculaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/zupSchool/matriculas")
public class MatriculaController {

    @Autowired
    MatriculaService matriculaService;

    @Autowired
    AlunoService alunoService;

    @Autowired
    CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<MatriculaDTO>> listarMatriculas() {
        List<MatriculaModel> matriculas = matriculaService.listar();
        List<MatriculaDTO> matriculasDTOS = new ArrayList<>();

        for (MatriculaModel matricula : matriculas) {
            MatriculaDTO matriculaDTO = new MatriculaDTO();

            AlunoDTO alunoDTO = new AlunoDTO();
            alunoDTO.setId(matricula.getAluno().getId());
            alunoDTO.setNome(matricula.getAluno().getNome());
            alunoDTO.setIdade(matricula.getAluno().getIdade());
            alunoDTO.setEmail(matricula.getAluno().getEmail());

            CursoDTO cursoDTO = new CursoDTO();
            cursoDTO.setId(matricula.getCurso().getId());
            cursoDTO.setNome(matricula.getCurso().getNome());
            cursoDTO.setCargaHoraria(matricula.getCurso().getCargaHoraria());

            matriculaDTO.setId(matricula.getId());
            matriculaDTO.setDataDaMatricula(matricula.getDataDaMatricula());
            matriculaDTO.setAluno(alunoDTO);
            matriculaDTO.setCurso(cursoDTO);

            matriculasDTOS.add(matriculaDTO);
        }

        return ResponseEntity.ok(matriculasDTOS);
    }

    @PostMapping
    public ResponseEntity<MatriculaDTO> matricularAluno(@RequestBody MatriculaModel matriculaModel) {
        MatriculaModel novaMatricula = matriculaService.cadastrar(matriculaModel);
        Optional<AlunoModel> alunoModel = alunoService.buscarPorId(matriculaModel.getAluno().getId());
        Optional<CursoModel> cursoModel = cursoService.buscarPorId(matriculaModel.getCurso().getId());

        AlunoDTO alunoDTO = new AlunoDTO();
        alunoDTO.setId(alunoModel.get().getId());
        alunoDTO.setNome(alunoModel.get().getNome());
        alunoDTO.setIdade(alunoModel.get().getIdade());
        alunoDTO.setEmail(alunoModel.get().getEmail());

        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setId(cursoModel.get().getId());
        cursoDTO.setNome(cursoModel.get().getNome());
        cursoDTO.setCargaHoraria(cursoModel.get().getCargaHoraria());

        MatriculaDTO matriculaDTO = new MatriculaDTO();
        matriculaDTO.setId(novaMatricula.getId());
        matriculaDTO.setDataDaMatricula(novaMatricula.getDataDaMatricula());
        matriculaDTO.setAluno(alunoDTO);
        matriculaDTO.setCurso(cursoDTO);

        return new ResponseEntity<>(matriculaDTO, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> atualizarCurso(@PathVariable Long id, @RequestBody MatriculaModel matriculaModel) {
        Optional<MatriculaModel> matricula = matriculaService.buscarPorId(id);

        if (matricula.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Matrícula não localizada.");
        }

        Optional<CursoModel> curso = cursoService.buscarPorId(matriculaModel.getCurso().getId());

        if (curso.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não localizado.");
        }

        matriculaService.atualizarCurso(matricula, curso);
        return ResponseEntity.ok().body("Curso atualizado com sucesso!");
    }
}
