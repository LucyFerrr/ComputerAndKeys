package org.ksa.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ssh_keys", uniqueConstraints = @UniqueConstraint(name = "uk_server_user_public",
        columnNames = {"server_name", "user_name", "public_key"}))
public class SshKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "server_name", nullable = false)
    private String serverName;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "key_type", nullable = false)
    private String type;

    @Column(name = "public_key", nullable = false)
    private String publicKey;

    @Column(name = "comment")
    private String comment;
}
