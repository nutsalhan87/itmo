package timeOfDay;

public enum DayConditions {
    SUNRISE {
        @Override
        public String toString() {
            return "Рассвет";
        }
    },
    EVENING {
        @Override
        public String toString() {
            return "Вечер";
        }
    },
    NIGHT {
        @Override
        public String toString() {
            return "Ночь";
        }
    },
    AFTERNOON {
        @Override
        public String toString() {
            return "Полдень";
        }
    };

    public abstract String toString();
}
