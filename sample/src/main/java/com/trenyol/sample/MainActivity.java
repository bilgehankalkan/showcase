package com.trenyol.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.trenyol.showcase.showcase.ShowcaseManager;
import com.trenyol.showcase.ui.showcase.HighlightType;
import com.trenyol.showcase.ui.tooltip.ArrowPosition;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button1 = findViewById(R.id.button1);
        final Button button2 = findViewById(R.id.button2);
        final Button button3 = findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShowcaseManager.Builder()
                        .view(button1)
                        .titleText("Title about myView")
                        .descriptionText("Little bit info for my lovely myView")
                        .titleTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent))
                        .descriptionTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary))
                        .backgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark))
                        .closeButtonColor(ContextCompat.getColor(getBaseContext(), R.color.white))
                        .showCloseButton(true)
                        .arrowPosition(ArrowPosition.AUTO)
                        .highlightType(HighlightType.CIRCLE)
                        .windowBackgroundAlpha(255)
                        .titleTextSize(30F)
                        .build()
                        .show(MainActivity.this);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShowcaseManager.Builder()
                        .titleText("Title about myView")
                        .descriptionText("Little bit info for my lovely myView")
                        .titleTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary))
                        .descriptionTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary))
                        .showCloseButton(true)
                        .arrowPosition(ArrowPosition.UP)
                        .highlightType(HighlightType.RECTANGLE)
                        .windowBackgroundAlpha(127)
                        .backgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimaryDark))
                        .build()
                        .show(MainActivity.this);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShowcaseManager.Builder()
                        .view(v)
                        .titleText("Title about myView")
                        .descriptionText("Custom aligned arrow for myView")
                        .showCloseButton(true)
                        .arrowPosition(ArrowPosition.UP)
                        .highlightType(HighlightType.CIRCLE)
                        .windowBackgroundAlpha(127)
                        .arrowPercentage(25)
                        .showCloseButton(false)
                        .build()
                        .show(MainActivity.this);
            }
        });
    }
}
