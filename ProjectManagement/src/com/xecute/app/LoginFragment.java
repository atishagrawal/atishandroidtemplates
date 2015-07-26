
package com.xecute.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    LoginFragmentListener mCallback;

    public interface LoginFragmentListener {
        public void onButtonSelected(View v);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LinearLayout loginView = (LinearLayout) inflater.inflate(R.layout.fragment_login, container, false);

        Button loginBtn = (Button) loginView.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        Button resetBtn = (Button) loginView.findViewById(R.id.goToResetBtn);
        resetBtn.setOnClickListener(this);
        Button signupBtn = (Button) loginView.findViewById(R.id.goToSignupBtn);
        signupBtn.setOnClickListener(this);


        return loginView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (LoginFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement LoginFragmentListener");
        }
    }

    @Override
    public void onClick(View v) {
        mCallback.onButtonSelected(v);

    }

}
