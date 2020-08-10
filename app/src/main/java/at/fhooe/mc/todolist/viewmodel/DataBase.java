package at.fhooe.mc.todolist.viewmodel;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import at.fhooe.mc.todolist.model.Task;
import at.fhooe.mc.todolist.model.TaskDao;

/**
 * The database for the to-do-list.
 */
@Database(entities = {Task.class}, version = 1)
public abstract class DataBase extends RoomDatabase {

    /**
     *
     *
     * @return the task
     */
    public abstract TaskDao taskDao();
}
