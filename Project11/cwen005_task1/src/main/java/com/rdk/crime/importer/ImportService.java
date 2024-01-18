package com.rdk.crime.importer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.rdk.crime.domain.Crime;
import com.rdk.crime.repository.CrimeRepository;

@Service
public class ImportService {

    private static final Logger LOG = LoggerFactory
        .getLogger( ImportService.class );

    @Autowired
    private CrimeRepository repository;

    private Path inputFile = Paths
        .get( "/Users/sascharodekamp/Downloads/Crimes_-_2001_to_present.csv" );

    public void readAndStoreCrimes() {

        if ( !inputFile.toFile().exists() ) {
            LOG.warn( "Input file was not found {}", inputFile.toFile()
                .getAbsolutePath() );
        }

        Instant start = Instant.now();
        long count = 0;

        try (BufferedReader reader = new BufferedReader( new FileReader(
            inputFile.toFile() ) )) {

            // skip first line, because its the CSV Header
            reader.readLine();

            String line = null;
            int packageCount = 0;
            List<Crime> crimePack = Lists.newArrayList();
            while ( ( line = reader.readLine() ) != null ) {
                Crime crime = Crime.createFromInput( line );
                if ( crime == null ) {
                    continue;
                }

                if ( packageCount == 500000 ) {
                    repository.save( crimePack );
                    packageCount = 0;
                    crimePack.clear();
                    LOG.info( "Stored records {}", count );
                }

                count++;
                packageCount++;
                crimePack.add( crime );

            }
        }
        catch ( Exception e ) {
            LOG.error( e.getMessage(), e );
        }

        Instant end = Instant.now();

        long d = end.getEpochSecond() - start.getEpochSecond();
        LOG.info( "Done in {}", Long.valueOf( d ) );
        LOG.info( "Stored overall records {}", count );
    }

}
