package ke.co.musira.tipcalculator;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etAmount;
    TextView tvPercent, bugs, info, close;
    SeekBar sbPercent;
    TextView tvTip, tvTotal, tvPersons, tvPerPerson;
    Button bPlus, bMinus;
    int peopleCount = 1;

    FloatingActionButton fabMain, fabOne, fabTwo, fabThree;
    Float translationY = 100f;

    OvershootInterpolator interpolator = new OvershootInterpolator();

    private static final String TAG = "MainActivity";

    Boolean isMenuOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAmount=findViewById(R.id.et_amount);
        tvPercent=findViewById(R.id.tv_percent);
        sbPercent=findViewById(R.id.sb_percent);
        tvTip=findViewById(R.id.tv_tip);
        tvTotal=findViewById(R.id.tv_total);
        tvPersons=findViewById(R.id.tv_persons_each);
        tvPerPerson=findViewById(R.id.tv_per_person);
        bPlus=findViewById(R.id.b_person_plus);
        bMinus=findViewById(R.id.b_person_minus);


        initFabMenu();

        bPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    peopleCount++;
                    tvPersons.setText(peopleCount + "");
            }
        });

        bMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(peopleCount > 1){
                    peopleCount--;
                    tvPersons.setText(peopleCount + "");
                }
            }
        });

        sbPercent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int percent = i;
                tvPercent.setText(percent+"%");
                calculate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculate();
            }
        });
        tvPersons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                calculate();
            }
        });

    }

    private void calculate(){
        if(etAmount.length()==0){
            etAmount.requestFocus();
//            etAmount.setError("Enter Amount");
            tvTip.setText("0.00");
            tvTotal.setText("0.00");
            tvPerPerson.setText("0.00");
        }else{
            double amount = Double.parseDouble(etAmount.getText().toString());
            int percent = sbPercent.getProgress();
            double tip = amount*percent/100.0;
            double total = amount + tip;
            double tipPerPerson = total/Double.valueOf(tvPersons.getText().toString());
            tvTip.setText(String.format("%.2f", tip));
            tvTotal.setText(String.format("%.2f", total));
            tvPerPerson.setText(String.format("%.2f", tipPerPerson));
        }
    }

    private void initFabMenu() {
        fabMain = findViewById(R.id.fabMain);
        fabOne = findViewById(R.id.fabOne);
        fabTwo = findViewById(R.id.fabTwo);
        fabThree = findViewById(R.id.fabThree);
        info = findViewById(R.id.info);
        bugs=findViewById(R.id.bugs);
        close=findViewById(R.id.close);

        fabOne.setAlpha(0f);
        fabTwo.setAlpha(0f);
        fabThree.setAlpha(0f);
        bugs.setAlpha(0f);
        info.setAlpha(0f);
        close.setAlpha(0f);

        fabOne.setTranslationY(translationY);
        fabTwo.setTranslationY(translationY);
        fabThree.setTranslationY(translationY);
        bugs.setTranslationY(translationY);
        info.setTranslationY(translationY);
        close.setTranslationY(translationY);

        fabMain.setOnClickListener(this);
        fabOne.setOnClickListener(this);
        fabTwo.setOnClickListener(this);
        fabThree.setOnClickListener(this);
    }

    private void openMenu() {
        isMenuOpen = !isMenuOpen;

        fabMain.animate().setInterpolator(interpolator).rotation(270f).setDuration(300).start();

        fabOne.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fabTwo.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fabThree.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        bugs.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        info.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        close.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();

    }

    private void closeMenu() {
        isMenuOpen = !isMenuOpen;

        fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();

        fabOne.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fabTwo.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fabThree.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        bugs.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        info.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        close.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();

    }

    private void handleFabOne() {
        Log.i(TAG, "handleFabOne: ");
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.fabMain:
//                Log.i(TAG, "onClick: fab main");
                if (isMenuOpen) {

                } else {
                    openMenu();
                }
                break;
            case R.id.fabOne:
//                Log.i(TAG, "onClick: fab one");
                handleFabOne();
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.fabTwo:
//                Log.i(TAG, "onClick: fab two");

                showDialogDeveloper();

                break;
            case R.id.fabThree:
//                Log.i(TAG, "onClick: fab three");

                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setType("message/rfc822");
                i.setData(Uri.parse("mailto:"));
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"musiraallan@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Bug reporting - Tip calculator");
                i.putExtra(Intent.EXTRA_TEXT   , "State error:");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }

    void showDialogDeveloper() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.alert_dialog_developer, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();

        Button acceptButton = view.findViewById(R.id.acceptButton);
        Button cancelButton = view.findViewById(R.id.cancelButton);

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(TAG, "onClick: website button");

                String url = "https://musira.co.ke";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: cancel button");
                alertDialog.dismiss();
            }
        });


        alertDialog.show();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        finish();
        if (isMenuOpen) {
            closeMenu();
        }
        fabOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMenuOpen) {
                    closeMenu();
                }
            }
        });
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed()
    {
//         code here to show dialog
        if (isMenuOpen) {
            closeMenu();
        } else {
            super.onBackPressed();  // optional depending on your needs
        }
    }
}
