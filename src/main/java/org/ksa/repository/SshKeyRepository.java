package org.ksa.repository;

import org.ksa.entity.SshKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for accessing and managing {@link SshKey} entities.
 */
public interface SshKeyRepository extends JpaRepository<SshKey, Long> {

    /**
     * Checks if SSH key exists for the given server type, server name and public key.
     *
     * @param serverType type of the server
     * @param serverName name of the server
     * @param publicKey  public key to check
     * @return {@code true} if a matching SSH key exists, otherwise {@code false}
     */
    boolean existsByServerTypeAndServerNameAndPublicKey(String serverType, String serverName, String publicKey);

    /**
     * Retrieves all SSH key associated with the given servertype and server name.
     *
     * @param serverType type of the server
     * @param serverName name of the server
     * @return list of matching {@link SshKey} entities
     */
    List<SshKey> findByServerTypeAndServerName(String serverType, String serverName);
}
