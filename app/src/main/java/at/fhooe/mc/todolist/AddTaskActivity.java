package at.fhooe.mc.todolist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import at.fhooe.mc.todolist.model.Task;
import at.fhooe.mc.todolist.viewmodel.DatabaseClient;

/**
 * This activity adds the tasks to the overview
 * and saves them in the database.
 */
public class AddTaskActivity extends AppCompatActivity {

    // the text components of the task
    private EditText mTaskText, mDescText;

    /**
     * Initializes all given ui elements.
     *
     * @param _savedInstanceState is the saved state
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mTaskText = findViewById(R.id.editTextTask);
        mDescText = findViewById(R.id.editTextDesc);
    }

    /**
     * Saves the task to the database.
     */
    public void saveTask(View _view) {
        final String inputTask = mTaskText.getText().toString().trim();
        if (inputTask.isEmpty()) {
            mTaskText.setError("Task missing!");
            mTaskText.requestFocus();
            return;
        }

        final String inputDesc = mDescText.getText().toString().trim();
        if (inputDesc.isEmpty()) {
            mDescText.setError("Description missing!");
            mDescText.requestFocus();
            return;
        }

        /**
         * This class makes the whole saving of the input data.
         */
        class SaveTask extends AsyncTask<Void, Void, Void> {

            /**
             * Saves the tasks in the background.
             * @param _void is the AsyncTask
             * @return null
             */
            @Override
            protected Void doInBackground(Void... _void) {
                Task task = new Task();
                task.setTask(inputTask);
                task.setDesc(inputDesc);
                task.setFinished(false);

                DatabaseClient.getInstance(getApplicationContext())
                        .getDatabase()
                        .getTaskDao()
                        .insert(task);
                return null;
            }

            /**
             * Go back to the main activitiy after finishing the saving.
             * @param _void
             */
            @Override
            protected void onPostExecute(Void _void) {
                super.onPostExecute(_void);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask saveTask = new SaveTask();
        saveTask.execute();
    }


}

