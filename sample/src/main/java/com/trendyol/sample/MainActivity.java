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

        buttonTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShowcaseManager.Builder()
                    .view(buttonTop)
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
                    .view(buttonCenter)
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
                    .view(buttonBottom)
                    .descriptionText("Some kind of description.")
                    .titleTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent))
                    .showCloseButton(true)
                    .arrowPosition(ArrowPosition.AUTO)
                    .highlightType(HighlightType.RECTANGLE)
                    .descriptionTextSize(21)
                    .highlightPadding(8F)
                    .textPosition(TextPosition.END)
                    .build()
                    .show(MainActivity.this, 0);
            }
        });
    }
}
