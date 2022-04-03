package org.privado.employee.team.controller.advice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ExceptionalAdviceTest {
    @Mock
    DummyService dummyService;
    MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController(dummyService))
                .setControllerAdvice(new ExceptionalAdvice()).build();
    }

    @Test
    void incorrectPathAsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test"))
        .andExpect(status().isNotFound());
    }

    @Test
    void missingQueryParamAsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test-get"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidMethodAsMethodNotAllowed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test-post"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void withoutDataAsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test-post"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void withoutContentTypeAsUnsupportedMediaType() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test-post").content("{\"name\":\"Vivek\", \"age\": 36}"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void withDataAsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/test-post").content("{\"age\": 36}").contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @RestController
    public class TestController {

        private final DummyService dummyService;
        public TestController(DummyService dummyService) {
            this.dummyService = dummyService;
        }

        @GetMapping("/test-get")
        public void testGet(@RequestParam("id") String id) {
            dummyService.dummyMethod();
        }
        @PostMapping("/test-post")
        public void testPost(@RequestBody @Valid @NotNull Pojo pojo) {
            dummyService.dummyMethod();
        }
    }

    private class DummyService {
        Object dummyMethod() {
            return new Object();
        }
    }

    private static class Pojo {
        @NotBlank
        private String name;
        private Integer age;

        public Pojo() {
        }

        public Pojo(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public Integer getAge() {
            return age;
        }

        public String getName() {
            return name;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
