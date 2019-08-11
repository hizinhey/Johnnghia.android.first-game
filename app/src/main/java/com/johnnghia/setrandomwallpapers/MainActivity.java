package com.johnnghia.setrandomwallpapers;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int score = 100;            // Your money
    CheckBox ckb1, ckb2, ckb3;  // 3 checkBox for petting
    SeekBar seb1, seb2, seb3;   // 3 SeekBar for comparing
    ImageButton btnPlay;
    TextView txtScore;

    private int[] VanToc = new int[3]; // Van toc 3 doi tuong


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Matching();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ckb1.isChecked() == false && ckb2.isChecked() == false && ckb3.isChecked() == false) {
                    Toast.makeText(getApplication(), "Ban chua check de danh cuoc", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "Game bat dau.", Toast.LENGTH_SHORT).show();
                    GameProcess();
                }
            }
        });


    }


    // Matching between Widget and variable
    private void Matching() {
        // CheckBox
        ckb1 = (CheckBox) findViewById(R.id.checkBox);
        ckb2 = (CheckBox) findViewById(R.id.checkBox2);
        ckb3 = (CheckBox) findViewById(R.id.checkBox3);

        // SeekBar
        seb1 = (SeekBar) findViewById(R.id.seb1);
        seb1.setEnabled(false);
        seb2 = (SeekBar) findViewById(R.id.seb2);
        seb2.setEnabled(false);
        seb3 = (SeekBar) findViewById(R.id.seb3);
        seb3.setEnabled(false);

        // ImageButton
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);

        // TextView
        txtScore = (TextView) findViewById(R.id.textView);
        txtScore.setText(score + "");
    }

    private void setEnableCheckBox(boolean isEnable){
        ckb1.setEnabled(isEnable);
        ckb2.setEnabled(isEnable);
        ckb3.setEnabled(isEnable);
    }

    private void GameProcess() {
        CountDownTimer GamePlay = new CountDownTimer(20000, 500) {
            Random rand;
            

            @Override
            public void onTick(long millisUntilFinished) {        // Cai dat van toc cho 3 doi tuong
                rand = new Random();

                VanToc[0] = rand.nextInt(20) + 5;
                VanToc[1] = rand.nextInt(20) + 5;
                VanToc[2] = rand.nextInt(20) + 5;
                
                int seb1_value = seb1.getProgress();
                int seb2_value = seb2.getProgress();
                int seb3_value = seb3.getProgress();
                
                // Lay ket qua
                if (seb1_value + VanToc[0] > seb1.getMax() || seb2_value + VanToc[1] > seb2.getMax() || seb3_value + VanToc[2] > seb3.getMax()) {
                    int result;
                    if (seb1_value > seb2_value) {
                        if (seb1_value > seb3_value) {
                            result = 1;
                        } else {
                            result = 3;
                        }
                    } else {
                        if (seb2_value > seb3_value) {
                            result = 2;
                        } else {
                            result = 3;
                        }
                    }
                    Toast.makeText(getApplication(), "Chuc mung nguoi choi " + result + " chien thang", Toast.LENGTH_SHORT).show();


                    if (ckb1.isChecked()) {
                        if (result != 1) {
                            score -= 10;
                        } else {
                            score += 10;
                        }
                    }

                    if (ckb2.isChecked()) {
                        if (result != 2) {
                            score -= 10;
                        } else {
                            score += 10;
                        }
                    }

                    if (ckb3.isChecked()) {
                        if (result != 3) {
                            score -= 10;
                        } else {
                            score += 10;
                        }
                    }

                    if (score <= 0) {
                        Toast.makeText(getApplication(), "You are loser!!!", Toast.LENGTH_SHORT);
                    } else {
                        txtScore.setText(score + "");
                    }


                    // reset qua trinh

                    seb1.setProgress(0);
                    seb2.setProgress(0);
                    seb3.setProgress(0);
                    this.onFinish();
                } else {
                    seb1.setProgress(seb1_value + VanToc[0]);
                    seb2.setProgress(seb2_value + VanToc[1]);
                    seb3.setProgress(seb3_value + VanToc[2]);
                }


            }


            // Sau khi ket thuc game, se cho phep nut Play hoat dong
            // Code co su trung lap trong qua trinh xu li voi phan Process
            @Override
            public void onFinish() {
                btnPlay.setVisibility(View.VISIBLE);
                setEnableCheckBox(true);
                this.cancel();
            }

        };
        btnPlay.setVisibility(View.INVISIBLE);
        setEnableCheckBox(false);
        GamePlay.start();
    }
}
