package com.bobrusha.android.artists.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        View v = inflater.inflate(R.layout.fragment_about, container, false);

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

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL_TO});
        i.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT);
        i.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(i, getString(R.string.send_email_chooser)));
        } catch (android.content.ActivityNotFoundException ex) {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.refresh_layout),
                    getString(R.string.no_email_client),
                    Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }
}
