package at.fhooe.mc.todolist.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import at.fhooe.mc.todolist.R;
import at.fhooe.mc.todolist.UpdateTaskActivity;
import at.fhooe.mc.todolist.model.Task;

/**
 * The adapter for the recyclerview to view all elements.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    //context
    private Context mContext;
    //list of all tasks
    private List<Task> mList;

    /**
     * Constructor
     *
     * @param _context the context
     * @param _list    the list of the tasks
     */
    public TaskAdapter(Context _context, List<Task> _list) {
        mContext = _context;
        mList = _list;
    }

    /**
     * Creates the view holder.
     *
     * @param _parent the view group on top
     * @param _type   the type of the view
     * @return the created view holder
     */
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup _parent, int _type) {
        return new TaskViewHolder(LayoutInflater
                .from(mContext)
                .inflate(R.layout.recyclerview_tasks, _parent, false));
    }

    /**
     * Binds the view holder.
     *
     * @param _viewHolder the given view holder
     * @param _pos        the current postion
     */
    @Override
    public void onBindViewHolder(TaskViewHolder _viewHolder, int _pos) {
        Task task = mList.get(_pos);
        _viewHolder.mTaskText.setText(task.getTask());
        _viewHolder.mDescText.setText(task.getDesc());

        if (task.isFinished())
            _viewHolder.mStatusText.setText("Completed");
        else
            _viewHolder.mStatusText.setText("Not Completed");
    }

    /**
     * Provides the item count.
     *
     * @return the item count
     */
    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * This class provvides the text view holder for the recycler view.
     */
    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //all given text views
        TextView mStatusText, mTaskText, mDescText;

        /**
         * Constructor
         *
         * @param _view is the given view
         */
        public TaskViewHolder(View _view) {
            super(_view);

            mStatusText = _view.findViewById(R.id.textViewStatus);
            mTaskText = _view.findViewById(R.id.textViewTask);
            mDescText = _view.findViewById(R.id.textViewDesc);

            _view.setOnClickListener(this);
        }

        /**
         * This onClick-Method routes to the UpdateTaskActivity.
         *
         * @param _view is the given view
         */
        @Override
        public void onClick(View _view) {
            Intent intent = new Intent(mContext, UpdateTaskActivity.class);
            intent.putExtra("task", mList.get(getAdapterPosition()));

            mContext.startActivity(intent);
        }
    }
}