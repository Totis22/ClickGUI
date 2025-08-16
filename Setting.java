package totis.modules.settings;

import java.util.function.Supplier;

@SuppressWarnings("all")
public class Setting {

    public String name;
    public boolean focused;

    public Supplier<Boolean> visibility = () -> true;
}
