package io.pivotal.cfapp.domain.accounting.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

@Builder
@Getter
@JsonPropertyOrder({"month", "year", "duration_in_hours", "average_instances", "maximum_instances"})
public class ServiceUsageMonthly {

    @JsonProperty("month")
    public Integer month;

    @JsonProperty("year")
    public Integer year;

    @Default
    @JsonProperty("duration_in_hours")
    public Double durationInHours = 0.0;

    @Default
    @JsonProperty("average_instances")
    public Double averageInstances = 0.0;

    @Default
    @JsonProperty("maximum_instances")
    public Integer maximumInstances = 0;

    @JsonCreator
    public ServiceUsageMonthly(
        @JsonProperty("month") Integer month,
        @JsonProperty("year") Integer year,
        @JsonProperty("duration_in_hours") Double durationInHours,
        @JsonProperty("average_instances") Double averageInstances,
        @JsonProperty("maximum_instances") Integer maximumInstances) {
        this.month = month;
        this.year = year;
        this.durationInHours = durationInHours;
        this.averageInstances = averageInstances;
        this.maximumInstances = maximumInstances;
    }

    @JsonIgnore
    public ServiceUsageMonthly combine(ServiceUsageMonthly usage) {
        ServiceUsageMonthly result = null;
        if (usage == null) {
            result = this;
        } else if (usage.getYear().equals(year) && usage.getMonth().equals(month)) {
            result =
                ServiceUsageMonthly
                    .builder()
                        .month(usage.getMonth())
                        .year(usage.getYear())
                        .durationInHours(this.durationInHours + usage.getDurationInHours())
                        .averageInstances(this.averageInstances + usage.getAverageInstances())
                        .maximumInstances(this.maximumInstances + usage.getMaximumInstances())
                        .build();
        } else {
            result = usage;
        }
        return result;
    }

    @JsonIgnore
    public String getYearAndMonth() {
        return String.join("-", String.valueOf(year), String.format("%02d", month));
    }

}
