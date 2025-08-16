package totis.modules;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import totis.Client;
import totis.eventmanager.impl.Shader2DEvent;
import totis.events.Event;
import totis.gui.notification.NotificationType;
import totis.modules.settings.KeybindSetting;
import totis.modules.settings.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("all")
public class Module {

    public Minecraft mc = Minecraft.getMinecraft();
    public boolean toggled;
    public boolean expanded;
    public String name;
    public KeybindSetting key = new KeybindSetting(0);
    public int index;
    public Category category;
    public List<Setting> settings = new ArrayList<>();

    public boolean hasSettings = false;

    public Module(String name, int key, Category category) {
        //EventManager.register(this);
        this.name = name;
        this.key.key = key;
        this.category = category;
        this.addSettings(this.key);
        setup();
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
        this.settings.sort(Comparator.comparingInt(s -> s == this.key ? 1 : 0));
    }
    public void removeSettings(Setting... settings) {
        this.settings.removeAll(Arrays.asList(settings));
        this.settings.sort(Comparator.comparingInt(s -> s == this.key ? 1 : 0));
    }
    public void toggle() {
        toggled = !toggled;

        if(toggled) {
            onEnable();
            Client.INSTANCE.getNotificationManager().post(NotificationType.OKAY, "Module toggled", name + " was " + ChatFormatting.GREEN + "enabled");
        } else {
            onDisable();
            Client.INSTANCE.getNotificationManager().post(NotificationType.WARNING, "Module toggled", name + " was " + ChatFormatting.RED + "disabled");
        }
    }

    public void onShader(Shader2DEvent event) {

    }

    public boolean isEnabled() {
        return toggled;
    }
    public void setEnabled(boolean e) {
        this.toggled = e;
    }
    public int getKey() {
        return key.key;
    }
    public void setKey(int key) {
        this.key.key = key;
    }
    public void onEnable() {

    }
    public void onDisable() {

    }
    public void tick(Event e) {

    }
    public void setup() {

    }
    public void setCategory(Category cat) {
        this.category = cat;
    }
    public Category getCategory() {
        return this.category;
    }
    public void enableOnStartup() {
        toggled = true;
        try {
            toggle();
            onEnable();
        } catch(Exception e) {}
    }



    public enum Category {
        PLAYER("player"), COMBAT("combat"), MOVEMENT("movement"), RENDER("render"), VISUAL("visual"), EXPLOIT("exploit");

        public String name;
        public int moduleIndex;

        Category(String name) {
            this.name = name;
        }
    }
}
