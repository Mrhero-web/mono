package com.ledar.mono.web.rest;

import com.ledar.mono.domain.AUser;
import com.ledar.mono.repository.AUserRepository;
import com.ledar.mono.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ledar.mono.domain.AUser}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AUserResource {

    private final Logger log = LoggerFactory.getLogger(AUserResource.class);

    private static final String ENTITY_NAME = "aUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AUserRepository aUserRepository;

    public AUserResource(AUserRepository aUserRepository) {
        this.aUserRepository = aUserRepository;
    }

    /**
     * {@code POST  /a-users} : Create a new aUser.
     *
     * @param aUser the aUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aUser, or with status {@code 400 (Bad Request)} if the aUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/a-users")
    public ResponseEntity<AUser> createAUser(@Valid @RequestBody AUser aUser) throws URISyntaxException {
        log.debug("REST request to save AUser : {}", aUser);
        if (aUser.getId() != null) {
            throw new BadRequestAlertException("A new aUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AUser result = aUserRepository.save(aUser);
        return ResponseEntity
            .created(new URI("/api/a-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /a-users/:id} : Updates an existing aUser.
     *
     * @param id the id of the aUser to save.
     * @param aUser the aUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aUser,
     * or with status {@code 400 (Bad Request)} if the aUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/a-users/{id}")
    public ResponseEntity<AUser> updateAUser(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody AUser aUser)
        throws URISyntaxException {
        log.debug("REST request to update AUser : {}, {}", id, aUser);
        if (aUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AUser result = aUserRepository.save(aUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /a-users/:id} : Partial updates given fields of an existing aUser, field will ignore if it is null
     *
     * @param id the id of the aUser to save.
     * @param aUser the aUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aUser,
     * or with status {@code 400 (Bad Request)} if the aUser is not valid,
     * or with status {@code 404 (Not Found)} if the aUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the aUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/a-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AUser> partialUpdateAUser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AUser aUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update AUser partially : {}, {}", id, aUser);
        if (aUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, aUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!aUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AUser> result = aUserRepository
            .findById(aUser.getId())
            .map(existingAUser -> {
                if (aUser.getLogin() != null) {
                    existingAUser.setLogin(aUser.getLogin());
                }
                if (aUser.getPassword() != null) {
                    existingAUser.setPassword(aUser.getPassword());
                }

                return existingAUser;
            })
            .map(aUserRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, aUser.getId().toString())
        );
    }

    /**
     * {@code GET  /a-users} : get all the aUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aUsers in body.
     */
    @GetMapping("/a-users")
    public List<AUser> getAllAUsers() {
        log.debug("REST request to get all AUsers");
        return aUserRepository.findAll();
    }

    /**
     * {@code GET  /a-users/:id} : get the "id" aUser.
     *
     * @param id the id of the aUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/a-users/{id}")
    public ResponseEntity<AUser> getAUser(@PathVariable Long id) {
        log.debug("REST request to get AUser : {}", id);
        Optional<AUser> aUser = aUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aUser);
    }

    /**
     * {@code DELETE  /a-users/:id} : delete the "id" aUser.
     *
     * @param id the id of the aUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/a-users/{id}")
    public ResponseEntity<Void> deleteAUser(@PathVariable Long id) {
        log.debug("REST request to delete AUser : {}", id);
        aUserRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
