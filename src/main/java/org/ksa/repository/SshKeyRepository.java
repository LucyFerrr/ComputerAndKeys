package org.ksa.repository;

import org.ksa.entity.SshKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SshKeyRepository extends JpaRepository<SshKey, Long> {

    boolean existsByServerTypeAndServerNameAndPublicKey(String serverType, String serverName, String publicKey);

    List<SshKey> findByServerTypeAndServerName(String serverType, String serverName);

}
