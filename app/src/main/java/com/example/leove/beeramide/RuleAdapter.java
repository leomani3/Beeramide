package com.example.leove.beeramide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leove on 20/04/2018.
 */

public class RuleAdapter extends ArrayAdapter<Rule> {


    public RuleAdapter(@NonNull Context context, int resource, ArrayList<Rule> rules) {
        super(context, resource, rules);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Rule rule = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rule_list_adapter, parent, false);
        }
        // Lookup view for data population
        TextView ruleName = convertView.findViewById(R.id.textViewRule);
        // Populate the data into the template view using the data object
        ruleName.setText(rule.getRule());
        // Return the completed view to render on screen
        return convertView;
    }

}
