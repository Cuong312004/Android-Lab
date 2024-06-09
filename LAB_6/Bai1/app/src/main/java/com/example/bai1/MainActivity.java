package com.example.bai1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnFadeInXml, btnFadeInCode, btnFadeOutXml, btnFadeOutCode,
            btnBlinkXml,
            btnBlinkCode, btnZoomInXml, btnZoomInCode, btnZoomOutXml,
            btnZoomOutCode, btnRotateXml,
            btnRotateCode, btnMoveXml, btnMoveCode, btnSlideUpXml, btnSlideUpCode,
            btnBounceXml,
            btnBounceCode, btnCombineXml, btnCombineCode;
    private ImageView ivUitLogo;
    private Animation.AnimationListener animationListener;

    private void findViewsByIds() {
        ivUitLogo = (ImageView) findViewById(R.id.iv_uit_logo);
        btnFadeInXml = (Button) findViewById(R.id.btn_fade_in_xml);
        btnFadeInCode = (Button) findViewById(R.id.btn_fade_in_code);
        btnFadeOutXml = (Button) findViewById(R.id.btn_fade_out_xml);
        btnFadeOutCode = (Button) findViewById(R.id.btn_fade_out_code);
        btnBlinkXml = (Button) findViewById(R.id.btn_blink_xml);
        btnBlinkCode = (Button) findViewById(R.id.btn_blink_code);
        btnZoomInXml = (Button) findViewById(R.id.btn_zoom_in_xml);
        btnZoomInCode = (Button) findViewById(R.id.btn_zoom_in_code);
        btnZoomOutXml = (Button) findViewById(R.id.btn_zoom_out_xml);
        btnZoomOutCode = (Button) findViewById(R.id.btn_zoom_out_code);
        btnRotateXml = (Button) findViewById(R.id.btn_rotate_xml);
        btnRotateCode = (Button) findViewById(R.id.btn_rotate_code);
        btnMoveXml = (Button) findViewById(R.id.btn_move_xml);
        btnMoveCode = (Button) findViewById(R.id.btn_move_code);
        btnSlideUpXml = (Button) findViewById(R.id.btn_slide_up_xml);
        btnSlideUpCode = (Button) findViewById(R.id.btn_slide_up_code);
        btnBounceXml = (Button) findViewById(R.id.btn_bounce_xml);
        btnBounceCode = (Button) findViewById(R.id.btn_bounce_code);
        btnCombineXml = (Button) findViewById(R.id.btn_combine_xml);
        btnCombineCode = (Button) findViewById(R.id.btn_combine_code);
    }

    private void initVariables() {
        animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(), "Animation Stopped",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        findViewsByIds();
        initVariables();
        ImageView ivUitLogo = findViewById(R.id.iv_uit_logo);
        ivUitLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi ImageView được click
                startActivity(new Intent(MainActivity.this, NewActivity.class));

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        handleClickAnimationXml(btnFadeInXml,R.anim.anim_fade_in);
        handleClickAnimationXml(btnFadeOutXml,R.anim.anim_fade_out);
        handleClickAnimationXml(btnBlinkXml,R.anim.anim_blink);
        handleClickAnimationXml(btnZoomInXml,R.anim.anim_zoom_in);
        handleClickAnimationXml(btnZoomOutXml,R.anim.anim_zoom_out);
        handleClickAnimationXml(btnRotateXml,R.anim.anim_rotate);
        handleClickAnimationXml(btnMoveXml,R.anim.anim_move);
        handleClickAnimationXml(btnSlideUpXml,R.anim.anim_slide_up);
        handleClickAnimationXml(btnBounceXml,R.anim.anim_bounce);
        handleClickAnimationXml(btnCombineXml,R.anim.anim_combine);

        handleClickAnimationCode(btnFadeInCode,initFadeInAnimation());
        handleClickAnimationCode(btnFadeOutCode,initFadeOutAnimation());
        handleClickAnimationCode(btnBlinkCode,initBlinkAnimation());
        handleClickAnimationCode(btnZoomInCode,initZoomInAnimation());
        handleClickAnimationCode(btnZoomOutCode,initZoomOutAnimation());
        handleClickAnimationCode(btnRotateCode,initRotateAnimation());
        handleClickAnimationCode(btnMoveCode,initMoveAnimation());
        handleClickAnimationCode(btnSlideUpCode,initSlideUpAnimation());
        handleClickAnimationCode(btnBounceCode,initBounceAnimation());
        handleClickAnimationCode(btnCombineCode,initCombineAnimation());
    }

    private void handleClickAnimationXml(Button btn, int animId)
    {
        final Animation animation = AnimationUtils.loadAnimation(MainActivity.this, animId);
        animation.setAnimationListener(animationListener);

        //final Animation ani = initBlinkAnimation();
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                ivUitLogo.startAnimation(animation);
            }
        });
    }

    private void handleClickAnimationCode(Button btn, final Animation animation) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivUitLogo.startAnimation(animation);
            }
        });
    }

    private Animation initFadeInAnimation()
    {
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        animation.setAnimationListener(animationListener);
        return animation;
    }

    private Animation initFadeOutAnimation() {
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(3000);
        animation.setFillAfter(true);
        animation.setAnimationListener(animationListener);
        return animation;
    }

    private Animation initBlinkAnimation() {
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(500);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        return animation;
    }

    private Animation initZoomInAnimation() {
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1f, 3f, // fromXScale, toXScale
                1f, 3f, // fromYScale, toYScale
                Animation.RELATIVE_TO_SELF, 0.5f, // pivotXType, pivotXValue
                Animation.RELATIVE_TO_SELF, 0.5f  // pivotYType, pivotYValue
        );
        scaleAnimation.setDuration(1000);
        animationSet.addAnimation(scaleAnimation);
        return animationSet;
    }

    private Animation initZoomOutAnimation() {
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 0.5f, // fromXScale, toXScale
                1.0f, 0.5f, // fromYScale, toYScale
                Animation.RELATIVE_TO_SELF, 0.5f, // pivotXType, pivotXValue
                Animation.RELATIVE_TO_SELF, 0.5f  // pivotYType, pivotYValue
        );
        scaleAnimation.setDuration(1000);
        animationSet.addAnimation(scaleAnimation);
        return animationSet;
    }

    private Animation initRotateAnimation() {
        AnimationSet animationSet = new AnimationSet(true);

        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360, // fromDegrees, toDegrees
                Animation.RELATIVE_TO_SELF, 0.5f, // pivotXType, pivotXValue
                Animation.RELATIVE_TO_SELF, 0.5f  // pivotYType, pivotYValue
        );
        rotateAnimation.setDuration(600);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(2);
        rotateAnimation.setInterpolator(new CycleInterpolator(2));
        animationSet.addAnimation(rotateAnimation);
        return animationSet;
    }

    private Animation initMoveAnimation() {
        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0f, // fromXDeltaType, fromXDeltaValue
                Animation.RELATIVE_TO_PARENT, 0.75f, // toXDeltaType, toXDeltaValue
                Animation.RELATIVE_TO_SELF, 0f, // fromYDeltaType, fromYDeltaValue
                Animation.RELATIVE_TO_SELF, 0f  // toYDeltaType, toYDeltaValue
        );
        translateAnimation.setDuration(800);
        translateAnimation.setInterpolator(new LinearInterpolator());
        animationSet.addAnimation(translateAnimation);
        return animationSet;
    }

    private Animation initSlideUpAnimation() {
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 1.0f, // fromXScale, toXScale
                1.0f, 0.0f, // fromYScale, toYScale
                Animation.RELATIVE_TO_SELF, 0.0f, // pivotXType, pivotXValue
                Animation.RELATIVE_TO_SELF, 0.0f  // pivotYType, pivotYValue
        );
        scaleAnimation.setDuration(500);
        animationSet.addAnimation(scaleAnimation);
        return animationSet;
    }

    private Animation initBounceAnimation() {
        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 1.0f, // fromXScale, toXScale
                0.0f, 1.0f, // fromYScale, toYScale
                Animation.RELATIVE_TO_SELF, 0.0f, // pivotXType, pivotXValue
                Animation.RELATIVE_TO_SELF, 0.0f  // pivotYType, pivotYValue
        );
        scaleAnimation.setDuration(500);
        scaleAnimation.setInterpolator(new BounceInterpolator());
        animationSet.addAnimation(scaleAnimation);
        return animationSet;
    }

    private Animation initCombineAnimation() {
        AnimationSet animationSet = new AnimationSet(true);

        // Scale Animation
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 3.0f, // fromXScale, toXScale
                1.0f, 3.0f, // fromYScale, toYScale
                Animation.RELATIVE_TO_SELF, 0.5f, // pivotXType, pivotXValue
                Animation.RELATIVE_TO_SELF, 0.5f  // pivotYType, pivotYValue
        );
        scaleAnimation.setDuration(4000);

        // Rotate Animation
        RotateAnimation rotateAnimation = new RotateAnimation(
                0, 360, // fromDegrees, toDegrees
                Animation.RELATIVE_TO_SELF, 0.5f, // pivotXType, pivotXValue
                Animation.RELATIVE_TO_SELF, 0.5f  // pivotYType, pivotYValue
        );
        rotateAnimation.setDuration(500);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setRepeatCount(2);

        // Add animations to AnimationSet
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);

        return animationSet;
    }
}