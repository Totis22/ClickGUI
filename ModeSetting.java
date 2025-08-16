package totis.modules.settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("all")
public class ModeSetting extends Setting {

    public int index;
    public List<String> modes;

    public ModeSetting(String name, Supplier<Boolean> visibility, String defaultMode, String ... modes) {
        this(name, defaultMode, modes);
        this.visibility = visibility;
    }

    public ModeSetting(String name, String displayName, Supplier<Boolean> visibility, String defaultMode, String ... modes) {
        this(name, defaultMode, modes);
        this.visibility = visibility;
    }
    public ModeSetting(String name, String defaultMode, String... modes) {
        this.name = name;
        this.modes = Arrays.asList(modes);
        this.index = this.modes.indexOf(defaultMode);
    }
    public ModeSetting(String name, String defaultMode, ArrayList modes) {
        this.name = name;
        this.modes = modes;
        this.index = this.modes.indexOf(defaultMode);
    }

    public String getMode() {
        return this.modes.get(index);
    }
    public boolean is(String mode) {
        return index == modes.indexOf(mode);
    }
    public void setMode(String mode) {
        if(modes.contains(mode)) {
            index = modes.indexOf(mode);
        }
    }
    public void cycle() {
        if(index < modes.size() - 1) {
            index++;
        } else {
            index = 0;
        }
    }
}