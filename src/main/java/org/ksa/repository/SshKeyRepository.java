package org.ksa.repository;

import org.ksa.entity.SshKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SshKeyRepository extends JpaRepository<SshKey, Long> {

    boolean existsByServerNameAndUserNameAndPublicKey(String serverType, String serverName, String publicKey);

    List<SshKey> findByServerNameAndUserName(String serverType, String serverName);

    Optional<SshKey> findByServerNameAndUserNameAndPublicKey(String serverType, String serverName, String publicKey);
}
