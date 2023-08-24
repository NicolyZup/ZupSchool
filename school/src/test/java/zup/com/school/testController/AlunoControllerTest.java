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
import zup.com.school.controller.AlunoController;
import zup.com.school.model.AlunoModel;
import zup.com.school.service.AlunoService;

import java.util.ArrayList;

//configuração do contexto do Spring
@SpringJUnitConfig
//configura qual controlador você que testar
@WebMvcTest(AlunoController.class)
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //biblioteca que converte objeto java em json
    @Autowired
    ObjectMapper objectMapper;

    //mock da classe service, já que a service é um componente da controller - simula o comportamento desse serviço
    @MockBean
    private AlunoService alunoService;

    @Test
    public void testarListaDeAlunos() throws Exception{
        Mockito.when(alunoService.listar()).thenReturn(new ArrayList<>());

       mockMvc.perform(get("/api/zupSchool/alunos"))
               .andExpect(status().isOk())
               //anotação $ é usada para acessar propriedade do json (retorno do endpoint)
               //jsonPath - verifica o objeto retornado do endpoint
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$.length()").value(0))
               .andDo(print());
    }

    @Test
    public void testarCadastroAluno() throws Exception{
        AlunoModel alunoModel = new AlunoModel();
        alunoModel.setId(1L);
        alunoModel.setNome("Nicoly");
        alunoModel.setIdade(23);
        alunoModel.setEmail("nicoly@zup.com");

        //preciso primeiro aplicar na service, para que ela me retorne o objeto alunoModel e eu consiga acessar no DTO
        //mockito.any serve para flexibilizar o comportamento do mock (mesmo eu passando o objeto idêntico ao que se
        //espera na service estava dando erro, usando essa flexibilização passou (?pq Deus?)
        Mockito.when(alunoService.cadastrar(Mockito.any(alunoModel.getClass()))).thenReturn(alunoModel);

        mockMvc.perform(post("/api/zupSchool/alunos")
                //tipo do body a ser enviado
                .contentType(MediaType.APPLICATION_JSON)
                //.content - acessar o conteúdo da resposta da requisição / testar o body da requisição
                //objectMapper - biblioteca para converter objetos JAVA em json
                .content(objectMapper.writeValueAsString(alunoModel)))
                .andExpect(status().isCreated())
                //jsonPath - verifica o objeto retornado do endpoint
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Nicoly"))
                .andExpect(jsonPath("$.idade").value(23))
                .andExpect(jsonPath("$.email").value("nicoly@zup.com"))
                .andDo(print());
    }

    @Test
    public void testarDeletarAluno() throws Exception{
        mockMvc.perform(delete("/api/zupSchool/alunos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.content - acessar o conteúdo da resposta da requisição
                .andExpect(content().string("Aluno(a) excluído(a) com sucesso!"))
                //mostra mais detalhes do teste, principalmente erros
                .andDo(print());
    }
}
