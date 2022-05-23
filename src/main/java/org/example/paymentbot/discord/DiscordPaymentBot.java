package org.example.paymentbot.discord;

import lombok.Getter;
import lombok.Setter;
import org.example.paymentbot.database.Domain;
import org.example.paymentbot.database.MainDatabase;
import org.example.paymentbot.database.User;
import org.example.paymentbot.discord.constants.DiscordConstants;
import org.h2.engine.Database;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import org.javacord.api.interaction.callback.InteractionCallbackDataFlag;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

import java.util.Arrays;
import java.util.Optional;

@Setter
@Getter
public class DiscordPaymentBot {
    private final DiscordApi api;
    private final MainDatabase database;

    public DiscordPaymentBot(String token, MessageComponentCreateListener messageComponentCreateListener) throws ClassNotFoundException {
        this.api = new DiscordApiBuilder()
                .addMessageComponentCreateListener(messageComponentCreateListener)
                .setToken(token)
                .login()
                .join();
        addSlashCommands();
        addSlashCommandListeners();
        database = new MainDatabase();
    }

    public void sendPaymentMessage(String channelId, String text) throws NoSuchFieldException {
        Optional<TextChannel> opt = api.getTextChannelById(channelId);
        if (!opt.isPresent()) {
            throw new NoSuchFieldException("channel not found");
        }
        TextChannel channel = opt.get();
        deletePrevMessages(channel, 100);
        new MessageBuilder()
                .setContent(text)
                .addComponents(
                        ActionRow.of(Button.success("buyId", "Buy the bot")))
                .send(channel)
                .exceptionally(ExceptionLogger.get());
    }

    public void deletePrevMessages(TextChannel channel, int limit) {
        channel.getMessages(limit).thenAccept(messages -> {
            messages.stream()
                    .filter(message -> message.getAuthor().isYourself())
                    .forEach(message -> {
                        message.delete().exceptionally(ExceptionLogger.get());
                    });
        }).exceptionally(ExceptionLogger.get());
    }

    private void addSlashCommands() {
        api.bulkOverwriteGlobalApplicationCommands(Arrays.asList(
                new SlashCommandBuilder().setName(DiscordConstants.stores.name())
                        .setDescription("show stores")
                        .setDefaultPermission(false),
                new SlashCommandBuilder().setName(DiscordConstants.products.name())
                        .setDescription("show products in store")
                        .setDefaultPermission(false)
                        .addOption(SlashCommandOption.create(
                                SlashCommandOptionType.STRING, "store", "store id")
                        ),
                new SlashCommandBuilder().setName(DiscordConstants.set_domain.name()).setDescription("set store domain")
                        .setDefaultPermission(false)
                        .addOption(SlashCommandOption.create(
                                SlashCommandOptionType.STRING, "domain", "domain url")
                        ))).join();
    }

    private void addSlashCommandListeners() {
        api.addSlashCommandCreateListener(event -> {
            var interaction = event.getSlashCommandInteraction();
            switch (DiscordConstants.valueOf(interaction.getCommandName())) {
                case set_domain:
                    String domain = interaction.getOptionStringRepresentationValueByIndex(0).orElse("");
                    database.getDomainsDadabase().save(new Domain(domain, 0));
                    var domains = database.getDomainsDadabase().get(domain);
                    if (domains.size() != 1) {
                        interaction.createImmediateResponder().setContent("domain not found").respond();
                        return;
                    }
                    var user = new User(interaction.getUser().getId(), domains.get(0).getId(), interaction.getUser().getName());
                    database.getUsersDatabase().save(user);
                    System.out.println(user);
                    var opt = database.getUsersDatabase().get(user.getId());
                    if (opt.isEmpty()) {
                        interaction.createImmediateResponder().setContent("user not found").respond();
                        return;
                    }
                    String response = "Domain Set Successfully\n" +
                            "User: " + opt.get().getName()+ "\n" +
                            "Domain: " + domains.get(0).getApiUrl();
                    interaction.createImmediateResponder()
                            .setFlags(InteractionCallbackDataFlag.EPHEMERAL)
                            .setContent(response)
                            .respond();
                    break;
                case stores:

                    break;
                case products:

                    break;
            }
        });
    }
}
