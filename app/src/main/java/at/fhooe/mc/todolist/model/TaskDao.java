package at.fhooe.mc.todolist.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import at.fhooe.mc.todolist.model.Task;

/**
 * This interface is the data access object of the task.
 */
@Dao
public interface TaskDao {

    /**
     * Query for all objects in the database.
     *
     * @return all objects in the database
     */
    @Query("SELECT * FROM task")
    List<Task> getAll();

    /**
     * Insert of the task into the database.
     *
     * @param task the given task
     */
    @Insert
    void insert(Task task);

    /**
     * Deletes the task from the database.
     *
     * @param task the given task
     */
    @Delete
    void delete(Task task);

    /**
     * Deletes all tasks from database.
     */
    @Query("DELETE FROM task")
    void deleteAll();


    /**
     * Updates changes of the task.
     *
     * @param task the given task
     */
    @Update
    void update(Task task);

}