package zup.com.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zup.com.school.model.AlunoModel;
import zup.com.school.model.MatriculaModel;
import zup.com.school.repository.AlunoRepository;
import zup.com.school.repository.MatriculaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    MatriculaRepository matriculaRepository;

    public List<AlunoModel> listar(){
        return alunoRepository.findAll();
    }

    public AlunoModel cadastrar(AlunoModel alunoModel){
        return alunoRepository.save(alunoModel);
    }

    public Optional<AlunoModel> buscarPorId(Long id){
        return  alunoRepository.findById(id);
    }

    public void excluir(Long id){
        List<MatriculaModel> matriculaModels = matriculaRepository.findByAlunoId(id);

        if(matriculaModels != null){
           matriculaRepository.deleteAll(matriculaModels);
        }

        alunoRepository.deleteById(id);
    }
}
