package com.example.ternaksmart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class LineChartView extends View {
    private List<Double> dataPoints = new ArrayList<>();
    private Paint linePaint;
    private Paint pointPaint;
    private Paint gridPaint;
    private Path path;

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#1B5E20"));
        linePaint.setStrokeWidth(5f);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setAntiAlias(true);

        pointPaint = new Paint();
        pointPaint.setColor(Color.parseColor("#1B5E20"));
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setAntiAlias(true);

        gridPaint = new Paint();
        gridPaint.setColor(Color.LTGRAY);
        gridPaint.setStrokeWidth(1f);
        gridPaint.setStyle(Paint.Style.STROKE);

        path = new Path();
    }

    public void setData(List<Double> data) {
        this.dataPoints = data;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dataPoints == null || dataPoints.size() < 2) return;

        float width = getWidth();
        float height = getHeight();
        float padding = 50f;

        float maxVal = 0;
        for (Double d : dataPoints) {
            if (d > maxVal) maxVal = (float) d.doubleValue();
        }
        if (maxVal == 0) maxVal = 1;

        float xStep = (width - 2 * padding) / (dataPoints.size() - 1);
        float yScale = (height - 2 * padding) / maxVal;

        path.reset();
        for (int i = 0; i < dataPoints.size(); i++) {
            float x = padding + i * xStep;
            float y = height - padding - (float) (dataPoints.get(i) * yScale);

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
            canvas.drawCircle(x, y, 8f, pointPaint);
        }
        canvas.drawPath(path, linePaint);
    }
}