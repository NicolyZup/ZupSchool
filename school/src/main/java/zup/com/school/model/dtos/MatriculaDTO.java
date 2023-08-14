package zup.com.school.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaDTO {
    private Long id;
    private LocalDate dataDaMatricula;
    private AlunoDTO aluno;
    private CursoDTO curso;
}
