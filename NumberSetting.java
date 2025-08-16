package totis.modules.settings;

import java.util.function.Supplier;

@SuppressWarnings("all")
public class NumberSetting extends Setting {

    public double value, min, max, increment;

    public NumberSetting(String name, double value, double min, double max, double increment) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    public NumberSetting(String name, double value, double min, double max) {
        this(name, value, min, max, 1);
    }

    public NumberSetting(String name, Supplier<Boolean> visibility, double value, double min, double max, double increment) {
        this(name, value, min, max, increment);
        this.visibility = visibility;
    }

    public NumberSetting(String name, String displayName, Supplier<Boolean> visibility, double value, double min, double max, double increment) {
        this(name, value, min, max, increment);
        this.visibility = visibility;
    }

    public double getValue() {
        return value;
    }
    public float getValueF() {
        return (float) value;
    }

    public void setValue(double value) {
        double precision = 1 / increment;
        this.value = Math.round(Math.max(min, Math.min(max, value)) * precision) / precision;
    }
    public void increment(boolean positive) {
        setValue(getValue() + (positive ? 1 : -1) * increment);
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getIncrement() {
        return increment;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }
}