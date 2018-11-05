package com.clickbus.service.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clickbus.service.domain.Place;
import com.clickbus.service.repository.PlaceRepository;
import com.clickbus.service.repository.search.PlaceSearchRepository;
import com.clickbus.service.service.PlaceService;
import com.clickbus.service.service.dto.PlaceDTO;
import com.clickbus.service.service.dto.PlaceSimpleDTO;
import com.clickbus.service.service.dto.projections.PlaceDetailsDTO;
import com.clickbus.service.service.mapper.PlaceMapper;
import com.clickbus.service.web.rest.errors.BadRequestAlertException;

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
    public Page<PlaceSimpleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Places");
        
        /*Page<Place> list = placeRepository.findAll(pageable);
        
        Page<PlaceDTO> listDto = list.map(placeMapper::toDto);
        
        for(PlaceDTO dto : listDto.getContent()) {
        	System.out.println(dto.getName());
        	System.out.println(dto.getClientApplications());
        }
        
        return listDto;*/
        return placeRepository.findAll(pageable)
        					  .map(placeMapper::toSimpleDto);
    }

    /**
     * Get all the Place with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<PlaceDetailsDTO> findAllWithEagerRelationships(Pageable pageable) {
    	
    	/*
    	Page<Place> list = placeRepository.findAllWithEagerRelationships(pageable);
        
        Page<PlaceDTO> listDto = list.map(placeMapper::toDto);
        
        for(PlaceDTO dto : listDto.getContent()) {
        	System.out.println(dto.getName());
        	System.out.println(dto.getClientApplications());
        }
        
        return listDto;*/
    	
        // return placeRepository.findAllWithEagerRelationships(pageable).map(placeMapper::toEagerDto);
    	// return placeRepository.findAllWithEagerRelationships(pageable).map(placeMapper::toEagerDto);
    	return placeRepository.findAllWithEagerRelationships(pageable);
    	
    }
    

    /**
     * Get one place by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlaceDTO> findOne(Long id) {
        log.debug("Request to get Place : {}", id);
        return placeRepository.findOneWithEagerRelationships(id)
                .map(placeMapper::toDto);
    }

    /**
     * Get one place with details by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlaceDetailsDTO> findOneDetails(Long id) {
        log.debug("Request to get Place : {}", id);
        
        PlaceDetailsDTO place = placeRepository.findOneWithEagerRelationshipsDetails(id)
        		.orElseThrow(() -> new BadRequestAlertException("A new city cannot already have an ID", "PLACE", "idnotexists"));
        
        return Optional.ofNullable(place);
    }
    

    /**
     * Get one place by slug.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PlaceDetailsDTO> findOneBySlug(String slug) {
        log.debug("Request to get Place : {}", slug);
        
        PlaceDetailsDTO place = placeRepository.findOneBySlug(slug)
        		.orElseThrow(() -> new BadRequestAlertException("A new city cannot already have an ID", "PLACE", "idnotexists"));
        
        return Optional.ofNullable(place);
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
