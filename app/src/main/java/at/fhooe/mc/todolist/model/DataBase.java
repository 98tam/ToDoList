package at.fhooe.mc.todolist.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * The database for the to-do-list.
 */
@Database(entities = {Task.class}, version = 1)
public abstract class DataBase extends RoomDatabase {

    /**
     * Get the database object of the task.
     *
     * @return the database object of the task
     */
    public abstract TaskDao getTaskDao();
}
