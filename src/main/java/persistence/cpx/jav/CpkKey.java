package persistence.cpx.jav;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CpkKey implements Serializable {
    @Column(name = "cpk_name")
    String cpkName;
    @Column(name = "cpk_version")
    String cpkVersion;
    @Column(name = "cpk_signer_summary_hash")
    String cpkSignerSummaryHash;

    public CpkKey() {
    }

    public CpkKey(String cpkName, String cpkVersion, String cpkSignerSummaryHash) {
        this.cpkName = cpkName;
        this.cpkVersion = cpkVersion;
        this.cpkSignerSummaryHash = cpkSignerSummaryHash;
    }

    public String getCpkName() {
        return cpkName;
    }

    public void setCpkName(String cpkName) {
        this.cpkName = cpkName;
    }

    public String getCpkVersion() {
        return cpkVersion;
    }

    public void setCpkVersion(String cpkVersion) {
        this.cpkVersion = cpkVersion;
    }

    public String getCpkSignerSummaryHash() {
        return cpkSignerSummaryHash;
    }

    public void setCpkSignerSummaryHash(String cpkSignerSummaryHash) {
        this.cpkSignerSummaryHash = cpkSignerSummaryHash;
    }
}