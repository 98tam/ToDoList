package at.fhooe.mc.todolist.viewmodel;

import android.content.Context;

import androidx.room.Room;

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

    /**
     * Constructor
     * @param _context is the context of the client
     */
    private DatabaseClient(Context _context) {
        this.mContext = _context;
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
}
