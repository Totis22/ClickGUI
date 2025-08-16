package totis.modules.settings;

@SuppressWarnings("all")
public class KeybindSetting extends Setting {

    public int key;

    public KeybindSetting(int key) {
        this.name = "Keybind";
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
