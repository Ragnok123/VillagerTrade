package ru.ragnok123.nametag;

import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.StringEntityData;
import cn.nukkit.Player;

public class Nametag {
	
	public Player player;
	public String string;
	
	public Nametag(Player player, String string) {
		this.player = player;
		this.string = string;
		changeTag();
	}
	
	public void changeTag() {
		if(GTNametag.tags.containsKey(player)) {
			GTNametag.tags.remove(player);
			GTNametag.tags.put(player, string);
		} else {
			GTNametag.tags.put(player, string);
		}
		((Entity)player).setDataProperty(new StringEntityData(Entity.DATA_SCORE_TAG, player.getName()));
	}

}
