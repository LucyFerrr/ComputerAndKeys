package org.ksa.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ssh_keys", uniqueConstraints = @UniqueConstraint(name = "uk_server_type_name_public",
        columnNames = {"server_type", "server_name", "public_key"}),
        indexes = @Index(name = "idx_server_type_name", columnList = "server_type,server_name"))
public class SshKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "server_type", nullable = false)
    private String serverType;

    @Column(name = "server_name", nullable = false)
    private String serverName;

    @Column(name = "key_type", nullable = false)
    private String type;

    @Column(name = "public_key", nullable = false)
    private String publicKey;

    @Column(name = "comment")
    private String comment;
}
