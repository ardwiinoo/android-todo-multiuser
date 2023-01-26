package com.example.room_database.Database.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.room_database.Database.Entity.TaskModel;
import com.example.room_database.Database.Entity.UserModel;

import java.util.List;

@Dao
public interface AllDao {

    @Insert
    void insertUser(UserModel userModel);

    @Delete
    void delete(UserModel userModel);

    @Insert
    void insertTask(TaskModel taskModel);

    @Delete
    void deleteTask(TaskModel taskModel);

    @Query("SELECT * FROM usermodel ")
    List<UserModel> getAllUsers();

    @Query("SELECT * FROM usermodel WHERE nim=:nim")
    UserModel checkNim(String nim);

    @Query("UPDATE usermodel SET name=:name, nim=:nim, password=:password, is_admin=:is_admin WHERE nim=:nim")
    void updateUser(String name, String nim, String password, Boolean is_admin);

    @Query("SELECT * FROM usermodel WHERE nim=:nim AND password=:password")
    UserModel login(String nim, String password);

    @Query("SELECT * FROM taskmodel WHERE userId=:id")
    List<TaskModel> getMyTask(int id);

    @Query("SELECT * FROM taskmodel")
    List<TaskModel> getAllTasks();

    @Query("SELECT * FROM taskmodel WHERE idTask=:id")
    TaskModel getMyTaskById(int id);

    @Query("UPDATE taskmodel SET taskName=:nameTask WHERE idTask=:id")
    void updateTask(String nameTask, int id);

}
