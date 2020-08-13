package at.fhooe.mc.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import at.fhooe.mc.todolist.model.Task;
import at.fhooe.mc.todolist.view.TaskAdapter;
import at.fhooe.mc.todolist.viewmodel.DatabaseClient;

/**
 * The main activity provides the overview of all to-do-tasks.
 */
public class MainActivity extends AppCompatActivity {
    //the recyclerview
    private RecyclerView mRecyclerView;

    /**
     * Initializes all given ui elements and provides the tasks
     * from the database.
     *
     * @param _savedInstanceState is the saved state
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.activity_main_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton buttonAddTask = findViewById(R.id.activity_main_button_add);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Supplies the needed database elements.
         */
        class GetTasks extends AsyncTask<Void, Void, List<Task>> {

            /**
             * Saves the tasks in the background.
             * @param _void is the AsyncTask
             * @return the list of tasks.
             */
            @Override
            protected List<Task> doInBackground(Void... _void) {
                return DatabaseClient.getInstance(getApplicationContext())
                        .getDatabase()
                        .getTaskDao()
                        .getAll();
            }

            /**
             * Provides the new adapter with the updated list.
             * @param _list is the list of tasks
             */
            @Override
            protected void onPostExecute(final List<Task> _list) {
                super.onPostExecute(_list);
                mRecyclerView.setAdapter(new TaskAdapter(MainActivity.this, _list));

                ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN |
                        ItemTouchHelper.START |
                        ItemTouchHelper.END,
                        0) {

                    /**
                     *
                     * @param _recyclerView
                     * @param _viewHolder
                     * @param _target
                     * @return
                     */
                    @Override
                    public boolean onMove(@NonNull RecyclerView _recyclerView, @NonNull RecyclerView.ViewHolder _viewHolder, @NonNull RecyclerView.ViewHolder _target) {
                        int fromPos = _viewHolder.getAdapterPosition();
                        int toPos = _target.getAdapterPosition();

                        Collections.swap(_list, fromPos, toPos);
                        Objects.requireNonNull(_recyclerView.getAdapter()).notifyItemMoved(fromPos, toPos);

                        return true;
                    }

                    /**
                     *
                     * @param _viewHolder
                     * @param _direction
                     */
                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder _viewHolder, int _direction) {

                    }
                };

                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
                itemTouchHelper.attachToRecyclerView(mRecyclerView);
            }
        }

        GetTasks getTasks = new GetTasks();
        getTasks.execute();
    }
}