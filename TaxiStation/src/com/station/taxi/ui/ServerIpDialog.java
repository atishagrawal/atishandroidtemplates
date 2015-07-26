package com.station.taxi.ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import com.station.taxi.R;

/**
 * Dialog to change ip
 * @author alex
 *
 */
public class ServerIpDialog extends DialogFragment {

	private static final String ARG_IP = "ip";

	interface ServerIpDialogListener {
		void onSetServerIp(String ip);
	}
	/**
     * Create a new instance of FindServerDialog
     */
    static ServerIpDialog newInstance(String ip) {
    	ServerIpDialog f = new ServerIpDialog();
    	
    	// Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString(ARG_IP, ip);
        f.setArguments(args);
    	
        return f;
    }

	private EditText mEditText;
    
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
    	//initial ip
    	String ip = getArguments().getString(ARG_IP);
    	 
    	mEditText = new EditText(getActivity());
    	
    	if (ip != null) {
    		mEditText.setText(ip);
    	}
    	
    	//create dialog using builder
    	Builder builder = new AlertDialog.Builder(getActivity());
    	builder
    		.setView(mEditText)
    		.setTitle(R.string.set_station_ip)
    		.setPositiveButton(R.string.save, new OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				((ServerIpDialogListener)getActivity()).onSetServerIp(mEditText.getText().toString());
			}
		});
    	
    	return builder.create();
    }
}
