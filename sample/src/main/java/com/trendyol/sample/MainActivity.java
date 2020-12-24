package com.trendyol.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.trendyol.showcase.showcase.ShowcaseManager;
import com.trendyol.showcase.ui.showcase.HighlightType;
import com.trendyol.showcase.ui.tooltip.ArrowPosition;
import com.trendyol.showcase.ui.tooltip.TextPosition;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button buttonTop = findViewById(R.id.button_top);
        final Button buttonCenter = findViewById(R.id.button_center);
        final Button buttonBottom = findViewById(R.id.button_bottom);
        final Button buttonMultipleView = findViewById(R.id.button_focus_multiple_view);
        final View textView = findViewById(R.id.textView);
        final View imageView = findViewById(R.id.imageView);

        buttonTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShowcaseManager.Builder()
                    .focus(buttonTop)
                    .titleText("Title For Top!")
                    .descriptionText("Simple, short description for top tooltip.")
                    .titleTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent))
                    .descriptionTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary))
                    .backgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark))
                    .imageUrl("https://cdn.dsmcdn.com/Assets/t/y/creative/mobile/InstantDelivery/instant-ty-onboarding.png")
                    .showCloseButton(true)
                    .arrowPosition(ArrowPosition.AUTO)
                    .highlightType(HighlightType.RECTANGLE)
                    .textPosition(TextPosition.START)
                    .windowBackgroundAlpha(255)
                    .titleTextSize(30F)
                    .build()
                    .show(MainActivity.this, 0);
            }
        });

        buttonCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShowcaseManager.Builder()
                    .focus(buttonCenter)
                    .titleText("Title For Center!")
                    .descriptionText("Center is here.")
                    .titleTextColor(ContextCompat.getColor(getBaseContext(), R.color.white))
                    .backgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark))
                    .closeButtonColor(ContextCompat.getColor(getBaseContext(), R.color.white))
                    .showCloseButton(true)
                    .arrowPosition(ArrowPosition.DOWN)
                    .highlightType(HighlightType.CIRCLE)
                    .arrowPercentage(100)
                    .textPosition(TextPosition.CENTER)
                    .build()
                    .show(MainActivity.this, 0);
            }
        });

        buttonBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShowcaseManager.Builder()
                    .focus(buttonBottom)
                    .titleText("Title without a description")
                    .titleTextSize(16)
                    .showCloseButton(true)
                    .arrowResource(android.R.drawable.arrow_down_float)
                    .arrowPosition(ArrowPosition.AUTO)
                    .highlightType(HighlightType.RECTANGLE)
                    .highlightPadding(8F)
                    .textPosition(TextPosition.START)
                    .build()
                    .show(MainActivity.this, 0);
            }
        });

        buttonMultipleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShowcaseManager.Builder()
                    .focus(textView, imageView)
                    .titleText("Multiple View Focus")
                    .titleTextSize(16)
                    .showCloseButton(true)
                    .arrowPosition(ArrowPosition.UP)
                    .highlightType(HighlightType.RECTANGLE)
                    .highlightPadding(8F)
                    .textPosition(TextPosition.START)
                    .build()
                    .show(MainActivity.this, 0);
            }
        });
    }
}
