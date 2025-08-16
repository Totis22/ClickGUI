package totis.modules.settings;

import java.util.function.Supplier;

@SuppressWarnings("all")
public class BooleanSetting extends Setting {

    private boolean enabled;

    public BooleanSetting(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public BooleanSetting(String name, Supplier<Boolean> visibility, boolean defaultState) {
        this(name, defaultState);
        this.visibility = visibility;
    }

    public BooleanSetting(String name, String displayName, Supplier<Boolean> visibility, boolean defaultState) {
        this(name, defaultState);
        this.visibility = visibility;
    }



    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggle() {
        enabled = !enabled;
    }
}
