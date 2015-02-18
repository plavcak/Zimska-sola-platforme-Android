package si.feri.um.uporabniskivmesnik.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import si.feri.um.uporabniskivmesnik.R;

public class MessageFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_TITLE = "title";

    private String title;

    private Button button;
    private EditText editText;
    private TextView textView;
    private OnFragmentInteractionListener mListener;

    public static MessageFragment newInstance(String name) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, name);
        fragment.setArguments(args);
        return fragment;
    }

    public MessageFragment() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        button = (Button) view.findViewById(R.id.button);
        editText = (EditText) view.findViewById(R.id.editText);
        textView = (TextView) view.findViewById(R.id.tvTitle);

        textView.setText(title);
        button.setOnClickListener(this);

        return view;
    }

    public void onButtonPressed() {
        if (mListener != null) {

            if(editText.getText().length() > 0) {
                //Pošlji sporočilo poslušalcu
                mListener.onMessageToSent(editText.getText().toString());
            } else {
                // Sporočilo je prekratko pokaži dialog
                new AlertDialog.Builder(getActivity()).setTitle(title).setMessage("Sporočilo je prekratko.").setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }


        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        if(v.equals(button)) {
            onButtonPressed();
        }

    }

    public interface OnFragmentInteractionListener {
        public void onMessageToSent(String message);
    }
}
