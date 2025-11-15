package org.ksa.repository;

import org.ksa.entity.SshKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SshKeyRepository extends JpaRepository<SshKey, Long> {

    boolean existsByServerNameAndUserNameAndPublicKey(String serverName, String userName, String publicKey);

    List<SshKey> findByServerNameAndUserName(String serverName, String userName);

    Optional<SshKey> findByServerNameAndUserNameAndPublicKey(String serverName, String userName, String publicKey);
}
