package com.jeremy94.portfolio_diary_v2.CalendarDecorate;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Date;

public class oneDayDecorator implements DayViewDecorator {

    private CalendarDay date;

    public oneDayDecorator() {
        date = CalendarDay.today();
    }

    public boolean shouldDecorate(CalendarDay day) {
        return date != null && day.equals(date);
    }

    public void decorate(DayViewFacade view) {
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new RelativeSizeSpan(1.4f));
        view.addSpan(new ForegroundColorSpan(Color.BLACK));
    }

    public void setDate(Date date) {
        this.date = CalendarDay.from(date);
    }
}
