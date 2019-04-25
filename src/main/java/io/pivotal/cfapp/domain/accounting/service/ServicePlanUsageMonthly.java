package io.pivotal.cfapp.domain.accounting.service;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "usages", "service_plan_name", "service_plan_guid"})
public class ServicePlanUsageMonthly {

    @JsonProperty("usages")
    public List<ServiceUsageMonthly> usages;

    @JsonProperty("service_plan_name")
    public String servicePlanName;

    @JsonProperty("service_plan_guid")
    public String servicePlanGuid;

    @JsonIgnore
    public boolean combine(ServicePlanUsageMonthly usage) {
        boolean combined = false;
        if (usage.getServicePlanName().equals(servicePlanName)) {
            for (ServiceUsageMonthly su: usage.getUsages()) {
                for (ServiceUsageMonthly suu: usages) {
                    if(!suu.combine(su)) {
                        usages.add(su);
                    }
                }
            }
            String newPlanGuid = String.join(",", this.servicePlanGuid, usage.getServicePlanGuid());
            this.servicePlanGuid = newPlanGuid;
            combined = true;
        }
        return combined;
    }

}
