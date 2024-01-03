package net.tickmc.megizen.bukkit.objects;

import com.denizenscript.denizen.objects.EntityFormObject;
import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizencore.objects.Adjustable;
import com.denizenscript.denizencore.objects.Fetchable;
import com.denizenscript.denizencore.objects.Mechanism;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.objects.core.ColorTag;
import com.denizenscript.denizencore.objects.core.ElementTag;
import com.denizenscript.denizencore.objects.core.ListTag;
import com.denizenscript.denizencore.tags.Attribute;
import com.denizenscript.denizencore.tags.ObjectTagProcessor;
import com.denizenscript.denizencore.tags.TagContext;
import com.denizenscript.denizencore.utilities.debugging.Debug;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Color;

import javax.annotation.Nullable;
import java.util.UUID;

public class MegActiveModelTag implements ObjectTag, Adjustable {

    //////////////////
    //    Object Fetcher
    ////////////////

    public static MegActiveModelTag valueOf(String string) {
        return valueOf(string, null);
    }

    @Fetchable("megaactivemodel")
    public static MegActiveModelTag valueOf(String string, TagContext context) {
        if (string == null) {
            return null;
        }
        try {
            string = string.replace("megactivemodel@", "");
            String[] split = string.split(",");
            ModeledEntity me = ModelEngineAPI.getModeledEntity(UUID.fromString(split[0]));
            if (me == null) {
                return null;
            }
            ActiveModel am = me.getModel(split[1]).orElse(null);
            if (am == null) {
                return null;
            }
            return new MegActiveModelTag(am);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static boolean matches(String arg) {
        return valueOf(arg) != null;
    }

    /////////////////////
    //   Constructors
    //////////////////

    public MegActiveModelTag(ActiveModel me) {
        this.activeModel = me;
    }

    /////////////////////
    //   Instance Fields/Methods
    /////////////////

    private ActiveModel activeModel;

    public ActiveModel getActiveModel() {
        return activeModel;
    }

    /////////////////////
    //  ObjectTag Methods
    ///////////////////

    private String prefix = "MegActiveModel";

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String identify() {
        return "megactivemodel@" + activeModel.getModeledEntity().getBase().getUUID().toString() + "," + activeModel.getBlueprint().getName();
    }

    @Override
    public String identifySimple() {
        return identify();
    }

    @Override
    public Object getJavaObject() {
        return activeModel;
    }

    @Override
    public ObjectTag setPrefix(String s) {
        prefix = s;
        return this;
    }

    @Override
    public String toString() {
        return identify();
    }

    /////////////////////
    //  Tags
    ///////////////////

    public static ObjectTagProcessor<MegActiveModelTag> tagProcessor = new ObjectTagProcessor<>();

    public static void registerTags() {

    }

    @Override
    public ObjectTag getObjectAttribute(Attribute attribute) {
        return tagProcessor.getObjectAttribute(this, attribute);
    }

    @Override
    public void adjust(Mechanism mechanism) {
        tagProcessor.processMechanism(this, mechanism);
    }

    @Override
    public void applyProperty(Mechanism mechanism) {
        Debug.echoError("Cannot apply properties to a MegModeledEntityTag!");
    }
}