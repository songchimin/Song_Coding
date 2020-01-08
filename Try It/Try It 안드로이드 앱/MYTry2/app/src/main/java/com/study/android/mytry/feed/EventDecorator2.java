package com.study.android.mytry.feed;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.study.android.mytry.R;

import java.util.Collection;
import java.util.HashSet;

/**
 * Decorate several days with a dot
 */
public class EventDecorator2 implements DayViewDecorator {

    private final Drawable drawable;
    private int color;
    private HashSet<CalendarDay> dates;

    public EventDecorator2(int color, Collection<CalendarDay> dates, Context context) {
        drawable = context.getResources().getDrawable(R.drawable.selected_bg);
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
       // view.setSelectionDrawable(drawable);
        view.addSpan(new DotSpan(5, color)); // 날자밑에 점
    }
}
