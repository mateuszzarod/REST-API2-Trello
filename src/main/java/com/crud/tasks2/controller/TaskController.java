package com.crud.tasks2.controller;
//Controller jest częścią aplikacji webowej,
// która odpowiada za otrzymywanie żądań i zwracanie odpowiedzi.
// Wewnątrz Controllera znajduje się również logika biznesowa,
// która w większości jest delegowana do innych klas.
// Controller po prostu przekazuje im parametry.

import com.crud.tasks2.domain.TaskDto;
import com.crud.tasks2.mapper.TaskMapper;
import com.crud.tasks2.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@CrossOrigin(origins = "*") //Spring adnotation permission for Cross Origin Resource Sharing
@RestController
@RequestMapping("v1/task") //generates adress for API
public class TaskController {

    @Autowired
    private DbService service;
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "getTasks"
    )
    public List<TaskDto> getTasks() {
        return taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "getTask"
    )
    public TaskDto getTask(@RequestParam Long taskId) throws TaskNotFoundException {
        return taskMapper.mapToTaskDto(service.getTask(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "deleteTask"
    )
    public void deleteTask(@RequestParam Long taskId) throws TaskNotFoundException {
        service.deleteTask(taskId);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "updateTask"
    )
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "createTask",
            consumes = APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto) {
        service.saveTask(taskMapper.mapToTask(taskDto));
    }
}
