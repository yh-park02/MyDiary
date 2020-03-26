package com.jeremy94.portfolio_diary_v2;

import java.util.List;

public class CurrentData {
    public class Main {
        public Double temp;
        public Integer temp_min;
        public Integer temp_max;
    }
    public class Weather {
        public String icon;
    }
    public List<Weather> weather = null;

    public Main main;
}
