package com.randomappsinc.bro.Persistence;

import com.randomappsinc.bro.Models.Record;
import com.randomappsinc.bro.Utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.Sort;

/**
 * Created by alexanderchiou on 10/17/16.
 */

public class DatabaseManager {
    private static final int CURRENT_REALM_VERSION = 0;
    private static DatabaseManager instance;

    public static DatabaseManager get() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    private static synchronized DatabaseManager getSync() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private Realm getRealm() {
        return Realm.getDefaultInstance();
    }

    private RealmMigration migration = new RealmMigration() {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {}
    };

    private DatabaseManager() {
        Realm.init(MyApplication.getAppContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(CURRENT_REALM_VERSION)
                .migration(migration)
                .build();
        Realm.setDefaultConfiguration(config);
    }

    public List<Record> getHistory() {
        List<RecordDO> recordDOs = getRealm().where(RecordDO.class).findAll();

        List<Record> records = new ArrayList<>();
        for (RecordDO recordDO : recordDOs) {
            records.add(getRecordFromDO(recordDO));
        }
        return records;
    }

    private Record getRecordFromDO(RecordDO recordDO) {
        Record record = new Record();
        record.setId(recordDO.getId());
        record.setTargetPhoneNumber(recordDO.getTargetPhoneNumber());
        record.setTargetName(recordDO.getTargetName());
        record.setMessage(recordDO.getMessage());
        record.setTime(recordDO.getTime());
        return record;
    }

    public void deleteRecord(final long recordId) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(RecordDO.class).equalTo("id", recordId).findFirst().deleteFromRealm();
            }
        });
    }

    public void addRecord(final Record record) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(record.toRecordDO());
            }
        });
    }

    public long getNextId() {
        List<RecordDO> recordDOs = getRealm().where(RecordDO.class).findAllSorted("id", Sort.DESCENDING);
        return recordDOs.isEmpty() ? 0L : recordDOs.get(0).getId() + 1L;
    }
}
