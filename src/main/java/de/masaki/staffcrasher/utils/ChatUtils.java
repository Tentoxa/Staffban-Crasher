package de.masaki.staffcrasher.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class ChatUtils {
    public static String PREFIX = "§8[§6§lSTAFFCRASH§8] §r§7";

    public static void sendMessage(String text){
        IChatComponent component = new ChatComponentText(PREFIX + text);
        ChatStyle textStyle = new ChatStyle();
        component.setChatStyle(textStyle);

        try {
            Minecraft.getMinecraft().thePlayer.addChatMessage(component);
        } catch(NullPointerException e) {

        }
    }
}
