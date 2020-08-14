package at.fhooe.mc.todolist.model;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class TaskJobService extends JobService {

    /**
     *
     * @param _jobParameters
     * @return
     */
    @Override
    public boolean onStartJob(JobParameters _jobParameters) {
        return false;
    }

    /**
     *
     * @param _jobParameters
     * @return
     */
    @Override
    public boolean onStopJob(JobParameters _jobParameters) {
        return false;
    }
}
