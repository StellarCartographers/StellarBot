package space.tscg.internal.template.publik;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import space.tscg.api.carrier.ICarrierServices;
import space.tscg.api.carrier.IFleetCarrier;
import space.tscg.api.carrier.IService;
import space.tscg.api.carrier.ITaxableService;
import space.tscg.internal.template.MessageTemplate;
import space.tscg.util.text.Ansi;
import space.tscg.util.text.Embed;
import space.tscg.util.text.Ansi.Color;

@AllArgsConstructor
public class ServicesEmbed implements MessageTemplate
{
    private ICarrierServices services;
    
    public ServicesEmbed(IFleetCarrier fleetCarrier)
    {
        this.services = fleetCarrier.getServices();
    }
    
    @Override
    public MessageCreateData getMessageData()
    {
        var builder = new MessageCreateBuilder();
        builder.addContent("# Carrier Services");
        Embed embed = Embed.newBuilder();
        if(this.allServicesSuspended())
        {
            embed.description(Ansi.newBlock(l -> l.add(Color.RED, "All Services Suspended")).toString());
        } else {
            embed.fieldInline("Refuel", this.getStatus(this.services.getRefuel()));
            embed.fieldInline("Repair", this.getStatus(this.services.getRepair()));
            embed.fieldInline("Armoury", this.getStatus(this.services.getArmoury()));
            embed.fieldInline("Shipyard", this.getStatus(this.services.getShipyard()));
            embed.fieldInline("Outfitting", this.getStatus(this.services.getOutfitting()));
            embed.fieldInline("Pioneer Supplies", this.getStatus(this.services.getPioneerSupplies()));
            embed.blankField();
            embed.field("Universal Cartographics", this.getStatus(this.services.getUniversalCartographics()));
            embed.field("Redemption Office", this.getStatus(this.services.getRedemptionOffice()));
            embed.field("Concourse Bar", this.getStatus(this.services.getConcourseBar()));
            embed.field("Vista Genomics", this.getStatus(this.services.getVistaGenomics()));
            embed.field("Secure Warehouse", this.getStatus(this.services.getSecureWarehouse()));
        }
        builder.addEmbeds(embed.toEmbed());
        return builder.build();
    }
    
    private String getStatus(Object service)
    {
        if(service == null)
        {
            return this.notInstalled();
        } else {
            if(service instanceof ITaxableService) {
                ITaxableService taxable = (ITaxableService) service;
                return taxable.isEnabled() ? this.activeTaxed(taxable.getTax()) : this.suspsended();
            } else {
                IService nonTaxable = (IService) service;
                return nonTaxable.isEnabled() ? this.active() : this.suspsended();
            }
        }
    }
    
    private String notInstalled()
    {
        return Ansi.newBlock(l -> l.add(Color.RED, "Not Installed")).toString();
    }
    
    private String suspsended()
    {
        return Ansi.newBlock(l -> l.add(Color.RED, "Suspended")).toString();
    }

    private String active()
    {
        return Ansi.newBlock(l -> l.add(Color.GREEN, "Active")).toString();
    }
    
    private String activeTaxed(int tax)
    {
        return Ansi.newBlock(
            l -> l.add(Color.GREEN, "Active"),
            l -> l.add(Color.YELLOW, "%d%% Tax".formatted(tax))).toString();
    }
    
    private boolean allServicesSuspended()
    {
        return 
            !this.services.getRefuel().isEnabled() &&
            !this.services.getRepair().isEnabled() &&
            !this.services.getArmoury().isEnabled() &&
            !this.services.getShipyard().isEnabled() &&
            !this.services.getOutfitting().isEnabled() &&
            !this.services.getPioneerSupplies().isEnabled() &&
            !this.services.getUniversalCartographics().isEnabled() &&
            !this.services.getRedemptionOffice().isEnabled() &&
            !this.services.getConcourseBar().isEnabled() &&
            !this.services.getVistaGenomics().isEnabled() &&
            !this.services.getSecureWarehouse().isEnabled();
    }
}
