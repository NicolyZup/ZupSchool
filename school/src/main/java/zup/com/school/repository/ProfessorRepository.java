package zup.com.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zup.com.school.model.ProfessorModel;

import java.util.List;

public interface ProfessorRepository extends JpaRepository<ProfessorModel, Long> {
    List<ProfessorModel> findByCursoId(Long id);
}
