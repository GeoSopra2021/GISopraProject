package org.geosopra;

import org.geotools.geometry.GeneralDirectPosition;
import org.geotools.referencing.ReferencingFactoryFinder;
import org.geotools.referencing.operation.DefaultCoordinateOperationFactory;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CRSAuthorityFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.CoordinateOperation;
import org.opengis.referencing.operation.TransformException;

public class ConvertCoords {

    public double[] transformUTMToWGS84(long easting, long northing) throws FactoryException, TransformException {

        CRSAuthorityFactory crsFac = ReferencingFactoryFinder
                .getCRSAuthorityFactory("EPSG", null);

        CoordinateReferenceSystem wgs84crs = crsFac
                .createCoordinateReferenceSystem("4326");
        CoordinateReferenceSystem osgbCrs = crsFac
                .createCoordinateReferenceSystem("32632");

        CoordinateOperation op = new DefaultCoordinateOperationFactory()
                .createOperation(osgbCrs, wgs84crs);

        DirectPosition eastNorth = new GeneralDirectPosition(easting, northing);
        DirectPosition latLng = op.getMathTransform().transform(eastNorth,
                eastNorth);

        double latitude = latLng.getOrdinate(0);
        double longitude = latLng.getOrdinate(1);

        return new double[]{latitude, longitude};

    }

}

