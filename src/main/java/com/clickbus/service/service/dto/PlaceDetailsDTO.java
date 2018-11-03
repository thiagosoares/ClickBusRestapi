package com.clickbus.service.service.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A DTO for the Place entity.
 */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(callSuper = true, exclude = "clientIds")
public interface PlaceDetailsDTO {

    public Long getId();

    public String getName();

    public String getSlug();

    public CityID getCity();
    
    @Value("#{target.city.state}")
    public StateID getState();
    
    @Value("#{target.city.state.country}")
    public CountryID getCountry();
    
    /*
    //public Set<Long> getClientIds();
    */
       
    interface CityID {
    	public String getName();
    }
    
    interface StateID {
    	//@Value("#{state.name}")
    	public String getName();
    }
    
    
    interface CountryID {
    	public String getName();
    }

}
