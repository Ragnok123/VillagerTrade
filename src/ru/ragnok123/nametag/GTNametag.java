package ru.ragnok123.nametag;

import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.server.DataPacketSendEvent;

import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.StringEntityData;
import cn.nukkit.plugin.*;
import cn.nukkit.command.*;
import cn.nukkit.Server;
import cn.nukkit.network.protocol.*;


public class GTNametag extends PluginBase implements Listener{

	
	public static HashMap<Player, String> tags = new HashMap<Player, String>();
	
	public void onEnable() {
		Server.getInstance().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void handlePacket(DataPacketSendEvent event) {
		DataPacket packet = event.getPacket();
		if(packet instanceof AddPlayerPacket) {
			AddPlayerPacket pk = (AddPlayerPacket) packet;
			Player player = Server.getInstance().getPlayerExact(pk.username);
			if(player instanceof Player) {
				pk.username = tags.get(player);
			}
		}
	}
	
	
	@EventHandler
	public void handleJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		new Nametag(player, player.getName());
	}
	

	
/*	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player)sender;
		switch(cmd.getName()){
		case "gtg":
			new Nametag(player, args[0]);
		}
		return false;
	}
	*/
	
	
}
