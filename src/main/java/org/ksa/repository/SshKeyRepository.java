package org.ksa.repository;

import org.ksa.entity.SshKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SshKeyRepository extends JpaRepository<SshKey, Long> {

    boolean existsByServerNameAndUserNameAndPublicKey(String serverName, String userName, String publicKey);

}
