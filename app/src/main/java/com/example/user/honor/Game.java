package com.example.user.honor;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.ImageReader;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.AlphaModifier;
import com.plattysoft.leonids.modifiers.ScaleModifier;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class Game extends AppCompatActivity {
    private ImageButton but1;
    private ImageButton but2;
    private ImageButton but3;
    private ImageButton but4;
    private ImageButton but5;
    private ImageButton but6;
    private ImageButton distractionOne;
    private ImageButton distractionTwo;
    private Drawable testtt;
    private ImageButton but1test;
    private boolean firstStart;
    private boolean but1Selected;
    private boolean but2Selected;
    private boolean but3Selected;
    private boolean but4Selected;
    private boolean but5Selected;
    private boolean but6Selected;
    private boolean but1Danger;
    private boolean but2Danger;
    private boolean but3Danger;
    private boolean but4Danger;
    private boolean but5Danger;
    private boolean but6Danger;
    private boolean movingDown;
    private boolean movingUp;
    private int translationDuration = 500;
    private int score;
    private int tempScore;
    CountDownTimer points_timer;
    long sec = 80000 ;
    private int counter;
    private int lives = 7;
    private int credits;
    private TextView scoreTextView;
    private TextView livesTextView;
    private TextView timeTextView;
    private int random;
    private int random2;
    private int random3;
    private RelativeLayout layout;
    private int randomPlayPick = (int) (Math.random() * (4 - 1)) + 1;
    private ImageView backgroundOne;
    private ImageView backgroundTwo ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        layout = (RelativeLayout)findViewById(R.id.activity_game);
        but1 = (ImageButton)findViewById(R.id.but1);
        but2 = (ImageButton)findViewById(R.id.but2);
        but3 = (ImageButton)findViewById(R.id.but3);
        but4 = (ImageButton)findViewById(R.id.but4);
        but5 = (ImageButton)findViewById(R.id.but5);
        but6 = (ImageButton)findViewById(R.id.but6);
        distractionOne = (ImageButton)findViewById(R.id.distOne);
        distractionTwo = (ImageButton)findViewById(R.id.distTwo);
        scoreTextView = (TextView)findViewById(R.id.scoree) ;
        livesTextView = (TextView)findViewById(R.id.lives);
        timeTextView = (TextView)findViewById(R.id.time);
       // backgroundOne = (ImageView) findViewById(R.id.background_one);
       // backgroundTwo = (ImageView) findViewById(R.id.background_two);

/*
        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 2.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        // your async action
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {

                        backgroundOne.setTranslationX(translationX);
                        backgroundTwo.setTranslationX(translationX - width);
                        super.onPostExecute(aVoid);
                    }
                }.execute();

            }
        });
        animator.start();
        */
        unmarkAll();
        translateButtons(10,10);
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random randomGenerator = new Random();
        while (numbers.size() < 3) {
            int random = randomGenerator .nextInt(7);
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }
        mark(numbers.get(0),numbers.get(1));
        createTimer();
        /*
        new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {
                if(score>tempScore) {
                    millisUntilFinished += 100000;
                    tempScore = score;
                }
                timeTextView.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Intent map = new Intent(Game.this, MapsActivity.class);
                int tempCredits = accountInfo.getInt("Credits",0);
                editor.putInt("Credits",tempCredits);
                startActivity(map);
            }

        }.start();
        */
        but1.setOnClickListener(onClickListener);
        but2.setOnClickListener(onClickListener);
        but3.setOnClickListener(onClickListener);
        but4.setOnClickListener(onClickListener);
        but5.setOnClickListener(onClickListener);
        but6.setOnClickListener(onClickListener);
        scoreTextView.setText("Your score is " + score);
        livesTextView.setText("Remaining lives " + lives);

    }
    public void mark(int firstNum,int secondNum){
        switch (firstNum){
            case 1:but1Selected = true;if(score<=20){but1.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but1.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but1.setBackgroundResource(R.drawable.planetthird);}break;
            case 2:but2Selected = true;if(score<=20){but2.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but2.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but2.setBackgroundResource(R.drawable.planetthird);}break;
            case 3:but3Selected = true;if(score<=20){but3.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but3.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but3.setBackgroundResource(R.drawable.planetthird);}break;
            case 4:but4Selected = true;if(score<=20){but4.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but4.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but4.setBackgroundResource(R.drawable.planetthird);}break;
            case 5:but5Selected = true;if(score<=20){but5.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but5.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but5.setBackgroundResource(R.drawable.planetthird);}break;
            case 6:but6Selected = true;if(score<=20){but6.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but6.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but6.setBackgroundResource(R.drawable.planetthird);}break;
            }
        switch (secondNum){
            case 1:but1Selected = true;if(score<=20){but1.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but1.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but1.setBackgroundResource(R.drawable.planetthird);}break;
            case 2:but2Selected = true;if(score<=20){but2.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but2.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but2.setBackgroundResource(R.drawable.planetthird);}break;
            case 3:but3Selected = true;if(score<=20){but3.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but3.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but3.setBackgroundResource(R.drawable.planetthird);}break;
            case 4:but4Selected = true;if(score<=20){but4.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but4.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but4.setBackgroundResource(R.drawable.planetthird);}break;
            case 5:but5Selected = true;if(score<=20){but5.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but5.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but5.setBackgroundResource(R.drawable.planetthird);}break;
            case 6:but6Selected = true;if(score<=20){but6.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but6.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but6.setBackgroundResource(R.drawable.planetthird);}break;
        }
    }
    public void mark(int firstNum,int secondNum,int thirdNum){

        switch (firstNum){
            case 1:but1Selected = true;if(score<=20){but1.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but1.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but1.setBackgroundResource(R.drawable.planetthird);}break;
            case 2:but2Selected = true;if(score<=20){but2.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but2.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but2.setBackgroundResource(R.drawable.planetthird);}break;
            case 3:but3Selected = true;if(score<=20){but3.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but3.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but3.setBackgroundResource(R.drawable.planetthird);}break;
            case 4:but4Selected = true;if(score<=20){but4.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but4.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but4.setBackgroundResource(R.drawable.planetthird);}break;
            case 5:but5Selected = true;if(score<=20){but5.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but5.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but5.setBackgroundResource(R.drawable.planetthird);}break;
            case 6:but6Selected = true;if(score<=20){but6.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but6.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but6.setBackgroundResource(R.drawable.planetthird);}break;
        }
        switch (secondNum){
            case 1:but1Selected = true;if(score<=20){but1.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but1.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but1.setBackgroundResource(R.drawable.planetthird);}break;
            case 2:but2Selected = true;if(score<=20){but2.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but2.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but2.setBackgroundResource(R.drawable.planetthird);}break;
            case 3:but3Selected = true;if(score<=20){but3.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but3.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but3.setBackgroundResource(R.drawable.planetthird);}break;
            case 4:but4Selected = true;if(score<=20){but4.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but4.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but4.setBackgroundResource(R.drawable.planetthird);}break;
            case 5:but5Selected = true;if(score<=20){but5.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but5.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but5.setBackgroundResource(R.drawable.planetthird);}break;
            case 6:but6Selected = true;if(score<=20){but6.setBackgroundResource(R.drawable.planet);} if(score>=20 && score<=60){but6.setBackgroundResource(R.drawable.planetsecond);};if(score>=60){but6.setBackgroundResource(R.drawable.planetthird);}break;
        }
        switch (thirdNum){
            case 1:but1Danger = true;but1.setBackgroundResource(R.drawable.blackhole);break;
            case 2:but2Danger = true;but2.setBackgroundResource(R.drawable.blackhole);break;
            case 3:but3Danger = true;but3.setBackgroundResource(R.drawable.blackhole);break;
            case 4:but4Danger = true;but4.setBackgroundResource(R.drawable.blackhole);break;
            case 5:but5Danger = true;but5.setBackgroundResource(R.drawable.blackhole);break;
            case 6:but6Danger = true;but6.setBackgroundResource(R.drawable.blackhole);break;
        }
    }
    public void unmarkAll(){
        but1.setBackgroundResource(R.drawable.meteor);but1Selected=false;but1Danger=false;
        but2.setBackgroundResource(R.drawable.meteor);but2Selected=false;but2Danger=false;
        but3.setBackgroundResource(R.drawable.meteor);but3Selected=false;but3Danger=false;
        but4.setBackgroundResource(R.drawable.meteor);but4Selected=false;but4Danger=false;
        but5.setBackgroundResource(R.drawable.meteor);but5Selected=false;but5Danger=false;
        but6.setBackgroundResource(R.drawable.meteor);but6Selected=false;but6Danger=false;

    }

    public boolean areNoneSelected(){
        if(!but1Selected && !but2Selected && !but3Selected && !but4Selected && !but5Selected && !but6Selected){
            return true;
        }
        return false;
    }
    /*
    method for moving  the 2 groups of buttons,

     */
    public void translateButtons(int grpOne,int grpTwo){

        switch (grpOne){
// int 1 = right , int 2 = left , int 3 = down , int 4 = up , int 5 = up and right,int 6 down and left + rotation 360,int 7 right and down + rotation 180,
            //int 8 = but1-down but2-up but3-down ,int 9 = but1-up but2-down but3-up
            case 1:
                but1.animate().translationX(15).setDuration(translationDuration);but2.animate().translationX(15).setDuration(translationDuration);
                but3.animate().translationX(15).setDuration(translationDuration);break;
            case 2:
                 but1.animate().translationX(-15).setDuration(translationDuration);but2.animate().translationX(-15).setDuration(translationDuration);
                 but3.animate().translationX(-15).setDuration(translationDuration);break;
            case 3:
                  but1.animate().translationY(100).setDuration(translationDuration);but2.animate().translationY(100).setDuration(translationDuration);
                  but3.animate().translationY(100).setDuration(translationDuration);break;
            case 4:
                 but1.animate().translationY(-100).setDuration(translationDuration);but2.animate().translationY(-100).setDuration(translationDuration);
                 but3.animate().translationY(-100).setDuration(translationDuration);break;
            case 5:
                 but1.animate().translationX(15).translationY(-120).setDuration(translationDuration);but2.animate().translationX(15).translationY(-120).setDuration(translationDuration);
                 but3.animate().translationX(15).translationY(-120).setDuration(translationDuration);break;
            case 6:
                but1.animate().rotation(360).translationX(-15).translationY(120).setDuration(translationDuration);but2.animate().rotation(360).translationX(-15).translationY(120).setDuration(translationDuration);
                but3.animate().rotation(360).translationX(-15).translationY(120).setDuration(translationDuration);break;
            case 7:
                but1.animate().rotation(180).translationX(15).translationY(120).setDuration(translationDuration);but2.animate().rotation(180).translationX(15).translationY(120).setDuration(translationDuration);
                but3.animate().rotation(180).translationX(15).translationY(120).setDuration(translationDuration);break;
            case 8:
                but1.animate().translationY(25).setDuration(translationDuration);but2.animate().translationY(-25).setDuration(translationDuration);
                but3.animate().translationY(25).setDuration(translationDuration);break;
            case 9:
                but1.animate().rotation(360).translationY(-25).setDuration(translationDuration);but2.animate().rotation(360).translationY(25).setDuration(translationDuration);
                but3.animate().rotation(360).translationY(-25).setDuration(translationDuration);break;
            case 10:
                but1.animate().translationX(-55).setDuration(translationDuration);but2.animate().translationX(-55).setDuration(translationDuration);
                but3.animate().translationX(-55).setDuration(translationDuration);break;
        }
          switch (grpTwo){
              // int 1 = right , int 2 = left , int 3 = down , int 4 = up, int 5 = up and right + rotation 360,int 6 down and left,int 7 right and down + rotation 180,
              //int 8 = but4-down but5-up but6-down ,int 9 = but4-up but5-down but6-up
              case 1:
                 but4.animate().translationX(15).setDuration(translationDuration);
                 but5.animate().translationX(15).setDuration(translationDuration);but6.animate().translationX(35).setDuration(translationDuration);break;
              case 2:
                but4.animate().translationX(-15).setDuration(translationDuration);
                 but5.animate().translationX(-15).setDuration(translationDuration);but6.animate().translationX(-15).setDuration(translationDuration);break;
              case 3:
                but4.animate().translationY(100).setDuration(translationDuration);
                but5.animate().translationY(100).setDuration(translationDuration);but6.animate().translationY(100).setDuration(translationDuration);break;
              case 4:
                 but4.animate().translationY(-100).setDuration(translationDuration);
                 but5.animate().translationY(-100).setDuration(translationDuration);but6.animate().translationY(-100).setDuration(translationDuration);break;
              case 5:
                  but4.animate().rotation(360).translationX(15).translationY(-120).setDuration(translationDuration);
                  but5.animate().rotation(360).translationX(15).translationY(-120).setDuration(translationDuration);but6.animate().rotation(360).translationX(15).translationY(-120).setDuration(translationDuration);break;
              case 6:
                  but4.animate().translationX(-15).translationY(120).setDuration(translationDuration);
                  but5.animate().translationX(-15).translationY(120).setDuration(translationDuration);but6.animate().translationX(-15).translationY(120).setDuration(translationDuration);break;
              case 7:
                  but4.animate().rotation(180).translationX(15).translationY(120).setDuration(translationDuration);
                  but5.animate().rotation(180).translationX(15).translationY(120).setDuration(translationDuration);but6.animate().rotation(180).translationX(15).translationY(120).setDuration(translationDuration);break;
              case 8:
                  but4.animate().translationY(25).setDuration(translationDuration);
                  but5.animate().translationY(-25).setDuration(translationDuration);but6.animate().translationY(25).setDuration(translationDuration);break;
              case 9:
                  but4.animate().translationY(-25).setDuration(translationDuration);
                  but5.animate().translationY(25).setDuration(translationDuration);but6.animate().translationY(-25).setDuration(translationDuration);break;
              case 10:
                  but4.animate().translationX(-55).setDuration(translationDuration);
                  but5.animate().translationX(-55).setDuration(translationDuration);but6.animate().translationX(-55).setDuration(translationDuration);break;
          }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.but1:
                    if(but1Selected) {
                        score++;counter++;
                        but1Selected = false;
                        if (score<= 20) {
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.particlefinal, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but1, 4);
                            but1.setBackgroundResource(R.drawable.meteor);
                        }
                        if(score>= 20 && score<=60){
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.explsecondplanet, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but1, 4);
                            but1.setBackgroundResource(R.drawable.meteor);
                        }
                        if(score>= 60){
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.explthirdplanet, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but1, 4);
                            but1.setBackgroundResource(R.drawable.meteor);
                        }
                    }
                    if(but1Danger){lives--;}
                    break;
                case R.id.but2:
                    if(but2Selected) {
                        score++;counter++;
                        but2Selected = false;
                        if (score<= 20) {
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.particlefinal, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but2, 4);
                            but2.setBackgroundResource(R.drawable.meteor);
                        }
                        if(score>= 20 && score<=60){
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.explsecondplanet, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but2, 4);
                            but2.setBackgroundResource(R.drawable.meteor);
                        }
                        if(score>= 60){
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.explthirdplanet, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but2, 4);
                            but2.setBackgroundResource(R.drawable.meteor);
                        }
                    }
                    if(but2Danger){lives--;}
                    break;
                case R.id.but3:
                    if(but3Selected) {
                        score++;
                        counter++;
                        but3Selected = false; //explosionField.explode(but3);
                        if (score <= 20) {
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.particlefinal, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but3, 4);
                            but3.setBackgroundResource(R.drawable.meteor);
                        }
                        if (score >= 20 && score<=60) {
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.explsecondplanet, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but3, 4);
                            but3.setBackgroundResource(R.drawable.meteor);
                        }
                        if (score >= 60) {
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.explthirdplanet, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but3, 4);
                            but3.setBackgroundResource(R.drawable.meteor);
                        }
                    }
                    if(but3Danger){lives--;}
                    break;
                case R.id.but4:
                    if(but4Selected) {
                        score++;counter++;
                        but4Selected = false; //explosionField.explode(but4);
                        if (score <= 20) {
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.particlefinal, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but4, 4);
                            but4.setBackgroundResource(R.drawable.meteor);
                        }
                        if (score >= 20 && score<=60) {
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.explsecondplanet, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but4, 4);
                            but4.setBackgroundResource(R.drawable.meteor);
                        }
                        if (score >= 60) {
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.explthirdplanet, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but4, 4);
                            but4.setBackgroundResource(R.drawable.meteor);
                        }
                    }
                    if(but4Danger){lives--;}
                    break;
                case R.id.but5:
                    if(but5Selected) {
                        score++;counter++;
                        but5Selected = false; //explosionField.explode(but5);
                        if (score <= 20) {
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.particlefinal, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but5, 4);
                            but5.setBackgroundResource(R.drawable.meteor);
                        }
                        if (score >= 20&& score<=60) {
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.explsecondplanet, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but5, 4);
                            but5.setBackgroundResource(R.drawable.meteor);
                        }
                        if (score >= 60) {
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.explthirdplanet, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but5, 4);
                            but5.setBackgroundResource(R.drawable.meteor);
                        }
                    }
                    if(but5Danger){lives--;}
                    break;
                case R.id.but6:
                    if(but6Selected) {
                        score++;counter++;
                        but6Selected = false; //explosionField.explode(but6);
                        if (score <= 20) {
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.particlefinal, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but6, 4);
                            but6.setBackgroundResource(R.drawable.meteor);
                        }
                        if (score >= 20 && score<=60) {
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.explsecondplanet, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but6, 4);
                            but6.setBackgroundResource(R.drawable.meteor);
                        }
                        if (score >= 60) {
                            new ParticleSystem(com.example.user.honor.Game.this, 4, R.drawable.explthirdplanet, 3000)
                                    .setSpeedRange(0.2f, 0.5f)
                                    .oneShot(but6, 4);
                            but6.setBackgroundResource(R.drawable.meteor);
                        }
                    }
                    if(but6Danger){lives--;}
                    break;
            }
            final SharedPreferences accountInfo = PreferenceManager.getDefaultSharedPreferences(com.example.user.honor.Game.this);
            if(lives <= 0){
                final SharedPreferences.Editor editor = accountInfo.edit();
                credits = accountInfo.getInt("Credits",0);
                credits--;
                editor.putInt("Credits",credits);
                int tempInt = accountInfo.getInt("HighScore",0);
                if(score>tempInt){
                    editor.putInt("HighScore",score);
                }
                editor.apply();
                Intent mapsActivity = new Intent(Game.this,MapsActivity.class);
                startActivity(mapsActivity);
            }
            if(areNoneSelected()){
                unmarkAll();
                ArrayList<Integer> numbers = new ArrayList<Integer>();
                Random randomGenerator = new Random();
                while (numbers.size() < 3) {

                    int random = randomGenerator .nextInt(7);
                    if (!numbers.contains(random)) {
                        numbers.add(random);
                    }
                }
                random = numbers.get(0);
                random2 = numbers.get(1);
                random3 = numbers.get(2);
                if(score<=15){mark(random, random2);}
                else{mark(random,random2,random3);}
            }
            if(counter == 7 || counter == 21 || counter == 35){
                distractionOne.setBackgroundResource(R.drawable.blackhole);
                final int randomX = (int) (Math.random() * (700 - 600)) + 600;
                distractionOne.animate().translationX(randomX).translationY(1100).setDuration(2300).setListener(new Animator.AnimatorListener() {
                    @Override public void onAnimationStart(Animator animator) {}
                    @Override
                    public void onAnimationEnd(Animator animator) {
                            distractionOne.animate().translationX(-210).setDuration(2300);
                    }
                    @Override public void onAnimationCancel(Animator animator) {}
                    @Override public void onAnimationRepeat(Animator animator) {}});
            }
            if(counter == 12 ||counter == 26 ||counter == 40 ){
                final int randomX = (int) (Math.random() * (700 - 600)) + 600;
                distractionOne.animate().translationX(randomX).translationY(-1000).setDuration(2300).setListener(new Animator.AnimatorListener() {
                    @Override public void onAnimationStart(Animator animator) {}
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        distractionOne.animate().translationX(-210).setDuration(2300);
                    }
                    @Override public void onAnimationCancel(Animator animator) {}
                    @Override public void onAnimationRepeat(Animator animator) {}});
            }
            //Distraction 2
            if(counter == 10 || counter == 24 || counter == 38){
                distractionTwo.setBackgroundResource(R.drawable.blackhole);
                final int randomX = (int) (Math.random() * (900 - 800)) + 800;
                distractionTwo.animate().translationX(-randomX).translationY(1100).setDuration(2300).setListener(new Animator.AnimatorListener() {
                    @Override public void onAnimationStart(Animator animator) {}
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        distractionTwo.animate().translationY(-310).setDuration(2300);
                    }
                    @Override public void onAnimationCancel(Animator animator) {}
                    @Override public void onAnimationRepeat(Animator animator) {}});
            }
            if(counter == 15 ||counter == 29 ||counter == 43 ){
                final int randomX = (int) (Math.random() * (700 - 600)) + 600;
                distractionTwo.animate().translationX(randomX).translationY(1000).setDuration(2300).setListener(new Animator.AnimatorListener() {
                    @Override public void onAnimationStart(Animator animator) {}
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        distractionTwo.animate().translationX(-810).translationY(-400).setDuration(2300);
                    }
                    @Override public void onAnimationCancel(Animator animator) {}
                    @Override public void onAnimationRepeat(Animator animator) {}});
            }


            if(randomPlayPick == 1){
                if(counter == 0){counter = score;}
                if(counter >= 3){translateButtons(4,3);}
                if(counter >= 5){translateButtons(9,8);}
                if(counter >= 8){translateButtons(5,4);}
                if(counter >=12){translateButtons(6,6);}
                if(counter >=15){translateButtons(4,5);}
                if(counter >=18){translateButtons(5,7);}
                if(counter >=21){translateButtons(7,7);translateButtons(1,2);
                    randomPlayPick = (int) (Math.random()*(4-1))+1;counter =3;}
            }
            if(randomPlayPick == 2){
              if(counter == 0){counter = score;}
              if(counter >= 3){translateButtons(9,8);}
              if(counter >= 5){translateButtons(8,9);}
              if(counter >= 8){translateButtons(10,7);}
              if(counter >=12){translateButtons(5,10);}
              if(counter >=15){translateButtons(6,6);}
              if(counter >=18){translateButtons(5,2);}
              if(counter >=21){translateButtons(8,3);translateButtons(2,1);
              randomPlayPick = (int) (Math.random()*(4-1))+1;counter =3;}
              }
            if(randomPlayPick == 3){
                if(counter == 0){counter = score;}
                if(counter >= 3){translateButtons(6,7);}
                if(counter >= 5){translateButtons(5,4);}
                if(counter >= 8){translateButtons(8,9);}
                if(counter >=12){translateButtons(3,6);}
                if(counter >=15){translateButtons(10,10);}
                if(counter >=18){translateButtons(5,8);}
                if(counter >=21){translateButtons(1,3);translateButtons(2,1);
                    randomPlayPick = (int) (Math.random()*(4-1))+1;counter =3;}
            }
            if(score >= 20 && score <=40){translationDuration=400;}
            if(score >= 40 && score <=100){translationDuration=300;}
            if(score >= 100 && score <=140){translationDuration=200;}
            if(lives <= 3){
                layout.setBackgroundResource(R.drawable.biggerbgred);
            }
            scoreTextView.setText("Your score is " + score);
            livesTextView.setText("Remaining lives " + lives);
        }
    };
    public void createTimer()
    {
        final SharedPreferences accountInfo = PreferenceManager.getDefaultSharedPreferences(com.example.user.honor.Game.this);
        final SharedPreferences.Editor editor = accountInfo.edit();
        points_timer =new CountDownTimer(sec,1000) {

            @Override
            public void onTick(long millisUntilFinished)
            {
                sec = millisUntilFinished;

                if (score > tempScore)
                {
                    sec += 3000;
                    points_timer.cancel();
                    tempScore = score;
                    createTimer();
                }
                timeTextView.setText(millisUntilFinished/1000+ "");
                if((millisUntilFinished/1000) <= 20 && timeTextView.getCurrentTextColor() == Color.BLACK){
                    timeTextView.setTextColor(Color.RED);
                    timeTextView.animate().translationY(180).scaleX(7).scaleY(7);
                }
                else{
                        timeTextView.setTextColor(Color.BLACK);
                        timeTextView.animate().translationY(-180).scaleX(1).scaleY(1);
                }
            }

            @Override
            public void onFinish()
            {
                Intent map = new Intent(Game.this, MapsActivity.class);
                int tempCredits = accountInfo.getInt("Credits",0);
                editor.putInt("Credits",tempCredits);
                startActivity(map);
            }
        }.start();;
    }

}







/*     do{
                   tempRandom = (int) (Math.random() * (upper - lower)) + lower;
                   tempRandom2 = (int) (Math.random() * (upper - lower)) + lower;
                   tempRandom3 = (int) (Math.random() * (upper - lower)) + lower;
                   Log.i("test",tempRandom+"");
                   Log.i("test2",tempRandom2+"");
                   Log.i("testRandom",random+"");
                   Log.i("testRandom",random2+"");
               }while(tempRandom == random && tempRandom2 == random2 && tempRandom == random2 && random == tempRandom2 && random3 == tempRandom3 && random3 == tempRandom && random == random3);


        if(but5Selected){
        score++;
        but5Selected = false;
        but5.setBackgroundColor(2);
        if(areNoneSelected()){
        int r = (int) (Math.random() * (upper - lower)) + lower;
        int r2 = (int) (Math.random() * (upper - lower)) + lower;
        markTwo(r,r2);
        if(score%10 == 0){
        translateButtons(r,r2);
        }
        }
        }
        scoreTextView.setText(score+ "");
        }
        })*/
