package com.study.android.mytry.feed;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.study.android.mytry.R;

import java.util.Collection;
import java.util.Date;

/**
 * Decorate a day by making the text big and bold
 */
public class OneDayDecorator2 implements DayViewDecorator {

    private CalendarDay date;
    private final Drawable drawable;

    public OneDayDecorator2(int color, Collection<CalendarDay> dates, Context context) {
        date = CalendarDay.today();
        drawable = context.getResources().getDrawable(R.drawable.selected_bg);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }

    /**
     * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
     */
    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
