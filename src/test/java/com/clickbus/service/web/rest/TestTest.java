package com.clickbus.service.web.rest;

import com.clickbus.service.ClickbusApp;

import com.clickbus.service.domain.Country;
import com.clickbus.service.repository.CountryRepository;
import com.clickbus.service.repository.search.CountrySearchRepository;
import com.clickbus.service.service.CountryService;
import com.clickbus.service.service.dto.CountryDTO;
import com.clickbus.service.service.mapper.CountryMapper;
import com.clickbus.service.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
import java.util.List;


import static com.clickbus.service.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CountryResource REST controller.
 *
 * @see CountryResource
 */
public class TestTest {

    @Test
    public void createCountry() throws Exception {

    	Instant in = Instant.now();
    	in = in.minusSeconds(65);
    	System.out.println(in);
    	
    	Instant now = Instant.now();
        System.out.println(now);
        
        Instant truncatedIn = in.truncatedTo(ChronoUnit.MINUTES);
        System.out.println(truncatedIn);
        
        Instant truncatedNow = now.truncatedTo(ChronoUnit.MINUTES);
        System.out.println(truncatedNow);
        
        assertEquals(truncatedIn, truncatedNow);
        
        assertTrue(TestUtil.compareDatesMinutes(in));
        
    }
    
    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Country.class);
        Country country1 = new Country();
        country1.setId(1L);
        Country country2 = new Country();
        country2.setId(country1.getId());
        assertThat(country1).isEqualTo(country2);
        country2.setId(2L);
        assertThat(country1).isNotEqualTo(country2);
        country1.setId(null);
        assertThat(country1).isNotEqualTo(country2);
    }
    
}
