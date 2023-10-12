package io.github.tomaszpro99.logic;

import io.github.tomaszpro99.model.TaskGroup;
import io.github.tomaszpro99.model.TaskGroupRepository;
import io.github.tomaszpro99.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {
    @Test
    @DisplayName("should throw when undone tasks")
    //metoda_stan/argumentyDoMetody_wynikSpodziewany
    void toggleGroup_undoneTasks_throwsIllegalStateException() {
        //given
//        var mockTaskRepository = mock(TaskRepository.class);
//        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(true);
        //Extract Method.. taskRepositoryReturning
        TaskRepository mockTaskRepository = taskRepositoryReturning(true);
        //system under test
        var toTest = new TaskGroupService(null, mockTaskRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("undone tasks");
    }
    @Test
    @DisplayName("should throw when no group")
    void toggleGroup_wrongId_throwsIllegalArgumentException() {
        //given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        //and
        var mockRepository =  mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //system under test
        var toTest = new TaskGroupService(mockRepository, mockTaskRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }
    @Test
    @DisplayName("should toggle group")
    void toggleGroup_worksAsExpected() {
        //given
        TaskRepository mockTaskRepository = taskRepositoryReturning(false);
        //and
        var group = new TaskGroup();
        var beforeToggle = group.isDone();
        //and
        var mockRepository =  mock(TaskGroupRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(group));
        //system under test
        var toTest = new TaskGroupService(mockRepository, mockTaskRepository);
        //when
        toTest.toggleGroup(0);
        //then
        assertThat(group.isDone()).isEqualTo(!beforeToggle);
    }
    private static TaskRepository taskRepositoryReturning(final boolean result) {
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(result);
        return mockTaskRepository;
    }
}