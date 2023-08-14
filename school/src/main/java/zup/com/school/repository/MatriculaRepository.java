package zup.com.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zup.com.school.model.MatriculaModel;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<MatriculaModel, Long> {
    List<MatriculaModel> findByAlunoId(Long alunoId);

    List<MatriculaModel> findByCursoId(Long cursoId);
}
