package at.fhooe.mc.todolist.model;

import android.os.AsyncTask;

public class DatabaseAsync extends AsyncTask<Void, Void, Void> {

    /**
     * a TaskDao object
     */
    private final TaskDao mDao;

    /**
     * Constructor of the class, set value of the mDao object
     * @param _db
     */
    DatabaseAsync(DataBase _db) {
        mDao = _db.getTaskDao();
    }

    @Override
    protected Void doInBackground(final Void... _params) {
        mDao.deleteAll();
        return null;
    }
}
