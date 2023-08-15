package com.example.SpringTodo;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class TodoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TodoMapper todoMapper;
    
    @InjectMocks
    private TodoController todoController;

    @InjectMocks
    private TodoService todoService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();

        // todoController에 todoService를 주입
        ReflectionTestUtils.setField(todoController, "todoService", todoService);
        
        Todo todo1 = new Todo("Task1", "2023-08-14", false);
        Todo todo2 = new Todo("Task2", "2023-08-15", true);
        List<Todo> todos = Arrays.asList(todo1, todo2);

        // todoMapper.findAll() 호출을 흉내냄. 리턴 값을 todos로 함
        when(todoMapper.findAll()).thenReturn(todos);
    }

    @Rule
    public TestWatcher watchman = new TestResultLogger();
    
    @Test
    public void testDownloadTodos() throws Exception {
        mockMvc.perform(get("/todo/download"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$[0].todo").value("Task1"))
               .andExpect(jsonPath("$[1].todo").value("Task2"));
    }
}
