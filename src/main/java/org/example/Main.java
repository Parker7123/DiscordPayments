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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws NoSuchFieldException, SQLException, ClassNotFoundException, IOException {
        var database = new MainDatabase();
        String appConfigPath = Thread.currentThread().getContextClassLoader().getResource("token.properties").getPath();
        System.out.println(appConfigPath);
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));
        DiscordPaymentBot discordPaymentBot =
                new DiscordPaymentBot(appProps.getProperty("token"), new BuyButtonDmProcessor());

        discordPaymentBot.sendPaymentMessage("855424831745818685","buy the bot");
    }
}