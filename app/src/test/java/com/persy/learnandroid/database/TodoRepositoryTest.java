package com.persy.learnandroid.database;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.persy.learnandroid.model.Todo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit Test cho {@link TodoRepository}.
 * Nhờ Dagger sử dụng Constructor Injection (@Inject TodoRepository(TodoDAO dao)),
 * chúng ta có thể dễ dàng "bơm" một Mock TodoDAO vào để test toàn bộ logic Repository
 * mà KHÔNG CẦN khởi tạo Room SQLite Database thật.
 */
public class TodoRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private TodoDAO mockTodoDAO;

    private TodoRepository todoRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        todoRepository = new TodoRepository(mockTodoDAO);
    }

    @Test
    public void testGetAllTodoLive_returnsDataFromDAO() {
        List<Todo> fakeTodoList = new ArrayList<>();
        Todo todo1 = new Todo("Học Dagger 2", new Date());
        todo1.setId(1);
        Todo todo2 = new Todo("Viết Unit Test", new Date());
        todo2.setId(2);
        fakeTodoList.add(todo1);
        fakeTodoList.add(todo2);

        MutableLiveData<List<Todo>> liveData = new MutableLiveData<>(fakeTodoList);
        when(mockTodoDAO.getAllTodoLive()).thenReturn(liveData);

        List<Todo> result = todoRepository.getAllTodoLive().getValue();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Học Dagger 2", result.get(0).getTitle());
        verify(mockTodoDAO).getAllTodoLive();
    }

    @Test
    public void testInsertTodo_callsDAOInsert() {
        Todo newTodo = new Todo("Task test", new Date());

        todoRepository.insert(newTodo);

        verify(mockTodoDAO, timeout(1000)).insert(newTodo);
    }

    @Test
    public void testDeleteTodo_callsDAODelete() {
        Todo todoToDelete = new Todo("Task delete", new Date());
        todoToDelete.setId(10);

        todoRepository.delete(todoToDelete);

        verify(mockTodoDAO, timeout(1000)).delete(todoToDelete);
    }

    @Test
    public void testUpdateTodo_callsDAOUpdate() {
        Todo todoToUpdate = new Todo("Task updated", new Date());
        todoToUpdate.setId(5);

        todoRepository.update(todoToUpdate);

        verify(mockTodoDAO, timeout(1000)).update(todoToUpdate);
    }
}
