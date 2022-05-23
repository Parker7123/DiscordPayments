package org.example.paymentbot.discord;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.interaction.callback.InteractionCallbackDataFlag;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BuyButtonDmProcessor implements MessageComponentCreateListener {
    private final LocksMap locksMap = new LocksMap();
    @Override
    public void onComponentCreate(MessageComponentCreateEvent event) {
        MessageComponentInteraction messageComponentInteraction = event.getMessageComponentInteraction();
        String customId = messageComponentInteraction.getCustomId();
        String userName = messageComponentInteraction.getUser().getIdAsString();
        Object lock = locksMap.get(userName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        synchronized (lock) {
            if (customId.equals("buyId")) {
                // messageComponentInteraction.getUser().sendMessage("Hi!");
                messageComponentInteraction.createImmediateResponder()
                        .setContent("Here's your USDT address")
                        .setFlags(InteractionCallbackDataFlag.EPHEMERAL)
                        .respond()
                        .exceptionally(ExceptionLogger.get());
            }
        }
    }
}
