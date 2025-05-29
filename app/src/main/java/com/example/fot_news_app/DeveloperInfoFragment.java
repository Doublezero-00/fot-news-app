package com.example.fot_news_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.fot_news_app.R; // Added import
import androidx.fragment.app.Fragment;

public class DeveloperInfoFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_developer_info, container, false);

        TextView nameText = view.findViewById(R.id.name_text);
        TextView studentNoText = view.findViewById(R.id.student_no_text);
        TextView statementText = view.findViewById(R.id.statement_text);
        TextView versionText = view.findViewById(R.id.version_text);

        // Add null checks for TextViews
        if (nameText != null) {
            nameText.setText("Chamoth Lakshitha");
        }
        if (studentNoText != null) {
            studentNoText.setText("2022001550");
        }
        if (statementText != null) {
            statementText.setText("I'm a student");
        }
        if (versionText != null) {
            versionText.setText("1.0");
        }

        return view;
    }
}