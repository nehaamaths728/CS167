package com.rdk.crime.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;

/**
 *
 *
 */
public class Crime {

	@Id
	private String id;

	private String internalId;

    private String caseNumber;

    private String date;

    private String block;

    private String iucr;

    private String primaryType;

    private String description;

    private String locationDescription;

    private String arrest;

    private String domestic;

    private String beat;

    private String district;

    private String ward;

    private String communityArea;

    private String fbiCode;

    private String xCoordinate;

    private String yCoordinate;

    private String year;

    private String updatedOn;

    private String latitude;

    private String longitude;

    private String location;

    private Crime() {}

    /**
     * Constructs the {@link Crime} object from an input string. It maps the csv input line to a new object.
     *
     * @param inputLine
     * @return
     */
    public static Crime createFromInput( String inputLine ) {
		String crimeLine = com.rdk.crime.utils.StringUtils
				.normalizeSubstring(inputLine);
        return mapInputToCrimeObject( crimeLine );
    }

    private static Crime mapInputToCrimeObject( String crimeLine ) {
        Crime crime = new Crime();
        String[] split = crimeLine.trim().split( "," );

        if ( split.length != 22 ) {
            return null;
        }

		crime.internalId = split[0];
        crime.caseNumber = split[1];
        crime.date = split[2];
        crime.block = split[3];
        crime.iucr = split[4];
        crime.primaryType = split[5];
        crime.description = cleanStringValue( split[6] );
        crime.locationDescription = cleanStringValue( split[7] );
        crime.arrest = split[8];
        crime.domestic = split[9];
        crime.beat = split[10];
        crime.district = split[11];
        crime.ward = split[12];
        crime.communityArea = split[13];
        crime.fbiCode = split[14];
        crime.xCoordinate = split[15];
        crime.yCoordinate = split[16];
        crime.year = split[17];
        crime.updatedOn = split[18];
        crime.latitude = split[19];
        crime.longitude = split[20];
        crime.location = cleanStringValue( split[21] );

        return crime;
    }

    private static String cleanStringValue( String value ) {
        if ( StringUtils.isNotBlank( value ) ) {
            return value.replace( ";", "," ).replace( "\"", "" );
        }

        return "";
    }

	public String getInternalId() {
		return internalId;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public String getDate() {
        return date;
    }

    public String getBlock() {
        return block;
    }

    public String getIucr() {
        return iucr;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public String getDescription() {
        return description;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public String getArrest() {
        return arrest;
    }

    public String getDomestic() {
        return domestic;
    }

    public String getBeat() {
        return beat;
    }

    public String getDistrict() {
        return district;
    }

    public String getWard() {
        return ward;
    }

    public String getCommunityArea() {
        return communityArea;
    }

    public String getFbiCode() {
        return fbiCode;
    }

    public String getxCoordinate() {
        return xCoordinate;
    }

    public String getyCoordinate() {
        return yCoordinate;
    }

    public String getYear() {
        return year;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Crime{" +
 "id='" + internalId + '\'' +
            ", caseNumber='" + caseNumber + '\'' +
            ", date='" + date + '\'' +
            ", block='" + block + '\'' +
            ", iucr='" + iucr + '\'' +
            ", primaryType='" + primaryType + '\'' +
            ", description='" + description + '\'' +
            ", locationDescription='" + locationDescription + '\'' +
            ", arrest='" + arrest + '\'' +
            ", domestic='" + domestic + '\'' +
            ", beat='" + beat + '\'' +
            ", district='" + district + '\'' +
            ", ward='" + ward + '\'' +
            ", communityArea='" + communityArea + '\'' +
            ", fbiCode='" + fbiCode + '\'' +
            ", xCoordinate='" + xCoordinate + '\'' +
            ", yCoordinate='" + yCoordinate + '\'' +
            ", year='" + year + '\'' +
            ", updatedOn='" + updatedOn + '\'' +
            ", latitude='" + latitude + '\'' +
            ", longitude='" + longitude + '\'' +
            ", location='" + location + '\'' +
            '}';
    }
}
