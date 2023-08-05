package space.tscg.bot.edsm.api.v1.system.modal;

import java.util.List;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import space.tscg.bot.edsm.api.v1.system.modal.adapter.Deserialization;
import space.tscg.bot.edsm.api.v1.system.modal.body.AtmosphereComposition;
import space.tscg.bot.edsm.api.v1.system.modal.body.Materials;
import space.tscg.bot.edsm.api.v1.system.modal.body.Ring;
import space.tscg.bot.edsm.api.v1.system.modal.body.SolidComposition;

@Getter
public class Planet extends Body<Planet>
{
    @SerializedName("isLandable")
    private boolean landable;
    @SerializedName("gravity")
    private double gravity;
    @SerializedName("earthMasses")
    private double earthMasses;
    @SerializedName("radius")
    private double radius;
    @SerializedName("volcanismType")
    private String volcanismType;
    @SerializedName("atmosphereType")
    private String atmosphereType;
    @JsonAdapter(Deserialization.AtmosphereCompositionAdapter.class)
    @SerializedName("atmosphereComposition")
    private AtmosphereComposition atmosphereComposition;
    @SerializedName("solidComposition")
    private SolidComposition solidComposition;
    @SerializedName("terraformingState")
    private String terraformingState;
    @JsonAdapter(Deserialization.MaterialsAdapter.class)
    @SerializedName("materials")
    private Materials materials;
    @SerializedName("updateTime")
    private String updateTime;
    @SerializedName("rings")
    private List<Ring> rings = null;
    
    @Override
    public Planet get()
    {
        return this;
    }
}
