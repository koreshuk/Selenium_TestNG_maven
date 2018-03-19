package msnowEntity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "fdc_weather", schema = "msnow", catalog = "ods_dev")
public class FdcWeatherEntity {
    private BigInteger id;
    private BigInteger municipalityId;
    private Timestamp snowDateFrom;
    private Timestamp snowDateTo;
    private Timestamp iceDate;
    private Integer rainfall;

    @Id
    @Column(name = "id")
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "municipality_id")
    public BigInteger getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(BigInteger municipalityId) {
        this.municipalityId = municipalityId;
    }

    @Basic
    @Column(name = "snow_date_from")
    public Timestamp getSnowDateFrom() {
        return snowDateFrom;
    }

    public void setSnowDateFrom(Timestamp snowDateFrom) {
        this.snowDateFrom = snowDateFrom;
    }

    @Basic
    @Column(name = "snow_date_to")
    public Timestamp getSnowDateTo() {
        return snowDateTo;
    }

    public void setSnowDateTo(Timestamp snowDateTo) {
        this.snowDateTo = snowDateTo;
    }

    @Basic
    @Column(name = "ice_date")
    public Timestamp getIceDate() {
        return iceDate;
    }

    public void setIceDate(Timestamp iceDate) {
        this.iceDate = iceDate;
    }

    @Basic
    @Column(name = "rainfall")
    public Integer getRainfall() {
        return rainfall;
    }

    public void setRainfall(Integer rainfall) {
        this.rainfall = rainfall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FdcWeatherEntity that = (FdcWeatherEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (municipalityId != null ? !municipalityId.equals(that.municipalityId) : that.municipalityId != null)
            return false;
        if (snowDateFrom != null ? !snowDateFrom.equals(that.snowDateFrom) : that.snowDateFrom != null) return false;
        if (snowDateTo != null ? !snowDateTo.equals(that.snowDateTo) : that.snowDateTo != null) return false;
        if (iceDate != null ? !iceDate.equals(that.iceDate) : that.iceDate != null) return false;
        if (rainfall != null ? !rainfall.equals(that.rainfall) : that.rainfall != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (municipalityId != null ? municipalityId.hashCode() : 0);
        result = 31 * result + (snowDateFrom != null ? snowDateFrom.hashCode() : 0);
        result = 31 * result + (snowDateTo != null ? snowDateTo.hashCode() : 0);
        result = 31 * result + (iceDate != null ? iceDate.hashCode() : 0);
        result = 31 * result + (rainfall != null ? rainfall.hashCode() : 0);
        return result;
    }
}
