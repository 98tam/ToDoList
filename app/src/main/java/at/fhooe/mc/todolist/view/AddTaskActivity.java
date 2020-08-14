package at.fhooe.mc.todolist.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import java.util.Objects;

import at.fhooe.mc.todolist.R;
import at.fhooe.mc.todolist.model.Task;
import at.fhooe.mc.todolist.model.DatabaseClient;

/**
 * This activity adds the tasks to the overview
 * and saves them in the database.
 */
public class AddTaskActivity extends AppCompatActivity {

    // the text components of the task
    private EditText mTaskText, mDescText;
    // given image urls
    private String[] mUrls = {"https://cdn.pixabay.com/photo/2015/09/02/12/25/basket-918416_1280.jpg",
            "https://cdn.pixabay.com/photo/2020/04/22/21/31/flowers-5080164_1280.jpg",
            "https://cdn.pixabay.com/photo/2017/03/27/15/14/forest-2179318_1280.jpg",
            "https://cdn.pixabay.com/photo/2019/05/06/19/13/green-4183977_1280.jpg",
            "https://cdn.pixabay.com/photo/2015/12/01/20/28/road-1072823_1280.jpg",
            "https://cdn.pixabay.com/photo/2013/08/20/15/47/sunset-174276_1280.jpg",
            "https://cdn.pixabay.com/photo/2019/05/07/09/01/spices-4185324_1280.jpg"};

    /**
     * Initializes all given ui elements.
     *
     * @param _savedInstanceState is the saved state
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mTaskText = findViewById(R.id.activity_add_text_task);
        mDescText = findViewById(R.id.activity_add_text_desc);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    /**
     * This method provides the right order for the back button.
     *
     * @param _item is the menuitem
     * @return true if the right activity is chosen
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem _item) {
        switch (_item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(_item);
        }
    }

    /**
     * Saves the task to the database.
     */
    public void saveTask(View _view) {
        final String inputTask = mTaskText.getText().toString().trim();
        if (inputTask.isEmpty()) {
            mTaskText.setError("Titel fehlt!");
            mTaskText.requestFocus();
            return;
        }

        final String inputDesc = mDescText.getText().toString().trim();
        if (inputDesc.isEmpty()) {
            mDescText.setError("Beschreibung fehlt!");
            mDescText.requestFocus();
            return;
        }

        int max = 6;
        int min = 0;
        final int choice = (int) (Math.random() * (max - min + 1)) + min;

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
                task.setImage(mUrls[choice]);

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
                Toast.makeText(getApplicationContext(), "Gespeichert", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask saveTask = new SaveTask();
        saveTask.execute();
    }
}

