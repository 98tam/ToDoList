package at.fhooe.mc.todolist.model;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import java.util.List;

/**
 * This class defines the client of the database.
 */
public class DatabaseClient {

    //context of the client
    private Context mContext;
    //the database client
    private static DatabaseClient mClient;
    //database object
    private DataBase mDatabase;
    // taskDao object
    private TaskDao mTask;
    // list of task objects
    List<Task> mAllTasks;

    /**
     * Constructor
     * @param _context is the context of the client
     */
    private DatabaseClient(Context _context) {
        mContext = _context;
        mDatabase = Room.databaseBuilder(_context, DataBase.class, "MyToDos").build();
    }

    /**
     * Constructor
     * @param _context is the context of the client
     * @return the client instance
     */
    public static synchronized DatabaseClient getInstance(Context _context) {
        if (mClient == null) {
            mClient = new DatabaseClient(_context);
        }
        return mClient;
    }

    /**
     * Getter for the data base.
     * @return the database
     */
    public DataBase getDatabase() {
        return mDatabase;
    }


    public DatabaseClient(Application _application){
        DataBase db = DataBase.getDatabase(_application);
        mTask = db.getTaskDao();
        mAllTasks = mTask.getAll();
    }

    /**
     * Returns a list with all tasks
     * @return list with the tasks
     */
    public List<Task> getAllTasks() {
        return mAllTasks;
    }

    /**
     * Insert a task or a list of tasks
     * @param task the given task, which should be inserted
     */
    public void insertTask(List<Task> task) {
        new insertAsyncTask(mTask).execute(task);
    }

    private static class insertAsyncTask extends AsyncTask<List<Task>, Void, Void> {
        // async TaskDao object
        private TaskDao mAsyncTaskDao;

        insertAsyncTask(TaskDao _dao) {
            mAsyncTaskDao = _dao;
        }

        @Override
        protected Void doInBackground(final List<Task>... _params) {
            mAsyncTaskDao.insert((Task) _params[0]);
            return null;
        }
    }
}
