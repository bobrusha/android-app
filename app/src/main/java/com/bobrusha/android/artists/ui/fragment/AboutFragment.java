package com.bobrusha.android.artists.ui.fragment;

import android.content.ActivityNotFoundException;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Aleksandra on 16/07/16.
 */
public class AboutFragment extends Fragment {
    private static final String EMAIL_TO = "bobrova.aleksand@gmail.com";
    private static final String EMAIL_SUBJECT = "Artists app";

    @BindView(R.id.btn_send_email_to_devs)
    Button sendToButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        ButterKnife.bind(this, v);
        return v;

    }

    @OnClick(R.id.btn_send_email_to_devs)
    public void onSendEmailClick() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL_TO});
        intent.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT);

        try {
            startActivity(Intent.createChooser(intent, getString(R.string.send_email_chooser)));
        } catch (ActivityNotFoundException ex) {
            Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.refresh_layout),
                    getString(R.string.no_email_client),
                    Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }
}
