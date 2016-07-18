package com.bobrusha.android.artists.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bobrusha.android.artists.R;

/**
 * Created by Aleksandra on 16/07/16.
 */
public class AboutFragment extends Fragment {
    private static final String EMAIL_TO = "bobrova.aleksand@gmail.com";
    private static final String EMAIL_SUBJECT = "Artists app";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        Button button = (Button) v.findViewById(R.id.btn_send_email_to_devs);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendEmailClick(v);
            }
        });

        return v;

    }

    public void onSendEmailClick(View v) {

        Log.v(this.getClass().toString(), "q");

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL_TO});
        i.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT);
        i.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            //TODO: delete toast
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }
}
