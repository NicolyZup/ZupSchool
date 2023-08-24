package zup.com.school.testController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import zup.com.school.controller.MatriculaController;
import zup.com.school.model.AlunoModel;
import zup.com.school.model.CursoModel;
import zup.com.school.model.MatriculaModel;
import zup.com.school.service.AlunoService;
import zup.com.school.service.CursoService;
import zup.com.school.service.MatriculaService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringJUnitConfig
@WebMvcTest(MatriculaController.class)
public class MatriculaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private AlunoService alunoService;

    @MockBean
    private CursoService cursoService;

    @MockBean
    private MatriculaService matriculaService;

    @Test
    public void testarListaDeMatriculas() throws Exception{
        Mockito.when(matriculaService.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/zupSchool/matriculas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0))
                .andDo(print());
    }

    @Test
    public void testarMatricularAluno() throws Exception{
        CursoModel cursoModel = new CursoModel();
        cursoModel.setId(1L);
        cursoModel.setNome("Ciência da Computação");
        cursoModel.setCargaHoraria(15000);

        AlunoModel alunoModel = new AlunoModel();
        alunoModel.setId(1L);
        alunoModel.setNome("Nicoly");
        alunoModel.setIdade(23);
        alunoModel.setEmail("nicoly@zup.com");

        MatriculaModel matriculaModel = new MatriculaModel();
        matriculaModel.setId(1L);
        matriculaModel.setDataDaMatricula(LocalDate.parse("2023-08-23"));
        matriculaModel.setAluno(alunoModel);
        matriculaModel.setCurso(cursoModel);

        Mockito.when(matriculaService.cadastrar(Mockito.any(matriculaModel.getClass()))).thenReturn(matriculaModel);
        //Optional.of(obj) - quando o objeto pode ser nulo, até pq não tem como fazer a busca pelo curso/aluno pq não
        //estamos conectados diretamente no banco de dados
        Mockito.when(cursoService.buscarPorId(cursoModel.getId())).thenReturn(Optional.of(cursoModel));
        Mockito.when(alunoService.buscarPorId(alunoModel.getId())).thenReturn(Optional.of(alunoModel));

        mockMvc.perform(post("/api/zupSchool/matriculas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matriculaModel)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.dataDaMatricula").value("2023-08-23"))
                .andExpect(jsonPath("$.aluno.id").value(1L))
                .andExpect(jsonPath("$.aluno.nome").value("Nicoly"))
                .andExpect(jsonPath("$.aluno.idade").value(23))
                .andExpect(jsonPath("$.aluno.email").value("nicoly@zup.com"))
                .andExpect(jsonPath("$.curso.id").value(1L))
                .andExpect(jsonPath("$.curso.nome").value("Ciência da Computação"))
                .andExpect(jsonPath("$.curso.cargaHoraria").value(15000))
                .andDo(print());
    }


    @Test
    public void testarAlteracaoCursoMatricula() throws Exception{
        CursoModel cursoModel = new CursoModel();
        cursoModel.setId(1L);
        cursoModel.setNome("Ciência da Computação");
        cursoModel.setCargaHoraria(15000);

        AlunoModel alunoModel = new AlunoModel();
        alunoModel.setId(1L);
        alunoModel.setNome("Nicoly");
        alunoModel.setIdade(23);
        alunoModel.setEmail("nicoly@zup.com");

        MatriculaModel matriculaModel = new MatriculaModel();
        matriculaModel.setId(1L);
        matriculaModel.setDataDaMatricula(LocalDate.parse("2023-08-23"));
        matriculaModel.setAluno(alunoModel);
        matriculaModel.setCurso(cursoModel);

        Mockito.when(matriculaService.buscarPorId(matriculaModel.getId())).thenReturn(Optional.of(matriculaModel));
        Mockito.when(cursoService.buscarPorId(cursoModel.getId())).thenReturn(Optional.of(cursoModel));
        Mockito.when(matriculaService.atualizarCurso(Optional.of(matriculaModel), Optional.of(cursoModel))).thenReturn(matriculaModel);

        mockMvc.perform(put("/api/zupSchool/matriculas/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(matriculaModel)))
                .andExpect(status().isOk())
                .andExpect(content().string("Curso atualizado com sucesso!"))
                .andDo(print());
    }
}
