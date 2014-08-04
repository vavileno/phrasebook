package ru.zebro.phrasebook;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by user on 02.08.2014.
 */
public class SubStringFilterArrayAdapter extends ArrayAdapter<String> implements Filterable {

    final ArrayContainer<String> allObjects;
    List<String> objects;
    final CustomFilter myfilter = new CustomFilter();


    public SubStringFilterArrayAdapter(final Context context_, final int tvResId_, final ArrayContainer<String> objects_) {
        super(context_, tvResId_, objects_.getArray());
        allObjects = objects_;
        objects = allObjects.getArray();
    }

    @Override
    public Filter getFilter() {
        return myfilter;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public String getItem(int position) {
        return objects.get(position);
    }

    private class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(final CharSequence prefix) {
            final FilterResults results = new FilterResults();
            List<String> matched = new ArrayList<>();

            List<String> matchedBeginning = new ArrayList<>();
            List<String> matchedAnywhere = new ArrayList<>();

            // Put in matched the results that match the prefix using your own implementation
            if(prefix == null || prefix.length() < 3) {
                results.count = 0;
                results.values = Collections.EMPTY_LIST;
                return results;
            }
            else {
                for(String checkObj : allObjects.getArray()) {
                    if(checkObj.toUpperCase().startsWith(prefix.toString().toUpperCase())) {
                        matchedBeginning.add(checkObj);
                    }
                    else if(checkObj.toUpperCase().contains(prefix.toString().toUpperCase())) {
                        matchedAnywhere.add(checkObj);
                    }
                }
            }

            Collections.sort(matchedBeginning);
            matched.addAll(matchedBeginning);
            Collections.sort(matchedAnywhere);
            matched.addAll(matchedAnywhere);

            results.values = matched;
            results.count = matched.size();

            return results;
        }

        @Override
        protected void publishResults(final CharSequence constraint, final FilterResults results) {

            objects = (List<String>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            }
            else {
                notifyDataSetInvalidated();
            }
        }
    }

}
