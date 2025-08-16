package totis;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import totis.eventmanager.EventManager;
import totis.events.Event;
import totis.events.EventChat;
import totis.events.EventKey;
import totis.gui.notification.NotificationManager;
import totis.hud.IRenderer;
import totis.modules.Module;
import totis.modules.player.AntiAFK;
import totis.modules.player.Killsults;
import totis.modules.render.*;
import totis.modules.visual.*;
import totis.utils.font.FontHelper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("all")
public class Client {

    // To set a customPlayerSessionName (A player name): net.minecraft.client.main

    private static final Minecraft mc = Minecraft.getMinecraft();
    private int groundTicks;
    private int offGroundTicks;

    public static final String DIRECTORY = "KetamineClient";
    public static final String assetsDir = "vape";

    public FontHelper fontHelper;

    public static Client INSTANCE = new Client();
    public static HUD hud = new HUD();
    public FileManager fileManager = new FileManager();
    private NotificationManager notificationManager = new NotificationManager();

    public static float GAMMA = 0;
    public int scaffoldInstance = 7;
    public static Color VAPE_COLOR = new Color(66, 255, 143);
    public static String name = "Ketamine", version = "1";

    public static String formattedName = name + " v" + version;
    public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();

    // Some modules were taken from https://github.com/SerenityEnterprises/SerenityCE/tree/b54889f1b74c8810dec9af50699335daa7a015e4/src/main/java/host/serenity/serenity/modules
    public void start() {
        Display.setTitle(name);
        fontHelper = new FontHelper();
        EventManager.register(this);
        EventManager.register(hud);

        // Combat


        // Movement


        // Player
        modules.add(new Killsults());
        modules.add(new AntiAFK());


        // Render
        modules.add(new Skeletal());
        modules.add(new ChinaHat());
        modules.add(new TntTimer());
        modules.add(new StorageESP());
        modules.add(new Tracers());
        modules.add(new WaveyCape());
        modules.add(new CustomModel());
        modules.add(new totis.modules.render.ArrayList());
        modules.add(new Notifications());


        //Visual
        modules.add(new TabGUI());
        modules.add(new ClientTheme());
        modules.add(new PostProcessing());
        modules.add(new TargetHUD());
        modules.add(new Radar());
        modules.add(new ModernScoreboard());
        modules.add(new GlowESP());
        modules.add(new JumpCircles());
        modules.add(new AttackEffect());



        fontHelper.init();
        modules.forEach(module -> {
            EventManager.register(module);
        });

        modules.forEach(module -> {
            if(module instanceof IRenderer) {
                IRenderer mod = (IRenderer) module;
                hud.register(mod);
            }
        });

        fileManager.init();
    }

    // This goes to "Minecraft.shutdown();", before "this.running = false;"
    public void stop() {
        Client.INSTANCE.fileManager.saveCategoryPos();
        Client.INSTANCE.fileManager.saveMods();
        Client.INSTANCE.fileManager.saveBinds();
    }

    public void tabGUITick(Event e) {
        for(Module module : modules) {
            if(module.name.equals("TabGUI")) module.tick(e);
        }
    }
    public void tick(Event e) {
        if(e instanceof EventChat) {
            //commandManager.handleChat((EventChat)e);
        }
        for(Module module : modules) {
            if(!module.isEnabled()) continue;
            module.tick(e);
        }
    }

    public void keyPress(int key) {
        Client.INSTANCE.tick(new EventKey(key));

        if(key == Keyboard.KEY_G) {
            hud.openConfigScreen();
        }

        for(Module module : modules) {
            if(module.getKey() == key) module.toggle();
        }
    }
    public List<Module> getModulesByCategory(Module.Category c) {
        List<Module> modules = new ArrayList<>();
        for(Module module : Client.modules) {
            if(module.category == c) modules.add(module);
        }
        return modules;
    }
    public <T extends Module> Module getModuleByClass(Class<T> moduleClass) {
        for(Module module : Client.modules) {
            if(module.getClass() == moduleClass) return module;
        }
        return null;
    }
    public Module getModuleByName(String name) {
        for(Module module : Client.modules) {
            if(module.name.equalsIgnoreCase(name)) return module;
        }
        return null;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public void chatInfoMessage(String message) {
        message = "§9" + name + "§r>> " + message;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
    public void chatErrorMessage(String message) {
        message = "§9" + name + "§r>> §c" + message;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
    }
    public void print(boolean prefix, String message) {
        if (mc.thePlayer != null) {
            if (prefix) message = "§7[§d§lVAPE§r§7] " + message;
            mc.thePlayer.addChatMessage(new ChatComponentText(message));
        }
    }

    public void sendCommand(String command) {
        if (mc.thePlayer != null) {
            mc.thePlayer.sendChatMessage(command);
        }
    }
}
