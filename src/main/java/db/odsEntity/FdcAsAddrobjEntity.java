package db.odsEntity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "fdc_as_addrobj", schema = "ods", catalog = "ods_dev")
public class FdcAsAddrobjEntity {
    private BigInteger id;
    private String formalName;
    private Integer regionCode;
    private Integer autoCode;
    private Integer areaCode;
    private Integer cityCode;
    private Integer ctarCode;
    private Integer placeCode;
    private Integer streetCode;
    private Integer extrCode;
    private Integer sextCode;
    private String offName;
    private String postalCode;
    private Integer ifnsFl;
    private Integer terrIfnsFl;
    private Integer ifnsUl;
    private Integer terrIfnsUl;
    private String okato;
    private String oktmo;
    private Timestamp updateDate;
    private String shortName;
    private Integer aoLevel;
    private String code;
    private String plainCode;
    private Boolean actStatus;
    private Boolean centStatus;
    private Integer operStatus;
    private Integer currStatus;
    private Date startDate;
    private Date endDate;
    private Boolean liveStatus;
    private String dtype;

    @Id
    @Column(name = "id")
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Basic
    @Column(name = "formal_name")
    public String getFormalName() {
        return formalName;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }

    @Basic
    @Column(name = "region_code")
    public Integer getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
    }

    @Basic
    @Column(name = "auto_code")
    public Integer getAutoCode() {
        return autoCode;
    }

    public void setAutoCode(Integer autoCode) {
        this.autoCode = autoCode;
    }

    @Basic
    @Column(name = "area_code")
    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    @Basic
    @Column(name = "city_code")
    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    @Basic
    @Column(name = "ctar_code")
    public Integer getCtarCode() {
        return ctarCode;
    }

    public void setCtarCode(Integer ctarCode) {
        this.ctarCode = ctarCode;
    }

    @Basic
    @Column(name = "place_code")
    public Integer getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(Integer placeCode) {
        this.placeCode = placeCode;
    }

    @Basic
    @Column(name = "street_code")
    public Integer getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(Integer streetCode) {
        this.streetCode = streetCode;
    }

    @Basic
    @Column(name = "extr_code")
    public Integer getExtrCode() {
        return extrCode;
    }

    public void setExtrCode(Integer extrCode) {
        this.extrCode = extrCode;
    }

    @Basic
    @Column(name = "sext_code")
    public Integer getSextCode() {
        return sextCode;
    }

    public void setSextCode(Integer sextCode) {
        this.sextCode = sextCode;
    }

    @Basic
    @Column(name = "off_name")
    public String getOffName() {
        return offName;
    }

    public void setOffName(String offName) {
        this.offName = offName;
    }

    @Basic
    @Column(name = "postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Basic
    @Column(name = "ifns_fl")
    public Integer getIfnsFl() {
        return ifnsFl;
    }

    public void setIfnsFl(Integer ifnsFl) {
        this.ifnsFl = ifnsFl;
    }

    @Basic
    @Column(name = "terr_ifns_fl")
    public Integer getTerrIfnsFl() {
        return terrIfnsFl;
    }

    public void setTerrIfnsFl(Integer terrIfnsFl) {
        this.terrIfnsFl = terrIfnsFl;
    }

    @Basic
    @Column(name = "ifns_ul")
    public Integer getIfnsUl() {
        return ifnsUl;
    }

    public void setIfnsUl(Integer ifnsUl) {
        this.ifnsUl = ifnsUl;
    }

    @Basic
    @Column(name = "terr_ifns_ul")
    public Integer getTerrIfnsUl() {
        return terrIfnsUl;
    }

    public void setTerrIfnsUl(Integer terrIfnsUl) {
        this.terrIfnsUl = terrIfnsUl;
    }

    @Basic
    @Column(name = "okato")
    public String getOkato() {
        return okato;
    }

    public void setOkato(String okato) {
        this.okato = okato;
    }

    @Basic
    @Column(name = "oktmo")
    public String getOktmo() {
        return oktmo;
    }

    public void setOktmo(String oktmo) {
        this.oktmo = oktmo;
    }

    @Basic
    @Column(name = "update_date")
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Basic
    @Column(name = "short_name")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Basic
    @Column(name = "ao_level")
    public Integer getAoLevel() {
        return aoLevel;
    }

    public void setAoLevel(Integer aoLevel) {
        this.aoLevel = aoLevel;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "plain_code")
    public String getPlainCode() {
        return plainCode;
    }

    public void setPlainCode(String plainCode) {
        this.plainCode = plainCode;
    }

    @Basic
    @Column(name = "act_status")
    public Boolean getActStatus() {
        return actStatus;
    }

    public void setActStatus(Boolean actStatus) {
        this.actStatus = actStatus;
    }

    @Basic
    @Column(name = "cent_status")
    public Boolean getCentStatus() {
        return centStatus;
    }

    public void setCentStatus(Boolean centStatus) {
        this.centStatus = centStatus;
    }

    @Basic
    @Column(name = "oper_status")
    public Integer getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(Integer operStatus) {
        this.operStatus = operStatus;
    }

    @Basic
    @Column(name = "curr_status")
    public Integer getCurrStatus() {
        return currStatus;
    }

    public void setCurrStatus(Integer currStatus) {
        this.currStatus = currStatus;
    }

    @Basic
    @Column(name = "start_date")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "live_status")
    public Boolean getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(Boolean liveStatus) {
        this.liveStatus = liveStatus;
    }

    @Basic
    @Column(name = "dtype")
    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FdcAsAddrobjEntity that = (FdcAsAddrobjEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (formalName != null ? !formalName.equals(that.formalName) : that.formalName != null) return false;
        if (regionCode != null ? !regionCode.equals(that.regionCode) : that.regionCode != null) return false;
        if (autoCode != null ? !autoCode.equals(that.autoCode) : that.autoCode != null) return false;
        if (areaCode != null ? !areaCode.equals(that.areaCode) : that.areaCode != null) return false;
        if (cityCode != null ? !cityCode.equals(that.cityCode) : that.cityCode != null) return false;
        if (ctarCode != null ? !ctarCode.equals(that.ctarCode) : that.ctarCode != null) return false;
        if (placeCode != null ? !placeCode.equals(that.placeCode) : that.placeCode != null) return false;
        if (streetCode != null ? !streetCode.equals(that.streetCode) : that.streetCode != null) return false;
        if (extrCode != null ? !extrCode.equals(that.extrCode) : that.extrCode != null) return false;
        if (sextCode != null ? !sextCode.equals(that.sextCode) : that.sextCode != null) return false;
        if (offName != null ? !offName.equals(that.offName) : that.offName != null) return false;
        if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null) return false;
        if (ifnsFl != null ? !ifnsFl.equals(that.ifnsFl) : that.ifnsFl != null) return false;
        if (terrIfnsFl != null ? !terrIfnsFl.equals(that.terrIfnsFl) : that.terrIfnsFl != null) return false;
        if (ifnsUl != null ? !ifnsUl.equals(that.ifnsUl) : that.ifnsUl != null) return false;
        if (terrIfnsUl != null ? !terrIfnsUl.equals(that.terrIfnsUl) : that.terrIfnsUl != null) return false;
        if (okato != null ? !okato.equals(that.okato) : that.okato != null) return false;
        if (oktmo != null ? !oktmo.equals(that.oktmo) : that.oktmo != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (aoLevel != null ? !aoLevel.equals(that.aoLevel) : that.aoLevel != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (plainCode != null ? !plainCode.equals(that.plainCode) : that.plainCode != null) return false;
        if (actStatus != null ? !actStatus.equals(that.actStatus) : that.actStatus != null) return false;
        if (centStatus != null ? !centStatus.equals(that.centStatus) : that.centStatus != null) return false;
        if (operStatus != null ? !operStatus.equals(that.operStatus) : that.operStatus != null) return false;
        if (currStatus != null ? !currStatus.equals(that.currStatus) : that.currStatus != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (liveStatus != null ? !liveStatus.equals(that.liveStatus) : that.liveStatus != null) return false;
        if (dtype != null ? !dtype.equals(that.dtype) : that.dtype != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (formalName != null ? formalName.hashCode() : 0);
        result = 31 * result + (regionCode != null ? regionCode.hashCode() : 0);
        result = 31 * result + (autoCode != null ? autoCode.hashCode() : 0);
        result = 31 * result + (areaCode != null ? areaCode.hashCode() : 0);
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (ctarCode != null ? ctarCode.hashCode() : 0);
        result = 31 * result + (placeCode != null ? placeCode.hashCode() : 0);
        result = 31 * result + (streetCode != null ? streetCode.hashCode() : 0);
        result = 31 * result + (extrCode != null ? extrCode.hashCode() : 0);
        result = 31 * result + (sextCode != null ? sextCode.hashCode() : 0);
        result = 31 * result + (offName != null ? offName.hashCode() : 0);
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (ifnsFl != null ? ifnsFl.hashCode() : 0);
        result = 31 * result + (terrIfnsFl != null ? terrIfnsFl.hashCode() : 0);
        result = 31 * result + (ifnsUl != null ? ifnsUl.hashCode() : 0);
        result = 31 * result + (terrIfnsUl != null ? terrIfnsUl.hashCode() : 0);
        result = 31 * result + (okato != null ? okato.hashCode() : 0);
        result = 31 * result + (oktmo != null ? oktmo.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (aoLevel != null ? aoLevel.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (plainCode != null ? plainCode.hashCode() : 0);
        result = 31 * result + (actStatus != null ? actStatus.hashCode() : 0);
        result = 31 * result + (centStatus != null ? centStatus.hashCode() : 0);
        result = 31 * result + (operStatus != null ? operStatus.hashCode() : 0);
        result = 31 * result + (currStatus != null ? currStatus.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (liveStatus != null ? liveStatus.hashCode() : 0);
        result = 31 * result + (dtype != null ? dtype.hashCode() : 0);
        return result;
    }
}
