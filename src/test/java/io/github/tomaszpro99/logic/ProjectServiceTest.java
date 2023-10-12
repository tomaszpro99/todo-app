package io.github.tomaszpro99.logic;

import io.github.tomaszpro99.TaskConfigurationProperties;
import io.github.tomaszpro99.model.*;
import io.github.tomaszpro99.model.projection.GroupReadModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
class ProjectServiceTest {
    @Test //JUnit
    @DisplayName("should throw IllegalStateException when configured to allow just 1 group and the other undone group exists")
    void createGroup_noMultipleGroupsConfig_And_undoneGroupExists_throwsIllegalStateException() {
        //Testy jednostkowe:
        // given - przygotowanie danych
        // when - wywolamy testowana metode
        // them - sprawdzamy skutek

        // given
        // Mockito - do nadpisywania zamiast overridowac
        //var mockGroupReposiotry = mock(TaskGroupRepository.class); // mock jak "wydmuszka" when(mockGroupReposiotry.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(true);
        //when(mockGroupReposiotry.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(true);
        // Extract Method... groupRepositoryReturning:
        TaskGroupRepository mockGroupReposiotry = groupRepositoryReturning(true);
        // and
        TaskConfigurationProperties mockConfig = configurationReturning(false);
        // system under test
        var toTest = new ProjectService(null, mockGroupReposiotry, mockConfig);
        // when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
        // then
        // AssertJ
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("one undone group");
    }
    @Test //JUnit
    @DisplayName("should throw IllegalArgumentException when configuration ok and no projects for a given id")
    void createGroup_configurationOk_And_noProjects_throwsIllegalStateException() {
        // given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        // and
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        // system under test
        var toTest = new ProjectService(mockRepository, null, mockConfig);
        // when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
        // then
        // AssertJ
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }
    @Test //JUnit
    @DisplayName("should throw IllegalArgumentException when configured to allow just 1 group and no groups and projects for a given id")
    void createGroup_noMultipleGroupsConfig_And_noUndoneGroupExists_noProjects_throwsIllegalStateException() {
        // given
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        // and (given z 1)
        TaskGroupRepository mockGroupRepository = groupRepositoryReturning(false);
        // and
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        // system under test
        var toTest = new ProjectService(mockRepository, null, mockConfig);
        // when
        var exception = catchThrowable(() -> toTest.createGroup(LocalDateTime.now(), 0));
        // then
        // AssertJ
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }
    @Test
    @DisplayName("should create a new group from project")
    void createGroup_configurationOk_existingProject_cratesAndSavesGroup() {
        //given
        var today = LocalDate.now().atStartOfDay(); //dzisiejsza data z czasem 00:00:00
        //and
        var project = projectWith("bar", Set.of(-1,-2));
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt()))
                .thenReturn(Optional.of(project));
        //and inMemoryGroupRepository
        InMemoryGroupRepository inMemoryGroupRepo = inMemoryGroupRepository();
        int countBeforeCall = inMemoryGroupRepo.count();
        //and
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        // system under test
        var toTest = new ProjectService(mockRepository, inMemoryGroupRepo, mockConfig);
        //when
        GroupReadModel result = toTest.createGroup(today, 1);
        //then
        assertThat(result.getDescription()).isEqualTo("bar");
        assertThat(result.getDeadline()).isEqualTo(today.minusDays(1));
        assertThat(result.getTasks()).allMatch(task -> task.getDescription().equals("asd"));
        assertThat(countBeforeCall +1).isEqualTo(inMemoryGroupRepo.count());
    }
    private Project projectWith(String projectDescription, Set<Integer> daysToDeadline) {
        Set<ProjectStep> steps = daysToDeadline.stream()
                .map(days -> {
                    var step = mock(ProjectStep.class);
                    when(step.getDescription()).thenReturn("asd");
                    when(step.getDaysToDeadline()).thenReturn(days);
                    return step;
                }).collect(Collectors.toSet());
        var result = mock(Project.class);
        when(result.getDescription()).thenReturn(projectDescription);
        when(result.getSteps()).thenReturn(steps);
        return result;
    }
    private TaskGroupRepository groupRepositoryReturning(final boolean result) {
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(result);
        return mockGroupRepository;
    }
    private TaskConfigurationProperties configurationReturning(final boolean result) { //Extract Method... Ctrl Alt M
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(result);
        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }
    private InMemoryGroupRepository inMemoryGroupRepository() {return new InMemoryGroupRepository();}
    private static class InMemoryGroupRepository implements TaskGroupRepository{
        private int index = 0;
        private Map<Integer, TaskGroup> map = new HashMap<>();
        public int count() { //zwroci rozmiar tablicy
            return map.values().size();
        }
        @Override
        public List<TaskGroup> findAll() {
            return new ArrayList<>(map.values());
        }
        @Override
        public Optional<TaskGroup> findById(Integer id) {
            return Optional.ofNullable(map.get(id));
        }
        @Override
        public TaskGroup save(TaskGroup entity) {
            if (entity.getId() == 0) {
                try {
                    var field = TaskGroup.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity, ++index);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            map.put(entity.getId(), entity);
            return entity;
        }
        @Override
        public boolean existsByDoneIsFalseAndProject_Id(Integer projectId) {
            return map.values().stream()
                    .filter(group -> !group.isDone())
                    .anyMatch(group -> group.getProject() != null && group.getProject().getId() == projectId);
        }
    }
}