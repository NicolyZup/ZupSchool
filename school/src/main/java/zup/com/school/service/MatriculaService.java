package zup.com.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zup.com.school.model.CursoModel;
import zup.com.school.model.MatriculaModel;
import zup.com.school.repository.MatriculaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MatriculaService {

    @Autowired
    MatriculaRepository matriculaRepository;

    public List<MatriculaModel> listar(){ return matriculaRepository.findAll(); }

    public MatriculaModel cadastrar(MatriculaModel professorModel){
        return matriculaRepository.save(professorModel);
    }

    public Optional<MatriculaModel> buscarPorId(Long id){
        return matriculaRepository.findById(id);
    }

    public MatriculaModel atualizarCurso(Optional<MatriculaModel> matricula, Optional<CursoModel> cursoModel){
        MatriculaModel nova = matricula.get();
        nova.setCurso(cursoModel.get());
        return matriculaRepository.save(nova);
    }
}
