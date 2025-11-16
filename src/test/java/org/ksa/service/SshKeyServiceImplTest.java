package org.ksa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ksa.dto.SshKeyRequestDTO;
import org.ksa.dto.SshKeyResponseDTO;
import org.ksa.entity.SshKey;
import org.ksa.exception.InvalidSshKeyException;
import org.ksa.exception.SshKeyNotFoundException;
import org.ksa.repository.SshKeyRepository;
import org.ksa.service.impl.SshKeyServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link SshKeyServiceImplTest}
 * <p>
 * Verifies CRUD operations and exception handling using mocked {@link SshKeyRepository}.
 * Test coverage includes:
 * <ul>
 *     <li>Retrieving SSH keys by id</li>
 *     <li>Creating new SSH keys</li>
 *     <li>Updating existing SSH keys by id</li>
 *     <li>Deleting SSH keys by id</li>
 *     <li>Handling invalid inputs and missing records</li>
 * </ul>
 */
@ExtendWith(MockitoExtension.class)
public class SshKeyServiceImplTest {

    @Mock
    private SshKeyRepository sshKeyRepository;

    @InjectMocks
    private SshKeyServiceImpl sshKeyService;

    private SshKey testSshKey;
    private SshKeyRequestDTO testSshKeyRequestDTO;

    @BeforeEach
    void setUp() {
        testSshKey = SshKey.builder()
                .id(1L)
                .serverType("build-server")
                .serverName("jenkins")
                .type("ssh-ed25519")
                .publicKey("AAAAC3NzaC1lZDI1NTE5AAAAIOiKKC7lLUcyvJMo1gjvMr56XvOq814Hhin0OCYFDqT4")
                .comment("test@example.com")
                .build();

        SshKeyRequestDTO.SshKeyDTO sshKeyDTO = SshKeyRequestDTO.SshKeyDTO.builder()
                .type("ssh-ed25519")
                .publicKey("AAAAC3NzaC1lZDI1NTE5AAAAIOiKKC7lLUcyvJMo1gjvMr56XvOq814Hhin0OCYFDqT4")
                .comment("test@example.com")
                .build();

        testSshKeyRequestDTO = SshKeyRequestDTO.builder()
                .sshKey(sshKeyDTO)
                .build();
    }

    /**
     * Tests successful addition of a valid SSH key.
     * Verifies that the key is saved and the response contains correct attributes.
     */
    @Test
    void addSshKey_ValidType_Success() {
        when(sshKeyRepository.existsByServerTypeAndServerNameAndPublicKey(anyString(), anyString(), anyString())).thenReturn(false);
        when(sshKeyRepository.save(any(SshKey.class))).thenReturn(testSshKey);

        SshKeyResponseDTO result = sshKeyService.addSshKey("build-server", "jenkins", testSshKeyRequestDTO);

        assertNotNull(result);
        assertEquals("ssh-ed25519", result.getType());
        assertEquals("build-server", result.getServerType());
        verify(sshKeyRepository, times(1)).save(any(SshKey.class));
    }

    /**
     * Tests addition of an invalid ED25519 key.
     * Expects {@link InvalidSshKeyException} due to malformed public key.
     */
    @Test
    void addSshKey_InvalidTypeED25519_ThrowsException() {
        SshKeyRequestDTO.SshKeyDTO invalidKey = SshKeyRequestDTO.SshKeyDTO.builder()
                .type("ssh-ed25519")
                .publicKey("TEST-ED25519")
                .comment("Testing123")
                .build();
        SshKeyRequestDTO request = SshKeyRequestDTO.builder().sshKey(invalidKey).build();

        assertThrows(InvalidSshKeyException.class, () -> sshKeyService.addSshKey("build-server", "jenkins", request));
    }

    /**
     * Tests addition of an unsupported RSA key type.
     * Expects {@link InvalidSshKeyException} due to unsupported key algorithm.
     */
    @Test
    void addSshKey_InvalidTypeRSA_ThrowsException() {
        SshKeyRequestDTO.SshKeyDTO invalidKey = SshKeyRequestDTO.SshKeyDTO.builder()
                .type("ssh-rsa")
                .publicKey("TEST-RSA")
                .comment("Testing123")
                .build();
        SshKeyRequestDTO request = SshKeyRequestDTO.builder().sshKey(invalidKey).build();

        assertThrows(InvalidSshKeyException.class, () -> sshKeyService.addSshKey("build-server", "jenkins", request));
    }

