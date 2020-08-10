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
     *
     * @param _savedInstanceState
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
        final String sTask = mTaskText.getText().toString().trim();
        final String sDesc = mDescText.getText().toString().trim();

        if (sTask.isEmpty()) {
            mTaskText.setError("Task missing!");
            mTaskText.requestFocus();
            return;
        }

        if (sDesc.isEmpty()) {
            mDescText.setError("Description missing!");
            mDescText.requestFocus();
            return;
        }

        /**
         *
         */
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                Task task = new Task();
                task.setTask(sTask);
                task.setDesc(sDesc);
                task.setFinished(false);

                DatabaseClient.getInstance(getApplicationContext()).getDatabase()
                        .taskDao()
                        .insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }


}

