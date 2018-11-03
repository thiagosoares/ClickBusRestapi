package com.clickbus.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.clickbus.service.service.PlaceService;
import com.clickbus.service.web.rest.errors.BadRequestAlertException;
import com.clickbus.service.web.rest.util.HeaderUtil;
import com.clickbus.service.web.rest.util.PaginationUtil;
import com.clickbus.service.service.dto.PlaceDTO;
import com.clickbus.service.service.dto.PlaceDetailsDTO;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Place.
 */
@RestController
@RequestMapping("/api")
public class PlaceResource {

    private final Logger log = LoggerFactory.getLogger(PlaceResource.class);

    private static final String ENTITY_NAME = "place";

    private PlaceService placeService;

    public PlaceResource(PlaceService placeService) {
        this.placeService = placeService;
    }

    /**
     * POST  /places : Create a new place.
     *
     * @param placeDTO the placeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new placeDTO, or with status 400 (Bad Request) if the place has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/places")
    @Timed
    public ResponseEntity<PlaceDTO> createPlace(@Valid @RequestBody PlaceDTO placeDTO) throws URISyntaxException {
        log.debug("REST request to save Place : {}", placeDTO);
        if (placeDTO.getId() != null) {
            throw new BadRequestAlertException("A new place cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlaceDTO result = placeService.save(placeDTO);
        return ResponseEntity.created(new URI("/api/places/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /places : Updates an existing place.
     *
     * @param placeDTO the placeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated placeDTO,
     * or with status 400 (Bad Request) if the placeDTO is not valid,
     * or with status 500 (Internal Server Error) if the placeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/places")
    @Timed
    public ResponseEntity<PlaceDTO> updatePlace(@Valid @RequestBody PlaceDTO placeDTO) throws URISyntaxException {
        log.debug("REST request to update Place : {}", placeDTO);
        if (placeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PlaceDTO result = placeService.save(placeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, placeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /places : get all the places.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of places in body
     */
    @GetMapping("/places")
    @Timed
    public ResponseEntity<List<PlaceDTO>> getAllPlaces(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Places");
        Page<PlaceDTO> page;
        if (eagerload) {
            page = placeService.findAllWithEagerRelationships(pageable);
        } else {
            page = placeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/places?eagerload=%b", eagerload));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /places/:id : get the "id" place.
     *
     * @param id the id of the placeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the placeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/places/{id}")
    @Timed
    public ResponseEntity<PlaceDetailsDTO> getPlace(@PathVariable Long id) {
        log.debug("REST request to get Place : {}", id);
        Optional<PlaceDetailsDTO> placeDTO = placeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(placeDTO);
    }

    /**
     * DELETE  /places/:id : delete the "id" place.
     *
     * @param id the id of the placeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/places/{id}")
    @Timed
    public ResponseEntity<Void> deletePlace(@PathVariable Long id) {
        log.debug("REST request to delete Place : {}", id);
        placeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/places?query=:query : search for the place corresponding
     * to the query.
     *
     * @param query the query of the place search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/places")
    @Timed
    public ResponseEntity<List<PlaceDTO>> searchPlaces(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Places for query {}", query);
        Page<PlaceDTO> page = placeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/places");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
