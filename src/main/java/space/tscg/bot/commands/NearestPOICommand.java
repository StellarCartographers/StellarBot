package space.tscg.bot.commands;

import io.github.readonly.command.SlashCommand;
import io.github.readonly.command.event.SlashCommandEvent;
import io.github.readonly.command.option.Option;

public class NearestPOICommand extends SlashCommand {
	
	public NearestPOICommand() {
	    super("nearest-poi", "Finds the nearest POI to your given location");
		options(
				Option.text("cmdr", "Cmdr name"));
	}

	@Override
	protected void execute(SlashCommandEvent event) {
		
	}
}
