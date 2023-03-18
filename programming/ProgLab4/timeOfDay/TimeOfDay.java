package timeOfDay;

import java.util.Objects;

public class TimeOfDay {
    private DayConditions condition;

    public TimeOfDay() {
        condition = DayConditions.AFTERNOON;
    }

    public TimeOfDay(DayConditions dayCondition) {
        condition = dayCondition;
    }

    public void setCondition(DayConditions dayCondition) {
        System.out.println("Наступил " + dayCondition.toString());
        condition = dayCondition;
    }

    public DayConditions getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return condition.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeOfDay timeOfDay = (TimeOfDay) o;
        return condition == timeOfDay.condition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition);
    }
}
