package se.puzzlingbytes.BLETest;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Mikael Olsson on 2013-09-20.
 */
public class CharaValueDialogFragment extends DialogFragment {

    public static final String TAG = CharaValueDialogFragment.class.getSimpleName();
    private static final String EXTRA_CHARA_NAME = "extra_chara_name";
    private Activity mActivity;
    private TextView mValueText;
    private ProgressBar mProgressBar;

    public static CharaValueDialogFragment newInstance(String charaName) {
        CharaValueDialogFragment dialogFragment = new CharaValueDialogFragment();
        Bundle dialogArgs = new Bundle();
        dialogArgs.putString(EXTRA_CHARA_NAME, charaName);
        dialogFragment.setArguments(dialogArgs);
        return dialogFragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    public void setValue(String value) {
        mProgressBar.setVisibility(View.GONE);
        mValueText.setVisibility(View.VISIBLE);
        mValueText.setText(value);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogLayout = View.inflate(mActivity, R.layout.dialogfrag_charavalue, null);
        mValueText = (TextView) dialogLayout.findViewById(R.id.valueText);
        mProgressBar = (ProgressBar) dialogLayout.findViewById(R.id.valueProgress);
        getDialog().setTitle(getArguments().getString(EXTRA_CHARA_NAME));
        return dialogLayout;
    }
}


