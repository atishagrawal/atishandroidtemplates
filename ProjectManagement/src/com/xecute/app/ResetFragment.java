
package com.xecute.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.support.v4.app.Fragment;

/**
 */
public class ResetFragment extends Fragment implements View.OnClickListener{

    ResetFragmentListener mCallback;

    public interface ResetFragmentListener {
        public void onButtonSelected(View v);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LinearLayout resetView = (LinearLayout) inflater.inflate(R.layout.fragment_reset, container, false);

        Button resetBtn = (Button) resetView.findViewById(R.id.resetBtn);
        resetBtn.setOnClickListener(this);

        return resetView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ResetFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ResetFragmentListener");
        }
    }

    @Override
    public void onClick(View v) {
        mCallback.onButtonSelected(v);
    }
}
