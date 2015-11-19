package database;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import model.BeaconCache;

/**
 * Created by dcortess on 9/5/15.
 */
public class DatabaseManager {

    private static DatabaseManager instance;
    private DatabaseHelper helper;

    public static void init(Context context){
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseManager(Context context) {
        helper = new DatabaseHelper(context);
    }

    private DatabaseHelper getHelper() {
        return helper;
    }

    public List<BeaconCache> getAllBeaconCache() {
        List<BeaconCache> beaconList = null;
        try {
            beaconList = getHelper().getBeaconCachesListDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beaconList;
    }

    public void addBeaconCache(BeaconCache bCache) {
        try {
            getHelper().getBeaconCachesListDao().create(bCache);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBeaconCache(BeaconCache bCache) {
        try {
            getHelper().getBeaconCachesListDao().update(bCache);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBeaconCache() {
        List<BeaconCache> lista= getAllBeaconCache();
        try {
            getHelper().getBeaconCachesListDao().delete(lista);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
