package at.fhooe.mc.todolist.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import at.fhooe.mc.todolist.R;
import at.fhooe.mc.todolist.model.Task;
import at.fhooe.mc.todolist.model.DatabaseClient;

/**
 * This activity updates the task changes.
 */
public class UpdateTaskActivity extends AppCompatActivity {

    //the given edit-text-fields
    private EditText mTaskText, mDescText;
    //the finish checkbox of a task
    private CheckBox mCheckbox;
    //the current task
    private Task mTask;

    /**
     * Initializes all given ui elements and loads the current task.
     *
     * @param _savedInstanceState is the saved state
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_update_task);

        mTaskText = findViewById(R.id.activity_add_text_task);
        mDescText = findViewById(R.id.activity_add_text_desc);
        mCheckbox = findViewById(R.id.checkBoxFinished);

        mTask = (Task) getIntent().getSerializableExtra("task");
        loadTask();
    }

    /**
     * Loads the current task.
     */
    private void loadTask() {
        mTaskText.setText(mTask.getTask());
        mDescText.setText(mTask.getDesc());
        mCheckbox.setChecked(mTask.isFinished());
    }

    /**
     * Updates changes on the task.
     *
     * @param _view the given view
     */
    public void updateTask(View _view) {
        final String inputTask = mTaskText.getText().toString().trim();

        if (inputTask.isEmpty()) {
            mTaskText.setError("Titel fehlt!");
            mTaskText.requestFocus();
            return;
        }

        final String inputDescription = mDescText.getText().toString().trim();
        if (inputDescription.isEmpty()) {
            mDescText.setError("Beschreibung fehlt!");
            mDescText.requestFocus();
            return;
        }

        /**
         * Updates the data in the database.
         */
        class UpdateTask extends AsyncTask<Void, Void, Void> {

            /**
             * Updates the data in the background.
             * @param _void is the AsyncTask
             * @return null
             */
            @Override
            protected Void doInBackground(Void... _void) {
                mTask.setTask(inputTask);
                mTask.setDesc(inputDescription);
                mTask.setFinished(mCheckbox.isChecked());
                DatabaseClient.getInstance(getApplicationContext()).getDatabase()
                        .getTaskDao()
                        .update(mTask);
                return null;
            }

            /**
             * Go back to the main activitiy after finishing the update.
             * @param _void
             */
            @Override
            protected void onPostExecute(Void _void) {
                super.onPostExecute(_void);
                startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));
            }
        }

        UpdateTask ut = new UpdateTask();
        ut.execute();
    }

    /**
     * Delete the given task.
     *
     * @param _task the given task
     */
    private void deleteTask(final Task _task) {

        /**
         * Deletes the task in the database.
         */
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            /**
             * Deletes the data in the background.
             * @param _void the AsyncTask
             * @return null
             */
            @Override
            protected Void doInBackground(Void... _void) {
                DatabaseClient.getInstance(getApplicationContext()).getDatabase()
                        .getTaskDao()
                        .delete(_task);
                return null;
            }

            /**
             * Go back to the main activitiy after finishing the deletion.
             * @param _void
             */
            @Override
            protected void onPostExecute(Void _void) {
                super.onPostExecute(_void);
                Toast.makeText(getApplicationContext(), "Gelöscht", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, MainActivity.class));
            }
        }

        DeleteTask deleteTask = new DeleteTask();
        deleteTask.execute();
    }

    /**
     * Provides the dialog which asks for deletion of the data.
     *
     * @param _view is the current view
     */
    public void deleteTask(View _view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
        builder.setTitle("Bist du sicher?");
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface _dialogInterface, int _i) {
                deleteTask(mTask);
            }
        });
        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface _dialogInterface, int _i) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

