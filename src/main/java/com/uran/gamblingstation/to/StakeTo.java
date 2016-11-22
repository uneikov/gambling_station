package com.uran.gamblingstation.to;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class StakeTo implements Serializable{
    private static final long serialVersionUID = 2L;

    private Integer id;

    @NotNull(message = "  must not be empty, minimum value - 1.0")
    @DecimalMin("1")
    private Double stakeValue;

    @Size(min = 2, max = 40)
    @NotEmpty(message = "  is required")
    private String horseName;

    public StakeTo() {
    }

    public StakeTo(Integer id, Double stakeValue, String horseName) {
        this.id = id;
        this.stakeValue = stakeValue;
        this.horseName = horseName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }

    public Double getStakeValue() {
        return stakeValue;
    }

    public void setStakeValue(Double stakeValue) {
        this.stakeValue = stakeValue;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    @Override
    public String toString() {
        return "StakeTo{" +
                "id=" + id +
                ", stakeValue=" + stakeValue +
                ", horseName='" + horseName + '\'' +
                '}';
    }
}
