package org.example;

import org.example.paymentbot.database.MainDatabase;
import org.example.paymentbot.discord.BuyButtonDmProcessor;
import org.example.paymentbot.discord.DiscordPaymentBot;
import org.h2.jdbcx.JdbcConnectionPool;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.interaction.MessageComponentInteraction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, SQLException, ClassNotFoundException {
        var database = new MainDatabase();
        DiscordPaymentBot discordPaymentBot =
                new DiscordPaymentBot("TOKEN",
                        new BuyButtonDmProcessor());

        discordPaymentBot.sendPaymentMessage("855424831745818685","buy the bot");
    }
}