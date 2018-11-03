package com.clickbus.service.service.impl;

import com.clickbus.service.service.PlaceService;
import com.clickbus.service.domain.Place;
import com.clickbus.service.repository.PlaceRepository;
import com.clickbus.service.repository.search.PlaceSearchRepository;
import com.clickbus.service.service.dto.CityDTO;
import com.clickbus.service.service.dto.CountryDTO;
import com.clickbus.service.service.dto.PlaceDTO;
import com.clickbus.service.service.dto.PlaceDetailsDTO;
import com.clickbus.service.service.dto.StateDTO;
import com.clickbus.service.service.mapper.PlaceMapper;
import com.clickbus.service.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Place.
 */
@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {

    private final Logger log = LoggerFactory.getLogger(PlaceServiceImpl.class);

    private PlaceRepository placeRepository;

    private PlaceMapper placeMapper;

    private PlaceSearchRepository placeSearchRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository, PlaceMapper placeMapper, PlaceSearchRepository placeSearchRepository) {
        this.placeRepository = placeRepository;
        this.placeMapper = placeMapper;
        this.placeSearchRepository = placeSearchRepository;
    }

    /**
     * Save a place.
     *
     * @param placeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PlaceDTO save(PlaceDTO placeDTO) {
        log.debug("Request to save Place : {}", placeDTO);

        Place place = placeMapper.toEntity(placeDTO);
        
        place = placeRepository.save(place);
        PlaceDTO result = placeMapper.toDto(place);
        placeSearchRepository.save(place);
        return result;
    }

    /**
     * Get all the places.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlaceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Places");
        return placeRepository.findAll(pageable)
            .map(placeMapper::toDto);
    }

    /**
     * Get all the Place with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<PlaceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return placeRepository.findAllWithEagerRelationships(pageable).map(placeMapper::toDto);
    }
    

    /**
     * Get one place by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlaceDetailsDTO> findOne(Long id) {
        log.debug("Request to get Place : {}", id);
        
        Place place = placeRepository.findOneWithEagerRelationships(id)
        		.orElseThrow(() -> new BadRequestAlertException("A new city cannot already have an ID", "PLACE", "idnotexists"));
        
        
        PlaceDetailsDTO placeDetails = new PlaceDetailsDTO();
        placeDetails.setName(place.getName());
        placeDetails.setSlug(place.getSlug());
        placeDetails.setCity(new CityDTO(place.getCity().getId()));
        placeDetails.setState(new StateDTO(place.getCity().getState().getId()));
        placeDetails.setCountry(new CountryDTO(place.getCity().getState().getCountry().getId()));
        
        return Optional.ofNullable(placeDetails);
    }

    /**
     * Delete the place by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Place : {}", id);
        placeRepository.deleteById(id);
        placeSearchRepository.deleteById(id);
    }

    /**
     * Search for the place corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PlaceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Places for query {}", query);
        return placeSearchRepository.search(queryStringQuery(query), pageable)
            .map(placeMapper::toDto);
    }
}
