package com.example.birdinbackend.task;

import com.example.birdinbackend.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper( TaskMapper.class );
    TaskDto taskDto(Task task);
}
