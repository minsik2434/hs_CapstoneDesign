package com.example.project_main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_2 extends Fragment implements StepCounterService.StepCountListener {

    private TextView stepCountTextView;
    private StepCounterService stepCounterService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frame_2, container, false);
        stepCountTextView = rootView.findViewById(R.id.stepCountTextView);

        stepCounterService = new StepCounterService();
        stepCounterService.setStepCountListener(this);

        if (getContext() != null) {
            getContext().startService(new Intent(getContext(), stepCounterService.getClass()));
        }

        return rootView;
    }

    @Override
    public void onStepCountChanged(int stepCount) {
        if (stepCountTextView != null) {
            stepCountTextView.setText("오늘 걸음수 : " + stepCount);
        }
    }
}