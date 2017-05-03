package com.example.user.honor;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Adapter that is backed by an array of {@code DetectedActivity} objects. Finds UI elements in the
 * detected_activity layout and populates each element with data from a DetectedActivity
 * object.
 */
public class DetectedActivitiesAdapter extends ArrayAdapter<DetectedActivity> {

    private long temp;

    public DetectedActivitiesAdapter(Context context,
                                     ArrayList<DetectedActivity> detectedActivities) {
        super(context, 0, detectedActivities);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        DetectedActivity detectedActivity = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.detected_activity, parent, false);
        }

        // Find the UI widgets.
        TextView activityName = (TextView) view.findViewById(R.id.detected_activity_name);
        TextView activityConfidenceLevel = (TextView) view.findViewById(R.id.detected_activity_confidence_level);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.detected_activity_progress_bar);

        // Populate widgets with values.
        activityName.setText(Constants.getActivityString(getContext(),
                detectedActivity.getType()));
        activityConfidenceLevel.setText(detectedActivity.getConfidence() + "%");
        progressBar.setProgress(detectedActivity.getConfidence());


        return view;
    }

    /**
     * Process list of recently detected activities and updates the list of {@code DetectedActivity}
     * objects backing this adapter.
     *
     * @param detectedActivities the freshly detected activities
     */
    protected void updateActivities(ArrayList<DetectedActivity> detectedActivities) {
        HashMap<Integer, Integer> detectedActivitiesMap = new HashMap<>();
        long curr = System.currentTimeMillis() - temp;
        for (DetectedActivity activity : detectedActivities) {
            detectedActivitiesMap.put(activity.getType(), activity.getConfidence());
            if(activity.getType() == 3 && activity.getConfidence()>=30){
                temp++;
                Log.i("temp", temp+"");
            }
            if(activity.getType() !=3 && activity.getConfidence()>=60){
                temp =0;
            }
        }
        // Every time we detect new activities, we want to reset the confidence level of ALL
        // activities that we monitor. Since we cannot directly change the confidence
        // of a DetectedActivity, we use a temporary list of DetectedActivity objects.
        ArrayList<DetectedActivity> tempList = new ArrayList<DetectedActivity>();
        for (int i = 0; i < Constants.MONITORED_ACTIVITIES.length; i++) {
            int confidence = detectedActivitiesMap.containsKey(Constants.MONITORED_ACTIVITIES[i]) ?
                    detectedActivitiesMap.get(Constants.MONITORED_ACTIVITIES[i]) : 0;

            if (detectedActivitiesMap.get(Constants.MONITORED_ACTIVITIES[i]) != null) {
                if (temp >= 20) {
                    Toast.makeText(getContext(), "Inactive for 2 minutes or more! Please consider going for a walk.", Toast.LENGTH_LONG).show();
                }
            }
            tempList.add(new DetectedActivity(Constants.MONITORED_ACTIVITIES[i],
                    confidence));
        }
        // Remove all items.
        this.clear();

        // Adding the new list items notifies attached observers that the underlying data has
        // changed and views reflecting the data should refresh.
        for (DetectedActivity detectedActivity : tempList) {
            this.add(detectedActivity);
        }
    }
}
