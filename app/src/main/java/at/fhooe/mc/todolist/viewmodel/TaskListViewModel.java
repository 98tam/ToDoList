package at.fhooe.mc.todolist.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import at.fhooe.mc.todolist.model.DatabaseClient;
import at.fhooe.mc.todolist.model.Task;
import at.fhooe.mc.todolist.model.WebService;

public class TaskListViewModel extends AndroidViewModel {

    // list of the tasks
    private List<Task> mAllTasks;

    /**
     * Constructor
     * @param application
     */
    public TaskListViewModel(Application application){
        super(application);
        DatabaseClient dataBase = new DatabaseClient(application);
        WebService webService = new WebService(application);
        LiveData<List<Task>> retroObservable = webService.providesWebService();
        mAllTasks = dataBase.getAllTasks();
    }

    /**
     * Method to get all Tasks
     * @return a list with all tasks
     */
    public List<Task> getAllPosts() {
        return mAllTasks;
    }
}
