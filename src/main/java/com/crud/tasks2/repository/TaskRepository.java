package com.crud.tasks2.repository;


import com.crud.tasks2.domain.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long>{
    @Override
    List<Task> findAll();

    @Override
    Task save (Task task);

    Optional<Task> findById(Long id);
}
