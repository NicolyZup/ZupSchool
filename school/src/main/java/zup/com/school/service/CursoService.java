package zup.com.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zup.com.school.model.AlunoModel;
import zup.com.school.model.CursoModel;
import zup.com.school.model.MatriculaModel;
import zup.com.school.model.ProfessorModel;
import zup.com.school.repository.CursoRepository;
import zup.com.school.repository.MatriculaRepository;
import zup.com.school.repository.ProfessorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    MatriculaRepository matriculaRepository;

    @Autowired
    ProfessorRepository professorRepository;

    public List<CursoModel> listar(){
        return cursoRepository.findAll();
    }

    public CursoModel cadastrar(CursoModel cursoModel){
        return cursoRepository.save(cursoModel);
    }

    public Optional<CursoModel> buscarPorId(Long id){
        return  cursoRepository.findById(id);
    }

    public void excluir(Long id){
        List<MatriculaModel> matriculaModels = matriculaRepository.findByCursoId(id);

        if(matriculaModels != null){
            matriculaRepository.deleteAll(matriculaModels);
        }

        List<ProfessorModel> professorModels = professorRepository.findByCursoId(id);

        if(professorModels != null){
            professorRepository.deleteAll(professorModels);
        }

        cursoRepository.deleteById(id);
    }
}
