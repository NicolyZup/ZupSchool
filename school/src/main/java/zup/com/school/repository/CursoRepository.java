package zup.com.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zup.com.school.model.CursoModel;

public interface CursoRepository extends JpaRepository<CursoModel, Long> {
}
