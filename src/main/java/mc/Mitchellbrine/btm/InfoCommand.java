package mc.Mitchellbrine.btm;

import mc.Mitchellbrine.btm.components.Booth;
import mc.Mitchellbrine.btm.components.Panel;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchellbrine on 2015.
 */
public class InfoCommand implements ICommand {

	public List aliases;

	public InfoCommand()
	{
		this.aliases = new ArrayList();
		this.aliases.add("i");
	}

	@Override
	public String getCommandName()
	{
		return "info";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "/info [player/panel/booth/reload] [booth name/panel name]";
	}

	@Override
	public List getCommandAliases()
	{
		return this.aliases;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		if (ConventionInfoMod.convention != null) {
			if (icommandsender instanceof EntityPlayer) {
				if (astring.length == 0) {
					general(icommandsender);
				} else {
					if (astring.length == 1) {
						if (astring[0].equals("reload")) {
							if (MinecraftServer.getServer().getConfigurationManager().func_152596_g(((EntityPlayer) icommandsender).getGameProfile())) {
								reload(icommandsender);
							} else {
								throw new WrongUsageException("You do not have permission to use this command!");
							}
						} else {
							player(icommandsender, astring[0]);
						}
					} else if (astring.length >= 2) {
						if (astring[0].equals("panel")) {
							panel(icommandsender, StringUtils.joinArray(astring, 1));
						} else if (astring[0].equals("booth")) {
							booth(icommandsender, StringUtils.joinArray(astring,1));
						} else {
							throw new WrongUsageException("Invalid arguments!");
						}
					}
				}
			} else {
				reload(icommandsender);
			}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	{
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender,String[] astring) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i)
	{
		return false;
	}

	@Override
	public int compareTo(Object o)
	{
		return 0;
	}

	public void reload(ICommandSender sender) {
		ConventionInfoMod.readJSON(ConventionInfoMod.conventionFile.getPath());
		sender.addChatMessage(new ChatComponentText("Reload complete!"));
	}

	public void player(ICommandSender sender, String name) {
		Convention con = ConventionInfoMod.convention;
		sender.addChatMessage(new ChatComponentText(con.eventName + ":").setChatStyle(con.titleColor));
		sender.addChatMessage(new ChatComponentText("- ~ -").setChatStyle(con.headerFooterColor));
		sender.addChatMessage(new ChatComponentText("IGN: " + name).setChatStyle(con.nameColor));
		if (con.eventHosts.contains(name)) {
			sender.addChatMessage(new ChatComponentText("[EVENT HOST]").setChatStyle(con.hostColor));
		}

		if (StringUtils.getPanels(con,name) != null) {
			sender.addChatMessage(new ChatComponentText("- ").setChatStyle(con.headerFooterColor));
			sender.addChatMessage(new ChatComponentText("Panels:").setChatStyle(con.panelColor));
			for (Panel panel : con.panels) {
				if (StringUtils.arrayContains(panel.participants, name)) {
					IChatComponent component = new ChatComponentText("- " + panel.name).setChatStyle(con.panelColor);
					component.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/info panel " + panel.name));
					sender.addChatMessage(component);
				}
			}
		}

		if (StringUtils.getBooths(con,name) != null) {
			sender.addChatMessage(new ChatComponentText("- ").setChatStyle(con.headerFooterColor));
			sender.addChatMessage(new ChatComponentText("Booths:").setChatStyle(con.boothColor));
			for (Booth booth : con.booths) {
				if (booth.host.contains(name)) {
					IChatComponent component = new ChatComponentText("- " + booth.name).setChatStyle(con.boothColor.createDeepCopy());
					component.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/info booth " + booth.name));
					sender.addChatMessage(component);
				}
			}
		}
		sender.addChatMessage(new ChatComponentTranslation("- ~ -").setChatStyle(con.headerFooterColor));
	}

	public void general(ICommandSender sender) {
		Convention con = ConventionInfoMod.convention;
		sender.addChatMessage(new ChatComponentText("- ~ -").setChatStyle(con.headerFooterColor));

		sender.addChatMessage(new ChatComponentText(con.eventName).setChatStyle(con.titleColor));
		sender.addChatMessage(new ChatComponentText(con.description).setChatStyle(con.titleColor));
		sender.addChatMessage(new ChatComponentText(con.startTime + " to " + con.endTime).setChatStyle(con.titleColor));
		sender.addChatMessage(new ChatComponentText(""));
		sender.addChatMessage(new ChatComponentText("Hosts: " + con.eventHosts).setChatStyle(con.hostColor));
		sender.addChatMessage(new ChatComponentText("-").setChatStyle(con.headerFooterColor));
		sender.addChatMessage(new ChatComponentText("Panels: ").setChatStyle(con.panelColor));
		for (Panel panel : con.panels) {
			IChatComponent component = new ChatComponentText("- " + panel.name).setChatStyle(con.panelColor.createDeepCopy());
			component.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/info panel " + panel.name));
			//component.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ChatComponentText("")))
			sender.addChatMessage(component);
		}
		sender.addChatMessage(new ChatComponentText("-").setChatStyle(con.headerFooterColor));
		sender.addChatMessage(new ChatComponentText("Booths: ").setChatStyle(con.boothColor));
		for (Booth booth : con.booths) {
			IChatComponent component = new ChatComponentText("- " + booth.name).setChatStyle(con.boothColor.createDeepCopy());
			component.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/info booth " + booth.name));
			sender.addChatMessage(component);
		}
		sender.addChatMessage(new ChatComponentText("- ~ -").setChatStyle(con.headerFooterColor));
	}

	public void panel(ICommandSender sender, String name) {
		if (StringUtils.getPanelByName(name) != null) {
			Convention con = ConventionInfoMod.convention;
			Panel panel = StringUtils.getPanelByName(name);
			sender.addChatMessage(new ChatComponentText(con.eventName + ":").setChatStyle(con.titleColor));
			sender.addChatMessage(new ChatComponentText("- ~ -").setChatStyle(con.headerFooterColor));
			sender.addChatMessage(new ChatComponentText("\"" + panel.name + "\"").setChatStyle(con.panelColor));
			sender.addChatMessage(new ChatComponentText(panel.description).setChatStyle(con.panelColor));
			sender.addChatMessage(new ChatComponentText(""));
			sender.addChatMessage(new ChatComponentText("Time: " + panel.startTime + "-" + panel.endTime + " " + panel.day).setChatStyle(con.panelColor));
			sender.addChatMessage(new ChatComponentText("Panelists:").setChatStyle(con.panelColor));
			for (String names : panel.participants) {
				IChatComponent panelist = new ChatComponentText("- " + names).setChatStyle(con.panelColor.createDeepCopy());
				panelist.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/info " + names));
				sender.addChatMessage(panelist);
			}
			sender.addChatMessage(new ChatComponentText("- ~ -").setChatStyle(con.headerFooterColor));
		} else {
			throw new WrongUsageException("Panel " + name + " doesn't exist");
		}
	}

	public void booth(ICommandSender sender, String name) {
		if (StringUtils.getBooothByName(name) != null) {
			Convention con = ConventionInfoMod.convention;
			Booth booth = StringUtils.getBooothByName(name);
			sender.addChatMessage(new ChatComponentText(con.eventName + ":").setChatStyle(con.titleColor));
			sender.addChatMessage(new ChatComponentText("- ~ -").setChatStyle(con.headerFooterColor));
			sender.addChatMessage(new ChatComponentText("\"" + booth.name + "\"").setChatStyle(con.boothColor));
			sender.addChatMessage(new ChatComponentText(booth.description).setChatStyle(con.boothColor));
			sender.addChatMessage(new ChatComponentText(""));
			sender.addChatMessage(new ChatComponentText("URL: " + booth.url).setChatStyle(con.boothColor));
			IChatComponent component = new ChatComponentText("Owner(s): " + booth.host).setChatStyle(con.boothColor.createDeepCopy());
			component.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/info " + booth.host));
			sender.addChatMessage(component);
			sender.addChatMessage(new ChatComponentText("- ~ -").setChatStyle(con.headerFooterColor));
		} else {
			throw new WrongUsageException("Booth " + name + " doesn't exist");
		}
	}

}
