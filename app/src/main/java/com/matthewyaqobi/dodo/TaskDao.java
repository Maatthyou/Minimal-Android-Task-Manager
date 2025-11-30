package com.matthewyaqobi.dodo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    long addTask(Task task);

    @Update
    int updateTask(Task task);

    @Delete
    int deleteTask(Task task);

    @Query("SELECT * From table_task")
    List<Task> getTasks();
}
