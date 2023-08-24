package zup.com.school.testController;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import zup.com.school.controller.CursoController;
import zup.com.school.model.CursoModel;
import zup.com.school.service.CursoService;

import java.util.ArrayList;

@SpringJUnitConfig
@WebMvcTest(CursoController.class)
public class CursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CursoService cursoService;

    @Test
    public void testarListaDeCursos() throws Exception{
        Mockito.when(cursoService.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/zupSchool/cursos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0))
                .andDo(print());
    }

    @Test
    public void testarCadastroAluno() throws Exception{
        CursoModel cursoModel = new CursoModel();
        cursoModel.setId(1L);
        cursoModel.setNome("Ciência da Computação");
        cursoModel.setCargaHoraria(15000);

        Mockito.when(cursoService.cadastrar(Mockito.any(cursoModel.getClass()))).thenReturn(cursoModel);

        mockMvc.perform(post("/api/zupSchool/cursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cursoModel)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Ciência da Computação"))
                .andExpect(jsonPath("$.cargaHoraria").value(15000))
                .andDo(print());
    }

    @Test
    public void testarDeletarCurso() throws Exception{
        mockMvc.perform(delete("/api/zupSchool/cursos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Curso excluído com sucesso!"))
                .andDo(print());
    }
}
