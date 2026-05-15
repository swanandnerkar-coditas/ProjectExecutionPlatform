package com.coditas.ProjectExecutionPlatform.utility;

import ch.qos.logback.core.joran.action.AppenderRefAction;
import com.coditas.ProjectExecutionPlatform.dto.request.FilterRequestDTO;
import com.coditas.ProjectExecutionPlatform.enums.Priority;
import com.coditas.ProjectExecutionPlatform.enums.TaskStatus;
import com.coditas.ProjectExecutionPlatform.model.Project;
import com.coditas.ProjectExecutionPlatform.model.Task;
import com.coditas.ProjectExecutionPlatform.model.User;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class TaskSpecificationBuilder {

    public static Specification<Task> getSpecification(FilterRequestDTO filterRequestDTO, Project project, User teamMember){
        return Specification.where(
            hasPriority(filterRequestDTO.getPriority())
                    .and(hasDueDate(filterRequestDTO.getStartDate(), filterRequestDTO.getEndDate()))
                    .and(hasTaskStatus(filterRequestDTO.getTaskStatus()))
                    .and(likeSearchKeyword(filterRequestDTO.getKeyword()))
                    .and(hasProject(project))
                    .and(hasTeamMember(teamMember))
        );
    }

    public static Specification<Task> hasProject(Project project){

        return (root, query, criteriaBuilder) ->{
            Predicate predicate = criteriaBuilder.conjunction();
            if(project != null)
                predicate = criteriaBuilder.equal(root.get("project"), project);

            return predicate;
        };
    }

    public static Specification<Task> hasTeamMember(User teamMember){

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if(teamMember != null)
                    predicate = criteriaBuilder.equal(root.get("user"), teamMember);
            return predicate;
        };
    }

    public static Specification<Task> hasPriority(Priority priority){
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if(priority != null){
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("priority"), priority));
            }
            return predicate;
        };
    }

    public static Specification<Task> hasTaskStatus(TaskStatus taskStatus){

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if(taskStatus != null)
                criteriaBuilder.equal(root.get("taskStatus"), taskStatus);

            return predicate;
        };
    }

    public static Specification<Task> hasDueDate(LocalDate startDate, LocalDate endDate){
        return (root, query, criteriaBuilder) -> {
        Predicate predicate = criteriaBuilder.conjunction();
            if(startDate != null && endDate != null)
                criteriaBuilder.between(root.get("dueDate"), startDate, endDate);

            return predicate;
        };
    }

    public static Specification<Task> likeSearchKeyword(String keyword){

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if(keyword != null)
                criteriaBuilder.like(root.get("description"), "%" + keyword + "%");

            return predicate;
        };
    }

}
