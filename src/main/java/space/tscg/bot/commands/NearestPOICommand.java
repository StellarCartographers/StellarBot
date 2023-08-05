package space.tscg.bot.commands;

import io.github.readonly.command.SlashCommand;
import io.github.readonly.command.event.SlashCommandEvent;
import io.github.readonly.command.option.Option;

public class NearestPOICommand extends SlashCommand {
	
	public NearestPOICommand() {
		this.name("nearest-poi");
		this.description("Finds the nearest POI to your given location");
		this.setOptions(
				Option.text("cmdr", "Cmdr name"));
	}

	@Override
	protected void execute(SlashCommandEvent event) {
		
	}
}
