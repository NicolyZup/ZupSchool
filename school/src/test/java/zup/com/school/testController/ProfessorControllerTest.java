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
import zup.com.school.controller.ProfessorController;
import zup.com.school.model.CursoModel;
import zup.com.school.model.ProfessorModel;
import zup.com.school.service.CursoService;
import zup.com.school.service.ProfessorService;


import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringJUnitConfig
@WebMvcTest(ProfessorController.class)
public class ProfessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ProfessorService professorService;

    @MockBean
    private CursoService cursoService;

    @Test
    public void testarListaDeProfessores() throws Exception{
        Mockito.when(professorService.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/zupSchool/professores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0))
                .andDo(print());
    }

    @Test
    public void testarCadastroProfessor() throws Exception{
        CursoModel cursoModel = new CursoModel();
        cursoModel.setId(1L);
        cursoModel.setNome("Ciência da Computação");
        cursoModel.setCargaHoraria(15000);

        ProfessorModel professorModel = new ProfessorModel();
        professorModel.setId(1L);
        professorModel.setNome("Debora Alaquim");
        professorModel.setIdade(32);
        professorModel.setSalario(3400);
        professorModel.setCurso(cursoModel);

        Mockito.when(cursoService.buscarPorId(cursoModel.getId())).thenReturn(Optional.of(cursoModel));
        Mockito.when(professorService.cadastrar(Mockito.any(professorModel.getClass()))).thenReturn(professorModel);

        mockMvc.perform(post("/api/zupSchool/professores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(professorModel)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Debora Alaquim"))
                .andExpect(jsonPath("$.idade").value(32))
                .andExpect(jsonPath("$.salario").value(3400))
                .andExpect(jsonPath("$.curso.id").value(1L))
                .andExpect(jsonPath("$.curso.nome").value("Ciência da Computação"))
                .andExpect(jsonPath("$.curso.cargaHoraria").value(15000))
                .andDo(print());
    }

    @Test
    public void testarDeletarProfessor() throws Exception{
        mockMvc.perform(delete("/api/zupSchool/professores/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Professor(a) excluído(a) com sucesso!"))
                .andDo(print());
    }
}
