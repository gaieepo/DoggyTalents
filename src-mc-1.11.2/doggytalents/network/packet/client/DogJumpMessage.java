package doggytalents.network.packet.client;

import doggytalents.entity.EntityDog;
import doggytalents.network.AbstractMessage.AbstractServerMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;

public class DogJumpMessage extends AbstractServerMessage {
	
	public int entityId;
	
	public DogJumpMessage() {}
    public DogJumpMessage(int entityId) {
        this.entityId = entityId;
    }
    
	@Override
	public void read(PacketBuffer buffer) {
		this.entityId = buffer.readInt();
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeInt(this.entityId);
	}
	
	@Override
	public void process(EntityPlayer player, Side side) {
		Entity target = player.world.getEntityByID(this.entityId);
        if(!(target instanceof EntityDog))
        	return;
        FMLLog.info("Recive");
        EntityDog dog = (EntityDog)target;
		if(dog.onGround) {
			
			//TODO
			dog.setJumping(true);
			  FMLLog.info("On ground");

			dog.motionY = 2F * dog.talents.getLevel("wolfmount") * 0.1F;
			  dog.motionY = 2F;
			if(dog.isPotionActive(MobEffects.JUMP_BOOST))
				dog.motionY += (double)((float)(dog.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
			dog.isAirBorne = true;
		}
		else if(dog.isInWater() && dog.talents.getLevel("swimmerdog") > 0) {
			dog.motionY = 0.2F;
		}
	}
}