    /**
     * Tests addition of a duplicate SSH key.
     * Expects {@link InvalidSshKeyException} when the key already exists.
     */
    @Test
    void addSshKey_Duplicate_ThrowsException() {
        when(sshKeyRepository.existsByServerTypeAndServerNameAndPublicKey(anyString(), anyString(), anyString())).thenReturn(true);

        assertThrows(InvalidSshKeyException.class, () -> sshKeyService.addSshKey("build-server", "jenkins", testSshKeyRequestDTO));
    }

    /**
     * Tests successful retrieval of an SSH key by ID.
     * Verifies that the correct key is returned and repository is queried once.
     */
    @Test
    void getKeyById_Success() {
        when(sshKeyRepository.findById(1L)).thenReturn(Optional.of(testSshKey));

        SshKeyResponseDTO result = sshKeyService.getKeyById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ssh-ed25519", result.getType());
        verify(sshKeyRepository, times(1)).findById(1L);
    }

    /**
     * Tests retrieval of an SSH key by ID when not found.
     * Expects {@link SshKeyNotFoundException}.
     */
    @Test
    void getKeyById_NotFound_ThrowsException() {
        when(sshKeyRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(SshKeyNotFoundException.class, () -> sshKeyService.getKeyById(10L));
    }

    /**
     * Tests successful retrieval of all SSH keys for a given server.
     * Verifies that the returned list contains expected data.
     */
    @Test
    void getAllKeys_Success() {
        List<SshKey> keys = Collections.singletonList(testSshKey);
        when(sshKeyRepository.findByServerTypeAndServerName("build-server", "jenkins")).thenReturn(keys);

        List<SshKeyResponseDTO> result = sshKeyService.getAllKeys("build-server", "jenkins");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("jenkins", result.get(0).getServerName());
    }

    /**
     * Tests successful update of an existing SSH key.
     * Verifies that the repository finds and saves the updated key.
     */
    @Test
    void updateSshKey_Success() {
        when(sshKeyRepository.findById(1L)).thenReturn(Optional.of(testSshKey));
        when(sshKeyRepository.save(any(SshKey.class))).thenReturn(testSshKey);

        SshKeyRequestDTO.SshKeyDTO updatedKey = SshKeyRequestDTO.SshKeyDTO.builder()
                .type("ssh-ed25519")
                .publicKey("AAAAC3NzaC1lZDI1NTE5AAAAIOiKKC7lLUcyvJMo1gjvMr56XvOq814Hhin0OCYFDqT4")
                .comment("updated@example.com")
                .build();
        SshKeyRequestDTO request = SshKeyRequestDTO.builder().sshKey(updatedKey).build();

        SshKeyResponseDTO result = sshKeyService.updateSshKey(1L, request);

        assertNotNull(result);
        verify(sshKeyRepository, times(1)).save(any(SshKey.class));
    }

    /**
     * Tests update behavior when the target SSH key is not found.
     * Expects {@link SshKeyNotFoundException}.
     */
    @Test
    void updateSshKey_NotFound_ThrowsException() {
        when(sshKeyRepository.findById(12L)).thenReturn(Optional.empty());

        assertThrows(SshKeyNotFoundException.class, () -> sshKeyService.updateSshKey(12L, testSshKeyRequestDTO));
    }

    /**
     * Tests successful deletion of an existing SSH key.
     * Verifies that the repository checks existence and deletes the key.
     */
    @Test
    void deleteSshKey_Success() {
        when(sshKeyRepository.existsById(1L)).thenReturn(true);

        sshKeyService.deleteSshKey(1L);

        verify(sshKeyRepository, times(1)).deleteById(1L);
    }

    /**
     * Tests deletion behavior when the SSH key is not found.
     * Expects {@link SshKeyNotFoundException}.
     */
    @Test
    void deleteSshKey_NotFound_ThrowsException() {
        when(sshKeyRepository.existsById(45L)).thenReturn(false);

        assertThrows(SshKeyNotFoundException.class, () -> sshKeyService.deleteSshKey(45L));
    }
}
