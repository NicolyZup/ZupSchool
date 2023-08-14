package zup.com.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zup.com.school.model.ProfessorModel;
import zup.com.school.repository.MatriculaRepository;
import zup.com.school.repository.ProfessorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository professorRepository;

    public List<ProfessorModel> listar(){
        return professorRepository.findAll();
    }

    public ProfessorModel cadastrar(ProfessorModel professorModel){
        return professorRepository.save(professorModel);
    }

    public Optional<ProfessorModel> buscarPorId(Long id){
        return  professorRepository.findById(id);
    }

    public void excluir(Long id){
        professorRepository.deleteById(id);
    }
}
