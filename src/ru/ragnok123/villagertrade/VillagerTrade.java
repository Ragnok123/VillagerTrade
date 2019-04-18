package ru.ragnok123.villagertrade;

import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.inventory.BaseInventory;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Random;
import cn.nukkit.Player;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.*;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.entity.data.StringEntityData;
import cn.nukkit.entity.passive.EntityVillager;
import cn.nukkit.plugin.*;
import cn.nukkit.command.*;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.network.protocol.*;

public class VillagerTrade extends PluginBase implements Listener{

	public static VillagerTrade instance;
	public void onEnable() {
		instance = this;
		Server.getInstance().getPluginManager().registerEvents(this, this);
		Entity.registerEntity("TradeVillager",TradeVillager.class);
	}
	
	@EventHandler
	public void EntityDamage(EntityDamageByEntityEvent event) {
		Entity e = event.getEntity();
		if(event instanceof EntityDamageByEntityEvent) {
			if(e instanceof TradeVillager) {
				TradeVillager vlg = (TradeVillager) e;
				Player player = (Player) ((EntityDamageByEntityEvent)event).getDamager();
				UpdateTradePacket pk = new UpdateTradePacket();
				pk.windowId = 2;
				pk.windowType = 15;
				pk.unknownVarInt1 = 0;
				pk.unknownVarInt2 = 0;
				pk.unknownVarInt3 = 0;
				pk.isWilling = true;
				pk.trader = vlg.getId();
				pk.player = player.getId();
				pk.displayName = vlg.getTradeName();
				try {
					pk.offers = NBTIO.write(vlg.getOffers(),ByteOrder.LITTLE_ENDIAN,true);
				} catch (IOException exception){
					
				}
				player.dataPacket(pk);
			}
		}
	}
	
	public CompoundTag getNBT(Player pos, Vector3 vector) {
		CompoundTag nbt = new CompoundTag()
				.putList(new ListTag<DoubleTag>("Pos")
						.add(new DoubleTag("", pos.x))
						.add(new DoubleTag("", pos.y))
						.add(new DoubleTag("", pos.z)))
		        .putList(new ListTag<DoubleTag>("Motion")
		        		.add(new DoubleTag("", vector.x))
		        		.add(new DoubleTag("", vector.y))
		        		.add(new DoubleTag("", vector.z)))
		        .putList(new ListTag<FloatTag>("Rotation")
		        		.add(new FloatTag("", (float) pos.yaw))
		        		.add(new FloatTag("", (float) pos.pitch)));
		return nbt;
	}
	
	
/*	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player)sender;
		switch(cmd.getName()) {
		case "bpos":
			FullChunk chunk = player.getLevel().getChunk((int) player.getX() >>4, (int) player.getY() >> 4);
			CompoundTag nbt = getNBT(player,new Vector3(0,0,0));
			TradeVillager vlg = new TradeVillager(chunk,nbt);
			vlg.setTradeName("Test");
			vlg.registerTrade(Item.get(Item.STONE),Item.get(Item.EMERALD,0,3));
			vlg.registerTrade(Item.get(Item.GOLD_INGOT,0,1),Item.get(Item.EMERALD),Item.get(Item.FEATHER));
			vlg.spawnToAll();
			break;
		}
		return false;
	}
	*/
	
	
}
