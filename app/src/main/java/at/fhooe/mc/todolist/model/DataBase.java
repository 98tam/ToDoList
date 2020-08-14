package at.fhooe.mc.todolist.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * The database for the to-do-list.
 */
@Database(entities = {Task.class}, version = 1)
public abstract class DataBase extends RoomDatabase {

    /**
     * abstract method to get a TaskDao object
     * @return a TaskDao object
     */
    public abstract TaskDao getTaskDao();

    /**
     * Instance of the database
     */
    private static DataBase INSTANCE;

    /**
     * Get the database object of the task.
     *
     * @return the database object of the task / the instance of the database
     */
    static DataBase getDatabase(final Context _context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(_context.getApplicationContext(),
                            DataBase.class, "task_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Callback method for getting database
     */
    private static Callback sRoomDatabaseCallback =
            new Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new DatabaseAsync(INSTANCE).execute();
                }
            };
}
