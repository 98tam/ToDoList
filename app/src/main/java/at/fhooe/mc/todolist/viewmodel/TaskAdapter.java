package at.fhooe.mc.todolist.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import at.fhooe.mc.todolist.R;
import at.fhooe.mc.todolist.view.UpdateTaskActivity;
import at.fhooe.mc.todolist.model.Task;

import static at.fhooe.mc.todolist.R.color.colorHint;

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
                .inflate(R.layout.fragment_recyclerview_task, _parent, false));
    }

    /**
     * Binds the view holder.
     *
     * @param _viewHolder the given view holder
     * @param _pos        the current postion
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TaskViewHolder _viewHolder, int _pos) {
        Task task = mList.get(_pos);
        _viewHolder.mTaskText.setText(task.getTask());
        _viewHolder.mDescText.setText(task.getDesc());
        Glide.with(mContext).load(task.getImage()).into(_viewHolder.mImage);

        if (task.isFinished()) {
            _viewHolder.mCheck.setVisibility(View.VISIBLE);
            _viewHolder.mTaskText.setTextColor(colorHint);
            _viewHolder.mDescText.setTextColor(colorHint);
            _viewHolder.mTaskText.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            _viewHolder.mDescText.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            _viewHolder.mImage.setAlpha(0.5f);
        } else {
            _viewHolder.mCheck.setVisibility(View.INVISIBLE);
        }
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
        TextView mTaskText, mDescText;
        //represents a check-mark when task is finished
        ImageView mCheck, mImage;

        /**
         * Constructor
         *
         * @param _view is the given view
         */
        public TaskViewHolder(View _view) {
            super(_view);

            mTaskText = _view.findViewById(R.id.fragment_recyclerview_text_task);
            mDescText = _view.findViewById(R.id.fragment_recyclerview_text_desc);
            mCheck = _view.findViewById(R.id.fragment_recyclerview_check);
            mImage = _view.findViewById(R.id.fragment_recyclerview_image);

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