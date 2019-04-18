package ru.ragnok123.villagertrade;

import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.entity.passive.EntityVillager;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;

public class TradeVillager extends EntityVillager{

	private CompoundTag offers = new CompoundTag();
	private String tradeDisplay = "Trade";
	
	public TradeVillager(FullChunk chunk, CompoundTag nbt) {
		super(chunk, nbt);
	}
	public void setTradeName(String name) {
		tradeDisplay = name;
	}
	
	public String getTradeName() {
		return tradeDisplay;
	}
	
	public void registerTrade(Item sell, Item buyA) {
		registerTrade(sell,buyA,Item.get(0));
	}
	public void registerTrade(Item sell, Item buyA, Item buyB) {
		registerTrade((byte)0,999,0,sell,buyA,buyB);
	}
	
	public void registerTrade(byte reward, int maxUses, int uses, Item sell, Item buyA, Item buyB) {
		CompoundTag tag;
		if(this.offers.contains("Recipes")) {
			tag = this.offers.getCompound("Recipes");
		} else {
			tag = new CompoundTag().putList(new ListTag<CompoundTag>("Recipes"));
		}
		CompoundTag nbt = new CompoundTag()
				.putCompound("buyA", putItemHelper(buyA,-1,"buyA"))
				.putCompound("buyB", putItemHelper(buyB,-1,"buyB"))
				.putInt("maxUses",maxUses)
				.putByte("rewardExp",0)
				.putCompound("sell", putItemHelper(sell,-1,"sell"))
				.putInt("uses",uses);
		tag.getList("Recipes",CompoundTag.class).add(nbt);
		this.offers.putCompound("Recipes",tag);
	}
	
	public CompoundTag getOffers() {
		if(this.offers.contains("Recipes")) {
			return this.offers.getCompound("Recipes");
		}
		return null;
	}
	
	private static CompoundTag putItemHelper(Item item, Integer slot, String tagName) {
        CompoundTag tag = new CompoundTag(tagName)
                .putShort("id", item.getId())
                .putByte("Count", item.getCount())
                .putShort("Damage", item.getDamage());
        if (slot != null) {
            tag.putByte("Slot", slot);
        }
        if (item.hasCompoundTag()) {
            tag.putCompound("tag", item.getNamedTag());
        }
        return tag;
    }

}
