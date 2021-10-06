package com.example.stopwatch2;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.os.Bundle;
import java.util.Locale;
import android.widget.TextView;

public class MainActivity extends Activity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // If there is a saved instance state, load these variables
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        stopWatch();
    }

    // Saves the state of the stopwatch before it's destroyed
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    // Stops the watch if the activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    // Resumes the stopwatch if it was previously running
    // wasRunning = running
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    // Start button
    public void onClickStart(View view) {
        running = true;
    }

    // Stop button
    public void onClickStop(View view) {
        running = false;
    }

    // Reset button
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    // Uses a Handler to increment the seconds and update the textview
    private void stopWatch() {

        final TextView timeView = (TextView)findViewById(R.id.time_view);

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d",
                        hours, minutes, secs);

                timeView.setText(time);

                if (running) {
                    seconds++;
                }

                // repost the code with a delay of 1000 milliseconds
                handler.postDelayed(this, 1000);
            }
        });
    }
}
