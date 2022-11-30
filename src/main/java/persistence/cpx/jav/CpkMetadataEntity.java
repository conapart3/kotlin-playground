package persistence.cpx.jav;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;

@Entity
@Table(name = "cpk_metadata", schema = "config")
public class CpkMetadataEntity implements Serializable {
    @EmbeddedId
    CpkKey id;
    @NaturalId
    @Column(name = "file_checksum", nullable = false, unique = true)
    String cpkFileChecksum;
    @Column(name = "format_version", nullable = false)
    String formatVersion;
    @Column(name = "metadata", nullable = false)
    String serializedMetadata;
    @Column(name = "is_deleted", nullable = false)
    Boolean isDeleted;

    @Version
    @Column(name = "entity_version", nullable = false)
    int entityVersion;

    public CpkMetadataEntity() {
    }

    public CpkMetadataEntity(CpkKey id, String cpkFileChecksum, String formatVersion, String serializedMetadata, Boolean isDeleted, int entityVersion) {
        this.id = id;
        this.cpkFileChecksum = cpkFileChecksum;
        this.formatVersion = formatVersion;
        this.serializedMetadata = serializedMetadata;
        this.isDeleted = isDeleted;
        this.entityVersion = entityVersion;
    }

    public CpkKey getId() {
        return id;
    }

    public void setId(CpkKey id) {
        this.id = id;
    }

    public String getCpkFileChecksum() {
        return cpkFileChecksum;
    }

    public void setCpkFileChecksum(String cpkFileChecksum) {
        this.cpkFileChecksum = cpkFileChecksum;
    }

    public String getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }

    public String getSerializedMetadata() {
        return serializedMetadata;
    }

    public void setSerializedMetadata(String serializedMetadata) {
        this.serializedMetadata = serializedMetadata;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public int getEntityVersion() {
        return entityVersion;
    }

    public void setEntityVersion(int entityVersion) {
        this.entityVersion = entityVersion;
    }
}

